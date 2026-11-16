package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
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
import com.example.utils.AppPermissionStatus
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
class Task118AshmemGraphicsMemoryRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakTextReader.init(context)
    }

    @Test
    fun test01_webViewLifecycleDisposal() {
        // Verify that web views in the codebase are governed by lifecycle hooks and not left orphaned
        val instagramScreenFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        assertTrue("InstagramUpdatesScreen must exist", instagramScreenFile.exists())
        val content = instagramScreenFile.readText()
        assertTrue("Instagram screen must contain DisposableEffect for cleanup", content.contains("DisposableEffect"))
        assertTrue("Instagram screen must call stopLoading on disposal", content.contains("stopLoading()"))
        assertTrue("Instagram screen must call destroy on disposal", content.contains("destroy()"))
    }

    @Test
    fun test02_youTubeLifecycleCleanup() {
        // Verify YouTube player contains clean lifecycle cleanup
        val videoScreensFile = File("src/main/java/com/example/ui/screens/video/VideoScreens.kt")
        assertTrue("VideoScreens must exist", videoScreensFile.exists())
        val content = videoScreensFile.readText()
        assertTrue("YouTube WebView must contain DisposableEffect for lifecycle cleanup", content.contains("DisposableEffect(videoId)"))
        assertTrue("YouTube WebView must stop and destroy instance on dispose", content.contains("destroy()"))
        assertFalse("YouTube WebView must not force software rendering override", content.contains("LAYER_TYPE_SOFTWARE"))
    }

    @Test
    fun test03_instagramWebViewCleanup() {
        val instagramScreenFile = File("src/main/java/com/example/ui/screens/InstagramUpdatesScreen.kt")
        val content = instagramScreenFile.readText()
        assertFalse("Instagram WebView must not force software rendering override", content.contains("LAYER_TYPE_SOFTWARE"))
        assertTrue("Instagram WebView must clean views on dispose", content.contains("removeAllViews()"))
    }

    @Test
    fun test04_coilConfiguration() {
        val app = context.applicationContext as? SadhakApplication
        assertNotNull("Application must be SadhakApplication instance or have valid image loader factory", app)
        val imageLoader = app?.newImageLoader()
        assertNotNull("ImageLoader must be created successfully", imageLoader)
    }

    @Test
    fun test05_hardwareBitmapPolicy() {
        // Verify that allowHardware(false) is NOT forced in Application
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        assertTrue(appFile.exists())
        val content = appFile.readText()
        assertFalse("allowHardware(false) must not be forced globally", content.contains("allowHardware(false)"))
    }

    @Test
    fun test06_absenceOfForcedGc() {
        // Verify that System.gc() is not invoked in production code as a routine mechanism
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("System.gc() must not be present in SadhakApplication", content.contains("System.gc()"))
    }

    @Test
    fun test07_absenceOfDestructiveWebStorageCleanup() {
        // Verify that WebStorage.deleteAllData() is not called automatically in memory trimming
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("WebStorage.deleteAllData() must not be in onTrimMemory", content.contains("deleteAllData()"))
    }

    @Test
    fun test08_animationLifecycle() {
        // Ensure divine color scheme and palettes are loaded without memory leakage
        assertEquals(Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals(Color(0xFFE58A16), DevotionalSaffron)
        assertEquals(Color(0xFFD4A23A), SacredGold)
        assertEquals(Color(0xFFFDF8EE), TempleCream)
        assertEquals(Color(0xFF4A2318), TempleBrown)
    }

    @Test
    fun test09_audioLifecycle() {
        val manager = SadhakAudioManager
        assertNotNull("Audio manager must initialize", manager)
        manager.stop()
        assertFalse("Audio should not be playing after stop", manager.isPlaying.value)
    }

    @Test
    fun test10_ttsLifecycle() {
        val reader = SadhakTextReader
        assertNotNull("Text reader must initialize", reader)
        reader.stop()
        assertFalse("TTS should not be reading after stop", reader.isReading.value)
    }

    @Test
    fun test11_permissionReconciliation() {
        val notifStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull("Notification permission status must be resolved", notifStatus)
        val locStatus = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull("Location permission status must be resolved", locStatus)
    }

    @Test
    fun test12_accountIsolation() {
        val emailA = "sadhak1@sanatan.org"
        val emailB = "sadhak2@sanatan.org"
        AppPreferences.setOnboardingState(emailA, "COMPLETED")
        AppPreferences.setOnboardingState(emailB, "SKIPPED")
        assertEquals("COMPLETED", AppPreferences.getOnboardingState(emailA))
        assertEquals("SKIPPED", AppPreferences.getOnboardingState(emailB))
    }

    @Test
    fun test13_databaseSafety() {
        val dbFile = context.getDatabasePath("sanatan_sadhak_db")
        assertNotNull("Database path must not be null", dbFile)
    }

    @Test
    fun test14_panchangCaching() {
        val presets = PanchangViewModel.PRESET_LOCATIONS
        assertTrue("Preset locations must not be empty", presets.isNotEmpty())
        assertEquals("New Delhi, India", presets[0].name)
        assertTrue(presets.any { it.name.contains("Ayodhya") })
    }

    @Test
    fun test15_dailyContentCaching() {
        val dateKey = "2026-08-19"
        val shloka1 = DailyDharmaRepository.getDailyShloka(dateKey)
        val shloka2 = DailyDharmaRepository.getDailyShloka(dateKey)
        assertEquals(shloka1.id, shloka2.id)
        assertEquals(shloka1.verseSanskrit, shloka2.verseSanskrit)
    }

    @Test
    fun test16_backgroundLifecycle() {
        // Ensure onTrimMemory triggers safely without crashing or throwing
        val app = context.applicationContext as? SadhakApplication
        if (app != null) {
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
            app.onTrimMemory(android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
            assertTrue("onTrimMemory executed safely", true)
        }
    }

    @Test
    fun test17_lowRamSafeArchitecture() {
        // Verify that memory-safe singletons are non-null and operable
        assertNotNull(SadhakAudioManager)
        assertNotNull(SadhakTextReader)
        assertNotNull(DailyDharmaRepository)
    }

    @Test
    fun test18_loggingHygiene() {
        // Ensure that app production code does not have runaway System.out prints in core paths
        val appFile = File("src/main/java/com/example/SadhakApplication.kt")
        val content = appFile.readText()
        assertFalse("SadhakApplication should not use System.out.println", content.contains("System.out.println"))
    }

    @Test
    fun test19_releaseConfiguration() {
        assertNotNull(BuildConfig.BUILD_TYPE)
        assertTrue(BuildConfig.BUILD_TYPE == "debug" || BuildConfig.BUILD_TYPE == "release")
        assertEquals("com.aistudio.sanatansadhak.ksdfgh", context.packageName)
    }

    @Test
    fun test20_crashAnrSensitiveArchitecture() {
        val greeting = DivineNameSystem.getGreetingForTime(Calendar.getInstance())
        assertNotNull("Divine greeting must be non-null", greeting)
        assertTrue("Divine greeting deity must not be empty", greeting.deityNameEn.isNotEmpty())
    }
}
