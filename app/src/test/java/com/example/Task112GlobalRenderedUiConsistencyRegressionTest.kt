package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.SadhanaRankCalculator
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.DevotionalSaffron
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SadhakCanonicalTokens
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeTypography
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleCream
import com.example.ui.theme.TempleDarkBackground
import com.example.ui.theme.TempleDarkSurface
import com.example.utils.AppPermissionStatus
import com.example.utils.DivineNameSystem
import com.example.utils.PermissionManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task112GlobalRenderedUiConsistencyRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
    }

    @Test
    fun test01_verifyCanonicalThemeColorsAndTokensConsistency() {
        // Master light palette validation
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        // Master dark palette validation
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
        assertEquals(Color(0xFF261710), TempleDarkSurface)

        // SadhakCanonicalTokens source-of-truth validation
        assertEquals(TempleCream, SadhakCanonicalTokens.TempleCream)
        assertEquals(DeepSacredMaroon, SadhakCanonicalTokens.DeepSacredMaroon)
        assertEquals(SacredGold, SadhakCanonicalTokens.SacredGold)
        assertEquals(TempleBrown, SadhakCanonicalTokens.TempleBrown)
    }

    @Test
    fun test02_verifyPermissionManagerNonIntrusiveCompliance() {
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must be non-null", status)
        assertTrue("Notification status must be a valid enum value", status in AppPermissionStatus.values())
    }

    @Test
    fun test03_verifyDivineNameSystemWeekdayGreetingsIntegrity() {
        val days = listOf(
            Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
            Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY
        )

        for (day in days) {
            val cal = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, day) }
            val greeting = DivineNameSystem.getGreetingForTime(cal)

            assertNotNull("Deity name EN for day $day must not be null", greeting.deityNameEn)
            assertNotNull("Deity name HI for day $day must not be null", greeting.deityNameHi)
            assertNotNull("Mantra EN for day $day must not be null", greeting.sacredMantraEn)
            assertNotNull("Mantra HI for day $day must not be null", greeting.sacredMantraHi)

            assertTrue("Mantra HI for day $day must contain text", greeting.sacredMantraHi.isNotBlank())
        }
    }

    @Test
    fun test04_verifySadhanaRankCalculatorThresholdsAndProgression() {
        // Test all 6 spiritual ranks
        val level0 = SadhanaRankCalculator.getLevelForScore(0)
        assertEquals("Jigyasu", level0.titleEn)

        val level1 = SadhanaRankCalculator.getLevelForScore(500)
        assertEquals("Sadhak", level1.titleEn)

        val level2 = SadhanaRankCalculator.getLevelForScore(1500)
        assertEquals("Bhakt", level2.titleEn)

        val level3 = SadhanaRankCalculator.getLevelForScore(3000)
        assertEquals("Karmayogi", level3.titleEn)

        val level4 = SadhanaRankCalculator.getLevelForScore(6000)
        assertEquals("Dhyanastha", level4.titleEn)

        val level5 = SadhanaRankCalculator.getLevelForScore(10000)
        assertEquals("Atmajnani", level5.titleEn)
    }

    @Test
    fun test05_verifyAppPreferencesPersistence() {
        // Theme preference
        AppPreferences.setThemePreference(2)
        assertEquals(2, AppPreferences.themePreference.value)

        AppPreferences.setThemePreference(1)
        assertEquals(1, AppPreferences.themePreference.value)

        // Language toggle
        val initialLang = AppPreferences.isEnglish.value
        AppPreferences.toggleLanguage()
        assertEquals(!initialLang, AppPreferences.isEnglish.value)
    }

    @Test
    fun test06_verifyGlobalDesignSystemTypographyConsistency() {
        assertNotNull(SadhakThemeTypography.brandTitle)
        assertNotNull(SadhakThemeTypography.sectionHeading)
        assertNotNull(SadhakThemeTypography.bodyText)
        assertNotNull(SadhakThemeTypography.sanskritVerse)
        assertEquals(DeepSacredMaroon, SadhakThemeTypography.brandTitle.color)
    }
}
