package com.example

import com.example.data.VedicEphemeris
import org.junit.Assert.assertEquals
import org.junit.Test

class VedicEphemerisTest {

    @Test
    fun sunMatchesMeeusExample25a() {
        // 1992 Oct 13.0 TD: true longitude 199.909°
        val (sun, _) = VedicEphemeris.tropicalSunMoon(VedicEphemeris.julianDay(1992, 10, 13, 0.0))
        assertEquals(199.909, sun, 0.05)
    }

    @Test
    fun moonMatchesMeeusExample47a() {
        // 1992 Apr 12.0 TD: apparent longitude 133.163°
        val (_, moon) = VedicEphemeris.tropicalSunMoon(VedicEphemeris.julianDay(1992, 4, 12, 0.0))
        assertEquals(133.163, moon, 0.1)
    }

    @Test
    fun nakshatraIsShravanaOn16Nov2026Morning() {
        // Sunrise Delhi ~06:50 IST = 01:20 UT. The sidereal Moon is ~283.5°, which is Shravana (index 21)
        val limbs = VedicEphemeris.limbs(VedicEphemeris.julianDay(2026, 11, 16, 1.33))
        assertEquals(21, limbs.nakshatraIndex)
        assertEquals(7, limbs.tithiNumber) // Shukla Saptami
    }

    @Test
    fun lahiriAyanamsaIsAbout24Point2In2026() {
        assertEquals(24.22, VedicEphemeris.lahiriAyanamsa(VedicEphemeris.julianDay(2026, 11, 16, 0.0)), 0.05)
    }
}
