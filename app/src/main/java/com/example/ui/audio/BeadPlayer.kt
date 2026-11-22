package com.example.ui.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

/**
 * The soft wooden "tock" of one mala bead sliding past the thumb. SoundPool lets quick taps overlap,
 * and a tiny random pitch change per bead keeps a long japa from sounding like a loop.
 */
object BeadPlayer {
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
                val file = AudioSynth.ensure(app, "bead_click")
                if (file == null) { loading = false; return@Thread }
                val sp = SoundPool.Builder()
                    .setMaxStreams(6)
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
                android.util.Log.w("BeadPlayer", "init failed: ${e.message}")
                loading = false
            }
        }.start()
    }

    fun click() {
        if (!SadhakAudioManager.soundEnabled.value) return
        val sp = pool ?: return
        if (!loaded) return
        val vol = 0.55f + rnd.nextFloat() * 0.25f
        sp.play(soundId, vol, vol, 1, 0, 0.94f + rnd.nextFloat() * 0.12f)
    }

    fun release() {
        try { pool?.release() } catch (_: Throwable) {}
        pool = null
        loaded = false
        loading = false
    }
}
