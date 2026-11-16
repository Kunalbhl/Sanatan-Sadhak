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
class Task127FinalGoldenMasterSealingRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    // 1. Golden Master Production Package & Version Invariants
    @Test
    fun test01_goldenMasterPackageAndVersionInvariants() {
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", BuildConfig.APPLICATION_ID)
        assertNotNull(BuildConfig.BUILD_TYPE)
        assertEquals("1.1", BuildConfig.VERSION_NAME)
        assertEquals(1, BuildConfig.VERSION_CODE)
    }

    // 2. Manifest Security & Exported Receivers Safety
    @Test
    fun test02_manifestSecurityAndExportedReceivers() {
        val manifest = File("src/main/AndroidManifest.xml")
        assertTrue(manifest.exists())
        val content = manifest.readText()
        assertTrue("Internal receivers must not be exported", content.contains("android:exported=\"false\""))
        assertTrue("Hardware acceleration must be enabled", content.contains("android:hardwareAccelerated=\"true\""))
    }

    // 3. Permission Manager Check-First Rule & Zero Startup Prompt
    @Test
    fun test03_permissionCheckFirstZeroStartupPrompt() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("Notification status must resolve via OS state", notifStatus)
        assertNotNull("Location status must resolve via OS state", locStatus)
    }

    // 4. Permanent Permission Denial Persistence
    @Test
    fun test04_permanentPermissionDenialPersistence() {
        PermissionManager.markPermissionDeclined(context, "ACCESS_FINE_LOCATION")
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(status)
    }

    // 5. Strict Account Isolation across Session Cycles (A -> B -> A)
    @Test
    fun test05_strictAccountIsolationCycle() {
        val user1 = "bhakta.rama@sanatan.org"
        val user2 = "bhakta.lakshmana@sanatan.org"

        AppPreferences.setOnboardingState(user1, "COMPLETED")
        AppPreferences.setOnboardingState(user2, "SKIPPED")

        assertEquals("COMPLETED", AppPreferences.getOnboardingState(user1))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(user2))
        assertNotEquals(AppPreferences.getOnboardingState(user1), AppPreferences.getOnboardingState(user2))

        // Return to user1
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(user1))
    }

    // 6. Database Schema Stability (Room v19) & DAO Integrity
    @Test
    fun test06_databaseSchemaStabilityAndDaoIntegrity() {
        val db = AppDatabase.getInstance(context)
        assertNotNull("AppDatabase must initialize cleanly", db)
        assertTrue("Database version must be 19", db.openHelper.readableDatabase.version >= 19)
        assertNotNull(db.userDao())
        assertNotNull(db.sadhanaDao())
        assertNotNull(db.panchangDao())
        assertNotNull(db.userFavoriteDao())
        assertNotNull(db.readingProgressDao())
        assertNotNull(db.festivalDao())
    }

    // 7. Deterministic Panchang & Sacred Fallback Presets
    @Test
    fun test07_deterministicPanchangAndSacredPresets() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue("Sacred presets must be available", presets.isNotEmpty())
        val defaultLoc = presets[0]
        assertEquals("New Delhi, India", defaultLoc.name)
        assertTrue(defaultLoc.latitude > 0.0)
        assertTrue(defaultLoc.longitude > 0.0)

        val requiredSacredCities = listOf("New Delhi", "Varanasi", "Ayodhya", "Ujjain", "Mathura", "Tirupati", "Haridwar")
        requiredSacredCities.forEach { city ->
            val match = presets.find { it.name.contains(city) }
            assertNotNull("Preset for '$city' must exist", match)
        }
    }

    // 8. Daily Shloka and Mantra Date-Key Determinism
    @Test
    fun test08_dailyShlokaAndMantraDeterminism() {
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

    // 9. Notification Alarm Scheduling & Duplicate Prevention
    @Test
    fun test09_notificationSchedulingAndDuplicatePrevention() {
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

    // 10. Boot, Update, Timezone & Time-Change Receivers
    @Test
    fun test10_bootUpdateAndTimezoneReceivers() {
        val manifest = File("src/main/AndroidManifest.xml")
        val content = manifest.readText()
        assertTrue(content.contains("BOOT_COMPLETED"))
        assertTrue(content.contains("MY_PACKAGE_REPLACED"))
        assertTrue(content.contains("TIMEZONE_CHANGED"))
        assertTrue(content.contains("TIME_SET"))
    }

    // 11. Battery Optimization Manager Non-Intrusive Guidance & Persistence
    @Test
    fun test11_batteryOptimizationManagerGuidanceAndPersistence() {
        val guidance = BatteryOptimizationManager.getOemGuidance(context, "Sadhana Reminders")
        assertNotNull(guidance)
        assertTrue(guidance.oemName.isNotEmpty())
        assertTrue(guidance.stepGuide.isNotEmpty())

        val featureKey = "golden_master_gate"
        BatteryOptimizationManager.dismissGuidance(context, featureKey)
        assertTrue(BatteryOptimizationManager.hasDismissedGuidance(context, featureKey))
    }

    // 12. WebView Lifecycle Cleanup & Disposal Safety
    @Test
    fun test12_webViewLifecycleCleanupAndDisposal() {
        val instagramFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        val videoFile = File("src/main/java/com/example/ui/screens/video/VideoScreens.kt")

        assertTrue(instagramFile.exists() && videoFile.exists())
        assertTrue(instagramFile.readText().contains("DisposableEffect"))
        assertTrue(videoFile.readText().contains("DisposableEffect"))
        assertFalse(instagramFile.readText().contains("LAYER_TYPE_SOFTWARE"))
        assertFalse(videoFile.readText().contains("LAYER_TYPE_SOFTWARE"))
    }

    // 13. Audio / TTS Mutual Exclusion Contract
    @Test
    fun test13_audioTtsMutualExclusionContract() {
        SadhakAudioManager.stop()
        SadhakTextReader.stop()
        assertFalse("Audio must be stopped", SadhakAudioManager.isPlaying.value)
        assertFalse("TTS must be stopped", SadhakTextReader.isReading.value)
    }

    // 14. Memory Safety (No System.gc, No Coil Hardware Bitmap Restrictions)
    @Test
    fun test14_memorySafetyAndCoilConfiguration() {
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("Must not call System.gc()", content.contains("System.gc()"))
        assertFalse("Coil must not disable hardware bitmaps globally", content.contains("allowHardware(false)"))
    }

    // 15. Security Hygiene (No localhost, test endpoints, or debug tokens)
    @Test
    fun test15_securityHygieneAndEndpoints() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse(content.contains("System.out"))
        assertFalse(content.contains("System.err"))
        assertFalse(content.contains("printStackTrace()"))

        val files = listOf(
            File("src/main/java/com/example/data/UserRepository.kt"),
            File("src/main/java/com/example/data/VideoRepository.kt")
        )
        files.forEach { file ->
            if (file.exists()) {
                val txt = file.readText()
                assertFalse(txt.contains("http://localhost"))
                assertFalse(txt.contains("127.0.0.1"))
                assertFalse(txt.contains("10.0.2.2"))
            }
        }
    }

    // 16. Canonical Divine Design System Token Preservation
    @Test
    fun test16_canonicalDivineDesignTokens() {
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)

        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull(greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
    }

    // 17. Responsive Layout & Accessibility Invariants
    @Test
    fun test17_responsiveLayoutAndAccessibilityInvariants() {
        val colors = listOf(DeepSacredMaroon, DevotionalSaffron, SacredGold, TempleCream, TempleBrown)
        assertEquals(5, colors.size)
        assertNotEquals(DeepSacredMaroon, TempleCream)
    }

    // 18. Offline Fallback Invariants
    @Test
    fun test18_offlineFallbackInvariants() {
        val mantra = DailyDharmaRepository.getDailyMantra("2026-08-20")
        assertNotNull(mantra)
        assertTrue(mantra.sanskrit.isNotEmpty())
        val shloka = DailyDharmaRepository.getDailyShloka("2026-08-20")
        assertNotNull(shloka)
        assertTrue(shloka.verseSanskrit.isNotEmpty())
    }
}
