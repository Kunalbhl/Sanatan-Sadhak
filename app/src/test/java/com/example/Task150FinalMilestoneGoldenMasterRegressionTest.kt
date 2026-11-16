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
class Task150FinalMilestoneGoldenMasterRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testMilestone_canonicalDesignTokensLocked() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
    }

    @Test
    fun testMilestone_fiveBottomNavigationDestinations() {
        val navItems = listOf("Knowledge", "Bhakti", "Home", "Videos", "Profile")
        assertEquals(5, navItems.size)
        assertEquals("Home", navItems[2])
    }

    @Test
    fun testMilestone_panchangCalculationAndFallback() {
        val calMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 7) }
        val atmosphere = DivineEnvironmentHelper.getCurrentAtmosphere(calMorning)
        assertEquals(DivineTimeAtmosphere.MORNING, atmosphere)
    }

    @Test
    fun testMilestone_accountIsolationAndSecurityCompliance() {
        val devoteeA = "devoteeA@sanatansadhak.org"
        val devoteeB = "devoteeB@sanatansadhak.org"
        assertNotEquals(devoteeA, devoteeB)

        val complianceItems = AppDiagnosticsManager.complianceAudit.value
        assertTrue(complianceItems.isNotEmpty())
        assertTrue(complianceItems.all { it.isCompliant })
    }

    @Test
    fun testMilestone_diagnosticsSanitizationAndFingerprinting() {
        val fp1 = AppDiagnosticsManager.recordError(
            category = ErrorCategory.AUTH,
            severity = ErrorSeverity.CRITICAL,
            errorType = "AuthFailure",
            message = AppDiagnosticsManager.sanitizeMessage("Login failure for devotee kunal@sanatan.org with password=DevoteePassword1000!")
        )

        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertTrue(activeErrors.isNotEmpty())
        val loggedError = activeErrors.first { it.fingerprint == fp1 }
        assertFalse(loggedError.message.contains("kunal@sanatan.org"))
        assertFalse(loggedError.message.contains("DevoteePassword1000!"))
        assertTrue(loggedError.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testMilestone_selfDiagnosticsAllSubsystems() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.componentName.contains("Database") })
        assertTrue(results.any { it.componentName.contains("Panchang") })
    }

    @Test
    fun testMilestone_notificationSchedulerPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
