import re

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'r') as f:
    text = f.read()

new_methods = """
    fun autoSyncYoutubeVideosIfNeeded(onNewVideo: () -> Unit) {
        viewModelScope.launch {
            try {
                val context = getApplication<android.app.Application>()
                val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
                val lastSync = prefs.getLong("last_youtube_sync_timestamp", 0L)
                val now = System.currentTimeMillis()
                
                val sdf = java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault())
                val lastSyncDate = if (lastSync == 0L) "" else sdf.format(java.util.Date(lastSync))
                val todayDate = sdf.format(java.util.Date(now))
                
                if (lastSync == 0L || lastSyncDate != todayDate) {
                    val apiKey = com.example.BuildConfig.YOUTUBE_API_KEY
                    val fetched = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        com.example.data.YoutubeFetcher.fetchVideosFromChannel(apiKey)
                    }
                    if (fetched.isNotEmpty()) {
                        val currentVideos = repository.getVideos().first()
                        val existingIds = currentVideos.map { it.videoId }.toSet()
                        val newVideos = fetched.filter { it.videoId !in existingIds }
                        if (newVideos.isNotEmpty()) {
                            newVideos.forEach { video ->
                                repository.addVideo(video)
                            }
                            onNewVideo()
                        }
                    }
                    prefs.edit().putLong("last_youtube_sync_timestamp", now).apply()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun syncYoutubeVideos(onComplete: (Int) -> Unit = {}) {
        viewModelScope.launch {
            _isSyncingVideos.value = true
            var addedCount = 0
            try {
                val apiKey = com.example.BuildConfig.YOUTUBE_API_KEY
                val fetched = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    com.example.data.YoutubeFetcher.fetchVideosFromChannel(apiKey)
                }
                if (fetched.isNotEmpty()) {
                    val currentVideos = repository.getVideos().first()
                    val existingIds = currentVideos.map { it.videoId }.toSet()
                    val newVideos = fetched.filter { it.videoId !in existingIds }
                    if (newVideos.isNotEmpty()) {
                        newVideos.forEach { video ->
                            repository.addVideo(video)
                        }
                        addedCount = newVideos.size
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isSyncingVideos.value = false
                onComplete(addedCount)
            }
        }
    }
}
"""

text = text[:text.rfind("}")] + new_methods

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'w') as f:
    f.write(text)

