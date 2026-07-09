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

    private const val MODEL_NAME = "gemini-3.5-flash"

    suspend fun generateResponse(chatHistory: List<ChatMessage>, prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.e(TAG, "API Key is missing or default placeholder")
            return@withContext "Pranam! I am 'Sadhak Mitra', your spiritual guide. To enable live AI-guided conversations with me, please configure your actual GEMINI_API_KEY in the Secrets panel of Google AI Studio. For now, let me share this sacred wisdom: 'Set your heart on your work, but never on its reward. Work selflessly for the divine, and you shall find true, everlasting happiness (Nishkama Karma).'"
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent?key=$apiKey"

        try {
            val root = JSONObject()
            val contentsArray = JSONArray()

            // System instruction
            val systemInstruction = JSONObject()
            val sysParts = JSONArray()
            val sysPart = JSONObject().put("text", "You are 'Sadhak Mitra', a wise, peaceful, calm, and deeply knowledgeable spiritual guide dedicated to Sanatan Dharma, Bhakti, Karma, and inner happiness. Answer in a warm, respectful, non-commercial, temple-like tone. Speak in simple language, bilingual (mix of Hindi/Sanskrit terms and English) based on what the user asks. Address doubts gently, quote Gita shlokas with translation when appropriate, and encourage daily gratitude and positive Karma.")
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

            val jsonStr = root.toString()
            Log.d(TAG, "Request payload length: ${jsonStr.length}")

            val requestBody = jsonStr.toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string()
                if (!response.isSuccessful || bodyStr == null) {
                    Log.e(TAG, "Request failed code: ${response.code}, body: $bodyStr")
                    return@withContext "Apologies, dear Sadhak. I was unable to connect to the divine knowledge network at this moment. Let us chant 'Om Namah Shivaya' together. (Error: ${response.code})"
                }

                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val contentObj = firstCandidate.optJSONObject("content")
                    val partsArray = contentObj?.optJSONArray("parts")
                    if (partsArray != null && partsArray.length() > 0) {
                        return@withContext partsArray.getJSONObject(0).optString("text", "Empty response text")
                    }
                }
                "I am silent, contemplating your words. Please try asking in a different way, dear Sadhak."
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception during Gemini generation: ${e.message}", e)
            "Pranam, dear Sadhak. I faced a connection issue while loading the spiritual response. Please ensure you are connected to the internet. Chanting 'Har Har Mahadev' for your well-being!"
        }
    }

}
