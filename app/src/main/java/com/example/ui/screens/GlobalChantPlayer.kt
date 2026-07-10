package com.example.ui.screens

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.sin

@Composable
fun GlobalChantPlayer(enabled: Boolean, volume: Float) {
    LaunchedEffect(enabled, volume) {
        if (!enabled) return@LaunchedEffect

        launch(Dispatchers.IO) {
            val sampleRate = 44100
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM
            )

            audioTrack.play()
            val buffer = ShortArray(bufferSize)
            var t = 0.0

            try {
                while (isActive) {
                    for (i in buffer.indices) {
                        t += 1.0 / sampleRate
                        // Om frequency ~ 136.1 Hz
                        val baseFreq = 136.1
                        val sine1 = sin(2.0 * Math.PI * baseFreq * t)
                        val sine2 = sin(2.0 * Math.PI * (baseFreq * 2) * t) * 0.3
                        val sine3 = sin(2.0 * Math.PI * (baseFreq * 3) * t) * 0.1
                        
                        // Slow modulation for breathing effect
                        val swell = sin(2.0 * Math.PI * 0.05 * t) * 0.4 + 0.6
                        
                        val sample = (sine1 + sine2 + sine3) * swell * volume * 0.3
                        buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                    }
                    audioTrack.write(buffer, 0, buffer.size)
                }
            } finally {
                audioTrack.stop()
                audioTrack.release()
            }
        }
    }
}
