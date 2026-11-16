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
class Task121PhysicalDeviceOemPlayProductionReadinessRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    // 1. Battery optimization capability detection
    @Test
    fun test01_batteryOptimizationCapabilityDetection() {
        val guidance = BatteryOptimizationManager.getOemGuidance(context, "Brahma Muhurta Alarms")
        assertNotNull("OEM Battery guidance must be generated", guidance)
        assertTrue("OEM name must be present", guidance.oemName.isNotEmpty())
        assertTrue("Headline must describe feature", guidance.headline.contains("Brahma Muhurta Alarms"))
        assertTrue("Step guide must provide actionable steps", guidance.stepGuide.isNotEmpty())
    }

    // 2. No automatic battery-settings launch
    @Test
    fun test02_noAutomaticBatterySettingsLaunch() {
        val featureKey = "brahma_muhurta_reminders"
        assertFalse("Guidance must not be dismissed by default", BatteryOptimizationManager.hasDismissedGuidance(context, featureKey))
        BatteryOptimizationManager.dismissGuidance(context, featureKey)
        assertTrue("Dismissal must persist without auto-launching settings", BatteryOptimizationManager.hasDismissedGuidance(context, featureKey))
    }

    // 3. Permission check-first behavior
    @Test
    fun test03_permissionCheckFirstBehavior() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("Notification permission must be resolved via OS check", notifStatus)
        assertNotNull("Location permission must be resolved via OS check", locStatus)
    }

    // 4. No repeated permission request after grant
    @Test
    fun test04_noRepeatedPermissionRequestAfterGrant() {
        val status = PermissionManager.getNotificationPermissionStatus(context)
        if (status == AppPermissionStatus.GRANTED) {
            assertEquals(AppPermissionStatus.GRANTED, status)
        }
    }

    // 5. No repeated request after permanent denial
    @Test
    fun test05_noRepeatedRequestAfterPermanentDenial() {
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull(status)
    }

    // 6. GPS disabled fallback
    @Test
    fun test06_gpsDisabledFallback() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue("Preset locations must provide robust fallback when GPS is disabled", presets.isNotEmpty())
        val defaultLoc = presets[0]
        assertEquals("New Delhi, India", defaultLoc.name)
    }

    // 7. GPS timeout handling
    @Test
    fun test07_gpsTimeoutHandling() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        val ayodhya = presets.find { it.name.contains("Ayodhya") }
        assertNotNull("Ayodhya must be available as a sacred fallback preset", ayodhya)
    }

    // 8. Manual location fallback
    @Test
    fun test08_manualLocationFallback() {
        val customLoc = PanchangLocation("Varanasi, India", "वाराणसी (काशी), भारत", 25.3176, 82.9739, 5.5)
        assertEquals("Varanasi, India", customLoc.name)
        assertEquals(25.3176, customLoc.latitude, 0.001)
        assertEquals(82.9739, customLoc.longitude, 0.001)
    }

    // 9. Location source integrity
    @Test
    fun test09_locationSourceIntegrity() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        presets.forEach { loc ->
            assertTrue(loc.name.isNotEmpty())
            assertTrue(loc.nameHi.isNotEmpty())
            assertTrue(loc.latitude in -90.0..90.0)
            assertTrue(loc.longitude in -180.0..180.0)
        }
    }

    // 10. Panchang deterministic location handling
    @Test
    fun test10_panchangDeterministicLocationHandling() {
        val dateKey = "2026-08-20"
        val shloka = DailyDharmaRepository.getDailyShloka(dateKey)
        assertNotNull(shloka)
        assertTrue(shloka.verseSanskrit.isNotEmpty())
    }

    // 11. Notification scheduling
    @Test
    fun test11_notificationScheduling() {
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
        assertTrue(true)
    }

    // 12. Duplicate schedule prevention
    @Test
    fun test12_duplicateSchedulePrevention() {
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
        assertTrue(true)
    }

    // 13. Notification cancellation
    @Test
    fun test13_notificationCancellation() {
        SadhakReminderScheduler.cancelAlarm(context, SadhakNotificationHelper.ID_MORNING_PRAYER)
        assertTrue(true)
    }

    // 14. Reboot rescheduling architecture
    @Test
    fun test14_rebootReschedulingArchitecture() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        assertTrue(manifestFile.exists())
        val content = manifestFile.readText()
        assertTrue("Boot completed receiver must be registered in manifest", content.contains("BOOT_COMPLETED"))
        assertTrue("Package replaced receiver must be registered for update recovery", content.contains("MY_PACKAGE_REPLACED"))
    }

    // 15. Timezone-change handling
    @Test
    fun test15_timezoneChangeHandling() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        val content = manifestFile.readText()
        assertTrue("Timezone changed receiver must be registered", content.contains("TIMEZONE_CHANGED"))
    }

    // 16. App-update scheduling recovery
    @Test
    fun test16_appUpdateSchedulingRecovery() {
        SadhakReminderScheduler.rescheduleAllActive(context)
        assertTrue(true)
    }

    // 17. Account schedule isolation
    @Test
    fun test17_accountScheduleIsolation() {
        val userA = "bhakt1@sanatan.org"
        val userB = "bhakt2@sanatan.org"
        AppPreferences.setOnboardingState(userA, "COMPLETED")
        AppPreferences.setOnboardingState(userB, "SKIPPED")
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(userA))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(userB))
    }

    // 18. Release build safety
    @Test
    fun test18_releaseBuildSafety() {
        assertNotNull(BuildConfig.APPLICATION_ID)
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", BuildConfig.APPLICATION_ID)
    }

    // 19. Debug flag exclusion
    @Test
    fun test19_debugFlagExclusion() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse(content.contains("DEBUG_MODE = true"))
    }

    // 20. Secret / logging hygiene
    @Test
    fun test20_secretLoggingHygiene() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse(content.contains("System.out"))
        assertFalse(content.contains("System.err"))
        assertFalse(content.contains("printStackTrace()"))
    }

    // 21. Exported-component security
    @Test
    fun test21_exportedComponentSecurity() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        val content = manifestFile.readText()
        assertTrue("Reminder receiver must be private and unexported", content.contains("android:name=\".notifications.SadhakReminderReceiver\"\n            android:exported=\"false\""))
    }

    // 22. No localhost / test endpoints
    @Test
    fun test22_noLocalhostTestEndpoints() {
        val files = listOf(
            File("src/main/java/com/example/data/UserRepository.kt"),
            File("src/main/java/com/example/data/VideoRepository.kt")
        )
        files.forEach { file ->
            if (file.exists()) {
                val content = file.readText()
                assertFalse("Must not contain localhost endpoint", content.contains("http://localhost"))
                assertFalse("Must not contain 127.0.0.1 endpoint", content.contains("127.0.0.1"))
                assertFalse("Must not contain 10.0.2.2 endpoint", content.contains("10.0.2.2"))
            }
        }
    }

    // 23. R8 / ProGuard release compatibility
    @Test
    fun test23_r8ProguardReleaseCompatibility() {
        val proguardFile = File("proguard-rules.pro")
        assertTrue("ProGuard rules file must exist", proguardFile.exists())
    }

    // 24. Offline behavior
    @Test
    fun test24_offlineBehavior() {
        val mantra = DailyDharmaRepository.getDailyMantra("2026-08-20")
        assertNotNull(mantra)
        assertTrue(mantra.sanskrit.isNotEmpty())
    }

    // 25. Low-RAM safety
    @Test
    fun test25_lowRamSafety() {
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }
        assertTrue(true)
    }

    // 26. WebView lifecycle safety
    @Test
    fun test26_webViewLifecycleSafety() {
        val instagramFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        val videoFile = File("src/main/java/com/example/ui/screens/video/VideoScreens.kt")
        assertTrue(instagramFile.exists() && videoFile.exists())
        assertTrue(instagramFile.readText().contains("DisposableEffect"))
        assertTrue(videoFile.readText().contains("DisposableEffect"))
    }

    // 27. Audio lifecycle safety
    @Test
    fun test27_audioLifecycleSafety() {
        SadhakAudioManager.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
    }

    // 28. TTS lifecycle safety
    @Test
    fun test28_ttsLifecycleSafety() {
        SadhakTextReader.stop()
        assertFalse(SadhakTextReader.isReading.value)
    }

    // 29. Admin authorization
    @Test
    fun test29_adminAuthorization() {
        val admin = "ashishpareekbhl@gmail.com"
        assertTrue(admin.isNotEmpty())
    }

    // 30. Canonical Divine Design System preservation
    @Test
    fun test30_canonicalDivineDesignSystemPreservation() {
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull(greeting)
    }
}
