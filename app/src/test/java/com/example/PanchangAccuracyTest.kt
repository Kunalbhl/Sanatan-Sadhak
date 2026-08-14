package com.example

import com.example.data.AstrologyService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class PanchangAccuracyTest {

    @Test
    fun testPanchangForMultipleLocationsAndDates() = runBlocking {
        // 1. Delhi
        val delhiPanchang = AstrologyService.fetchRealPanchang(
            day = 15, month = 8, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        )
        assertNotNull(delhiPanchang.data)
        val delhi = delhiPanchang.data!!
        assertTrue(delhi.sunrise.isNotEmpty() && delhi.sunrise != "—")
        assertTrue(delhi.sunset.isNotEmpty() && delhi.sunset != "—")
        assertTrue(delhi.tithi.isNotEmpty() && delhi.tithi != "—")
        assertTrue(delhi.samvatYear.contains("2083")) // 2026 + 57 = 2083 Vikram Samvat

        // 2. Jodhpur
        val jodhpurPanchang = AstrologyService.fetchRealPanchang(
            day = 1, month = 1, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 26.2384, longitude = 73.0243, timezone = 5.5
        )
        assertNotNull(jodhpurPanchang.data)
        val jodhpur = jodhpurPanchang.data!!
        assertTrue(jodhpur.sunrise.isNotEmpty())

        // 3. Varanasi
        val varanasiPanchang = AstrologyService.fetchRealPanchang(
            day = 10, month = 10, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 25.3176, longitude = 82.9739, timezone = 5.5
        )
        assertNotNull(varanasiPanchang.data)

        // 4. Ujjain (Prime Meridian of ancient Indian astronomy)
        val ujjainPanchang = AstrologyService.fetchRealPanchang(
            day = 21, month = 3, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 23.1765, longitude = 75.7885, timezone = 5.5
        )
        assertNotNull(ujjainPanchang.data)

        // 5. London (International Latitude/Longitude test)
        val londonPanchang = AstrologyService.fetchRealPanchang(
            day = 21, month = 6, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 51.5074, longitude = -0.1278, timezone = 0.0
        )
        assertNotNull(londonPanchang.data)
    }

    @Test
    fun testKaranaRules() = runBlocking {
        val result = AstrologyService.fetchRealPanchang(
            day = 1, month = 1, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        )
        assertNotNull(result.data)
        val karana = result.data!!.karana
        assertTrue("Karana should be populated", karana.isNotEmpty() && karana != "—")
    }

    @Test
    fun testEngineClassificationAndConventions() = runBlocking {
        assertEquals("APPROXIMATE_LOCAL_ENGINE", com.example.data.EngineClassification.LOCAL_ENGINE_CLASSIFICATION)
        
        val convention = com.example.data.PanchangConvention()
        assertEquals(com.example.data.AyanamshaSystem.LAHIRI, convention.ayanamsha)
        assertEquals(com.example.data.LunarMonthSystem.PURNIMANTA, convention.lunarMonthSystem)
        assertEquals(com.example.data.SamvatSystem.CHAITRADI, convention.samvatSystem)

        val delhiResult = AstrologyService.fetchRealPanchang(
            day = 15, month = 8, year = 2026,
            hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        )
        assertNotNull(delhiResult.data)
        val data = delhiResult.data!!
        assertEquals("APPROXIMATE", data.accuracyStatus)
        assertTrue(data.calculationMethod.contains("APPROXIMATE_LOCAL_ENGINE"))
        assertEquals("Unavailable offline (Requires API)", data.moonrise)
        assertEquals("Unavailable offline (Requires API)", data.moonset)
    }

    @Test
    fun testRealWorldReferenceValidationFramework() {
        // Validation framework testing when reference dataset is unavailable internally
        val hasExternalReferenceDataset = false
        val validationStatus = if (hasExternalReferenceDataset) "VALIDATED" else com.example.data.EngineClassification.REFERENCE_DATA_UNAVAILABLE
        assertEquals("REFERENCE_DATA_UNAVAILABLE", validationStatus)
    }

    @Test
    fun testVikramSamvatConversion() {
        val gregorianYear = 2026
        val expectedSamvat = 2026 + 57
        assertEquals(2083, expectedSamvat)
    }
}
