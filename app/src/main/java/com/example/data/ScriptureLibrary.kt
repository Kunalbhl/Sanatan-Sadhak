package com.example.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Knowledge Hub texts (Vedas, Upanishads, Itihasa, 18 Mahapuranas, Darshanas, Granths) fetched live
 * from Wikipedia (English + Hindi), which cites its sources. Nothing is hard-coded except the list of
 * text names; content, length and updates come from the source. Cached in Room, refreshed weekly.
 */
object ScriptureLibrary {

    private const val TAG = "ScriptureLibrary"
    const val SOURCE = "Wikipedia (CC BY-SA)"
    private const val PREFS = "scripture_library"
    private const val WEEK = 7L * 24 * 60 * 60 * 1000

    /** category → English Wikipedia page titles */
    val catalog: Map<String, List<String>> = linkedMapOf(
        "Vedas & Vedangas" to listOf("Vedas", "Rigveda", "Samaveda", "Yajurveda", "Atharvaveda", "Vedanga", "Brahmana", "Aranyaka"),
        "Upanishads" to listOf(
            "Upanishads", "Isha Upanishad", "Kena Upanishad", "Katha Upanishad", "Prashna Upanishad", "Mundaka Upanishad",
            "Mandukya Upanishad", "Taittiriya Upanishad", "Aitareya Upanishad", "Chandogya Upanishad",
            "Brihadaranyaka Upanishad", "Shvetashvatara Upanishad"
        ),
        "Itihasa" to listOf("Itihasa", "Ramayana", "Mahabharata", "Bhagavad Gita", "Harivamsa", "Yoga Vasistha"),
        "Puranas" to listOf(
            "Puranas", "Vishnu Purana", "Narada Purana", "Padma Purana", "Garuda Purana", "Varaha Purana",
            "Bhagavata Purana", "Matsya Purana", "Kurma Purana", "Linga Purana", "Shiva Purana", "Skanda Purana",
            "Agni Purana", "Brahmanda Purana", "Brahma Vaivarta Purana", "Markandeya Purana", "Bhavishya Purana",
            "Vamana Purana", "Brahma Purana"
        ),
        "Darshana" to listOf("Hindu philosophy", "Nyaya", "Vaisheshika", "Samkhya", "Yoga (philosophy)", "Mimamsa", "Vedanta", "Advaita Vedanta", "Vishishtadvaita", "Dvaita Vedanta"),
        "Scriptures & Granths" to listOf(
            "Ramcharitmanas", "Devi Mahatmya", "Yoga Sutras of Patanjali", "Manusmriti", "Gita Govinda",
            "Vishnu Sahasranama", "Hanuman Chalisa", "Agama (Hinduism)", "Tantras (Hinduism)", "Dharmaśāstra"
        )
    )

    private val http = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()

    /** Refresh the library if it has never been fetched or is older than a week. Safe to call often. */
    suspend fun syncIfStale(context: Context, force: Boolean = false) = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (!force && System.currentTimeMillis() - prefs.getLong("last", 0) < WEEK) return@withContext
        val dao = AppDatabase.getInstance(context).knowledgeArticleDao()
        var saved = 0
        for ((category, titles) in catalog) {
            for (title in titles) {
                try {
                    val en = fetch("en", title) ?: continue
                    saved += upsert(dao, en, category, "en")
                    en.hiTitle?.let { hiTitle -> fetch("hi", hiTitle)?.let { saved += upsert(dao, it, category, "hi") } }
                } catch (e: Exception) {
                    Log.w(TAG, "$title: ${e.message}")
                }
            }
        }
        if (saved > 0) prefs.edit().putLong("last", System.currentTimeMillis()).apply()
        Log.i(TAG, "Knowledge library synced: $saved texts")
    }

    private data class Page(val title: String, val extract: String, val url: String, val hiTitle: String?)

    private fun fetch(lang: String, title: String): Page? {
        val url = "https://$lang.wikipedia.org/w/api.php".toHttpUrl().newBuilder()
            .addQueryParameter("action", "query").addQueryParameter("format", "json").addQueryParameter("formatversion", "2")
            .addQueryParameter("redirects", "1").addQueryParameter("titles", title)
            .addQueryParameter("prop", "extracts|langlinks|info").addQueryParameter("explaintext", "1")
            .addQueryParameter("exsectionformat", "wiki").addQueryParameter("inprop", "url")
            .addQueryParameter("lllang", "hi").build()
        val req = Request.Builder().url(url).header("User-Agent", "SanatanSadhak/1.0 (support@chaloone.com)").build()
        http.newCall(req).execute().use { r ->
            if (!r.isSuccessful) return null
            val page = JSONObject(r.body?.string() ?: return null).getJSONObject("query").getJSONArray("pages").getJSONObject(0)
            if (page.optBoolean("missing")) return null
            val extract = page.optString("extract").trim()
            if (extract.length < 200) return null
            val hi = page.optJSONArray("langlinks")?.optJSONObject(0)?.optString("title")?.ifBlank { null }
            return Page(page.getString("title"), extract, page.optString("fullurl"), hi)
        }
    }

    /** Drop reference-only tail sections; turn "== Heading ==" into readable headings. */
    private fun clean(text: String): String {
        val cut = Regex("\\n==\\s*(See also|References|Notes|Further reading|External links|Bibliography|Sources|Citations|इन्हें भी देखें|सन्दर्भ|संदर्भ|बाहरी कड़ियाँ)\\s*==").find(text)?.range?.first ?: text.length
        return text.substring(0, cut)
            .replace(Regex("\\n===+\\s*(.+?)\\s*===+"), "\n\n▸ $1\n")
            .replace(Regex("\\n==\\s*(.+?)\\s*=="), "\n\n■ $1\n")
            .replace(Regex("\\n{3,}"), "\n\n").trim()
    }

    private suspend fun upsert(dao: KnowledgeArticleDao, p: Page, category: String, lang: String): Int {
        val content = clean(p.extract)
        val summary = content.substringBefore("\n\n").take(400)
        val words = content.split(Regex("\\s+")).size
        val existing = dao.getAllArticles(lang).first().firstOrNull { it.title == p.title }
        val article = KnowledgeArticle(
            id = existing?.id ?: 0,
            category = category,
            title = p.title,
            summary = summary,
            content = content,
            language = lang,
            difficulty = if (words > 4000) "Advanced" else if (words > 1500) "Intermediate" else "Beginner",
            readTimeMinutes = maxOf(1, words / 200),
            format = "Scripture",
            sourceName = SOURCE,
            sourceType = "Encyclopedia",
            textReference = p.url,
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "ARTICLE"
        )
        if (existing == null) dao.insertArticle(article) else dao.updateArticle(article)
        return 1
    }
}
