package com.example

import com.example.data.PanchangTimeline
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PanchangTimelineTest {
    private val tz = 5.5

    @Test fun everyDayHasContiguousSegmentsCoveringTheWholeDay() {
        for (day in 1..28) {
            for (limb in 0..4) {
                val segs = PanchangTimeline.segments(limb, 2026, 11, day, tz)
                assertTrue("limb $limb day $day has no segment", segs.isNotEmpty())
                assertTrue("first segment must start by midnight", segs.first().startMin <= 0)
                assertTrue("last segment must reach midnight", segs.last().endMin >= 1440)
                for (i in 1 until segs.size) {
                    assertEquals("gap between segments (limb $limb, day $day)", segs[i - 1].endMin, segs[i].startMin)
                }
            }
        }
    }

    @Test fun aTithiLastsAboutADayAndSomeDaysHoldTwo() {
        var daysWithTwoTithis = 0
        for (day in 1..30) {
            val t = PanchangTimeline.segments(0, 2026, 11, day, tz)
            if (t.size >= 2) daysWithTwoTithis++
            t.forEach { s ->
                if (s.startMin > -9000 && s.endMin < 90000) {
                    val hours = (s.endMin - s.startMin) / 60.0
                    assertTrue("tithi length $hours h out of range", hours in 14.0..28.0)
                }
            }
        }
        assertTrue("expected several days with two tithis in a month, got $daysWithTwoTithis", daysWithTwoTithis in 5..25)
    }

    @Test fun dayPartsAreOrderedAndNamed() {
        val parts = PanchangTimeline.dayParts(6 * 60 + 40, 17 * 60 + 25)
        assertEquals(5, parts.size)
        assertEquals("Brahma Muhurta", parts.first().first)
    }
}
