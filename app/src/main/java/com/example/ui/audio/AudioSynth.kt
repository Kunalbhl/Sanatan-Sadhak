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
    private const val VERSION = "v3"
    val supported = setOf("om_ambient", "temple_bell", "sadhana_success", "flute_ambient")

    fun supports(rawName: String) = rawName in supported

    private fun fileFor(context: Context, rawName: String) =
        File(File(context.cacheDir, "synth_audio_$VERSION"), "$rawName.wav")

    fun isReady(context: Context, rawName: String): Boolean =
        fileFor(context, rawName).let { it.exists() && it.length() > 1000 }

    @Volatile private var warming = false

    /** Builds every sound on a background thread (the Om takes a few seconds the first time), then calls onReady on the main thread. */
    fun prewarm(context: Context, onReady: (() -> Unit)? = null) {
        val app = context.applicationContext
        if (supported.all { isReady(app, it) }) {
            onReady?.let { android.os.Handler(android.os.Looper.getMainLooper()).post(it) }
            return
        }
        if (warming) return
        warming = true
        Thread {
            try {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
                File(app.cacheDir, "synth_audio_v2").deleteRecursively()
                for (name in supported) ensure(app, name)
            } catch (e: Throwable) {
                android.util.Log.w("AudioSynth", "prewarm failed: ${e.message}")
            } finally {
                warming = false
            }
            onReady?.let { android.os.Handler(android.os.Looper.getMainLooper()).post(it) }
        }.start()
    }

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

    // ---------------------------------------------------------------------------------------------
    // Om: a meditator chanting "A-U-M" in long breaths over a tanpura, inside a stone hall.
    // Voice = glottal source shaped by moving vowel formants (A → U → M), two slightly detuned voices,
    // vibrato + drift, breath noise. Tanpura = four plucked strings with jivari buzz. Hall = Schroeder
    // reverb run twice around the loop so the end flows into the start without a seam.
    // ---------------------------------------------------------------------------------------------
    private const val TWO_PI = 2 * PI
    private const val TABLE = 4096
    private val sinTable = DoubleArray(TABLE + 1) { sin(TWO_PI * it / TABLE) }
    private fun fastSin(phase: Double): Double {
        val x = (phase / TWO_PI) % 1.0
        val p = (if (x < 0) x + 1.0 else x) * TABLE
        val i = p.toInt()
        val f = p - i
        return sinTable[i] * (1 - f) + sinTable[i + 1] * f
    }

    private fun smooth(x: Double): Double { val c = x.coerceIn(0.0, 1.0); return c * c * (3 - 2 * c) }

    private class Vowel(val f: DoubleArray, val g: DoubleArray, val bw: DoubleArray)
    private val vowelA = Vowel(doubleArrayOf(730.0, 1090.0, 2440.0), doubleArrayOf(1.0, 0.55, 0.22), doubleArrayOf(90.0, 110.0, 160.0))
    private val vowelU = Vowel(doubleArrayOf(320.0, 870.0, 2250.0), doubleArrayOf(1.0, 0.30, 0.10), doubleArrayOf(70.0, 100.0, 150.0))
    private val vowelM = Vowel(doubleArrayOf(260.0, 1100.0, 2400.0), doubleArrayOf(1.0, 0.10, 0.03), doubleArrayOf(60.0, 120.0, 200.0))

    /** Spectral weight of a harmonic at frequency hz for a blend of vowels (wa + wu + wm = 1). */
    private fun vocalGain(hz: Double, wa: Double, wu: Double, wm: Double): Double {
        var g = 0.0
        for ((v, w) in listOf(vowelA to wa, vowelU to wu, vowelM to wm)) {
            if (w <= 0.0) continue
            var sum = 0.0
            for (k in 0..2) {
                val d = (hz - v.f[k]) / v.bw[k]
                sum += v.g[k] / (1.0 + d * d)
            }
            g += w * sum
        }
        return g
    }

    /** One 20 s breath: inhale, A, A→U, U, U→M, M that fades out. Returns the voiced + breath signal. */
    private fun chantCycle(f0: Double, detune: Double, vibPhase: Double, level: Double, seed: Long): DoubleArray {
        val cycleSeconds = 20
        val n = SR * cycleSeconds
        val out = DoubleArray(n)
        val rnd = java.util.Random(seed)
        val harmonics = 20
        val amps = DoubleArray(harmonics + 1)
        var phase = rnd.nextDouble() * TWO_PI
        var drift = 0.0
        var noiseLp = 0.0
        var ampSmooth = 0.0
        for (i in 0 until n) {
            val t = i.toDouble() / SR
            // Vowel path
            val aU = smooth((t - 6.0) / 1.5)
            val uM = smooth((t - 10.5) / 1.5)
            val wa = 1.0 - aU
            val wu = aU * (1.0 - uM)
            val wm = uM
            // Loudness: attack at 2.0 s, swells into U and M, long release 17.0 → 18.8 s
            val attack = smooth((t - 2.0) / 0.6)
            val release = 1.0 - smooth((t - 17.0) / 1.8)
            val swell = 0.8 + 0.2 * smooth((t - 6.0) / 3.0) + 0.1 * smooth((t - 12.0) / 2.0)
            val target = attack * release * swell
            ampSmooth += (target - ampSmooth) * 0.0006
            val voiced = ampSmooth

            // Pitch: gentle slow drift, vibrato that deepens while holding, small start glide
            if (i % 441 == 0) drift += (rnd.nextGaussian() * 0.0009 - drift * 0.05)
            val vibDepth = 0.002 + 0.006 * smooth((t - 4.0) / 6.0)
            val glide = 1.0 - 0.025 * (1.0 - smooth((t - 2.0) / 0.5))
            val intonation = 1.0 - 0.012 * smooth((t - 2.0) / 16.0) // settles slightly lower as the breath empties
            val f = f0 * detune * glide * intonation * (1.0 + drift + vibDepth * sin(TWO_PI * 5.1 * t + vibPhase))
            phase += TWO_PI * f / SR
            if (phase > TWO_PI * 1000) phase -= TWO_PI * 1000

            if (i % 64 == 0) {
                for (h in 1..harmonics) {
                    val hz = h * f
                    amps[h] = if (hz > 6000) 0.0 else vocalGain(hz, wa, wu, wm) / Math.pow(h.toDouble(), 1.15)
                }
            }
            var x = 0.0
            for (h in 1..harmonics) {
                if (amps[h] > 0.0) x += amps[h] * fastSin(h * phase)
            }
            // Breath: soft inhale before the chant and a little air riding on the voice
            val inhale = smooth(t / 1.0) * (1.0 - smooth((t - 1.9) / 0.5)) * 0.10
            val noise = rnd.nextGaussian()
            noiseLp += (noise - noiseLp) * 0.25
            val air = noiseLp * (inhale + 0.035 * voiced)
            out[i] = (x * voiced * 0.22 + air) * level
        }
        return out
    }

    private fun tanpura(totalSamples: Int, f0: Double): DoubleArray {
        // One 6 s round of four strings (Pa, Sa, Sa, low Sa), rendered so tails wrap inside the round,
        // then repeated: the round is exactly periodic, so the loop has no seam.
        val round = (6.0 * SR).toInt()
        val one = DoubleArray(round)
        val strings = doubleArrayOf(0.75 * f0, f0, f0, 0.5 * f0)
        val spacing = (1.5 * SR).toInt()
        val rnd = java.util.Random(7)
        for (p in 0 until 4) {
            val base = strings[p] * (1.0 + (rnd.nextDouble() - 0.5) * 0.0015)
            val start = p * spacing
            val len = (5.0 * SR).toInt()
            val gain = if (p == 3) 0.9 else 0.7
            for (h in 1..20) {
                val hz = base * h
                if (hz > 6000) break
                val amp = gain / h
                val tau = 3.6 / (1.0 + 0.16 * h)
                val decay = exp(-1.0 / (tau * SR))
                val ph = rnd.nextDouble() * TWO_PI
                var env = 1.0
                for (j in 0 until len) {
                    val t = j.toDouble() / SR
                    // jivari: the thread buzz makes the upper harmonics bloom shortly after the pluck
                    val d = (t - 0.75) / 0.6
                    val bloom = if (h >= 3) 1.0 + 1.4 * max(0.0, 1.0 - d * d) else 1.0
                    one[(start + j) % round] += amp * env * bloom * min(1.0, t / 0.004) * fastSin(TWO_PI * hz * t + ph)
                    env *= decay
                }
            }
        }
        return DoubleArray(totalSamples) { one[it % round] }
    }

    /** Schroeder hall reverb, processed over two passes of the loop so the tail wraps seamlessly. */
    private fun hall(input: DoubleArray): DoubleArray {
        val n = input.size
        val combDelays = intArrayOf((0.0297 * SR).toInt(), (0.0371 * SR).toInt(), (0.0411 * SR).toInt(), (0.0437 * SR).toInt(), (0.0521 * SR).toInt(), (0.0613 * SR).toInt())
        val g = 0.935
        val combs = Array(combDelays.size) { DoubleArray(combDelays[it]) }
        val combPos = IntArray(combDelays.size)
        val damp = DoubleArray(combDelays.size)
        val apDelays = intArrayOf((0.0050 * SR).toInt(), (0.0017 * SR).toInt())
        val aps = Array(2) { DoubleArray(apDelays[it]) }
        val apPos = IntArray(2)
        val out = DoubleArray(n)
        for (pass in 0..1) {
            for (i in 0 until n) {
                val x = input[i]
                var wet = 0.0
                for (c in combs.indices) {
                    val buf = combs[c]
                    val y = buf[combPos[c]]
                    damp[c] += (y - damp[c]) * 0.35 // darker with each bounce
                    buf[combPos[c]] = x + damp[c] * g
                    combPos[c] = (combPos[c] + 1) % buf.size
                    wet += y
                }
                wet /= combs.size
                for (a in 0..1) {
                    val buf = aps[a]
                    val b = buf[apPos[a]]
                    val v = wet - 0.5 * b
                    buf[apPos[a]] = v
                    wet = b + 0.5 * v
                    apPos[a] = (apPos[a] + 1) % buf.size
                }
                if (pass == 1) out[i] = wet
            }
        }
        return out
    }

    internal fun om(): DoubleArray {
        val f0 = 136.10 // the traditional "Om" tone
        val cycles = 3
        val cycleLen = SR * 20
        val n = cycleLen * cycles
        val voice = DoubleArray(n)
        val pitchMul = doubleArrayOf(1.0, 0.994, 1.007)
        val level = doubleArrayOf(1.0, 0.93, 1.0)
        for (c in 0 until cycles) {
            // two voices, slightly apart in pitch and vibrato, like two people chanting together
            val a = chantCycle(f0, pitchMul[c], 0.0, level[c], 11L + c)
            val b = chantCycle(f0, pitchMul[c] * 1.0035, 1.9, level[c] * 0.65, 91L + c)
            for (i in 0 until cycleLen) voice[c * cycleLen + i] = a[i] + b[i]
        }
        val drone = tanpura(n, f0)
        val dry = DoubleArray(n) { voice[it] + 0.16 * drone[it] }
        val wet = hall(dry)
        // Voice stays close and clear, the hall wraps around it; the drone sits behind
        return DoubleArray(n) { dry[it] * 0.75 + wet[it] * 0.9 }
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
