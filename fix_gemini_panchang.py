import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Replace getDetailedPanchangForDate
replacement = '''fun getDetailedPanchangForDate(day: Int, month: Int, year: Int, isEng: Boolean, callback: (DetailedPanchang?) -> Unit) {
        viewModelScope.launch {
            val jsonStr = com.example.data.GeminiService.fetchDetailedPanchang(day, month, year)
            if (jsonStr.isNotEmpty()) {
                try {
                    val jsonObj = org.json.JSONObject(jsonStr)
                    val tithi = if (isEng) jsonObj.optString("tithi_en") else jsonObj.optString("tithi_hi")
                    val nakshatra = if (isEng) jsonObj.optString("nakshatra_en") else jsonObj.optString("nakshatra_hi")
                    val yoga = if (isEng) jsonObj.optString("yoga_en") else jsonObj.optString("yoga_hi")
                    val karana = if (isEng) jsonObj.optString("karana_en") else jsonObj.optString("karana_hi")
                    val rashi = if (isEng) jsonObj.optString("rashi_en") else jsonObj.optString("rashi_hi")
                    val festival = if (isEng) jsonObj.optString("festival_en") else jsonObj.optString("festival_hi")
                    val details = if (isEng) jsonObj.optString("details_en") else jsonObj.optString("details_hi")
                    
                    val p = com.example.data.DetailedPanchang(
                        tithi = tithi,
                        nakshatra = nakshatra,
                        yoga = yoga,
                        karana = karana,
                        rashi = rashi,
                        sunrise = jsonObj.optString("sunrise"),
                        sunset = jsonObj.optString("sunset"),
                        rahukaal = jsonObj.optString("rahukaal"),
                        gulikaKaal = "—",
                        yamaganda = "—",
                        abhijeet = jsonObj.optString("abhijeet_muhurat"),
                        festival = festival,
                        details = details
                    )
                    callback(p)
                } catch (e: Exception) {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    }'''

text = re.sub(r'fun getDetailedPanchangForDate.*?callback\(realPanchang\.data\)\s*\}\s*\}', replacement, text, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
