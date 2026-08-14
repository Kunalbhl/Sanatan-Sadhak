package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class PanchangRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val panchangDao = db.panchangDao()
    private val festivalDao = db.festivalDao()

    suspend fun getPanchangForDate(date: String): PanchangEntity? = panchangDao.getPanchangForDate(date)
    suspend fun savePanchang(panchang: PanchangEntity) = panchangDao.insertPanchang(panchang)

    suspend fun getFestival(date: String): FestivalEntity? = festivalDao.getFestival(date)
    suspend fun saveFestival(festival: FestivalEntity) = festivalDao.insertFestival(festival)
    fun getAllFestivals(): Flow<List<FestivalEntity>> = festivalDao.getAllFestivals()
}
