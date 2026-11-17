package com.example.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Production VideoRepository coordinating Remote Firestore Backend, YouTube Data API/RSS,
 * and local Room Database cache (Remote -> Repository -> Room -> UI).
 */
class VideoRepository(private val context: Context) {

    private val tag = "VideoRepository"
    private val db = AppDatabase.getInstance(context)
    private val videoDao = db.bhaktiVideoDao()
    private val firebaseService = FirebaseService(context)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            ensureSeedVideos()
        }
    }

    suspend fun ensureSeedVideos() = withContext(Dispatchers.IO) {
        try {
            // Old builds shipped placeholder videos whose IDs pointed at unrelated YouTube videos.
            // Remove them; the library now only holds videos fetched from admin-approved channels.
            videoDao.deleteByVideoIds(SpiritualData.defaultVideos.map { it.videoId })
        } catch (e: Exception) {
            Log.e(tag, "Error ensuring seed videos", e)
        }
    }

    private val _lastSyncTimestamp = MutableStateFlow(0L)
    val lastSyncTimestamp: StateFlow<Long> = _lastSyncTimestamp.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _syncErrorMessage = MutableStateFlow<String?>(null)
    val syncErrorMessage: StateFlow<String?> = _syncErrorMessage.asStateFlow()

    // Room Database access flows
    fun getVideos(): Flow<List<BhaktiVideo>> = videoDao.getAllVideos()

    suspend fun addVideo(video: BhaktiVideo) = withContext(Dispatchers.IO) {
        videoDao.insertVideo(video)
        // Also persist remotely if Firebase is available
        try {
            val remote = RemoteBhaktiVideo.fromBhaktiVideo(video)
            firebaseService.saveBhaktiVideoToFirestore(remote)
        } catch (e: Exception) {
            Log.w(tag, "Remote save fallback (stored locally in Room): ${e.message}")
        }
    }

    suspend fun insertVideo(video: BhaktiVideo) = addVideo(video)

    suspend fun insertVideos(videos: List<BhaktiVideo>) = withContext(Dispatchers.IO) {
        videoDao.insertVideos(videos)
    }

    suspend fun updateVideo(video: BhaktiVideo) = withContext(Dispatchers.IO) {
        videoDao.updateVideo(video)
        try {
            val remote = RemoteBhaktiVideo.fromBhaktiVideo(video)
            firebaseService.saveBhaktiVideoToFirestore(remote)
        } catch (e: Exception) {
            Log.w(tag, "Remote update fallback: ${e.message}")
        }
    }

    suspend fun deleteVideo(id: Int) = withContext(Dispatchers.IO) {
        videoDao.deleteVideoById(id)
    }

    suspend fun deleteVideoById(id: Int) = deleteVideo(id)

    suspend fun deleteVideoPermanently(video: BhaktiVideo) = withContext(Dispatchers.IO) {
        videoDao.deleteVideoById(video.id)
        if (video.videoId.isNotBlank()) {
            try {
                firebaseService.deleteBhaktiVideoFromFirestore(video.videoId)
            } catch (e: Exception) {
                Log.w(tag, "Remote delete error: ${e.message}")
            }
        }
    }

    /**
     * Executes robust synchronization:
     * 1. Fetches remote curated videos from Firestore.
     * 2. Fetches official channel videos from YouTube Data API v3 or RSS.
     * 3. Deduplicates by videoId.
     * 4. Persists into Room cache.
     * 5. Handles transient failures with exponential retry.
     */
    suspend fun syncAllVideos(
        apiKey: String = "",
        channelId: String = "UCzHzPEdLJEvTSHn_ev3JYBA",
        maxRetries: Int = 3
    ): Result<Int> = withContext(Dispatchers.IO) {
        _isSyncing.value = true
        _syncErrorMessage.value = null
        var attempt = 0
        var lastException: Exception? = null

        while (attempt < maxRetries) {
            attempt++
            try {
                var totalNewVideos = 0
                val currentLocal = videoDao.getAllVideos().first()
                val existingVideoIds = currentLocal.map { it.videoId }.toSet()

                // Step 1: Fetch remote curated videos from Firestore
                val remoteFirestoreVideos = firebaseService.fetchBhaktiVideosFromFirestore(includeDrafts = false)
                val newFirestoreVideos = remoteFirestoreVideos
                    .filter { it.videoId.isNotBlank() && it.videoId !in existingVideoIds }
                    .map { it.toBhaktiVideo() }

                if (newFirestoreVideos.isNotEmpty()) {
                    videoDao.insertVideos(newFirestoreVideos)
                    totalNewVideos += newFirestoreVideos.size
                }

                // Step 2: Fetch every admin-approved channel (Firestore `youtube_channels`)
                val store = YoutubeChannelStore.get(context)
                store.refresh()
                val channelIds = store.enabledChannels().map { it.channelId }.ifEmpty { listOf(channelId) }
                val fetchedFromChannel = channelIds.flatMap { id ->
                    try { YoutubeFetcher.fetchVideosFromChannel(apiKey, id) } catch (e: Exception) {
                        Log.w(tag, "Channel $id failed: ${e.message}"); emptyList()
                    }
                }.distinctBy { it.videoId }
                if (fetchedFromChannel.isNotEmpty()) {
                    val updatedExistingIds = videoDao.getAllVideos().first().map { it.videoId }.toSet()
                    val newChannelVideos = fetchedFromChannel.filter { it.videoId.isNotBlank() && it.videoId !in updatedExistingIds }
                    if (newChannelVideos.isNotEmpty()) {
                        videoDao.insertVideos(newChannelVideos)
                        totalNewVideos += newChannelVideos.size
                    }
                }

                val now = System.currentTimeMillis()
                _lastSyncTimestamp.value = now
                _isSyncing.value = false
                Log.i(tag, "Sync completed successfully. Added $totalNewVideos new videos.")
                return@withContext Result.success(totalNewVideos)
            } catch (e: Exception) {
                lastException = e
                Log.w(tag, "Sync attempt $attempt failed: ${e.message}")
                AppDiagnosticsManager.recordException(e, ErrorCategory.YOUTUBE, ErrorSeverity.MEDIUM)
                if (attempt < maxRetries) {
                    val backoffDelay = (1000L * (1 shl (attempt - 1))).coerceAtMost(5000L)
                    delay(backoffDelay)
                }
            }
        }

        _isSyncing.value = false
        val errMsg = lastException?.message ?: "YouTube and Firestore sync failed after $maxRetries attempts."
        _syncErrorMessage.value = errMsg
        return@withContext Result.failure(lastException ?: Exception(errMsg))
    }

    suspend fun refreshYoutubeVideos() = withContext(Dispatchers.IO) {
        syncAllVideos()
    }
}
