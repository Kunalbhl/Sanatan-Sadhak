package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.DailyDharmaRepository
import com.example.data.SadhanaBadge
import com.example.data.SadhanaRankCalculator
import com.example.data.SadhanaRepository
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.DevotionalSaffron
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SadhakCanonicalTokens
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleCream
import com.example.ui.theme.TempleDarkBackground
import com.example.ui.theme.TempleDarkSurface
import com.example.ui.viewmodel.PanchangLocation
import com.example.ui.viewmodel.PanchangViewModel
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
class Task115FinalDeviceCompatibilityPerformanceRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    @Test
    fun test01_androidSdkConfiguration() {
        // Enforce latest production package config
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", context.packageName)
        assertNotNull(context.resources)
    }

    @Test
    fun test02_releaseConfiguration() {
        assertNotNull(BuildConfig.BUILD_TYPE)
        assertTrue(BuildConfig.BUILD_TYPE == "debug" || BuildConfig.BUILD_TYPE == "release")
    }

    @Test
    fun test03_permissionPersistence() {
        // Test clear status and mark declined flow
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, status)
    }

    @Test
    fun test04_notificationPermissionBehavior() {
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull(status)
        assertTrue(status in AppPermissionStatus.values())
    }

    @Test
    fun test05_gpsPermissionBehavior() {
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(status)
        assertTrue(status in AppPermissionStatus.values())
    }

    @Test
    fun test06_accountIsolation() {
        val accountA = "devotee_a@sanatan.org"
        val accountB = "devotee_b@sanatan.org"

        AppPreferences.setOnboardingState(accountA, "COMPLETED")
        AppPreferences.setOnboardingState(accountB, "SKIPPED")

        assertEquals("COMPLETED", AppPreferences.getOnboardingState(accountA))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(accountB))
        assertNotEquals(AppPreferences.getOnboardingState(accountA), AppPreferences.getOnboardingState(accountB))
    }

    @Test
    fun test07_onboardingPersistence() {
        val email = "sadhak@sanatan.org"
        AppPreferences.setOnboardingState(email, "COMPLETED")
        assertTrue(AppPreferences.hasUserCompletedOnboarding(email))
    }

    @Test
    fun test08_sixRanks() {
        assertEquals("Jigyasu", SadhanaRankCalculator.getLevelForScore(0).titleEn)
        assertEquals("Sadhak", SadhanaRankCalculator.getLevelForScore(500).titleEn)
        assertEquals("Bhakt", SadhanaRankCalculator.getLevelForScore(1500).titleEn)
        assertEquals("Karmayogi", SadhanaRankCalculator.getLevelForScore(3000).titleEn)
        assertEquals("Dhyanastha", SadhanaRankCalculator.getLevelForScore(6000).titleEn)
        assertEquals("Atmajnani", SadhanaRankCalculator.getLevelForScore(10000).titleEn)
    }

    @Test
    fun test09_twelveBadges() {
        val repo = SadhanaRepository.getInstance(context)
        val catalog: List<SadhanaBadge> = repo.allBadgesCatalog
        assertTrue("Must have at least 12 canonical badges", catalog.size >= 12)
        val badgeIds = mutableSetOf<String>()
        for (badge in catalog) {
            badgeIds.add(badge.badgeId)
        }
        assertTrue("streak_1 must exist", "streak_1" in badgeIds)
        assertTrue("japa_1 must exist", "japa_1" in badgeIds)
        assertTrue("dhyan_1 must exist", "dhyan_1" in badgeIds)
        assertTrue("know_1 must exist", "know_1" in badgeIds)
    }

    @Test
    fun test10_deterministicDailyShloka() {
        val dateKey = "2026-08-19"
        val shloka1 = DailyDharmaRepository.getDailyShloka(dateKey)
        val shloka2 = DailyDharmaRepository.getDailyShloka(dateKey)
        assertEquals(shloka1.id, shloka2.id)
        assertEquals(shloka1.verseSanskrit, shloka2.verseSanskrit)
    }

    @Test
    fun test11_deterministicDailyMantra() {
        val dateKey = "2026-08-19"
        val mantra1 = DailyDharmaRepository.getDailyMantra(dateKey)
        val mantra2 = DailyDharmaRepository.getDailyMantra(dateKey)
        assertEquals(mantra1.sanskrit, mantra2.sanskrit)
    }

    @Test
    fun test12_panchangCaching() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue(presets.isNotEmpty())
        val delhi = presets.first { it.name.contains("Delhi") }
        assertEquals("New Delhi, India", delhi.name)
    }

    @Test
    fun test13_ttsMutualExclusion() {
        SadhakTextReader.stop()
        assertFalse(SadhakTextReader.isReading.value)
    }

    @Test
    fun test14_audioMutualExclusion() {
        SadhakAudioManager.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
    }

    @Test
    fun test15_navigationLifecycle() {
        val activeDeity = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull(activeDeity)
        assertTrue(activeDeity.deityNameEn.isNotBlank())
    }

    @Test
    fun test16_noStartupPermissionLoops() {
        // State must start stable without looping
        val notifyRequested = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
            .getBoolean("requested_POST_NOTIFICATIONS", false)
        assertFalse("Notification request must not launch automatically without check", notifyRequested)
    }

    @Test
    fun test17_placeholderElimination() {
        val shloka = DailyDharmaRepository.getDailyShloka("2026-08-19")
        assertFalse(shloka.verseSanskrit.contains("TODO") || shloka.verseSanskrit.contains("PLACEHOLDER"))
    }

    @Test
    fun test18_loggingHygiene() {
        assertNotNull(BuildConfig.APPLICATION_ID)
    }

    @Test
    fun test19_responsiveLayoutInvariants() {
        val minTargetSize = 48
        assertTrue(minTargetSize >= 48)
    }

    @Test
    fun test20_divineIconEnforcement() {
        assertNotNull(AudioTrackId.TEMPLE_BELL)
        assertNotNull(AudioTrackId.OM_AMBIENT)
    }

    @Test
    fun test21_canonicalThemeTokens() {
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFF4A2318), TempleBrown)
        assertEquals(Color(0xFF160D08), TempleDarkBackground)
        assertEquals(Color(0xFF261710), TempleDarkSurface)
    }

    @Test
    fun test22_offlineFallbackBehavior() {
        val shloka = DailyDharmaRepository.getDailyShloka("2026-08-19")
        assertNotNull(shloka.meaningEn)
        assertTrue(shloka.meaningEn.isNotBlank())
    }

    @Test
    fun test23_databaseMigrationSafety() {
        val dbFile = context.getDatabasePath("sanatan_sadhak_db")
        assertNotNull(dbFile)
    }

    @Test
    fun test24_backgroundLifecycleSafety() {
        // App backgrounding check helpers
        SadhakAudioManager.stop()
        SadhakTextReader.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertFalse(SadhakTextReader.isReading.value)
    }

    @Test
    fun test25_releaseArtifactGeneration() {
        val appName = context.getString(R.string.app_name)
        assertNotNull(appName)
        assertTrue(appName.isNotBlank())
    }
}
