package com.example.data

import java.util.Calendar

data class DeitySlot(
    val deityNameEn: String,
    val deityNameHi: String,
    val greetingEn: String,
    val greetingHi: String
)

data class WeekdayGreeting(
    val weekday: Int, // Calendar.SUNDAY, Calendar.MONDAY, etc.
    val firstSlotDeity: DeitySlot,
    val secondSlotDeity: DeitySlot? = null
) {
    // Backwards compatibility helpers
    val slot1En: String get() = firstSlotDeity.greetingEn
    val slot1Hi: String get() = firstSlotDeity.greetingHi
    val slot2En: String get() = secondSlotDeity?.greetingEn ?: firstSlotDeity.greetingEn
    val slot2Hi: String get() = secondSlotDeity?.greetingHi ?: firstSlotDeity.greetingHi
    val deityNameEn: String get() = if (secondSlotDeity != null) "${firstSlotDeity.deityNameEn} & ${secondSlotDeity.deityNameEn}" else firstSlotDeity.deityNameEn
    val deityNameHi: String get() = if (secondSlotDeity != null) "${firstSlotDeity.deityNameHi} एवं ${secondSlotDeity.deityNameHi}" else firstSlotDeity.deityNameHi
}

// Alias for existing references
typealias DeityGreeting = WeekdayGreeting

object DeityGreetingEngine {
    private val WEEKDAY_MAPPINGS: Map<Int, WeekdayGreeting> = mapOf(
        Calendar.MONDAY to WeekdayGreeting(
            weekday = Calendar.MONDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Lord Shiva / Mahakaal",
                deityNameHi = "भगवान शिव / महाकाल",
                greetingEn = "Jai Shree Mahakaal 🕉",
                greetingHi = "जय श्री महाकाल 🕉"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Lord Shiva / Mahadev",
                deityNameHi = "भगवान शिव / महादेव",
                greetingEn = "Har Har Mahadev 🔱",
                greetingHi = "हर हर महादेव 🔱"
            )
        ),
        Calendar.TUESDAY to WeekdayGreeting(
            weekday = Calendar.TUESDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Hanuman Ji",
                deityNameHi = "हनुमान जी",
                greetingEn = "Jai Bajrangbali 🚩",
                greetingHi = "जय बजरंगबली 🚩"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Narasimha Bhagwan",
                deityNameHi = "नरसिंह भगवान",
                greetingEn = "Jai Narasimha Bhagwan 🕉",
                greetingHi = "जय नरसिंह भगवान 🕉"
            )
        ),
        Calendar.WEDNESDAY to WeekdayGreeting(
            weekday = Calendar.WEDNESDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Shri Ganesh",
                deityNameHi = "श्री गणेश",
                greetingEn = "Jai Shree Ganesh 🕉",
                greetingHi = "जय श्री गणेश 🕉"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Shri Krishna",
                deityNameHi = "श्री कृष्ण",
                greetingEn = "Jai Shree Krishna 🦚",
                greetingHi = "जय श्री कृष्ण 🦚"
            )
        ),
        Calendar.THURSDAY to WeekdayGreeting(
            weekday = Calendar.THURSDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Bhagwan Vishnu",
                deityNameHi = "भगवान विष्णु",
                greetingEn = "Jai Shree Hari Vishnu 🕉",
                greetingHi = "जय श्री हरि विष्णु 🕉"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Gurudev",
                deityNameHi = "गुरुदेव",
                greetingEn = "Jai Gurudev 🙏",
                greetingHi = "जय गुरुदेव 🙏"
            )
        ),
        Calendar.FRIDAY to WeekdayGreeting(
            weekday = Calendar.FRIDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Maa Lakshmi",
                deityNameHi = "माँ लक्ष्मी",
                greetingEn = "Jai Maa Lakshmi 🪷",
                greetingHi = "जय माँ लक्ष्मी 🪷"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Maa Durga",
                deityNameHi = "माँ दुर्गा",
                greetingEn = "Jai Maa Durga 🚩",
                greetingHi = "जय माँ दुर्गा 🚩"
            )
        ),
        Calendar.SATURDAY to WeekdayGreeting(
            weekday = Calendar.SATURDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Shanidev",
                deityNameHi = "शनिदेव",
                greetingEn = "Jai Shanidev 🕉",
                greetingHi = "जय शनिदेव 🕉"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Hanuman Ji",
                deityNameHi = "हनुमान जी",
                greetingEn = "Jai Bajrangbali 🚩",
                greetingHi = "जय बजरंगबली 🚩"
            )
        ),
        Calendar.SUNDAY to WeekdayGreeting(
            weekday = Calendar.SUNDAY,
            firstSlotDeity = DeitySlot(
                deityNameEn = "Surya Dev",
                deityNameHi = "सूर्य देव",
                greetingEn = "Om Suryaya Namah ☀️",
                greetingHi = "ॐ सूर्याय नमः ☀️"
            ),
            secondSlotDeity = DeitySlot(
                deityNameEn = "Shri Ram",
                deityNameHi = "प्रभु श्री राम",
                greetingEn = "Jai Shree Ram 🏹",
                greetingHi = "जय श्री राम 🏹"
            )
        )
    )

    fun getGreeting(calendar: Calendar = Calendar.getInstance(), isEnglish: Boolean = false): String {
        val activeSlot = getActiveDeitySlot(calendar)
        return if (isEnglish) activeSlot.greetingEn else activeSlot.greetingHi
    }

    fun getActiveDeitySlot(calendar: Calendar = Calendar.getInstance()): DeitySlot {
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val mapping = WEEKDAY_MAPPINGS[dayOfWeek] ?: WEEKDAY_MAPPINGS[Calendar.SUNDAY]!!

        return if (mapping.secondSlotDeity == null || hour in 0..11) {
            // 00:00 - 11:59 (First slot, or all day if single deity)
            mapping.firstSlotDeity
        } else {
            // 12:00 - 23:59 (Second slot)
            mapping.secondSlotDeity
        }
    }

    fun getDeityMapping(dayOfWeek: Int): WeekdayGreeting {
        return WEEKDAY_MAPPINGS[dayOfWeek] ?: WEEKDAY_MAPPINGS[Calendar.SUNDAY]!!
    }
}
