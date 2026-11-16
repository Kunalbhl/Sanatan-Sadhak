package com.example.ui.audio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AudioCategory(val titleEn: String, val titleHi: String) {
    MANTRA("Sacred Mantras", "पवित्र मंत्र"),
    DEVOTIONAL("Stotrams & Chalisa", "स्तोत्र एवं चालीसा"),
    AMBIENT("Dhyan & Ambient", "ध्यान एवं शांति")
}

enum class AudioTrackId(
    val rawName: String,
    val titleEn: String,
    val titleHi: String,
    val category: AudioCategory,
    val subtitleEn: String = "Divine Vedic Resonance",
    val subtitleHi: String = "दिव्य वैदिक ध्वनि",
    val isOneShot: Boolean = false
) {
    TEMPLE_BELL("temple_bell", "Temple Bell", "मंदिर की घंटी", AudioCategory.AMBIENT, isOneShot = true),
    SADHANA_SUCCESS("sadhana_success", "Success Chime", "सफलता ध्वनि", AudioCategory.AMBIENT, isOneShot = true),
    
    // Mantras
    GAYATRI_MANTRA("gayatri_mantra", "Gayatri Mantra", "गायत्री मंत्र", AudioCategory.MANTRA, "Universal Prayer for Wisdom", "बुद्धि और प्रकाश का महामंत्र"),
    MAHA_MRITYUNJAYA("maha_mrityunjaya", "Mahamrityunjaya Mantra", "महामृत्युंजय मंत्र", AudioCategory.MANTRA, "Moksha & Healing Chant", "मोक्ष एवं स्वास्थ्य प्रदायक मंत्र"),
    
    // Devotional / Stotrams
    SHIVA_TANDAV("shiva_tandav", "Shiva Tandav Stotram", "शिव तांडव स्तोत्रम्", AudioCategory.DEVOTIONAL, "Ravana's Hymn to Lord Shiva", "शिव स्तुति - रावण कृत"),
    HANUMAN_CHALISA("hanuman_chalisa", "Hanuman Chalisa", "श्री हनुमान चालीसा", AudioCategory.DEVOTIONAL, "40 Chaupais of Devotion", "संकटमोचन हनुमान स्तुति"),
    VISHNU_SAHASRANAMAM("vishnu_sahasranamam", "Vishnu Sahasranamam", "विष्णु सहस्रनाम", AudioCategory.DEVOTIONAL, "1000 Holy Names of Vishnu", "श्री हरि के १००० पवित्र नाम"),

    // Ambient / Dhyan
    OM_AMBIENT("om_ambient", "Om Chanting", "ॐ ध्वनि", AudioCategory.AMBIENT, "Cosmic Sound of Creation", "सृष्टि का अनाहत नाद"),
    FLUTE_AMBIENT("flute_ambient", "Divine Flute", "दिव्य बाँसुरी", AudioCategory.AMBIENT, "Meditation Melody of Shri Krishna", "श्री कृष्ण की मधुर मुरली धुन");

    companion object {
        fun playableTracks(): List<AudioTrackId> = entries.filter { !it.isOneShot }
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
    private var loginAmbientPlayer: MediaPlayer? = null
    private var isInitialized = false
    private var appContext: Context? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _isLoginAmbientPlaying = MutableStateFlow(false)
    val isLoginAmbientPlaying: StateFlow<Boolean> = _isLoginAmbientPlaying.asStateFlow()

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

    private val _volume = MutableStateFlow(1.0f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    private val _isRepeatEnabled = MutableStateFlow(false)
    val isRepeatEnabled: StateFlow<Boolean> = _isRepeatEnabled.asStateFlow()

    private val _queue = MutableStateFlow<List<AudioTrackId>>(AudioTrackId.playableTracks())
    val queue: StateFlow<List<AudioTrackId>> = _queue.asStateFlow()

    private val _showFullPlayer = MutableStateFlow(false)
    val showFullPlayer: StateFlow<Boolean> = _showFullPlayer.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var updateJob: Job? = null
    private var noisyReceiver: BroadcastReceiver? = null

    private var lastOneShotTrack: AudioTrackId? = null
    private var lastOneShotTimeMs: Long = 0L

    fun init(context: Context) {
        appContext = context.applicationContext
        audioManager = appContext?.getSystemService(Context.AUDIO_SERVICE) as? AudioManager
        isInitialized = true
        SadhakTextReader.init(context)
        registerNoisyReceiver(context)
    }

    private fun registerNoisyReceiver(context: Context) {
        if (noisyReceiver == null) {
            noisyReceiver = object : BroadcastReceiver() {
                override fun onReceive(c: Context?, intent: Intent?) {
                    if (intent?.action == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                        pause()
                    }
                }
            }
            try {
                val filter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.applicationContext.registerReceiver(noisyReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
                } else {
                    context.applicationContext.registerReceiver(noisyReceiver, filter)
                }
            } catch (e: Exception) {
                Log.w(TAG, "Failed to register becoming noisy receiver: ${e.message}")
            }
        }
    }

    private fun unregisterNoisyReceiver(context: Context?) {
        noisyReceiver?.let { receiver ->
            try {
                context?.applicationContext?.unregisterReceiver(receiver)
            } catch (_: Exception) {}
            noisyReceiver = null
        }
    }

    fun openFullPlayer() {
        _showFullPlayer.value = true
    }

    fun closeFullPlayer() {
        _showFullPlayer.value = false
    }

    fun toggleRepeat() {
        _isRepeatEnabled.value = !_isRepeatEnabled.value
        mediaPlayer?.isLooping = _isRepeatEnabled.value
    }

    fun toggleMute() {
        val newMuted = !_isMuted.value
        _isMuted.value = newMuted
        if (newMuted) {
            mediaPlayer?.setVolume(0f, 0f)
        } else {
            val currentVol = _volume.value
            mediaPlayer?.setVolume(currentVol, currentVol)
        }
    }

    fun setMuted(muted: Boolean) {
        _isMuted.value = muted
        if (muted) {
            mediaPlayer?.setVolume(0f, 0f)
        } else {
            val currentVol = _volume.value
            mediaPlayer?.setVolume(currentVol, currentVol)
        }
    }

    fun restart() {
        seekTo(0f)
        resume()
    }

    fun setSoundEnabled(enabled: Boolean) {
        _soundEnabled.value = enabled
        if (!enabled) {
            stop()
            stopLoginAmbient()
        }
    }

    private fun createPlayerForRaw(
        context: Context,
        resId: Int,
        usage: Int = AudioAttributes.USAGE_MEDIA,
        onError: ((MediaPlayer) -> Unit)? = null
    ): MediaPlayer? {
        return try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(usage)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            val player = MediaPlayer.create(context, resId)
            player?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    try {
                        setAudioAttributes(audioAttributes)
                    } catch (_: Throwable) {}
                }
                setOnErrorListener { mp, what, extra ->
                    Log.w(TAG, "MediaPlayer error ($what, $extra) handled safely")
                    try {
                        if (mp.isPlaying) {
                            mp.stop()
                        }
                    } catch (_: Throwable) {}
                    try {
                        mp.reset()
                        mp.release()
                    } catch (_: Throwable) {}
                    onError?.invoke(mp)
                    true // Consume error to prevent unhandled MediaPlayerNative errors
                }
            }
        } catch (e: Throwable) {
            Log.w(TAG, "Failed creating MediaPlayer for resId $resId: ${e.message}")
            null
        }
    }

    fun startLoginAmbient(context: Context) {
        if (!_soundEnabled.value) return
        if (loginAmbientPlayer != null && _isLoginAmbientPlaying.value) return

        try {
            stopLoginAmbient()
            val ctx = context.applicationContext
            val resId = ctx.resources.getIdentifier("om_ambient", "raw", ctx.packageName)
            if (resId != 0) {
                val player = createPlayerForRaw(ctx, resId, AudioAttributes.USAGE_MEDIA) { mp ->
                    if (loginAmbientPlayer == mp) {
                        loginAmbientPlayer = null
                    }
                    _isLoginAmbientPlaying.value = false
                }
                if (player != null) {
                    player.setVolume(0.12f, 0.12f)
                    player.isLooping = true
                    player.start()
                    loginAmbientPlayer = player
                    _isLoginAmbientPlaying.value = true
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error starting login ambient audio: ${e.message}")
            try {
                loginAmbientPlayer?.reset()
                loginAmbientPlayer?.release()
            } catch (_: Exception) {}
            loginAmbientPlayer = null
            _isLoginAmbientPlaying.value = false
        }
    }

    fun stopLoginAmbient() {
        try {
            if (loginAmbientPlayer?.isPlaying == true) {
                loginAmbientPlayer?.stop()
            }
            loginAmbientPlayer?.reset()
            loginAmbientPlayer?.release()
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping login ambient audio: ${e.message}")
        } finally {
            loginAmbientPlayer = null
            _isLoginAmbientPlaying.value = false
        }
    }

    private fun requestAudioFocus(): Boolean {
        val am = audioManager ?: return true
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val playbackAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            val req = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener { focusChange ->
                    when (focusChange) {
                        AudioManager.AUDIOFOCUS_LOSS -> pause()
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pause()
                        AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> setVolume(0.2f)
                        AudioManager.AUDIOFOCUS_GAIN -> {
                            setVolume(1.0f)
                            resume()
                        }
                    }
                }
                .build()
            focusRequest = req
            am.requestAudioFocus(req) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        } else {
            @Suppress("DEPRECATION")
            am.requestAudioFocus(
                { focusChange ->
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        pause()
                    }
                },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            ) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
        }
    }

    private fun abandonAudioFocus() {
        val am = audioManager ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusRequest?.let { am.abandonAudioFocusRequest(it) }
        } else {
            @Suppress("DEPRECATION")
            am.abandonAudioFocus(null)
        }
    }

    @Synchronized
    fun playOneShot(trackId: AudioTrackId) {
        playOneShot(null, trackId)
    }

    @Synchronized
    fun playOneShot(context: Context? = null, trackId: AudioTrackId) {
        if (!_soundEnabled.value) return
        val now = System.currentTimeMillis()
        if (lastOneShotTrack == trackId && now - lastOneShotTimeMs < 100L) {
            return // Prevent duplicate trigger from accidental rapid hardware/UI bounce
        }
        lastOneShotTrack = trackId
        lastOneShotTimeMs = now

        val ctx = context ?: appContext ?: return

        try {
            val resId = ctx.resources.getIdentifier(trackId.rawName, "raw", ctx.packageName)
            if (resId != 0) {
                try {
                    oneShotPlayer?.reset()
                    oneShotPlayer?.release()
                } catch (_: Exception) {}
                oneShotPlayer = createPlayerForRaw(ctx, resId, AudioAttributes.USAGE_ASSISTANCE_SONIFICATION) { mp ->
                    if (oneShotPlayer == mp) {
                        oneShotPlayer = null
                    }
                }?.apply {
                    setOnCompletionListener { mp ->
                        try {
                            mp.reset()
                            mp.release()
                        } catch (_: Exception) {}
                        if (oneShotPlayer == mp) {
                            oneShotPlayer = null
                        }
                    }
                    start()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "One-shot playback error for ${trackId.rawName}: ${e.message}")
        }
    }

    fun playTrack(context: Context, trackId: AudioTrackId, isLooping: Boolean = false, volume: Float = 1.0f) {
        if (!_soundEnabled.value) return
        SadhakTextReader.stop()

        if (_currentTrack.value == trackId && mediaPlayer != null) {
            setVolume(volume)
            if (!_isPlaying.value) {
                try {
                    if (requestAudioFocus()) {
                        mediaPlayer?.start()
                        _isPlaying.value = true
                        startProgressUpdate()
                    }
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
            if (requestAudioFocus()) {
                mediaPlayer = createPlayerForRaw(context, resId, AudioAttributes.USAGE_MEDIA) { mp ->
                    if (mediaPlayer == mp) {
                        mediaPlayer = null
                    }
                    _isPlaying.value = false
                    _isBuffering.value = false
                }?.apply {
                    setVolume(volume, volume)
                    this.isLooping = isLooping || _isRepeatEnabled.value
                    _durationStr.value = formatTime(duration)
                    _isBuffering.value = false
                    _isPlaying.value = true
                    setOnCompletionListener {
                        if (!_isRepeatEnabled.value && !isLooping) {
                            playNext(context)
                        }
                    }
                    start()
                    startProgressUpdate()
                }
                if (mediaPlayer == null) {
                    _isBuffering.value = false
                    _isPlaying.value = false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error playing track ${trackId.rawName}", e)
            try {
                mediaPlayer?.let { mp ->
                    if (mp.isPlaying) mp.stop()
                    mp.reset()
                    mp.release()
                }
            } catch (_: Throwable) {}
            mediaPlayer = null
            _isBuffering.value = false
            _isPlaying.value = false
        }
    }

    fun playNext(context: Context? = null) {
        val ctx = context ?: appContext ?: return
        val current = _currentTrack.value ?: return
        val q = _queue.value
        val index = q.indexOf(current)
        if (index != -1 && q.isNotEmpty()) {
            val nextIndex = (index + 1) % q.size
            playTrack(ctx, q[nextIndex])
        }
    }

    fun playPrevious(context: Context? = null) {
        val ctx = context ?: appContext ?: return
        val current = _currentTrack.value ?: return
        val q = _queue.value
        val index = q.indexOf(current)
        if (index != -1 && q.isNotEmpty()) {
            val prevIndex = if (index - 1 < 0) q.size - 1 else index - 1
            playTrack(ctx, q[prevIndex])
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
        SadhakTextReader.stop()
        try {
            if (mediaPlayer != null && !_isPlaying.value) {
                if (requestAudioFocus()) {
                    mediaPlayer?.start()
                    _isPlaying.value = true
                    startProgressUpdate()
                }
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
            mediaPlayer?.let { mp ->
                try {
                    if (mp.isPlaying) mp.stop()
                } catch (_: Throwable) {}
                try { mp.reset() } catch (_: Throwable) {}
                try { mp.release() } catch (_: Throwable) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping audio", e)
        } finally {
            mediaPlayer = null
            _currentTrack.value = null
        }
        try {
            oneShotPlayer?.let { mp ->
                try {
                    if (mp.isPlaying) mp.stop()
                } catch (_: Throwable) {}
                try { mp.reset() } catch (_: Throwable) {}
                try { mp.release() } catch (_: Throwable) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping oneShotPlayer", e)
        } finally {
            oneShotPlayer = null
        }
        abandonAudioFocus()
    }

    fun setVolume(volume: Float) {
        _volume.value = volume
        if (_isMuted.value) return
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
        stopLoginAmbient()
        unregisterNoisyReceiver(appContext)
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
