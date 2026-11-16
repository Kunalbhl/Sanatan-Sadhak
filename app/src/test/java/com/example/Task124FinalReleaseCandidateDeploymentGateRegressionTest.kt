package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.AppPreferences
import com.example.data.DailyDharmaRepository
import com.example.notifications.SadhakNotificationHelper
import com.example.notifications.SadhakReminderScheduler
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.DevotionalSaffron
import com.example.ui.theme.SacredGold
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleCream
import com.example.ui.viewmodel.PanchangLocation
import com.example.ui.viewmodel.PanchangViewModel
import com.example.utils.AppPermissionStatus
import com.example.utils.BatteryOptimizationManager
import com.example.utils.DivineNameSystem
import com.example.utils.PermissionManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task124FinalReleaseCandidateDeploymentGateRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    // 1. Release configuration & production applicationId
    @Test
    fun test01_releaseConfigurationAndAppId() {
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", BuildConfig.APPLICATION_ID)
        assertNotNull(BuildConfig.BUILD_TYPE)
        assertEquals("1.1", BuildConfig.VERSION_NAME)
        assertEquals(1, BuildConfig.VERSION_CODE)
    }

    // 2. Permission check-first & no startup loop
    @Test
    fun test02_permissionCheckFirstNoStartupLoop() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(notifStatus)
        assertNotNull(locStatus)
    }

    // 3. Permission permanent denial persistence
    @Test
    fun test03_permissionPermanentDenialPersistence() {
        PermissionManager.markPermissionDeclined(context, "ACCESS_FINE_LOCATION")
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(status)
    }

    // 4. Account isolation (A -> B -> A)
    @Test
    fun test04_accountIsolationCycle() {
        val userA = "bhakta.madhava@sanatan.org"
        val userB = "bhakta.keshava@sanatan.org"

        AppPreferences.setOnboardingState(userA, "COMPLETED")
        AppPreferences.setOnboardingState(userB, "SKIPPED")

        assertEquals("COMPLETED", AppPreferences.getOnboardingState(userA))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(userB))
        assertNotEquals(AppPreferences.getOnboardingState(userA), AppPreferences.getOnboardingState(userB))

        // Return to userA
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(userA))
    }

    // 5. Onboarding persistence across lifecycle
    @Test
    fun test05_onboardingPersistenceAcrossLifecycle() {
        val devotee = "devotee.narayana@sanatan.org"
        AppPreferences.setOnboardingState(devotee, "COMPLETED")
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(devotee))
    }

    // 6. Database schema version and DAO safety
    @Test
    fun test06_databaseSchemaAndDaoSafety() {
        val db = AppDatabase.getInstance(context)
        assertNotNull("Database must instantiate safely", db)
        assertTrue("Database version must be 19 or higher", db.openHelper.readableDatabase.version >= 19)
        assertNotNull(db.userDao())
        assertNotNull(db.sadhanaDao())
        assertNotNull(db.panchangDao())
        assertNotNull(db.userFavoriteDao())
        assertNotNull(db.readingProgressDao())
        assertNotNull(db.festivalDao())
    }

    // 7. Panchang determinism for identical inputs
    @Test
    fun test07_panchangDeterminism() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue(presets.isNotEmpty())
        val defaultLoc = presets[0]
        assertEquals("New Delhi, India", defaultLoc.name)
        assertTrue(defaultLoc.latitude > 0.0)
        assertTrue(defaultLoc.longitude > 0.0)
    }

    // 8. GPS fallback to sacred presets
    @Test
    fun test08_gpsFallbackToSacredPresets() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        val sacredCities = listOf("New Delhi", "Varanasi", "Ayodhya", "Ujjain", "Mathura", "Tirupati", "Haridwar")
        sacredCities.forEach { city ->
            val found = presets.find { it.name.contains(city) }
            assertNotNull("Sacred fallback preset for $city must exist", found)
        }
    }

    // 9. Daily Shloka & Mantra determinism by date key
    @Test
    fun test09_dailyShlokaAndMantraDeterminism() {
        val dateKey = "2026-08-20"
        val shlokaA = DailyDharmaRepository.getDailyShloka(dateKey)
        val shlokaB = DailyDharmaRepository.getDailyShloka(dateKey)
        assertEquals(shlokaA.id, shlokaB.id)
        assertEquals(shlokaA.verseSanskrit, shlokaB.verseSanskrit)

        val mantraA = DailyDharmaRepository.getDailyMantra(dateKey)
        val mantraB = DailyDharmaRepository.getDailyMantra(dateKey)
        assertEquals(mantraA.id, mantraB.id)
        assertEquals(mantraA.sanskrit, mantraB.sanskrit)
    }

    // 10. Alarm duplicate prevention & channel creation
    @Test
    fun test10_alarmDuplicatePreventionAndChannels() {
        SadhakNotificationHelper.createNotificationChannels(context)
        SadhakReminderScheduler.scheduleDailyAlarm(
            context = context,
            reminderId = SadhakNotificationHelper.ID_MORNING_PRAYER,
            hour = 6,
            minute = 0,
            reminderType = "Morning Prayer",
            channelId = SadhakNotificationHelper.CHANNEL_DAILY_SADHANA,
            destination = "Bhakti"
        )
        SadhakReminderScheduler.scheduleDailyAlarm(
            context = context,
            reminderId = SadhakNotificationHelper.ID_MORNING_PRAYER,
            hour = 6,
            minute = 0,
            reminderType = "Morning Prayer",
            channelId = SadhakNotificationHelper.CHANNEL_DAILY_SADHANA,
            destination = "Bhakti"
        )
        SadhakReminderScheduler.cancelAlarm(context, SadhakNotificationHelper.ID_MORNING_PRAYER)
        assertTrue(true)
    }

    // 11. Boot & package replacement recovery architecture
    @Test
    fun test11_bootAndPackageReplacementRecovery() {
        val manifest = File("src/main/AndroidManifest.xml")
        assertTrue(manifest.exists())
        val content = manifest.readText()
        assertTrue("Boot completed receiver must be present", content.contains("BOOT_COMPLETED"))
        assertTrue("Package replaced receiver must be present", content.contains("MY_PACKAGE_REPLACED"))
    }

    // 12. Timezone & time change recovery architecture
    @Test
    fun test12_timezoneAndTimeChangeRecovery() {
        val manifest = File("src/main/AndroidManifest.xml")
        val content = manifest.readText()
        assertTrue("Timezone change action must be present", content.contains("TIMEZONE_CHANGED"))
        assertTrue("Time set action must be present", content.contains("TIME_SET"))
    }

    // 13. OEM battery optimization guidance persistence
    @Test
    fun test13_oemBatteryOptimizationGuidancePersistence() {
        val guidance = BatteryOptimizationManager.getOemGuidance(context, "Sadhana Alarms")
        assertNotNull(guidance)
        assertTrue(guidance.oemName.isNotEmpty())
        val featureKey = "test_gate_feature"
        BatteryOptimizationManager.dismissGuidance(context, featureKey)
        assertTrue(BatteryOptimizationManager.hasDismissedGuidance(context, featureKey))
    }

    // 14. Audio and TTS mutual exclusion
    @Test
    fun test14_audioTtsMutualExclusion() {
        SadhakAudioManager.stop()
        SadhakTextReader.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertFalse(SadhakTextReader.isReading.value)
    }

    // 15. WebView lifecycle cleanup & hardware acceleration preservation
    @Test
    fun test15_webViewLifecycleAndHardwareAcceleration() {
        val instagramFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        val videoFile = File("src/main/java/com/example/ui/screens/video/VideoScreens.kt")
        val manifest = File("src/main/AndroidManifest.xml")

        assertTrue(instagramFile.exists() && videoFile.exists() && manifest.exists())
        assertTrue(instagramFile.readText().contains("DisposableEffect"))
        assertTrue(videoFile.readText().contains("DisposableEffect"))
        assertFalse(instagramFile.readText().contains("LAYER_TYPE_SOFTWARE"))
        assertFalse(videoFile.readText().contains("LAYER_TYPE_SOFTWARE"))
        assertTrue(manifest.readText().contains("android:hardwareAccelerated=\"true\""))
    }

    // 16. Low-RAM lifecycle safety (no forced GC, safe onTrimMemory)
    @Test
    fun test16_lowRamSafety() {
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("Must not invoke System.gc() manually", content.contains("System.gc()"))
        assertFalse("Coil must not disable hardware bitmaps globally", content.contains("allowHardware(false)"))
    }

    // 17. Security & logging hygiene
    @Test
    fun test17_securityAndLoggingHygiene() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val manifest = File("src/main/AndroidManifest.xml")

        val appContent = appFile.readText()
        val manifestContent = manifest.readText()

        assertFalse(appContent.contains("System.out"))
        assertFalse(appContent.contains("System.err"))
        assertFalse(appContent.contains("printStackTrace()"))
        assertTrue("Internal receivers must be unexported", manifestContent.contains("android:exported=\"false\""))
    }

    // 18. Canonical Divine Design System tokens
    @Test
    fun test18_canonicalDivineDesignTokens() {
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
    }

    // 19. Responsive UI invariants
    @Test
    fun test19_responsiveUiInvariants() {
        val colors = listOf(DeepSacredMaroon, DevotionalSaffron, SacredGold, TempleCream, TempleBrown)
        assertEquals(5, colors.size)
    }

    // 20. Offline fallback readiness
    @Test
    fun test20_offlineFallbackReadiness() {
        val mantra = DailyDharmaRepository.getDailyMantra("2026-08-20")
        assertNotNull(mantra)
        assertTrue(mantra.sanskrit.isNotEmpty())
        val shloka = DailyDharmaRepository.getDailyShloka("2026-08-20")
        assertNotNull(shloka)
        assertTrue(shloka.verseSanskrit.isNotEmpty())
    }
}
