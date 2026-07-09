import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

# Replace API part
api_bad = """                    if (data != null) {
                        _currentTithi.value = data.tithi?.details?.list_name ?: "Pratipada"
                        _currentNakshatra.value = data.nakshatra?.details?.list_name ?: "Ashwini"
                        _currentYoga.value = data.yog?.details?.list_name ?: "Vishkambha"
                        _currentFestival.value = data.festival ?: "No Festival"
                        lastPanchangFetchDate = dateStr
                        return@launch
                    }"""

api_good = """                    if (data != null) {
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
                    }"""
content = content.replace(api_bad, api_good)

# Replace Offline part
offline_bad = """            val isEng = _isEnglish.value
            _currentTithi.value = "${if (isEng) tithisEn[tithiIndex] else tithisHi[tithiIndex]} (${if (isEng) pakshasEn[pakshaIndex] else pakshasHi[pakshaIndex]})"
            _currentNakshatra.value = if (isEng) nakshatrasEn[nakshatraIndex] else nakshatrasHi[nakshatraIndex]
            _currentYoga.value = if (isEng) "Shiva Yoga" else "शिव योग"
            
            if (tithiIndex == 10) {
                _currentFestival.value = if (isEng) "Ekadashi Vrat" else "एकादशी व्रत"
            } else if (tithiIndex == 14 && pakshaIndex == 0) {
                _currentFestival.value = if (isEng) "Purnima Puja" else "पूर्णिमा पूजा"
            } else {
                _currentFestival.value = ""
            }"""

offline_good = """            _tithiEn.value = "${tithisEn[tithiIndex]} (${pakshasEn[pakshaIndex]})"
            _tithiHi.value = "${tithisHi[tithiIndex]} (${pakshasHi[pakshaIndex]})"
            _nakshatraEn.value = nakshatrasEn[nakshatraIndex]
            _nakshatraHi.value = nakshatrasHi[nakshatraIndex]
            _yogaEn.value = "Shiva Yoga"
            _yogaHi.value = "शिव योग"
            
            if (tithiIndex == 10) {
                _festivalEn.value = "Ekadashi Vrat"
                _festivalHi.value = "एकादशी व्रत"
            } else if (tithiIndex == 14 && pakshaIndex == 0) {
                _festivalEn.value = "Purnima Puja"
                _festivalHi.value = "पूर्णिमा पूजा"
            } else {
                _festivalEn.value = ""
                _festivalHi.value = ""
            }"""
content = content.replace(offline_bad, offline_good)

# Actually, I also need to make sure _nakshatraHi, _yogaHi, etc are defined.
# Let's check if they exist, if not we add them. Wait, they exist because they were used in the combine earlier.

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
