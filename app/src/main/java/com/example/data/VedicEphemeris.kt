package com.example.data

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Offline Sun/Moon positions for the Panchang.
 *
 * Sun and Moon use Paul Schlyter's low-precision theory (Kepler orbit plus the 12 main
 * lunar perturbations), accurate to about 1–2 arcminutes. That is plenty for tithi,
 * nakshatra, yoga and karana. It was checked against Meeus, Astronomical Algorithms,
 * examples 25.a and 47.a.
 *
 * All Panchang limbs use the SIDEREAL zodiac: tropical longitude minus Lahiri
 * (Chitrapaksha) ayanamsa. Forgetting the ayanamsa shifts the nakshatra by about
 * 1.8 places (for example, Shatabhisha shown instead of Shravana).
 */
object VedicEphemeris {

    private fun rad(d: Double) = Math.toRadians(d)
    private fun deg(r: Double) = Math.toDegrees(r)
    fun norm(x: Double): Double { val v = x % 360.0; return if (v < 0) v + 360.0 else v }

    /** Julian Day for a UT calendar date and fractional hour. */
    fun julianDay(year: Int, month: Int, day: Int, utHours: Double): Double {
        var y = year
        var m = month
        if (m <= 2) { y -= 1; m += 12 }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + utHours / 24.0 + b - 1524.5
    }

    /** Lahiri ayanamsa in degrees: 23°51'11" at J2000, about 50.27" per year. */
    fun lahiriAyanamsa(jd: Double): Double = 23.853 + (jd - 2451545.0) / 365.25 * 0.013964

    /** Tropical ecliptic longitudes (degrees, equinox of date): Pair(sun, moon). */
    fun tropicalSunMoon(jd: Double): Pair<Double, Double> {
        val d = jd - 2451543.5

        // Sun
        val ws = 282.9404 + 4.70935e-5 * d
        val es = 0.016709 - 1.151e-9 * d
        val ms = norm(356.0470 + 0.9856002585 * d)
        val eS = ms + deg(es) * sin(rad(ms)) * (1 + es * cos(rad(ms)))
        val sxv = cos(rad(eS)) - es
        val syv = sqrt(1 - es * es) * sin(rad(eS))
        val sun = norm(deg(atan2(syv, sxv)) + ws)

        // Moon
        val n = norm(125.1228 - 0.0529538083 * d)
        val i = 5.1454
        val w = norm(318.0634 + 0.1643573223 * d)
        val e = 0.054900
        val mm = norm(115.3654 + 13.0649929509 * d)
        var ecc = mm + deg(e) * sin(rad(mm)) * (1 + e * cos(rad(mm)))
        repeat(8) { ecc -= (ecc - deg(e) * sin(rad(ecc)) - mm) / (1 - e * cos(rad(ecc))) }
        val xv = cos(rad(ecc)) - e
        val yv = sqrt(1 - e * e) * sin(rad(ecc))
        val v = deg(atan2(yv, xv))
        val r = hypot(xv, yv)
        val xh = r * (cos(rad(n)) * cos(rad(v + w)) - sin(rad(n)) * sin(rad(v + w)) * cos(rad(i)))
        val yh = r * (sin(rad(n)) * cos(rad(v + w)) + cos(rad(n)) * sin(rad(v + w)) * cos(rad(i)))
        var lon = deg(atan2(yh, xh))

        val ls = norm(ms + ws)
        val lm = norm(mm + w + n)
        val dd = norm(lm - ls)
        val f = norm(lm - n)
        fun s(x: Double) = sin(rad(x))
        lon += -1.274 * s(mm - 2 * dd) + 0.658 * s(2 * dd) - 0.186 * s(ms) -
            0.059 * s(2 * mm - 2 * dd) - 0.057 * s(mm - 2 * dd + ms) + 0.053 * s(mm + 2 * dd) +
            0.046 * s(2 * dd - ms) + 0.041 * s(mm - ms) - 0.035 * s(dd) - 0.031 * s(mm + ms) -
            0.015 * s(2 * f - 2 * dd) + 0.011 * s(mm - 4 * dd)

        return Pair(sun, norm(lon))
    }

    /**
     * Moon's geocentric altitude (degrees) at [jd] for an observer at lat/lon (east +).
     * Uses the same Schlyter lunar theory incl. ecliptic latitude, then RA/Dec → hour angle.
     */
    fun moonAltitude(jd: Double, latDeg: Double, lonDeg: Double): Double {
        val d = jd - 2451543.5
        val n = norm(125.1228 - 0.0529538083 * d)
        val i = 5.1454
        val w = norm(318.0634 + 0.1643573223 * d)
        val e = 0.054900
        val mm = norm(115.3654 + 13.0649929509 * d)
        var ecc = mm + deg(e) * sin(rad(mm)) * (1 + e * cos(rad(mm)))
        repeat(8) { ecc -= (ecc - deg(e) * sin(rad(ecc)) - mm) / (1 - e * cos(rad(ecc))) }
        val xv = cos(rad(ecc)) - e
        val yv = sqrt(1 - e * e) * sin(rad(ecc))
        val v = deg(atan2(yv, xv))
        val r = hypot(xv, yv)
        val xh = r * (cos(rad(n)) * cos(rad(v + w)) - sin(rad(n)) * sin(rad(v + w)) * cos(rad(i)))
        val yh = r * (sin(rad(n)) * cos(rad(v + w)) + cos(rad(n)) * sin(rad(v + w)) * cos(rad(i)))
        val zh = r * sin(rad(v + w)) * sin(rad(i))
        val ms = norm(356.0470 + 0.9856002585 * d)
        val lm = norm(mm + w + n)
        val dd = norm(lm - norm(ms + 282.9404 + 4.70935e-5 * d))
        val ff = norm(lm - n)
        val lat = deg(atan2(zh, hypot(xh, yh))) - 0.173 * sin(rad(ff - 2 * dd)) - 0.055 * sin(rad(mm - ff - 2 * dd)) -
            0.046 * sin(rad(mm + ff - 2 * dd)) + 0.033 * sin(rad(ff + 2 * dd)) + 0.017 * sin(rad(2 * mm + ff))
        val lon = tropicalSunMoon(jd).second
        val obl = rad(23.4393 - 3.563e-7 * d)
        val ra = atan2(sin(rad(lon)) * cos(obl) - kotlin.math.tan(rad(lat)) * sin(obl), cos(rad(lon)))
        val dec = kotlin.math.asin(sin(rad(lat)) * cos(obl) + cos(rad(lat)) * sin(obl) * sin(rad(lon)))
        val gmst = norm(280.46061837 + 360.98564736629 * (jd - 2451545.0))
        val ha = rad(norm(gmst + lonDeg) ) - ra
        val phi = rad(latDeg)
        return deg(kotlin.math.asin(sin(phi) * sin(dec) + cos(phi) * cos(dec) * cos(ha)))
    }

    /**
     * Moonrise and moonset in local minutes after midnight (null if the event does not happen that day).
     * Scans the local day in 5-minute steps and interpolates the crossing of +0.125° (parallax − refraction − semidiameter).
     */
    fun moonRiseSet(year: Int, month: Int, day: Int, latDeg: Double, lonDeg: Double, tz: Double): Pair<Int?, Int?> {
        val h0 = 0.125
        val jd0 = julianDay(year, month, day, -tz)
        var rise: Int? = null; var set: Int? = null
        var prev = moonAltitude(jd0, latDeg, lonDeg) - h0
        var m = 5
        while (m <= 1440) {
            val cur = moonAltitude(jd0 + m / 1440.0, latDeg, lonDeg) - h0
            if (prev < 0 && cur >= 0 && rise == null) rise = (m - 5 + 5 * (-prev / (cur - prev))).toInt()
            if (prev >= 0 && cur < 0 && set == null) set = (m - 5 + 5 * (prev / (prev - cur))).toInt()
            prev = cur; m += 5
        }
        return Pair(rise, set)
    }

    data class Limbs(
        val sunSidereal: Double,
        val moonSidereal: Double,
        val elongation: Double,      // moon − sun, 0..360
        val tithiNumber: Int,        // 1..30 (1–15 Shukla, 16–30 Krishna)
        val nakshatraIndex: Int,     // 0..26
        val nakshatraPada: Int,      // 1..4
        val yogaIndex: Int,          // 0..26
        val karanaNumber: Int,       // 1..60
        val hoursToTithiEnd: Double,
        val hoursToNakshatraEnd: Double,
        val amantaMonthIndex: Int    // 0 = Chaitra … 11 = Phalguna
    )

    fun limbs(jd: Double): Limbs {
        val (sunT, moonT) = tropicalSunMoon(jd)
        val ay = lahiriAyanamsa(jd)
        val sun = norm(sunT - ay)
        val moon = norm(moonT - ay)
        val elong = norm(moonT - sunT)
        val nakSpan = 360.0 / 27.0

        // Motion over one hour, to estimate when the current tithi/nakshatra ends
        val (sun2, moon2) = tropicalSunMoon(jd + 1.0 / 24.0)
        val elongRate = norm(norm(moon2 - sun2) - elong).let { if (it > 180) it - 360 else it }.coerceAtLeast(0.3)
        val moonRate = norm(moon2 - moonT).let { if (it > 180) it - 360 else it }.coerceAtLeast(0.3)

        // Approximate previous new moon, to name the lunar month from the Sun's rashi then
        val daysSinceNewMoon = elong / 12.19
        val (sunNm, _) = tropicalSunMoon(jd - daysSinceNewMoon)
        val sunRashiAtNewMoon = (norm(sunNm - lahiriAyanamsa(jd)) / 30.0).toInt()
        // New moon with Sun in Meena (11) starts Chaitra (0)
        val amanta = (sunRashiAtNewMoon + 1) % 12

        val nakPos = moon / nakSpan
        return Limbs(
            sunSidereal = sun,
            moonSidereal = moon,
            elongation = elong,
            tithiNumber = (elong / 12.0).toInt() + 1,
            nakshatraIndex = nakPos.toInt().coerceIn(0, 26),
            nakshatraPada = ((nakPos % 1.0) * 4).toInt() + 1,
            yogaIndex = (norm(sun + moon) / nakSpan).toInt().coerceIn(0, 26),
            karanaNumber = (elong / 6.0).toInt() + 1,
            hoursToTithiEnd = (12.0 - elong % 12.0) / elongRate,
            hoursToNakshatraEnd = (nakSpan - moon % nakSpan) / moonRate,
            amantaMonthIndex = amanta
        )
    }
}
