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
class Task147PostReleaseMonitoringRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testMonitoring_allCategoriesValidation() {
        val categories = ErrorCategory.values()
        assertTrue(categories.contains(ErrorCategory.AUTH))
        assertTrue(categories.contains(ErrorCategory.DATABASE))
        assertTrue(categories.contains(ErrorCategory.NETWORK))
        assertTrue(categories.contains(ErrorCategory.API))
        assertTrue(categories.contains(ErrorCategory.YOUTUBE))
        assertTrue(categories.contains(ErrorCategory.PANCHANG))
        assertTrue(categories.contains(ErrorCategory.NOTIFICATION))
        assertTrue(categories.contains(ErrorCategory.GPS))
        assertTrue(categories.contains(ErrorCategory.AUDIO_TTS))
        assertTrue(categories.contains(ErrorCategory.WEBVIEW))
        assertTrue(categories.contains(ErrorCategory.UI_NAV))
        assertTrue(categories.contains(ErrorCategory.PERMISSION))
        assertTrue(categories.contains(ErrorCategory.BACKGROUND_WORKER))
    }

    @Test
    fun testMonitoring_deduplicationAcrossIncidents() {
        var lastFp = ""
        for (i in 1..5) {
            lastFp = AppDiagnosticsManager.recordError(
                category = ErrorCategory.YOUTUBE,
                severity = ErrorSeverity.MEDIUM,
                errorType = "RssFeedSyncTimeout",
                message = "YouTube RSS feed timeout, fallback to cache"
            )
        }

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, activeErrors.size)
        val incident = activeErrors.first()
        assertEquals(5, incident.occurrenceCount)
        assertEquals(lastFp, incident.fingerprint)
    }

    @Test
    fun testMonitoring_piiSanitization() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.API,
            severity = ErrorSeverity.CRITICAL,
            errorType = "AuthFailure",
            message = AppDiagnosticsManager.sanitizeMessage("Incident for devotee devotee@sanatan.org with Bearer ReleaseSecretToken")
        )

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        val loggedError = activeErrors.first { it.fingerprint == fp }
        assertFalse(loggedError.message.contains("devotee@sanatan.org"))
        assertFalse(loggedError.message.contains("ReleaseSecretToken"))
        assertTrue(loggedError.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testMonitoring_selfDiagnostics() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.componentName.contains("Database") })
        assertTrue(results.any { it.componentName.contains("Panchang") })
    }

    @Test
    fun testMonitoring_notificationSchedulerPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
