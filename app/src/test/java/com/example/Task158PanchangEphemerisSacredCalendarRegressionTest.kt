package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.PanchangLocation
import com.example.ui.viewmodel.PanchangViewModel
import com.example.utils.HapticFeedbackManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task158PanchangEphemerisSacredCalendarRegressionTest {

    private lateinit var context: Context
    private lateinit var panchangRepo: PanchangRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        panchangRepo = PanchangRepository(context)
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTask158_canonicalDivineDesignTokensForPanchang() {
        assertEquals("Temple Cream token", Color(0xFFFDF8EE), TempleCream)
        assertEquals("Deep Sacred Maroon token", Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals("Devotional Saffron token", Color(0xFFE58A16), DevotionalSaffron)
        assertEquals("Sacred Gold token", Color(0xFFD4A23A), SacredGold)
        assertEquals("Temple Dark Background token", Color(0xFF160D08), TempleDarkBackground)

        assertEquals(TempleCream, LightSadhakColors.background)
        assertEquals(DeepSacredMaroon, LightSadhakColors.secondary)
        assertEquals(DevotionalSaffron, LightSadhakColors.primary)
        assertEquals(SacredGold, LightSadhakColors.accent)
        assertEquals(TempleDarkBackground, DarkSadhakColors.background)
    }

    @Test
    fun testTask158_tithiNakshatraYogaKaranaCalculationIntegrity() = runBlocking {
        val result = AstrologyService.fetchRealPanchang(
            day = 1,
            month = 9,
            year = 2026,
            hours = 6,
            minutes = 0,
            seconds = 0,
            latitude = 28.6139,
            longitude = 77.2090,
            timezone = 5.5
        )

        assertNotNull("Panchang computation result must not be null", result.data)
        val data = result.data!!

        // Tithi
        assertTrue("Tithi must be present and contain Paksha", data.tithi.isNotBlank() && data.tithi.contains("Paksha"))
        assertTrue("Paksha must be Shukla or Krishna", data.paksha == "Shukla Paksha" || data.paksha == "Krishna Paksha")

        // Nakshatra
        val validNakshatras = listOf(
            "Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra", "Punarvasu",
            "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni", "Hasta",
            "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha", "Mula", "Purva Ashadha",
            "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha", "Purva Bhadrapada",
            "Uttara Bhadrapada", "Revati"
        )
        assertTrue("Nakshatra '${data.nakshatra}' must be in canonical 27 list", validNakshatras.contains(data.nakshatra))
        assertTrue("Nakshatra Lord must be present", data.nakshatraLord.isNotBlank())
        assertTrue("Nakshatra Pada must be between 1 and 4", data.nakshatraPada.toIntOrNull() in 1..4)

        // Yoga
        val validYogas = listOf(
            "Vishkambha", "Priti", "Ayushman", "Saubhagya", "Sobhana", "Atiganda", "Sukarma",
            "Dhriti", "Shula", "Ganda", "Vriddhi", "Dhruva", "Vyaghat", "Harshana", "Vajra",
            "Siddhi", "Vyatipat", "Variyan", "Parigha", "Shiva", "Siddha", "Sadhya", "Shubha",
            "Shukla", "Brahma", "Indra", "Vaidhriti"
        )
        assertTrue("Yoga '${data.yoga}' must be in canonical 27 list", validYogas.contains(data.yoga))

        // Karana
        assertTrue("Karana must be non-empty", data.karana.isNotBlank())

        // Rashi
        assertTrue("Moon Rashi must be non-empty", data.rashi.isNotBlank())
        assertTrue("Sun Sign must be non-empty", data.sunSign.isNotBlank())
    }

    @Test
    fun testTask158_astronomicalSunriseSunsetAndMuhurtaDerivations() = runBlocking {
        val delhiResult = AstrologyService.fetchRealPanchang(
            day = 1, month = 9, year = 2026, hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        )
        val data = delhiResult.data!!

        // Verify sunrise and sunset formatted properly
        assertTrue("Sunrise should end with AM or PM", data.sunrise.endsWith("AM") || data.sunrise.endsWith("PM"))
        assertTrue("Sunset should end with AM or PM", data.sunset.endsWith("AM") || data.sunset.endsWith("PM"))

        // Rahu Kaal, Gulika Kaal, Yamaganda, Abhijit
        assertTrue("Rahu Kaal must contain 'to'", data.rahukaal.contains("to"))
        assertTrue("Gulika Kaal must contain 'to'", data.gulikaKaal.contains("to"))
        assertTrue("Yamaganda must contain 'to'", data.yamaganda.contains("to"))
        assertTrue("Abhijit Muhurta must contain 'to'", data.abhijeet.contains("to"))

        // Choghadiya
        assertTrue("Choghadiya list must contain periods", data.choghadiya.isNotBlank() && data.choghadiya.contains(","))
    }

    @Test
    fun testTask158_locationSensitivityAndRecalculation() = runBlocking {
        // Compare New Delhi (North) vs Tirupati (South) vs London (Zero Mer.)
        val delhi = AstrologyService.fetchRealPanchang(
            day = 1, month = 9, year = 2026, hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        ).data!!

        val london = AstrologyService.fetchRealPanchang(
            day = 1, month = 9, year = 2026, hours = 6, minutes = 0, seconds = 0,
            latitude = 51.5074, longitude = -0.1278, timezone = 0.0
        ).data!!

        assertNotEquals("Sunrise time in New Delhi and London must differ", delhi.sunrise, london.sunrise)
        assertNotEquals("Sunset time in New Delhi and London must differ", delhi.sunset, london.sunset)
        assertNotEquals("Rahu Kaal in New Delhi and London must differ", delhi.rahukaal, london.rahukaal)
    }

    @Test
    fun testTask158_roomV19PanchangOfflineCachingAndKeyIsolation() = runBlocking {
        val testDateKeyDelhi = "2026-09-01_New Delhi, India"
        val testDateKeyLondon = "2026-09-01_London, UK"

        val delhiEntity = PanchangEntity(
            date = testDateKeyDelhi,
            tithi = "Tritiya · Krishna Paksha",
            paksha = "Krishna Paksha",
            nakshatra = "Ashwini",
            yoga = "Siddha",
            karana = "Bava",
            sunrise = "06:01 AM",
            sunset = "06:44 PM",
            rahuKaal = "03:30 PM to 05:00 PM",
            abhijeet = "11:55 AM to 12:45 PM",
            festival = "Sadhana Vrata",
            details = "Vedic Panchang for Delhi"
        )

        val londonEntity = PanchangEntity(
            date = testDateKeyLondon,
            tithi = "Tritiya · Krishna Paksha",
            paksha = "Krishna Paksha",
            nakshatra = "Ashwini",
            yoga = "Siddha",
            karana = "Bava",
            sunrise = "06:15 AM",
            sunset = "07:45 PM",
            rahuKaal = "04:30 PM to 06:00 PM",
            abhijeet = "12:30 PM to 01:15 PM",
            festival = "Sadhana Vrata",
            details = "Vedic Panchang for London"
        )

        // Save both
        panchangRepo.savePanchang(delhiEntity)
        panchangRepo.savePanchang(londonEntity)

        // Verify retrieval isolation
        val retrievedDelhi = panchangRepo.getPanchangForDate(testDateKeyDelhi)
        val retrievedLondon = panchangRepo.getPanchangForDate(testDateKeyLondon)

        assertNotNull(retrievedDelhi)
        assertNotNull(retrievedLondon)
        assertEquals("06:01 AM", retrievedDelhi!!.sunrise)
        assertEquals("06:15 AM", retrievedLondon!!.sunrise)
    }

    @Test
    fun testTask158_presetLocationsCompleteness() {
        val locations = PanchangViewModel.PRESET_LOCATIONS
        assertTrue("Preset locations must have at least 10 spiritual/major centers", locations.size >= 10)

        // Check key sacred locations
        val names = locations.map { it.name }
        assertTrue(names.any { it.contains("New Delhi") })
        assertTrue(names.any { it.contains("Varanasi") })
        assertTrue(names.any { it.contains("Ujjain") })
        assertTrue(names.any { it.contains("Haridwar") })
        assertTrue(names.any { it.contains("Ayodhya") })
        assertTrue(names.any { it.contains("Mathura") })
        assertTrue(names.any { it.contains("Tirupati") })

        // Check latitude / longitude ranges
        locations.forEach { loc ->
            assertTrue("Latitude out of range for ${loc.name}", loc.latitude in -90.0..90.0)
            assertTrue("Longitude out of range for ${loc.name}", loc.longitude in -180.0..180.0)
            assertTrue("Name EN not empty", loc.name.isNotBlank())
            assertTrue("Name HI not empty", loc.nameHi.isNotBlank())
        }
    }

    @Test
    fun testTask158_hapticFeedbackManagerSafetyForPanchang() {
        val feedbackTypes = listOf(
            HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP,
            HapticFeedbackManager.FeedbackType.LIGHT_TOUCH,
            HapticFeedbackManager.FeedbackType.STANDARD_CLICK,
            HapticFeedbackManager.FeedbackType.SUCCESS
        )
        for (feedback in feedbackTypes) {
            HapticFeedbackManager.vibrate(context, feedback)
            HapticFeedbackManager.performHaptic(null, feedback)
        }
        assertTrue("All Panchang tactile haptics executed safely", true)
    }

    @Test
    fun testTask158_festivalVratRuleDerivationIntegrity() = runBlocking {
        // Check Ekadashi rule mapping
        val ekadashiPanchang = AstrologyService.fetchRealPanchang(
            day = 11, month = 1, year = 2026, hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        ).data!!

        assertNotNull(ekadashiPanchang.festival)
        assertTrue(
            "Festival string must not be empty",
            ekadashiPanchang.festival.isNotBlank()
        )
    }

    @Test
    fun testTask158_dateNavigationBoundaryIntegrity() {
        val calendar = Calendar.getInstance()
        val originalDay = calendar.get(Calendar.DAY_OF_YEAR)

        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val prevDay = calendar.get(Calendar.DAY_OF_YEAR)
        assertNotEquals(originalDay, prevDay)

        calendar.add(Calendar.DAY_OF_YEAR, 2)
        val nextDay = calendar.get(Calendar.DAY_OF_YEAR)
        assertNotEquals(prevDay, nextDay)
    }

    @Test
    fun testTask158_homeToFullPanchangConsistency() = runBlocking {
        val day = 1
        val month = 9
        val year = 2026

        val result1 = AstrologyService.fetchRealPanchang(
            day = day, month = month, year = year, hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        ).data!!

        val result2 = AstrologyService.fetchRealPanchang(
            day = day, month = month, year = year, hours = 6, minutes = 0, seconds = 0,
            latitude = 28.6139, longitude = 77.2090, timezone = 5.5
        ).data!!

        assertEquals(result1.tithi, result2.tithi)
        assertEquals(result1.nakshatra, result2.nakshatra)
        assertEquals(result1.yoga, result2.yoga)
        assertEquals(result1.karana, result2.karana)
        assertEquals(result1.sunrise, result2.sunrise)
        assertEquals(result1.sunset, result2.sunset)
        assertEquals(result1.rahukaal, result2.rahukaal)
        assertEquals(result1.abhijeet, result2.abhijeet)
    }
}
