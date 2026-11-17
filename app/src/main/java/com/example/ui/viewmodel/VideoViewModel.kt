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
    private val channelStore = com.example.data.YoutubeChannelStore.get(application)
    val youtubeChannels: StateFlow<List<com.example.data.YoutubeChannel>> = channelStore.channels

    fun refreshChannels() { viewModelScope.launch { channelStore.refresh() } }

    fun addYoutubeChannel(input: String, adminEmail: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            channelStore.addChannel(input, adminEmail)
                .onSuccess { ch -> onResult("Added ${ch.title}. Fetching its videos…"); syncYoutubeVideos { n -> onResult("Added ${ch.title} • $n new videos") } }
                .onFailure { onResult(it.message ?: "Couldn't add channel") }
        }
    }

    fun setYoutubeChannelEnabled(id: String, enabled: Boolean) { viewModelScope.launch { channelStore.setEnabled(id, enabled) } }
    val hiddenVideoIds: StateFlow<Set<String>> = channelStore.hiddenVideoIds
    fun setVideoHidden(videoId: String, hidden: Boolean, adminEmail: String) {
        viewModelScope.launch { channelStore.setVideoHidden(videoId, hidden, adminEmail) }
    }
    fun removeYoutubeChannel(id: String) { viewModelScope.launch { channelStore.remove(id) } }

    init {
        autoSyncYoutubeVideosIfNeeded {}
    }

    // Filtering State
    private val _selectedVideoCategory = MutableStateFlow("All")
    val selectedVideoCategory: StateFlow<String> = _selectedVideoCategory

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _currentFestival = MutableStateFlow("")
    val currentFestival: StateFlow<String> = _currentFestival

    fun setVideoCategory(category: String) {
        _selectedVideoCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCurrentFestival(festival: String) {
        _currentFestival.value = festival
    }

    fun filterVideos(list: List<BhaktiVideo>, cat: String, query: String): List<BhaktiVideo> {
        val catFiltered = when (cat) {
            "All" -> list
            "YouTube" -> list.filter { !it.isCustom }
            "Darshan" -> list.filter { it.category.contains("Darshan", ignoreCase = true) }
            "Puja" -> list.filter { it.category.contains("Puja", ignoreCase = true) || it.category.contains("Aarti", ignoreCase = true) || it.category.contains("Abhishek", ignoreCase = true) }
            "Stories" -> list.filter { it.category.contains("Stories", ignoreCase = true) || it.category.contains("Experiences", ignoreCase = true) }
            else -> list.filter { it.category.equals(cat, ignoreCase = true) }
        }
        return if (query.isBlank()) {
            catFiltered
        } else {
            val q = query.trim().lowercase()
            catFiltered.filter { 
                it.title.lowercase().contains(q) || 
                it.description.lowercase().contains(q) || 
                it.category.lowercase().contains(q) ||
                it.videoId.lowercase().contains(q)
            }
        }
    }

    // Processed Video List based on Category and Search Query
    val videos: StateFlow<List<BhaktiVideo>> = combine(
        repository.getVideos(),
        _selectedVideoCategory,
        _searchQuery,
        com.example.data.YoutubeChannelStore.get(application).hiddenVideoIds
    ) { list, cat, query, hidden ->
        filterVideos(list.filterNot { it.videoId in hidden }, cat, query)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun computeCuratedPlaylist(allVideos: List<BhaktiVideo>, festival: String): List<BhaktiVideo> {
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
        return if (matched.isNotEmpty()) {
            matched
        } else {
            allVideos.take(6)
        }
    }

    // Curated dynamic playlist based on current day of the week or active festival
    val curatedPlaylistVideos: StateFlow<List<BhaktiVideo>> = combine(
        repository.getVideos(),
        _currentFestival,
        com.example.data.YoutubeChannelStore.get(application).hiddenVideoIds
    ) { allVideos, festival, hidden ->
        computeCuratedPlaylist(allVideos.filterNot { it.videoId in hidden }, festival)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

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
                    val result = repository.syncAllVideos(apiKey = apiKey)
                    result.onSuccess { added ->
                        if (added > 0) onNewVideo()
                    }
                    prefs.edit().putLong("last_youtube_sync_timestamp", now).apply()
                }
            } catch (e: Exception) {
                android.util.Log.e("VideoViewModel", "Error in background YouTube fetch", e)
            }
        }
    }

    fun syncYoutubeVideos(onComplete: (Int) -> Unit = {}) {
        viewModelScope.launch {
            _isSyncingVideos.value = true
            var addedCount = 0
            try {
                val apiKey = com.example.BuildConfig.YOUTUBE_API_KEY
                val result = repository.syncAllVideos(apiKey = apiKey)
                result.onSuccess { count ->
                    addedCount = count
                }
            } catch (e: Exception) {
                android.util.Log.e("VideoViewModel", "Error in YouTube sync", e)
            } finally {
                _isSyncingVideos.value = false
                onComplete(addedCount)
            }
        }
    }
}
