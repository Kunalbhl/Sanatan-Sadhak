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
    private val OVERPASS = listOf(
        "https://overpass-api.de/api/interpreter",
        "https://overpass.kumi.systems/api/interpreter"
    )

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private data class CacheEntry(val at: Long, val temples: List<HinduTemple>)
    private val cache = HashMap<String, CacheEntry>()
    private const val CACHE_MS = 10 * 60 * 1000L

    data class Place(val name: String, val lat: Double, val lon: Double, val temple: HinduTemple?)

    /** Hindu temples within [radiusM] metres of the point. Empty list when offline or none found. */
    suspend fun around(lat: Double, lon: Double, radiusM: Int = 20000): List<HinduTemple> = withContext(Dispatchers.IO) {
        val key = "%.2f,%.2f,%d".format(lat, lon, radiusM)
        cache[key]?.let { if (System.currentTimeMillis() - it.at < CACHE_MS) return@withContext it.temples }

        val q = "[out:json][timeout:25];(" +
            "node[\"amenity\"=\"place_of_worship\"][\"religion\"=\"hindu\"][\"name\"](around:$radiusM,$lat,$lon);" +
            "way[\"amenity\"=\"place_of_worship\"][\"religion\"=\"hindu\"][\"name\"](around:$radiusM,$lat,$lon);" +
            ");out center tags 150;"
        for (endpoint in OVERPASS) {
            try {
                val req = Request.Builder().url(endpoint).header("User-Agent", UA)
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
                    cache[key] = CacheEntry(System.currentTimeMillis(), list)
                    return@withContext list
                }
            } catch (e: Exception) {
                Log.w(TAG, "Overpass $endpoint failed: ${e.message}")
            }
        }
        emptyList()
    }

    /** Search any temple or place name worldwide. */
    suspend fun search(query: String): List<Place> = withContext(Dispatchers.IO) {
        try {
            val url = "https://nominatim.openstreetmap.org/search?format=jsonv2&limit=6&addressdetails=1&extratags=1&namedetails=1&q=" +
                URLEncoder.encode(query, "UTF-8")
            val req = Request.Builder().url(url).header("User-Agent", UA).build()
            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return@withContext emptyList()
                val arr = JSONArray(resp.body?.string() ?: return@withContext emptyList())
                val out = ArrayList<Place>()
                for (i in 0 until arr.length()) {
                    val o = arr.getJSONObject(i)
                    val lat = o.optString("lat").toDoubleOrNull() ?: continue
                    val lon = o.optString("lon").toDoubleOrNull() ?: continue
                    val display = o.optString("name").ifBlank { o.optString("display_name").substringBefore(",") }
                    val extra = o.optJSONObject("extratags") ?: JSONObject()
                    val isHindu = extra.optString("religion") == "hindu" || o.optString("type") == "hindu_temple"
                    val tags = JSONObject(extra.toString()).apply {
                        put("name", display)
                        o.optJSONObject("namedetails")?.optString("name:hi")?.takeIf { it.isNotBlank() }?.let { put("name:hi", it) }
                        o.optJSONObject("address")?.let { a ->
                            for (k in listOf("city", "town", "village", "suburb", "state", "country")) {
                                if (a.has(k)) put("addr:$k", a.getString(k))
                            }
                        }
                    }
                    out.add(Place(display, lat, lon, if (isHindu) toTemple(o.optLong("osm_id"), tags, lat, lon) else null))
                }
                out
            }
        } catch (e: Exception) {
            Log.w(TAG, "Nominatim search failed: ${e.message}")
            emptyList()
        }
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
        val tagged = tags.optString("deity").ifBlank { tags.optString("deity:en") }
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
        val hours = tags.optString("opening_hours")
        val website = tags.optString("website").ifBlank { tags.optString("contact:website") }
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
