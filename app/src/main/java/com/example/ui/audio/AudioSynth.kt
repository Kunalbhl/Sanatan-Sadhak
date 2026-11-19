package com.example.ui.audio

import android.content.Context
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Generates the app's ambient sounds on the device (Om drone, temple bell, chime, flute) as valid WAV files.
 * Generating them in code keeps the app free of binary audio assets that can be corrupted by text-based
 * syncing, and means every sound is always playable. Recorded mantras are not bundled.
 */
object AudioSynth {
    private const val SR = 22050
    private const val VERSION = "v2"
    val supported = setOf("om_ambient", "temple_bell", "sadhana_success", "flute_ambient")

    fun supports(rawName: String) = rawName in supported

    fun ensure(context: Context, rawName: String): File? {
        if (!supports(rawName)) return null
        val dir = File(context.cacheDir, "synth_audio_$VERSION").apply { mkdirs() }
        val file = File(dir, "$rawName.wav")
        if (file.exists() && file.length() > 1000) return file
        val samples = when (rawName) {
            "om_ambient" -> om()
            "temple_bell" -> bell()
            "sadhana_success" -> chime()
            else -> flute()
        }
        writeWav(file, samples)
        return file
    }

    private fun om(): DoubleArray {
        val seconds = 20 // all component frequencies are multiples of 1/20 Hz so the loop is seamless
        val n = SR * seconds
        val f0 = 136.10
        val harmonics = listOf(1 to 1.0, 2 to 0.7, 3 to 0.45, 4 to 0.3, 5 to 0.18, 6 to 0.12, 8 to 0.07)
        return DoubleArray(n) { i ->
            val t = i.toDouble() / SR
            var x = 0.0
            for ((k, a) in harmonics) x += a * sin(2 * PI * f0 * k * t + 0.3 * sin(2 * PI * 0.2 * t + k))
            x += 0.35 * sin(2 * PI * (f0 / 2) * t)
            x += 0.25 * sin(2 * PI * f0 * 1.5 * t)
            x * (0.8 + 0.2 * sin(2 * PI * 0.15 * t))
        }
    }

    private fun bell(): DoubleArray {
        val partials = listOf(Triple(520.0, 1.0, 1.8), Triple(1040.0, 0.6, 1.2), Triple(1560.0, 0.35, 0.9),
            Triple(2180.0, 0.25, 0.6), Triple(2790.0, 0.15, 0.4), Triple(528.0, 0.5, 2.4))
        return DoubleArray(SR * 5) { i ->
            val t = i.toDouble() / SR
            var x = 0.0
            for ((f, a, d) in partials) x += a * exp(-t / d) * sin(2 * PI * f * t)
            x * min(1.0, t / 0.004)
        }
    }

    private fun chime(): DoubleArray {
        val out = DoubleArray((SR * 1.8).toInt())
        listOf(659.25, 783.99, 1046.5).forEachIndexed { idx, f ->
            val start = (idx * 0.18 * SR).toInt()
            val len = (1.2 * SR).toInt()
            for (j in 0 until len) {
                val p = start + j
                if (p >= out.size) break
                val t = j.toDouble() / SR
                out[p] += exp(-t / 0.5) * (sin(2 * PI * f * t) + 0.3 * sin(2 * PI * 2 * f * t))
            }
        }
        return out
    }

    private fun flute(): DoubleArray {
        // Bhoopali-style pentatonic phrase on C
        val phrase = listOf(261.63 to 2.5, 293.66 to 1.5, 329.63 to 2.5, 392.0 to 2.0, 440.0 to 3.0, 392.0 to 1.5,
            329.63 to 2.5, 293.66 to 1.5, 261.63 to 3.5, 329.63 to 2.0, 392.0 to 2.5, 523.25 to 3.0, 440.0 to 2.0,
            392.0 to 2.0, 329.63 to 3.5)
        val rnd = java.util.Random(1)
        val out = ArrayList<Double>()
        for ((f, d) in phrase) {
            val n = (d * SR).toInt()
            var phase = 0.0
            for (i in 0 until n) {
                val t = i.toDouble() / SR
                phase += 2 * PI * f * (1 + 0.006 * sin(2 * PI * 5.2 * t)) / SR
                val y = sin(phase) + 0.25 * sin(2 * phase) + 0.08 * sin(3 * phase) + 0.04 * rnd.nextGaussian()
                out.add(y * min(1.0, t / 0.15) * max(0.0, min(1.0, (d - t) / 0.35)))
            }
        }
        return out.toDoubleArray()
    }

    private fun writeWav(file: File, samples: DoubleArray) {
        val peak = max(1e-9, samples.maxOf { kotlin.math.abs(it) })
        val data = ByteBuffer.allocate(samples.size * 2).order(ByteOrder.LITTLE_ENDIAN)
        for (s in samples) data.putShort((s / peak * 0.85 * 32767).toInt().toShort())
        val header = ByteBuffer.allocate(44).order(ByteOrder.LITTLE_ENDIAN)
        header.put("RIFF".toByteArray()).putInt(36 + data.capacity()).put("WAVE".toByteArray())
        header.put("fmt ".toByteArray()).putInt(16).putShort(1).putShort(1).putInt(SR).putInt(SR * 2).putShort(2).putShort(16)
        header.put("data".toByteArray()).putInt(data.capacity())
        file.outputStream().use { it.write(header.array()); it.write(data.array()) }
    }
}
