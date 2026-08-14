import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

replacement = """    private fun initMediaPlayer() {
        try {
            val track = devotionalTracks[_currentAudioTrackIndex.value]
            val resId = getApplication<android.app.Application>().resources.getIdentifier(track.url, "raw", getApplication<android.app.Application>().packageName)
            if (resId == 0) {
                android.util.Log.w("Audio", "Audio asset missing: res/raw/${track.url}.mp3")
                _isAudioPlaying.value = false
                return
            }
            mediaPlayer = android.media.MediaPlayer.create(getApplication<android.app.Application>(), resId)?.apply {
                setOnCompletionListener {
                    nextAudioTrack()
                }
                setOnErrorListener { _, _, _ ->
                    _isAudioPlaying.value = false
                    false
                }
                if (_isAudioPlaying.value) {
                    start()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _isAudioPlaying.value = false
        }
    }"""

pattern = r"    private fun initMediaPlayer\(\) \{.*?(?=    private fun stopAndReleasePlayer)"
content = re.sub(pattern, replacement + "\n\n", content, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
