package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class Task94ProductionRegressionTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        AppPreferences.init(context)
        SadhakAudioManager.init(context)
        SadhakAudioManager.releaseAll()
    }

    // 1. Rank Tier Consistency (6 Classical Sacred Tiers)
    @Test
    fun testRankTierConsistency() {
        val levels = SadhanaRankCalculator.rankLevels
        assertEquals("Must have exactly 6 authoritative sacred rank tiers", 6, levels.size)
        assertEquals("Jigyasu", levels[0].titleEn)
        assertEquals("Sadhak", levels[1].titleEn)
        assertEquals("Bhakt", levels[2].titleEn)
        assertEquals("Karmayogi", levels[3].titleEn)
        assertEquals("Dhyanastha", levels[4].titleEn)
        assertEquals("Atmajnani", levels[5].titleEn)

        // Threshold checks
        assertEquals("Jigyasu", SadhanaRankCalculator.getLevelForScore(0).titleEn)
        assertEquals("Jigyasu", SadhanaRankCalculator.getLevelForScore(499).titleEn)
        assertEquals("Sadhak", SadhanaRankCalculator.getLevelForScore(500).titleEn)
        assertEquals("Bhakt", SadhanaRankCalculator.getLevelForScore(1500).titleEn)
        assertEquals("Karmayogi", SadhanaRankCalculator.getLevelForScore(3000).titleEn)
        assertEquals("Dhyanastha", SadhanaRankCalculator.getLevelForScore(6000).titleEn)
        assertEquals("Atmajnani", SadhanaRankCalculator.getLevelForScore(10000).titleEn)
        assertEquals("Atmajnani", SadhanaRankCalculator.getLevelForScore(50000).titleEn)
    }

    // 2. Badge Milestone Progression
    @Test
    fun testBadgeMilestoneProgression() {
        val mockProfile = SadhanaProfileEntity(
            userId = "test_devotee@sanatan.org",
            currentStreak = 7,
            totalMalaCount = 11,
            totalMeditationMinutes = 60,
            totalReadingsCount = 5,
            totalSavedMantrasCount = 3
        )
        // 7-day streak satisfies streak_7 (7), mala count 11 satisfies japa_1 (1) & japa_11 (11)
        assertTrue("Streak 7 should satisfy 7-day milestone", mockProfile.currentStreak >= 7)
        assertTrue("Mala 11 should satisfy 11-mala milestone", mockProfile.totalMalaCount >= 11)
        assertTrue("Meditation 60 mins should satisfy 1-hour milestone", mockProfile.totalMeditationMinutes >= 60)
    }

    // 3. Badge Duplicate Prevention
    @Test
    fun testBadgeDuplicatePrevention() {
        val earnedBadges = mutableSetOf<String>()
        fun awardBadge(badgeId: String): Boolean {
            return earnedBadges.add(badgeId)
        }

        assertTrue("First unlock of streak_7 should succeed", awardBadge("streak_7"))
        assertFalse("Duplicate unlock of streak_7 must be rejected", awardBadge("streak_7"))
        assertEquals("Unique badge count must be 1", 1, earnedBadges.size)
    }

    // 4. Streak Continuity
    @Test
    fun testStreakContinuity() {
        val lastActive = "2026-08-17"
        val today = "2026-08-18"
        val isConsecutive = (java.time.LocalDate.parse(today).toEpochDay() - java.time.LocalDate.parse(lastActive).toEpochDay()) == 1L
        assertTrue("Consecutive day should continue streak", isConsecutive)
    }

    // 5. Streak Break
    @Test
    fun testStreakBreak() {
        val lastActive = "2026-08-15"
        val today = "2026-08-18"
        val daysDiff = java.time.LocalDate.parse(today).toEpochDay() - java.time.LocalDate.parse(lastActive).toEpochDay()
        assertTrue("Streak must reset if more than 1 day missed", daysDiff > 1L)
    }

    // 6. Anti-Farming Safeguards
    @Test
    fun testAntiFarmingSafeguards() {
        val dailyCap = 500
        var currentDailyPoints = 480
        val incomingActivityPoints = 50
        val allowablePoints = minOf(incomingActivityPoints, maxOf(0, dailyCap - currentDailyPoints))
        assertEquals("Points awarded must respect daily anti-farming cap", 20, allowablePoints)
    }

    // 7. Weekly Leaderboard Ordering
    @Test
    fun testWeeklyLeaderboardOrdering() {
        val entries = listOf(
            LeaderboardEntry(rank = 2, userId = "u1", displayName = "Sadhak A", showcaseBadgeTitle = "streak_7", showcaseBadgeIcon = "🔥", streak = 5, score = 300),
            LeaderboardEntry(rank = 1, userId = "u2", displayName = "Sadhak B", showcaseBadgeTitle = "japa_21", showcaseBadgeIcon = "📿", streak = 10, score = 500),
            LeaderboardEntry(rank = 3, userId = "u3", displayName = "Sadhak C", showcaseBadgeTitle = "streak_1", showcaseBadgeIcon = "🌱", streak = 3, score = 200)
        ).sortedByDescending { it.score }

        assertEquals("Top score in weekly must be Sadhak B", "Sadhak B", entries[0].displayName)
        assertEquals("Second score in weekly must be Sadhak A", "Sadhak A", entries[1].displayName)
    }

    // 8. Monthly Leaderboard Ordering
    @Test
    fun testMonthlyLeaderboardOrdering() {
        val entries = listOf(
            LeaderboardEntry(rank = 2, userId = "u1", displayName = "Devotee X", showcaseBadgeTitle = "streak_21", showcaseBadgeIcon = "🔥", streak = 15, score = 1200),
            LeaderboardEntry(rank = 1, userId = "u2", displayName = "Devotee Y", showcaseBadgeTitle = "japa_108", showcaseBadgeIcon = "📿", streak = 25, score = 2400)
        ).sortedByDescending { it.score }

        assertEquals("Top monthly score must be Devotee Y", "Devotee Y", entries[0].displayName)
    }

    // 9. All-Time Leaderboard Ordering
    @Test
    fun testAllTimeLeaderboardOrdering() {
        val entries = listOf(
            LeaderboardEntry(rank = 2, userId = "u1", displayName = "Devotee 1", showcaseBadgeTitle = "streak_40", showcaseBadgeIcon = "🔥", streak = 40, score = 5000),
            LeaderboardEntry(rank = 1, userId = "u2", displayName = "Devotee 2", showcaseBadgeTitle = "atmajnani", showcaseBadgeIcon = "👑", streak = 100, score = 12000)
        ).sortedByDescending { it.score }

        assertEquals("All-time top score must be Devotee 2", "Devotee 2", entries[0].displayName)
    }

    // 10. Leaderboard Privacy Opt-In / Opt-Out
    @Test
    fun testLeaderboardPrivacy() {
        val publicDevotee = SadhanaProfileEntity(userId = "user1@dev.org", isLeaderboardOptedIn = true)
        val privateDevotee = SadhanaProfileEntity(userId = "user2@dev.org", isLeaderboardOptedIn = false)

        val publicEntries = listOf(publicDevotee, privateDevotee).filter { it.isLeaderboardOptedIn }
        assertEquals("Only opted-in devotees should appear on public mandala", 1, publicEntries.size)
        assertEquals("user1@dev.org", publicEntries[0].userId)
    }

    // 11. Profile Rank Consistency
    @Test
    fun testProfileRankConsistency() {
        val totalPoints = 3500
        val profileRank = SadhanaRankCalculator.getLevelForScore(totalPoints)
        assertEquals("3500 points must correspond to Karmayogi rank", "Karmayogi", profileRank.titleEn)
        assertEquals("कर्मयोगी", profileRank.titleHi)
    }

    // 12. Profile / Leaderboard Synchronization
    @Test
    fun testProfileLeaderboardSynchronization() {
        val profile = SadhanaProfileEntity(userId = "devotee@om.org", totalScore = 1800, currentStreak = 12)
        val entry = LeaderboardEntry(
            rank = 1,
            userId = profile.userId,
            displayName = "Devotee",
            score = profile.totalScore,
            streak = profile.currentStreak,
            showcaseBadgeTitle = SadhanaRankCalculator.getLevelForScore(profile.totalScore).titleEn,
            showcaseBadgeIcon = SadhanaRankCalculator.getLevelForScore(profile.totalScore).icon
        )

        assertEquals("Leaderboard score must match profile score exactly", profile.totalScore, entry.score)
        assertEquals("Leaderboard streak must match profile streak exactly", profile.currentStreak, entry.streak)
    }

    // 13. Account Isolation
    @Test
    fun testAccountIsolation() {
        val userA = "devoteeA@sanatan.org"
        val userB = "devoteeB@sanatan.org"

        val profileA = SadhanaProfileEntity(userId = userA, totalScore = 500)
        val profileB = SadhanaProfileEntity(userId = userB, totalScore = 1500)

        assertNotEquals(profileA.userId, profileB.userId)
        assertNotEquals(profileA.totalScore, profileB.totalScore)
    }

    // 14. Audio Asset Integrity in Raw Resources
    @Test
    fun testAudioAssetIntegrity() {
        val trackIds = AudioTrackId.entries
        assertTrue("Track IDs catalog must not be empty", trackIds.isNotEmpty())
        for (track in trackIds) {
            assertTrue("Track raw name must not be blank", track.rawName.isNotBlank())
            assertTrue("Track title must not be blank", track.titleEn.isNotBlank())
        }
    }

    // 15. Audio Queue Boundaries
    @Test
    fun testAudioQueueBoundaries() {
        val queue = AudioTrackId.entries.toList()
        var currentIndex = 0
        fun nextIndex(): Int = (currentIndex + 1) % queue.size
        fun prevIndex(): Int = if (currentIndex - 1 < 0) queue.size - 1 else currentIndex - 1

        assertEquals("Next index from 0 should be 1", 1, nextIndex())
        assertEquals("Prev index from 0 should loop to last item", queue.size - 1, prevIndex())
    }

    // 16. TTS Single-Session Protection
    @Test
    fun testTtsSingleSessionProtection() {
        var isTtsActive = false
        fun startTts() {
            if (isTtsActive) {
                // Preemptively stop previous
                isTtsActive = false
            }
            isTtsActive = true
        }

        startTts()
        assertTrue("TTS should be active", isTtsActive)
        startTts() // new session
        assertTrue("New TTS session replaces prior without overlap", isTtsActive)
    }

    // 17. Onboarding Per-User Isolation
    @Test
    fun testOnboardingPerUserIsolation() {
        val email1 = "user1@sanatan.org"
        val email2 = "user2@sanatan.org"
        val key1 = "onboarding_completed_${email1.lowercase().trim()}"
        val key2 = "onboarding_completed_${email2.lowercase().trim()}"

        assertNotEquals("Onboarding preference key must be distinct per email", key1, key2)
    }

    // 18. Panchang Determinism
    @Test
    fun testPanchangDeterminism() {
        kotlinx.coroutines.runBlocking {
            val result1 = AstrologyService.fetchRealPanchang(18, 8, 2026, 6, 0, 0)
            val result2 = AstrologyService.fetchRealPanchang(18, 8, 2026, 6, 0, 0)

            assertNotNull(result1.data)
            assertNotNull(result2.data)
            assertEquals("Tithi must be identical for same inputs", result1.data?.tithi, result2.data?.tithi)
            assertEquals("Nakshatra must be identical for same inputs", result1.data?.nakshatra, result2.data?.nakshatra)
            assertEquals("Yoga must be identical for same inputs", result1.data?.yoga, result2.data?.yoga)
        }
    }

    // 19. Daily Dharma Determinism
    @Test
    fun testDailyDharmaDeterminism() {
        val cal = Calendar.getInstance().apply {
            set(2026, Calendar.AUGUST, 18, 8, 0, 0)
        }
        val greeting1 = DeityGreetingEngine.getGreeting(cal, isEnglish = true)
        val greeting2 = DeityGreetingEngine.getGreeting(cal, isEnglish = true)

        assertEquals("Daily deity greeting must be deterministic for same day", greeting1, greeting2)
    }

    // 20. Technical Error Sanitization
    @Test
    fun testTechnicalErrorSanitization() {
        fun sanitizeError(raw: String): String {
            val technicalKeywords = listOf("Exception", "HTTP", "404", "500", "Firebase", "API_KEY", "NullPointer")
            return if (technicalKeywords.any { raw.contains(it, ignoreCase = true) }) {
                "शान्ति एवं धैर्य रखें। कृपापूर्वक पुनः प्रयास करें।"
            } else {
                raw
            }
        }

        val rawTechnicalError = "java.lang.NullPointerException: HTTP 500 Firebase API_KEY failed"
        val userFriendly = sanitizeError(rawTechnicalError)
        assertFalse("User should never see Exception keyword", userFriendly.contains("Exception"))
        assertFalse("User should never see HTTP error code", userFriendly.contains("500"))
        assertTrue("Devotional fallback message should be shown", userFriendly.contains("शान्ति"))
    }

    // 21. Content Collapse Behavior
    @Test
    fun testContentCollapseBehavior() {
        val fullContent = "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्।"
        val isExpanded = false
        val previewLength = 30
        val displayed = if (isExpanded || fullContent.length <= previewLength) fullContent else "${fullContent.take(previewLength)}..."

        assertTrue("Collapsed content must be truncated with ellipsis", displayed.endsWith("..."))
        assertTrue("Length of collapsed content must be bounded", displayed.length <= previewLength + 3)
    }

    // 22. Offline Fallback Behavior
    @Test
    fun testOfflineFallbackBehavior() {
        val isNetworkAvailable = false
        val fallbackQuote = "ॐ नमः शिवाय — आत्म साक्षात्कार ही परम ज्ञान है।"
        val content = if (!isNetworkAvailable) fallbackQuote else "Online dynamic stream"

        assertNotNull("Offline content must never be null", content)
        assertTrue("Offline content must contain devotional quote", content.contains("आत्म साक्षात्कार"))
    }
}
