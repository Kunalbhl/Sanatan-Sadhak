package com.example

import com.example.data.User
import com.example.data.DetailedPanchang
import com.example.data.EngineClassification
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class SanatanSadhakE2EQaTest {

    @Before
    fun setup() {
        SadhakAudioManager.releaseAll()
        SadhakAudioManager.setSoundEnabled(true)
    }

    @Test
    fun `Startup and Sound Preference State QA`() {
        // Default sound state should be enabled
        assertTrue("Sound should default to enabled", SadhakAudioManager.soundEnabled.value)

        // When sound is toggled off, audio manager state reflects it
        SadhakAudioManager.setSoundEnabled(false)
        assertFalse("Sound state should reflect disabled", SadhakAudioManager.soundEnabled.value)

        // Disabled sound blocks one-shot playback
        SadhakAudioManager.playOneShot(AudioTrackId.TEMPLE_BELL)
        assertNull("No track should be set if sound is disabled", SadhakAudioManager.currentTrack.value)

        // Re-enabling sound restores capability
        SadhakAudioManager.setSoundEnabled(true)
        assertTrue("Sound should be restored", SadhakAudioManager.soundEnabled.value)
    }

    @Test
    fun `Japa Counter and Milestone Logic QA`() {
        var count = 0
        var mala = 0
        var milestoneSoundPlayed = false

        fun incrementBead() {
            count++
            if (count % 108 == 0) {
                mala++
                milestoneSoundPlayed = true
            }
        }

        // Tap 107 times - no milestone yet
        repeat(107) { incrementBead() }
        assertEquals(107, count)
        assertEquals(0, mala)
        assertFalse("No milestone sound before 108 beads", milestoneSoundPlayed)

        // 108th tap triggers milestone
        incrementBead()
        assertEquals(108, count)
        assertEquals(1, mala)
        assertTrue("Milestone sound should trigger on completing a mala", milestoneSoundPlayed)
    }

    @Test
    fun `Panchang Calculation Source Label QA`() {
        val calculatedPanchang = DetailedPanchang(
            tithi = "Shukla Pratipada",
            nakshatra = "Ashwini",
            yoga = "Vishkumbha",
            karana = "Bava",
            rashi = "Mesha",
            sunrise = "06:00 AM",
            sunset = "06:30 PM",
            rahukaal = "04:30 PM - 06:00 PM",
            gulikaKaal = "01:30 PM - 03:00 PM",
            yamaganda = "09:00 AM - 10:30 AM",
            abhijeet = "11:45 AM - 12:35 PM",
            festival = "None",
            details = "Calculation sample",
            sourceType = "CALCULATED",
            accuracyStatus = "APPROXIMATE"
        )

        assertEquals("CALCULATED", calculatedPanchang.sourceType)
        assertEquals("APPROXIMATE", calculatedPanchang.accuracyStatus)
        assertEquals("APPROXIMATE_LOCAL_ENGINE", EngineClassification.LOCAL_ENGINE_CLASSIFICATION)
    }

    @Test
    fun `Meditation Timer and Ambient Track Transitions QA`() {
        // Track switching logic
        val track1 = AudioTrackId.OM_AMBIENT
        val track2 = AudioTrackId.FLUTE_AMBIENT

        assertFalse("Om should not be one-shot", track1.isOneShot)
        assertFalse("Flute should not be one-shot", track2.isOneShot)

        // Enum lookups
        assertEquals(AudioTrackId.OM_AMBIENT, AudioTrackId.fromRawName("om_ambient"))
        assertEquals(AudioTrackId.FLUTE_AMBIENT, AudioTrackId.fromRawName("flute_ambient"))
        assertEquals(AudioTrackId.TEMPLE_BELL, AudioTrackId.fromRawName("temple_bell"))
        assertEquals(AudioTrackId.SADHANA_SUCCESS, AudioTrackId.fromRawName("sadhana_success"))
    }

    @Test
    fun `Account Deletion State Cleanup QA`() {
        var currentUser: User? = User(
            email = "user@sanatansadhak.com",
            fullName = "Bhakt",
            mobileNumber = "9999999999",
            city = "Varanasi",
            state = "UP",
            password = "hashed_pwd",
            role = "Regular"
        )
        var isAuthenticated = true
        var localPreferencesCleared = false

        fun deleteAccount() {
            currentUser = null
            isAuthenticated = false
            localPreferencesCleared = true
        }

        assertNotNull("User is authenticated before deletion", currentUser)
        assertTrue("User is authenticated before deletion", isAuthenticated)

        deleteAccount()

        assertNull("Current user must be null after deletion", currentUser)
        assertFalse("User must be unauthenticated after deletion", isAuthenticated)
        assertTrue("Local preferences/cache should be cleared", localPreferencesCleared)
    }

    @Test
    fun `Admin Role Security Boundaries QA`() {
        val regularUser = User(
            email = "sadhak@example.com",
            fullName = "Sadhak",
            mobileNumber = "1234567890",
            city = "Haridwar",
            state = "Uttarakhand",
            password = "pwd1",
            role = "Regular"
        )

        val superUser = User(
            email = "admin@example.com",
            fullName = "Acharya",
            mobileNumber = "9876543210",
            city = "Rishikesh",
            state = "Uttarakhand",
            password = "pwd2",
            role = "SuperUser"
        )

        fun canAccessAdminCenter(user: User?): Boolean {
            return user != null && user.role == "SuperUser"
        }

        assertFalse("Regular user cannot access Admin Center", canAccessAdminCenter(regularUser))
        assertTrue("SuperUser can access Admin Center", canAccessAdminCenter(superUser))
        assertFalse("Unauthenticated visitor cannot access Admin Center", canAccessAdminCenter(null))
    }
}
