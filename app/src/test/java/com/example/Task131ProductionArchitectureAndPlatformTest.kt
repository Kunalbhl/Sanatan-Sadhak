package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task131ProductionArchitectureAndPlatformTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testYoutubeUrlParser_extractsValidVideoIds() {
        // Standard watch URL
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        // Short URL
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://youtu.be/dQw4w9WgXcQ"))
        // Shorts URL
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/shorts/dQw4w9WgXcQ"))
        // Embed URL
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/embed/dQw4w9WgXcQ"))
        // Raw 11-char ID
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("dQw4w9WgXcQ"))
        // With query params
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ&t=45s"))
    }

    @Test
    fun testYoutubeUrlParser_rejectsInvalidInputs() {
        assertNull(YoutubeUrlParser.extractVideoId(""))
        assertNull(YoutubeUrlParser.extractVideoId("   "))
        assertNull(YoutubeUrlParser.extractVideoId("https://google.com"))
        assertNull(YoutubeUrlParser.extractVideoId("not_a_valid_id_at_all"))
    }

    @Test
    fun testYoutubeUrlParser_generatesCorrectThumbnailAndWatchUrls() {
        val videoId = "dQw4w9WgXcQ"
        assertEquals("https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg", YoutubeUrlParser.getThumbnailUrl(videoId))
        assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ", YoutubeUrlParser.getWatchUrl(videoId))
    }

    @Test
    fun testYoutubeUrlParser_supportedCategoriesList() {
        assertTrue(YoutubeUrlParser.SUPPORTED_CATEGORIES.contains("Sanatan Sadhak"))
        assertTrue(YoutubeUrlParser.SUPPORTED_CATEGORIES.contains("Mantra"))
        assertTrue(YoutubeUrlParser.SUPPORTED_CATEGORIES.contains("Darshan & Puja"))
        assertTrue(YoutubeUrlParser.SUPPORTED_CATEGORIES.size >= 10)
    }

    @Test
    fun testAppDiagnosticsManager_recordsAndDeduplicatesExceptions() {
        val ex1 = RuntimeException("Network timeout communicating with server")
        val fp1 = AppDiagnosticsManager.recordException(ex1, ErrorCategory.NETWORK, ErrorSeverity.MEDIUM)

        val errors = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, errors.size)
        assertEquals(fp1, errors[0].fingerprint)
        assertEquals(1, errors[0].occurrenceCount)
        assertEquals(ErrorCategory.NETWORK, errors[0].category)

        // Record the same exception again -> occurrence count increments
        val fp2 = AppDiagnosticsManager.recordException(ex1, ErrorCategory.NETWORK, ErrorSeverity.MEDIUM)
        assertEquals(fp1, fp2)

        val errorsAfterSecond = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, errorsAfterSecond.size)
        assertEquals(2, errorsAfterSecond[0].occurrenceCount)
    }

    @Test
    fun testAppDiagnosticsManager_sanitizesPiiInMessages() {
        val sensitiveMsg = "Error sending email to devotee.user@example.com using key AIzaSyD9876543210abcdefghijklmnopqrs"
        val ex = IllegalStateException(sensitiveMsg)
        AppDiagnosticsManager.recordException(ex, ErrorCategory.AUTH, ErrorSeverity.HIGH)

        val recorded = AppDiagnosticsManager.errorRecords.value[0]
        assertFalse(recorded.message.contains("devotee.user@example.com"))
        assertTrue(recorded.message.contains("[REDACTED_EMAIL]"))
        assertFalse(recorded.message.contains("AIzaSyD9876543210abcdefghijklmnopqrs"))
        assertTrue(recorded.message.contains("[REDACTED_API_KEY]"))
    }

    @Test
    fun testAppDiagnosticsManager_updatesErrorStatusAndClears() {
        val ex = IllegalArgumentException("Bad input test")
        val fp = AppDiagnosticsManager.recordException(ex, ErrorCategory.UI_NAV, ErrorSeverity.LOW)

        assertEquals(ErrorStatus.OPEN, AppDiagnosticsManager.errorRecords.value[0].status)

        AppDiagnosticsManager.updateErrorStatus(fp, ErrorStatus.RESOLVED)
        assertEquals(ErrorStatus.RESOLVED, AppDiagnosticsManager.errorRecords.value[0].status)

        AppDiagnosticsManager.clearAllErrors()
        assertTrue(AppDiagnosticsManager.errorRecords.value.isEmpty())
    }

    @Test
    fun testAppDiagnosticsManager_selfDiagnosticsExecution() {
        val healthResults = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(healthResults.isNotEmpty())
        assertTrue(healthResults.any { it.componentName.contains("Room Database") })
        assertTrue(healthResults.any { it.componentName.contains("Astronomical Panchang") })
        assertTrue(healthResults.any { it.componentName.contains("Notification Scheduler") })
        assertTrue(healthResults.any { it.componentName.contains("YouTube Content Sync") })
    }

    @Test
    fun testAppDiagnosticsManager_complianceAuditChecklistIntegrity() {
        val checklist = AppDiagnosticsManager.complianceAudit.value
        assertEquals(8, checklist.size)
        assertTrue(checklist.all { it.isCompliant })
        assertTrue(checklist.any { it.policyCategory == "YouTube TOS" })
        assertTrue(checklist.any { it.policyCategory == "Privacy & Data Safety" })
        assertTrue(checklist.any { it.policyCategory == "Google Play Policy" })
    }
}
