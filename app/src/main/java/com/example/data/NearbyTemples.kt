package com.example.data

import android.util.Log
import com.example.ui.screens.HinduTemple
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

/**
 * Live temple lookup for anywhere in the world, from OpenStreetMap's public data:
 *  - Overpass API: Hindu places of worship around a point
 *  - Nominatim: search a temple or place by name
 * Nothing here is hardcoded; the curated list in TempleFinderScreen only adds rich
 * descriptions for a few famous temples.
 */
object NearbyTemples {
    private const val TAG = "NearbyTemples"
    private const val UA = "SanatanSadhak/1.3 (support@chaloone.com)"
    // Backup only: the main Overpass server often answers 406, so Nominatim is the primary source.
    private val OVERPASS = listOf(
        "https://overpass.private.coffee/api/interpreter",
        "https://overpass.kumi.systems/api/interpreter",
        "https://overpass-api.de/api/interpreter"
    )

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private data class CacheEntry(val at: Long, val temples: List<HinduTemple>)
    private val cache = HashMap<String, CacheEntry>()
    private const val CACHE_MS = 10 * 60 * 1000L

    data class Place(val name: String, val lat: Double, val lon: Double, val temple: HinduTemple?)

    /**
     * Hindu temples near the point, nearest first. Uses Nominatim's "place of worship near lat,lon",
     * which returns the nearest 40 with their religion tag, then drops other faiths. Overpass is only a backup.
     */
    suspend fun around(lat: Double, lon: Double, radiusM: Int = 20000): List<HinduTemple> = withContext(Dispatchers.IO) {
        val key = "%.2f,%.2f".format(lat, lon)
        cache[key]?.let { if (System.currentTimeMillis() - it.at < CACHE_MS) return@withContext it.temples }

        var list = nominatim("place of worship near $lat,$lon", 40).mapNotNull { it.temple }
        if (list.size < 5) {
            val extra = overpass(lat, lon, radiusM)
            list = (list + extra).distinctBy { it.id }
        }
        if (list.isNotEmpty()) cache[key] = CacheEntry(System.currentTimeMillis(), list)
        list
    }

    private fun overpass(lat: Double, lon: Double, radiusM: Int): List<HinduTemple> {
        val q = "[out:json][timeout:25];(" +
            "node[\"amenity\"=\"place_of_worship\"][\"religion\"=\"hindu\"][\"name\"](around:$radiusM,$lat,$lon);" +
            "way[\"amenity\"=\"place_of_worship\"][\"religion\"=\"hindu\"][\"name\"](around:$radiusM,$lat,$lon);" +
            ");out center tags 150;"
        for (endpoint in OVERPASS) {
            try {
                val req = Request.Builder().url(endpoint).header("User-Agent", UA).header("Accept", "application/json")
                    .post(FormBody.Builder().add("data", q).build()).build()
                client.newCall(req).execute().use { resp ->
                    if (!resp.isSuccessful) return@use
                    val body = resp.body?.string() ?: return@use
                    val elements = JSONObject(body).optJSONArray("elements") ?: JSONArray()
                    val list = ArrayList<HinduTemple>()
                    for (i in 0 until elements.length()) {
                        val e = elements.getJSONObject(i)
                        val tags = e.optJSONObject("tags") ?: continue
                        val (tLat, tLon) = if (e.has("lat")) e.getDouble("lat") to e.getDouble("lon")
                        else e.optJSONObject("center")?.let { it.getDouble("lat") to it.getDouble("lon") } ?: continue
                        toTemple(e.optLong("id"), tags, tLat, tLon)?.let { list.add(it) }
                    }
                    return list
                }
            } catch (e: Exception) {
                Log.w(TAG, "Overpass $endpoint failed: ${e.message}")
            }
        }
        return emptyList()
    }

    /**
     * Search any temple or place on earth. Runs the text as typed (finds a named temple or a city) and
     * "place of worship in <text>" (lists the temples of that place); other faiths are filtered out.
     */
    suspend fun search(query: String): List<Place> = withContext(Dispatchers.IO) {
        val first = nominatim(query, 8)
        kotlinx.coroutines.delay(1100) // Nominatim asks for at most one request per second
        val second = nominatim("place of worship in $query", 40)
        (first + second).distinctBy { "%.4f,%.4f".format(it.lat, it.lon) + it.name }
    }

    /** One Nominatim request, mapped to places; a place carries a temple only when it is a Hindu place of worship. */
    private fun nominatim(q: String, limit: Int): List<Place> {
        try {
            val url = "https://nominatim.openstreetmap.org/search?format=jsonv2&limit=$limit&addressdetails=1&extratags=1&namedetails=1&q=" +
                URLEncoder.encode(q, "UTF-8")
            val req = Request.Builder().url(url).header("User-Agent", UA).header("Accept", "application/json").build()
            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) { Log.w(TAG, "Nominatim HTTP ${resp.code}"); return emptyList() }
                val arr = JSONArray(resp.body?.string() ?: return emptyList())
                val out = ArrayList<Place>()
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val lat = o.optString("lat").toDoubleOrNull() ?: continue
                    val lon = o.optString("lon").toDoubleOrNull() ?: continue
                    val rawName = o.optString("name")
                    val display = rawName.ifBlank { o.optString("display_name").substringBefore(",") }
                    val extra = o.optJSONObject("extratags") ?: JSONObject()
                    val isWorship = o.optString("type") == "place_of_worship" || o.optString("type") == "hindu_temple"
                    val tags = JSONObject(extra.toString()).apply {
                        put("name", rawName)
                        o.optJSONObject("namedetails")?.let { nd ->
                            nd.optString("name:en").takeIf { it.isNotBlank() }?.let { put("name:en", it) }
                            nd.optString("name:hi").takeIf { it.isNotBlank() }?.let { put("name:hi", it) }
                        }
                        o.optJSONObject("address")?.let { a ->
                            for (k in listOf("road", "suburb", "city", "town", "village", "state", "country")) {
                                if (a.has(k)) put("addr:$k", a.getString(k))
                            }
                        }
                    }
                    val temple = if (isWorship && rawName.isNotBlank() && isHindu(extra, rawName)) toTemple(o.optLong("osm_id"), tags, lat, lon) else null
                    // A worship place of another faith is neither a temple nor a "place" to centre on
                    if (isWorship && temple == null) continue
                    out.add(Place(display, lat, lon, temple))
                }
                return out
            }
        } catch (e: Exception) {
            Log.w(TAG, "Nominatim failed: ${e.message}")
            return emptyList()
        }
    }

    private val otherFaith = listOf("church", "masjid", "mosque", "gurudwara", "gurdwara", "monastery", "dargah", "buddh", "jain", "cathedral", "chapel", "synagogue")
    private val templeWords = listOf("mandir", "temple", "devi", "shiv", "hanuman", "krishna", "ram", "ganesh", "durga", "kali", "mata", "dham", "vishnu", "balaji", "sai", "iskcon", "ashram")

    private fun isHindu(extra: JSONObject, name: String): Boolean {
        val religion = extra.optString("religion")
        if (religion.isNotBlank()) return religion == "hindu"
        val n = name.lowercase()
        if (otherFaith.any { n.contains(it) }) return false
        return templeWords.any { n.contains(it) }
    }

    // ---- mapping ----

    private val deityByName = listOf(
        "shiv" to "Lord Shiva", "mahadev" to "Lord Shiva", "jyotirling" to "Lord Shiva",
        "hanuman" to "Lord Hanuman", "balaji" to "Lord Venkateswara (Vishnu)", "venkatesh" to "Lord Venkateswara (Vishnu)",
        "krishna" to "Lord Krishna", "iskcon" to "Lord Krishna", "radha" to "Radha-Krishna",
        "ram" to "Lord Rama", "jagannath" to "Lord Jagannath", "vishnu" to "Lord Vishnu", "narayan" to "Lord Vishnu",
        "ganesh" to "Lord Ganesha", "durga" to "Goddess Durga", "kali" to "Goddess Kali", "devi" to "Goddess (Devi)",
        "mata" to "Goddess (Devi)", "lakshmi" to "Goddess Lakshmi", "saraswati" to "Goddess Saraswati",
        "murugan" to "Lord Murugan", "subramanya" to "Lord Murugan", "ayyappa" to "Lord Ayyappa", "surya" to "Lord Surya",
        "sai" to "Sai Baba"
    )

    private fun deityOf(tags: JSONObject): String {
        val tagged = tags.optString("deity").ifBlank { tags.optString("hindu:deity") }.ifBlank { tags.optString("deity:en") }
        if (tagged.isNotBlank()) return tagged.split(";").joinToString(", ") { it.trim().replaceFirstChar { c -> c.uppercase() } }
        val n = (tags.optString("name:en").ifBlank { tags.optString("name") }).lowercase()
        deityByName.firstOrNull { n.contains(it.first) }?.let { return it.second }
        return "Hindu temple"
    }

    private fun toTemple(osmId: Long, tags: JSONObject, lat: Double, lon: Double): HinduTemple? {
        val name = tags.optString("name:en").ifBlank { tags.optString("name") }
        if (name.isBlank()) return null
        val nameHi = tags.optString("name:hi").ifBlank { tags.optString("name") }
        val city = listOf("addr:city", "addr:town", "addr:village", "addr:suburb", "addr:state", "addr:country")
            .map { tags.optString(it) }.firstOrNull { it.isNotBlank() } ?: ""
        val addr = listOf("addr:housename", "addr:street", "addr:suburb", "addr:city", "addr:state")
            .map { tags.optString(it) }.filter { it.isNotBlank() }.distinct().joinToString(", ")
        val deity = deityOf(tags)
        val desc = tags.optString("description:en").ifBlank { tags.optString("description") }.ifBlank { addr }
            .let { if (wikiUrl.isNotBlank() && it.isNotBlank()) "$it. Tap Live timings for the Wikipedia page." else it }
        val hours = tags.optString("opening_hours").ifBlank { tags.optString("service_times") }
        val wiki = tags.optString("wikipedia") // like "en:Mahakaleshwar Jyotirlinga"
        val wikiUrl = if (wiki.contains(":")) "https://${wiki.substringBefore(":")}.wikipedia.org/wiki/" + wiki.substringAfter(":").replace(' ', '_') else ""
        val website = tags.optString("website").ifBlank { tags.optString("contact:website") }.ifBlank { wikiUrl }
        return HinduTemple(
            id = 5_000_000_000L + osmId,
            nameEn = name, nameHi = nameHi,
            deityEn = deity, deityHi = deity,
            lat = lat, lon = lon,
            descEn = desc, descHi = desc,
            significanceEn = "", significanceHi = "",
            openingHoursEn = hours, openingHoursHi = hours,
            dressCodeEn = "", dressCodeHi = "",
            majorFestivalsEn = "", majorFestivalsHi = "",
            cityEn = city, cityHi = city,
            imageUrl = null,
            website = website.ifBlank { null }
        )
    }
}
