package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface IAdminRepository {
    val auditLogs: StateFlow<List<AuditLog>>
    val deities: StateFlow<List<DeityItem>>
    val poojaVidhis: StateFlow<List<PoojaVidhiItem>>
    val samagriItems: StateFlow<List<SamagriItem>>
    val mantras: StateFlow<List<MantraItem>>
    val festivals: StateFlow<List<FestivalItem>>
    val panchangConfig: StateFlow<PanchangAdminConfig>
    val youtubeConfig: StateFlow<YoutubeChannelConfig>
    val adminNotifications: StateFlow<List<AdminNotificationRecord>>
    val appSettings: StateFlow<AppSystemSettings>

    suspend fun logAction(adminIdentifier: String, action: String, entityType: String, entityId: String, oldValue: String = "", newValue: String = "")
    suspend fun addDeity(deity: DeityItem, adminId: String)
    suspend fun updateDeity(deity: DeityItem, adminId: String)
    suspend fun addPoojaVidhi(pooja: PoojaVidhiItem, adminId: String)
    suspend fun updatePoojaVidhi(pooja: PoojaVidhiItem, adminId: String)
    suspend fun addSamagri(samagri: SamagriItem, adminId: String)
    suspend fun addMantra(mantra: MantraItem, adminId: String)
    suspend fun updateMantra(mantra: MantraItem, adminId: String)
    suspend fun addFestival(festival: FestivalItem, adminId: String)
    suspend fun updateFestival(festival: FestivalItem, adminId: String)
    suspend fun updatePanchangConfig(config: PanchangAdminConfig, adminId: String)
    suspend fun updateYoutubeConfig(config: YoutubeChannelConfig, adminId: String)
    suspend fun createNotification(notification: AdminNotificationRecord, adminId: String)
    suspend fun updateAppSettings(settings: AppSystemSettings, adminId: String)
}

class AdminRepository(private val context: Context) : IAdminRepository {

    private val _auditLogs = MutableStateFlow<List<AuditLog>>(
        listOf(
            AuditLog(
                adminIdentifier = "System Admin",
                action = "SYSTEM_INITIALIZED",
                entityType = "CONTROL_CENTER",
                entityId = "SYS_INIT",
                newValue = "Sanatan Sadhak Admin Control Center Started"
            )
        )
    )
    override val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

    private val defaultDeities = listOf(
        DeityItem("shiva", "Shiva", "शिव", "शिवḥ", "The Supreme Yogi, Mahadev", sortOrder = 1),
        DeityItem("vishnu", "Vishnu", "विष्णु", "विष्णुḥ", "The Preserver of the Cosmos", sortOrder = 2),
        DeityItem("krishna", "Krishna", "कृष्ण", "कृष्णḥ", "Avatar of Vishnu, Speaker of Bhagavad Gita", sortOrder = 3),
        DeityItem("rama", "Rama", "राम", "रामḥ", "Maryada Purushottama Lord Rama", sortOrder = 4),
        DeityItem("hanuman", "Hanuman", "हनुमान", "हनुमान्", "Sankat Mochan, Embodiment of Bhakti", sortOrder = 5),
        DeityItem("ganesha", "Ganesha", "गणेश", "गणेशḥ", "Vighnaharta, Lord of Beginnings", sortOrder = 6),
        DeityItem("durga", "Durga", "दुर्गा", "दुर्गा", "Divine Mother, Shakti", sortOrder = 7),
        DeityItem("lakshmi", "Lakshmi", "लक्ष्मी", "लक्ष्मीḥ", "Goddess of Prosperity and Virtue", sortOrder = 8),
        DeityItem("saraswati", "Saraswati", "सरस्वती", "सरस्वती", "Goddess of Knowledge and Arts", sortOrder = 9),
        DeityItem("kali", "Kali", "काली", "काली", "Form of Shakti, Destroyer of Ego", sortOrder = 10),
        DeityItem("surya", "Surya", "सूर्य", "सूर्यḥ", "Sun Deity, Source of Light and Health", sortOrder = 11),
        DeityItem("navagraha", "Navagraha", "नवग्रह", "नवग्रहाः", "Nine Planetary Deities", sortOrder = 12),
        DeityItem("dattatreya", "Dattatreya", "दत्तात्रेय", "दत्तात्रेयḥ", "Combined Avatar of Trimurti", sortOrder = 13),
        DeityItem("other", "Other", "अन्य", "अन्य", "Other Sacred Deities", sortOrder = 14)
    )
    private val _deities = MutableStateFlow<List<DeityItem>>(defaultDeities)
    override val deities: StateFlow<List<DeityItem>> = _deities.asStateFlow()

    private val defaultPoojaVidhis = listOf(
        PoojaVidhiItem(
            id = "p_shiva_1",
            pujaName = "Shiva Abhishekam & Archana",
            deity = "Shiva",
            occasion = "Pradosham / Mahashivratri / Mondays",
            samagriList = "Water, Milk, Honey, Curd, Ghee, Bel leaves, Panchamrit, Chandan, Vibhuti",
            preparation = "Clean the altar and place the Shiva Lingam on a clean copper or brass plate.",
            purificationSteps = "Perform Aachamanam: Drink water thrice while chanting Om Keshavaya Namah, Om Narayanaya Namah, Om Madhavaya Namah.",
            sankalpa = "Declare intent for inner purification, peace, and spiritual growth.",
            steps = listOf(
                PoojaStep(1, "Aavahan", "Invoke Mahadev into the Shiva Lingam with devotion.", "Om Namah Shivaya", 3, true),
                PoojaStep(2, "Abhishekam", "Pour pure water, milk, curd, honey, ghee and water sequentially.", "Tryambakam Mantra", 10, true),
                PoojaStep(3, "Bilva Archana", "Offer fresh Bilva leaves with Sanskrit chanting.", "Om Namah Shivaya", 5, true),
                PoojaStep(4, "Aarti & Prayer", "Perform camphor aarti and seek forgiveness for any shortcomings.", "Karpur Gauram Karunavataram", 5, true)
            ),
            offerings = "Fruit, Panchamrit, Bel leaves",
            closingProcedure = "Offer respectful obeisances and accept Prasadam with humility.",
            safetyNotes = "Ensure camphor is held safely away from flammable materials.",
            source = "Shiva Purana & Traditional Paddhati",
            verificationStatus = "VERIFIED_SOURCE",
            workflowStatus = "PUBLISHED"
        )
    )
    private val _poojaVidhis = MutableStateFlow<List<PoojaVidhiItem>>(defaultPoojaVidhis)
    override val poojaVidhis: StateFlow<List<PoojaVidhiItem>> = _poojaVidhis.asStateFlow()

    private val defaultSamagri = listOf(
        SamagriItem("s_1", "Ghee (Pure Cow Ghee)", "शुद्ध गाय का घी", "गोघृतम्", "Used for diya lamps and homa offerings", "250", "ml", true, "All", "Sesame oil for lamp", "Gita Press Puja Paddhati"),
        SamagriItem("s_2", "Bel Leaves (Bilva Patra)", "बेलपत्र", "बिल्वपत्रम्", "Trifold sacred leaves pleasing to Mahadev", "11", "leaves", true, "Shiva Pooja", "Fresh flowers", "Shiva Purana"),
        SamagriItem("s_3", "Camphor (Karpur)", "कपूर", "कर्पूरम्", "Pure white camphor for Aarti", "1", "pack", true, "All", "Ghee lamp", "Traditional")
    )
    private val _samagriItems = MutableStateFlow<List<SamagriItem>>(defaultSamagri)
    override val samagriItems: StateFlow<List<SamagriItem>> = _samagriItems.asStateFlow()

    private val defaultMantras = listOf(
        MantraItem(
            id = "m_1",
            name = "Maha Mrityunjaya Mantra",
            deity = "Shiva",
            originalSanskrit = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान् मृत्योर्मुक्षीय मामृतात्॥",
            transliteration = "Om Tryambakam Yajamahe Sugandhim Pushti-Vardhanam\nUrvarukamiva Bandhanan Mrityor Mukshiya Maamritat",
            meaning = "We worship the Three-Eyed Lord Shiva who is fragrant and who nourishes all beings. May He liberate us from death for the sake of immortality.",
            purpose = "Health, longevity, protection, and spiritual liberation",
            source = "Rigveda (7.59.12) & Yajurveda",
            reference = "Rigveda 7.59.12",
            tradition = "Vedic Sanatan",
            japaRecommendation = "108 times daily in early morning",
            verificationStatus = "VERIFIED_SOURCE",
            workflowStatus = "PUBLISHED"
        ),
        MantraItem(
            id = "m_2",
            name = "Gayatri Mantra",
            deity = "Surya / Savitur",
            originalSanskrit = "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्॥",
            transliteration = "Om Bhur Bhuva Swaha, Tat Savitur Varenyam, Bhargo Devasya Dhimahi, Dhiyo Yo Nah Prachodayat",
            meaning = "We meditate on the adorable light of the Divine Sun. May That divine light illuminate and inspire our intellect.",
            purpose = "Wisdom, clarity, mental vigor, enlightenment",
            source = "Rigveda (3.62.10)",
            reference = "Rigveda 3.62.10",
            tradition = "Vedic Sanatan",
            japaRecommendation = "108 times at Sandhya Kaal",
            verificationStatus = "VERIFIED_SOURCE",
            workflowStatus = "PUBLISHED"
        )
    )
    private val _mantras = MutableStateFlow<List<MantraItem>>(defaultMantras)
    override val mantras: StateFlow<List<MantraItem>> = _mantras.asStateFlow()

    private val defaultFestivals = listOf(
        FestivalItem(
            id = "f_1",
            nameEn = "Mahashivratri",
            nameHi = "महाशिवरात्रि",
            deity = "Shiva",
            description = "The great night of Lord Shiva celebrating the divine union and cosmic dance of Shiva and Shakti.",
            tradition = "Sanatan Vedic",
            region = "All India",
            observanceRule = "Phalguna Krishna Chaturdashi Tithi (Nishita Kaal Vyapti)",
            dateType = "RULE_BASED",
            source = "Vratraj & Nirnaya Sindhu",
            verificationStatus = "VERIFIED_SOURCE",
            workflowStatus = "PUBLISHED"
        ),
        FestivalItem(
            id = "f_2",
            nameEn = "Janmashtami",
            nameHi = "श्रीकृष्ण जन्माष्टमी",
            deity = "Krishna",
            description = "Celebration of the divine advent of Lord Shri Krishna in Mathura at midnight.",
            tradition = "Vaishnava Sanatan",
            region = "All India",
            observanceRule = "Bhadrapada Krishna Ashtami Tithi (Rohini Nakshatra & Midnight)",
            dateType = "RULE_BASED",
            source = "Dharma Sindhu",
            verificationStatus = "VERIFIED_SOURCE",
            workflowStatus = "PUBLISHED"
        )
    )
    private val _festivals = MutableStateFlow<List<FestivalItem>>(defaultFestivals)
    override val festivals: StateFlow<List<FestivalItem>> = _festivals.asStateFlow()

    private val _panchangConfig = MutableStateFlow(PanchangAdminConfig())
    override val panchangConfig: StateFlow<PanchangAdminConfig> = _panchangConfig.asStateFlow()

    private val _youtubeConfig = MutableStateFlow(YoutubeChannelConfig())
    override val youtubeConfig: StateFlow<YoutubeChannelConfig> = _youtubeConfig.asStateFlow()

    private val defaultNotifications = listOf(
        AdminNotificationRecord(
            id = "notif_1",
            title = "Daily Shloka & Panchang Update",
            message = "Explore today's auspicious Tithi, Nakshatra, and sacred Shloka.",
            category = "Daily Panchang",
            schedule = "DAILY_8AM",
            enabled = true,
            priority = "NORMAL"
        )
    )
    private val _adminNotifications = MutableStateFlow<List<AdminNotificationRecord>>(defaultNotifications)
    override val adminNotifications: StateFlow<List<AdminNotificationRecord>> = _adminNotifications.asStateFlow()

    private val _appSettings = MutableStateFlow(AppSystemSettings())
    override val appSettings: StateFlow<AppSystemSettings> = _appSettings.asStateFlow()

    override suspend fun logAction(adminIdentifier: String, action: String, entityType: String, entityId: String, oldValue: String, newValue: String) {
        val entry = AuditLog(
            adminIdentifier = adminIdentifier,
            action = action,
            entityType = entityType,
            entityId = entityId,
            oldValue = oldValue,
            newValue = newValue
        )
        _auditLogs.value = listOf(entry) + _auditLogs.value
    }

    override suspend fun addDeity(deity: DeityItem, adminId: String) {
        val current = _deities.value.toMutableList()
        current.removeAll { it.id == deity.id }
        current.add(deity)
        _deities.value = current
        logAction(adminId, "DEITY_ADDED", "DEITY", deity.id, "", deity.nameEn)
    }

    override suspend fun updateDeity(deity: DeityItem, adminId: String) {
        val current = _deities.value.toMutableList()
        val idx = current.indexOfFirst { it.id == deity.id }
        if (idx >= 0) {
            val oldName = current[idx].nameEn
            current[idx] = deity
            _deities.value = current
            logAction(adminId, "DEITY_UPDATED", "DEITY", deity.id, oldName, deity.nameEn)
        }
    }

    override suspend fun addPoojaVidhi(pooja: PoojaVidhiItem, adminId: String) {
        val current = _poojaVidhis.value.toMutableList()
        current.add(pooja)
        _poojaVidhis.value = current
        logAction(adminId, "CONTENT_CREATED", "POOJA_VIDHI", pooja.id, "", pooja.pujaName)
    }

    override suspend fun updatePoojaVidhi(pooja: PoojaVidhiItem, adminId: String) {
        val current = _poojaVidhis.value.toMutableList()
        val idx = current.indexOfFirst { it.id == pooja.id }
        if (idx >= 0) {
            val oldName = current[idx].pujaName
            current[idx] = pooja
            _poojaVidhis.value = current
            logAction(adminId, "CONTENT_EDITED", "POOJA_VIDHI", pooja.id, oldName, pooja.pujaName)
        }
    }

    override suspend fun addSamagri(samagri: SamagriItem, adminId: String) {
        val current = _samagriItems.value.toMutableList()
        current.add(samagri)
        _samagriItems.value = current
        logAction(adminId, "CONTENT_CREATED", "SAMAGRI", samagri.id, "", samagri.nameEn)
    }

    override suspend fun addMantra(mantra: MantraItem, adminId: String) {
        val current = _mantras.value.toMutableList()
        current.add(mantra)
        _mantras.value = current
        logAction(adminId, "CONTENT_CREATED", "MANTRA", mantra.id, "", mantra.name)
    }

    override suspend fun updateMantra(mantra: MantraItem, adminId: String) {
        val current = _mantras.value.toMutableList()
        val idx = current.indexOfFirst { it.id == mantra.id }
        if (idx >= 0) {
            val oldName = current[idx].name
            current[idx] = mantra
            _mantras.value = current
            logAction(adminId, "CONTENT_EDITED", "MANTRA", mantra.id, oldName, mantra.name)
        }
    }

    override suspend fun addFestival(festival: FestivalItem, adminId: String) {
        val current = _festivals.value.toMutableList()
        current.add(festival)
        _festivals.value = current
        logAction(adminId, "CONTENT_CREATED", "FESTIVAL", festival.id, "", festival.nameEn)
    }

    override suspend fun updateFestival(festival: FestivalItem, adminId: String) {
        val current = _festivals.value.toMutableList()
        val idx = current.indexOfFirst { it.id == festival.id }
        if (idx >= 0) {
            val oldName = current[idx].nameEn
            current[idx] = festival
            _festivals.value = current
            logAction(adminId, "CONTENT_EDITED", "FESTIVAL", festival.id, oldName, festival.nameEn)
        }
    }

    override suspend fun updatePanchangConfig(config: PanchangAdminConfig, adminId: String) {
        val oldSource = _panchangConfig.value.defaultPanchangSource
        _panchangConfig.value = config
        logAction(adminId, "PANCHANG_CONFIG_CHANGED", "PANCHANG_CONFIG", "MAIN_CONFIG", oldSource, config.defaultPanchangSource)
    }

    override suspend fun updateYoutubeConfig(config: YoutubeChannelConfig, adminId: String) {
        val oldId = _youtubeConfig.value.channelId
        _youtubeConfig.value = config
        logAction(adminId, "VIDEO_SYNCED", "YOUTUBE_CONFIG", config.channelId, oldId, config.syncStatus)
    }

    override suspend fun createNotification(notification: AdminNotificationRecord, adminId: String) {
        val current = _adminNotifications.value.toMutableList()
        current.add(0, notification)
        _adminNotifications.value = current
        logAction(adminId, "NOTIFICATION_CREATED", "NOTIFICATION", notification.id, "", notification.title)
    }

    override suspend fun updateAppSettings(settings: AppSystemSettings, adminId: String) {
        val oldName = _appSettings.value.appName
        _appSettings.value = settings
        logAction(adminId, "APP_SETTINGS_CHANGED", "SYSTEM_SETTINGS", "APP_CONFIG", oldName, settings.appName)
    }
}
