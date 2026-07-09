package com.example.ui.screens

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.sin

object AmbientSoundPlayer {
    private var audioTrack: AudioTrack? = null
    private var isPlaying = false
    private var playThread: Thread? = null

    fun playSound(frequency: Double = 432.0, volume: Float = 1.0f) {
        stopSound()
        isPlaying = true
        playThread = Thread {
            val sampleRate = 44100
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack?.setVolume(volume)
            audioTrack?.play()

            val buffer = ShortArray(bufferSize)
            var angle = 0.0
            val increment = 2.0 * Math.PI * frequency / sampleRate

            while (isPlaying) {
                for (i in buffer.indices) {
                    // Create a soothing pulsing effect
                    val pulse = sin(angle / 1000.0) * 0.5 + 0.5 
                    buffer[i] = (sin(angle) * Short.MAX_VALUE * 0.5 * pulse).toInt().toShort()
                    angle += increment
                }
                audioTrack?.write(buffer, 0, buffer.size)
            }

            audioTrack?.stop()
            audioTrack?.release()
            audioTrack = null
        }
        playThread?.start()
    }

    fun stopSound() {
        isPlaying = false
        playThread?.join(500)
        playThread = null
    }
}
