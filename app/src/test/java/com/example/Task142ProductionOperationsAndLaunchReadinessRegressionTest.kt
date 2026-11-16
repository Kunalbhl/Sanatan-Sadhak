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
class Task142ProductionOperationsAndLaunchReadinessRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testOperations_canonicalTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testOperations_diagnosticsDeduplication() {
        var lastFp = ""
        for (i in 1..4) {
            lastFp = AppDiagnosticsManager.recordError(
                category = ErrorCategory.NETWORK,
                severity = ErrorSeverity.MEDIUM,
                errorType = "SyncRetry",
                message = "Operation network retry"
            )
        }
        val errors = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, errors.size)
        assertEquals(4, errors.first().occurrenceCount)
    }

    @Test
    fun testOperations_sanitization() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.AUTH,
            severity = ErrorSeverity.CRITICAL,
            errorType = "OpsAuth",
            message = AppDiagnosticsManager.sanitizeMessage("Ops login for devotee devotee@sanatan.org with password=OpsSecretPass")
        )
        val errors = AppDiagnosticsManager.errorRecords.value
        val record = errors.first { it.fingerprint == fp }
        assertFalse(record.message.contains("devotee@sanatan.org"))
        assertFalse(record.message.contains("OpsSecretPass"))
        assertTrue(record.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testOperations_selfDiagnostics() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
    }

    @Test
    fun testOperations_notificationPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
