package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class ProfileRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val karmaDao = db.karmaLogDao()
    private val gratitudeDao = db.gratitudeLogDao()
    private val userFavoriteDao = db.userFavoriteDao()

    val userRepository = UserRepository.getInstance(context)

    fun getKarmaLogs(): Flow<List<KarmaLog>> = karmaDao.getAllLogs()
    suspend fun addKarmaLog(log: KarmaLog) = karmaDao.insertLog(log)

    fun getGratitudeLogs(): Flow<List<GratitudeLog>> = gratitudeDao.getAllLogs()
    suspend fun addGratitudeLog(log: GratitudeLog) = gratitudeDao.insertLog(log)

    fun getUserFavorites(userId: String): Flow<List<UserFavorite>> = userFavoriteDao.getFavorites(userId)
    suspend fun addUserFavorite(favorite: UserFavorite) = userFavoriteDao.insertFavorite(favorite)
    suspend fun deleteUserFavorite(userId: String, targetId: String, type: String) = userFavoriteDao.deleteFavorite(userId, targetId, type)
}
