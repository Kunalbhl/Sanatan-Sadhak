package com.example

import com.example.ui.audio.AudioCategory
import com.example.ui.audio.AudioTrackId
import com.example.data.DeityGreetingEngine
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class Task79UnitTests {

    @Test
    fun testDeityGreetingMorningSlot() {
        val cal = Calendar.getInstance()
        // Monday morning (9 AM)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.HOUR_OF_DAY, 9)
        val greetingHi = DeityGreetingEngine.getGreeting(cal, isEnglish = false)
        val greetingEn = DeityGreetingEngine.getGreeting(cal, isEnglish = true)

        assertEquals("जय श्री महाकाल 🕉", greetingHi)
        assertEquals("Jai Shree Mahakaal 🕉", greetingEn)
    }

    @Test
    fun testDeityGreetingEveningSlot() {
        val cal = Calendar.getInstance()
        // Monday evening (6 PM)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        cal.set(Calendar.HOUR_OF_DAY, 18)
        val greetingHi = DeityGreetingEngine.getGreeting(cal, isEnglish = false)
        val greetingEn = DeityGreetingEngine.getGreeting(cal, isEnglish = true)

        assertEquals("हर हर महादेव 🔱", greetingHi)
        assertEquals("Har Har Mahadev 🔱", greetingEn)
    }

    @Test
    fun testTuesdayGreeting() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        cal.set(Calendar.HOUR_OF_DAY, 8)
        val greeting = DeityGreetingEngine.getGreeting(cal, isEnglish = true)
        assertEquals("Jai Bajrangbali 🚩", greeting)
    }

    @Test
    fun testAudioTrackPlayableListAndCategories() {
        val playable = AudioTrackId.playableTracks()
        assertTrue(playable.isNotEmpty())
        assertFalse(playable.contains(AudioTrackId.TEMPLE_BELL))
        assertFalse(playable.contains(AudioTrackId.SADHANA_SUCCESS))

        val mantras = playable.filter { it.category == AudioCategory.MANTRA }
        assertTrue(mantras.isNotEmpty())
        assertTrue(mantras.contains(AudioTrackId.GAYATRI_MANTRA))

        val stotrams = playable.filter { it.category == AudioCategory.DEVOTIONAL }
        assertTrue(stotrams.isNotEmpty())
        assertTrue(stotrams.contains(AudioTrackId.HANUMAN_CHALISA))
    }
}
