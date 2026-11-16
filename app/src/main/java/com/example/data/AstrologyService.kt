package com.example.data

import android.util.Log
import com.example.BuildConfig
import com.example.data.DetailedPanchang
import com.example.data.PanchangResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object AstrologyService {
    private const val TAG = "AstrologyService"
    
    // Configurable API key (placeholder by default, reads from BuildConfig if set)
    var PANCHANG_API_KEY: String = BuildConfig.PANCHANG_API_KEY.ifEmpty { "" }

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    /**
     * Fetches Panchang details from freeastrologyapi.com for a given date.
     * Returns a DetailedPanchang on success, or null on error.
     */
    suspend fun fetchRealPanchang(
        day: Int,
        month: Int,
        year: Int,
        hours: Int,
        minutes: Int,
        seconds: Int,
        latitude: Double = 28.6139,
        longitude: Double = 77.2090,
        timezone: Double = 5.5
    ): PanchangResult = withContext(Dispatchers.IO) {
        // If API key is empty or default, trigger robust dynamic local Vedic calculations to ensure real data is ALWAYS displayed
        if (PANCHANG_API_KEY.isEmpty() || PANCHANG_API_KEY == "MY_PANCHANG_API_KEY" || PANCHANG_API_KEY == "YOUR_VEDIC_ASTRO_API_KEY" || PANCHANG_API_KEY == "placeholder" || PANCHANG_API_KEY == "") {
            Log.w(TAG, "PANCHANG_API_KEY is empty or default. Using real-time local Vedic Panchang calculation engine to provide accurate data.")
            
            // Local high-fidelity Vedic calculation based on date, location and timezone
            val calendar = java.util.Calendar.getInstance()
            calendar.set(year, month - 1, day)
            val dayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            val dayOfYear = calendar.get(java.util.Calendar.DAY_OF_YEAR)

            // 1. Calculate Astronomical Sunrise/Sunset for latitude, longitude, timezone
            val gamma = 2 * Math.PI * (dayOfYear - 1) / 365.0
            val eqtime = 229.18 * (0.000075 + 0.001868 * Math.cos(gamma) - 0.032077 * Math.sin(gamma) - 0.014615 * Math.cos(2 * gamma) - 0.040849 * Math.sin(2 * gamma))
            val decl = 0.006918 - 0.399912 * Math.cos(gamma) + 0.070257 * Math.sin(gamma) - 0.006758 * Math.cos(2 * gamma) + 0.000907 * Math.sin(2 * gamma) - 0.002697 * Math.cos(3 * gamma) + 0.00148 * Math.sin(3 * gamma)

            val latRad = Math.toRadians(latitude)
            val cosH = (Math.sin(Math.toRadians(-0.833)) - Math.sin(latRad) * Math.sin(decl)) / (Math.cos(latRad) * Math.cos(decl))
            val H = if (cosH >= 1.0) 0.0 else if (cosH <= -1.0) Math.PI else Math.acos(cosH)
            val hDeg = Math.toDegrees(H)

            val solarNoonUtc = 720 - 4 * longitude - eqtime
            val solarNoonLocal = solarNoonUtc + 60 * timezone

            val sunriseMinutes = (solarNoonLocal - 4 * hDeg).toInt().coerceIn(0, 1439)
            val sunsetMinutes = (solarNoonLocal + 4 * hDeg).toInt().coerceIn(0, 1439)

            fun formatMin(min: Int): String {
                var h = (min / 60) % 24
                if (h < 0) h += 24
                val m = Math.abs(min % 60)
                val ampm = if (h >= 12) "PM" else "AM"
                var displayH = h % 12
                if (displayH == 0) displayH = 12
                return String.format("%02d:%02d %s", displayH, m, ampm)
            }

            val sunriseStr = formatMin(sunriseMinutes)
            val sunsetStr = formatMin(sunsetMinutes)

            // 2. Calculate Octet Muhurtas (Rahu Kaal, Yamaganda, Gulika Kaal, Abhijeet)
            val dayLength = (sunsetMinutes - sunriseMinutes).coerceAtLeast(1)
            val octet = dayLength / 8
            val part15 = dayLength / 15

            // Rahu Kaal Octets: Sun=8, Mon=2, Tue=7, Wed=5, Thu=6, Fri=4, Sat=3
            val rahuOctet = when (dayOfWeek) {
                java.util.Calendar.MONDAY -> 2
                java.util.Calendar.TUESDAY -> 7
                java.util.Calendar.WEDNESDAY -> 5
                java.util.Calendar.THURSDAY -> 6
                java.util.Calendar.FRIDAY -> 4
                java.util.Calendar.SATURDAY -> 3
                else -> 8 // Sunday
            }
            val rahuStartMin = sunriseMinutes + (rahuOctet - 1) * octet
            val rahuEndMin = sunriseMinutes + rahuOctet * octet
            val rahuKaalStr = "${formatMin(rahuStartMin)} to ${formatMin(rahuEndMin)}"

            // Yamaganda Octets: Sun=5, Mon=4, Tue=3, Wed=2, Thu=1, Fri=7, Sat=6
            val yamaOctet = when (dayOfWeek) {
                java.util.Calendar.SUNDAY -> 5
                java.util.Calendar.MONDAY -> 4
                java.util.Calendar.TUESDAY -> 3
                java.util.Calendar.WEDNESDAY -> 2
                java.util.Calendar.THURSDAY -> 1
                java.util.Calendar.FRIDAY -> 7
                else -> 6 // Saturday
            }
            val yamaStartMin = sunriseMinutes + (yamaOctet - 1) * octet
            val yamaEndMin = sunriseMinutes + yamaOctet * octet
            val yamaStr = "${formatMin(yamaStartMin)} to ${formatMin(yamaEndMin)}"

            // Gulika Kaal Octets: Sun=7, Mon=6, Tue=5, Wed=4, Thu=3, Fri=2, Sat=1
            val gulikaOctet = when (dayOfWeek) {
                java.util.Calendar.SUNDAY -> 7
                java.util.Calendar.MONDAY -> 6
                java.util.Calendar.TUESDAY -> 5
                java.util.Calendar.WEDNESDAY -> 4
                java.util.Calendar.THURSDAY -> 3
                java.util.Calendar.FRIDAY -> 2
                else -> 1 // Saturday
            }
            val gulikaStartMin = sunriseMinutes + (gulikaOctet - 1) * octet
            val gulikaEndMin = sunriseMinutes + gulikaOctet * octet
            val gulikaStr = "${formatMin(gulikaStartMin)} to ${formatMin(gulikaEndMin)}"

            // Abhijeet Muhurta = 8th muhurta of 15 daytime muhurtas
            val abhiStartMin = sunriseMinutes + 7 * part15
            val abhiEndMin = sunriseMinutes + 8 * part15
            val abhijeetStr = "${formatMin(abhiStartMin)} to ${formatMin(abhiEndMin)}"

            // 3. Moonrise / Moonset handling for local calculation
            // Note: Accurate Moonrise/Moonset requires precise topocentric ephemeris.
            // As per astronomical standards, offline simplified models do not report exact moonrise times.
            val moonriseStr = "Unavailable offline (Requires API)"
            val moonsetStr = "Unavailable offline (Requires API)"

            // 4. Sun & Moon positions at local sunrise (Udaya Tithi convention), Lahiri sidereal zodiac
            val sunriseUtHours = sunriseMinutes / 60.0 - timezone
            val jdSunrise = VedicEphemeris.julianDay(year, month, day, sunriseUtHours)
            val limbs = VedicEphemeris.limbs(jdSunrise)
            val sunLongitude = limbs.sunSidereal
            val moonLongitude = limbs.moonSidereal

            // Tithi = (Moon − Sun) / 12°
            val tithiNum = limbs.tithiNumber
            val isShukla = tithiNum <= 15
            val localTithiNum = if (isShukla) tithiNum else tithiNum - 15

            val tithiNames = listOf("Pratipada", "Dwitiya", "Tritiya", "Chaturthi", "Panchami", "Shashthi", "Saptami", "Ashtami", "Navami", "Dashami", "Ekadashi", "Dwadashi", "Trayodashi", "Chaturdashi", "Purnima", "Amavasya")
            val tithiName = if (localTithiNum == 15) {
                if (isShukla) "Purnima" else "Amavasya"
            } else if (localTithiNum in 1..14) {
                tithiNames[localTithiNum - 1]
            } else "Tritiya"
            val pakshaVal = if (isShukla) "Shukla Paksha" else "Krishna Paksha"
            val tithiVal = "$tithiName · $pakshaVal"

            // Tithi end time, from the current relative motion of the Moon and Sun
            val tithiEndMin = ((sunriseMinutes + limbs.hoursToTithiEnd * 60).toInt()) % 1440
            val tithiEndTimeStr = "${formatMin(tithiEndMin)} (Approx)"

            // Nakshatras (27) derived from Moon Sidereal Longitude (13°20' = 13.3333° each)
            val nakshatras = listOf("Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra", "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni", "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha", "Mula", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha", "Purva Bhadrapada", "Uttara Bhadrapada", "Revati")
            val nakLords = listOf("Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury", "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury", "Ketu", "Venus", "Sun", "Moon", "Mars", "Rahu", "Jupiter", "Saturn", "Mercury")
            val nakIndex = limbs.nakshatraIndex
            val nakshatraVal = nakshatras[nakIndex]
            val nakLordVal = nakLords[nakIndex]
            val nakPadaVal = limbs.nakshatraPada.toString()

            // Yoga (27) = (Sun Longitude + Moon Longitude) / 13°20'
            val yogas = listOf("Vishkambha", "Priti", "Ayushman", "Saubhagya", "Sobhana", "Atiganda", "Sukarma", "Dhriti", "Shula", "Ganda", "Vriddhi", "Dhruva", "Vyaghat", "Harshana", "Vajra", "Siddhi", "Vyatipat", "Variyan", "Parigha", "Shiva", "Siddha", "Sadhya", "Shubha", "Shukla", "Brahma", "Indra", "Vaidhriti")
            val yogaIndex = limbs.yogaIndex
            val yogaVal = yogas[yogaIndex]

            // Karana (60 halves of 30 Tithis in a month)
            // Karana 1 = Kimstughna (Fixed)
            // Karanas 2..57 = 7 Movable Karanas repeated 8 times (Bava, Balava, Kaulava, Taitila, Gara, Vanija, Vishti)
            // Karana 58 = Shakuni (Fixed), 59 = Chatushpada (Fixed), 60 = Naga (Fixed)
            val halfTithi = limbs.karanaNumber
            val karanaVal = when (halfTithi) {
                1 -> "Kimstughna (Fixed)"
                in 2..57 -> {
                    val movables = listOf("Bava", "Balava", "Kaulava", "Taitila", "Gara", "Vanija", "Vishti (Bhadra)")
                    movables[(halfTithi - 2) % 7]
                }
                58 -> "Shakuni (Fixed)"
                59 -> "Chatushpada (Fixed)"
                60 -> "Naga (Fixed)"
                else -> "Bava"
            }

            // Moon Rashi (12 Rashis from Moon Longitude)
            val rashis = listOf("Mesha (Aries)", "Vrishabha (Taurus)", "Mithuna (Gemini)", "Karka (Cancer)", "Simha (Leo)", "Kanya (Virgo)", "Tula (Libra)", "Vrishchika (Scorpio)", "Dhanu (Sagittarius)", "Makara (Capricorn)", "Kumbha (Aquarius)", "Meena (Pisces)")
            val rashiIndex = ((moonLongitude / 30.0).toInt()) % 12
            val moonRashiVal = rashis[rashiIndex]

            // Sun Sign from Sun Longitude
            val sunSignIndex = ((sunLongitude / 30.0).toInt()) % 12
            val sunSignVal = rashis[sunSignIndex]

            // Hindu Months & Ritu & Ayana
            val monthsHi = listOf("Chaitra", "Vaisakha", "Jyaistha", "Ashadha", "Shravana", "Bhadrapada", "Ashwin", "Kartik", "Margashirsha", "Pausha", "Magha", "Phalguna")
            val ritus = listOf("Vasanta (Spring)", "Grishma (Summer)", "Varsha (Monsoon)", "Sharad (Autumn)", "Hemant (Pre-winter)", "Shishir (Winter)")
            // Amanta month from the Sun's rashi at the last new moon; Purnimanta runs one month ahead in Krishna paksha
            val amantaIndex = limbs.amantaMonthIndex
            val monthIndex = if (isShukla) amantaIndex else (amantaIndex + 1) % 12
            val hinduMonthVal = "${monthsHi[monthIndex]} (Purnimanta)"
            val rituVal = ritus[amantaIndex / 2]
            // Uttarayana while the sidereal Sun runs from Makara (270°) to the end of Mithuna (90°)
            val ayanaVal = if (sunLongitude >= 270.0 || sunLongitude < 90.0) "Uttarayana" else "Dakshinayana"

            // Vikram Samvat (Chaitradi): the new year starts at Chaitra Shukla Pratipada
            val samvatVal = "${if (month >= 4 || amantaIndex <= 1) year + 57 else year + 56} Vikram Samvat (Chaitradi)"

            // Choghadiya Summary
            val choghadiyaList = when (dayOfWeek) {
                java.util.Calendar.SUNDAY -> "Udveg, Chara, Labha, Amrita, Kala, Shubha, Roga, Udveg"
                java.util.Calendar.MONDAY -> "Amrita, Kala, Shubha, Roga, Udveg, Chara, Labha, Amrita"
                java.util.Calendar.TUESDAY -> "Roga, Udveg, Chara, Labha, Amrita, Kala, Shubha, Roga"
                java.util.Calendar.WEDNESDAY -> "Labha, Amrita, Kala, Shubha, Roga, Udveg, Chara, Labha"
                java.util.Calendar.THURSDAY -> "Shubha, Roga, Udveg, Chara, Labha, Amrita, Kala, Shubha"
                java.util.Calendar.FRIDAY -> "Chara, Labha, Amrita, Kala, Shubha, Roga, Udveg, Chara"
                else -> "Kala, Shubha, Roga, Udveg, Chara, Labha, Amrita, Kala" // Saturday
            }

            val festivalVal = when {
                localTithiNum == 11 -> "Ekadashi Vrata (एकादशी व्रत)"
                localTithiNum == 14 -> "Pradosha Vrata (प्रदोष व्रत)"
                localTithiNum == 15 && isShukla -> "Purnima Vrata & Satyanarayan Puja (पूर्णिमा व्रत)"
                localTithiNum == 15 && !isShukla -> "Amavasya Pitru Tarpan (अमावस्या)"
                localTithiNum == 4 -> "Sankashti Chaturthi (संकष्टी चतुर्थी)"
                else -> "—"
            }

            val detailedPanchang = DetailedPanchang(
                tithi = tithiVal,
                nakshatra = nakshatraVal,
                yoga = yogaVal,
                karana = karanaVal,
                rashi = moonRashiVal,
                sunrise = sunriseStr,
                sunset = sunsetStr,
                rahukaal = rahuKaalStr,
                gulikaKaal = gulikaStr,
                yamaganda = yamaStr,
                abhijeet = abhijeetStr,
                festival = festivalVal,
                details = "Auspicious Vedic Panchang under $nakshatraVal nakshatra ($nakLordVal lord, Pada $nakPadaVal) and $tithiVal tithi.",
                weekday = when (dayOfWeek) {
                    java.util.Calendar.MONDAY -> "Monday (सोमवार)"
                    java.util.Calendar.TUESDAY -> "Tuesday (मंगलवार)"
                    java.util.Calendar.WEDNESDAY -> "Wednesday (बुधवार)"
                    java.util.Calendar.THURSDAY -> "Thursday (गुरुवार)"
                    java.util.Calendar.FRIDAY -> "Friday (शुक्रवार)"
                    java.util.Calendar.SATURDAY -> "Saturday (शनिवार)"
                    else -> "Sunday (रविवार)"
                },
                tithiEndTime = tithiEndTimeStr,
                nakshatraPada = nakPadaVal,
                nakshatraLord = nakLordVal,
                lunarMonth = hinduMonthVal,
                paksha = pakshaVal,
                ritu = rituVal,
                samvatYear = samvatVal,
                sunSign = sunSignVal,
                moonrise = moonriseStr,
                moonset = moonsetStr,
                ayana = ayanaVal,
                choghadiya = choghadiyaList,
                locationName = "Lat: ${String.format("%.2f", latitude)}, Lng: ${String.format("%.2f", longitude)}",
                sourceType = "CALCULATED",
                accuracyStatus = "APPROXIMATE",
                calculationMethod = "${EngineClassification.LOCAL_ENGINE_CLASSIFICATION} (Lahiri Sidereal Zodiac)",
                conventionNote = "Lahiri Ayanamsha (${String.format("%.2f", VedicEphemeris.lahiriAyanamsa(jdSunrise))}°) • Sidereal • Purnimanta • Chaitradi Samvat",
                festivalType = if (localTithiNum == 11) "REQUIRES_VERIFICATION" else "RULE_DERIVED_OBSERVANCE"
            )
            return@withContext PanchangResult(detailedPanchang, null, null, null, null, 200)
        }
        
        Log.d(TAG, "Fetching Panchang for: $day-$month-$year")

        // Validate inputs
        if (latitude < -90.0 || latitude > 90.0) return@withContext PanchangResult(null, "Invalid latitude: $latitude", null, null, null, null)
        if (longitude < -180.0 || longitude > 180.0) return@withContext PanchangResult(null, "Invalid longitude: $longitude", null, null, null, null)
        if (year < 1900 || year > 2100) return@withContext PanchangResult(null, "Invalid year: $year", null, null, null, null)
        if (month < 1 || month > 12) return@withContext PanchangResult(null, "Invalid month: $month", null, null, null, null)
        if (day < 1 || day > 31) return@withContext PanchangResult(null, "Invalid day: $day", null, null, null, null)

        // Prepare JSON request body
        val jsonBody = JSONObject().apply {
            put("year", year)
            put("month", month)
            put("date", day)
            put("hours", hours)
            put("minutes", minutes)
            put("seconds", seconds)
            put("latitude", latitude)
            put("longitude", longitude)
            put("timezone", timezone)
            put("config", JSONObject().apply {
                put("observation_point", "topocentric")
                put("ayanamsha", "lahiri")
            })
        }
        val requestBodyStr = jsonBody.toString()

        val requestBody = requestBodyStr.toRequestBody("application/json".toMediaType())
        
        val url = "https://json.freeastrologyapi.com/panchang"
        // Setup request with custom API Key headers
        val request = Request.Builder()
            .url(url)
            .addHeader("x-api-key", PANCHANG_API_KEY)
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string()
                val statusCode = response.code
                Log.d(TAG, "Panchang API Response code: $statusCode")
                // Log.d(TAG, "Panchang API Raw Response received")
                
                if (!response.isSuccessful || bodyStr == null) {
                    val retryAfter = response.header("Retry-After")
                    if (statusCode == 429) {
                        Log.w(TAG, "Panchang API Rate Limited (429). Retry-After: $retryAfter. Falling back to offline local calculation engine.")
                    } else {
                        Log.w(TAG, "Panchang API call failed: $statusCode / ${response.message} - falling back to local calculation.")
                    }
                    val originalKey = PANCHANG_API_KEY
                    PANCHANG_API_KEY = ""
                    val fallbackResult = fetchRealPanchang(day, month, year, hours, minutes, seconds, latitude, longitude, timezone)
                    PANCHANG_API_KEY = originalKey
                    return@withContext fallbackResult
                }

                val responseJson = JSONObject(bodyStr)
                
                // Locate Panchang output data block
                val output = if (responseJson.has("output")) {
                    responseJson.optJSONObject("output") ?: responseJson
                } else if (responseJson.has("data")) {
                    responseJson.optJSONObject("data") ?: responseJson
                } else {
                    responseJson
                }

                // ... (Helper to parse nested values or return string directly)
                fun parseValue(obj: JSONObject, key: String, nestedKey: String = "name"): String {
                    if (!obj.has(key)) return ""
                    val value = obj.get(key)
                    if (value is JSONObject) {
                        if (value.has("details")) {
                            val details = value.optJSONObject("details")
                            if (details != null) {
                                if (details.has(nestedKey)) return details.optString(nestedKey)
                                if (details.has("name")) return details.optString("name")
                            }
                        }
                        if (value.has(nestedKey)) return value.optString(nestedKey)
                        if (value.has("name")) return value.optString("name")
                        return value.toString()
                    }
                    return value.toString()
                }

                // After fetching, process the raw response

                // Extracted fields with robust parsing & defaults
                var tithiVal = "—"
                var tithiEndTime = "—"
                if (output.has("tithi") && !output.isNull("tithi")) {
                    val t = output.get("tithi")
                    if (t is JSONObject) {
                        val name = t.optString("name", "").ifEmpty { t.optString("details", "") }
                        val paksha = t.optString("paksha", "")
                        tithiEndTime = t.optString("end_time", "—")
                        tithiVal = if (name.isNotEmpty() && paksha.isNotEmpty()) {
                            "$name · $paksha"
                        } else if (name.isNotEmpty()) {
                            name
                        } else {
                            t.toString()
                        }
                    } else {
                        tithiVal = t.toString()
                    }
                }
                if (tithiVal.isEmpty() || tithiVal == "null") tithiVal = "—"
                if (tithiEndTime.isEmpty() || tithiEndTime == "null") tithiEndTime = "—"

                var nakshatraVal = "—"
                var nakshatraPada = "—"
                var nakshatraLord = "—"
                if (output.has("nakshatra") && !output.isNull("nakshatra")) {
                    val n = output.get("nakshatra")
                    if (n is JSONObject) {
                        val name = n.optString("name", "")
                        val endTime = n.optString("end_time", "")
                        nakshatraPada = n.optString("pada", "—")
                        nakshatraLord = n.optString("lord", "").ifEmpty { n.optString("ruler", "—") }
                        nakshatraVal = if (name.isNotEmpty() && endTime.isNotEmpty()) {
                            "$name (until $endTime)"
                        } else if (name.isNotEmpty()) {
                            name
                        } else {
                            n.toString()
                        }
                    } else {
                        nakshatraVal = n.toString()
                    }
                }
                if (nakshatraVal.isEmpty() || nakshatraVal == "null") nakshatraVal = "—"
                if (nakshatraPada.isEmpty() || nakshatraPada == "null") nakshatraPada = "—"
                if (nakshatraLord.isEmpty() || nakshatraLord == "null") nakshatraLord = "—"

                var yogaVal = "—"
                if (output.has("yoga") && !output.isNull("yoga")) {
                    val y = output.get("yoga")
                    if (y is JSONObject) {
                        yogaVal = y.optString("name", "—")
                    } else {
                        yogaVal = y.toString()
                    }
                }
                if (yogaVal.isEmpty() || yogaVal == "null") yogaVal = "—"

                var karanaVal = "—"
                if (output.has("karana") && !output.isNull("karana")) {
                    val k = output.get("karana")
                    if (k is JSONObject) {
                        karanaVal = k.optString("name", "—")
                    } else {
                        karanaVal = k.toString()
                    }
                } else if (output.has("karanas") && !output.isNull("karanas")) {
                    val arr = output.optJSONArray("karanas")
                    if (arr != null && arr.length() > 0) {
                        val k = arr.optJSONObject(0)
                        if (k != null) {
                            karanaVal = k.optString("name", "—")
                        }
                    }
                }
                if (karanaVal.isEmpty() || karanaVal == "null") karanaVal = "—"

                var moonSignVal = "—"
                if (output.has("moon_sign") && !output.isNull("moon_sign")) {
                    val m = output.get("moon_sign")
                    if (m is JSONObject) {
                        moonSignVal = m.optString("name", "—")
                    } else {
                        moonSignVal = m.toString()
                    }
                } else if (output.has("rashi") && !output.isNull("rashi")) {
                    val r = output.get("rashi")
                    if (r is JSONObject) {
                        moonSignVal = r.optString("name", "—")
                    } else {
                        moonSignVal = r.toString()
                    }
                }
                if (moonSignVal.isEmpty() || moonSignVal == "null") moonSignVal = "—"

                var sunSignVal = "—"
                if (output.has("sun_sign") && !output.isNull("sun_sign")) {
                    val s = output.get("sun_sign")
                    if (s is JSONObject) {
                        sunSignVal = s.optString("name", "—")
                    } else {
                        sunSignVal = s.toString()
                    }
                }
                if (sunSignVal.isEmpty() || sunSignVal == "null") sunSignVal = "—"

                var sunriseVal = "—"
                if (output.has("sunrise") && !output.isNull("sunrise")) {
                    sunriseVal = output.optString("sunrise", "—")
                } else if (output.has("sun_rise") && !output.isNull("sun_rise")) {
                    sunriseVal = output.optString("sun_rise", "—")
                }
                if (sunriseVal.isEmpty() || sunriseVal == "null") sunriseVal = "—"

                var sunsetVal = "—"
                if (output.has("sunset") && !output.isNull("sunset")) {
                    sunsetVal = output.optString("sunset", "—")
                } else if (output.has("sun_set") && !output.isNull("sun_set")) {
                    sunsetVal = output.optString("sun_set", "—")
                }
                if (sunsetVal.isEmpty() || sunsetVal == "null") sunsetVal = "—"

                var rahukaalVal = "—"
                if (output.has("rahukaal") && !output.isNull("rahukaal")) {
                    val r = output.get("rahukaal")
                    if (r is JSONObject) {
                        val start = r.optString("start", "")
                        val end = r.optString("end", "")
                        rahukaalVal = if (start.isNotEmpty() && end.isNotEmpty()) {
                            "$start to $end"
                        } else {
                            r.toString()
                        }
                    } else {
                        rahukaalVal = r.toString()
                    }
                } else if (output.has("rahu_kaal") && !output.isNull("rahu_kaal")) {
                    val r = output.get("rahu_kaal")
                    if (r is JSONObject) {
                        val start = r.optString("start", "")
                        val end = r.optString("end", "")
                        rahukaalVal = if (start.isNotEmpty() && end.isNotEmpty()) {
                            "$start to $end"
                        } else {
                            r.toString()
                        }
                    } else {
                        rahukaalVal = r.toString()
                    }
                }
                if (rahukaalVal.isEmpty() || rahukaalVal == "null") rahukaalVal = "—"

                var abhijitVal = "—"
                if (output.has("abhijit_muhurta") && !output.isNull("abhijit_muhurta")) {
                    val a = output.get("abhijit_muhurta")
                    if (a is JSONObject) {
                        val start = a.optString("start", "")
                        val end = a.optString("end", "")
                        abhijitVal = if (start.isNotEmpty() && end.isNotEmpty()) {
                            "$start to $end"
                        } else {
                            a.toString()
                        }
                    } else {
                        abhijitVal = a.toString()
                    }
                } else if (output.has("abhijit") && !output.isNull("abhijit")) {
                    val a = output.get("abhijit")
                    if (a is JSONObject) {
                        val start = a.optString("start", "")
                        val end = a.optString("end", "")
                        abhijitVal = if (start.isNotEmpty() && end.isNotEmpty()) {
                            "$start to $end"
                        } else {
                            a.toString()
                        }
                    } else {
                        abhijitVal = a.toString()
                    }
                }
                if (abhijitVal.isEmpty() || abhijitVal == "null") abhijitVal = "—"

                var pakshaVal = "—"
                if (output.has("paksha") && !output.isNull("paksha")) {
                    pakshaVal = output.optString("paksha", "—")
                }
                if (pakshaVal.isEmpty() || pakshaVal == "null") pakshaVal = "—"

                var hinduMonthVal = "—"
                if (output.has("hindu_maah") && !output.isNull("hindu_maah")) {
                    val h = output.get("hindu_maah")
                    if (h is JSONObject) {
                        hinduMonthVal = h.optString("name", "—")
                    } else {
                        hinduMonthVal = h.toString()
                    }
                } else if (output.has("lunar_month") && !output.isNull("lunar_month")) {
                    val l = output.get("lunar_month")
                    if (l is JSONObject) {
                        hinduMonthVal = l.optString("name", "—")
                    } else {
                        hinduMonthVal = l.toString()
                    }
                }
                if (hinduMonthVal.isEmpty() || hinduMonthVal == "null") hinduMonthVal = "—"

                var samvatVal = "—"
                if (output.has("vikram_samvat") && !output.isNull("vikram_samvat")) {
                    samvatVal = output.optString("vikram_samvat", "—")
                } else if (output.has("samvat_year") && !output.isNull("samvat_year")) {
                    samvatVal = output.optString("samvat_year", "—")
                } else if (output.has("samvat") && !output.isNull("samvat")) {
                    samvatVal = output.optString("samvat", "—")
                }
                if (samvatVal.isEmpty() || samvatVal == "null") samvatVal = "—"

                var rituVal = "—"
                if (output.has("ritu") && !output.isNull("ritu")) {
                    rituVal = output.optString("ritu", "—")
                }
                if (rituVal.isEmpty() || rituVal == "null") rituVal = "—"

                var weekdayVal = "—"
                if (output.has("weekday") && !output.isNull("weekday")) {
                    weekdayVal = output.optString("weekday", "—")
                }
                if (weekdayVal.isEmpty() || weekdayVal == "null") weekdayVal = "—"

                var festivalVal = "—"
                if (output.has("festival") && !output.isNull("festival")) {
                    festivalVal = output.optString("festival", "—")
                }
                if (festivalVal.isEmpty() || festivalVal == "null") festivalVal = "—"

                // Map to domain model
                val detailedPanchang = DetailedPanchang(
                    tithi = tithiVal,
                    nakshatra = nakshatraVal,
                    yoga = yogaVal,
                    karana = karanaVal,
                    rashi = moonSignVal,
                    sunrise = sunriseVal,
                    sunset = sunsetVal,
                    rahukaal = rahukaalVal,
                    gulikaKaal = output.optString("gulika_kaal", "—"),
                    yamaganda = output.optString("yamaganda", "—"),
                    abhijeet = abhijitVal,
                    festival = festivalVal,
                    details = "Auspicious Vedic day under $nakshatraVal nakshatra and $tithiVal tithi.",
                    weekday = weekdayVal,
                    tithiEndTime = tithiEndTime,
                    nakshatraPada = nakshatraPada,
                    nakshatraLord = nakshatraLord,
                    lunarMonth = hinduMonthVal,
                    paksha = pakshaVal,
                    ritu = rituVal,
                    samvatYear = samvatVal,
                    sunSign = sunSignVal,
                    sourceType = "REMOTE_SOURCE",
                    accuracyStatus = "REMOTE_SOURCE",
                    calculationMethod = "Authoritative Ephemeris API",
                    conventionNote = "Authoritative Remote Ephemeris API • Lahiri Ayanamsha",
                    festivalType = "VERIFIED_FESTIVAL_DATE"
                )

                PanchangResult(detailedPanchang, null, null, jsonBody.toString(), bodyStr, statusCode)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calling freeastrologyapi.com: ${e.message} - falling back to local calculation.", e)
            val originalKey = PANCHANG_API_KEY
            PANCHANG_API_KEY = ""
            val fallbackResult = fetchRealPanchang(day, month, year, hours, minutes, seconds, latitude, longitude, timezone)
            PANCHANG_API_KEY = originalKey
            return@withContext fallbackResult
        }
    }
}
