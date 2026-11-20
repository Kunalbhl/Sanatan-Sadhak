package com.example

import com.example.data.AstrologyService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

/** The festival rules must land on (or within a day of) the published 2026 dates for New Delhi. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class FestivalDatesTest {
    private fun festivalOn(y: Int, m: Int, d: Int): String = runBlocking {
        AstrologyService.fetchRealPanchang(d, m, y, 6, 0, 0, 28.6139, 77.2090, 5.5).data?.festival ?: "(none)"
    }

    private fun check(keyword: String, y: Int, m: Int, d: Int) {
        val cal = Calendar.getInstance().apply { clear(); set(y, m - 1, d) }
        val seen = mutableListOf<String>()
        for (delta in -1..1) {
            val c = (cal.clone() as Calendar).apply { add(Calendar.DAY_OF_YEAR, delta) }
            val f = festivalOn(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH))
            seen += f
            if (f.contains(keyword, ignoreCase = true)) return
        }
        throw AssertionError("$keyword not found within a day of $y-$m-$d; saw $seen")
    }

    @Test fun majorFestivals2026() {
        check("Sankranti", 2026, 1, 14)
        check("Shivaratri", 2026, 2, 15)
        check("Holi", 2026, 3, 4)
        check("Ram Navami", 2026, 3, 26)
        check("Hanuman Jayanti", 2026, 4, 2)
        check("Akshaya Tritiya", 2026, 4, 19)
        check("Guru Purnima", 2026, 7, 29)
        check("Raksha Bandhan", 2026, 8, 28)
        check("Janmashtami", 2026, 9, 4)
        check("Ganesh Chaturthi", 2026, 9, 14)
        check("Navratri", 2026, 10, 11)
        check("Dussehra", 2026, 10, 20)
        check("Diwali", 2026, 11, 8)
    }

    /** Ekadashi comes twice a lunar month: expect about 24 in a year, each with its own name and ~14-16 days apart. */
    @Test fun ekadashiThroughout2026() {
        val cal = Calendar.getInstance().apply { clear(); set(2026, 0, 1) }
        val days = mutableListOf<Int>()
        val names = mutableSetOf<String>()
        for (i in 0 until 365) {
            val f = festivalOn(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
            if (f.contains("Ekadashi", ignoreCase = true)) { days += i; names += f }
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        // A long Ekadashi can be running at two consecutive sunrises: count that as one observance.
        val groups = days.fold(mutableListOf<Int>()) { acc, d -> if (acc.isEmpty() || d - acc.last() > 1) acc.add(d); acc }
        assertTrue("Expected 22..27 Ekadashi observances, got ${groups.size}: $names", groups.size in 22..27)
        assertTrue("Expected many distinct named Ekadashis, got ${names.size}: $names", names.size >= 18)
        for ((a, b) in groups.zipWithNext()) assertTrue("Ekadashis too close: day $a and $b", b - a >= 12)
    }
}
