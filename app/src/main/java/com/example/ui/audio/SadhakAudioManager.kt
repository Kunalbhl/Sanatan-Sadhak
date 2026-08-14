package com.example.ui.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AudioTrackId(
    val rawName: String,
    val titleEn: String,
    val titleHi: String,
    val isOneShot: Boolean = false
) {
    TEMPLE_BELL("temple_bell", "Temple Bell", "मंदिर की घंटी", isOneShot = true),
    SADHANA_SUCCESS("sadhana_success", "Success Chime", "सफलता ध्वनि", isOneShot = true),
    OM_AMBIENT("om_ambient", "Om Chanting", "ॐ ध्वनि"),
    FLUTE_AMBIENT("flute_ambient", "Divine Flute", "दिव्य बाँसुरी"),
    GAYATRI_MANTRA("gayatri_mantra", "Gayatri Mantra", "गायत्री मंत्र"),
    SHIVA_TANDAV("shiva_tandav", "Shiva Tandav Stotram", "शिव तांडव स्तोत्रम्"),
    MAHA_MRITYUNJAYA("maha_mrityunjaya", "Mahamrityunjaya Mantra", "महामृत्युंजय मंत्र"),
    VISHNU_SAHASRANAMAM("vishnu_sahasranamam", "Vishnu Sahasranamam", "विष्णु सहस्रनाम"),
    HANUMAN_CHALISA("hanuman_chalisa", "Hanuman Chalisa", "श्री हनुमान चालीसा");

    companion object {
        fun fromRawName(name: String): AudioTrackId? = entries.find { it.rawName == name }
        fun fromIndex(index: Int): AudioTrackId = when (index) {
            1 -> TEMPLE_BELL
            2 -> OM_AMBIENT
            3 -> FLUTE_AMBIENT
            4 -> GAYATRI_MANTRA
            5 -> SHIVA_TANDAV
            6 -> MAHA_MRITYUNJAYA
            7 -> VISHNU_SAHASRANAMAM
            8 -> HANUMAN_CHALISA
            else -> OM_AMBIENT
        }
    }
}

object SadhakAudioManager {
    private const val TAG = "SadhakAudioManager"

    private var mediaPlayer: MediaPlayer? = null
    private var oneShotPlayer: MediaPlayer? = null
    private var isInitialized = false
    private var appContext: Context? = null

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _currentTrack = MutableStateFlow<AudioTrackId?>(null)
    val currentTrack: StateFlow<AudioTrackId?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    private val _currentPositionStr = MutableStateFlow("00:00")
    val currentPositionStr: StateFlow<String> = _currentPositionStr.asStateFlow()

    private val _durationStr = MutableStateFlow("00:00")
    val durationStr: StateFlow<String> = _durationStr.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var updateJob: Job? = null

    fun init(context: Context) {
        appContext = context.applicationContext
        isInitialized = true
    }

    fun setSoundEnabled(enabled: Boolean) {
        _soundEnabled.value = enabled
        if (!enabled) {
            stop()
        }
    }

    @Synchronized
    fun playOneShot(context: Context? = null, trackId: AudioTrackId) {
        if (!_soundEnabled.value) return
        val ctx = context ?: appContext ?: return

        try {
            val resId = ctx.resources.getIdentifier(trackId.rawName, "raw", ctx.packageName)
            if (resId != 0) {
                try {
                    oneShotPlayer?.reset()
                    oneShotPlayer?.release()
                } catch (_: Exception) {}
                oneShotPlayer = MediaPlayer.create(ctx, resId)?.apply {
                    setOnCompletionListener { mp ->
                        try {
                            mp.reset()
                            mp.release()
                        } catch (_: Exception) {}
                        if (oneShotPlayer == mp) {
                            oneShotPlayer = null
                        }
                    }
                    setOnErrorListener { mp, _, _ ->
                        try {
                            mp.reset()
                            mp.release()
                        } catch (_: Exception) {}
                        if (oneShotPlayer == mp) {
                            oneShotPlayer = null
                        }
                        false
                    }
                    start()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "One-shot playback error for ${trackId.rawName}: ${e.message}")
        }
    }

    fun playOneShot(trackId: AudioTrackId) {
        playOneShot(null, trackId)
    }

    fun playLoop(context: Context, trackId: AudioTrackId, volume: Float = 1.0f) {
        playTrack(context, trackId, isLooping = true, volume = volume)
    }

    fun playTrack(context: Context, trackId: AudioTrackId, isLooping: Boolean = false, volume: Float = 1.0f) {
        if (!_soundEnabled.value) return

        if (_currentTrack.value == trackId && mediaPlayer != null) {
            setVolume(volume)
            if (!_isPlaying.value) {
                try {
                    mediaPlayer?.start()
                    _isPlaying.value = true
                    startProgressUpdate()
                } catch (e: Exception) {
                    Log.e(TAG, "Error resuming track ${trackId.rawName}", e)
                }
            }
            return
        }

        stop()

        val resId = context.resources.getIdentifier(trackId.rawName, "raw", context.packageName)
        if (resId == 0) {
            Log.w(TAG, "Audio asset missing: res/raw/${trackId.rawName}")
            _currentTrack.value = trackId
            _isPlaying.value = false
            return
        }

        _currentTrack.value = trackId
        _isBuffering.value = true
        _isPlaying.value = false
        _progress.value = 0f
        _currentPositionStr.value = "00:00"
        _durationStr.value = "00:00"

        try {
            mediaPlayer = MediaPlayer.create(context, resId)?.apply {
                setVolume(volume, volume)
                this.isLooping = isLooping
                _durationStr.value = formatTime(duration)
                _isBuffering.value = false
                _isPlaying.value = true
                setOnCompletionListener {
                    if (!isLooping) {
                        _isPlaying.value = false
                        _progress.value = 1.0f
                    }
                }
                setOnErrorListener { _, _, _ ->
                    _isPlaying.value = false
                    _isBuffering.value = false
                    false
                }
                start()
                startProgressUpdate()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing track ${trackId.rawName}", e)
            _isBuffering.value = false
            _isPlaying.value = false
        }
    }

    fun pause() {
        try {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
                _isPlaying.value = false
                updateJob?.cancel()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error pausing audio", e)
        }
    }

    fun resume() {
        if (!_soundEnabled.value) return
        try {
            if (mediaPlayer != null && !_isPlaying.value) {
                mediaPlayer?.start()
                _isPlaying.value = true
                startProgressUpdate()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error resuming audio", e)
        }
    }

    fun stop() {
        updateJob?.cancel()
        _isPlaying.value = false
        _isBuffering.value = false
        _progress.value = 0f
        _currentPositionStr.value = "00:00"
        try {
            mediaPlayer?.reset()
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping audio", e)
        } finally {
            mediaPlayer = null
            _currentTrack.value = null
        }
        try {
            oneShotPlayer?.reset()
            oneShotPlayer?.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping oneShotPlayer", e)
        } finally {
            oneShotPlayer = null
        }
    }

    fun setVolume(volume: Float) {
        try {
            mediaPlayer?.setVolume(volume, volume)
        } catch (e: Exception) {
            Log.e(TAG, "Error setting volume", e)
        }
    }

    fun seekTo(progressFraction: Float) {
        mediaPlayer?.let { mp ->
            try {
                val dur = mp.duration
                if (dur > 0) {
                    val pos = (dur * progressFraction).toInt()
                    mp.seekTo(pos)
                    _progress.value = progressFraction
                    _currentPositionStr.value = formatTime(pos)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error seeking audio", e)
            }
        }
    }

    fun releaseAll() {
        stop()
        isInitialized = false
    }

    private fun startProgressUpdate() {
        updateJob?.cancel()
        updateJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { mp ->
                    try {
                        if (mp.isPlaying) {
                            val pos = mp.currentPosition
                            val dur = mp.duration
                            if (dur > 0) {
                                _progress.value = pos.toFloat() / dur.toFloat()
                                _currentPositionStr.value = formatTime(pos)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error updating progress", e)
                    }
                }
                delay(500)
            }
        }
    }

    private fun formatTime(ms: Int): String {
        val totalSeconds = ms / 1000
        val m = totalSeconds / 60
        val s = totalSeconds % 60
        return String.format("%02d:%02d", m, s)
    }
}
