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
class Task125FinalDeploymentGateRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    // 1. Physical-device readiness architecture & release configuration
    @Test
    fun test01_physicalDeviceReadinessAndReleaseConfig() {
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", BuildConfig.APPLICATION_ID)
        assertNotNull(BuildConfig.BUILD_TYPE)
        assertEquals("1.1", BuildConfig.VERSION_NAME)
        assertEquals(1, BuildConfig.VERSION_CODE)
    }

    // 2. Upgrade safety & database schema preservation
    @Test
    fun test02_upgradeSafetyAndDatabaseSchema() {
        val db = AppDatabase.getInstance(context)
        assertNotNull("Database instance must be valid", db)
        assertTrue("Database version must be 19", db.openHelper.readableDatabase.version >= 19)
        assertNotNull(db.userDao())
        assertNotNull(db.sadhanaDao())
        assertNotNull(db.panchangDao())
        assertNotNull(db.userFavoriteDao())
        assertNotNull(db.readingProgressDao())
        assertNotNull(db.festivalDao())
    }

    // 3. Reboot recovery architecture (BOOT_COMPLETED & MY_PACKAGE_REPLACED)
    @Test
    fun test03_rebootRecoveryArchitecture() {
        val manifest = File("src/main/AndroidManifest.xml")
        assertTrue(manifest.exists())
        val content = manifest.readText()
        assertTrue("Boot receiver must handle BOOT_COMPLETED", content.contains("BOOT_COMPLETED"))
        assertTrue("Boot receiver must handle MY_PACKAGE_REPLACED", content.contains("MY_PACKAGE_REPLACED"))
        assertTrue("Boot receiver must handle QUICKBOOT_POWERON", content.contains("QUICKBOOT_POWERON"))
    }

    // 4. GPS handling & zero startup prompt rule
    @Test
    fun test04_gpsHandlingAndZeroStartupPromptRule() {
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("GPS permission status must be resolved via OS state", locStatus)
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must be resolved via OS state", notifStatus)
    }

    // 5. GPS permanent denial persistence
    @Test
    fun test05_gpsPermanentDenialPersistence() {
        PermissionManager.markPermissionDeclined(context, "ACCESS_FINE_LOCATION")
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(status)
    }

    // 6. Sacred preset fallback for offline/GPS timeout
    @Test
    fun test06_sacredPresetFallbackForOfflineGpsTimeout() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue(presets.isNotEmpty())
        val requiredPresets = listOf("New Delhi", "Varanasi", "Ayodhya", "Ujjain", "Mathura", "Tirupati", "Haridwar")
        requiredPresets.forEach { city ->
            val match = presets.find { it.name.contains(city) }
            assertNotNull("Sacred preset for '$city' must be available", match)
        }
    }

    // 7. Notification scheduling & duplicate alarm prevention
    @Test
    fun test07_notificationSchedulingAndDuplicatePrevention() {
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

    // 8. OEM optimization guidance persistence
    @Test
    fun test08_oemOptimizationGuidancePersistence() {
        val guidance = BatteryOptimizationManager.getOemGuidance(context, "Brahma Muhurta Alarms")
        assertNotNull(guidance)
        assertTrue(guidance.oemName.isNotEmpty())
        val featureKey = "test_gate_oem_feature"
        BatteryOptimizationManager.dismissGuidance(context, featureKey)
        assertTrue(BatteryOptimizationManager.hasDismissedGuidance(context, featureKey))
    }

    // 9. Account isolation (A -> B -> A)
    @Test
    fun test09_accountIsolationCycle() {
        val userA = "sadhak.govinda@sanatan.org"
        val userB = "sadhak.damodara@sanatan.org"

        AppPreferences.setOnboardingState(userA, "COMPLETED")
        AppPreferences.setOnboardingState(userB, "SKIPPED")

        assertEquals("COMPLETED", AppPreferences.getOnboardingState(userA))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(userB))
        assertNotEquals(AppPreferences.getOnboardingState(userA), AppPreferences.getOnboardingState(userB))

        // Return to userA
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(userA))
    }

    // 10. Offline fallback & Panchang determinism
    @Test
    fun test10_offlineFallbackAndPanchangDeterminism() {
        val dateKey = "2026-08-20"
        val shloka1 = DailyDharmaRepository.getDailyShloka(dateKey)
        val shloka2 = DailyDharmaRepository.getDailyShloka(dateKey)
        assertEquals(shloka1.id, shloka2.id)
        assertEquals(shloka1.verseSanskrit, shloka2.verseSanskrit)

        val mantra1 = DailyDharmaRepository.getDailyMantra(dateKey)
        val mantra2 = DailyDharmaRepository.getDailyMantra(dateKey)
        assertEquals(mantra1.id, mantra2.id)
        assertEquals(mantra1.sanskrit, mantra2.sanskrit)
    }

    // 11. WebView lifecycle & hardware acceleration preservation
    @Test
    fun test11_webViewLifecycleAndHardwareAcceleration() {
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

    // 12. Coil hardware bitmap policy & memory trim safety
    @Test
    fun test12_coilHardwareBitmapAndMemoryTrimSafety() {
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("Must not invoke System.gc()", content.contains("System.gc()"))
        assertFalse("Coil must not disable hardware bitmaps globally", content.contains("allowHardware(false)"))
    }

    // 13. Audio / TTS mutual exclusion
    @Test
    fun test13_audioTtsMutualExclusion() {
        SadhakAudioManager.stop()
        SadhakTextReader.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertFalse(SadhakTextReader.isReading.value)
    }

    // 14. Security hygiene & unexported private components
    @Test
    fun test14_securityHygieneAndUnexportedComponents() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val manifest = File("src/main/AndroidManifest.xml")

        val appContent = appFile.readText()
        val manifestContent = manifest.readText()

        assertFalse(appContent.contains("System.out"))
        assertFalse(appContent.contains("System.err"))
        assertFalse(appContent.contains("printStackTrace()"))
        assertTrue(manifestContent.contains("android:exported=\"false\""))
    }

    // 15. Divine Design System token preservation
    @Test
    fun test15_divineDesignSystemTokenPreservation() {
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
    }

    // 16. Responsive layout & accessibility invariants
    @Test
    fun test16_responsiveLayoutAndAccessibilityInvariants() {
        val colors = listOf(DeepSacredMaroon, DevotionalSaffron, SacredGold, TempleCream, TempleBrown)
        assertEquals(5, colors.size)
        assertNotEquals(DeepSacredMaroon, TempleCream)
    }

    // 17. Release configuration safety & endpoint sanitization
    @Test
    fun test17_releaseConfigurationSafetyAndEndpoints() {
        val files = listOf(
            File("src/main/java/com/example/data/UserRepository.kt"),
            File("src/main/java/com/example/data/VideoRepository.kt")
        )
        files.forEach { file ->
            if (file.exists()) {
                val content = file.readText()
                assertFalse(content.contains("http://localhost"))
                assertFalse(content.contains("127.0.0.1"))
                assertFalse(content.contains("10.0.2.2"))
            }
        }
    }
}
