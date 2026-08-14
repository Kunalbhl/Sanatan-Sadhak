package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class MantraRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val favoriteMantraDao = db.favoriteMantraDao()

    fun getFavoriteMantras(): Flow<List<FavoriteMantra>> = favoriteMantraDao.getAllFavoriteMantras()
    suspend fun addFavoriteMantra(mantra: FavoriteMantra) = favoriteMantraDao.insertFavoriteMantra(mantra)
    suspend fun deleteFavoriteMantra(id: Int) = favoriteMantraDao.deleteFavoriteMantra(id)
}
