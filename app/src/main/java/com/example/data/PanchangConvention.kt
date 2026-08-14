package com.example.data

enum class AyanamshaSystem(val displayName: String) {
    LAHIRI("Lahiri (Chitrapaksha) Ayanamsha (~23.85° - Simplified)"),
    RAMAN("Raman Ayanamsha"),
    KRISHNAMURTI("KP Ayanamsha"),
    TROPICAL("Tropical (No Ayanamsha)")
}

enum class ZodiacType(val displayName: String) {
    SIDEREAL("Nirayana (Sidereal Zodiac)"),
    TROPICAL("Sayana (Tropical Zodiac)")
}

enum class LunarMonthSystem(val displayName: String) {
    PURNIMANTA("Purnimanta (Month ends on Purnima - North/Central India)"),
    AMANTA("Amanta (Month ends on Amavasya - South/West India)")
}

enum class SamvatSystem(val displayName: String) {
    CHAITRADI("Chaitradi Vikram Samvat (Year starts in Chaitra Shukla 1)"),
    KARTIKADI("Kartikadi Vikram Samvat (Year starts in Kartik Shukla 1)"),
    SHAKA("Shaka Samvat (National Calendar)")
}

enum class SunriseConvention(val displayName: String) {
    CENTER_DISC("Geometric Center Disc"),
    UPPER_LIMB_REFRACTED("Upper Limb Refracted (~0°34' atmospheric refraction + 0°16' semi-diameter)")
}

enum class FestivalCategory(val typeName: String) {
    ASTRONOMICAL_EVENT("Astronomical Event"),
    TRADITIONAL_OBSERVANCE("Traditional Observance"),
    VERIFIED_FESTIVAL("Verified Festival")
}

data class FestivalRuleRecord(
    val name: String,
    val date: String,
    val region: String = "ALL_INDIA",
    val rule: String,
    val source: String,
    val verificationStatus: String = "RULE_DERIVED_OBSERVANCE",
    val category: FestivalCategory = FestivalCategory.TRADITIONAL_OBSERVANCE
)

data class PanchangConvention(
    val ayanamsha: AyanamshaSystem = AyanamshaSystem.LAHIRI,
    val zodiacType: ZodiacType = ZodiacType.SIDEREAL,
    val lunarMonthSystem: LunarMonthSystem = LunarMonthSystem.PURNIMANTA,
    val samvatSystem: SamvatSystem = SamvatSystem.CHAITRADI,
    val festivalRegion: String = "USER_SELECTED",
    val sunriseConvention: SunriseConvention = SunriseConvention.UPPER_LIMB_REFRACTED,
    val dayBoundary: String = "SUNRISE_TO_SUNRISE",
    val adhikaMasaSupportNote: String = "Adhika Masa calculation not currently supported locally"
)

object EngineClassification {
    const val LOCAL_ENGINE_CLASSIFICATION = "APPROXIMATE_LOCAL_ENGINE"
    const val REFERENCE_DATA_UNAVAILABLE = "REFERENCE_DATA_UNAVAILABLE"
}

/**
 * Admin preparation configuration hooks for future Panchang governance.
 */
data class PanchangAdminConfig(
    val defaultPanchangSource: String = "REMOTE_PREFERRED",
    val fallbackBehavior: String = "FALLBACK_TO_APPROXIMATE_LOCAL",
    val defaultAyanamsha: AyanamshaSystem = AyanamshaSystem.LAHIRI,
    val defaultLunarMonthSystem: LunarMonthSystem = LunarMonthSystem.PURNIMANTA,
    val defaultFestivalRegion: String = "ALL_INDIA",
    val displaySourceInformation: Boolean = true
)

object PanchangLocalization {
    private val tithiMap = mapOf(
        "pratipada" to "प्रतिपदा",
        "dwitiya" to "द्वितीया",
        "tritiya" to "तृतीया",
        "chaturthi" to "चतुर्थी",
        "panchami" to "पंचमी",
        "shashthi" to "षष्ठी",
        "saptami" to "सप्तमी",
        "ashtami" to "अष्टमी",
        "navami" to "नवमी",
        "dashami" to "दशमी",
        "ekadashi" to "एकादशी",
        "dwadashi" to "द्वादशी",
        "trayodashi" to "त्रयोदशी",
        "chaturdashi" to "चतुर्दशी",
        "purnima" to "पूर्णिमा",
        "amavasya" to "अमावस्या"
    )

    private val pakshaMap = mapOf(
        "shukla" to "शुक्ल पक्ष",
        "krishna" to "कृष्ण पक्ष",
        "shukla paksha" to "शुक्ल पक्ष",
        "krishna paksha" to "कृष्ण पक्ष"
    )

    private val nakshatraMap = mapOf(
        "ashwini" to "अश्विनी",
        "bharani" to "भरणी",
        "krittika" to "कृत्तिका",
        "rohini" to "रोहिणी",
        "mrigashira" to "मृगशिरा",
        "ardra" to "आर्द्रा",
        "punarvasu" to "पुनर्वसु",
        "pushya" to "पुष्य",
        "ashlesha" to "अश्लेषा",
        "magha" to "मघा",
        "purva phalguni" to "पूर्वाफाल्गुनी",
        "uttara phalguni" to "उत्तराफाल्गुनी",
        "hasta" to "हस्त",
        "chitra" to "चित्रा",
        "swati" to "स्वाति",
        "vishakha" to "विशाखा",
        "anuradha" to "अनुराधा",
        "jyeshtha" to "ज्येष्ठा",
        "mula" to "मूल",
        "purva ashadha" to "पूर्वाषाढ़ा",
        "uttara ashadha" to "उत्तराषाढ़ा",
        "shravana" to "श्रवण",
        "dhanishta" to "धनिष्ठा",
        "shatabhisha" to "शतभिषा",
        "purva bhadrapada" to "पूर्वाभाद्रपद",
        "uttara bhadrapada" to "उत्तराभाद्रपद",
        "revati" to "रेवती"
    )

    private val yogaMap = mapOf(
        "vishkambha" to "विष्कम्भ", "priti" to "प्रीति", "ayushman" to "आयुष्मान",
        "saubhagya" to "सौभाग्य", "shobhana" to "शोभन", "sobhana" to "शोभन",
        "atiganda" to "अतिगण्ड", "sukarma" to "सुकर्मा", "dhriti" to "धृति",
        "shula" to "शूल", "ganda" to "गण्ड", "vriddhi" to "वृद्धि",
        "dhruva" to "ध्रुव", "vyaghata" to "व्याघात", "vyaghat" to "व्याघात",
        "harshana" to "हर्षण", "vajra" to "वज्र", "siddhi" to "सिद्धि",
        "vyatipata" to "व्यतीपात", "vyatipat" to "व्यतीपात", "variyan" to "वरीयान्",
        "parigha" to "परिघ", "shiva" to "शिव", "siddha" to "सिद्ध",
        "sadhya" to "साध्य", "shubha" to "शुभ", "shukla" to "शुक्ल",
        "brahma" to "ब्रह्म", "indra" to "इन्द्र", "vaidhriti" to "वैधृति"
    )

    private val karanaMap = mapOf(
        "bava" to "बव", "balava" to "बालव", "kaulava" to "कौलव",
        "taitila" to "तैतिल", "gara" to "गर", "vanija" to "वणिज",
        "vishti" to "विष्टि (भद्रा)", "vishti (bhadra)" to "विष्टि (भद्रा)",
        "shakuni" to "शकुनि", "chatushpada" to "चतुष्पाद", "naga" to "नाग",
        "kimstughna" to "किंस्तुघ्न"
    )

    private val rashiMap = mapOf(
        "aries" to "मेष", "mesha" to "मेष",
        "taurus" to "वृषभ", "vrishabha" to "वृषभ",
        "gemini" to "मिथुन", "mithuna" to "मिथुन",
        "cancer" to "कर्क", "karka" to "कर्क",
        "leo" to "सिंह", "simha" to "सिंह",
        "virgo" to "कन्या", "kanya" to "कन्या",
        "libra" to "तुला", "tula" to "तुला",
        "scorpio" to "वृश्चिक", "vrishchika" to "वृश्चिक",
        "sagittarius" to "धनु", "dhanu" to "धनु",
        "capricorn" to "मकर", "makara" to "मकर",
        "aquarius" to "कुंभ", "kumbha" to "कुंभ",
        "pisces" to "मीन", "meena" to "मीन"
    )

    private val monthMap = mapOf(
        "chaitra" to "चैत्र", "vaishakha" to "वैशाख", "jyeshtha" to "ज्येष्ठ",
        "ashadha" to "आषाढ़", "shravana" to "श्रावण", "bhadrapada" to "भाद्रपद",
        "ashwin" to "आश्विन", "kartika" to "कार्तिक", "margashirsha" to "मार्गशीर्ष",
        "pausha" to "पौष", "magha" to "माघ", "phalguna" to "फाल्गुन"
    )

    private val weekdayMap = mapOf(
        "sunday" to "रविवार", "monday" to "सोमवार", "tuesday" to "मंगलवार",
        "wednesday" to "बुधवार", "thursday" to "गुरुवार", "friday" to "शुक्रवार",
        "saturday" to "शनिवार"
    )

    fun localizeTithi(tithi: String, paksha: String = ""): String {
        if (tithi.isBlank()) return "—"
        val lower = tithi.lowercase()
        var result = tithi
        for ((k, v) in tithiMap) {
            if (lower.contains(k)) {
                result = v
                break
            }
        }
        val pakshaLower = (if (paksha.isNotBlank()) paksha else tithi).lowercase()
        val pakshaHi = when {
            pakshaLower.contains("shukla") -> "शुक्ल पक्ष"
            pakshaLower.contains("krishna") -> "कृष्ण पक्ष"
            else -> ""
        }
        return if (pakshaHi.isNotEmpty() && !result.contains(pakshaHi)) {
            "$result · $pakshaHi"
        } else {
            result
        }
    }

    fun localizeNakshatra(nakshatra: String): String {
        if (nakshatra.isBlank()) return "—"
        val clean = nakshatra.replace("Nakshatra", "", ignoreCase = true).trim()
        val lower = clean.lowercase()
        for ((k, v) in nakshatraMap) {
            if (lower.contains(k)) {
                return "$v नक्षत्र"
            }
        }
        return "$clean नक्षत्र"
    }

    fun localizeYoga(yoga: String): String {
        if (yoga.isBlank()) return "—"
        val clean = yoga.replace("Yoga", "", ignoreCase = true).trim()
        val lower = clean.lowercase()
        for ((k, v) in yogaMap) {
            if (lower.contains(k)) {
                return "$v योग"
            }
        }
        return "$clean योग"
    }

    fun localizeKarana(karana: String): String {
        if (karana.isBlank()) return "—"
        val clean = karana.replace("Karana", "", ignoreCase = true).trim()
        val lower = clean.lowercase()
        for ((k, v) in karanaMap) {
            if (lower.contains(k)) {
                return "$v करण"
            }
        }
        return "$clean करण"
    }

    fun localizeRashi(rashi: String): String {
        if (rashi.isBlank()) return "—"
        val lower = rashi.lowercase()
        for ((k, v) in rashiMap) {
            if (lower.contains(k)) {
                return "$v राशि"
            }
        }
        return rashi
    }

    fun localizePaksha(paksha: String): String {
        val lower = paksha.lowercase()
        return when {
            lower.contains("shukla") -> "शुक्ल पक्ष"
            lower.contains("krishna") -> "कृष्ण पक्ष"
            else -> paksha
        }
    }

    fun localizeMonth(month: String): String {
        val lower = month.lowercase()
        for ((k, v) in monthMap) {
            if (lower.contains(k)) return "$v मास"
        }
        return month
    }

    fun localizeWeekday(day: String): String {
        val lower = day.lowercase()
        for ((k, v) in weekdayMap) {
            if (lower.contains(k)) return v
        }
        return day
    }
}
