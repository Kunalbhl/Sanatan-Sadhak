package com.example

import com.example.data.AstrologyService
import com.example.data.DeityGreetingEngine
import com.example.ui.audio.AudioCategory
import com.example.ui.audio.AudioTrackId
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class Task80PanchangDeterministicTest {

    @Test
    fun testPanchangDeterministicCalculation() {
        runBlocking {
            val prevKey = AstrologyService.PANCHANG_API_KEY
            AstrologyService.PANCHANG_API_KEY = ""
            try {
                val lat = 28.6139
                val lon = 77.2090
                val tz = 5.5
                val day = 17
                val month = 8
                val year = 2026

                val result1 = AstrologyService.fetchRealPanchang(
                    day = day,
                    month = month,
                    year = year,
                    hours = 6,
                    minutes = 0,
                    seconds = 0,
                    latitude = lat,
                    longitude = lon,
                    timezone = tz
                )

                val result2 = AstrologyService.fetchRealPanchang(
                    day = day,
                    month = month,
                    year = year,
                    hours = 6,
                    minutes = 0,
                    seconds = 0,
                    latitude = lat,
                    longitude = lon,
                    timezone = tz
                )

                assertNotNull(result1.data)
                assertNotNull(result2.data)

                val p1 = result1.data!!
                val p2 = result2.data!!

                assertEquals("Tithi must match identically", p1.tithi, p2.tithi)
                assertEquals("Nakshatra must match identically", p1.nakshatra, p2.nakshatra)
                assertEquals("Yoga must match identically", p1.yoga, p2.yoga)
                assertEquals("Karana must match identically", p1.karana, p2.karana)
                assertEquals("Sunrise must match identically", p1.sunrise, p2.sunrise)
                assertEquals("Sunset must match identically", p1.sunset, p2.sunset)
                assertEquals("Rahu Kaal must match identically", p1.rahukaal, p2.rahukaal)
                assertEquals("Abhijit Muhurta must match identically", p1.abhijeet, p2.abhijeet)
                assertEquals("Source type must be deterministic", p1.sourceType, p2.sourceType)
            } finally {
                AstrologyService.PANCHANG_API_KEY = prevKey
            }
        }
    }

    @Test
    fun testDeityGreetingAllDaysAndSlots() {
        val days = listOf(
            Calendar.SUNDAY to ("Om Suryaya Namah ☀️" to "Jai Shree Ram 🏹"),
            Calendar.MONDAY to ("Jai Shree Mahakaal 🕉" to "Har Har Mahadev 🔱"),
            Calendar.TUESDAY to ("Jai Bajrangbali 🚩" to "Jai Narasimha Bhagwan 🕉"),
            Calendar.WEDNESDAY to ("Jai Shree Ganesh 🕉" to "Jai Shree Krishna 🦚"),
            Calendar.THURSDAY to ("Jai Shree Hari Vishnu 🕉" to "Jai Gurudev 🙏"),
            Calendar.FRIDAY to ("Jai Maa Lakshmi 🪷" to "Jai Maa Durga 🚩"),
            Calendar.SATURDAY to ("Jai Shanidev 🕉" to "Jai Bajrangbali 🚩")
        )

        val cal = Calendar.getInstance()
        for ((day, greetings) in days) {
            cal.set(Calendar.DAY_OF_WEEK, day)
            
            // Morning slot (8 AM)
            cal.set(Calendar.HOUR_OF_DAY, 8)
            val morningGreeting = DeityGreetingEngine.getGreeting(cal, isEnglish = true)
            assertEquals("Day $day morning greeting mismatch", greetings.first, morningGreeting)

            // Evening slot (6 PM)
            cal.set(Calendar.HOUR_OF_DAY, 18)
            val eveningGreeting = DeityGreetingEngine.getGreeting(cal, isEnglish = true)
            assertEquals("Day $day evening greeting mismatch", greetings.second, eveningGreeting)
        }
    }

    @Test
    fun testAudioCategoriesAndSafety() {
        val allPlayable = AudioTrackId.playableTracks()
        assertFalse(allPlayable.isEmpty())

        // Check MANTRA
        val mantras = allPlayable.filter { it.category == AudioCategory.MANTRA }
        assertTrue(mantras.any { it.rawName == "gayatri_mantra" })
        assertTrue(mantras.any { it.rawName == "maha_mrityunjaya" })

        // Check DEVOTIONAL (Stotram/Chalisa)
        val stotras = allPlayable.filter { it.category == AudioCategory.DEVOTIONAL }
        assertTrue(stotras.any { it.rawName == "hanuman_chalisa" })
        assertTrue(stotras.any { it.rawName == "shiva_tandav" })
        assertTrue(stotras.any { it.rawName == "vishnu_sahasranamam" })

        // Check AMBIENT (Dhyan)
        val ambient = allPlayable.filter { it.category == AudioCategory.AMBIENT }
        assertTrue(ambient.any { it.rawName == "om_ambient" })
        assertTrue(ambient.any { it.rawName == "flute_ambient" })
    }
}
