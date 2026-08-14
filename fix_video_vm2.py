import re

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'r') as f:
    text = f.read()

new_methods = """
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
}
"""

text = text[:text.rfind("}")] + new_methods

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'w') as f:
    f.write(text)

