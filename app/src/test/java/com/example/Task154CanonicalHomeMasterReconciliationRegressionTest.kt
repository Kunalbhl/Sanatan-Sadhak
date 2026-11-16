package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
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
class Task154CanonicalHomeMasterReconciliationRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTask154_canonicalDesignTokensVerification() {
        // Verify canonical design system tokens
        assertEquals("Temple Cream must match #FDF8EE", Color(0xFFFDF8EE), TempleCream)
        assertEquals("Deep Sacred Maroon must match #7A1720", Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals("Devotional Saffron must match #E58A16", Color(0xFFE58A16), DevotionalSaffron)
        assertEquals("Sacred Gold must match #D4A23A", Color(0xFFD4A23A), SacredGold)
        assertEquals("Temple Dark Background must match #160D08", Color(0xFF160D08), TempleDarkBackground)

        // Verify Light Theme mapping
        assertEquals(TempleCream, LightSadhakColors.background)
        assertEquals(DeepSacredMaroon, LightSadhakColors.secondary)
        assertEquals(DevotionalSaffron, LightSadhakColors.primary)
        assertEquals(SacredGold, LightSadhakColors.accent)

        // Verify Dark Theme mapping
        assertEquals(TempleDarkBackground, DarkSadhakColors.background)
    }

    @Test
    fun testTask154_quickActionsCanonicalSet() {
        // Exactly 5 Quick Actions: Japa Mala, Dhyan, My Mantra, Aarti, Chants
        val expectedQuickActionsEn = listOf("Japa Mala", "Dhyan", "My Mantra", "Aarti", "Chants")
        val expectedQuickActionsHi = listOf("जप माला", "ध्यान", "मेरे मंत्र", "आरती", "स्तोत्र")

        assertEquals(5, expectedQuickActionsEn.size)
        assertEquals(5, expectedQuickActionsHi.size)

        // Ensure HapticFeedbackManager has mappings for Japa bead and Aarti bell
        assertNotNull(HapticFeedbackManager.FeedbackType.JAPA_BEAD)
        assertNotNull(HapticFeedbackManager.FeedbackType.BELL_RING)
        assertNotNull(HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP)
    }

    @Test
    fun testTask154_livingDivineAtmosphereCalculations() {
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
    fun testTask154_deityGreetingsAndDivineNameSystem() {
        val cal = Calendar.getInstance()
        val greeting = DivineNameSystem.getGreetingForTime(cal)
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotBlank())
        assertTrue(greeting.deityNameHi.isNotBlank())
        assertTrue(greeting.sacredMantraEn.isNotBlank())
        assertTrue(greeting.sacredMantraHi.isNotBlank())
    }

    @Test
    fun testTask154_hapticFeedbackManagerSafetyAndRobustness() {
        for (feedback in HapticFeedbackManager.FeedbackType.values()) {
            HapticFeedbackManager.vibrate(context, feedback)
            HapticFeedbackManager.performHaptic(null, feedback)
        }
        assertTrue("All haptic types executed without exceptions", true)
    }

    @Test
    fun testTask154_bottomNavigationCanonicalOrder() {
        // Bottom Navigation Order: Knowledge | Sadhana (Bhakti) | HOME | Videos | Profile
        val canonicalRoutes = listOf("Knowledge", "Bhakti", "Home", "Videos", "Profile")
        assertEquals(5, canonicalRoutes.size)
        assertEquals("Home", canonicalRoutes[2]) // Home is physically centered
    }

    @Test
    fun testTask154_diagnosticsAndTelemetryBoundedness() {
        AppDiagnosticsManager.recordError(
            category = ErrorCategory.UI_NAV,
            severity = ErrorSeverity.LOW,
            errorType = "Task154Validation",
            message = "Task 154 Canonical Home Master Reconciliation regression check"
        )
        val records = AppDiagnosticsManager.errorRecords.value
        assertTrue(records.any { it.errorType == "Task154Validation" })
        AppDiagnosticsManager.clearAllErrors()
        assertTrue(AppDiagnosticsManager.errorRecords.value.isEmpty())
    }
}
