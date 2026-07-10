import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

# Replace getOfflinePanchang entirely with empty string
text = re.sub(r"    fun getOfflinePanchang\(.*?\).*?return DetailedPanchang\(.*?\)\n    }", "", text, flags=re.DOTALL)

# Now rewrite loadDailyPanchangAndThought
target_load = r"    fun loadDailyPanchangAndThought\(\) \{.*?lastPanchangFetchDate = dateStr\n                return@launch\n            \}\n\n            // No cache / cache expired -> Fetch Real Panchang from Free Astrology API\n            val cal = java\.util\.Calendar\.getInstance\(\)\n            val d = cal\.get\(java\.util\.Calendar\.DAY_OF_MONTH\)\n            val m = cal\.get\(java\.util\.Calendar\.MONTH\)\n            val y = cal\.get\(java\.util\.Calendar\.YEAR\)\n\n            val realPanchang = AstrologyService\.fetchRealPanchang\(d, m \+ 1, y\)\n            if \(realPanchang != null\) \{.*?\n            \}\n\n            // Fallback to Gemini if real API failed\n            val apiKey = com\.example\.BuildConfig\.GEMINI_API_KEY\n            if \(apiKey\.isNotEmpty\(\) && apiKey != \"MY_GEMINI_API_KEY\"\) \{.*?\n            \}\n\n            // Final Fallback: Offline\n            val offlineEng = getOfflinePanchang\(d, m, y, true\)\n            val offlineHi = getOfflinePanchang\(d, m, y, false\)\n.*?\n            lastPanchangFetchDate = dateStr\n        \}\n    \}"

# It's better to just use replace with exactly matching or finding the start and end indices of `fun loadDailyPanchangAndThought`

start_idx = text.find("    fun loadDailyPanchangAndThought()")
end_idx = text.find("    fun updateCustomThought(", start_idx)

replacement_load = """    fun loadDailyPanchangAndThought() {
        viewModelScope.launch {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val dateStr = sdf.format(java.util.Date())
            
            val prefs = getApplication<android.app.Application>()
                .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)

            val customThoughtEn = prefs.getString("custom_thought_en", "") ?: ""
            val customThoughtHi = prefs.getString("custom_thought_hi", "") ?: ""

            if (customThoughtEn.isNotEmpty() && customThoughtHi.isNotEmpty()) {
                _thoughtEn.value = customThoughtEn
                _thoughtHi.value = customThoughtHi
            } else {
                val dailyShloka = com.example.data.GeminiService.fetchDailyShloka()
                if (dailyShloka != null) {
                    _thoughtEn.value = dailyShloka
                    _thoughtHi.value = dailyShloka
                } else {
                    val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt()
                    val thoughtsEnList = listOf(
                        "Perform action without attachment to outcomes.",
                        "You have the right to work, but never to the fruit of work.",
                        "When meditation is mastered, the mind is unwavering like the flame of a lamp in a windless place.",
                        "There is nothing lost or wasted in this life.",
                        "A person is shaped by their belief. As they believe, so they become."
                    )
                    val thoughtsHiList = listOf(
                        "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन। (परिणामों की आसक्ति के बिना कर्म करो।)",
                        "तुम्हारा अधिकार कर्म पर है, उसके फलों पर नहीं।",
                        "जिस प्रकार वायु रहित स्थान में दीपक की लौ नहीं हिलती, उसी प्रकार ध्यान में योगी का चित्त स्थिर रहता है।",
                        "इस जीवन में कुछ भी खोता या व्यर्थ नहीं होता।",
                        "मनुष्य अपने विश्वास से निर्मित होता है। जैसा वह विश्वास करता है, वैसा ही वह बन जाता है।"
                    )
                    _thoughtEn.value = thoughtsEnList[dayIndex % thoughtsEnList.size]
                    _thoughtHi.value = thoughtsHiList[dayIndex % thoughtsHiList.size]
                }
            }

            // Fetch Real Panchang from Free Astrology API
            panchangError.value = null
            
            val cal = java.util.Calendar.getInstance()
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val m = cal.get(java.util.Calendar.MONTH) // 0-Indexed
            val y = cal.get(java.util.Calendar.YEAR)

            val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y)
            if (realPanchang != null) {
                _tithiEn.value = realPanchang.tithi
                _tithiHi.value = realPanchang.tithi
                _nakshatraEn.value = realPanchang.nakshatra
                _nakshatraHi.value = realPanchang.nakshatra
                _yogaEn.value = realPanchang.yoga
                _yogaHi.value = realPanchang.yoga
                _karanaEn.value = realPanchang.karana
                _karanaHi.value = realPanchang.karana
                _rashiEn.value = realPanchang.rashi
                _rashiHi.value = realPanchang.rashi
                _sunrise.value = realPanchang.sunrise
                _sunset.value = realPanchang.sunset
                _rahukaal.value = realPanchang.rahukaal
                _abhijeet.value = realPanchang.abhijeet
                _festivalEn.value = realPanchang.festival
                _festivalHi.value = realPanchang.festival
                _detailsEn.value = realPanchang.details
                _detailsHi.value = realPanchang.details
                
                panchangSource.value = "API"
                todayDetailedPanchang.value = realPanchang
                
                val timeFormat = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
                panchangLastUpdated.value = dateStr + " " + timeFormat.format(java.util.Date())
            } else {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
            }
        }
    }

"""

if start_idx != -1 and end_idx != -1:
    text = text[:start_idx] + replacement_load + text[end_idx:]

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)

