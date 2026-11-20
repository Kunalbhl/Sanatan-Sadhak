package com.example

import com.example.ui.audio.AudioSynth
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs
import kotlin.math.sqrt

class OmChantSynthTest {
    @Test fun omIsALongSeamlessLoopWithAChantShape() {
        val x = AudioSynth.om()
        val sr = 22050
        assertEquals("three 20 s breaths", sr * 60, x.size)
        assertFalse("no NaN or infinity", x.any { it.isNaN() || it.isInfinite() })
        val peak = x.maxOf { abs(it) }
        assertTrue(peak > 0.01)

        fun rms(from: Int, to: Int): Double { var s = 0.0; for (i in from until to) s += x[i] * x[i]; return sqrt(s / (to - from)) / peak }

        // seam: the last 50 ms and the first 50 ms must be the same kind of signal, and the join must not jump
        val w = sr / 20
        val seamJump = abs(x[x.size - 1] - x[0]) / peak
        assertTrue("seam jump $seamJump", seamJump < 0.12)
        val endRms = rms(x.size - w, x.size); val startRms = rms(0, w)
        assertTrue("seam energy mismatch $endRms vs $startRms", abs(endRms - startRms) < 0.08)

        // it must breathe: loud chanting (the long M of the first breath) clearly louder than the pause between breaths
        val chant = rms(sr * 13, sr * 16)
        val pause = rms(sr * 19, sr * 20 - 1)
        assertTrue("chant $chant should exceed pause $pause", chant > pause * 1.2)
        assertTrue("chant should be audible, rms $chant", chant > 0.05)
    }
}
