import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

replacement = '''// Fetch Real Panchang from Gemini
            panchangError.value = null
            
            val cal = java.util.Calendar.getInstance()
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val m = cal.get(java.util.Calendar.MONTH) // 0-Indexed
            val y = cal.get(java.util.Calendar.YEAR)

            try {
                val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                val jsonStr = com.example.data.GeminiService.fetchDetailedPanchang(d, m, y)
                if (jsonStr.isNotEmpty()) {
                    val jsonObj = org.json.JSONObject(jsonStr)
                    
                    val p = com.example.data.DetailedPanchang(
                        tithi = jsonObj.optString("tithi_en"),
                        nakshatra = jsonObj.optString("nakshatra_en"),
                        yoga = jsonObj.optString("yoga_en"),
                        karana = jsonObj.optString("karana_en"),
                        rashi = jsonObj.optString("rashi_en"),
                        sunrise = jsonObj.optString("sunrise"),
                        sunset = jsonObj.optString("sunset"),
                        rahukaal = jsonObj.optString("rahukaal"),
                        gulikaKaal = "—",
                        yamaganda = "—",
                        abhijeet = jsonObj.optString("abhijeet_muhurat"),
                        festival = jsonObj.optString("festival_en"),
                        details = jsonObj.optString("details_en")
                    )
                    
                    todayDetailedPanchang.value = p
                    
                    val entity = com.example.data.PanchangEntity(
                        date = dateStr,
                        tithi = p.tithi,
                        paksha = "",
                        nakshatra = p.nakshatra,
                        yoga = p.yoga,
                        karana = p.karana,
                        sunrise = p.sunrise,
                        sunset = p.sunset,
                        rahuKaal = p.rahukaal,
                        abhijeet = p.abhijeet,
                        festival = p.festival,
                        details = p.details
                    )
                    repository.savePanchang(entity)
                    
                    _tithiEn.value = jsonObj.optString("tithi_en")
                    _tithiHi.value = jsonObj.optString("tithi_hi")
                    _nakshatraEn.value = jsonObj.optString("nakshatra_en")
                    _nakshatraHi.value = jsonObj.optString("nakshatra_hi")
                    _yogaEn.value = jsonObj.optString("yoga_en")
                    _yogaHi.value = jsonObj.optString("yoga_hi")
                    _karanaEn.value = jsonObj.optString("karana_en")
                    _karanaHi.value = jsonObj.optString("karana_hi")
                    _sunrise.value = jsonObj.optString("sunrise")
                    _sunset.value = jsonObj.optString("sunset")
                    _rahukaal.value = jsonObj.optString("rahukaal")
                    _abhijeet.value = jsonObj.optString("abhijeet_muhurat")
                    _festivalEn.value = jsonObj.optString("festival_en")
                    _festivalHi.value = jsonObj.optString("festival_hi")
                    _detailsEn.value = jsonObj.optString("details_en")
                    _detailsHi.value = jsonObj.optString("details_hi")
                    
                    panchangSource.value = "Gemini"
                    panchangLastUpdated.value = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                } else {
                    panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                }
            } catch (e: Exception) {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                panchangTechnicalError.value = "Exception: ${e.message}"
            }'''

text = re.sub(r'// Fetch Real Panchang from Free Astrology API.*?\}\s*catch\s*\(e:\s*Exception\)\s*\{\s*panchangError\.value =.*?panchangTechnicalError\.value =.*?\}\s*\}', replacement, text, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
