with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

old_curated = """        if (matched.isNotEmpty()) {
            matched
        } else {
            allVideos.take(6)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun getCuratedPlaylistTitle(isEng: Boolean): String {"""

new_curated = """        if (matched.isNotEmpty()) {
            matched
        } else {
            allVideos.take(6)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())
    
    fun getCuratedPlaylistTitle(isEng: Boolean): String {"""

text = text.replace(old_curated, new_curated)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
