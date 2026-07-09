package com.example.ui.components

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

object AudioEngine {
    private var audioTrack: AudioTrack? = null
    private var playingJob: Job? = null
    private var isPlaying = false

    fun playAmbientSound(index: Int, volume: Float) {
        stopAmbientSound()
        if (index == 0) return // Silent

        val sampleRate = 44100
        val frequency = when(index) {
            1 -> 432.0 // Bells (Simulated with a bell-like frequency)
            2 -> 136.1 // Divine OM
            3 -> 528.0 // Flute (Love frequency)
            4 -> 100.0 // Noise/Ganga Flow (will just be low frequency hum here)
            else -> 136.1
        }

        isPlaying = true
        playingJob = CoroutineScope(Dispatchers.IO).launch {
            val minSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    android.media.AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(minSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
            audioTrack?.setVolume(volume)
            audioTrack?.play()

            val buffer = ShortArray(minSize)
            var angle = 0.0

            while (isPlaying) {
                for (i in buffer.indices) {
                    // Simple sine wave generation
                    val value = sin(angle)
                    buffer[i] = (value * Short.MAX_VALUE * 0.5).toInt().toShort()
                    
                    // Add some harmonics for OM
                    if (index == 2) {
                        val harmonic = sin(angle * 2.0)
                        buffer[i] = ((value + harmonic * 0.5) * Short.MAX_VALUE * 0.3).toInt().toShort()
                    }
                    
                    angle += 2.0 * Math.PI * frequency / sampleRate
                    if (angle > 2.0 * Math.PI) {
                        angle -= 2.0 * Math.PI
                    }
                }
                audioTrack?.write(buffer, 0, buffer.size)
            }
            
            audioTrack?.stop()
            audioTrack?.release()
            audioTrack = null
        }
    }

    fun stopAmbientSound() {
        isPlaying = false
        playingJob?.cancel()
        playingJob = null
    }
}
