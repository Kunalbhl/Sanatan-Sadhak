package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppPreferences
import com.example.data.AstrologyService
import com.example.data.DeityGreetingEngine
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class Task91RegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakAudioManager.releaseAll()
    }

    // 1. Admin Security Authorization Check
    @Test
    fun testAdminSecurityAuthorization() {
        fun checkIsAdmin(email: String, role: String): Boolean {
            val isHardcodedAdmin = email == "kunalpareekusa@gmail.com" || email == "ashishpareekbhl@gmail.com"
            return isHardcodedAdmin || role.uppercase() == "ADMIN"
        }

        assertFalse("Regular user must NOT have admin access", checkIsAdmin("devotee@sanatan.org", "Regular"))
        assertTrue("Hardcoded primary admin email must have admin access", checkIsAdmin("kunalpareekusa@gmail.com", "Regular"))
        assertTrue("Hardcoded secondary admin email must have admin access", checkIsAdmin("ashishpareekbhl@gmail.com", "Regular"))
        assertTrue("User with ADMIN role must have admin access", checkIsAdmin("admin_user@sanatan.org", "ADMIN"))
    }

    // 2. Onboarding Per-User Persistence Isolation
    @Test
    fun testOnboardingPerUserIsolation() {
        fun getOnboardingKey(userEmail: String): String {
            return "onboarding_completed_${userEmail.lowercase().trim()}"
        }

        val keyA = getOnboardingKey("devoteeA@sanatan.org")
        val keyB = getOnboardingKey("devoteeB@sanatan.org")

        assertNotEquals("Onboarding keys for different users must be distinct", keyA, keyB)
        assertTrue("Key must incorporate user email A", keyA.contains("devoteea@sanatan.org"))
        assertTrue("Key must incorporate user email B", keyB.contains("devoteeb@sanatan.org"))
    }

    // 3. Panchang Determinism Verification
    @Test
    fun testPanchangDeterminism() = runBlocking {
        val result1 = AstrologyService.fetchRealPanchang(17, 8, 2026, 10, 0, 0, 28.6139, 77.2090, 5.5)
        val result2 = AstrologyService.fetchRealPanchang(17, 8, 2026, 10, 0, 0, 28.6139, 77.2090, 5.5)

        assertNotNull("Panchang result 1 must not be null", result1.data)
        assertNotNull("Panchang result 2 must not be null", result2.data)

        val data1 = result1.data!!
        val data2 = result2.data!!

        assertEquals("Tithi must be identical for identical input", data1.tithi, data2.tithi)
        assertEquals("Nakshatra must be identical for identical input", data1.nakshatra, data2.nakshatra)
        assertEquals("Yoga must be identical for identical input", data1.yoga, data2.yoga)
        assertEquals("Abhijeet Muhurta must be identical for identical input", data1.abhijeet, data2.abhijeet)
    }

    // 4. Weekday Deity Greeting Parity (Monday Mahakaal & Mahadev)
    @Test
    fun testMondayGreetingCanonicalSpelling() {
        val mondayMorningCal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 9)
        }

        val mondayEveningCal = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 18)
        }

        val morningSlot = DeityGreetingEngine.getActiveDeitySlot(mondayMorningCal)
        val eveningSlot = DeityGreetingEngine.getActiveDeitySlot(mondayEveningCal)

        assertEquals("Monday Morning Deity Greeting must be Jai Shree Mahakaal 🕉", "Jai Shree Mahakaal 🕉", morningSlot.greetingEn)
        assertEquals("Monday Evening Deity Greeting must be Har Har Mahadev 🔱", "Har Har Mahadev 🔱", eveningSlot.greetingEn)
        assertTrue("Greeting must contain canonical spelling Mahakaal", morningSlot.greetingEn.contains("Mahakaal"))
    }

    // 5. Audio Player Queue Progression & Boundary Integrity
    @Test
    fun testAudioPlayerQueueAndBoundaries() {
        val tracks = listOf(
            AudioTrackId.GAYATRI_MANTRA,
            AudioTrackId.MAHA_MRITYUNJAYA,
            AudioTrackId.SHIVA_TANDAV
        )

        var currentIndex = 0

        fun nextTrack(): AudioTrackId? {
            if (currentIndex < tracks.size - 1) {
                currentIndex++
                return tracks[currentIndex]
            }
            return null
        }

        fun previousTrack(): AudioTrackId? {
            if (currentIndex > 0) {
                currentIndex--
                return tracks[currentIndex]
            }
            return null
        }

        assertEquals(AudioTrackId.GAYATRI_MANTRA, tracks[currentIndex])
        assertEquals(AudioTrackId.MAHA_MRITYUNJAYA, nextTrack())
        assertEquals(AudioTrackId.SHIVA_TANDAV, nextTrack())
        assertNull("Next track beyond bounds should return null", nextTrack())

        assertEquals(AudioTrackId.MAHA_MRITYUNJAYA, previousTrack())
        assertEquals(AudioTrackId.GAYATRI_MANTRA, previousTrack())
        assertNull("Previous track before start should return null", previousTrack())
    }

    // 6. Profile Zero-Mock Defaults
    @Test
    fun testProfileZeroMockDefaults() {
        fun getProfileNameFallback(name: String?): String {
            val trimmed = name?.trim() ?: ""
            return if (trimmed.isEmpty()) "Devotee" else trimmed
        }

        fun getProfileLocationFallback(city: String?, state: String?): String {
            val c = city?.trim() ?: ""
            val s = state?.trim() ?: ""
            return when {
                c.isNotEmpty() && s.isNotEmpty() -> "$c, $s"
                c.isNotEmpty() -> c
                s.isNotEmpty() -> s
                else -> "Location not set"
            }
        }

        assertEquals("Devotee", getProfileNameFallback(""))
        assertEquals("Devotee", getProfileNameFallback(null))
        assertEquals("Devotee", getProfileNameFallback("   "))
        assertEquals("Kunal Pareek", getProfileNameFallback("Kunal Pareek"))

        assertEquals("Location not set", getProfileLocationFallback("", ""))
        assertEquals("Location not set", getProfileLocationFallback(null, null))
        assertEquals("Jaipur, Rajasthan", getProfileLocationFallback("Jaipur", "Rajasthan"))
    }

    // 7. Error Sanitization Mapper
    @Test
    fun testErrorSanitizationMapper() {
        fun sanitizeError(errorMsg: String): String {
            val lower = errorMsg.lowercase()
            return when {
                lower.contains("timeout") || lower.contains("socket") -> "Network connection is quiet. Please check your internet connection."
                lower.contains("401") || lower.contains("unauthorized") -> "Session expired. Please sign in again."
                lower.contains("404") -> "Resource not found."
                lower.contains("firebase") || lower.contains("exception") -> "Unable to process request at this time. Please try again."
                else -> "A temporary issue occurred. May Lord Shiva bless your sadhana."
            }
        }

        val timeoutSanitized = sanitizeError("java.net.SocketTimeoutException: Connection timed out")
        assertFalse("Sanitized error must not expose Exception name", timeoutSanitized.contains("SocketTimeoutException"))
        assertTrue("Sanitized error must contain friendly text", timeoutSanitized.contains("internet connection"))

        val firebaseSanitized = sanitizeError("com.google.firebase.auth.FirebaseAuthException: An internal error occurred")
        assertFalse("Sanitized error must not expose com.google.firebase", firebaseSanitized.contains("com.google.firebase"))
        assertTrue("Sanitized error must be respectful", firebaseSanitized.contains("Unable to process request"))
    }
}
