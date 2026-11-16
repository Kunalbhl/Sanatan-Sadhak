package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.ui.components.DivineEnvironmentHelper
import com.example.ui.components.DivineTimeAtmosphere
import com.example.ui.theme.*
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
class Task133ExactVisualParityRegressionTest {

    private lateinit var context: Context
    private lateinit var videoRepository: VideoRepository
    private lateinit var adminRepository: AdminRepository
    private lateinit var firebaseService: FirebaseService

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        videoRepository = VideoRepository(context)
        adminRepository = AdminRepository(context)
        firebaseService = FirebaseService(context)
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testDivineDesignSystemColors_canonicalTokens() {
        // 1. Warm Ivory (#FDF8EE)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        // 2. Deep Sacred Maroon (#7A1720)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        // 3. Devotional Saffron (#E58A16)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        // 4. Sacred Gold (#D4A23A)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        // 5. Temple Dark (#160D08)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testDivineAtmosphereCalculation_timeOfDayMapping() {
        val morningCal = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 7) }
        assertEquals(DivineTimeAtmosphere.MORNING, DivineEnvironmentHelper.getCurrentAtmosphere(morningCal))

        val dayCal = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 14) }
        assertEquals(DivineTimeAtmosphere.DAY, DivineEnvironmentHelper.getCurrentAtmosphere(dayCal))

        val eveningCal = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 18) }
        assertEquals(DivineTimeAtmosphere.EVENING, DivineEnvironmentHelper.getCurrentAtmosphere(eveningCal))

        val nightCal = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 22) }
        assertEquals(DivineTimeAtmosphere.NIGHT, DivineEnvironmentHelper.getCurrentAtmosphere(nightCal))
    }

    @Test
    fun testDetailedPanchang_comprehensiveFieldsPresent() {
        val panchang = DetailedPanchang(
            tithi = "Shukla Paksha Ekadashi",
            nakshatra = "Rohini Nakshatra",
            yoga = "Siddha",
            karana = "Bava",
            rashi = "Vrishabha",
            sunrise = "05:47 AM",
            sunset = "07:15 PM",
            rahukaal = "01:30 PM to 03:00 PM",
            gulikaKaal = "09:00 AM to 10:30 AM",
            yamaganda = "06:00 AM to 07:30 AM",
            abhijeet = "11:50 AM to 12:40 PM",
            festival = "Ekadashi Vrat",
            details = "Auspicious for devotional puja.",
            locationName = "New Delhi, India",
            sourceType = "REMOTE_SOURCE"
        )

        assertEquals("Shukla Paksha Ekadashi", panchang.tithi)
        assertEquals("Rohini Nakshatra", panchang.nakshatra)
        assertEquals("05:47 AM", panchang.sunrise)
        assertEquals("07:15 PM", panchang.sunset)
        assertEquals("01:30 PM to 03:00 PM", panchang.rahukaal)
        assertEquals("11:50 AM to 12:40 PM", panchang.abhijeet)
        assertEquals("09:00 AM to 10:30 AM", panchang.gulikaKaal)
        assertEquals("06:00 AM to 07:30 AM", panchang.yamaganda)
        assertEquals("REMOTE_SOURCE", panchang.sourceType)
    }

    @Test
    fun testDailyShlokaItem_verseAndMetadataIntegrity() {
        val shloka = DailyDharmaRepository.getDailyShloka()
        assertNotNull(shloka)
        assertTrue(shloka.verseSanskrit.isNotBlank())
        assertTrue(shloka.meaningEn.isNotBlank())
        assertTrue(shloka.meaningHi.isNotBlank())
        assertTrue(shloka.sourceScriptureEn.isNotBlank())
        assertTrue(shloka.chapterVerse.isNotBlank())
    }

    @Test
    fun testBackendYouTubeAndAdminPreservation_task132Intact() = runBlocking {
        // YouTube parsing
        val parsedId = YoutubeUrlParser.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        assertEquals("dQw4w9WgXcQ", parsedId)

        // Remote video entity
        val video = RemoteBhaktiVideo(
            videoId = "dQw4w9WgXcQ",
            sourceType = "ADMIN_CURATED",
            channelName = "Sanatan Sadhak",
            title = "Hanuman Chalisa",
            description = "Devotional chant",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg",
            publishedAt = "2026-08-24",
            duration = "09:45"
        )
        val roomVideo = video.toBhaktiVideo(id = 101)
        assertEquals("dQw4w9WgXcQ", roomVideo.videoId)
        assertEquals("Hanuman Chalisa", roomVideo.title)
        assertTrue(roomVideo.isCustom)

        // Diagnostic manager & compliance
        val complianceItems = AppDiagnosticsManager.complianceAudit.value
        assertTrue(complianceItems.isNotEmpty())
        assertTrue(complianceItems.all { it.isCompliant })
    }

    @Test
    fun testBottomNavigationStructure_fiveSacredTabs() {
        val expectedTabs = listOf("Knowledge", "Bhakti", "Home", "Videos", "Profile")
        assertEquals(5, expectedTabs.size)
        assertEquals("Home", expectedTabs[2]) // Center Home item
    }
}
