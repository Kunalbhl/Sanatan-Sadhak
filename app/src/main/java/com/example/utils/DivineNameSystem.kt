package com.example.utils

import java.util.Calendar

/**
 * Deterministic Devotional Greeting System based on Weekday and 12-Hour Period (AM/PM).
 * Rotates subtly every few minutes to maintain a peaceful living temple atmosphere without aggressive recompositions.
 */
object DivineNameSystem {

    data class DevotionalGreeting(
        val deityNameEn: String,
        val deityNameHi: String,
        val sacredMantraEn: String,
        val sacredMantraHi: String
    )

    fun getGreetingForTime(calendar: Calendar = Calendar.getInstance()): DevotionalGreeting {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday, 2 = Monday, ... 7 = Saturday
        val isAm = calendar.get(Calendar.AM_PM) == Calendar.AM
        val minuteBucket = (calendar.get(Calendar.MINUTE) / 5) // rotates every 5 minutes

        return when (dayOfWeek) {
            Calendar.MONDAY -> if (isAm) {
                val names = listOf(
                    DevotionalGreeting("Mahadev", "महादेव", "Om Namah Shivaya", "ॐ नमः शिवाय"),
                    DevotionalGreeting("Shankara", "शंकर", "Har Har Mahadev", "हर हर महादेव"),
                    DevotionalGreeting("Neelkanth", "नीलकंठ", "Om Namah Shivaya", "ॐ नमः शिवाय")
                )
                names[minuteBucket % names.size]
            } else {
                val names = listOf(
                    DevotionalGreeting("Shiva", "शिव", "Om Namah Shivaya", "ॐ नमः शिवाय"),
                    DevotionalGreeting("Mahesh", "महेश", "Har Har Mahadev", "हर हर महादेव"),
                    DevotionalGreeting("Rudra", "रुद्र", "Om Namah Shivaya", "ॐ नमः शिवाय"),
                    DevotionalGreeting("Shambhu", "शंभू", "Jai Bholenath", "जय भोलेनाथ")
                )
                names[minuteBucket % names.size]
            }

            Calendar.TUESDAY -> {
                val names = listOf(
                    DevotionalGreeting("Hanuman", "हनुमान", "Jai Shri Ram • Jai Bajrangbali", "जय श्री राम • जय बजरंगबली"),
                    DevotionalGreeting("Bajrangbali", "बजरंगबली", "Om Hanumate Namah", "ॐ हनुमते नमः"),
                    DevotionalGreeting("Anjaneya", "आंजनेय", "Jai Shri Ram", "जय श्री राम"),
                    DevotionalGreeting("Mahaveer", "महावीर", "Jai Bajrangbali", "जय बजरंगबली")
                )
                names[minuteBucket % names.size]
            }

            Calendar.WEDNESDAY -> {
                val names = listOf(
                    DevotionalGreeting("Ganesh", "गणेश", "Om Gam Ganapataye Namah", "ॐ गं गणपतये नमः"),
                    DevotionalGreeting("Ganapati", "गणपति", "Jai Shri Ganesha", "जय श्री गणेश"),
                    DevotionalGreeting("Vighnaharta", "विघ्नहर्ता", "Om Gam Ganapataye Namah", "ॐ गं गणपतये नमः"),
                    DevotionalGreeting("Ekdant", "एकदंत", "Jai Shri Ganesha", "जय श्री गणेश")
                )
                names[minuteBucket % names.size]
            }

            Calendar.THURSDAY -> {
                val names = listOf(
                    DevotionalGreeting("Vishnu", "विष्णु", "Om Namo Narayanaya", "ॐ नमो नारायणाय"),
                    DevotionalGreeting("Narayana", "नारायण", "Om Namo Bhagavate Vasudevaya", "ॐ नमो भगवते वासुदेवाय"),
                    DevotionalGreeting("Govinda", "गोविंदा", "Om Namo Narayanaya", "ॐ नमो नारायणाय"),
                    DevotionalGreeting("Hari", "हरि", "Hari Om Tat Sat", "हरि ॐ तत्सत्")
                )
                names[minuteBucket % names.size]
            }

            Calendar.FRIDAY -> {
                val names = listOf(
                    DevotionalGreeting("Maa Lakshmi", "माँ लक्ष्मी", "Om Shreem Mahalakshmiyei Namah", "ॐ श्रीं महालक्ष्म्यै नमः"),
                    DevotionalGreeting("Shri", "श्री", "Jai Maa Lakshmi", "जय माँ लक्ष्मी"),
                    DevotionalGreeting("Mahalakshmi", "महालक्ष्मी", "Om Shreem Namah", "ॐ श्रीं नमः")
                )
                names[minuteBucket % names.size]
            }

            Calendar.SATURDAY -> {
                val names = listOf(
                    DevotionalGreeting("Shani Dev", "शनि देव", "Om Sham Shanayscharaya Namah", "ॐ शं शनैश्चराय नमः"),
                    DevotionalGreeting("Suryaputra", "सूर्यपुत्र", "Jai Shani Dev", "जय शनि देव"),
                    DevotionalGreeting("Neelambar", "नीलांबर", "Om Sham Shanaye Namah", "ॐ शं शनये नमः")
                )
                names[minuteBucket % names.size]
            }

            Calendar.SUNDAY -> {
                val names = listOf(
                    DevotionalGreeting("Surya Dev", "सूर्य देव", "Om Suryaya Namah", "ॐ सूर्याय नमः"),
                    DevotionalGreeting("Aditya", "आदित्य", "Om Ghrini Suryaya Namah", "ॐ घृणिः सूर्याय नमः"),
                    DevotionalGreeting("Bhaskar", "भास्कर", "Jai Surya Dev", "जय सूर्य देव")
                )
                names[minuteBucket % names.size]
            }

            else -> DevotionalGreeting("Mahadev", "महादेव", "Om Namah Shivaya", "ॐ नमः शिवाय")
        }
    }
}
