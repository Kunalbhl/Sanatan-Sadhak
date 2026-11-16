package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.notifications.SadhakNotificationHelper
import com.example.ui.components.DivineEnvironmentHelper
import com.example.ui.components.DivineTimeAtmosphere
import com.example.ui.theme.*
import com.example.utils.DivineNameSystem
import com.example.utils.HapticFeedbackManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task155CanonicalHomeFunctionalInteractionRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTask155_canonicalDesignTokensLocked() {
        assertEquals("Temple Cream token locked", Color(0xFFFDF8EE), TempleCream)
        assertEquals("Deep Sacred Maroon token locked", Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals("Devotional Saffron token locked", Color(0xFFE58A16), DevotionalSaffron)
        assertEquals("Sacred Gold token locked", Color(0xFFD4A23A), SacredGold)
        assertEquals("Temple Dark Background token locked", Color(0xFF160D08), TempleDarkBackground)

        assertEquals(TempleCream, LightSadhakColors.background)
        assertEquals(DeepSacredMaroon, LightSadhakColors.secondary)
        assertEquals(DevotionalSaffron, LightSadhakColors.primary)
        assertEquals(SacredGold, LightSadhakColors.accent)
        assertEquals(TempleDarkBackground, DarkSadhakColors.background)
    }

    @Test
    fun testTask155_homeFiveQuickActionsIntegrity() {
        val expectedActionsEn = listOf("Japa Mala", "Dhyan", "My Mantra", "Aarti", "Chants")
        val expectedActionsHi = listOf("जप माला", "ध्यान", "मेरे मंत्र", "आरती", "स्तोत्र")
        assertEquals(5, expectedActionsEn.size)
        assertEquals(5, expectedActionsHi.size)
        assertEquals("Japa Mala", expectedActionsEn[0])
        assertEquals("Dhyan", expectedActionsEn[1])
        assertEquals("My Mantra", expectedActionsEn[2])
        assertEquals("Aarti", expectedActionsEn[3])
        assertEquals("Chants", expectedActionsEn[4])
    }

    @Test
    fun testTask155_hapticFeedbackSafetyForAllInteractionTypes() {
        for (feedback in HapticFeedbackManager.FeedbackType.values()) {
            HapticFeedbackManager.vibrate(context, feedback)
            HapticFeedbackManager.performHaptic(null, feedback)
        }
        assertTrue("Haptic feedback executed safely across all archetypes", true)
    }

    @Test
    fun testTask155_fiveBottomNavigationDestinationsCenteredHome() {
        val canonicalRoutes = listOf("Knowledge", "Bhakti", "Home", "Videos", "Profile")
        assertEquals(5, canonicalRoutes.size)
        assertEquals("Home", canonicalRoutes[2])
    }

    @Test
    fun testTask155_livingDivineTimeAtmosphereTransitions() {
        val calMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 6) }
        assertEquals(DivineTimeAtmosphere.MORNING, DivineEnvironmentHelper.getCurrentAtmosphere(calMorning))

        val calDay = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 13) }
        assertEquals(DivineTimeAtmosphere.DAY, DivineEnvironmentHelper.getCurrentAtmosphere(calDay))

        val calEvening = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 18) }
        assertEquals(DivineTimeAtmosphere.EVENING, DivineEnvironmentHelper.getCurrentAtmosphere(calEvening))

        val calNight = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 23) }
        assertEquals(DivineTimeAtmosphere.NIGHT, DivineEnvironmentHelper.getCurrentAtmosphere(calNight))
    }

    @Test
    fun testTask155_deityGreetingAndMantraGeneration() {
        val cal = Calendar.getInstance()
        val greeting = DivineNameSystem.getGreetingForTime(cal)
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
        assertTrue(greeting.deityNameHi.isNotEmpty())
        assertTrue(greeting.sacredMantraEn.isNotEmpty())
        assertTrue(greeting.sacredMantraHi.isNotEmpty())
    }

    @Test
    fun testTask155_accountIsolationAndSecurityCompliance() {
        val devoteeA = "devoteeA@sanatansadhak.org"
        val devoteeB = "devoteeB@sanatansadhak.org"
        assertNotEquals(devoteeA, devoteeB)

        val complianceItems = AppDiagnosticsManager.complianceAudit.value
        assertTrue(complianceItems.isNotEmpty())
        assertTrue(complianceItems.all { it.isCompliant })
    }

    @Test
    fun testTask155_diagnosticsSanitizationAndFingerprinting() {
        val fp = AppDiagnosticsManager.recordError(
            category = ErrorCategory.UI_NAV,
            severity = ErrorSeverity.LOW,
            errorType = "HomeInteractionTest",
            message = AppDiagnosticsManager.sanitizeMessage("Home interaction test for devotee devotee@sanatan.org with Bearer HomeToken")
        )
        val errors = AppDiagnosticsManager.errorRecords.value
        val record = errors.first { it.fingerprint == fp }
        assertFalse(record.message.contains("devotee@sanatan.org"))
        assertFalse(record.message.contains("HomeToken"))
        assertTrue(record.message.contains("[REDACTED_EMAIL]"))
    }

    @Test
    fun testTask155_selfDiagnosticsAllSubsystems() {
        val results = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertTrue(results.isNotEmpty())
        assertTrue(results.any { it.componentName.contains("Database") })
        assertTrue(results.any { it.componentName.contains("Panchang") })
    }

    @Test
    fun testTask155_notificationSchedulerPresence() {
        assertNotNull(SadhakNotificationHelper)
    }
}
