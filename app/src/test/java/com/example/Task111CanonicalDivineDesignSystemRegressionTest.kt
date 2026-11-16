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
class Task111CanonicalDivineDesignSystemRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
    }

    @Test
    fun test01_canonicalColorTokensMatchMasterPaletteSpec() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFF4A2318), TempleBrown)
    }

    @Test
    fun test02_darkThemeTokensMatchDeepTempleDarkSpec() {
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
        assertEquals(Color(0xFF261710), TempleDarkSurface)
    }

    @Test
    fun test03_typographyTokensHierarchyDefined() {
        assertNotNull(SadhakThemeTypography.brandTitle)
        assertNotNull(SadhakThemeTypography.sectionHeading)
        assertNotNull(SadhakThemeTypography.bodyText)
        assertNotNull(SadhakThemeTypography.sanskritVerse)
        assertEquals(DeepSacredMaroon, SadhakThemeTypography.brandTitle.color)
    }

    @Test
    fun test04_canonicalTokenValuesDefined() {
        assertEquals(Color(0xFFFDF8EE), SadhakCanonicalTokens.TempleCream)
        assertEquals(Color(0xFF7A1720), SadhakCanonicalTokens.DeepSacredMaroon)
    }

    @Test
    fun test05_divineNameSystemProvidesDeterministicWeekdayGreetings() {
        val mondayCalendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.AM_PM, Calendar.AM)
            set(Calendar.MINUTE, 10)
        }
        val mondayGreeting = DivineNameSystem.getGreetingForTime(mondayCalendar)
        assertNotNull(mondayGreeting.deityNameEn)
        assertNotNull(mondayGreeting.sacredMantraEn)
        assertTrue(mondayGreeting.sacredMantraHi.contains("ॐ"))

        val tuesdayCalendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        }
        val tuesdayGreeting = DivineNameSystem.getGreetingForTime(tuesdayCalendar)
        assertTrue(tuesdayGreeting.deityNameEn.contains("Hanuman") || tuesdayGreeting.deityNameEn.contains("Bajrangbali") || tuesdayGreeting.deityNameEn.contains("Anjaneya") || tuesdayGreeting.deityNameEn.contains("Mahaveer"))
    }

    @Test
    fun test06_canonicalSpiritualRanksContainAll6Levels() {
        val rank0 = SadhanaRankCalculator.getLevelForScore(0)
        assertEquals("Jigyasu", rank0.titleEn)

        val rank1 = SadhanaRankCalculator.getLevelForScore(500)
        assertEquals("Sadhak", rank1.titleEn)

        val rank2 = SadhanaRankCalculator.getLevelForScore(1500)
        assertEquals("Bhakt", rank2.titleEn)

        val rank3 = SadhanaRankCalculator.getLevelForScore(3000)
        assertEquals("Karmayogi", rank3.titleEn)

        val rank4 = SadhanaRankCalculator.getLevelForScore(6000)
        assertEquals("Dhyanastha", rank4.titleEn)

        val rank5 = SadhanaRankCalculator.getLevelForScore(10000)
        assertEquals("Atmajnani", rank5.titleEn)
    }

    @Test
    fun test07_permissionPersistenceMaintainsStateWithoutPromptSpam() {
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, status)
    }

    @Test
    fun test08_guidedTourOnboardingStatePersistsPermanently() {
        AppPreferences.setOnboardingState("user@sanatan.org", "COMPLETED")
        assertTrue(AppPreferences.hasUserCompletedOnboarding("user@sanatan.org"))
    }

    @Test
    fun test09_guestUserDoesNotReceiveAutomaticOnboarding() {
        AppPreferences.setOnboardingState("guest@sanatan.org", "NOT_STARTED")
        assertFalse(AppPreferences.hasUserCompletedOnboarding("guest@sanatan.org"))
    }
}
