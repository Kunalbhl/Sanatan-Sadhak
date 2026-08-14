import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

replacement = '''// Fetch Real Panchang from AstrologyService
            panchangError.value = null
            
            val cal = java.util.Calendar.getInstance()
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val m = cal.get(java.util.Calendar.MONTH) // 0-Indexed
            val y = cal.get(java.util.Calendar.YEAR)

            try {
                val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                // Use 6:00 AM (Sunrise approximation) to get the day's standard Tithi, just like Calendar
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, 6, 0, 0)
                
                if (realPanchang.data != null) {
                    todayDetailedPanchang.value = realPanchang.data
                    
                    val entity = com.example.data.PanchangEntity(
                        date = dateStr,
                        tithi = realPanchang.data.tithi,
                        paksha = realPanchang.data.paksha,
                        nakshatra = realPanchang.data.nakshatra,
                        yoga = realPanchang.data.yoga,
                        karana = realPanchang.data.karana,
                        sunrise = realPanchang.data.sunrise,
                        sunset = realPanchang.data.sunset,
                        rahuKaal = realPanchang.data.rahukaal,
                        abhijeet = realPanchang.data.abhijeet,
                        festival = realPanchang.data.festival,
                        details = realPanchang.data.details
                    )
                    repository.savePanchang(entity)
                    updatePanchangWithEntity(entity)
                    
                    panchangSource.value = "Local/API"
                    panchangLastUpdated.value = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                } else {
                    panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                }
            } catch (e: Exception) {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                panchangTechnicalError.value = "Exception: ${e.message}"
            }'''

text = re.sub(r'// Fetch Real Panchang from Gemini.*?\}\s*catch\s*\(e:\s*Exception\)\s*\{\s*panchangError\.value =.*?panchangTechnicalError\.value =.*?\}\s*', replacement + '\n', text, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
