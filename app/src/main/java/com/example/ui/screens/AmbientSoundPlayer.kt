package com.example.ui.screens

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.sin
import kotlin.math.exp

object AmbientSoundPlayer {
    private var audioTrack: AudioTrack? = null
    private var isPlaying = false
    private var playThread: Thread? = null

    fun playSound(soundType: Int, volume: Float = 1.0f) {
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
            
            // Synthesis state variables
            var sampleCount = 0L
            var omPhase = 0.0
            var omPhaseSub = 0.0
            var flutePhase = 0.0
            
            // Rain / River pink noise generator state
            var b0 = 0.0
            var b1 = 0.0
            var b2 = 0.0
            var b3 = 0.0
            var b4 = 0.0
            var b5 = 0.0
            var b6 = 0.0

            while (isPlaying) {
                audioTrack?.setVolume(volume) // Keep volume updated dynamically
                for (i in buffer.indices) {
                    val t = sampleCount.toDouble() / sampleRate
                    var finalSample = 0.0

                    when (soundType) {
                        1 -> { // 1. TEMPLE BELLS (Periodic majestic strikes)
                            // Strike period = 4.0 seconds
                            val period = 4.0
                            val tInPeriod = t % period
                            
                            // Exponential decay of strike
                            val decay = exp(-1.2 * tInPeriod)
                            
                            // Rich overtones of a large heavy bronze temple bell
                            val f0 = 220.0 // G3/A3 note fundamental
                            val harmonics = doubleArrayOf(1.0, 1.01, 1.2, 1.5, 2.0, 2.5, 3.0, 4.2)
                            val amplitudes = doubleArrayOf(1.0, 0.7, 0.4, 0.3, 0.25, 0.15, 0.1, 0.05)
                            
                            var bellSynth = 0.0
                            for (h in harmonics.indices) {
                                val freq = f0 * harmonics[h]
                                bellSynth += amplitudes[h] * sin(2.0 * Math.PI * freq * tInPeriod)
                            }
                            
                            finalSample = bellSynth * decay * 0.4
                        }
                        2 -> { // 2. DIVINE OM CHANTING (Tibetan resonant monk choir style)
                            // Om cycle period = 6.0 seconds
                            val period = 6.0
                            val tInPeriod = t % period
                            
                            val fundamentalFreq = 136.1 // Sacred cosmic frequency
                            
                            // Smooth raised-cosine-like bell curve envelope for natural breathing and chanting cycles
                            val envelope = if (tInPeriod < 1.2) {
                                0.0
                            } else if (tInPeriod < 5.4) {
                                val chantTime = tInPeriod - 1.2
                                val duration = 4.2
                                sin(Math.PI * (chantTime / duration))
                            } else {
                                0.0
                            }
                            
                            if (envelope > 0.0) {
                                val chantTime = tInPeriod - 1.2
                                val duration = 4.2
                                val progress = chantTime / duration
                                
                                // Vowel morphing: "Aaaa" -> "Oooo" -> "Mmmm"
                                val wA = if (progress < 0.3) {
                                    1.0 - (progress / 0.3)
                                } else 0.0
                                val wO = if (progress < 0.7) {
                                    if (progress < 0.3) progress / 0.3 else (0.7 - progress) / 0.4
                                } else 0.0
                                val wM = if (progress >= 0.3) {
                                    if (progress < 0.7) (progress - 0.3) / 0.4 else 1.0
                                } else 0.0
                                
                                // Synthesize fundamental + deep sub-bass octave + harmonics
                                var omSynth = 0.0
                                val harmonics = doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)
                                val ampA = doubleArrayOf(1.0, 0.8, 0.6, 0.4, 0.2, 0.1)
                                val ampO = doubleArrayOf(1.0, 0.5, 0.25, 0.1, 0.05, 0.02)
                                val ampM = doubleArrayOf(1.0, 0.1, 0.02, 0.0, 0.0, 0.0)
                                
                                omPhase += 2.0 * Math.PI * fundamentalFreq / sampleRate
                                omPhaseSub += 2.0 * Math.PI * (fundamentalFreq / 2.0) / sampleRate
                                
                                // 1. Deep sub-octave drone for that powerful meditative chest resonance
                                omSynth += 0.5 * sin(omPhaseSub)
                                
                                // 2. Formant harmonic synthesis with chorus detuning
                                for (h in harmonics.indices) {
                                    val amp = (ampA[h] * wA + ampO[h] * wO + ampM[h] * wM)
                                    val phaseVal = omPhase * harmonics[h]
                                    omSynth += amp * sin(phaseVal)
                                    // Soft chorus sidebands
                                    omSynth += amp * 0.25 * sin(phaseVal + 0.05 * t)
                                }
                                
                                finalSample = omSynth * envelope * 0.35
                            }
                        }
                        3 -> { // 3. COSMIC FLUTE (Warm pure wooden tone with sweet vibrato)
                            // Melodic sequence of notes in meditative Raga Bhoopali scale
                            val melodyPeriod = 12.0
                            val tInMelody = t % melodyPeriod
                            
                            val notes = doubleArrayOf(587.33, 659.25, 698.46, 783.99, 880.00, 783.99, 698.46, 587.33)
                            val noteDuration = melodyPeriod / notes.size
                            val currentNoteIndex = (tInMelody / noteDuration).toInt() % notes.size
                            val baseFreq = notes[currentNoteIndex]
                            
                            // Sweet 5.2Hz vibrato
                            val vibratoFreq = 5.2
                            val vibratoAmount = 0.012
                            val modFreq = baseFreq * (1.0 + vibratoAmount * sin(2.0 * Math.PI * vibratoFreq * t))
                            
                            // Breath blow envelope
                            val noteT = tInMelody % noteDuration
                            val noteEnv = sin(Math.PI * (noteT / noteDuration))
                            
                            // Continuous phase increment (completely removes clicking noise!)
                            flutePhase += 2.0 * Math.PI * modFreq / sampleRate
                            
                            // Sine wave fundamental + woody even & odd harmonics
                            var fluteSynth = sin(flutePhase)
                            fluteSynth += 0.12 * sin(flutePhase * 2.0)
                            fluteSynth += 0.05 * sin(flutePhase * 3.0)
                            
                            // Bamboo blowing breath noise simulation (pink-noise filtered)
                            val whiteNoise = (kotlin.random.Random.nextDouble() * 2.0 - 1.0)
                            b0 = 0.95 * b0 + whiteNoise * 0.05
                            fluteSynth += b0 * 0.05
                            
                            finalSample = fluteSynth * noteEnv * 0.3
                        }
                        4 -> { // 4. GANGA FLOW / RIVER (Dynamic pink-noise waves)
                            val white = kotlin.random.Random.nextDouble() * 2.0 - 1.0
                            b0 = 0.99886 * b0 + white * 0.0555179
                            b1 = 0.99332 * b1 + white * 0.0750759
                            b2 = 0.96900 * b2 + white * 0.1538520
                            b3 = 0.86650 * b3 + white * 0.3104856
                            b4 = 0.55000 * b4 + white * 0.5329522
                            b5 = -0.7616 * b5 - white * 0.0168980
                            var pink = b0 + b1 + b2 + b3 + b4 + b5 + b6 + white * 0.5362
                            b6 = white * 0.115926
                            pink *= 0.11
                            
                            val waveSwell = sin(2.0 * Math.PI * 0.08 * t) * 0.3 + 0.7
                            finalSample = (pink * waveSwell) * 0.65
                        }
                        5 -> { // 5. GENTLE RAIN (High frequency white-noise focus)
                            val white = kotlin.random.Random.nextDouble() * 2.0 - 1.0
                            var rainTick = 0.0
                            if (kotlin.random.Random.nextDouble() < 0.003) {
                                rainTick = (kotlin.random.Random.nextDouble() * 2.0 - 1.0) * 0.3
                            }
                            finalSample = (white * 0.05 + rainTick) * 0.7
                        }
                        6 -> { // 6. MORNING BIRDS (Random high-pitched sine chirps)
                            val inChirp = t % 3.0 < 0.2
                            if (inChirp) {
                                val chirpFreq = 3000.0 + sin(2.0 * Math.PI * 15.0 * t) * 500.0
                                finalSample = sin(2.0 * Math.PI * chirpFreq * t) * 0.15
                            } else {
                                finalSample = 0.0
                            }
                        }
                        7 -> { // 7. HIMALAYAN WIND (Low-frequency whistling noise)
                            val white = kotlin.random.Random.nextDouble() * 2.0 - 1.0
                            val windSwell = sin(2.0 * Math.PI * 0.05 * t) * 0.5 + 0.5
                            val filterFreq = 400.0 + windSwell * 600.0
                            
                            b0 = 0.9 * b0 + white * 0.1
                            b1 = 0.9 * b1 + b0 * 0.1
                            
                            finalSample = b1 * 0.5 * windSwell
                        }
                        else -> { // SILENT / DEFAULT
                            finalSample = 0.0
                        }
                    }

                    // Write 16-bit PCM output sample
                    buffer[i] = (finalSample * Short.MAX_VALUE).toInt().coerceIn(-32768, 32767).toShort()
                    sampleCount++
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
