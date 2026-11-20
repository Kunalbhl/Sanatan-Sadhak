package com.example.data

import kotlin.math.floor

/**
 * When each Panchang limb actually changes during the local calendar day. A day often holds two tithis
 * (or nakshatras, yogas, karanas, moon signs): this finds every change, to the minute, from the same
 * ephemeris as the rest of the Panchang, plus the extra day-parts people ask for (Brahma Muhurta, Vijaya,
 * Godhuli, Pradosh, Nishita) and the weekday + nakshatra special yogas.
 */
object PanchangTimeline {

    data class Segment(val name: String, val startMin: Int, val endMin: Int)

    private val tithiNames = listOf("Pratipada", "Dwitiya", "Tritiya", "Chaturthi", "Panchami", "Shashthi", "Saptami", "Ashtami", "Navami", "Dashami", "Ekadashi", "Dwadashi", "Trayodashi", "Chaturdashi")
    private val nakshatras = listOf("Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra", "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni", "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha", "Mula", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha", "Purva Bhadrapada", "Uttara Bhadrapada", "Revati")
    private val yogas = listOf("Vishkambha", "Priti", "Ayushman", "Saubhagya", "Sobhana", "Atiganda", "Sukarma", "Dhriti", "Shula", "Ganda", "Vriddhi", "Dhruva", "Vyaghat", "Harshana", "Vajra", "Siddhi", "Vyatipat", "Variyan", "Parigha", "Shiva", "Siddha", "Sadhya", "Shubha", "Shukla", "Brahma", "Indra", "Vaidhriti")
    private val rashis = listOf("Mesha", "Vrishabha", "Mithuna", "Karka", "Simha", "Kanya", "Tula", "Vrishchika", "Dhanu", "Makara", "Kumbha", "Meena")
    private val movableKaranas = listOf("Bava", "Balava", "Kaulava", "Taitila", "Gara", "Vanija", "Vishti (Bhadra)")

    private fun tithiName(n: Int): String = when {
        n == 15 -> "Purnima · Shukla"
        n == 30 -> "Amavasya · Krishna"
        n < 15 -> "${tithiNames[n - 1]} · Shukla"
        else -> "${tithiNames[n - 16]} · Krishna"
    }

    private fun karanaName(n: Int): String = when (n) {
        1 -> "Kimstughna"
        in 2..57 -> movableKaranas[(n - 2) % 7]
        58 -> "Shakuni"
        59 -> "Chatushpada"
        else -> "Naga"
    }

    /** value of each limb as an Int key at a given Julian day */
    private fun key(limb: Int, jd: Double): Int {
        val l = VedicEphemeris.limbs(jd)
        return when (limb) {
            0 -> l.tithiNumber
            1 -> l.nakshatraIndex
            2 -> l.yogaIndex
            3 -> l.karanaNumber
            else -> ((l.moonSidereal / 30.0).toInt()) % 12
        }
    }

    private fun name(limb: Int, k: Int): String = when (limb) {
        0 -> tithiName(k)
        1 -> nakshatras[k]
        2 -> yogas[k]
        3 -> karanaName(k)
        else -> rashis[k]
    }

    /** Local minutes (relative to local midnight of the day) → Julian day */
    private fun jd(year: Int, month: Int, day: Int, tz: Double, minutes: Double) =
        VedicEphemeris.julianDay(year, month, day, minutes / 60.0 - tz)

    /** Segments for one limb that overlap the local day [0, 1440). Start/end may fall outside the day. */
    fun segments(limb: Int, year: Int, month: Int, day: Int, tz: Double): List<Segment> {
        // Scan from 40 h before to 64 h after local midnight, find where the value changes, then refine to the minute
        val step = 10.0
        val from = -40.0 * 60
        val to = 64.0 * 60
        val changes = mutableListOf<Pair<Double, Int>>() // time of change, new value
        var t = from
        var prev = key(limb, jd(year, month, day, tz, t))
        val firstValue = prev
        while (t < to) {
            val next = t + step
            val v = key(limb, jd(year, month, day, tz, next))
            if (v != prev) {
                var lo = t
                var hi = next
                repeat(10) {
                    val mid = (lo + hi) / 2
                    if (key(limb, jd(year, month, day, tz, mid)) == prev) lo = mid else hi = mid
                }
                changes += hi to v
                prev = v
            }
            t = next
        }
        val out = mutableListOf<Segment>()
        var curName = name(limb, firstValue)
        var curStart = Double.NEGATIVE_INFINITY
        for ((time, v) in changes) {
            out += Segment(curName, if (curStart.isInfinite()) -9999 else Math.round(curStart).toInt(), Math.round(time).toInt())
            curName = name(limb, v)
            curStart = time
        }
        out += Segment(curName, if (curStart.isInfinite()) -9999 else Math.round(curStart).toInt(), 99999)
        // Merge neighbours with the same name (e.g. karana names repeat) and keep what touches the day
        val merged = mutableListOf<Segment>()
        for (s in out) {
            if (merged.isNotEmpty() && merged.last().name == s.name) merged[merged.lastIndex] = merged.last().copy(endMin = s.endMin)
            else merged += s
        }
        return merged.filter { it.endMin > 0 && it.startMin < 1440 }
    }

    fun fmt(min: Int): String {
        val dayShift = floor(min / 1440.0).toInt()
        val m = min - dayShift * 1440
        val h24 = m / 60
        val ampm = if (h24 >= 12) "PM" else "AM"
        val h = if (h24 % 12 == 0) 12 else h24 % 12
        val time = "%d:%02d %s".format(h, m % 60, ampm)
        return when {
            dayShift < 0 -> "$time (prev day)"
            dayShift > 0 -> "$time (next day)"
            else -> time
        }
    }

    /** "2:14 PM – 11:02 AM (next day)", "until 2:14 PM", "from 2:14 PM" */
    fun describe(s: Segment): String {
        val a = if (s.startMin > -9000) fmt(s.startMin) else null
        val b = if (s.endMin < 90000) fmt(s.endMin) else null
        return when {
            a != null && b != null -> "$a – $b"
            b != null -> "until $b"
            a != null -> "from $a"
            else -> "all day"
        }
    }

    data class Info(val limbLabel: String, val segments: List<Segment>)

    /** All five changing limbs for the day. */
    fun limbs(year: Int, month: Int, day: Int, tz: Double): List<Info> = listOf(
        Info("Tithi", segments(0, year, month, day, tz)),
        Info("Nakshatra", segments(1, year, month, day, tz)),
        Info("Yoga", segments(2, year, month, day, tz)),
        Info("Karana", segments(3, year, month, day, tz)),
        Info("Moon sign", segments(4, year, month, day, tz))
    )

    /** Extra day-parts from sunrise and sunset (minutes from local midnight). */
    fun dayParts(sunriseMin: Int, sunsetMin: Int): List<Pair<String, String>> {
        val day = (sunsetMin - sunriseMin).coerceAtLeast(60)
        val dayMuhurta = day / 15.0
        val nextSunrise = sunriseMin + 1440
        val night = nextSunrise - sunsetMin
        val nightMuhurta = night / 15.0
        fun r(a: Double, b: Double) = "${fmt(a.toInt())} – ${fmt(b.toInt())}"
        return listOf(
            "Brahma Muhurta" to r(sunriseMin - 96.0, sunriseMin - 48.0),
            "Vijaya Muhurta" to r(sunriseMin + 10 * dayMuhurta, sunriseMin + 11 * dayMuhurta),
            "Godhuli Muhurta" to r(sunsetMin - 24.0, sunsetMin + 24.0),
            "Pradosh Kaal" to r(sunsetMin.toDouble(), sunsetMin + 144.0),
            "Nishita Muhurta" to r(sunsetMin + 7 * nightMuhurta, sunsetMin + 8 * nightMuhurta)
        )
    }

    private val amritSiddhi = mapOf(1 to "Hasta", 2 to "Mrigashira", 3 to "Ashwini", 4 to "Anuradha", 5 to "Pushya", 6 to "Revati", 7 to "Rohini")
    private val sarvarthaSiddhi = mapOf(
        1 to setOf("Hasta", "Mula", "Uttara Phalguni", "Uttara Ashadha", "Uttara Bhadrapada", "Ashwini", "Pushya"),
        2 to setOf("Rohini", "Mrigashira", "Pushya", "Anuradha", "Shravana"),
        3 to setOf("Ashwini", "Krittika", "Ashlesha", "Uttara Bhadrapada"),
        4 to setOf("Krittika", "Rohini", "Mrigashira", "Anuradha", "Hasta"),
        5 to setOf("Ashwini", "Punarvasu", "Pushya", "Anuradha", "Revati"),
        6 to setOf("Ashwini", "Punarvasu", "Anuradha", "Shravana", "Revati"),
        7 to setOf("Rohini", "Swati", "Shravana")
    )

    /** Special yogas from the weekday (Calendar.DAY_OF_WEEK) and the nakshatra at sunrise. */
    fun specialYogas(dayOfWeek: Int, sunriseNakshatra: String): List<String> {
        val out = mutableListOf<String>()
        if (amritSiddhi[dayOfWeek] == sunriseNakshatra) out += "Amrit Siddhi Yoga"
        if (sarvarthaSiddhi[dayOfWeek]?.contains(sunriseNakshatra) == true) out += "Sarvartha Siddhi Yoga"
        return out
    }
}
