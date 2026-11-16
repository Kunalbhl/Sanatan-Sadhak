package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.SadhanaRankCalculator
import com.example.data.SadhanaRepository
import com.example.utils.HapticFeedbackManager
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.SimpleDateFormat
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task160SadhanaJapaDhyanRegressionTest {

    private lateinit var context: Context
    private lateinit var repository: SadhanaRepository
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        repository = SadhanaRepository.getInstance(context)
    }

    @Test
    fun testTask160_streakCalculation_consecutiveDaysIncrements() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = dateFormat.format(cal.time)

        val (newStreak, newLongest, newTotal) = repository.calculateNewStreak(
            lastActiveDate = yesterday,
            currentStreak = 5,
            longestStreak = 10,
            totalPracticeDays = 15
        )
        assertEquals("Consecutive day practice must increment streak by 1", 6, newStreak)
        assertEquals(10, newLongest)
        assertEquals(16, newTotal)
    }

    @Test
    fun testTask160_streakCalculation_sameDayMaintainsStreak() {
        val today = dateFormat.format(Date())

        val (newStreak, newLongest, newTotal) = repository.calculateNewStreak(
            lastActiveDate = today,
            currentStreak = 5,
            longestStreak = 10,
            totalPracticeDays = 15
        )
        assertEquals("Same-day practice must maintain existing streak without reset", 5, newStreak)
        assertEquals(10, newLongest)
        assertEquals(15, newTotal)
    }

    @Test
    fun testTask160_streakCalculation_gapOverOneDayResetsStreak() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -2)
        val twoDaysAgo = dateFormat.format(cal.time)

        val (newStreak, newLongest, newTotal) = repository.calculateNewStreak(
            lastActiveDate = twoDaysAgo,
            currentStreak = 12,
            longestStreak = 15,
            totalPracticeDays = 25
        )
        assertEquals("Practicing after 2 or more days gap must reset streak to 1", 1, newStreak)
        assertEquals(15, newLongest)
        assertEquals(26, newTotal)
    }

    @Test
    fun testTask160_streakCalculation_emptyLastDateStartsAtOne() {
        val (newStreak, newLongest, newTotal) = repository.calculateNewStreak(
            lastActiveDate = "",
            currentStreak = 0,
            longestStreak = 0,
            totalPracticeDays = 0
        )
        assertEquals("First time practice with no previous date must start streak at 1", 1, newStreak)
        assertEquals(1, newLongest)
        assertEquals(1, newTotal)
    }

    @Test
    fun testTask160_accountIsolation_preferencesKeySanitization() {
        val user1 = "devotee.one@sacred.org"
        val user2 = "devotee.two@sacred.org"

        val sanitized1 = user1.replace("[^a-zA-Z0-9_]".toRegex(), "_")
        val sanitized2 = user2.replace("[^a-zA-Z0-9_]".toRegex(), "_")

        val key1 = "mantra_counter_prefs_$sanitized1"
        val key2 = "mantra_counter_prefs_$sanitized2"

        assertNotEquals("Keys for different devotees must be strictly distinct", key1, key2)

        val prefs1 = context.getSharedPreferences(key1, Context.MODE_PRIVATE)
        val prefs2 = context.getSharedPreferences(key2, Context.MODE_PRIVATE)

        prefs1.edit().putInt("current_count", 108).apply()
        prefs2.edit().putInt("current_count", 216).apply()

        assertEquals(108, prefs1.getInt("current_count", 0))
        assertEquals(216, prefs2.getInt("current_count", 0))
    }

    @Test
    fun testTask160_japaTargetAndMalaComputation() {
        val beadsPerMala = 108
        val targetMalas = 2
        val dailyTargetChants = beadsPerMala * targetMalas

        assertEquals(216, dailyTargetChants)

        val chantsDone = 108
        val progress = (chantsDone.toFloat() / dailyTargetChants.toFloat()).coerceAtMost(1.0f)
        assertEquals(0.5f, progress, 0.001f)

        val fullProgress = (216.toFloat() / dailyTargetChants.toFloat()).coerceAtMost(1.0f)
        assertEquals(1.0f, fullProgress, 0.001f)
    }

    @Test
    fun testTask160_dhyanMeditationSessionRecording() = runBlocking {
        val testUser = "dhyan_tester_${System.currentTimeMillis()}@sanatan.org"
        
        repository.recordMeditationMinutes(testUser, 20)
        val profile = repository.getOrCreateProfile(testUser)

        assertEquals("Total meditation minutes must be accumulated", 20, profile.totalMeditationMinutes)
        assertTrue("Devotee score must increase with meditation points", profile.totalScore > 0)
    }

    @Test
    fun testTask160_hapticFeedbackManagerSafetyForSadhana() {
        // Ensure that all Sadhana, Japa, and Dhyan haptic feedback types execute safely without exception
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.JAPA_BEAD)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.MALA_COMPLETE)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.BELL_RING)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.SUCCESS)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP)
    }

    @Test
    fun testTask160_sadhanaBadgesCatalogCompleteness() {
        val badges = repository.allBadgesCatalog
        assertTrue("Badges catalog must not be empty", badges.isNotEmpty())

        // Japa badges
        val firstMala = badges.find { it.badgeId == "japa_1" }
        assertNotNull(firstMala)
        assertEquals("First Mala", firstMala?.title)
        assertEquals(1, firstMala?.requiredValue)

        val tenMalas = badges.find { it.badgeId == "japa_10" }
        assertNotNull(tenMalas)
        assertEquals(10, tenMalas?.requiredValue)

        // Dhyan badges
        val firstDhyan = badges.find { it.badgeId == "dhyan_1" }
        assertNotNull(firstDhyan)
        assertEquals("First Dhyan", firstDhyan?.title)
        assertEquals(1, firstDhyan?.requiredValue)

        val hourDhyan = badges.find { it.badgeId == "dhyan_60" }
        assertNotNull(hourDhyan)
        assertEquals(60, hourDhyan?.requiredValue)

        // Streak badges
        val streak1 = badges.find { it.badgeId == "streak_1" }
        val streak7 = badges.find { it.badgeId == "streak_7" }
        val streak40 = badges.find { it.badgeId == "streak_40" }
        assertNotNull(streak1)
        assertNotNull(streak7)
        assertNotNull(streak40)
    }

    @Test
    fun testTask160_sadhanaRankCalculatorProgression() {
        assertEquals("Jigyasu", SadhanaRankCalculator.getLevelForScore(0).titleEn)
        assertEquals("Jigyasu", SadhanaRankCalculator.getLevelForScore(499).titleEn)
        assertEquals("Sadhak", SadhanaRankCalculator.getLevelForScore(500).titleEn)
        assertEquals("Bhakt", SadhanaRankCalculator.getLevelForScore(1500).titleEn)
        assertEquals("Karmayogi", SadhanaRankCalculator.getLevelForScore(3000).titleEn)
        assertEquals("Dhyanastha", SadhanaRankCalculator.getLevelForScore(6000).titleEn)
        assertEquals("Atmajnani", SadhanaRankCalculator.getLevelForScore(10000).titleEn)

        // Hindi titles
        assertEquals("जिज्ञासु", SadhanaRankCalculator.getLevelForScore(0).titleHi)
        assertEquals("साधक", SadhanaRankCalculator.getLevelForScore(500).titleHi)
        assertEquals("आत्मज्ञानी", SadhanaRankCalculator.getLevelForScore(15000).titleHi)
    }
}
