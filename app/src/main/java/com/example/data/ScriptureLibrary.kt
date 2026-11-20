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
        ),
        "Deities & Concepts" to listOf(
            "Vishnu", "Shiva", "Brahma", "Devi", "Ganesha", "Hanuman", "Rama", "Krishna", "Durga", "Lakshmi", "Saraswati",
            "Kartikeya", "Surya", "Kali", "Parvati", "Narasimha", "Venkateswara", "Jagannath", "Ayyappan", "Dattatreya",
            "Radha", "Sita", "Vishvakarma", "Hindu deities", "Trimurti", "Dashavatara", "Navagraha", "Ashtavinayaka"
        ),
        "Spiritual Concepts" to listOf(
            "Dharma", "Karma in Hinduism", "Moksha", "Atman (Hinduism)", "Brahman", "Maya (religion)", "Samsara", "Ahimsa",
            "Bhakti", "Jnana yoga", "Karma yoga", "Raja yoga", "Bhakti yoga", "Guṇa", "Ashrama (stage)", "Purushartha",
            "Reincarnation", "Hindu cosmology", "Yuga", "Avatar", "Dhyana in Hinduism", "Satsang", "Guru–shishya tradition",
            "Sanskrit", "Hindu eschatology", "Ishvara", "Shakti", "Prana", "Chakra", "Kundalini"
        ),
        "Sadhana & Yoga" to listOf(
            "Yoga", "Pranayama", "Meditation", "Japa", "Hatha yoga", "Kriya Yoga", "Ashtanga (eight limbs of yoga)",
            "Asana", "Tantra", "Pratyahara", "Samadhi", "Ayurveda", "Jyotisha", "Vastu shastra", "Panchangam", "Hindu calendar"
        ),
        "Mantras & Chanting" to listOf(
            "Om", "Gayatri Mantra", "Mantra", "Maha Mrityunjaya Mantra", "Hare Krishna (mantra)", "Namaste", "Bija mantra",
            "Shanti Mantras", "Sanskrit prosody", "Vedic chant", "Kirtan", "Bhajan", "Aarti", "Stotra"
        ),
        "Pooja & Vidhi" to listOf(
            "Puja (Hinduism)", "Yajna", "Havan", "Tilaka", "Rangoli", "Sandhyavandanam", "Samskara", "Upanayana", "Hindu wedding",
            "Shraddha (Hinduism)", "Prasada", "Abhisheka", "Darshan (Indian religions)", "Diya (lamp)", "Tulsi"
        ),
        "Festivals & Fasting" to listOf(
            "Diwali", "Holi", "Navaratri", "Maha Shivaratri", "Ganesh Chaturthi", "Raksha Bandhan", "Krishna Janmashtami",
            "Makar Sankranti", "Rama Navami", "Dussehra", "Kumbh Mela", "Ratha Yatra", "Pongal", "Onam", "Vasant Panchami",
            "Karva Chauth", "Ugadi", "Guru Purnima", "Hanuman Jayanti", "Ekadashi", "Pradosha", "Sankashti Chaturthi",
            "Akshaya Tritiya", "Chhath", "Durga Puja", "Vijayadashami", "Dhanteras", "Bhai Dooj", "Fasting in Hinduism"
        ),
        "Temples & Sacred Sites" to listOf(
            "Char Dham", "Kedarnath Temple", "Badrinath Temple", "Jagannath Temple, Puri", "Ram Mandir, Ayodhya", "Kashi Vishwanath Temple",
            "Tirumala Venkateswara Temple", "Vaishno Devi", "Somnath temple", "Jyotirlinga", "Shakti Peetha", "Dwarkadhish Temple",
            "Meenakshi Temple", "Amarnath Temple", "Kailash Mansarovar", "Varanasi", "Ayodhya", "Mathura", "Vrindavan", "Rameswaram",
            "Haridwar", "Ujjain", "Prayagraj", "Nashik", "Pushkar", "Sabarimala", "Konark Sun Temple", "Siddhivinayak Temple, Mumbai",
            "Mahakaleshwar Jyotirlinga", "Sapta Puri", "Hindu pilgrimage sites", "Pilgrimage in Hinduism"
        ),
        "Saints & Acharyas" to listOf(
            "Adi Shankara", "Ramanuja", "Madhvacharya", "Chaitanya Mahaprabhu", "Tulsidas", "Kabir", "Meera", "Ramakrishna",
            "Swami Vivekananda", "Sri Aurobindo", "Ramana Maharshi", "Valmiki", "Vyasa", "Patanjali", "Surdas", "Tukaram",
            "Dnyaneshwar", "Basava", "Vallabhacharya", "Nimbarkacharya", "Paramahansa Yogananda", "Sant Mat", "Ravidas",
            "Narsinh Mehta", "Purandara Dasa", "Annamacharya", "Namdev", "Eknath", "Ramdas", "Sri Chinmoy", "Anandamayi Ma"
        )
    )

    /** Wikipedia categories whose pages are discovered live, so newly added articles appear without an app update. */
    private val discoveredCategories: List<Triple<String, String, Int>> = listOf(
        Triple("Festivals & Fasting", "Hindu festivals", 40),
        Triple("Deities & Concepts", "Hindu deities", 40),
        Triple("Spiritual Concepts", "Hindu philosophical concepts", 40),
        Triple("Temples & Sacred Sites", "Hindu pilgrimage sites", 40),
        Triple("Mantras & Chanting", "Hindu mantras", 30),
        Triple("Sadhana & Yoga", "Yoga", 20)
    )

    private val http = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()

    private const val DAY = 24L * 60 * 60 * 1000
    private const val MAX_FETCH_PER_RUN = 120

    /**
     * Keeps the library in step with Wikipedia. Once a day it asks which pages changed (one cheap request
     * per 50 titles) and downloads only new or changed ones, so edits and additions flow in without a new
     * app version. Large first fills continue over the next few app opens.
     */
    suspend fun syncIfStale(context: Context, force: Boolean = false) = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        if (!force && System.currentTimeMillis() - prefs.getLong("last", 0) < DAY) return@withContext
        val dao = AppDatabase.getInstance(context).knowledgeArticleDao()

        syncApprovedContributions(context, dao)

        // Curated titles + pages discovered in Wikipedia categories
        val plan = LinkedHashMap<String, MutableList<String>>()
        for ((cat, titles) in catalog) plan.getOrPut(cat) { mutableListOf() }.addAll(titles)
        for ((cat, wikiCategory, cap) in discoveredCategories) {
            try {
                val found = categoryMembers(wikiCategory, cap)
                val known = plan.getOrPut(cat) { mutableListOf() }
                for (t in found) if (known.none { it.equals(t, ignoreCase = true) }) known.add(t)
            } catch (e: Exception) {
                Log.w(TAG, "category $wikiCategory: ${e.message}")
            }
        }

        var saved = 0
        var pending = 0
        val done = HashSet<String>()
        val editor = prefs.edit()
        for ((category, titles) in plan) {
            for (batch in titles.distinct().chunked(50)) {
                val revs = try { revisions("en", batch) } catch (e: Exception) { Log.w(TAG, "revisions: ${e.message}"); continue }
                for (title in batch) {
                    if (!done.add(title.lowercase())) continue   // already handled under another category
                    val rev = revs[title] ?: continue            // page does not exist
                    if (!force && prefs.getLong("rev_$title", 0L) == rev) continue // unchanged since last download
                    if (saved >= MAX_FETCH_PER_RUN) { pending++; continue }
                    try {
                        val en = fetch("en", title) ?: continue
                        saved += upsert(dao, en, category, "en")
                        en.hiTitle?.let { hiTitle -> fetch("hi", hiTitle)?.let { saved += upsert(dao, it, category, "hi") } }
                        editor.putLong("rev_$title", rev)
                    } catch (e: Exception) {
                        Log.w(TAG, "$title: ${e.message}")
                    }
                }
            }
        }
        // If a backlog remains, look again soon instead of waiting a full day
        editor.putLong("last", if (pending > 0) System.currentTimeMillis() - DAY + 30L * 60 * 1000 else System.currentTimeMillis())
        editor.apply()
        Log.i(TAG, "Knowledge library synced: $saved texts, $pending waiting for the next run")
    }

    const val COMMUNITY_SOURCE = "Community contributor (approved)"

    /** Mirrors contributions an admin/moderator approved; anything later withdrawn is removed again. */
    private suspend fun syncApprovedContributions(context: Context, dao: KnowledgeArticleDao) {
        try {
            val fb = FirebaseService(context)
            if (!fb.isFirebaseAvailable.value) return
            val approved = fb.fetchApprovedContributions() ?: return
            val local = dao.getAllArticles("en").first().filter { it.sourceName == COMMUNITY_SOURCE }
            for (c in approved) {
                val words = c.body.split(Regex("\\s+")).size
                val existing = local.firstOrNull { it.textReference == "contribution:${c.id}" }
                val article = KnowledgeArticle(
                    id = existing?.id ?: 0, category = c.category, title = c.title,
                    summary = c.body.substringBefore("\n\n").take(400), content = c.body, language = "en",
                    difficulty = if (words > 1500) "Intermediate" else "Beginner", readTimeMinutes = maxOf(1, words / 200),
                    format = "Article", sourceName = COMMUNITY_SOURCE, sourceType = "Community",
                    textReference = "contribution:${c.id}", verificationStatus = "EDITOR_REVIEW_REQUIRED",
                    translator = c.authorName, edition = c.sourceUrl, contentType = "ARTICLE"
                )
                if (existing == null) dao.insertArticle(article) else dao.updateArticle(article)
            }
            // Only prune when the list came back non-empty or we are sure it is genuinely empty (no error path returns here)
            val keep = approved.map { "contribution:${it.id}" }.toSet()
            local.filter { it.textReference !in keep }.forEach { dao.deleteArticleById(it.id) }
        } catch (e: Exception) {
            Log.w(TAG, "contributions: ${e.message}")
        }
    }

    private data class Page(val title: String, val extract: String, val url: String, val hiTitle: String?)

    private fun get(url: okhttp3.HttpUrl): JSONObject? {
        val req = Request.Builder().url(url).header("User-Agent", "SanatanSadhak/1.0 (support@chaloone.com)").build()
        http.newCall(req).execute().use { r ->
            if (!r.isSuccessful) return null
            return JSONObject(r.body?.string() ?: return null)
        }
    }

    /** Pages (not sub-categories) of an English Wikipedia category; lists, stubs and meta pages are skipped later. */
    private fun categoryMembers(category: String, limit: Int): List<String> {
        val url = "https://en.wikipedia.org/w/api.php".toHttpUrl().newBuilder()
            .addQueryParameter("action", "query").addQueryParameter("format", "json").addQueryParameter("formatversion", "2")
            .addQueryParameter("list", "categorymembers").addQueryParameter("cmtitle", "Category:$category")
            .addQueryParameter("cmtype", "page").addQueryParameter("cmlimit", limit.toString()).build()
        val arr = get(url)?.optJSONObject("query")?.optJSONArray("categorymembers") ?: return emptyList()
        return (0 until arr.length()).map { arr.getJSONObject(it).getString("title") }
            .filter { !it.startsWith("List of") && !it.startsWith("Category:") && !it.contains("(disambiguation)") }
    }

    /** Latest revision id of each requested title (following redirects); missing pages are absent from the map. */
    private fun revisions(lang: String, titles: List<String>): Map<String, Long> {
        val url = "https://$lang.wikipedia.org/w/api.php".toHttpUrl().newBuilder()
            .addQueryParameter("action", "query").addQueryParameter("format", "json").addQueryParameter("formatversion", "2")
            .addQueryParameter("redirects", "1").addQueryParameter("prop", "info")
            .addQueryParameter("titles", titles.joinToString("|")).build()
        val q = get(url)?.optJSONObject("query") ?: return emptyMap()
        val toFinal = HashMap<String, String>()
        q.optJSONArray("normalized")?.let { a -> for (i in 0 until a.length()) a.getJSONObject(i).let { toFinal[it.getString("from")] = it.getString("to") } }
        val redirected = HashMap<String, String>()
        q.optJSONArray("redirects")?.let { a -> for (i in 0 until a.length()) a.getJSONObject(i).let { redirected[it.getString("from")] = it.getString("to") } }
        val revByTitle = HashMap<String, Long>()
        q.optJSONArray("pages")?.let { a ->
            for (i in 0 until a.length()) {
                val p = a.getJSONObject(i)
                if (!p.optBoolean("missing")) revByTitle[p.getString("title")] = p.optLong("lastrevid")
            }
        }
        val out = HashMap<String, Long>()
        for (t in titles) {
            val n = toFinal[t] ?: t
            val r = redirected[n] ?: n
            revByTitle[r]?.let { out[t] = it }
        }
        return out
    }

    private fun fetch(lang: String, title: String): Page? {
        val url = "https://$lang.wikipedia.org/w/api.php".toHttpUrl().newBuilder()
            .addQueryParameter("action", "query").addQueryParameter("format", "json").addQueryParameter("formatversion", "2")
            .addQueryParameter("redirects", "1").addQueryParameter("titles", title)
            .addQueryParameter("prop", "extracts|langlinks|info").addQueryParameter("explaintext", "1")
            .addQueryParameter("exsectionformat", "wiki").addQueryParameter("inprop", "url")
            .addQueryParameter("lllang", "hi").build()
        val page = get(url)?.getJSONObject("query")?.getJSONArray("pages")?.getJSONObject(0) ?: return null
        if (page.optBoolean("missing")) return null
        val extract = page.optString("extract").trim()
        if (extract.length < 600) return null // skip stubs
        val hi = page.optJSONArray("langlinks")?.optJSONObject(0)?.optString("title")?.ifBlank { null }
        return Page(page.getString("title"), extract, page.optString("fullurl"), hi)
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
