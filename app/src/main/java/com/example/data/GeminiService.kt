package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {
    private const val TAG = "GeminiService"
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /** Tried in order; the first one this API key can use wins and is remembered. */
    private val MODELS = listOf("gemini-flash-latest", "gemini-2.5-flash", "gemini-2.0-flash")
    @Volatile private var workingModel: String? = null
    private const val MODEL_NAME = "gemini-flash-latest"

    /** Set once from MainActivity; lets requests identify the app when the API key is restricted to it. */
    @Volatile var appContext: android.content.Context? = null

    @Volatile var lastError: String = ""

    private fun signatureSha1(): String? = try {
        val ctx = appContext ?: return null
        val pm = ctx.packageManager
        @Suppress("DEPRECATION")
        val sig = if (android.os.Build.VERSION.SDK_INT >= 28) {
            pm.getPackageInfo(ctx.packageName, android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES)
                .signingInfo?.apkContentsSigners?.firstOrNull()
        } else {
            pm.getPackageInfo(ctx.packageName, android.content.pm.PackageManager.GET_SIGNATURES).signatures?.firstOrNull()
        }
        sig?.let {
            java.security.MessageDigest.getInstance("SHA-1").digest(it.toByteArray())
                .joinToString("") { b -> "%02X".format(b) }
        }
    } catch (_: Exception) { null }

    /** POSTs [root] to the first Gemini model that accepts it. Returns the response body, or null (reason in lastError). */
    private fun postToGemini(root: JSONObject, apiKey: String): String? {
        val models = workingModel?.let { listOf(it) + MODELS.filter { m -> m != it } } ?: MODELS
        val body = root.toString()
        for (model in models) {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent"
            val builder = Request.Builder().url(url)
                .header("x-goog-api-key", apiKey)
                .post(body.toRequestBody("application/json".toMediaType()))
            appContext?.let { builder.header("X-Android-Package", it.packageName) }
            signatureSha1()?.let { builder.header("X-Android-Cert", it) }
            try {
                client.newCall(builder.build()).execute().use { resp ->
                    val text = resp.body?.string()
                    if (resp.isSuccessful && text != null) {
                        workingModel = model
                        lastError = ""
                        return text
                    }
                    lastError = "HTTP ${resp.code} on $model: ${text?.take(200)}"
                    Log.e(TAG, lastError)
                    // 400/401/403 mean the key itself is refused; another model will not help
                    if (resp.code == 401 || resp.code == 403) return null
                }
            } catch (e: Exception) {
                lastError = "${e.javaClass.simpleName}: ${e.message}"
                Log.e(TAG, lastError)
                return null
            }
        }
        return null
    }

    suspend fun generateResponse(chatHistory: List<ChatMessage>, prompt: String, isEnglish: Boolean): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.e(TAG, "API Key is missing or default placeholder")
            if (isEnglish) {
                return@withContext "Pranam! I am 'Sadhak Mitra', your spiritual guide. Live AI guidance is not available right now. Until then, let me share this sacred wisdom: 'Set your heart on your work, but never on its reward. Work selflessly for the divine, and you shall find true, everlasting happiness (Nishkama Karma).'"
            } else {
                return@withContext "प्रणाम! मैं 'साधक मित्र' हूं, आपका आध्यात्मिक मार्गदर्शक। लाइव AI मार्गदर्शन अभी उपलब्ध नहीं है। तब तक, मैं आपके साथ यह पवित्र ज्ञान साझा करता हूँ: 'अपने कर्म पर ध्यान लगाओ, उसके फल पर नहीं। ईश्वर के लिए निस्वार्थ भाव से कार्य करो, और तुम्हें सच्ची, शाश्वत प्रसन्नता प्राप्त होगी (निष्काम कर्म)।'"
            }
        }

        try {
            val root = JSONObject()
            val contentsArray = JSONArray()

            // System instruction
            val systemInstruction = JSONObject()
            val sysParts = JSONArray()
            val preferredLanguage = if (isEnglish) "English" else "Hindi (Primary)"
            val sysPart = JSONObject().put("text", "You are 'Sadhak Mitra', a wise, peaceful, calm, and deeply knowledgeable spiritual guide dedicated to Sanatan Dharma, Bhakti, Karma, and inner happiness. Answer in a warm, respectful, non-commercial, temple-like tone. Speak in simple language, primarily in $preferredLanguage. Address doubts gently, quote Gita shlokas with translation when appropriate, and encourage daily gratitude and positive Karma. CRITICAL: Never represent generated content as scripture, verified quotation, or historical fact without a verified source. Do not fabricate Sanskrit quotations. If you provide a scriptural answer, structure it clearly by separating: SOURCE TEXT, TRADITIONAL INTERPRETATION, and AI GENERATED EXPLANATION. If uncertain, clearly state that source verification is required.")
            sysParts.put(sysPart)
            systemInstruction.put("parts", sysParts)
            root.put("systemInstruction", systemInstruction)

            // Add Chat History (limited to last 10 turns to avoid exceeding context window limits)
            val historyToUse = if (chatHistory.size > 10) chatHistory.takeLast(10) else chatHistory
            historyToUse.forEach { msg ->
                val turn = JSONObject()
                val role = if (msg.sender == "user") "user" else "model"
                turn.put("role", role)
                
                val parts = JSONArray()
                val partObj = JSONObject().put("text", msg.content)
                parts.put(partObj)
                turn.put("parts", parts)
                
                contentsArray.put(turn)
            }

            // Add the new user prompt
            val newUserTurn = JSONObject()
            newUserTurn.put("role", "user")
            val parts = JSONArray()
            val partObj = JSONObject().put("text", prompt)
            parts.put(partObj)
            newUserTurn.put("parts", parts)
            contentsArray.put(newUserTurn)

            root.put("contents", contentsArray)

            val bodyStr = postToGemini(root, apiKey)
            if (bodyStr == null) {
                val code = lastError.substringAfter("HTTP ", "").take(3)
                val hint = when {
                    code == "403" || code == "401" -> if (isEnglish) " (the AI key was refused — the app owner needs to check it)" else " (एआई कुंजी अस्वीकृत — ऐप स्वामी को जाँचनी होगी)"
                    code == "429" -> if (isEnglish) " (too many requests right now, please try again in a minute)" else " (अभी बहुत अनुरोध हैं, एक मिनट बाद पुनः प्रयास करें)"
                    lastError.isNotBlank() && !lastError.startsWith("HTTP") -> if (isEnglish) " (please check your internet)" else " (कृपया इंटरनेट जाँचें)"
                    else -> ""
                }
                return@withContext if (isEnglish) "Apologies, dear Sadhak. AI Mitra could not answer just now$hint. Please try again shortly." else "क्षमा करें, प्रिय साधक। एआई मित्र अभी उत्तर नहीं दे सका$hint। कृपया थोड़ी देर में पुनः प्रयास करें।"
            }

            val responseJson = JSONObject(bodyStr)
            val candidates = responseJson.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val contentObj = firstCandidate.optJSONObject("content")
                val partsArray = contentObj?.optJSONArray("parts")
                if (partsArray != null && partsArray.length() > 0) {
                    val sb = StringBuilder()
                    for (i in 0 until partsArray.length()) sb.append(partsArray.getJSONObject(i).optString("text", ""))
                    if (sb.isNotBlank()) return@withContext sb.toString()
                }
            }
            "I am silent, contemplating your words. Please try asking in a different way, dear Sadhak."
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Gemini generation: ${e.message}", e)
            "Pranam, dear Sadhak. I faced a connection issue while loading the spiritual response. Please ensure you are connected to the internet. Chanting 'Har Har Mahadev' for your well-being!"
        }
    }

    suspend fun fetchDetailedPanchang(day: Int, month: Int, year: Int): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext ""
        }
        val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent?key=$apiKey"
        val dateStr = "$day/${month + 1}/$year"
        try {
            val root = JSONObject()
            val contentsArray = JSONArray()
            val userTurn = JSONObject().put("role", "user")
            val parts = JSONArray()
            
            val prompt = """
                Calculate the authentic Hindu Vedic Panchang for the date $dateStr.
                You MUST return only a valid JSON object. No markdown, no ```json formatting.
                Schema to return:
                {
                  "tithi_en": "Dwadashi",
                  "tithi_hi": "द्वादशी",
                  "nakshatra_en": "Rohini",
                  "nakshatra_hi": "रोहिणी",
                  "yoga_en": "Siddha",
                  "yoga_hi": "सिद्ध",
                  "karana_en": "Bava",
                  "karana_hi": "बव",
                  "sunrise": "05:47 AM",
                  "sunset": "07:15 PM",
                  "rahukaal": "01:30 PM to 03:00 PM",
                  "abhijeet_muhurat": "11:50 AM to 12:40 PM",
                  "rashi_en": "Taurus",
                  "rashi_hi": "वृषभ राशि",
                  "festival_en": "Pradosh Vrat (or any other, or blank)",
                  "festival_hi": "प्रदोष व्रत (या खाली)",
                  "details_en": "This day is highly auspicious for Shiva puja. Performing charity and spiritual readings will yield infinite peace.",
                  "details_hi": "यह दिन शिव पूजा के लिए अत्यंत शुभ है। दान और आध्यात्मिक पठन से असीम शांति प्राप्त होगी।"
                }
            """.trimIndent()
            
            parts.put(JSONObject().put("text", prompt))
            userTurn.put("parts", parts)
            contentsArray.put(userTurn)
            root.put("contents", contentsArray)

            val systemInstruction = JSONObject().put("parts", JSONArray().put(JSONObject().put("text", "You are a strict Vedic Astrologer who outputs ONLY valid JSON and nothing else.")))
            root.put("systemInstruction", systemInstruction)

            val requestBody = root.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(requestBody).build()

            client.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string()
                if (response.isSuccessful && bodyStr != null) {
                    val responseJson = JSONObject(bodyStr)
                    val candidates = responseJson.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val partText = candidates.getJSONObject(0)
                            .optJSONObject("content")
                            ?.optJSONArray("parts")
                            ?.getJSONObject(0)
                            ?.optString("text", "") ?: ""
                        
                        // Clean markdown formatting if any
                        var cleaned = partText.trim()
                        if (cleaned.startsWith("```json")) {
                            cleaned = cleaned.substring(7)
                        } else if (cleaned.startsWith("```")) {
                            cleaned = cleaned.substring(3)
                        }
                        if (cleaned.endsWith("```")) {
                            cleaned = cleaned.substring(0, cleaned.length - 3)
                        }
                        return@withContext cleaned.trim()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching detailed panchang: ${e.message}")
        }
        ""
    }

    suspend fun fetchDailyShloka(): String? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext null
        }
        val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent?key=$apiKey"
        try {
            val root = JSONObject()
            val contentsArray = JSONArray()
            val promptObj = JSONObject()
            val partsArray = JSONArray()
            partsArray.put(JSONObject().put("text", "Fetch a random authentic Bhagavad Gita or Upanishad verse. Format exactly like this without any extra text or markdown codeblocks:\n[Sanskrit Verse in Devanagari]\n\n[Meaning in Hindi]\n\n[Meaning in English]"))
            promptObj.put("parts", partsArray)
            promptObj.put("role", "user")
            contentsArray.put(promptObj)
            root.put("contents", contentsArray)

            val requestBody = root.toString().toRequestBody("application/json".toMediaType())
            val request = Request.Builder().url(url).post(requestBody).build()
            // use{} closes the response so connections are not leaked
            val responseBody = client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) null else response.body?.string()
            } ?: return@withContext null
            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates") ?: return@withContext null
            if (candidates.length() > 0) {
                val parts = candidates.getJSONObject(0).optJSONObject("content")?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val shlokaText = parts.getJSONObject(0).optString("text")
                    return@withContext shlokaText.replace("```","").trim()
                }
            }
            return@withContext null
        } catch (e: Exception) {
            return@withContext null
        }
    }

}
