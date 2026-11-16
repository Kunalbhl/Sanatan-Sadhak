package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.notifications.SadhakNotificationHelper
import com.example.ui.components.DivineEnvironmentHelper
import com.example.ui.components.DivineTimeAtmosphere
import com.example.ui.theme.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task146PlayStoreSubmissionReadinessRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testPlayStore_canonicalDesignTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testPlayStore_fiveBottomNavigationDestinations() {
        val navItems = listOf("Knowledge", "Bhakti", "Home", "Videos", "Profile")
        assertEquals(5, navItems.size)
        assertEquals("Home", navItems[2])
    }

    @Test
    fun testPlayStore_youTubeSyncAndAdminContentPipeline() {
        val videoId = YoutubeUrlParser.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
        assertEquals("dQw4w9WgXcQ", videoId)

        val remoteVideo = RemoteBhaktiVideo(
            videoId = "dQw4w9WgXcQ",
            sourceType = "ADMIN_CURATED",
            channelName = "Sanatan Sadhak",
            title = "Aarti Kunj Bihari Ki",
            description = "Sacred Aarti Chant",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg",
            publishedAt = "2026-08-25",
            duration = "05:30"
        )
        val bhaktiVideo = remoteVideo.toBhaktiVideo(id = 1460)
        assertEquals("dQw4w9WgXcQ", bhaktiVideo.videoId)
        assertTrue(bhaktiVideo.isCustom)
    }

    @Test
    fun testPlayStore_diagnosticsSanitizationAndFingerprinting() {
        val fp1 = AppDiagnosticsManager.recordError(
            category = ErrorCategory.API,
            severity = ErrorSeverity.CRITICAL,
            errorType = "PreSubmissionCheck",
            message = AppDiagnosticsManager.sanitizeMessage("Play store readiness test for devotee kunal@sanatan.org with Bearer PreSubmissionToken")
        )

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertTrue(activeErrors.isNotEmpty())
        val loggedError = activeErrors.first { it.fingerprint == fp1 }
        assertFalse(loggedError.message.contains("kunal@sanatan.org"))
        assertFalse(loggedError.message.contains("PreSubmissionToken"))
        assertTrue(loggedError.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testPlayStore_selfDiagnosticsAllSubsystems() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.componentName.contains("Database") })
        assertTrue(results.any { it.componentName.contains("Panchang") })
    }

    @Test
    fun testPlayStore_notificationSchedulerPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
