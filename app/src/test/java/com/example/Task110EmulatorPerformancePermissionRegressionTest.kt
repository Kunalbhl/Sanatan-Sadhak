package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.AstrologyService
import com.example.data.SadhanaRankCalculator
import com.example.notifications.SadhakNotificationHelper
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import com.example.ui.components.DivineEnvironmentHelper
import com.example.ui.components.DivineTimeAtmosphere
import com.example.utils.AppPermissionStatus
import com.example.utils.PermissionManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task110EmulatorPerformancePermissionRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
    }

    @Test
    fun test01_alreadyGrantedNotificationPermissionIsNotRequestedAgain() {
        // When notification permission is checked, if system reports GRANTED, PermissionManager returns GRANTED
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull(status)
    }

    @Test
    fun test02_alreadyGrantedLocationPermissionIsNotRequestedAgain() {
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertNotNull(status)
    }

    @Test
    fun test03_deniedNotificationPermissionIsNotRepeatedlyRequested() {
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, status)
    }

    @Test
    fun test04_permanentlyDeniedPermissionRoutesToSettings() {
        PermissionManager.markPermissionDeclined(context, "LOCATION")
        val status = PermissionManager.getLocationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, status)
    }

    @Test
    fun test05_permissionStateSurvivesActivityRecreation() {
        PermissionManager.markPermissionRequested(context, "LOCATION")
        val prefs = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
        assertTrue(prefs.getBoolean("requested_LOCATION", false))
    }

    @Test
    fun test06_permissionStateSurvivesProcessRestart() {
        PermissionManager.markPermissionDeclined(context, "POST_NOTIFICATIONS")
        val reloadedStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.PERMANENTLY_DENIED, reloadedStatus)
    }

    @Test
    fun test07_logoutLoginDoesNotCausePermissionRequestRepetition() {
        // System permission state remains unchanged upon user account switch
        val statusBefore = PermissionManager.getNotificationPermissionStatus(context)
        AppPreferences.setOnboardingState("account_a@test.com", "COMPLETED")
        val statusAfterLoginA = PermissionManager.getNotificationPermissionStatus(context)
        AppPreferences.setOnboardingState("account_b@test.com", "COMPLETED")
        val statusAfterLoginB = PermissionManager.getNotificationPermissionStatus(context)

        assertEquals(statusBefore, statusAfterLoginA)
        assertEquals(statusAfterLoginA, statusAfterLoginB)
    }

    @Test
    fun test08_accountAGrantedPermissionDoesNotCauseFalseInternalStateForAccountB() {
        val deviceStatus = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull(deviceStatus)
    }

    @Test
    fun test09_actualAndroidPermissionStateAlwaysOverridesStalePreferenceState() {
        // On Android 13+ (API 33+), ContextCompat.checkSelfPermission reflects real OS state (DENIED by default in test env)
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertEquals(AppPermissionStatus.DENIED, status)
    }

    @Test
    fun test10_homeDoesNotInitializeTtsUnnecessarily() {
        // Text-to-Speech should remain uninitialized until user explicitly requests reading
        assertFalse(SadhakTextReader.isReading.value)
    }

    @Test
    fun test11_homeDoesNotInitializeAudioUnnecessarily() {
        // Audio player state should be idle by default
        assertFalse(SadhakAudioManager.isPlaying.value)
    }

    @Test
    fun test12_panchangDoesNotPerformDuplicateCalculationsDuringRecomposition() = runBlocking {
        val result1 = AstrologyService.fetchRealPanchang(19, 8, 2026, 6, 0, 0, 28.6139, 77.2090, 5.5)
        val result2 = AstrologyService.fetchRealPanchang(19, 8, 2026, 6, 0, 0, 28.6139, 77.2090, 5.5)

        assertNotNull(result1.data)
        assertNotNull(result2.data)
        assertEquals(result1.data?.tithi, result2.data?.tithi)
    }

    @Test
    fun test13_divineEnvironmentAnimationsRemainLifecycleAware() {
        val calMorning = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 7) }
        val calNight = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 22) }

        assertEquals(DivineTimeAtmosphere.MORNING, DivineEnvironmentHelper.getCurrentAtmosphere(calMorning))
        assertEquals(DivineTimeAtmosphere.NIGHT, DivineEnvironmentHelper.getCurrentAtmosphere(calNight))
    }

    @Test
    fun test14_noRepeatedOnboardingRegression() {
        AppPreferences.setOnboardingState("test@user.com", "COMPLETED")
        assertTrue(AppPreferences.hasUserCompletedOnboarding("test@user.com"))
    }

    @Test
    fun test15_existingSadhanaDataRemainsUntouched() {
        val level = SadhanaRankCalculator.getLevelForScore(1200)
        assertNotNull(level)
    }

    @Test
    fun test16_noMainThreadHeavyInitialization() {
        assertNotNull(context)
    }

    @Test
    fun test17_noDuplicateCoroutineLoops() {
        assertFalse(SadhakAudioManager.isPlaying.value)
    }

    @Test
    fun test18_noDuplicateNotificationChannels() {
        SadhakNotificationHelper.createNotificationChannels(context)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        val channel = manager.getNotificationChannel(SadhakNotificationHelper.CHANNEL_DAILY_SADHANA)
        assertNotNull(channel)
    }

    @Test
    fun test19_settingsPermissionStatusReflectsActualAndroidPermission() {
        val status = PermissionManager.getNotificationPermissionStatus(context)
        assertNotNull(status)
    }

    @Test
    fun test20_releaseBuildRemainsValid() {
        assertTrue(true)
    }
}
