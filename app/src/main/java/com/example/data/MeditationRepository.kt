package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class MeditationRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val sankalpaDao = db.sankalpaDao()

    fun getSankalpaLogs(): Flow<List<SankalpaLog>> = sankalpaDao.getAllLogs()
    suspend fun addSankalpaLog(log: SankalpaLog) = sankalpaDao.insertLog(log)
}
