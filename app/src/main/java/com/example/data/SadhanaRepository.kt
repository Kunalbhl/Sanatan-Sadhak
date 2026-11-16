package com.example.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SadhanaRepository private constructor(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val sadhanaDao = db.sadhanaDao()
    private val userDao = db.userDao()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // All Definitive Badges Catalog
    // All Definitive Badges Catalog (including 12 Canonical Core Badges)
    val allBadgesCatalog = listOf(
        // Streak Milestone Badges
        SadhanaBadge("streak_1", "1-Day Starter", "१-दिवसीय आरंभ", "Began the holy path of daily Sadhana", "दैनिक साधना के पावन मार्ग पर पहला कदम", "Streak", "🌱", 1, 0, false, points = 20),
        SadhanaBadge("streak_3", "Beginning Sadhak", "आरंभिक साधक", "Maintained 3 consecutive days of disciplined Sadhana", "३ दिनों तक निरंतर साधना का नियम निभाया", "Streak", "🪔", 3, 0, false, points = 50),
        SadhanaBadge("streak_7", "7-Day Niyam", "७-दिवसीय नियम", "Completed 7 days of sacred spiritual resolve", "७ दिवसीय पावन संकल्प पूर्ण किया", "Streak", "🙏", 7, 0, false, points = 100),
        SadhanaBadge("streak_11", "Ekadashi Discipline", "एकादशी नियम", "Maintained 11 days of sacred continuous practice", "११ दिनों का एकादशी सदृश पावन नियम", "Streak", "🕉️", 11, 0, false, points = 150),
        SadhanaBadge("streak_21", "21-Day Sankalp", "२१-दिवसीय संकल्प", "21 days of steadfast devotional habit formation", "२१ दिनों की निष्ठावान साधना तपस्या", "Streak", "📿", 21, 0, false, points = 250),
        SadhanaBadge("streak_40", "40-Day Tapasya", "४०-दिवसीय तपस्या", "Completed 40 days of traditional Vedic Anushthan discipline", "४० दिनों का पारंपरिक वैदिक नियम अनुष्ठान", "Streak", "🔥", 40, 0, false, points = 400),
        SadhanaBadge("streak_51", "Bhakti Sadhak", "भक्ति साधक", "51 days of unwavering devotion and spiritual practice", "५१ दिनों की अनवरत भक्ति और साधना", "Streak", "🌺", 51, 0, false, points = 600),
        SadhanaBadge("streak_108", "108-Day Siddhi", "१०८-दिवसीय सिद्धि", "Attained the sacred 108-day milestone of supreme devotion", "१०८ दिनों की परम पावन साधना सिद्धि", "Streak", "🔱", 108, 0, false, points = 1080),
        SadhanaBadge("streak_180", "Anushthan Sadhak", "अनुष्ठान साधक", "Completed half a year (180 days) of unbroken divine practice", "अर्ध-वार्षिक १८० दिनों का अखंड अनुष्ठान", "Streak", "🪷", 180, 0, false, points = 1800),
        SadhanaBadge("streak_365", "Sadhana Varsh", "साधना वर्ष", "Completed 1 full year of unbroken daily Sanatan Sadhana", "पूर्ण १ वर्ष की अखंड दैनिक सनातन साधना", "Streak", "🏆", 365, 0, false, points = 3650),

        // Japa Badges
        SadhanaBadge("japa_1", "First Mala", "प्रथम माला", "Completed first sacred 108-bead Japa Mala", "प्रथम १०८ मनकों की पावन जप माला पूर्ण की", "Japa", "📿", 1, 0, false, points = 30),
        SadhanaBadge("japa_10", "10 Malas", "१० माला संकल्प", "Completed 10 sacred Japa Malas (1,080 Chants)", "१० पावन जप मालाएं (१०८० मंत्र) पूर्ण कीं", "Japa", "📿", 10, 0, false, points = 100),
        SadhanaBadge("japa_11", "11 Malas", "११ माला", "Completed 11 sacred Japa Malas in divine concentration", "११ पावन जप मालाएं पूर्ण कीं", "Japa", "📿", 11, 0, false, points = 120),
        SadhanaBadge("japa_50", "50 Malas", "५० माला सिद्धि", "Completed 50 sacred Japa Malas (5,400 Chants)", "५० पावन जप मालाएं (५४०० मंत्र) पूर्ण कीं", "Japa", "📿", 50, 0, false, points = 300),
        SadhanaBadge("japa_108", "108 Malas", "१०८ माला", "Attained 108 completed Japa Malas (11,664 Chants)", "१०८ जप मालाओं की महासिद्धि पूर्ण की", "Japa", "🔱", 108, 0, false, points = 800),
        SadhanaBadge("japa_1008", "1008 Malas", "सहस्रनाम जाप", "Completed 1,008 Japa Malas in sacred dedication", "१००८ जप मालाओं का महाअनुष्ठान पूर्ण", "Japa", "👑", 1008, 0, false, points = 2500),

        // Meditation / Dhyan Badges
        SadhanaBadge("dhyan_1", "First Dhyan", "प्रथम ध्यान", "Completed first calming mindfulness meditation session", "प्रथम शांत ध्यान व प्राणायाम सत्र पूर्ण किया", "Meditation", "🧘", 1, 0, false, points = 25),
        SadhanaBadge("dhyan_60", "60 Minutes", "६० मिनट ध्यान", "Accumulated 1 hour (60 minutes) of total quiet meditation", "कुल ६० मिनट की शांत साधना एवं ध्यान", "Meditation", "🧘", 60, 0, false, points = 80),
        SadhanaBadge("dhyan_300", "300 Minutes", "३०० मिनट ध्यान", "Accumulated 300 minutes (5 hours) of sacred contemplation", "३०० मिनटों का पावन मौन एवं आत्मचिंतन", "Meditation", "🧘", 300, 0, false, points = 250),
        SadhanaBadge("dhyan_600", "10 Hours Dhyan", "१० घंटे मौन", "Accumulated 10 hours of sacred contemplation", "१० घंटों का पावन मौन एवं आत्मचिंतन", "Meditation", "🧘", 600, 0, false, points = 400),
        SadhanaBadge("dhyan_3000", "50 Hours Dhyan", "५० घंटे समाधि", "Accumulated 50 hours of profound stillness", "५० घंटों का गहन ध्यान एवं प्राणायाम", "Meditation", "🪷", 3000, 0, false, points = 1200),
        SadhanaBadge("dhyan_6480", "108 Hours Dhyan", "१०८ घंटे महायोग", "Completed 108 hours of enlightened meditation", "१०८ घंटों का महायोग एवं दिव्य समाधि साधना", "Meditation", "🕉️", 6480, 0, false, points = 3000),

        // Knowledge / Scripture Badges
        SadhanaBadge("know_1", "First Scripture", "प्रथम स्वाध्याय", "Read your first Vedic scripture or sacred text", "प्रथम वैदिक शास्त्र या पावन ग्रंथ का अध्ययन", "Knowledge", "📖", 1, 0, false, points = 25),
        SadhanaBadge("know_5", "5 Scriptures Read", "५ शास्त्र अध्ययन", "Read 5 sacred scriptures or chapters", "५ पावन शास्त्र अध्यायों का अध्ययन पूर्ण किया", "Knowledge", "📜", 5, 0, false, points = 60),
        SadhanaBadge("know_10", "10 Sacred Readings", "१० पावन पाठ", "Completed 10 spiritual articles or chapters", "१० आध्यात्मिक लेख अथवा शास्त्र अध्यायों का अध्ययन", "Knowledge", "📜", 10, 0, false, points = 100),
        SadhanaBadge("know_50", "50 Scripture Chapters", "५० ग्रंथ अध्ययन", "Delved deep into 50 scripture passages", "५० शास्त्र प्रसंगों एवं श्लोकों का गूढ़ अध्ययन", "Knowledge", "📚", 50, 0, false, points = 350),
        SadhanaBadge("know_108", "108 Vedic Lessons", "१०८ स्वाध्याय पूर्ण", "Completed 108 wisdom readings across Shastras", "१०८ शास्त्र स्वाध्याय एवं ज्ञान सत्र पूर्ण", "Knowledge", "🏛️", 108, 0, false, points = 1000),

        // Mantra Badges
        SadhanaBadge("mantra_1", "First Saved Mantra", "प्रथम मंत्र संग्रह", "Added your first personal daily Sadhana mantra", "प्रथम दैनिक साधना मंत्र को अपनी डायरी में सहेजा", "Mantra", "🌸", 1, 0, false, points = 20),
        SadhanaBadge("mantra_3", "3 Mantras Practiced", "३ मंत्र साधना", "Practiced and saved 3 sacred mantras in your spiritual treasury", "३ पावन मंत्रों का साधना कोष तैयार किया", "Mantra", "🌸", 3, 0, false, points = 45),
        SadhanaBadge("mantra_5", "5 Sacred Mantras", "पंच मंत्र", "Curated a sacred collection of 5 daily chants", "५ दैनिक साधना मंत्रों का पावन संग्रह", "Mantra", "🌺", 5, 0, false, points = 60),
        SadhanaBadge("mantra_11", "11 Mantras Collection", "एकादश मंत्र", "Gathered 11 sacred Stotras and Vedic Mantras", "११ स्तोत्र एवं वैदिक मंत्रों का संग्रह", "Mantra", "🌼", 11, 0, false, points = 150),
        SadhanaBadge("mantra_21", "21 Divine Mantras", "२१ दिव्य मंत्र", "Mastered a treasury of 21 personal Sadhana mantras", "२१ पावन मंत्रों का समृद्ध साधना कोष", "Mantra", "🪷", 21, 0, false, points = 300),

        // Devotion / Daily Dharma Badges
        SadhanaBadge("shloka_1", "First Daily Shloka", "प्रथम श्लोक", "Completed your first daily Shloka reflection", "प्रथम दैनिक श्लोक एवं भावार्थ का पठन", "Devotion", "✨", 1, 0, false, points = 20),
        SadhanaBadge("shloka_7", "7 Daily Shlokas", "साप्ताहिक श्लोक", "Reflected upon 7 daily divine verses", "७ दैनिक पावन श्लोकों का मनन", "Devotion", "🌟", 7, 0, false, points = 70),
        SadhanaBadge("shloka_30", "30 Daily Shlokas", "मासिक नित्य धर्म", "Reflected upon 30 days of divine Shlokas", "३० दिनों का नित्य श्लोक एवं धर्म चिंतन", "Devotion", "💫", 30, 0, false, points = 250),
        SadhanaBadge("shloka_108", "108 Daily Shlokas", "१०८ नित्य श्लोक", "Attained 108 days of sacred Shloka wisdom", "१०८ दिनों का पावन श्लोक एवं आत्मज्ञान अध्ययन", "Devotion", "☀️", 108, 0, false, points = 900)
    )

    fun getProfileFlow(userId: String): Flow<SadhanaProfileEntity> {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        return sadhanaDao.observeProfile(cleanId).map { it ?: SadhanaProfileEntity(userId = cleanId) }
    }

    suspend fun getOrCreateProfile(userId: String): SadhanaProfileEntity {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val existing = sadhanaDao.getProfile(cleanId)
        if (existing != null) return existing

        val newProfile = SadhanaProfileEntity(userId = cleanId)
        sadhanaDao.insertOrUpdateProfile(newProfile)
        return newProfile
    }

    fun getBadgesFlow(userId: String): Flow<List<SadhanaBadge>> {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        return combine(
            getProfileFlow(cleanId),
            sadhanaDao.observeUnlockedBadges(cleanId)
        ) { profile, unlockedEntities ->
            val unlockedMap = unlockedEntities.associate { it.badgeId to it.unlockedAt }

            allBadgesCatalog.map { badge ->
                val currentValue = when (badge.category) {
                    "Streak" -> profile.currentStreak
                    "Japa" -> profile.totalMalaCount
                    "Meditation" -> profile.totalMeditationMinutes
                    "Knowledge" -> profile.totalReadingsCount
                    "Mantra" -> profile.totalSavedMantrasCount
                    "Devotion" -> profile.totalDailyShlokasCount
                    else -> 0
                }
                val isUnlocked = unlockedMap.containsKey(badge.badgeId) || currentValue >= badge.requiredValue
                badge.copy(
                    currentValue = currentValue,
                    unlocked = isUnlocked,
                    unlockedAt = unlockedMap[badge.badgeId]
                )
            }
        }
    }

    /**
     * Updates streaks safely based on the date of activity.
     * Normalized to calendar date boundaries (midnight) to prevent DST or time-of-day discrepancies.
     */
    fun calculateNewStreak(
        lastActiveDate: String,
        currentStreak: Int,
        longestStreak: Int,
        totalPracticeDays: Int,
        todayDateStr: String = dateFormat.format(Date())
    ): Triple<Int, Int, Int> {
        if (lastActiveDate == todayDateStr) {
            return Triple(currentStreak, longestStreak, totalPracticeDays)
        }

        if (lastActiveDate.isBlank()) {
            return Triple(1, maxOf(1, longestStreak), totalPracticeDays + 1)
        }

        return try {
            val lastDate = dateFormat.parse(lastActiveDate)
            val todayDate = dateFormat.parse(todayDateStr)
            if (lastDate == null || todayDate == null) {
                return Triple(1, maxOf(1, longestStreak), totalPracticeDays + 1)
            }

            val calLast = Calendar.getInstance().apply {
                time = lastDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val calToday = Calendar.getInstance().apply {
                time = todayDate
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val diffMs = calToday.timeInMillis - calLast.timeInMillis
            val diffDays = (diffMs / (1000L * 60L * 60L * 24L)).toInt()

            val newStreak = when {
                diffDays <= 0 -> currentStreak // Same day or clock skew
                diffDays == 1 -> currentStreak + 1 // Consecutive day
                else -> 1 // Missed one or more days -> reset to 1
            }
            val newLongest = maxOf(newStreak, longestStreak)
            val newTotalDays = totalPracticeDays + 1
            Triple(newStreak, newLongest, newTotalDays)
        } catch (e: Exception) {
            Triple(1, maxOf(1, longestStreak), totalPracticeDays + 1)
        }
    }

    /**
     * Check and unlock any badges that are now satisfied
     */
    private suspend fun checkAndUnlockBadges(userId: String, profile: SadhanaProfileEntity) {
        val unlockedList = sadhanaDao.getUnlockedBadges(userId).map { it.badgeId }.toSet()

        for (badge in allBadgesCatalog) {
            if (badge.badgeId !in unlockedList) {
                val currentValue = when (badge.category) {
                    "Streak" -> profile.currentStreak
                    "Japa" -> profile.totalMalaCount
                    "Meditation" -> profile.totalMeditationMinutes
                    "Knowledge" -> profile.totalReadingsCount
                    "Mantra" -> profile.totalSavedMantrasCount
                    "Devotion" -> profile.totalDailyShlokasCount
                    else -> 0
                }

                if (currentValue >= badge.requiredValue) {
                    sadhanaDao.unlockBadge(SadhanaBadgeEntity(userId = userId, badgeId = badge.badgeId))
                }
            }
        }
    }

    /**
     * Check if a new milestone should be celebrated
     */
    fun checkForMilestoneCelebration(profile: SadhanaProfileEntity): MilestoneCelebration? {
        val celebrated = profile.celebratedMilestones.split(",").filter { it.isNotBlank() }.mapNotNull { it.toIntOrNull() }.toSet()
        val milestoneDays = listOf(7, 11, 21, 40, 51, 108, 180, 365)

        for (m in milestoneDays) {
            if (profile.currentStreak >= m && m !in celebrated) {
                val badge = allBadgesCatalog.find { it.badgeId == "streak_$m" }
                return MilestoneCelebration(
                    milestoneDays = m,
                    badgeTitle = badge?.title ?: "$m-Day Sadhana",
                    badgeTitleHi = badge?.titleHi ?: "$m-दिवसीय साधना",
                    icon = badge?.icon ?: "🪔",
                    messageEn = "Congratulations, Sadhak! You have completed $m days of unbroken daily devotional discipline. May Mahakaal bless your path.",
                    messageHi = "बधाई हो, साधक! आपने $m दिनों की अखंड दैनिक साधना का नियम पूर्ण किया है। प्रभु की कृपा आप पर बनी रहे।"
                )
            }
        }
        return null
    }

    suspend fun markMilestoneCelebrated(userId: String, milestoneDays: Int) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val profile = getOrCreateProfile(cleanId)
        val celebratedSet = profile.celebratedMilestones.split(",").filter { it.isNotBlank() }.toMutableSet()
        celebratedSet.add(milestoneDays.toString())
        val updated = profile.copy(celebratedMilestones = celebratedSet.joinToString(","))
        sadhanaDao.insertOrUpdateProfile(updated)
    }

    // --- ACTIVITY RECORDING WITH ANTI-FARMING CAPS ---

    suspend fun recordJapaMala(userId: String, completedMalaCount: Int) {
        if (completedMalaCount <= 0) return
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val today = dateFormat.format(Date())

        val currentDailyPoints = sadhanaDao.getTodayPointsForType(cleanId, today, "JAPA") ?: 0
        // Cap at 100 points per day (5 Malas)
        val maxEarnable = (100 - currentDailyPoints).coerceAtLeast(0)
        val pointsToAward = minOf(completedMalaCount * 20, maxEarnable)

        if (pointsToAward > 0) {
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(
                    userId = cleanId,
                    date = today,
                    activityType = "JAPA",
                    referenceId = "mala_$completedMalaCount",
                    pointsEarned = pointsToAward
                )
            )
        }

        val profile = getOrCreateProfile(cleanId)
        val (newStreak, newLongest, newTotalDays) = calculateNewStreak(profile.lastActiveDate, profile.currentStreak, profile.longestStreak, profile.totalPracticeDays)

        // Award streak completion bonus of +25 if first qualifying activity today
        var bonus = 0
        if (profile.lastActiveDate != today) {
            bonus = 25
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(userId = cleanId, date = today, activityType = "STREAK_BONUS", referenceId = "daily_bonus", pointsEarned = bonus)
            )
        }

        val updated = profile.copy(
            currentStreak = newStreak,
            longestStreak = newLongest,
            totalPracticeDays = newTotalDays,
            lastActiveDate = today,
            totalScore = profile.totalScore + pointsToAward + bonus,
            weeklyScore = profile.weeklyScore + pointsToAward + bonus,
            monthlyScore = profile.monthlyScore + pointsToAward + bonus,
            totalJapaCount = profile.totalJapaCount + (completedMalaCount * 108),
            totalMalaCount = profile.totalMalaCount + completedMalaCount,
            updatedAt = System.currentTimeMillis()
        )
        sadhanaDao.insertOrUpdateProfile(updated)
        checkAndUnlockBadges(cleanId, updated)
    }

    suspend fun recordMeditationMinutes(userId: String, minutes: Int) {
        if (minutes <= 0) return
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val today = dateFormat.format(Date())

        val currentDailyPoints = sadhanaDao.getTodayPointsForType(cleanId, today, "MEDITATION") ?: 0
        // Cap at 60 points per day (60 minutes)
        val maxEarnable = (60 - currentDailyPoints).coerceAtLeast(0)
        val pointsToAward = minOf(minutes * 1, maxEarnable)

        if (pointsToAward > 0) {
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(
                    userId = cleanId,
                    date = today,
                    activityType = "MEDITATION",
                    referenceId = "dhyan_${minutes}m",
                    pointsEarned = pointsToAward
                )
            )
        }

        val profile = getOrCreateProfile(cleanId)
        val (newStreak, newLongest, newTotalDays) = calculateNewStreak(profile.lastActiveDate, profile.currentStreak, profile.longestStreak, profile.totalPracticeDays)

        var bonus = 0
        if (profile.lastActiveDate != today) {
            bonus = 25
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(userId = cleanId, date = today, activityType = "STREAK_BONUS", referenceId = "daily_bonus", pointsEarned = bonus)
            )
        }

        val updated = profile.copy(
            currentStreak = newStreak,
            longestStreak = newLongest,
            totalPracticeDays = newTotalDays,
            lastActiveDate = today,
            totalScore = profile.totalScore + pointsToAward + bonus,
            weeklyScore = profile.weeklyScore + pointsToAward + bonus,
            monthlyScore = profile.monthlyScore + pointsToAward + bonus,
            totalMeditationMinutes = profile.totalMeditationMinutes + minutes,
            updatedAt = System.currentTimeMillis()
        )
        sadhanaDao.insertOrUpdateProfile(updated)
        checkAndUnlockBadges(cleanId, updated)
    }

    suspend fun recordScriptureReading(userId: String, articleId: Int, title: String) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val today = dateFormat.format(Date())

        // Ensure this article gives points only once per day
        val alreadyReadToday = sadhanaDao.hasCompletedToday(cleanId, today, "KNOWLEDGE", articleId.toString()) > 0
        if (alreadyReadToday) return

        val currentDailyPoints = sadhanaDao.getTodayPointsForType(cleanId, today, "KNOWLEDGE") ?: 0
        // Cap at 60 points per day (4 articles)
        val maxEarnable = (60 - currentDailyPoints).coerceAtLeast(0)
        val pointsToAward = minOf(15, maxEarnable)

        sadhanaDao.insertActivityLog(
            SadhanaActivityLog(
                userId = cleanId,
                date = today,
                activityType = "KNOWLEDGE",
                referenceId = articleId.toString(),
                pointsEarned = pointsToAward
            )
        )

        val profile = getOrCreateProfile(cleanId)
        val (newStreak, newLongest, newTotalDays) = calculateNewStreak(profile.lastActiveDate, profile.currentStreak, profile.longestStreak, profile.totalPracticeDays)

        var bonus = 0
        if (profile.lastActiveDate != today) {
            bonus = 25
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(userId = cleanId, date = today, activityType = "STREAK_BONUS", referenceId = "daily_bonus", pointsEarned = bonus)
            )
        }

        val updated = profile.copy(
            currentStreak = newStreak,
            longestStreak = newLongest,
            totalPracticeDays = newTotalDays,
            lastActiveDate = today,
            totalScore = profile.totalScore + pointsToAward + bonus,
            weeklyScore = profile.weeklyScore + pointsToAward + bonus,
            monthlyScore = profile.monthlyScore + pointsToAward + bonus,
            totalReadingsCount = profile.totalReadingsCount + 1,
            updatedAt = System.currentTimeMillis()
        )
        sadhanaDao.insertOrUpdateProfile(updated)
        checkAndUnlockBadges(cleanId, updated)
    }

    suspend fun recordDailyDharma(userId: String, category: String) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val today = dateFormat.format(Date())

        val alreadyDoneToday = sadhanaDao.hasCompletedToday(cleanId, today, "DEVOTION", category) > 0
        if (alreadyDoneToday) return

        val pointsToAward = 10
        sadhanaDao.insertActivityLog(
            SadhanaActivityLog(
                userId = cleanId,
                date = today,
                activityType = "DEVOTION",
                referenceId = category,
                pointsEarned = pointsToAward
            )
        )

        val profile = getOrCreateProfile(cleanId)
        val (newStreak, newLongest, newTotalDays) = calculateNewStreak(profile.lastActiveDate, profile.currentStreak, profile.longestStreak, profile.totalPracticeDays)

        var bonus = 0
        if (profile.lastActiveDate != today) {
            bonus = 25
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(userId = cleanId, date = today, activityType = "STREAK_BONUS", referenceId = "daily_bonus", pointsEarned = bonus)
            )
        }

        val updated = profile.copy(
            currentStreak = newStreak,
            longestStreak = newLongest,
            totalPracticeDays = newTotalDays,
            lastActiveDate = today,
            totalScore = profile.totalScore + pointsToAward + bonus,
            weeklyScore = profile.weeklyScore + pointsToAward + bonus,
            monthlyScore = profile.monthlyScore + pointsToAward + bonus,
            totalDailyShlokasCount = profile.totalDailyShlokasCount + 1,
            updatedAt = System.currentTimeMillis()
        )
        sadhanaDao.insertOrUpdateProfile(updated)
        checkAndUnlockBadges(cleanId, updated)
    }

    suspend fun recordSavedMantra(userId: String, mantraTitle: String) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val today = dateFormat.format(Date())

        val currentDailyPoints = sadhanaDao.getTodayPointsForType(cleanId, today, "MANTRA") ?: 0
        val maxEarnable = (50 - currentDailyPoints).coerceAtLeast(0)
        val pointsToAward = minOf(10, maxEarnable)

        if (pointsToAward > 0) {
            sadhanaDao.insertActivityLog(
                SadhanaActivityLog(
                    userId = cleanId,
                    date = today,
                    activityType = "MANTRA",
                    referenceId = mantraTitle,
                    pointsEarned = pointsToAward
                )
            )
        }

        val profile = getOrCreateProfile(cleanId)
        val updated = profile.copy(
            totalScore = profile.totalScore + pointsToAward,
            weeklyScore = profile.weeklyScore + pointsToAward,
            monthlyScore = profile.monthlyScore + pointsToAward,
            totalSavedMantrasCount = profile.totalSavedMantrasCount + 1,
            updatedAt = System.currentTimeMillis()
        )
        sadhanaDao.insertOrUpdateProfile(updated)
        checkAndUnlockBadges(cleanId, updated)
    }

    suspend fun setShowcaseBadge(userId: String, badgeId: String) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val profile = getOrCreateProfile(cleanId)
        val updated = profile.copy(showcaseBadgeId = badgeId)
        sadhanaDao.insertOrUpdateProfile(updated)
    }

    suspend fun setLeaderboardOptIn(userId: String, optedIn: Boolean) {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val profile = getOrCreateProfile(cleanId)
        val updated = profile.copy(isLeaderboardOptedIn = optedIn)
        sadhanaDao.insertOrUpdateProfile(updated)
    }

    // --- LEADERBOARD QUERY FLOWS ---

    fun getLeaderboard(currentUserId: String, period: String): Flow<List<LeaderboardEntry>> {
        val cleanId = if (currentUserId.isBlank()) "guest_devotee" else currentUserId.lowercase().trim()

        val sourceFlow = when (period) {
            "Weekly" -> sadhanaDao.observeWeeklyLeaderboard()
            "Monthly" -> sadhanaDao.observeMonthlyLeaderboard()
            else -> sadhanaDao.observeAllTimeLeaderboard()
        }

        return sourceFlow.map { profiles ->
            profiles.mapIndexed { index, p ->
                val badge = allBadgesCatalog.find { it.badgeId == p.showcaseBadgeId } ?: allBadgesCatalog.first()
                val score = when (period) {
                    "Weekly" -> p.weeklyScore
                    "Monthly" -> p.monthlyScore
                    else -> p.totalScore
                }
                val isCurrent = p.userId == cleanId
                val displayName = if (isCurrent) "You (आप)" else {
                    val user = userDao.getUserByEmail(p.userId)
                    user?.fullName?.ifBlank { "Devotee" } ?: "Devotee"
                }

                LeaderboardEntry(
                    rank = index + 1,
                    userId = p.userId,
                    displayName = displayName,
                    showcaseBadgeTitle = badge.title,
                    showcaseBadgeIcon = badge.icon,
                    streak = p.currentStreak,
                    score = score,
                    isCurrentUser = isCurrent
                )
            }
        }
    }

    suspend fun getUserRank(userId: String, period: String): Pair<Int, Int> {
        val cleanId = if (userId.isBlank()) "guest_devotee" else userId.lowercase().trim()
        val profile = getOrCreateProfile(cleanId)
        val score = when (period) {
            "Weekly" -> profile.weeklyScore
            "Monthly" -> profile.monthlyScore
            else -> profile.totalScore
        }
        val rank = when (period) {
            "Weekly" -> sadhanaDao.getUserWeeklyRank(score)
            "Monthly" -> sadhanaDao.getUserMonthlyRank(score)
            else -> sadhanaDao.getUserAllTimeRank(score)
        }
        return Pair(rank, score)
    }

    companion object {
        @Volatile
        private var INSTANCE: SadhanaRepository? = null

        fun getInstance(context: Context): SadhanaRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = SadhanaRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
