package com.example.data

/**
 * Major festivals worked out from the Panchang itself (lunar month + paksha + tithi, and the Sun's sign),
 * not from a fixed list of dates, so they move correctly every year.
 *
 * Months use the Amanta index (0 = Chaitra … 11 = Phalguna); the Krishna paksha of a month belongs to the
 * same Amanta month, which is how these festivals are normally reckoned.
 * Tithi is the one running at sunrise, so on some days a festival that depends on midday, evening or
 * moonrise (Ram Navami, Diwali, Karwa Chauth, Janmashtami…) can fall a day earlier or later by region.
 */
object FestivalRules {
    /** @param tithi 1..15 within the paksha (15 = Purnima in Shukla, Amavasya in Krishna) */
    fun majorFestival(isShukla: Boolean, tithi: Int, amantaMonth: Int, sunSiderealLongitude: Double): String? {
        if (sunSiderealLongitude >= 270.0 && sunSiderealLongitude < 271.0) {
            // Makar Sankranti: Sun enters Makara
            return "Makar Sankranti (मकर संक्रांति)"
        }
        val s = isShukla
        return when (amantaMonth) {
            0 -> when {
                s && tithi == 1 -> "Gudi Padwa / Ugadi · Hindu New Year (गुड़ी पड़वा / नव संवत्सर)"
                s && tithi == 9 -> "Ram Navami (राम नवमी)"
                s && tithi == 15 -> "Hanuman Jayanti (हनुमान जयंती)"
                else -> null
            }
            1 -> when {
                s && tithi == 3 -> "Akshaya Tritiya (अक्षय तृतीया)"
                s && tithi == 14 -> "Narasimha Jayanti (नृसिंह जयंती)"
                else -> null
            }
            3 -> when {
                s && tithi == 2 -> "Jagannath Rath Yatra (जगन्नाथ रथ यात्रा)"
                s && tithi == 15 -> "Guru Purnima (गुरु पूर्णिमा)"
                else -> null
            }
            4 -> when {
                s && tithi == 5 -> "Nag Panchami (नाग पंचमी)"
                s && tithi == 15 -> "Raksha Bandhan (रक्षा बंधन)"
                !s && tithi == 8 -> "Krishna Janmashtami (कृष्ण जन्माष्टमी)"
                else -> null
            }
            5 -> when {
                s && tithi == 4 -> "Ganesh Chaturthi (गणेश चतुर्थी)"
                s && tithi == 8 -> "Radha Ashtami (राधा अष्टमी)"
                s && tithi == 14 -> "Anant Chaturdashi (अनंत चतुर्दशी)"
                !s && tithi == 15 -> "Sarva Pitru Amavasya (सर्वपितृ अमावस्या)"
                else -> null
            }
            6 -> when {
                s && tithi == 1 -> "Sharad Navratri begins (शारदीय नवरात्रि प्रारंभ)"
                s && tithi == 8 -> "Durga Ashtami (दुर्गा अष्टमी)"
                s && tithi == 9 -> "Maha Navami (महानवमी)"
                s && tithi == 10 -> "Vijayadashami / Dussehra (विजयादशमी / दशहरा)"
                s && tithi == 15 -> "Sharad Purnima (शरद पूर्णिमा)"
                !s && tithi == 4 -> "Karwa Chauth (करवा चौथ)"
                !s && tithi == 13 -> "Dhanteras (धनतेरस)"
                !s && tithi == 14 -> "Naraka Chaturdashi / Choti Diwali (नरक चतुर्दशी)"
                !s && tithi == 15 -> "Diwali · Lakshmi Puja (दीपावली · लक्ष्मी पूजन)"
                else -> null
            }
            7 -> when {
                s && tithi == 1 -> "Govardhan Puja (गोवर्धन पूजा)"
                s && tithi == 2 -> "Bhai Dooj (भाई दूज)"
                s && tithi == 11 -> "Dev Uthani Ekadashi (देवउठनी एकादशी)"
                s && tithi == 15 -> "Kartik Purnima · Dev Diwali (कार्तिक पूर्णिमा)"
                else -> null
            }
            8 -> when {
                s && tithi == 5 -> "Vivah Panchami (विवाह पंचमी)"
                s && tithi == 11 -> "Gita Jayanti · Mokshada Ekadashi (गीता जयंती)"
                s && tithi == 15 -> "Dattatreya Jayanti (दत्तात्रेय जयंती)"
                else -> null
            }
            10 -> when {
                s && tithi == 5 -> "Vasant Panchami (वसंत पंचमी)"
                !s && tithi == 14 -> "Maha Shivaratri (महाशिवरात्रि)"
                else -> null
            }
            11 -> when {
                s && tithi == 15 -> "Holika Dahan (होलिका दहन)"
                !s && tithi == 1 -> "Holi (होली)"
                else -> null
            }
            else -> null
        }
    }

    private val ekadashiNames = listOf(
        // Amanta month: Krishna paksha name, Shukla paksha name
        "Papmochani" to "Kamada",             // Chaitra
        "Varuthini" to "Mohini",              // Vaishakha
        "Apara" to "Nirjala",                 // Jyeshtha
        "Yogini" to "Devshayani",             // Ashadha
        "Kamika" to "Putrada (Shravana)",     // Shravana
        "Aja" to "Parivartini",               // Bhadrapada
        "Indira" to "Papankusha",             // Ashwin
        "Rama" to "Devutthana (Prabodhini)",  // Kartika
        "Utpanna" to "Mokshada",              // Margashirsha
        "Saphala" to "Putrada (Pausha)",      // Pausha
        "Shattila" to "Jaya",                 // Magha
        "Vijaya" to "Amalaki"                 // Phalguna
    )

    /**
     * Ekadashi observed on the day tithi 11 is running at sunrise (the Smarta rule). When it also runs at the
     * next sunrise, Vaishnava traditions keep the second day; the app shows the sunrise day.
     */
    fun ekadashi(isShukla: Boolean, amantaMonth: Int): String {
        val pair = ekadashiNames.getOrNull(amantaMonth) ?: return "Ekadashi Vrata (एकादशी व्रत)"
        val name = if (isShukla) pair.second else pair.first
        return "$name Ekadashi Vrata (एकादशी व्रत)"
    }
}
