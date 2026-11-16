package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SadhanaDao {

    @Query("SELECT * FROM sadhana_profiles WHERE userId = :userId LIMIT 1")
    suspend fun getProfile(userId: String): SadhanaProfileEntity?

    @Query("SELECT * FROM sadhana_profiles WHERE userId = :userId LIMIT 1")
    fun observeProfile(userId: String): Flow<SadhanaProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: SadhanaProfileEntity)

    @Query("SELECT * FROM sadhana_badges WHERE userId = :userId")
    fun observeUnlockedBadges(userId: String): Flow<List<SadhanaBadgeEntity>>

    @Query("SELECT * FROM sadhana_badges WHERE userId = :userId")
    suspend fun getUnlockedBadges(userId: String): List<SadhanaBadgeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockBadge(badge: SadhanaBadgeEntity)

    @Query("SELECT * FROM sadhana_activity_logs WHERE userId = :userId AND date = :date")
    suspend fun getTodayActivities(userId: String, date: String): List<SadhanaActivityLog>

    @Query("SELECT SUM(pointsEarned) FROM sadhana_activity_logs WHERE userId = :userId AND date = :date AND activityType = :activityType")
    suspend fun getTodayPointsForType(userId: String, date: String, activityType: String): Int?

    @Query("SELECT COUNT(*) FROM sadhana_activity_logs WHERE userId = :userId AND date = :date AND activityType = :activityType AND referenceId = :refId")
    suspend fun hasCompletedToday(userId: String, date: String, activityType: String, refId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: SadhanaActivityLog)

    @Query("SELECT * FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 ORDER BY totalScore DESC, currentStreak DESC LIMIT 100")
    fun observeAllTimeLeaderboard(): Flow<List<SadhanaProfileEntity>>

    @Query("SELECT * FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 ORDER BY weeklyScore DESC, currentStreak DESC LIMIT 100")
    fun observeWeeklyLeaderboard(): Flow<List<SadhanaProfileEntity>>

    @Query("SELECT * FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 ORDER BY monthlyScore DESC, currentStreak DESC LIMIT 100")
    fun observeMonthlyLeaderboard(): Flow<List<SadhanaProfileEntity>>

    @Query("SELECT COUNT(*) + 1 FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 AND totalScore > :score")
    suspend fun getUserAllTimeRank(score: Int): Int

    @Query("SELECT COUNT(*) + 1 FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 AND weeklyScore > :score")
    suspend fun getUserWeeklyRank(score: Int): Int

    @Query("SELECT COUNT(*) + 1 FROM sadhana_profiles WHERE isLeaderboardOptedIn = 1 AND monthlyScore > :score")
    suspend fun getUserMonthlyRank(score: Int): Int
}
