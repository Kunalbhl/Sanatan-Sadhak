import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = """            // Fetch Panchang
            try {
                // We use Vedic Astro API
                val apiKey = BuildConfig.VEDIC_ASTRO_API_KEY
                if (apiKey.isNotBlank()) {
                    val response = com.example.data.RetrofitClient.panchangApi.getPanchang(apiKey, dateStr)
                    if (response.isSuccessful && response.body() != null) {
                        val panchangData = response.body()!!.response
                        _currentTithi.value = Pair(panchangData.tithi.name, "")
                        _currentNakshatra.value = Pair(panchangData.nakshatra.name, "")
                        _currentYoga.value = Pair(panchangData.yoga.name, "")
                        _currentFestival.value = Pair("", "")
                        lastPanchangFetchDate = dateStr
                        isPanchangLoaded = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }"""

good = """            // Fetch Panchang using Gemini Search
            try {
                if (!isPanchangLoaded) {
                    val pResponse = GeminiService.generateSpiritualResponse(
                        "What is the Hindu Panchang for today? Provide only the Tithi, Nakshatra, and Yoga in a short 3-line format.", 
                        null, 
                        useSearch = true
                    )
                    _currentTithi.value = Pair(pResponse.take(50), "") // Just display the raw gemini output in tithi temporarily or parse it
                    _currentNakshatra.value = Pair("", "")
                    _currentYoga.value = Pair("", "")
                    lastPanchangFetchDate = dateStr
                    isPanchangLoaded = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(content)
    print("Replaced Panchang")
else:
    print("Not found")

