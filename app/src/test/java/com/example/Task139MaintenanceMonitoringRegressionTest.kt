package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.notifications.SadhakNotificationHelper
import com.example.ui.theme.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task139MaintenanceMonitoringRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testMaintenance_canonicalTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testMaintenance_diagnosticsDeduplication() {
        var lastFp = ""
        for (i in 1..2) {
            lastFp = AppDiagnosticsManager.recordError(
                category = ErrorCategory.NETWORK,
                severity = ErrorSeverity.MEDIUM,
                errorType = "MaintenanceNetwork",
                message = "Maintenance ping"
            )
        }
        val errors = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, errors.size)
        assertEquals(2, errors.first().occurrenceCount)
    }

    @Test
    fun testMaintenance_sanitization() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.AUTH,
            severity = ErrorSeverity.CRITICAL,
            errorType = "MaintenanceAuth",
            message = AppDiagnosticsManager.sanitizeMessage("Maintenance login for devotee devotee@sanatan.org with Bearer MaintKey")
        )
        val errors = AppDiagnosticsManager.errorRecords.value
        val record = errors.first { it.fingerprint == fp }
        assertFalse(record.message.contains("devotee@sanatan.org"))
        assertFalse(record.message.contains("MaintKey"))
        assertTrue(record.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testMaintenance_selfDiagnostics() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
    }

    @Test
    fun testMaintenance_notificationPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
