package com.example.data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/** A YouTube channel whose uploads feed the in-app video library. */
data class YoutubeChannel(
    val channelId: String,
    val title: String,
    val url: String = "https://www.youtube.com/channel/$channelId",
    val enabled: Boolean = true,
    val addedBy: String = "",
    val addedAt: Long = System.currentTimeMillis()
)

/**
 * Admin-managed list of YouTube channels.
 * Source of truth: Firestore collection `youtube_channels` (public read, SuperUser write).
 * A local copy in SharedPreferences keeps the library working offline and on first launch.
 */
class YoutubeChannelStore private constructor(private val context: Context) {

    private val prefs = context.getSharedPreferences("youtube_channels", Context.MODE_PRIVATE)
    private val _channels = MutableStateFlow(loadLocal())
    val channels: StateFlow<List<YoutubeChannel>> = _channels.asStateFlow()

    /** Video IDs an admin has hidden from users (Firestore `hidden_videos`, doc id = videoId). */
    private val _hidden = MutableStateFlow(prefs.getStringSet(HIDDEN_KEY, emptySet())!!.toSet())
    val hiddenVideoIds: StateFlow<Set<String>> = _hidden.asStateFlow()

    suspend fun refreshHidden() = withContext(Dispatchers.IO) {
        try {
            val snap = db()?.collection(HIDDEN_COLLECTION)?.get()?.await() ?: return@withContext
            saveHidden(snap.documents.map { it.id }.toSet())
        } catch (e: Exception) { Log.w(TAG, "hidden refresh: ${e.message}") }
    }

    suspend fun setVideoHidden(videoId: String, hidden: Boolean, adminEmail: String) = withContext(Dispatchers.IO) {
        saveHidden(if (hidden) _hidden.value + videoId else _hidden.value - videoId)
        try {
            val ref = db()?.collection(HIDDEN_COLLECTION)?.document(videoId)
            if (hidden) ref?.set(mapOf("hiddenBy" to adminEmail, "hiddenAt" to System.currentTimeMillis()))?.await()
            else ref?.delete()?.await()
        } catch (e: Exception) { Log.w(TAG, "hide video: ${e.message}") }
    }

    private fun saveHidden(set: Set<String>) {
        _hidden.value = set
        prefs.edit().putStringSet(HIDDEN_KEY, set).apply()
    }

    private val http = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build()

    private fun db(): FirebaseFirestore? = try { FirebaseFirestore.getInstance() } catch (e: Exception) { null }

    fun enabledChannels(): List<YoutubeChannel> = _channels.value.filter { it.enabled }

    /** Pull the latest list from Firestore (falls back to the local copy). */
    suspend fun refresh(): List<YoutubeChannel> = withContext(Dispatchers.IO) {
        refreshHidden()
        try {
            val snap = db()?.collection(COLLECTION)?.get()?.await()
            if (snap != null && !snap.isEmpty) {
                val list = snap.documents.mapNotNull { d ->
                    val id = d.getString("channelId") ?: d.id
                    if (!id.startsWith("UC")) null else YoutubeChannel(
                        channelId = id,
                        title = d.getString("title") ?: id,
                        url = d.getString("url") ?: "https://www.youtube.com/channel/$id",
                        enabled = d.getBoolean("enabled") ?: true,
                        addedBy = d.getString("addedBy") ?: "",
                        addedAt = d.getLong("addedAt") ?: 0L
                    )
                }.sortedBy { it.addedAt }
                save(list)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Channel refresh failed, using local list: ${e.message}")
        }
        _channels.value
    }

    /**
     * Add a channel from a URL, @handle or UC… id. Resolves handles to the channel id and
     * reads the channel title from its public RSS feed, so admins never need the raw id.
     */
    suspend fun addChannel(input: String, adminEmail: String): Result<YoutubeChannel> = withContext(Dispatchers.IO) {
        try {
            val id = resolveChannelId(input.trim()) ?: return@withContext Result.failure(
                IllegalArgumentException("Couldn't find that channel. Paste the channel link, @handle or its UC… id.")
            )
            if (_channels.value.any { it.channelId == id }) {
                return@withContext Result.failure(IllegalStateException("That channel is already in the list."))
            }
            val title = fetchTitle(id) ?: return@withContext Result.failure(
                IllegalStateException("YouTube didn't return a feed for this channel. Check the link and try again.")
            )
            val ch = YoutubeChannel(channelId = id, title = title, addedBy = adminEmail)
            db()?.collection(COLLECTION)?.document(id)?.set(toMap(ch))?.await()
            save(_channels.value + ch)
            Result.success(ch)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun setEnabled(channelId: String, enabled: Boolean) = withContext(Dispatchers.IO) {
        save(_channels.value.map { if (it.channelId == channelId) it.copy(enabled = enabled) else it })
        try { db()?.collection(COLLECTION)?.document(channelId)?.update("enabled", enabled)?.await() } catch (e: Exception) { Log.w(TAG, "toggle: ${e.message}") }
    }

    suspend fun remove(channelId: String) = withContext(Dispatchers.IO) {
        save(_channels.value.filterNot { it.channelId == channelId })
        try { db()?.collection(COLLECTION)?.document(channelId)?.delete()?.await() } catch (e: Exception) { Log.w(TAG, "remove: ${e.message}") }
    }

    private fun resolveChannelId(input: String): String? {
        Regex("(UC[A-Za-z0-9_-]{22})").find(input)?.let { return it.value }
        val url = when {
            input.startsWith("@") -> "https://www.youtube.com/$input"
            input.startsWith("http") -> input
            input.contains("youtube.com") -> "https://$input"
            else -> "https://www.youtube.com/@$input"
        }
        return try {
            http.newCall(Request.Builder().url(url).header("Accept-Language", "en").build()).execute().use { r ->
                val html = r.body?.string() ?: return null
                Regex("\"externalId\":\"(UC[A-Za-z0-9_-]{22})\"").find(html)?.groupValues?.get(1)
                    ?: Regex("youtube\\.com/channel/(UC[A-Za-z0-9_-]{22})").find(html)?.groupValues?.get(1)
                    ?: Regex("\"channelId\":\"(UC[A-Za-z0-9_-]{22})\"").find(html)?.groupValues?.get(1)
            }
        } catch (e: Exception) { null }
    }

    private fun fetchTitle(id: String): String? = try {
        http.newCall(Request.Builder().url("https://www.youtube.com/feeds/videos.xml?channel_id=$id").build()).execute().use { r ->
            if (!r.isSuccessful) null
            else Regex("<title>([^<]+)</title>").find(r.body?.string() ?: "")?.groupValues?.get(1)
                ?.replace("&amp;", "&")?.replace("&#39;", "'")?.replace("&quot;", "\"")
        }
    } catch (e: Exception) { null }

    private fun toMap(c: YoutubeChannel) = mapOf(
        "channelId" to c.channelId, "title" to c.title, "url" to c.url,
        "enabled" to c.enabled, "addedBy" to c.addedBy, "addedAt" to c.addedAt
    )

    private fun save(list: List<YoutubeChannel>) {
        _channels.value = list
        val arr = JSONArray()
        list.forEach { arr.put(JSONObject(toMap(it))) }
        prefs.edit().putString(KEY, arr.toString()).apply()
    }

    private fun loadLocal(): List<YoutubeChannel> {
        val raw = prefs.getString(KEY, null) ?: return listOf(DEFAULT)
        return try {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                YoutubeChannel(
                    channelId = o.getString("channelId"), title = o.optString("title"),
                    url = o.optString("url"), enabled = o.optBoolean("enabled", true),
                    addedBy = o.optString("addedBy"), addedAt = o.optLong("addedAt")
                )
            }
        } catch (e: Exception) { listOf(DEFAULT) }
    }

    companion object {
        private const val TAG = "YoutubeChannelStore"
        private const val COLLECTION = "youtube_channels"
        private const val KEY = "channels_json"
        private const val HIDDEN_KEY = "hidden_video_ids"
        private const val HIDDEN_COLLECTION = "hidden_videos"
        val DEFAULT = YoutubeChannel("UCzHzPEdLJEvTSHn_ev3JYBA", "Sanatan Sadhak", "https://www.youtube.com/@SanatanSadhakk", addedAt = 0L)

        @Volatile private var instance: YoutubeChannelStore? = null
        fun get(context: Context): YoutubeChannelStore =
            instance ?: synchronized(this) { instance ?: YoutubeChannelStore(context.applicationContext).also { instance = it } }
    }
}
