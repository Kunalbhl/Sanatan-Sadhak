package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Sadhana Badge definition and state
 */
data class SadhanaBadge(
    val badgeId: String,
    val title: String,
    val titleHi: String,
    val description: String,
    val descriptionHi: String,
    val category: String, // "Streak", "Japa", "Meditation", "Knowledge", "Mantra", "Devotion"
    val icon: String, // Devotional icon/emoji
    val requiredValue: Int,
    val currentValue: Int,
    val unlocked: Boolean,
    val unlockedAt: Long? = null,
    val rarity: String = "Common", // Common, Rare, Sacred, Divine
    val points: Int = 50
)

/**
 * Sadhana Devotional Ranking Level
 */
data class SadhanaRankLevel(
    val titleEn: String,
    val titleHi: String,
    val minScore: Int,
    val maxScore: Int,
    val icon: String
)

object SadhanaRankCalculator {
    val rankLevels = listOf(
        SadhanaRankLevel("Jigyasu", "जिज्ञासु", 0, 499, "🌱"),
        SadhanaRankLevel("Sadhak", "साधक", 500, 1499, "🪔"),
        SadhanaRankLevel("Bhakt", "भक्त", 1500, 2999, "🙏"),
        SadhanaRankLevel("Karmayogi", "कर्मयोगी", 3000, 5999, "🕉️"),
        SadhanaRankLevel("Dhyanastha", "ध्यानस्थ", 6000, 9999, "🧘"),
        SadhanaRankLevel("Atmajnani", "आत्मज्ञानी", 10000, Int.MAX_VALUE, "👑")
    )

    fun getLevelForScore(score: Int): SadhanaRankLevel {
        return rankLevels.find { score in it.minScore..it.maxScore } ?: rankLevels.last()
    }

    fun getNextLevelThreshold(score: Int): Int {
        val current = getLevelForScore(score)
        val next = rankLevels.getOrNull(rankLevels.indexOf(current) + 1)
        return next?.minScore ?: current.maxScore
    }
}

/**
 * User-scoped Sadhana persistent profile entity
 */
@Entity(tableName = "sadhana_profiles")
data class SadhanaProfileEntity(
    @PrimaryKey val userId: String, // email or "guest_devotee"
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalPracticeDays: Int = 0,
    val lastActiveDate: String = "", // yyyy-MM-dd
    val totalScore: Int = 0,
    val weeklyScore: Int = 0,
    val monthlyScore: Int = 0,
    val totalJapaCount: Int = 0,
    val totalMalaCount: Int = 0,
    val totalMeditationMinutes: Int = 0,
    val totalReadingsCount: Int = 0,
    val totalSavedMantrasCount: Int = 0,
    val totalDailyShlokasCount: Int = 0,
    val showcaseBadgeId: String = "streak_1",
    val isLeaderboardOptedIn: Boolean = true,
    val celebratedMilestones: String = "", // comma-separated e.g. "7,21,40"
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Activity log for anti-farming validation & daily caps
 */
@Entity(tableName = "sadhana_activity_logs")
data class SadhanaActivityLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val date: String, // yyyy-MM-dd
    val activityType: String, // "JAPA", "MEDITATION", "KNOWLEDGE", "DEVOTION", "MANTRA", "STREAK_BONUS"
    val referenceId: String, // article ID, mantra title, or session ID
    val pointsEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Unlocked Badge persistence per user
 */
@Entity(tableName = "sadhana_badges", primaryKeys = ["userId", "badgeId"])
data class SadhanaBadgeEntity(
    val userId: String,
    val badgeId: String,
    val unlockedAt: Long = System.currentTimeMillis()
)

/**
 * Leaderboard Display Item
 */
data class LeaderboardEntry(
    val rank: Int,
    val userId: String,
    val displayName: String,
    val avatarId: Int = 1,
    val showcaseBadgeTitle: String,
    val showcaseBadgeIcon: String,
    val streak: Int,
    val score: Int,
    val isCurrentUser: Boolean = false
)

/**
 * Milestone Celebration Data
 */
data class MilestoneCelebration(
    val milestoneDays: Int,
    val badgeTitle: String,
    val badgeTitleHi: String,
    val icon: String,
    val messageEn: String,
    val messageHi: String
)
