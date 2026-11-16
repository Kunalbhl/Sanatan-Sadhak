package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.DailyDharmaRepository
import com.example.data.SadhanaRankCalculator
import com.example.data.SadhanaRepository
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
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
class Task113FinalProductionHardeningRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    @Test
    fun test01_verifyCanonicalDivineThemeTokensAndMasterPalette() {
        // Master light palette
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        // Master dark palette
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
        assertEquals(Color(0xFF261710), TempleDarkSurface)

        // SadhakCanonicalTokens baseline
        assertEquals(TempleCream, SadhakCanonicalTokens.TempleCream)
        assertEquals(DeepSacredMaroon, SadhakCanonicalTokens.DeepSacredMaroon)
        assertEquals(SacredGold, SadhakCanonicalTokens.SacredGold)
        assertEquals(TempleBrown, SadhakCanonicalTokens.TempleBrown)
    }

    @Test
    fun test02_verifyAuthenticationAndOnboardingStateIsolation() {
        val userA = "devotee_a@sanatan.org"
        val userB = "devotee_b@sanatan.org"

        // User A completes onboarding
        AppPreferences.setOnboardingState(userA, "COMPLETED")
        assertTrue("User A onboarding must be completed", AppPreferences.hasUserCompletedOnboarding(userA))

        // User B has not completed onboarding
        assertFalse("User B onboarding must not be completed initially", AppPreferences.hasUserCompletedOnboarding(userB))

        // User B skips onboarding
        AppPreferences.setOnboardingState(userB, "SKIPPED")
        assertTrue("User B onboarding must be completed when skipped", AppPreferences.hasUserCompletedOnboarding(userB))

        // Verify User A state persists after User B action
        assertTrue("User A onboarding completion must remain intact", AppPreferences.hasUserCompletedOnboarding(userA))
    }

    @Test
    fun test03_verifyPermissionManagerPersistenceAndNonIntrusiveStatus() {
        // Notification permission check
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must be non-null", notifStatus)
        assertTrue("Status must be valid enum", notifStatus in AppPermissionStatus.values())

        // Mark notification permission declined
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val updatedNotifStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, updatedNotifStatus)

        // Location permission check
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("Location permission status must be non-null", locStatus)
        assertTrue("Location status must be valid enum", locStatus in AppPermissionStatus.values())
    }

    @Test
    fun test04_verifyCanonicalSadhanaRanksAndThresholds() {
        // Jigyasu (0 - 499)
        val rank0 = SadhanaRankCalculator.getLevelForScore(0)
        assertEquals("Jigyasu", rank0.titleEn)
        assertEquals("जिज्ञासु", rank0.titleHi)

        // Sadhak (500 - 1499)
        val rank1 = SadhanaRankCalculator.getLevelForScore(500)
        assertEquals("Sadhak", rank1.titleEn)
        assertEquals("साधक", rank1.titleHi)

        // Bhakt (1500 - 2999)
        val rank2 = SadhanaRankCalculator.getLevelForScore(1500)
        assertEquals("Bhakt", rank2.titleEn)
        assertEquals("भक्त", rank2.titleHi)

        // Karmayogi (3000 - 5999)
        val rank3 = SadhanaRankCalculator.getLevelForScore(3000)
        assertEquals("Karmayogi", rank3.titleEn)
        assertEquals("कर्मयोगी", rank3.titleHi)

        // Dhyanastha (6000 - 9999)
        val rank4 = SadhanaRankCalculator.getLevelForScore(6000)
        assertEquals("Dhyanastha", rank4.titleEn)
        assertEquals("ध्यानस्थ", rank4.titleHi)

        // Atmajnani (10000+)
        val rank5 = SadhanaRankCalculator.getLevelForScore(10000)
        assertEquals("Atmajnani", rank5.titleEn)
        assertEquals("आत्मज्ञानी", rank5.titleHi)
    }

    @Test
    fun test05_verifyBadgeCatalogCompletenessAndCategories() {
        val repo = SadhanaRepository.getInstance(context)
        val badges = repo.allBadgesCatalog
        assertTrue("Badge catalog must contain at least 12 canonical badges", badges.size >= 12)

        val categories = badges.map { it.category }.toSet()
        assertTrue("Catalog must contain Streak category", "Streak" in categories)
        assertTrue("Catalog must contain Japa category", "Japa" in categories)
        assertTrue("Catalog must contain Meditation category", "Meditation" in categories)
        assertTrue("Catalog must contain Knowledge category", "Knowledge" in categories)
        assertTrue("Catalog must contain Mantra category", "Mantra" in categories)
    }

    @Test
    fun test06_verifyDailyShlokaAndMantraDeterminismForGivenDate() {
        val dateKey = "2026-08-19"

        val shloka1 = DailyDharmaRepository.getDailyShloka(dateKey)
        val shloka2 = DailyDharmaRepository.getDailyShloka(dateKey)

        assertEquals("Same date key must yield identical Shloka Sanskrit", shloka1.verseSanskrit, shloka2.verseSanskrit)
        assertEquals("Same date key must yield identical Shloka English Meaning", shloka1.meaningEn, shloka2.meaningEn)

        val mantra1 = DailyDharmaRepository.getDailyMantra(dateKey)
        val mantra2 = DailyDharmaRepository.getDailyMantra(dateKey)

        assertEquals("Same date key must yield identical Mantra Sanskrit", mantra1.sanskrit, mantra2.sanskrit)
    }

    @Test
    fun test07_verifyAudioAndTtsMutualExclusionEngine() {
        // Stop TTS reader
        SadhakTextReader.stop()
        assertFalse("SadhakTextReader must not be reading after stop", SadhakTextReader.isReading.value)

        // Starting audio track calls SadhakTextReader.stop()
        SadhakAudioManager.playTrack(context, AudioTrackId.OM_AMBIENT)
        assertFalse("SadhakTextReader must remain stopped when playing audio track", SadhakTextReader.isReading.value)

        // Stopping audio manager resets state
        SadhakAudioManager.stop()
        assertFalse("Audio manager must not be playing after stop", SadhakAudioManager.isPlaying.value)

        // Cleanup
        SadhakTextReader.stop()
        SadhakAudioManager.stop()
    }

    @Test
    fun test08_verifyPreferenceSettingsPersistence() {
        // Verify theme preference setter
        AppPreferences.setThemePreference(2)
        assertEquals(2, AppPreferences.themePreference.value)

        AppPreferences.setThemePreference(1)
        assertEquals(1, AppPreferences.themePreference.value)

        // Verify language toggle maintains state
        val initialLang = AppPreferences.isEnglish.value
        AppPreferences.toggleLanguage()
        assertEquals(!initialLang, AppPreferences.isEnglish.value)
    }

    @Test
    fun test09_verifyHindiDevanagariGreetingsAndNameSystem() {
        val cal = Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, Calendar.MONDAY) }
        val greeting = DivineNameSystem.getGreetingForTime(cal)

        assertNotNull("Monday Deity Name HI must exist", greeting.deityNameHi)
        assertNotNull("Monday Sacred Mantra HI must exist", greeting.sacredMantraHi)
        assertTrue("Mantra HI must not be blank", greeting.sacredMantraHi.isNotBlank())
    }

    @Test
    fun test10_verifyGlobalDesignSystemTypographyAndColors() {
        assertNotNull("Brand title style must exist", SadhakThemeTypography.brandTitle)
        assertNotNull("Section heading style must exist", SadhakThemeTypography.sectionHeading)
        assertNotNull("Body text style must exist", SadhakThemeTypography.bodyText)
        assertNotNull("Sanskrit verse style must exist", SadhakThemeTypography.sanskritVerse)

        assertEquals(DeepSacredMaroon, SadhakThemeTypography.brandTitle.color)
    }
}
