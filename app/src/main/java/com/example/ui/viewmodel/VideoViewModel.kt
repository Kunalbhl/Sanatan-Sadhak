package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.data.BhaktiVideo
import com.example.data.VideoRepository

class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VideoRepository(application)

    init {
        autoSyncYoutubeVideosIfNeeded {}
    }

    // Filtering State
    private val _selectedVideoCategory = MutableStateFlow("All")
    val selectedVideoCategory: StateFlow<String> = _selectedVideoCategory

    private val _currentFestival = MutableStateFlow("")
    val currentFestival: StateFlow<String> = _currentFestival

    fun setVideoCategory(category: String) {
        _selectedVideoCategory.value = category
    }

    fun setCurrentFestival(festival: String) {
        _currentFestival.value = festival
    }

    // Processed Video List based on Category
    val videos: StateFlow<List<BhaktiVideo>> = repository.getVideos()
        .combine(_selectedVideoCategory) { list, cat ->
            when (cat) {
                "All" -> list
                "YouTube" -> list.filter { !it.isCustom }
                "Darshan" -> list.filter { it.category.contains("Darshan", ignoreCase = true) }
                "Puja" -> list.filter { it.category.contains("Puja", ignoreCase = true) || it.category.contains("Aarti", ignoreCase = true) || it.category.contains("Abhishek", ignoreCase = true) }
                "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
                else -> list.filter { it.category.equals(cat, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Curated dynamic playlist based on current day of the week or active festival
    val curatedPlaylistVideos: StateFlow<List<BhaktiVideo>> = combine(
        repository.getVideos(),
        _currentFestival
    ) { allVideos, festival ->
        val cal = java.util.Calendar.getInstance()
        val dow = cal.get(java.util.Calendar.DAY_OF_WEEK) // 1=Sunday, 2=Monday, ..., 7=Saturday
        
        // Day of Week Keywords
        val dayKeywords = when (dow) {
            1 -> listOf("surya", "ram", "rama", "sun", "aarati", "aarti", "रविवार", "राम", "सूर्य")
            2 -> listOf("shiva", "shiv", "mahadev", "bholenath", "somwar", "somvar", "shankar", "सोमवार", "शिव", "महादेव", "भोलेनाथ")
            3 -> listOf("hanuman", "ganesh", "ganpati", "mangal", "mangalwar", "mangalvar", "हनुमान", "गणेश", "गणपति", "मंगलवार")
            4 -> listOf("krishna", "ganesh", "ganpati", "budh", "budhwar", "budhvar", "बुधवार", "कृष्ण", "गणेश", "गणपति")
            5 -> listOf("vishnu", "guru", "hari", "guruwar", "guruvar", "narayan", "विष्णु", "गुरु", "गुरुवार", "नारायण")
            6 -> listOf("devi", "durga", "lakshmi", "laxmi", "shukra", "shukrawar", "shukravar", "शुक्रवार", "दुर्गा", "लक्ष्मी", "देवी")
            7 -> listOf("hanuman", "shani", "shaniwar", "shanivar", "हनुमान", "शनि")
            else -> emptyList()
        }

        // Active festival keywords
        val festKeywords = if (festival.isNotEmpty()) {
            festival.lowercase().split(" ", "-", "_")
        } else {
            emptyList()
        }

        // Filter and Score videos
        val scoredVideos = allVideos.map { video ->
            val titleLower = video.title.lowercase()
            val descLower = video.description.lowercase()
            var score = 0
            
            // Score for day of the week keywords
            dayKeywords.forEach { kw ->
                if (titleLower.contains(kw)) score += 5
                if (descLower.contains(kw)) score += 2
            }
            
            // Score for festival keywords (high priority)
            festKeywords.forEach { kw ->
                if (kw.length > 2) {
                    if (titleLower.contains(kw)) score += 15
                    if (descLower.contains(kw)) score += 5
                }
            }
            
            Pair(video, score)
        }

        // Return matched videos sorted by relevance, or fallback to first 6 videos
        val matched = scoredVideos.filter { it.second > 0 }.sortedByDescending { it.second }.map { it.first }
        if (matched.isNotEmpty()) {
            matched
        } else {
            allVideos.take(6)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getCuratedPlaylistTitle(isEng: Boolean): String {
        val cal = java.util.Calendar.getInstance()
        val dow = cal.get(java.util.Calendar.DAY_OF_WEEK)
        val festival = _currentFestival.value
        
        if (festival.isNotEmpty()) {
            return if (isEng) "Festival Special: $festival Devotion" else "उत्सव विशेष: $festival आराधना"
        }
        
        return when (dow) {
            1 -> if (isEng) "Sunday Special: Surya Dev & Lord Rama" else "रविवार विशेष: सूर्य देव व श्री राम आराधना"
            2 -> if (isEng) "Monday Special: Lord Shiva Bhakti" else "सोमवार विशेष: शिव भक्ति व आराधना"
            3 -> if (isEng) "Tuesday Special: Lord Hanuman & Ganesha" else "मंगलवार विशेष: हनुमान जी व श्री गणेश वंदना"
            4 -> if (isEng) "Wednesday Special: Lord Ganesha & Krishna" else "बुधवार विशेष: श्री गणेश व कृष्ण आराधना"
            5 -> if (isEng) "Thursday Special: Lord Vishnu & Guru Dev" else "गुरुवार विशेष: श्री हरि विष्णु व गुरु आराधना"
            6 -> if (isEng) "Friday Special: Goddess Durga & Lakshmi" else "शुक्रवार विशेष: माँ दुर्गा व महालक्ष्मी उपासना"
            7 -> if (isEng) "Saturday Special: Lord Shani Dev & Hanuman" else "शनिवार विशेष: शनि देव व संकटमोचन हनुमान"
            else -> if (isEng) "Daily Devotional Curated" else "दैनिक भक्ति आराधना"
        }
    }

    private val _isSyncingVideos = MutableStateFlow(false)
    val isSyncingVideos: StateFlow<Boolean> = _isSyncingVideos

    fun updateBhaktiVideo(video: BhaktiVideo) {
        viewModelScope.launch {
            repository.updateVideo(video)
        }
    }

    fun deleteBhaktiVideo(id: Int) {
        viewModelScope.launch {
            repository.deleteVideo(id)
        }
    }

    fun addBhaktiVideo(title: String, description: String, category: String, videoId: String) {
        viewModelScope.launch {
            val newVideo = BhaktiVideo(
                title = title,
                description = description,
                category = category,
                videoId = videoId,
                isCustom = true
            )
            repository.addVideo(newVideo)
        }
    }

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
