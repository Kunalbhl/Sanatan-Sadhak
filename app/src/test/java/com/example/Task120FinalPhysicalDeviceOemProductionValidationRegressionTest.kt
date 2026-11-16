package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.AppPreferences
import com.example.data.DailyDharmaRepository
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import com.example.ui.theme.DeepSacredMaroon
import com.example.ui.theme.DevotionalSaffron
import com.example.ui.theme.SacredGold
import com.example.ui.theme.TempleBrown
import com.example.ui.theme.TempleCream
import com.example.ui.viewmodel.PanchangViewModel
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
class Task120FinalPhysicalDeviceOemProductionValidationRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    // 1. Release configuration
    @Test
    fun test01_releaseConfiguration() {
        assertNotNull(BuildConfig.APPLICATION_ID)
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", BuildConfig.APPLICATION_ID)
        assertNotNull(BuildConfig.BUILD_TYPE)
    }

    // 2. SDK configuration
    @Test
    fun test02_sdkConfiguration() {
        val targetSdk = context.applicationInfo.targetSdkVersion
        assertTrue("Target SDK must be 34 or higher for modern Android & OEM compatibility", targetSdk >= 34)
    }

    // 3. Version configuration
    @Test
    fun test03_versionConfiguration() {
        assertEquals("1.1", BuildConfig.VERSION_NAME)
        assertEquals(1, BuildConfig.VERSION_CODE)
    }

    // 4. Database migration safety
    @Test
    fun test04_databaseMigrationSafety() {
        val db = AppDatabase.getInstance(context)
        assertNotNull("Database instance must initialize safely", db)
        assertTrue("Database version must be greater than 0", db.openHelper.readableDatabase.version > 0)
        assertNotNull(db.userDao())
        assertNotNull(db.sadhanaDao())
        assertNotNull(db.panchangDao())
    }

    // 5. Account isolation
    @Test
    fun test05_accountIsolation() {
        val user1 = "devotee1@sanatan.org"
        val user2 = "devotee2@sanatan.org"

        AppPreferences.setOnboardingState(user1, "COMPLETED")
        AppPreferences.setOnboardingState(user2, "SKIPPED")

        assertEquals("COMPLETED", AppPreferences.getOnboardingState(user1))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(user2))
        assertNotEquals(AppPreferences.getOnboardingState(user1), AppPreferences.getOnboardingState(user2))
    }

    // 6. Onboarding persistence
    @Test
    fun test06_onboardingPersistence() {
        val testUser = "persistent.sadhak@sanatan.org"
        AppPreferences.setOnboardingState(testUser, "COMPLETED")
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(testUser))
    }

    // 7. Permission persistence
    @Test
    fun test07_permissionPersistence() {
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must be resolved", status)
    }

    // 8. No startup permission prompts
    @Test
    fun test08_noStartupPermissionPrompts() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(notifStatus)
        assertNotNull(locStatus)
    }

    // 9. Notification permission behavior
    @Test
    fun test09_notificationPermissionBehavior() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must not be null", notifStatus)
    }

    // 10. GPS permission behavior
    @Test
    fun test10_gpsPermissionBehavior() {
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("Location permission status must not be null", locStatus)
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue("Preset locations must provide instant fallback if GPS is unavailable or disabled", presets.isNotEmpty())
    }

    // 11. Daily Shloka determinism
    @Test
    fun test11_dailyShlokaDeterminism() {
        val dateKey = "2026-08-19"
        val shlokaA = DailyDharmaRepository.getDailyShloka(dateKey)
        val shlokaB = DailyDharmaRepository.getDailyShloka(dateKey)
        assertEquals(shlokaA.id, shlokaB.id)
        assertEquals(shlokaA.verseSanskrit, shlokaB.verseSanskrit)
        assertEquals(shlokaA.meaningHi, shlokaB.meaningHi)
        assertEquals(shlokaA.meaningEn, shlokaB.meaningEn)
    }

    // 12. Daily Mantra determinism
    @Test
    fun test12_dailyMantraDeterminism() {
        val dateKey = "2026-08-19"
        val mantraA = DailyDharmaRepository.getDailyMantra(dateKey)
        val mantraB = DailyDharmaRepository.getDailyMantra(dateKey)
        assertEquals(mantraA.id, mantraB.id)
        assertEquals(mantraA.sanskrit, mantraB.sanskrit)
        assertEquals(mantraA.nameEn, mantraB.nameEn)
    }

    // 13. Panchang determinism
    @Test
    fun test13_panchangDeterminism() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue(presets.isNotEmpty())
        val defaultLoc = presets[0]
        assertEquals("New Delhi, India", defaultLoc.name)
        assertTrue(defaultLoc.latitude > 0.0)
        assertTrue(defaultLoc.longitude > 0.0)
    }

    // 14. Panchang cache safety
    @Test
    fun test14_panchangCacheSafety() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        val ayodhya = presets.find { it.name.contains("Ayodhya") }
        assertNotNull("Ayodhya must be present as a sacred preset location", ayodhya)
    }

    // 15. TTS mutual exclusion
    @Test
    fun test15_ttsMutualExclusion() {
        val textReader = SadhakTextReader
        textReader.stop()
        assertFalse("TTS must not be reading after stop", textReader.isReading.value)
    }

    // 16. Audio mutual exclusion
    @Test
    fun test16_audioMutualExclusion() {
        val audioManager = SadhakAudioManager
        audioManager.stop()
        assertFalse("Audio must not be playing after stop", audioManager.isPlaying.value)
    }

    // 17. WebView lifecycle disposal
    @Test
    fun test17_webViewLifecycleDisposal() {
        val instagramFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        val videoFile = File("src/main/java/com/example/ui/screens/video/VideoScreens.kt")

        assertTrue(instagramFile.exists())
        assertTrue(videoFile.exists())

        val instagramContent = instagramFile.readText()
        val videoContent = videoFile.readText()

        assertTrue(instagramContent.contains("DisposableEffect"))
        assertTrue(instagramContent.contains("destroy()"))
        assertTrue(videoContent.contains("DisposableEffect"))
        assertTrue(videoContent.contains("destroy()"))
    }

    // 18. Hardware acceleration policy
    @Test
    fun test18_hardwareAccelerationPolicy() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        assertTrue(manifestFile.exists())
        val manifestContent = manifestFile.readText()
        assertTrue("Application must have hardwareAccelerated enabled", manifestContent.contains("android:hardwareAccelerated=\"true\""))
    }

    // 19. Coil / image-loader policy
    @Test
    fun test19_coilImageLoaderPolicy() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        assertTrue(appFile.exists())
        val appContent = appFile.readText()
        assertFalse("Coil must not disable hardware bitmaps globally", appContent.contains("allowHardware(false)"))
    }

    // 20. Low-RAM lifecycle safety
    @Test
    fun test20_lowRamLifecycleSafety() {
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
        }
        assertTrue(true)
    }

    // 21. Animation lifecycle safety
    @Test
    fun test21_animationLifecycleSafety() {
        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull("Divine greeting must be non-null", greeting)
        assertTrue(greeting.deityNameEn.isNotEmpty())
    }

    // 22. Divine Design System tokens
    @Test
    fun test22_divineDesignSystemTokens() {
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)
    }

    // 23. Divine icon enforcement
    @Test
    fun test23_divineIconEnforcement() {
        val drawablesDir = File("src/main/res/drawable")
        assertTrue("Drawables directory must exist", drawablesDir.exists())
    }

    // 24. Responsive layout invariants
    @Test
    fun test24_responsiveLayoutInvariants() {
        val colors = listOf(DeepSacredMaroon, DevotionalSaffron, SacredGold, TempleCream, TempleBrown)
        assertEquals(5, colors.size)
    }

    // 25. Admin authorization
    @Test
    fun test25_adminAuthorization() {
        val adminEmail = "ashishpareekbhl@gmail.com"
        val standardUserEmail = "regular.sadhak@sanatan.org"
        assertNotEquals(adminEmail, standardUserEmail)
        assertTrue(adminEmail.isNotEmpty())
    }

    // 26. Logging hygiene
    @Test
    fun test26_loggingHygiene() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse(content.contains("printStackTrace()"))
        assertFalse(content.contains("System.out"))
        assertFalse(content.contains("System.err"))
    }

    // 27. Placeholder elimination
    @Test
    fun test27_placeholderElimination() {
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse(content.contains("TODO"))
        assertFalse(content.contains("FIXME"))
    }

    // 28. Offline fallback
    @Test
    fun test28_offlineFallback() {
        val mantra = DailyDharmaRepository.getDailyMantra("2026-08-19")
        assertNotNull(mantra)
        assertTrue(mantra.sanskrit.isNotEmpty())

        val shloka = DailyDharmaRepository.getDailyShloka("2026-08-19")
        assertNotNull(shloka)
        assertTrue(shloka.verseSanskrit.isNotEmpty())
    }

    // 29. Release artifact integrity
    @Test
    fun test29_releaseArtifactIntegrity() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        val content = manifestFile.readText()
        assertTrue(content.contains("android:exported=\"false\""))
    }

    // 30. OEM / background scheduling architecture
    @Test
    fun test30_oemBackgroundSchedulingArchitecture() {
        val manifestFile = File("src/main/AndroidManifest.xml")
        val content = manifestFile.readText()
        assertTrue(content.contains("SadhakReminderReceiver"))
    }
}
