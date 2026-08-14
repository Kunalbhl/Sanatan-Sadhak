cat << 'INNER_EOF' > replacement.txt
    private fun initMediaPlayer() {
        try {
            val track = devotionalTracks[_currentAudioTrackIndex.value]
            val resId = getApplication<Application>().resources.getIdentifier(track.url, "raw", getApplication<Application>().packageName)
            if (resId == 0) {
                android.util.Log.w("Audio", "Audio asset missing: res/raw/${track.url}.mp3")
                _isAudioPlaying.value = false
                return
            }
            mediaPlayer = android.media.MediaPlayer.create(getApplication(), resId).apply {
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
    }
INNER_EOF
sed -i -e '/private fun initMediaPlayer() {/,/prepareAsync()/d' -e '/} catch (e: Exception) {/d' -e '/e.printStackTrace()/d' -e '/^        }$/d' app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt
# Actually this sed is fragile, I'll write a better python script to replace the function.
