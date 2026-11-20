package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreferences
import com.example.data.DetailedPanchang
import com.example.data.PanchangEntity
import com.example.data.PanchangLocalization
import com.example.data.PanchangRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class PanchangLocation(
    val name: String,
    val nameHi: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: Double
)

class PanchangViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PanchangRepository(application)
    private val isEnglish = AppPreferences.isEnglish

    companion object {
        val PRESET_LOCATIONS = listOf(
            PanchangLocation("New Delhi, India", "नई दिल्ली, भारत", 28.6139, 77.2090, 5.5),
            PanchangLocation("Varanasi, India", "वाराणसी (काशी), भारत", 25.3176, 82.9739, 5.5),
            PanchangLocation("Ujjain, India", "उज्जैन (अवंतिका), भारत", 23.1765, 75.7885, 5.5),
            PanchangLocation("Haridwar, India", "हरिद्वार, भारत", 29.9457, 78.1642, 5.5),
            PanchangLocation("Ayodhya, India", "अयोध्या, भारत", 26.7922, 82.1998, 5.5),
            PanchangLocation("Mathura, India", "मथुरा, भारत", 27.4924, 77.6737, 5.5),
            PanchangLocation("Tirupati, India", "तिरुपति, भारत", 13.6288, 79.4192, 5.5),
            PanchangLocation("Mumbai, India", "मुंबई, भारत", 19.0760, 72.8777, 5.5),
            PanchangLocation("Kathmandu, Nepal", "काठमांडू, नेपाल", 27.7172, 85.3240, 5.75),
            PanchangLocation("London, UK", "लंदन, यूके", 51.5074, -0.1278, 0.0),
            PanchangLocation("New York, USA", "न्यू यॉर्क, अमेरिका", 40.7128, -74.0060, -5.0)
        )
    }

    val currentLocation = MutableStateFlow(PRESET_LOCATIONS[0])
    val selectedCalendar = MutableStateFlow<Calendar>(Calendar.getInstance())

    private val _tithiEn = MutableStateFlow("Shukla Paksha Ekadashi")
    private val _tithiHi = MutableStateFlow("शुक्ल पक्ष एकादशी")
    private val _nakshatraEn = MutableStateFlow("Rohini Nakshatra")
    private val _nakshatraHi = MutableStateFlow("रोहिणी नक्षत्र")
    private val _yogaEn = MutableStateFlow("Siddha Yoga")
    private val _yogaHi = MutableStateFlow("सिद्ध योग")
    private val _festivalEn = MutableStateFlow("Devotional Puja Muhurat")
    private val _festivalHi = MutableStateFlow("भक्ति पूजा मुहूर्त")
    private val _thoughtEn = MutableStateFlow("Perform action without attachment to outcomes.")
    private val _thoughtHi = MutableStateFlow("फल की इच्छा के बिना कर्म करो।")
    private val _karanaEn = MutableStateFlow("Bava Karana")
    private val _karanaHi = MutableStateFlow("बव करण")
    private val _rashiEn = MutableStateFlow("Taurus (Vrishabha)")
    private val _rashiHi = MutableStateFlow("वृषभ राशि")
    private val _sunrise = MutableStateFlow("05:47 AM")
    private val _sunset = MutableStateFlow("07:15 PM")
    private val _rahukaal = MutableStateFlow("01:30 PM to 03:00 PM")
    private val _abhijeet = MutableStateFlow("11:50 AM to 12:40 PM")
    private val _detailsEn = MutableStateFlow("This day is auspicious for devotional activities.")
    private val _detailsHi = MutableStateFlow("यह दिन भक्ति गतिविधियों के लिए शुभ है।")

    val currentTithi: StateFlow<String> = combine(isEnglish, _tithiEn, _tithiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Shukla Paksha Ekadashi")
    val currentNakshatra: StateFlow<String> = combine(isEnglish, _nakshatraEn, _nakshatraHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Rohini Nakshatra")
    val currentYoga: StateFlow<String> = combine(isEnglish, _yogaEn, _yogaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Siddha Yoga")
    val currentFestival: StateFlow<String> = combine(isEnglish, _festivalEn, _festivalHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Devotional Puja Muhurat")
    val currentThought: StateFlow<String> = combine(isEnglish, _thoughtEn, _thoughtHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Perform action without attachment to outcomes.")
    val currentKarana: StateFlow<String> = combine(isEnglish, _karanaEn, _karanaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Bava Karana")
    val currentRashi: StateFlow<String> = combine(isEnglish, _rashiEn, _rashiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Taurus (Vrishabha)")
    val currentSunrise: StateFlow<String> = _sunrise
    val currentSunset: StateFlow<String> = _sunset
    val currentRahukaal: StateFlow<String> = _rahukaal
    val currentAbhijeet: StateFlow<String> = _abhijeet
    val currentDetails: StateFlow<String> = combine(isEnglish, _detailsEn, _detailsHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "This day is auspicious for devotional activities.")

    val panchangSource = MutableStateFlow("Local/API")
    val todayDetailedPanchang = MutableStateFlow<DetailedPanchang?>(null)
    val todayDetailedPanchangForDetail = MutableStateFlow<DetailedPanchang?>(null)
    val panchangError = MutableStateFlow<String?>(null)
    val panchangTechnicalError = MutableStateFlow<String?>(null)
    val panchangLastUpdated = MutableStateFlow<String>("")
    val isCachedData = MutableStateFlow<Boolean>(false)
    val isLoading = MutableStateFlow<Boolean>(false)

    /** Every change of tithi / nakshatra / yoga / karana / moon sign during the selected day, plus extra day-parts. */
    data class DayTimeline(
        val limbs: List<com.example.data.PanchangTimeline.Info>,
        val dayParts: List<Pair<String, String>>,
        val specialYogas: List<String>
    )
    val dayTimeline = MutableStateFlow<DayTimeline?>(null)

    private fun parseMinutes(text: String): Int? {
        val m = Regex("(\\d{1,2}):(\\d{2})\\s*(AM|PM)?", RegexOption.IGNORE_CASE).find(text) ?: return null
        var h = m.groupValues[1].toInt()
        val mm = m.groupValues[2].toInt()
        when (m.groupValues[3].uppercase()) {
            "PM" -> if (h < 12) h += 12
            "AM" -> if (h == 12) h = 0
        }
        return h * 60 + mm
    }

    init {
        loadSavedLocation()
        loadDailyPanchang()
        viewModelScope.launch {
            todayDetailedPanchang.collect { d ->
                if (d == null) { dayTimeline.value = null; return@collect }
                val cal = selectedCalendar.value
                val tz = currentLocation.value.timezone
                dayTimeline.value = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Default) {
                    try {
                        val y = cal.get(Calendar.YEAR); val m = cal.get(Calendar.MONTH) + 1; val day = cal.get(Calendar.DAY_OF_MONTH)
                        val rise = parseMinutes(d.sunrise); val set = parseMinutes(d.sunset)
                        DayTimeline(
                            limbs = com.example.data.PanchangTimeline.limbs(y, m, day, tz),
                            dayParts = if (rise != null && set != null) com.example.data.PanchangTimeline.dayParts(rise, set) else emptyList(),
                            specialYogas = com.example.data.PanchangTimeline.specialYogas(cal.get(Calendar.DAY_OF_WEEK), d.nakshatra)
                        )
                    } catch (e: Exception) { null }
                }
            }
        }
    }

    private fun loadSavedLocation() {
        val prefs = getApplication<Application>().getSharedPreferences("panchang_prefs", Context.MODE_PRIVATE)
        val name = prefs.getString("loc_name", null)
        if (name != null) {
            val lat = prefs.getFloat("loc_lat", 28.6139f).toDouble()
            val lng = prefs.getFloat("loc_lng", 77.2090f).toDouble()
            val tz = prefs.getFloat("loc_tz", 5.5f).toDouble()
            val nameHi = prefs.getString("loc_name_hi", name) ?: name
            currentLocation.value = PanchangLocation(name, nameHi, lat, lng, tz)
        }
    }

    fun setLocation(location: PanchangLocation) {
        currentLocation.value = location
        val prefs = getApplication<Application>().getSharedPreferences("panchang_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("loc_name", location.name)
            .putString("loc_name_hi", location.nameHi)
            .putFloat("loc_lat", location.latitude.toFloat())
            .putFloat("loc_lng", location.longitude.toFloat())
            .putFloat("loc_tz", location.timezone.toFloat())
            .apply()
        refreshPanchang()
    }

    fun goToPreviousDay() {
        val newCal = (selectedCalendar.value.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, -1)
        }
        selectedCalendar.value = newCal
        loadPanchangForCalendar(newCal)
    }

    fun goToNextDay() {
        val newCal = (selectedCalendar.value.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }
        selectedCalendar.value = newCal
        loadPanchangForCalendar(newCal)
    }

    fun goToToday() {
        val today = Calendar.getInstance()
        selectedCalendar.value = today
        loadPanchangForCalendar(today)
    }

    fun selectDate(year: Int, month: Int, day: Int) {
        val newCal = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
        }
        selectedCalendar.value = newCal
        loadPanchangForCalendar(newCal)
    }

    fun refreshPanchang() {
        loadPanchangForCalendar(selectedCalendar.value, forceRefresh = true)
    }

    fun loadDailyPanchang() {
        loadPanchangForCalendar(selectedCalendar.value)
    }

    private fun loadPanchangForCalendar(cal: Calendar, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val dateStr = String.format("%04d-%02d-%02d", y, m + 1, d)
            val loc = currentLocation.value
            val cacheKey = "${dateStr}_${loc.name}"

            isLoading.value = true
            panchangError.value = null

            if (!forceRefresh) {
                val cached = repository.getPanchangForDate(cacheKey) ?: repository.getPanchangForDate(dateStr)
                if (cached != null) {
                    val detailedPanchang = DetailedPanchang(
                        tithi = cached.tithi,
                        nakshatra = cached.nakshatra,
                        yoga = cached.yoga,
                        karana = cached.karana,
                        rashi = "—",
                        sunrise = cached.sunrise,
                        sunset = cached.sunset,
                        rahukaal = cached.rahuKaal,
                        gulikaKaal = "—",
                        yamaganda = "—",
                        abhijeet = cached.abhijeet,
                        festival = cached.festival,
                        details = cached.details,
                        paksha = cached.paksha,
                        locationName = loc.name
                    )
                    todayDetailedPanchang.value = detailedPanchang
                    todayDetailedPanchangForDetail.value = detailedPanchang
                    updatePanchangWithEntity(cached)
                    isCachedData.value = true
                    panchangSource.value = "Local Cache"
                    isLoading.value = false
                }
            }

            try {
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(
                    day = d,
                    month = m + 1,
                    year = y,
                    hours = 6,
                    minutes = 0,
                    seconds = 0,
                    latitude = loc.latitude,
                    longitude = loc.longitude,
                    timezone = loc.timezone
                )

                if (realPanchang.data != null) {
                    val panchangWithLoc = realPanchang.data.copy(locationName = loc.name)
                    todayDetailedPanchang.value = panchangWithLoc
                    todayDetailedPanchangForDetail.value = panchangWithLoc

                    val entity = PanchangEntity(
                        date = cacheKey,
                        tithi = panchangWithLoc.tithi,
                        paksha = panchangWithLoc.paksha,
                        nakshatra = panchangWithLoc.nakshatra,
                        yoga = panchangWithLoc.yoga,
                        karana = panchangWithLoc.karana,
                        sunrise = panchangWithLoc.sunrise,
                        sunset = panchangWithLoc.sunset,
                        rahuKaal = panchangWithLoc.rahukaal,
                        abhijeet = panchangWithLoc.abhijeet,
                        festival = panchangWithLoc.festival,
                        details = panchangWithLoc.details
                    )
                    repository.savePanchang(entity)
                    updatePanchangWithEntity(entity)

                    isCachedData.value = false
                    panchangSource.value = if (panchangWithLoc.sourceType == "REMOTE_SOURCE") "Authoritative Ephemeris API" else "Local Ephemeris"
                    panchangLastUpdated.value = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                    panchangError.value = null
                } else {
                    // Fallback to local calculation directly
                    val fallback = com.example.data.AstrologyService.fetchRealPanchang(
                        day = d, month = m + 1, year = y, hours = 6, minutes = 0, seconds = 0,
                        latitude = loc.latitude, longitude = loc.longitude, timezone = loc.timezone
                    )
                    if (fallback.data != null) {
                        val panchangWithLoc = fallback.data.copy(locationName = loc.name)
                        todayDetailedPanchang.value = panchangWithLoc
                        todayDetailedPanchangForDetail.value = panchangWithLoc
                        val entity = PanchangEntity(
                            date = cacheKey,
                            tithi = panchangWithLoc.tithi,
                            paksha = panchangWithLoc.paksha,
                            nakshatra = panchangWithLoc.nakshatra,
                            yoga = panchangWithLoc.yoga,
                            karana = panchangWithLoc.karana,
                            sunrise = panchangWithLoc.sunrise,
                            sunset = panchangWithLoc.sunset,
                            rahuKaal = panchangWithLoc.rahukaal,
                            abhijeet = panchangWithLoc.abhijeet,
                            festival = panchangWithLoc.festival,
                            details = panchangWithLoc.details
                        )
                        updatePanchangWithEntity(entity)
                    }
                }
            } catch (e: Exception) {
                // Ensure local calculation is always presented
                try {
                    val fallback = com.example.data.AstrologyService.fetchRealPanchang(
                        day = d, month = m + 1, year = y, hours = 6, minutes = 0, seconds = 0,
                        latitude = loc.latitude, longitude = loc.longitude, timezone = loc.timezone
                    )
                    if (fallback.data != null) {
                        val panchangWithLoc = fallback.data.copy(locationName = loc.name)
                        todayDetailedPanchang.value = panchangWithLoc
                        todayDetailedPanchangForDetail.value = panchangWithLoc
                        val entity = PanchangEntity(
                            date = cacheKey,
                            tithi = panchangWithLoc.tithi,
                            paksha = panchangWithLoc.paksha,
                            nakshatra = panchangWithLoc.nakshatra,
                            yoga = panchangWithLoc.yoga,
                            karana = panchangWithLoc.karana,
                            sunrise = panchangWithLoc.sunrise,
                            sunset = panchangWithLoc.sunset,
                            rahuKaal = panchangWithLoc.rahukaal,
                            abhijeet = panchangWithLoc.abhijeet,
                            festival = panchangWithLoc.festival,
                            details = panchangWithLoc.details
                        )
                        updatePanchangWithEntity(entity)
                    }
                } catch (_: Exception) {}
            } finally {
                isLoading.value = false
            }
        }
    }

    fun loadDetailPanchang() {
        loadPanchangForCalendar(selectedCalendar.value)
    }

    fun getDetailedPanchangForDate(day: Int, month: Int, year: Int, isEng: Boolean, callback: (DetailedPanchang?) -> Unit) {
        viewModelScope.launch {
            try {
                val loc = currentLocation.value
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(
                    day = day,
                    month = month + 1,
                    year = year,
                    hours = 6,
                    minutes = 0,
                    seconds = 0,
                    latitude = loc.latitude,
                    longitude = loc.longitude,
                    timezone = loc.timezone
                )
                if (realPanchang.data != null) {
                    callback(realPanchang.data.copy(locationName = loc.name))
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    private fun updatePanchangWithEntity(panchang: PanchangEntity) {
        val tithiEn = panchang.tithi
        val tithiHi = PanchangLocalization.localizeTithi(panchang.tithi, panchang.paksha)
        val nakshatraEn = panchang.nakshatra
        val nakshatraHi = PanchangLocalization.localizeNakshatra(panchang.nakshatra)
        val yogaEn = panchang.yoga
        val yogaHi = PanchangLocalization.localizeYoga(panchang.yoga)
        val karanaEn = panchang.karana
        val karanaHi = PanchangLocalization.localizeKarana(panchang.karana)

        _tithiEn.value = tithiEn
        _tithiHi.value = tithiHi
        _nakshatraEn.value = nakshatraEn
        _nakshatraHi.value = nakshatraHi
        _yogaEn.value = yogaEn
        _yogaHi.value = yogaHi
        _karanaEn.value = karanaEn
        _karanaHi.value = karanaHi
        _sunrise.value = panchang.sunrise
        _sunset.value = panchang.sunset
        _rahukaal.value = panchang.rahuKaal
        _abhijeet.value = panchang.abhijeet
        _festivalEn.value = panchang.festival
        _festivalHi.value = if (panchang.festival.isNotBlank() && panchang.festival != "—") panchang.festival else "भक्ति पूजा मुहूर्त"
        _detailsEn.value = panchang.details
        _detailsHi.value = "आज का दिन भक्तिमय गतिविधियों एवं पूजा के लिए शुभ है।"
    }
}
