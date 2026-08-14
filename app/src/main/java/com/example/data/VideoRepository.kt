package com.example.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class VideoRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val videoDao = db.bhaktiVideoDao()

    fun getVideos(): Flow<List<BhaktiVideo>> = videoDao.getAllVideos()
    suspend fun addVideo(video: BhaktiVideo) = videoDao.insertVideo(video)
    suspend fun updateVideo(video: BhaktiVideo) = videoDao.updateVideo(video)
    suspend fun deleteVideo(id: Int) = videoDao.deleteVideoById(id)

    suspend fun refreshYoutubeVideos() = withContext(Dispatchers.IO) {
        try {
            val fetched = YoutubeFetcher.fetchVideosFromChannel("")
            if (fetched.isNotEmpty()) {
                videoDao.clearNonCustomVideos()
                videoDao.insertVideos(fetched)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
