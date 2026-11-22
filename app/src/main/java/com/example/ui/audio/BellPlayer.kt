package com.example.ui.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

/**
 * Plays the temple bell so that rings overlap and ring out naturally (a real bell does not cut off
 * when struck again). Uses SoundPool with several simultaneous streams and a tiny random pitch
 * and volume variation per strike so repeated rings never sound like a loop.
 */
object BellPlayer {
    private var pool: SoundPool? = null
    private var soundId = 0
    @Volatile private var loaded = false
    @Volatile private var loading = false
    private val rnd = java.util.Random()

    fun init(context: Context) {
        if (loaded || loading) return
        loading = true
        val app = context.applicationContext
        Thread {
            try {
                val file = AudioSynth.ensure(app, "temple_bell")
                if (file == null) { loading = false; return@Thread }
                val sp = SoundPool.Builder()
                    .setMaxStreams(8)
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    ).build()
                sp.setOnLoadCompleteListener { _, _, status -> loaded = status == 0; loading = false }
                soundId = sp.load(file.path, 1)
                pool = sp
            } catch (e: Throwable) {
                android.util.Log.w("BellPlayer", "init failed: ${e.message}")
                loading = false
            }
        }.start()
    }

    /** @param strength 0..1, how hard the bell is struck */
    fun ring(strength: Float = 1f) {
        if (!SadhakAudioManager.soundEnabled.value) return
        val sp = pool ?: return
        if (!loaded) return
        val vol = (0.55f + 0.45f * strength.coerceIn(0f, 1f)) * (0.94f + rnd.nextFloat() * 0.06f)
        val rate = 0.985f + rnd.nextFloat() * 0.03f
        sp.play(soundId, vol, vol, 1, 0, rate)
    }

    fun release() {
        try { pool?.release() } catch (_: Throwable) {}
        pool = null
        loaded = false
        loading = false
    }
}
