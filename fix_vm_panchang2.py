import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = """            // Fetch Panchang
            val apiKey = com.example.BuildConfig.PANCHANG_API_KEY
            if (apiKey.isNotBlank() && apiKey != "YOUR_VEDIC_ASTRO_API_KEY") {
                try {
                    val response = com.example.data.RetrofitClient.panchangApi.getPanchang(apiKey, dateStr)
                    val data = response.response
                    if (data != null) {
                        _tithiEn.value = data.tithi?.details?.list_name ?: "Pratipada"
                        _tithiHi.value = data.tithi?.details?.list_name ?: "प्रतिपदा"
                        _nakshatraEn.value = data.nakshatra?.details?.list_name ?: "Ashwini"
                        _nakshatraHi.value = data.nakshatra?.details?.list_name ?: "अश्विनी"
                        _yogaEn.value = data.yog?.details?.list_name ?: "Vishkambha"
                        _yogaHi.value = data.yog?.details?.list_name ?: "विष्कम्भ"
                        _festivalEn.value = data.festival ?: "No Festival"
                        _festivalHi.value = data.festival ?: "कोई त्योहार नहीं"
                        lastPanchangFetchDate = dateStr
                        return@launch
                    }
                } catch (e: Exception) {
                    android.util.Log.e("SadhakViewModel", "Error fetching panchang", e)
                }
            }"""

good = """            // Fetch Panchang using Gemini Google Search
            try {
                val searchResult = com.example.data.GeminiService.generateSpiritualResponse(
                    "What is today's Hindu Panchang? Just reply with Tithi, Nakshatra, and Yoga briefly.", 
                    null,
                    useSearch = true
                )
                // We fallback to deterministic if it fails, but for now we set the raw output to Tithi for display
                // The prompt requested a 3 line format.
                if (searchResult.isNotEmpty() && !searchResult.contains("Error")) {
                    _tithiEn.value = "Live Panchang Update"
                    _tithiHi.value = "लाइव पंचांग अपडेट"
                    _nakshatraEn.value = searchResult
                    _nakshatraHi.value = searchResult
                    _yogaEn.value = ""
                    _yogaHi.value = ""
                    lastPanchangFetchDate = dateStr
                    return@launch
                }
            } catch(e: Exception) {
                android.util.Log.e("SadhakViewModel", "Error fetching panchang from Gemini", e)
            }"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(content)
    print("Replaced Panchang 2")
else:
    print("Not found")

