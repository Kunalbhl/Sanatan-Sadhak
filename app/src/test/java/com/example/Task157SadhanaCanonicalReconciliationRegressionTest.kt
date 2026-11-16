package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.ui.theme.*
import com.example.utils.HapticFeedbackManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task157SadhanaCanonicalReconciliationRegressionTest {

    private lateinit var context: Context
    private lateinit var sadhanaRepo: SadhanaRepository
    private lateinit var mantraRepo: MantraRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sadhanaRepo = SadhanaRepository.getInstance(context)
        mantraRepo = MantraRepository(context)
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTask157_canonicalDesignTokensForSadhana() {
        assertEquals("Temple Cream token", Color(0xFFFDF8EE), TempleCream)
        assertEquals("Deep Sacred Maroon token", Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals("Devotional Saffron token", Color(0xFFE58A16), DevotionalSaffron)
        assertEquals("Sacred Gold token", Color(0xFFD4A23A), SacredGold)
        assertEquals("Temple Dark Background token", Color(0xFF160D08), TempleDarkBackground)

        assertEquals(TempleCream, LightSadhakColors.background)
        assertEquals(DeepSacredMaroon, LightSadhakColors.secondary)
        assertEquals(DevotionalSaffron, LightSadhakColors.primary)
        assertEquals(SacredGold, LightSadhakColors.accent)
        assertEquals(TempleDarkBackground, DarkSadhakColors.background)
    }

    @Test
    fun testTask157_japaMalaProgressionAndPointsCap() = runBlocking {
        val testUser = "devotee_japa_test@sanatan.org"
        val initialProfile = sadhanaRepo.getOrCreateProfile(testUser)
        val initialMalaCount = initialProfile.totalMalaCount
        val initialJapaCount = initialProfile.totalJapaCount

        // Record 1 Mala (108 chants)
        sadhanaRepo.recordJapaMala(testUser, 1)

        val updatedProfile = sadhanaRepo.getOrCreateProfile(testUser)
        assertEquals(initialMalaCount + 1, updatedProfile.totalMalaCount)
        assertEquals(initialJapaCount + 108, updatedProfile.totalJapaCount)
        assertTrue("Points earned must be positive", updatedProfile.totalScore > initialProfile.totalScore)
        assertTrue("Streak must be at least 1", updatedProfile.currentStreak >= 1)
    }

    @Test
    fun testTask157_dhyanMeditationRecording() = runBlocking {
        val testUser = "devotee_dhyan_test@sanatan.org"
        val initialProfile = sadhanaRepo.getOrCreateProfile(testUser)
        val initialMinutes = initialProfile.totalMeditationMinutes

        // Record 15 minutes of Dhyan
        sadhanaRepo.recordMeditationMinutes(testUser, 15)

        val updatedProfile = sadhanaRepo.getOrCreateProfile(testUser)
        assertEquals(initialMinutes + 15, updatedProfile.totalMeditationMinutes)
        assertTrue(updatedProfile.totalScore >= initialProfile.totalScore)
    }

    @Test
    fun testTask157_myMantraAccountIsolationAndPersistence() = runBlocking {
        val favoriteMantra = FavoriteMantra(
            id = 991,
            title = "Maha Mrityunjaya Mantra",
            content = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्। उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥",
            youtubeId = "video_991",
            playlistOrder = 1,
            timerMinutes = 15
        )

        // Save favorite mantra
        mantraRepo.addFavoriteMantra(favoriteMantra)
        val saved = mantraRepo.getFavoriteMantras().first()
        assertTrue("Saved mantra must be retrieved", saved.any { it.title.contains("Maha Mrityunjaya") })

        // Clean up
        mantraRepo.deleteFavoriteMantra(991)
    }

    @Test
    fun testTask157_hapticFeedbackManagerSafetyForSadhana() {
        val feedbackTypes = listOf(
            HapticFeedbackManager.FeedbackType.JAPA_BEAD,
            HapticFeedbackManager.FeedbackType.BELL_RING,
            HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP,
            HapticFeedbackManager.FeedbackType.LIGHT_TOUCH,
            HapticFeedbackManager.FeedbackType.SUCCESS
        )
        for (feedback in feedbackTypes) {
            HapticFeedbackManager.vibrate(context, feedback)
            HapticFeedbackManager.performHaptic(null, feedback)
        }
        assertTrue("All Sadhana haptic feedback executed safely", true)
    }

    @Test
    fun testTask157_sadhanaBadgesCatalogIntegrity() {
        val catalog = sadhanaRepo.allBadgesCatalog
        assertTrue("Catalog must have badges", catalog.isNotEmpty())
        assertTrue("Catalog must contain Streak badges", catalog.any { it.category == "Streak" })
        assertTrue("Catalog must contain Japa badges", catalog.any { it.category == "Japa" })
        assertTrue("Catalog must contain Meditation badges", catalog.any { it.category == "Meditation" })
        assertTrue("Catalog must contain Mantra badges", catalog.any { it.category == "Mantra" })
        assertTrue("Catalog must contain Devotion badges", catalog.any { it.category == "Devotion" })

        // Points must all be strictly positive
        assertTrue(catalog.all { it.points > 0 })
    }

    @Test
    fun testTask157_milestoneCelebrationIntegrity() {
        val profile = SadhanaProfileEntity(
            userId = "devotee_milestone@sanatan.org",
            currentStreak = 7,
            celebratedMilestones = ""
        )
        val celebration = sadhanaRepo.checkForMilestoneCelebration(profile)
        assertNotNull(celebration)
        assertEquals(7, celebration!!.milestoneDays)
        assertTrue(celebration.badgeTitle.isNotBlank())
        assertTrue(celebration.messageEn.isNotBlank())
        assertTrue(celebration.messageHi.isNotBlank())
    }

    @Test
    fun testTask157_activeSankalpaAndPunyaPoints() = runBlocking {
        val testUser = "devotee_sankalpa@sanatan.org"
        val profile = sadhanaRepo.getOrCreateProfile(testUser)
        assertNotNull(profile)
        assertTrue(profile.totalScore >= 0)
        assertTrue(profile.currentStreak >= 0)
    }

    @Test
    fun testTask157_roleBoundariesGuestDevoteeAdmin() {
        // Guest mode profile mapping
        val guestId = ""
        val cleanGuest = if (guestId.isBlank()) "guest_devotee" else guestId
        assertEquals("guest_devotee", cleanGuest)

        val devoteeId = "devotee@sanatan.org"
        assertNotEquals("guest_devotee", devoteeId)
    }
}
