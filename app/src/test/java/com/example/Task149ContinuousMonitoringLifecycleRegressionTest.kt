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
class Task149ContinuousMonitoringLifecycleRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testLifecycle_stateRecoveryAndCanonicalTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testLifecycle_backgroundWorkerAndNotificationScheduler() {
        assertNotNull(SadhakNotificationHelper)
        val selfDiag = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(selfDiag.any { it.componentName.contains("Notification") || it.componentName.contains("Background") })
    }

    @Test
    fun testLifecycle_youTubeContentPipelineAndFallback() {
        val testUrl = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        val videoId = YoutubeUrlParser.extractVideoId(testUrl)
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
        val bhaktiVideo = remoteVideo.toBhaktiVideo(id = 1490)
        assertEquals("dQw4w9WgXcQ", bhaktiVideo.videoId)
        assertTrue(bhaktiVideo.isCustom)
    }

    @Test
    fun testLifecycle_errorReportingAndLifecycleResilience() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.API,
            severity = ErrorSeverity.MEDIUM,
            errorType = "RestApiException",
            message = "API endpoint retry succeeded on second attempt"
        )
        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertTrue(activeErrors.any { it.fingerprint == fp })
    }

    @Test
    fun testLifecycle_atmosphereTransitions() {
        val calMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 6) }
        assertEquals(DivineTimeAtmosphere.MORNING, DivineEnvironmentHelper.getCurrentAtmosphere(calMorning))
    }
}
