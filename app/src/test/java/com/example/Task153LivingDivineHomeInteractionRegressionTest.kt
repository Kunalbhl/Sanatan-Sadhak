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
class Task153LivingDivineHomeInteractionRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testLivingDivine_timeAtmosphereCalculations() {
        val calMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 7) }
        assertEquals(DivineTimeAtmosphere.MORNING, DivineEnvironmentHelper.getCurrentAtmosphere(calMorning))

        val calDay = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 14) }
        assertEquals(DivineTimeAtmosphere.DAY, DivineEnvironmentHelper.getCurrentAtmosphere(calDay))

        val calEvening = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 18) }
        assertEquals(DivineTimeAtmosphere.EVENING, DivineEnvironmentHelper.getCurrentAtmosphere(calEvening))

        val calNight = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 22) }
        assertEquals(DivineTimeAtmosphere.NIGHT, DivineEnvironmentHelper.getCurrentAtmosphere(calNight))

        val calEarlyMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 3) }
        assertEquals(DivineTimeAtmosphere.NIGHT, DivineEnvironmentHelper.getCurrentAtmosphere(calEarlyMorning))
    }

    @Test
    fun testLivingDivine_hapticFeedbackManagerSafety() {
        // Verify all feedback types can be dispatched safely without crashing on JVM / Robolectric
        for (type in HapticFeedbackManager.FeedbackType.values()) {
            HapticFeedbackManager.vibrate(context, type)
            HapticFeedbackManager.performHaptic(null, type)
        }
        assertTrue(true)
    }

    @Test
    fun testLivingDivine_deityGreetingGeneration() {
        val cal = Calendar.getInstance()
        val greeting = DivineNameSystem.getGreetingForTime(cal)
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
        assertTrue(greeting.deityNameHi.isNotEmpty())
        assertTrue(greeting.sacredMantraEn.isNotEmpty())
        assertTrue(greeting.sacredMantraHi.isNotEmpty())
    }

    @Test
    fun testLivingDivine_canonicalColorTokensAndDimensions() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
    }

    @Test
    fun testLivingDivine_devoteeIsolationAndDiagnosticsIntegrity() {
        AppDiagnosticsManager.recordError(
            category = ErrorCategory.UI_NAV,
            severity = ErrorSeverity.LOW,
            errorType = "InteractionTest",
            message = "Living divine interaction test"
        )
        val activeErrors = AppDiagnosticsManager.errorRecords.value
        assertTrue(activeErrors.isNotEmpty())
        AppDiagnosticsManager.clearAllErrors()
        assertTrue(AppDiagnosticsManager.errorRecords.value.isEmpty())
    }
}
