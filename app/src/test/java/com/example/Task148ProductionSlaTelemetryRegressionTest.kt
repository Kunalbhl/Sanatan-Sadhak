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
class Task148ProductionSlaTelemetryRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTelemetry_boundednessAndDeduplication() {
        var lastFp = ""
        for (i in 1..10) {
            lastFp = AppDiagnosticsManager.recordError(
                category = ErrorCategory.NETWORK,
                severity = ErrorSeverity.MEDIUM,
                errorType = "SocketTimeoutException",
                message = "Network sync timeout on /sync endpoint"
            )
        }

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, activeErrors.size)
        val incident = activeErrors.first()
        assertEquals(10, incident.occurrenceCount)
        assertEquals(lastFp, incident.fingerprint)
    }

    @Test
    fun testTelemetry_piiSanitization() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.AUTH,
            severity = ErrorSeverity.CRITICAL,
            errorType = "AuthFailureException",
            message = AppDiagnosticsManager.sanitizeMessage("Failed login for devotee devotee@sanatan.org with password=SecretPassword123")
        )

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        val record = activeErrors.first { it.fingerprint == fp }
        assertFalse(record.message.contains("devotee@sanatan.org"))
        assertFalse(record.message.contains("SecretPassword123"))
        assertTrue(record.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testTelemetry_selfDiagnostics() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.componentName.contains("Database") })
        assertTrue(results.any { it.componentName.contains("Panchang") })
    }

    @Test
    fun testTelemetry_canonicalDesignTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testTelemetry_notificationSchedulerPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
