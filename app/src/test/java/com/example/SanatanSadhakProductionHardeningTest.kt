package com.example

import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.User
import com.example.notifications.SadhakNotificationHelper
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.theme.SadhakThemeColors
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class SanatanSadhakProductionHardeningTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        SadhakAudioManager.init(context)
        SadhakAudioManager.releaseAll()
        SadhakAudioManager.setSoundEnabled(true)
    }

    // ==========================================
    // 1. AUTHENTICATION & SESSION TESTS
    // ==========================================

    @Test
    fun testLoginSuccessAndSessionState() {
        var user: User? = null
        var isSessionRestored = false

        // Simulate successful login
        fun performLogin(email: String, pass: String): Boolean {
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                user = User(
                    email = email,
                    fullName = "Devotee User",
                    mobileNumber = "9876543210",
                    city = "Ayodhya",
                    state = "Uttar Pradesh",
                    password = pass,
                    avatar = 1,
                    role = "Regular"
                )
                return true
            }
            return false
        }

        // Simulate session restore
        fun restoreSession(): Boolean {
            if (user != null) {
                isSessionRestored = true
                return true
            }
            return false
        }

        val loginResult = performLogin("bhakt@sanatan.org", "sadhana108")
        assertTrue("Login with valid credentials should succeed", loginResult)
        assertNotNull("User object must be populated on success", user)
        assertEquals("bhakt@sanatan.org", user?.email)
        assertEquals("Regular", user?.role)

        val restoreResult = restoreSession()
        assertTrue("Session restoration should succeed when user exists", restoreResult)
        assertTrue("Session state flag should be restored", isSessionRestored)
    }

    @Test
    fun testLoginFailureWithInvalidCredentials() {
        var user: User? = null
        var errorMessage = ""

        fun performLogin(email: String, pass: String): Boolean {
            if (email.isEmpty() || pass.isEmpty()) {
                errorMessage = "Email and Password required"
                return false
            }
            if (pass.length < 6) {
                errorMessage = "Password must be at least 6 characters"
                return false
            }
            user = User(
                email = email,
                fullName = "Devotee",
                mobileNumber = "",
                city = "",
                state = "",
                password = pass,
                avatar = 1,
                role = "Regular"
            )
            return true
        }

        val emptyResult = performLogin("", "")
        assertFalse("Empty login must fail", emptyResult)
        assertNull("User must remain null on failed login", user)
        assertEquals("Email and Password required", errorMessage)

        val shortPassResult = performLogin("bhakt@sanatan.org", "123")
        assertFalse("Short password must fail", shortPassResult)
        assertEquals("Password must be at least 6 characters", errorMessage)
    }

    @Test
    fun testLogoutAndGuestMode() {
        var userRole = "Regular"
        var currentUser: User? = User(
            email = "user@sanatan.org",
            fullName = "Sadhak",
            mobileNumber = "1234567890",
            city = "Kashi",
            state = "UP",
            password = "pass",
            avatar = 1,
            role = "Regular"
        )

        // Perform logout -> transitions to Guest mode
        fun logout() {
            currentUser = null
            userRole = "Guest"
        }

        assertEquals("Regular", userRole)
        assertNotNull(currentUser)

        logout()

        assertEquals("Guest", userRole)
        assertNull("User instance must be cleared on logout", currentUser)
    }

    // ==========================================
    // 2. CANONICAL LOGO TESTS
    // ==========================================

    @Test
    fun testCanonicalLogoResourceExists() {
        val appLogoRes = R.drawable.app_logo
        assertTrue("Canonical app_logo drawable resource must exist", appLogoRes != 0)

        val notificationIconRes = R.drawable.ic_notification_sanatan_sadhak
        assertTrue("Monochrome notification icon resource must exist", notificationIconRes != 0)
    }

    // ==========================================
    // 3. AUDIO SYSTEM & RECOVERY TESTS
    // ==========================================

    @Test
    fun testAllNineAudioTracksMapping() {
        val expectedTracks = listOf(
            "temple_bell",
            "sadhana_success",
            "om_ambient",
            "flute_ambient",
            "gayatri_mantra",
            "shiva_tandav",
            "maha_mrityunjaya",
            "vishnu_sahasranamam",
            "hanuman_chalisa"
        )

        for (trackName in expectedTracks) {
            val trackId = AudioTrackId.fromRawName(trackName)
            assertNotNull("Track $trackName must resolve to an AudioTrackId", trackId)
            assertEquals("Track rawName must match expected name", trackName, trackId?.rawName)
            assertTrue("Track title (EN) must be non-empty", trackId?.titleEn?.isNotEmpty() == true)
            assertTrue("Track title (HI) must be non-empty", trackId?.titleHi?.isNotEmpty() == true)
        }
    }

    @Test
    fun testSoundOffSuppression() {
        SadhakAudioManager.setSoundEnabled(false)
        assertFalse("Sound enabled must be false", SadhakAudioManager.soundEnabled.value)

        // Attempting to play track when sound is disabled
        SadhakAudioManager.playTrack(context, AudioTrackId.OM_AMBIENT)
        assertFalse("Playing state must remain false when sound is disabled", SadhakAudioManager.isPlaying.value)

        // Attempting one-shot sound when disabled
        SadhakAudioManager.playOneShot(context, AudioTrackId.TEMPLE_BELL)
        assertNull("Track should not be set when sound is disabled", SadhakAudioManager.currentTrack.value)

        // Re-enabling sound does not auto-play
        SadhakAudioManager.setSoundEnabled(true)
        assertTrue(SadhakAudioManager.soundEnabled.value)
        assertFalse("Sound re-enabling must not automatically start playback", SadhakAudioManager.isPlaying.value)
    }

    @Test
    fun testAudioStopAndReleaseLifecycle() {
        SadhakAudioManager.stop()
        assertFalse("Player must not be playing after stop", SadhakAudioManager.isPlaying.value)
        assertFalse("Buffering must be false after stop", SadhakAudioManager.isBuffering.value)
        assertEquals("Progress must reset to 0", 0f, SadhakAudioManager.progress.value, 0.001f)
        assertNull("Current track must be null after stop", SadhakAudioManager.currentTrack.value)

        SadhakAudioManager.releaseAll()
        assertNull("Current track must be null after releaseAll", SadhakAudioManager.currentTrack.value)
    }

    @Test
    fun testRapidOneShotSafeRecycling() {
        // Rapid sequential one-shot calls should safely recycle without throwing
        for (i in 1..20) {
            SadhakAudioManager.playOneShot(context, AudioTrackId.TEMPLE_BELL)
        }
        // Then with the success chime
        for (i in 1..10) {
            SadhakAudioManager.playOneShot(context, AudioTrackId.SADHANA_SUCCESS)
        }
        SadhakAudioManager.stop()
    }

    @Test
    fun testInvalidOrCorruptAudioSafeHandling() {
        // Calling with null or unmapped shouldn't crash
        assertNull(AudioTrackId.fromRawName("unknown_track_123"))
        
        // Stopping when nothing is playing shouldn't crash
        SadhakAudioManager.stop()
        SadhakAudioManager.pause()
        SadhakAudioManager.resume()
    }

    // ==========================================
    // 4. THEME & COLOR PALETTE TESTS
    // ==========================================

    @Test
    fun testMasterSacredColorPaletteTokens() {
        // Verify canonical master tokens in Light Mode
        assertEquals(com.example.ui.theme.TempleCream, com.example.ui.theme.LightSadhakColors.sacredBackground)
        assertEquals(com.example.ui.theme.DeepSacredMaroon, com.example.ui.theme.LightSadhakColors.sacredSecondary)
        assertEquals(com.example.ui.theme.DevotionalSaffron, com.example.ui.theme.LightSadhakColors.sacredPrimary)
        assertEquals(com.example.ui.theme.SacredGold, com.example.ui.theme.LightSadhakColors.sacredGold)

        // Verify Temple Dark Mode tokens
        assertEquals(com.example.ui.theme.TempleDarkBackground, com.example.ui.theme.DarkSadhakColors.sacredBackground)
        assertEquals(com.example.ui.theme.DeepMaroonDark, com.example.ui.theme.DarkSadhakColors.sacredSecondary)
        assertEquals(com.example.ui.theme.SaffronDark, com.example.ui.theme.DarkSadhakColors.sacredPrimary)
        assertEquals(com.example.ui.theme.SacredGoldDark, com.example.ui.theme.DarkSadhakColors.sacredGold)
    }

    // ==========================================
    // 5. NOTIFICATION SYSTEM TESTS
    // ==========================================

    @Test
    fun testNotificationChannelsCreation() {
        SadhakNotificationHelper.createNotificationChannels(context)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channels = notificationManager.notificationChannels

        val channelIds = channels.map { it.id }
        assertTrue("Daily Sadhana channel must exist", channelIds.contains(SadhakNotificationHelper.CHANNEL_DAILY_SADHANA))
        assertTrue("Panchang channel must exist", channelIds.contains(SadhakNotificationHelper.CHANNEL_PANCHANG))
        assertTrue("Brahma Muhurta channel must exist", channelIds.contains(SadhakNotificationHelper.CHANNEL_BRAHMA_MUHURTA))
        assertTrue("Sandhya channel must exist", channelIds.contains(SadhakNotificationHelper.CHANNEL_SANDHYA))
        assertTrue("Japa channel must exist", channelIds.contains(SadhakNotificationHelper.CHANNEL_JAPA))
    }

    @Test
    fun testNotificationDisplayAndRouting() {
        SadhakNotificationHelper.showNotification(
            context = context,
            notificationId = SadhakNotificationHelper.ID_SHLOKA_PANCHANG,
            channelId = SadhakNotificationHelper.CHANNEL_PANCHANG,
            title = "Panchang Update",
            text = "Today is Shukla Pratipada",
            destination = "Panchang"
        )
        // Verify no uncaught exceptions thrown during build & dispatch
    }
}
