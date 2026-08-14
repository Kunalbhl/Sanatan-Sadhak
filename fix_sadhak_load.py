import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

bad_chunk = """            if (customThoughtEn.isNotEmpty() && customThoughtHi.isNotEmpty()) {
                _thoughtEn.value = customThoughtEn
                _thoughtHi.value = customThoughtHi
                                    repository.savePanchang(entity)
                    updatePanchangWithEntity(entity)"""

good_chunk = """            if (customThoughtEn.isNotEmpty() && customThoughtHi.isNotEmpty()) {
                _thoughtEn.value = customThoughtEn
                _thoughtHi.value = customThoughtHi
            } else {
                _thoughtEn.value = zipped.first().first
                _thoughtHi.value = zipped.first().second
            }
            _multipleInspirations.value = zipped.take(3)

            // Fetch Real Panchang from AstrologyService
            panchangError.value = null
            
            val cal = java.util.Calendar.getInstance()
            val y = cal.get(java.util.Calendar.YEAR)
            val m = cal.get(java.util.Calendar.MONTH)
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            
            try {
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, 6, 0, 0)
                if (realPanchang.data != null) {
                    todayDetailedPanchang.value = realPanchang.data
                    val entity = com.example.data.PanchangEntity(
                        dateStr = "$y-$m-$d",
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
                    updatePanchangWithEntity(entity)"""

content = content.replace(bad_chunk, good_chunk)

# Fix audioUrl
content = content.replace('"Om Namah Shivaya",\n            "I bow to Lord Shiva. A powerful mantra for inner peace and destruction of negative energies.",\n            "ॐ नमः शिवाय"', '"Om Namah Shivaya",\n            "I bow to Lord Shiva. A powerful mantra for inner peace and destruction of negative energies.",\n            "ॐ नमः शिवाय", ""')

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
