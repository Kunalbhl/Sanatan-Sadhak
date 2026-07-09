import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = """            // Fetch Panchang using Gemini Google Search
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

good = """            // Fetch Panchang using Gemini Google Search
            try {
                val responseJson = com.example.data.GeminiService.fetchDailyPanchangAndThought(dateStr)
                if (responseJson != null) {
                    _tithiEn.value = responseJson.optString("tithi_en", "Pratipada")
                    _tithiHi.value = responseJson.optString("tithi_hi", "प्रतिपदा")
                    _nakshatraEn.value = responseJson.optString("nakshatra_en", "Ashwini")
                    _nakshatraHi.value = responseJson.optString("nakshatra_hi", "अश्विनी")
                    _yogaEn.value = responseJson.optString("yoga_en", "Vishkambha")
                    _yogaHi.value = responseJson.optString("yoga_hi", "विष्कम्भ")
                    _festivalEn.value = responseJson.optString("festival_en", "No Festival")
                    _festivalHi.value = responseJson.optString("festival_hi", "कोई त्योहार नहीं")
                    
                    val thoughtEn = responseJson.optString("thought_en")
                    val thoughtHi = responseJson.optString("thought_hi")
                    if (thoughtEn.isNotBlank() && thoughtHi.isNotBlank()) {
                        _currentThought.value = Pair(thoughtEn, thoughtHi)
                    }
                    
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
    print("Replaced Panchang 3")
else:
    print("Not found")

