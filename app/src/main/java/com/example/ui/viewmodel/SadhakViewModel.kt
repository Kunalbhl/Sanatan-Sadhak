package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.BhaktiVideo
import com.example.data.ChatMessage
import com.example.data.CommunityPost
import com.example.data.GratitudeLog
import com.example.data.KarmaLog
import com.example.data.KnowledgeArticle
import com.example.data.PostComment
import com.example.data.UserRepository
import com.example.data.CommunityRepository
import com.example.data.KnowledgeRepository
import com.example.data.MantraRepository
import com.example.data.PanchangRepository
import com.example.data.ProfileRepository
import com.example.data.MeditationRepository
import com.example.data.GeminiService
import com.example.data.AstrologyService
import com.example.data.DailyMantra
import com.example.data.Shloka
import com.example.data.User
import com.example.data.InstagramPost
import com.example.data.ThoughtQuote
import com.example.data.Announcement
import com.example.data.YoutubeFetcher
import com.example.data.DetailedPanchang
import com.example.data.PanchangResult
import com.example.data.SadhanaRepository
import com.example.data.SadhanaBadge
import com.example.data.SadhanaProfileEntity
import com.example.data.LeaderboardEntry
import com.example.data.MilestoneCelebration
import com.example.data.SadhanaRankCalculator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import org.json.JSONObject

data class PrayerData(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val contentEn: String,
    val contentHi: String,
    val descEn: String,
    val descHi: String,
    val youtubeId: String = ""
)

val allPrayersList = listOf(
    PrayerData(
        id = "hanuman",
        titleEn = "Hanuman Chalisa",
        titleHi = "श्री हनुमान चालीसा",
        contentEn = "Shri Guru Charan Saroj Raj, Nij Manu Mukur Sudhari.\nBarnaum Raghuvar Bimal Jasu, Jo Dayaku Phal Chari.\nBudhi Heen Tanu Janike, Sumirow Pavan Kumar.\nBal Buddhi Vidya Dehu Mohi, Harahu Kalesh Bikaar.\n\nJai Hanuman Gyan Gun Sagar. Jai Kapis Tihun Lok Ujagar.\nRam Doot Atulit Bal Dhama. Anjani Putra Pavan Sut Nama.\nMahabir Bikram Bajrangi. Kumati Nivar Sumati Ke Sangi.\nKanchan Baran Viraj Subesa. Kanan Kundal Kunchit Kesa.\n\nHath Vajra Aur Dhvaja Viraje. Kandhe Moonj Janeu Saje.\nSankar Suvan Kesri Nandan. Tej Pratap Maha Jag Vandan.\nVidyavan Guni Ati Chatur. Ram Kaj Karibe Ko Atur.\nPrabhu Charitra Sunibe Ko Rasiya. Ram Lakhan Sita Man Basiya.\n\nSukshma Roop Dhari Siyahi Dikhava. Bikat Roop Dhari Lank Jarava.\nBhima Roop Dhari Asur Sanghare. Ramachandra Ke Kaj Sanvare.\nLaye Sanjivan Lakhan Jiyaye. Shri Raghuvir Harashi Ur Laye.\nRaghupati Kinhi Bahut Badai. Tum Mam Priye Bharat-Hi Sam Bhai.\n\nSahas Badan Tumharo Yash Gaave. Us Kahi Shripati Kanth Lagaave.\nSankadik Brahmadi Muneesa. Narad Sarad Sahit Aheesa.\nYam Kuber Digpal Jahan Te. Kavi Kovid Kahi Sake Kahan Te.\nTum Upkar Sugreevahin Keenha. Ram Milaye Rajpad Deenha.\n\nTumharo Mantra Vibheeshan Maana. Lankeshwar Bhay Sub Jag Jana.\nYug Sahasra Yojan Par Bhanu. Leelyo Tahi Madhur Phal Janu.\nPrabhu Mudrika Melikh Mukh Mahee. Jaladhi Langhi Gaye Achraj Nahee.\nDurgam Kaj Jagat Ke Jete. Sugam Anugraha Tumhare Tete.\n\nRam Duware Tum Rakhavare. Hoat Na Agya Binu Paisare.\nSub Sukh Lahai Tumhari Sarna. Tum Rakshak Kahu Ko Darna.\nAapan Tej Samharo Aapai. Teenhon Lok Hank Te Kanpai.\nBhoot Pisach Nikat Nahin Aavai. Mahavir Jab Naam Sunavai.\n\nNase Rog Harai Sab Peera. Japat Nirantar Hanumat Beera.\nSankat Se Hanuman Chhudavai. Man Kram Vachan Dhyan Jo Lavai.\nSub Par Ram Tapasvee Raja. Tin Ke Kaj Sakal Tum Saja.\nAur Manorath Jo Koi Lavai. Sohi Amit Jeevan Phal Pavai.\n\nCharon Yug Partap Tumhara. Hai Parsiddh Jagat Ujiyara.\nSadhu Sant Ke Tum Rakhware. Asur Nikandan Ram Dulhare.\nAshta Siddhi Nav Nidhi Ke Daata. As Var Deen Janki Mata.\nRam Rasayan Tumhare Pasa. Sada Raho Raghupati Ke Dasa.\n\nTumhare Bhajan Ram Ko Pavai. Janam Janam Ke Dukh Bisravai.\nAnth Kaal Raghuvir Pur Jayee. Jahan Janam Hari-Bhakta Kahayee.\nAur Devata Chitt Na Dharayi. Hanumat Sei Sarv Sukh Karayi.\nSankat Kate Mite Sab Peera. Jo Sumirai Hanumat Balbeera.\n\nJai Jai Jai Hanuman Gosahin. Kripa Karahu Gurudev Ki Nyahin.\nJo Sat Bar Path Kare Kohi. Chhutahi Bandi Maha Sukh Hohi.\nJo Yah Padhe Hanuman Chalisa. Hoye Siddhi Sakhi Gaureesa.\nTulsidas Sada Hari Chera. Keejai Das Hridaye Mein Dera.\n\nPavantanaye Sankat Haran, Mangal Moorti Roop.\nRam Lakhan Sita Sahit, Hriday Basahu Sur Bhoop.",
        contentHi = "श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।\nबरनऊं रघुबर बिमल जसु, जो दायकु फल चारि॥\nबुद्धिहीन तनु जानिके, सुमिरौं पवन-कुमार।\nबल बुद्धि बिद्या देहु मोहिं, हरहु कलेस बिकार॥\n\nजय हनुमान ज्ञान गुन सागर। जय कपीस तिहुं लोक उजागर॥\nरामदूत अतुलित बल धामा। अंजनि-पुत्र पवनसुत नामा॥\nमहाबीर बिक्रम बजरंगी। कुमति निवार सुमति के संगी॥\nकंचन बरन बिराज सुबेसा। कानन कुंडल कुंचित केसा॥\n\nहाथ बज्र औ ध्वजा बिराजै। कांधे मूंज जनेऊ साजै॥\nसंकर सुवन केसरीनंदन। तेज प्रताप महा जग बंदन॥\nबिद्यावान गुनी अति चातुर। राम काज करिबे को आतुर॥\nप्रभु चरित्र सुनिबे को रसिया। राम लखन सीता मन बसिया॥\n\nसूक्ष्म रूप धरि सियहिं दिखावा। बिकट रूप धरि लंक जरावा॥\nभीम रूप धरि असुर संहारे। रामचंद्र के काज संवारे॥\nलाय सजीवन लखन जियाये। श्रीरघुबीर हरषि उर लाये॥\nरघुपति कीन्ही बहुत बड़ाई। तुम मम प्रिय भरतहि सम भाई॥\n\nसहस बदन तुम्हरो जस गावैं। अस कहि श्रीपति कंठ लगावैं॥\nसनकादिक ब्रह्मादि मुनीसा। नारद सारद सहित अहीसा॥\nजम कुबेर दिगपाल जहां ते। कबि कोबिद कहि सके कहां ते॥\nतुम उपकार सुग्रीवहिं कीन्हा। राम मिलाय राज पद दीन्हा॥\n\nतुम्हरो मंत्र बिभीषन माना। लंकेस्वर भए सब जग जाना॥\nजुग सहस्र जोजन पर भानू। लील्यो ताहि मधुर फल जानू॥\nप्रभु मुद्रिका मेलि मुख माहीं। जलधि लांघि गये अचरज नाहीं॥\nदुर्गम काज जगत के जेते। सुगम अनुग्रह तुम्हरे तेते॥\n\nराम दुआरे तुम रखवारे। होत न आज्ञा बिनु पैसारे॥\nसब सुख लहै तुम्हारी सरना। तुम रक्षक काहू को डरना॥\nआपन तेज सम्हारो आपै। तीनों लोक हांक तें कांपै॥\nभूत पिसाच निकट नहिं आवै। महाबीर जब नाम सुनावै॥\n\nनासै रोग हरै सब पीरा। जपत निरंतर हनुमत बीरा॥\nसंकट तें हनुमान छुड़ावै। मन क्रम बचन ध्यान जो लावै॥\nसब पर राम तपस्वी राजा। तिन के काज सकल तुम साजा॥\nऔर मनोरथ जो कोई लावै। सोइ अमित जीवन फल पावै॥\n\nचारों जुग परताप तुम्हारा। है परसिद्ध जगत उजियारा॥\nसाधु संत के तुम रखवारे। असुर निकंदन राम दुलारे॥\nअष्ट सिद्धि नौ निधि के दाता। अस बर दीन जानकी माता॥\nराम रसायन तुम्हरे पासा। सदा रहो रघुपति के दासा॥\n\nतुम्हरे भजन राम को पावै। जनम जनम के दुख बिसरावै॥\nअंतकाल रघुबर पुर जाई। जहां जन्म हरि-भक्त कहाई॥\nऔर देवता चित्त न धरई। हनुमत सेइ सर्ब सुख करई॥\nसंकट कटै मिटै सब पीरा। जो सुमिरै हनुमत बलबीरा॥\n\nजै जै जै हनुमान गोसाईं। कृपा करहु गुरुदेव की नाईं॥\nजो सत बार पाठ कर कोई। छूटहि बंदि महा सुख होई॥\nजो यह पढ़ै हनुमान चालीसा। होय सिद्धि साखी गौरीसा॥\nतुलसीदास सदा हरि चेरा। कीजै नाथ हृदय मंह डेरा॥\n\nपवन तनय संकट हरन, मंगल मूरति रूप।\nराम लखन सीता सहित, हृदय बसहु सुर भूप॥",
        descEn = "A devotional hymn addressed to Lord Hanuman.",
        descHi = "भगवान हनुमान को समर्पित एक भक्ति भजन।"
    ),
    PrayerData(
        id = "annapurna",
        titleEn = "Maa Annapurna Stotram",
        titleHi = "श्री अन्नपूर्णा स्तोत्रम्",
        contentEn = "Nityanandakari Varabhayakari Saundaryaratnakari\nNirdhutakhilaghorapavanakari Pratyakshamaheshwari.\nPraleyachala Vamsha Pavanakari Kashipuradhishwari\nBhiksham Dehi Kripavalambanakari Mata Annapurneshwari.\n\nNanaratnavichitrabhushanakarim Hemambaradambarim\nMuktaharamavilambamana Vilasadvakshojakumbhantarim.\nKashmiraagaru Vasitangiruchiram Kashipuradhishwari\nBhiksham Dehi Kripavalambanakari Mata Annapurneshwari.\n\n(The Stotram praises Maa Annapurna, Goddess of Kashi, seeking her grace for physical and spiritual nourishment.)",
        contentHi = "नित्यानन्दकरी वराभयकरी सौन्दर्यरत्नाकरी\nनिर्धूताखिलघोरपावनकरी प्रत्यक्षमाहेश्वरी।\nप्रालेयाचलवंशपावनकरी काशीपुराधीश्वरी\nभिक्षां देहि कृपावलम्बनकरी मातान्नपूर्णेश्वरी॥\n\nनानारत्नविचित्रभूषणकरी हेमम्बराडम्बरी\nमुक्ताहारविलम्बमान विलसद्वक्षोजकुम्भान्तरी।\nकाश्मीरागरुवासिताङ्गिरुचिरा काशीपुराधीश्वरी\nभिक्षां देहि कृपावलम्बनकरी मातान्नपूर्णेश्वरी॥\n\n(माता अन्नपूर्णा का वंदन, जो काशी की देवी हैं। यह स्तोत्र शारीरिक और आध्यात्मिक पोषण के लिए उनकी कृपा का आह्वान करता है।)",
        descEn = "Stotram to Maa Annapurna for nourishment.",
        descHi = "पोषण के लिए माँ अन्नपूर्णा का स्तोत्र।"
    ),
    PrayerData(
        id = "shiva",
        titleEn = "Shiva Panchakshari Mantra",
        titleHi = "श्री शिव पंचाक्षर स्तोत्र",
        contentEn = "Nagendra Haraya Trilochanaya,\nBhasmanga Ragaya Maheshwaraya.\nNityaya Shuddhaya Digambaraya,\nTasmai 'Na' Karaya Namah Shivaya.\n\nMandakini Salila Chandana Charchitaya,\nNandishvara Pramatha Natha Maheshwaraya.\nMandarapushpa Bahupushpa Supujitaya,\nTasmai 'Ma' Karaya Namah Shivaya.\n\nShivaaya Gauri Vadanabjavrunda,\nSuryaya Dakshadhvara Nashakaya.\nShri Neelakanthaya Vrushadhvajaya,\nTasmai 'Shi' Karaya Namah Shivaya.\n\n(This supreme 5-syllable chant of Mahadev Shiva cleanses consciousness.)",
        contentHi = "नागेन्द्रहाराय त्रिलोचनाय,\nभस्माङ्गरागाय महेश्वराय।\nनित्याय शुद्धाय दिगम्बराय,\nतस्मै नकाराय नमः शिवाय॥\n\nमन्दाकिनी सलिल चन्दन चर्चिताय,\nनन्दीश्वर प्रमथनाथ महेश्वराय।\nमन्दारपुष्प बहुपुष्प सुपूजिताय,\nतस्मै मकाराय नमः शिवाय॥\n\nशिवाय गौरी वदनाब्जवृन्द,\nसूर्याय दक्षाध्वरनाशकाय।\nश्री नीलकण्ठाय वृषध्वजाय,\nतस्मै शिकाराय नमः शिवाय॥\n\n(भगवान शिव का सर्वोच्च 5-अक्षरी मंत्र जो चेतना को शुद्ध करता है।)",
        descEn = "The supreme 5-syllable chant of Mahadev.",
        descHi = "महादेव का सर्वोच्च 5-अक्षरी मंत्र।"
    ),
    PrayerData(
        id = "mrityunjaya",
        titleEn = "Maha Mrityunjaya Mantra",
        titleHi = "महामृत्युंजय मंत्र",
        contentEn = "Om Tryambakam Yajamahe\nSugandhim Pushti-Vardhanam\nUrvarukamiva Bandhanan\nMrityor Mukshiya Maamritat\n\n(A life-giving prayer to Lord Shiva to overcome the fear of death and grant spiritual liberation.)",
        contentHi = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान् मृत्योर्मुक्षीय मामृतात्॥\n\n(भगवान शिव से प्रार्थना जो मृत्यु के भय को दूर कर मोक्ष प्रदान करती है।)",
        descEn = "A life-giving prayer to Lord Shiva.",
        descHi = "भगवान शिव से जीवनदायिनी प्रार्थना।"
    )
)

data class DevotionalTrack(
    val id: Int,
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val artistEn: String,
    val artistHi: String,
    val url: String,
    val category: String = "General",
    val phoneticGuide: String? = null
)

val devotionalTracks = listOf(
    DevotionalTrack(id = 1, titleEn = "Mahamrityunjaya Mantra", titleHi = "महामृत्युंजय मंत्र", descriptionEn = "Protection and healing", descriptionHi = "रक्षा और उपचार", artistEn = "Aum Vedic", artistHi = "ॐ वैदिक", url = "maha_mrityunjaya", category = "Chants", phoneticGuide = "Om Tryambakam Yajamahe Sugandhim Pushti-Vardhanam..."),
    DevotionalTrack(id = 2, titleEn = "Gayatri Mantra", titleHi = "गायत्री मंत्र", descriptionEn = "Divine wisdom", descriptionHi = "दिव्य बुद्धि", artistEn = "Sadhana", artistHi = "साधना", url = "gayatri_mantra", category = "Chants", phoneticGuide = "Om Bhur Bhuvah Svah Tat Savitur Varenyam..."),
    DevotionalTrack(id = 3, titleEn = "Hanuman Chalisa", titleHi = "श्री हनुमान चालीसा", descriptionEn = "Strength and courage", descriptionHi = "बल और साहस", artistEn = "Vrindavan Singers", artistHi = "वृंदावन", url = "hanuman_chalisa", category = "Bhajans", phoneticGuide = "Sri Guru Charana Saroja Raja..."),
    DevotionalTrack(id = 4, titleEn = "Shiva Tandav Stotram", titleHi = "शिव तांडव स्तोत्रम्", descriptionEn = "Powerful praise", descriptionHi = "शक्तिशाली स्तुति", artistEn = "Traditional", artistHi = "परंपरागत", url = "shiva_tandav", category = "Stotras", phoneticGuide = "Jataatavee galajjala pravaha pavitha sthale..."),
    DevotionalTrack(id = 5, titleEn = "Vishnu Sahasranamam", titleHi = "विष्णु सहस्रनाम", descriptionEn = "Thousand names", descriptionHi = "विष्णु के हजार नाम", artistEn = "Traditional", artistHi = "परंपरागत", url = "vishnu_sahasranamam", category = "Stotras", phoneticGuide = "Vishwam Vishnuh Vashatkaro..."),
    DevotionalTrack(id = 6, titleEn = "Rameshbhai Ojha - Sampurna Vishnu Sahasranamam", titleHi = "रमेशभाई ओझा - सम्पूर्ण विष्णु सहस्रनाम", descriptionEn = "Complete thousand names of Lord Vishnu recited by Pujya Bhaishree", descriptionHi = "पूज्य भाईश्री द्वारा पाठ किया गया भगवान विष्णु के सम्पूर्ण हजार नाम", artistEn = "Rameshbhai Ojha", artistHi = "रमेशभाई ओझा", url = "vishnu_sahasranamam", category = "Rameshbhai Ojha"),
    DevotionalTrack(id = 7, titleEn = "Rameshbhai Ojha - Sampurna Shiv Tandav Stotram", titleHi = "रमेशभाई ओझा - सम्पूर्ण शिव तांडव स्तोत्रम्", descriptionEn = "Complete Shiv Tandav Stotram recitation by Pujya Bhaishree", descriptionHi = "पूज्य भाईश्री द्वारा शिव तांडव स्तोत्रम् का सम्पूर्ण पाठ", artistEn = "Rameshbhai Ojha", artistHi = "रमेशभाई ओझा", url = "shiva_tandav", category = "Rameshbhai Ojha")
)


@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class SadhakViewModel(application: Application) : AndroidViewModel(application) {

    private val _isRefreshing = kotlinx.coroutines.flow.MutableStateFlow(false)
    val isRefreshing: kotlinx.coroutines.flow.StateFlow<Boolean> = _isRefreshing

    fun refreshHome() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                kotlinx.coroutines.delay(600)
            } catch (_: Exception) {
                // Keep cached data safe without crash
            } finally {
                _isRefreshing.value = false
            }
        }
    }
    
    fun refreshGeneric() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            try {
                _isRefreshing.value = true
                kotlinx.coroutines.delay(600)
            } catch (_: Exception) {
                // Keep cached data safe without crash
            } finally {
                _isRefreshing.value = false
            }
        }
    }


    private val userRepository = UserRepository.getInstance(application)
    private val communityRepository = CommunityRepository(application)
    private val knowledgeRepository = KnowledgeRepository(application)
    private val mantraRepository = MantraRepository(application)
    private val panchangRepository = PanchangRepository(application)
    private val profileRepository = ProfileRepository(application)
    private val meditationRepository = MeditationRepository(application)
    private val sadhanaRepository = SadhanaRepository.getInstance(application)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Current Active Screen
    private val _currentScreen = MutableStateFlow("Home") // "Home", "Knowledge", "Community", "Bhakti", "Videos", "Chat", "Profile", "Admin"
    val currentScreen: StateFlow<String> = _currentScreen

    // Bilingual UI Toggle

    val isEnglish: StateFlow<Boolean> = com.example.data.AppPreferences.isEnglish





    // Daily Mantra and Shloka

    
    
    
    val isDarkMode: StateFlow<Boolean> = com.example.data.AppPreferences.isDarkMode

    private val _hasNotifications = MutableStateFlow(false)
    val hasNotifications: StateFlow<Boolean> = _hasNotifications

    fun setNotifications(has: Boolean) {
        _hasNotifications.value = has
    }

    fun toggleTheme() {
        com.example.data.AppPreferences.toggleDarkMode()
    }

    // Global Audio Player State
    private val _globalAudioTitle = MutableStateFlow("")
    val globalAudioTitle: StateFlow<String> = _globalAudioTitle

    private val _isGlobalAudioPlaying = MutableStateFlow(false)
    val isGlobalAudioPlaying: StateFlow<Boolean> = _isGlobalAudioPlaying

    private val _globalAudioProgress = MutableStateFlow(0f)
    val globalAudioProgress: StateFlow<Float> = _globalAudioProgress

    private fun getSoundName(type: Int): String {
        return when(type) {
            1 -> "temple_bell"
            2 -> "om_ambient"
            3 -> "flute_ambient"
            else -> "om_ambient"
        }
    }

    fun playGlobalAudio(title: String, type: Int) {
        pauseAudio()
        _globalAudioTitle.value = title
        _isGlobalAudioPlaying.value = true
        val trackId = com.example.ui.audio.AudioTrackId.fromIndex(type)
        com.example.ui.audio.SadhakAudioManager.playTrack(getApplication(), trackId, isLooping = true)
    }

    fun pauseGlobalAudio() {
        _isGlobalAudioPlaying.value = false
        com.example.ui.audio.SadhakAudioManager.pause()
    }
    
    fun toggleGlobalAudio() {
        if (_isGlobalAudioPlaying.value) {
            pauseGlobalAudio()
        } else {
            pauseAudio()
            _isGlobalAudioPlaying.value = true
            com.example.ui.audio.SadhakAudioManager.playTrack(getApplication(), com.example.ui.audio.AudioTrackId.OM_AMBIENT, isLooping = true)
        }
    }

    fun stopGlobalAudio() {
        _isGlobalAudioPlaying.value = false
        _globalAudioTitle.value = ""
        _globalAudioProgress.value = 0f
        com.example.ui.audio.SadhakAudioManager.stop()
    }

    // Theme Preference (0 = System, 1 = Light, 2 = Dark)
    val themePreference: StateFlow<Int> = com.example.data.AppPreferences.themePreference

    val soundEnabled: StateFlow<Boolean> = com.example.data.AppPreferences.soundEnabled

    fun toggleSound() {
        com.example.data.AppPreferences.toggleSound()
    }

    // Notifications toggle state
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    private val _backgroundChantEnabled = MutableStateFlow(false)
    val backgroundChantEnabled: StateFlow<Boolean> = _backgroundChantEnabled
    private val _backgroundChantVolume = MutableStateFlow(0.3f)
    val backgroundChantVolume: StateFlow<Float> = _backgroundChantVolume

    fun toggleBackgroundChant() {
        _backgroundChantEnabled.value = !_backgroundChantEnabled.value
    }
    fun setBackgroundChantVolume(vol: Float) {
        _backgroundChantVolume.value = vol
    }

    fun toggleNotifications() {
        val newVal = !_notificationsEnabled.value
        _notificationsEnabled.value = newVal
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences(com.example.notifications.SadhakReminderScheduler.PREFS_NAME, android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean(com.example.notifications.SadhakReminderScheduler.KEY_MASTER_NOTIFICATIONS, newVal).apply()
        if (newVal) {
            com.example.notifications.SadhakReminderScheduler.rescheduleAllActive(context)
        } else {
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_SHLOKA_PANCHANG)
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_MORNING_PRAYER)
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_EVENING_PRAYER)
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_BRAHMA_MUHURTA)
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_SANDHYA)
            com.example.notifications.SadhakReminderScheduler.cancelAlarm(context, com.example.notifications.SadhakNotificationHelper.ID_JAPA)
        }
    }

    // --- DEVOTIONAL MUSIC PLAYER ---
    val isAudioPlaying: StateFlow<Boolean> = com.example.ui.audio.SadhakAudioManager.isPlaying

    private val _currentAudioTrackIndex = MutableStateFlow(0)
    val currentAudioTrackIndex: StateFlow<Int> = _currentAudioTrackIndex

    private val _isAudioPlayerVisible = MutableStateFlow(true)
    val isAudioPlayerVisible: StateFlow<Boolean> = _isAudioPlayerVisible
    
    private val _isBottomBarVisible = MutableStateFlow(true)
    val isBottomBarVisible: StateFlow<Boolean> = _isBottomBarVisible

    fun setBottomBarVisibility(visible: Boolean) {
        _isBottomBarVisible.value = visible
    }

    fun closeAudioPlayer() {
        _isAudioPlayerVisible.value = false
        pauseAudio()
        stopAndReleasePlayer()
    }

    fun showAudioPlayer() {
        _isAudioPlayerVisible.value = true
    }

    fun toggleAudioPlay() {
        _isAudioPlayerVisible.value = true
        if (isAudioPlaying.value) {
            pauseAudio()
        } else {
            playAudio()
        }
    }

    fun playAudio() {
        viewModelScope.launch {
            try {
                pauseGlobalAudio()
                _isAudioPlayerVisible.value = true
                val track = devotionalTracks[_currentAudioTrackIndex.value]
                val trackId = com.example.ui.audio.AudioTrackId.fromRawName(track.url) ?: com.example.ui.audio.AudioTrackId.OM_AMBIENT
                com.example.ui.audio.SadhakAudioManager.playTrack(getApplication(), trackId, isLooping = false)
            } catch (e: Exception) {
                android.util.Log.e("SadhakViewModel", "Error starting audio playback", e)
            }
        }
    }

    fun pauseAudio() {
        try {
            com.example.ui.audio.SadhakAudioManager.pause()
        } catch (e: Exception) {
            android.util.Log.e("SadhakViewModel", "Error pausing audio", e)
        }
    }
    
    fun seekAudio(progress: Float) {
        try {
            com.example.ui.audio.SadhakAudioManager.seekTo(progress)
            _globalAudioProgress.value = progress
        } catch (e: Exception) {
            android.util.Log.e("SadhakViewModel", "Error seeking audio", e)
        }
    }

    fun nextAudioTrack() {
        viewModelScope.launch {
            var nextIndex = _currentAudioTrackIndex.value + 1
            if (nextIndex >= devotionalTracks.size) {
                nextIndex = 0
            }
            changeAudioTrack(nextIndex)
        }
    }

    fun prevAudioTrack() {
        viewModelScope.launch {
            var prevIndex = _currentAudioTrackIndex.value - 1
            if (prevIndex < 0) {
                prevIndex = devotionalTracks.size - 1
            }
            changeAudioTrack(prevIndex)
        }
    }

    fun changeAudioTrack(index: Int) {
        _isAudioPlayerVisible.value = true
        _currentAudioTrackIndex.value = index
        stopAndReleasePlayer()
        playAudio()
    }

    private fun stopAndReleasePlayer() {
        com.example.ui.audio.SadhakAudioManager.stop()
    }

    // --- DAILY SHLOKA & PANCHANG REMINDERS ---
    private val _reminderEnabled = MutableStateFlow(false)
    val reminderEnabled: StateFlow<Boolean> = _reminderEnabled

    private val _reminderHour = MutableStateFlow(8)
    val reminderHour: StateFlow<Int> = _reminderHour

    private val _reminderMinute = MutableStateFlow(0)
    val reminderMinute: StateFlow<Int> = _reminderMinute

    private val _reminderType = MutableStateFlow("Both")
    val reminderType: StateFlow<String> = _reminderType

    fun scheduleShlokaPanchangReminder(hour: Int, minute: Int, type: String) {
        val app = getApplication<android.app.Application>()
        val prefs = app.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("reminder_hour", hour)
            .putInt("reminder_minute", minute)
            .putString("reminder_type", type)
            .putBoolean("reminder_enabled", true)
            .apply()

        val schedPrefs = app.getSharedPreferences(com.example.notifications.SadhakReminderScheduler.PREFS_NAME, android.content.Context.MODE_PRIVATE)
        schedPrefs.edit()
            .putBoolean(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_ENABLED, true)
            .putInt(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_HOUR, hour)
            .putInt(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_MINUTE, minute)
            .putString(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_TYPE, type)
            .apply()

        _reminderHour.value = hour
        _reminderMinute.value = minute
        _reminderType.value = type
        _reminderEnabled.value = true

        val destination = when (type) {
            "Morning Prayer" -> "Bhakti"
            "Panchang" -> "Calendar"
            "Shloka" -> "Home"
            else -> "Home"
        }

        // Schedule exact alarm
        com.example.notifications.SadhakReminderScheduler.scheduleDailyAlarm(
            context = app,
            reminderId = com.example.notifications.SadhakNotificationHelper.ID_SHLOKA_PANCHANG,
            hour = hour,
            minute = minute,
            reminderType = type,
            channelId = com.example.notifications.SadhakNotificationHelper.CHANNEL_PANCHANG,
            destination = destination
        )

        // WorkManager backup
        val now = java.util.Calendar.getInstance()
        val scheduledTime = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
        }
        if (scheduledTime.before(now)) {
            scheduledTime.add(java.util.Calendar.DAY_OF_YEAR, 1)
        }
        val initialDelay = scheduledTime.timeInMillis - now.timeInMillis
        val workRequest = androidx.work.PeriodicWorkRequestBuilder<com.example.workers.NotificationWorker>(24, java.util.concurrent.TimeUnit.HOURS)
            .setInitialDelay(initialDelay, java.util.concurrent.TimeUnit.MILLISECONDS)
            .setInputData(androidx.work.Data.Builder()
                .putString("notification_type", type)
                .putString("destination", destination)
                .build())
            .build()
            
        androidx.work.WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "daily_spiritual_reminder",
            androidx.work.ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun cancelShlokaPanchangReminder() {
        val app = getApplication<android.app.Application>()
        val prefs = app.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("reminder_enabled", false)
            .apply()

        val schedPrefs = app.getSharedPreferences(com.example.notifications.SadhakReminderScheduler.PREFS_NAME, android.content.Context.MODE_PRIVATE)
        schedPrefs.edit()
            .putBoolean(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_ENABLED, false)
            .apply()

        _reminderEnabled.value = false
        com.example.notifications.SadhakReminderScheduler.cancelAlarm(app, com.example.notifications.SadhakNotificationHelper.ID_SHLOKA_PANCHANG)
        androidx.work.WorkManager.getInstance(app).cancelUniqueWork("daily_spiritual_reminder")
    }

    fun scheduleDailyPrayerReminder(hour: Int, minute: Int) {
        val app = getApplication<android.app.Application>()
        val isMorning = hour < 12
        val reminderId = if (isMorning) com.example.notifications.SadhakNotificationHelper.ID_MORNING_PRAYER else com.example.notifications.SadhakNotificationHelper.ID_EVENING_PRAYER
        val reminderType = if (isMorning) "Morning Prayer" else "Evening Prayer"
        val channelId = com.example.notifications.SadhakNotificationHelper.CHANNEL_DAILY_SADHANA

        val schedPrefs = app.getSharedPreferences(com.example.notifications.SadhakReminderScheduler.PREFS_NAME, android.content.Context.MODE_PRIVATE)
        if (isMorning) {
            schedPrefs.edit()
                .putBoolean(com.example.notifications.SadhakReminderScheduler.KEY_MORNING_PRAYER_ENABLED, true)
                .putInt(com.example.notifications.SadhakReminderScheduler.KEY_MORNING_PRAYER_HOUR, hour)
                .putInt(com.example.notifications.SadhakReminderScheduler.KEY_MORNING_PRAYER_MINUTE, minute)
                .apply()
        } else {
            schedPrefs.edit()
                .putBoolean(com.example.notifications.SadhakReminderScheduler.KEY_EVENING_PRAYER_ENABLED, true)
                .putInt(com.example.notifications.SadhakReminderScheduler.KEY_EVENING_PRAYER_HOUR, hour)
                .putInt(com.example.notifications.SadhakReminderScheduler.KEY_EVENING_PRAYER_MINUTE, minute)
                .apply()
        }

        // Schedule exact alarm
        com.example.notifications.SadhakReminderScheduler.scheduleDailyAlarm(
            context = app,
            reminderId = reminderId,
            hour = hour,
            minute = minute,
            reminderType = reminderType,
            channelId = channelId,
            destination = "Bhakti"
        )

        // WorkManager backup
        val now = java.util.Calendar.getInstance()
        val scheduledTime = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, hour)
            set(java.util.Calendar.MINUTE, minute)
            set(java.util.Calendar.SECOND, 0)
        }
        if (scheduledTime.before(now)) {
            scheduledTime.add(java.util.Calendar.DAY_OF_YEAR, 1)
        }
        val initialDelay = scheduledTime.timeInMillis - now.timeInMillis
        val workRequest = androidx.work.PeriodicWorkRequestBuilder<com.example.workers.NotificationWorker>(24, java.util.concurrent.TimeUnit.HOURS)
            .setInitialDelay(initialDelay, java.util.concurrent.TimeUnit.MILLISECONDS)
            .setInputData(androidx.work.Data.Builder()
                .putString("notification_type", reminderType)
                .putString("destination", "Bhakti")
                .build())
            .build()
            
        androidx.work.WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            "daily_prayer_$hour:$minute",
            androidx.work.ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }


    // Active Category Filters
    private val _selectedKnowledgeCategory = MutableStateFlow("All")
    val selectedKnowledgeCategory: StateFlow<String> = _selectedKnowledgeCategory

    private val _selectedVideoCategory = MutableStateFlow("All")
    val selectedVideoCategory: StateFlow<String> = _selectedVideoCategory

    private val _communitySortBy = MutableStateFlow("New") // "New", "Top", "Discussed"
    val communitySortBy: StateFlow<String> = _communitySortBy

    // Selected Post for Comments Details
    private val _selectedPostId = MutableStateFlow<Int?>(null)
    val selectedPostId: StateFlow<Int?> = _selectedPostId

    // Chatbot loading state
    

    // Dialog control states
    private val _showLanguagePrefDialog = MutableStateFlow(false)
    val showLanguagePrefDialog: StateFlow<Boolean> = _showLanguagePrefDialog

    private val _showGuestPromoDialog = MutableStateFlow(false)
    val showGuestPromoDialog: StateFlow<Boolean> = _showGuestPromoDialog

    fun dismissLanguagePrefDialog() {
        _showLanguagePrefDialog.value = false
    }

    fun triggerGuestPromo() {
        _showGuestPromoDialog.value = true
    }

    fun dismissGuestPromo() {
        _showGuestPromoDialog.value = false
    }

    fun logoutUser() {
        userRepository.logout()
    }

    fun setUserSessionForTesting(email: String, role: String, name: String = "Test Sadhak") {
        userRepository.loginAsUser(
            com.example.data.User(
                email = email,
                fullName = name,
                mobileNumber = "",
                city = "",
                state = "",
                password = "",
                role = role
            )
        )
    }

    fun deleteUserAccount(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = userRepository.deleteAccount()
            onResult(success)
        }
    }

    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            userRepository.updateUserProfile(userEmail.value, fullName, mobileNumber, city, state, avatar, imageUri)
            onResult(true)
        }
    }

    fun addFavoriteMantra(title: String, content: String, youtubeId: String = "") {
        viewModelScope.launch {
            mantraRepository.addFavoriteMantra(com.example.data.FavoriteMantra(title = title, content = content, youtubeId = youtubeId))
        }
    }

    fun deleteFavoriteMantra(id: Int) {
        viewModelScope.launch {
            mantraRepository.deleteFavoriteMantra(id)
        }
    }

    // User Profile & Roles (linked to repository flow)
    val userRole: StateFlow<String> = userRepository.userRoleState
    val userName: StateFlow<String> = userRepository.userNameState
    val userAvatar: StateFlow<Int> = userRepository.userAvatarState
    val userProfileImageUri: StateFlow<String> = userRepository.userProfileImageUriState
    val isPublicPostingEnabled: StateFlow<Boolean> = userRepository.isPublicPostingEnabled

    val favoriteMantras: StateFlow<List<com.example.data.FavoriteMantra>> = mantraRepository.getFavoriteMantras().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val bgChantEnabled: StateFlow<Boolean> = com.example.data.AppPreferences.bgChantEnabled
    val bgChantVolume: StateFlow<Float> = com.example.data.AppPreferences.bgChantVolume

    val userEmail: StateFlow<String> = userRepository.userEmailState
    val userMobile: StateFlow<String> = userRepository.userMobileState
    val userCity: StateFlow<String> = userRepository.userCityState
    val userStateProvince: StateFlow<String> = userRepository.userStateProvinceState

    // Panchang & Daily Thought Backing States
    private val _tithiEn = MutableStateFlow("Shukla Paksha Ekadashi")
    private val _tithiHi = MutableStateFlow("शुक्ल पक्ष एकादशी")
    private val _nakshatraEn = MutableStateFlow("Rohini Nakshatra")
    private val _nakshatraHi = MutableStateFlow("रोहिणी नक्षत्र")
    private val _yogaEn = MutableStateFlow("Siddha Yoga")
    private val _yogaHi = MutableStateFlow("सिद्ध योग")
    private val _festivalEn = MutableStateFlow("Devotional Puja Muhurat")
    private val _festivalHi = MutableStateFlow("भक्ति पूजा मुहूर्त")
    private val _thoughtEn = MutableStateFlow("Perform action without attachment to outcomes.")
    private val _thoughtHi = MutableStateFlow("फल की इच्छा के बिना कर्म करो।")

    private val _karanaEn = MutableStateFlow("Bava Karana")
    private val _karanaHi = MutableStateFlow("बव करण")
    private val _rashiEn = MutableStateFlow("Taurus (Vrishabha)")
    private val _rashiHi = MutableStateFlow("वृषभ राशि")
    private val _sunrise = MutableStateFlow("05:47 AM")
    private val _sunset = MutableStateFlow("07:15 PM")
    private val _rahukaal = MutableStateFlow("01:30 PM to 03:00 PM")
    private val _abhijeet = MutableStateFlow("11:50 AM to 12:40 PM")
    private val _detailsEn = MutableStateFlow("This day is auspicious for devotional activities.")
    private val _detailsHi = MutableStateFlow("आज का दिन भक्तिमय गतिविधियों के लिए शुभ है।")

    val currentTithi: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _tithiEn, _tithiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Shukla Paksha Ekadashi")
    
    val currentNakshatra: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _nakshatraEn, _nakshatraHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Rohini Nakshatra")

    val currentYoga: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _yogaEn, _yogaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Siddha Yoga")

    val currentFestival: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _festivalEn, _festivalHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Devotional Puja Muhurat")

    val currentThought: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _thoughtEn, _thoughtHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Perform action without attachment to outcomes.")

    val currentKarana: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _karanaEn, _karanaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Bava Karana")

    val currentRashi: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _rashiEn, _rashiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Taurus (Vrishabha)")

    val currentSunrise: StateFlow<String> = _sunrise
    val currentSunset: StateFlow<String> = _sunset
    val currentRahukaal: StateFlow<String> = _rahukaal
    val currentAbhijeet: StateFlow<String> = _abhijeet

    val currentDetails: StateFlow<String> = combine(com.example.data.AppPreferences.isEnglish, _detailsEn, _detailsHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "This day is auspicious for devotional activities.")

    

    val rawTithiEn: StateFlow<String> = _tithiEn
    val rawTithiHi: StateFlow<String> = _tithiHi
    val rawNakshatraEn: StateFlow<String> = _nakshatraEn
    val rawNakshatraHi: StateFlow<String> = _nakshatraHi
    val rawYogaEn: StateFlow<String> = _yogaEn
    val rawYogaHi: StateFlow<String> = _yogaHi
    val rawFestivalEn: StateFlow<String> = _festivalEn
    val rawFestivalHi: StateFlow<String> = _festivalHi
    val rawThoughtEn: StateFlow<String> = _thoughtEn
    val rawThoughtHi: StateFlow<String> = _thoughtHi

    // --- ADMINISTRATIVE & PERMISSION STATE FLOWS ---
    fun isAdminUser(email: String = userEmail.value, role: String = userRole.value): Boolean {
        return email.equals("kunalpareekusa@gmail.com", ignoreCase = true) ||
               email.equals("ashishpareekbhl@gmail.com", ignoreCase = true) ||
               role.equals("ADMIN", ignoreCase = true) ||
               role.equals("SuperUser", ignoreCase = true) ||
               role.equals("ADMINISTRATOR", ignoreCase = true)
    }

    val isAdmin: StateFlow<Boolean> = combine(userEmail, userRole) { email, role ->
        isAdminUser(email, role)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val allUsers: StateFlow<List<User>> = userRepository.getAllUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentUser: StateFlow<User?> = userEmail.flatMapLatest { email ->
        if (email.isEmpty()) flowOf(null)
        else {
            userRepository.getAllUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()).map { users -> users.find { it.email.equals(email, ignoreCase = true) } }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val canUserPost: StateFlow<Boolean> = combine(userEmail, currentUser, isPublicPostingEnabled) { email, user, globalEnabled ->
        val isHardcodedAdmin = email == "kunalpareekusa@gmail.com" || email == "ashishpareekbhl@gmail.com"
        isHardcodedAdmin || globalEnabled || (user?.canPost ?: false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _onboardingDismissedInSession = MutableStateFlow<Set<String>>(emptySet())

    val shouldShowAutomaticOnboarding: StateFlow<Boolean> = combine(userRole, userEmail, _onboardingDismissedInSession) { role, email, dismissedSet ->
        val cleanEmail = email.lowercase().trim()
        if (role.equals("Guest", ignoreCase = true) || cleanEmail.isBlank() || cleanEmail == "guest" || cleanEmail == "guest@sanatan.org") {
            false
        } else if (dismissedSet.contains(cleanEmail)) {
            false
        } else {
            !com.example.data.AppPreferences.hasUserCompletedOnboarding(cleanEmail)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun completeOnboardingForActiveUser() {
        val email = userEmail.value.lowercase().trim()
        if (email.isNotBlank()) {
            com.example.data.AppPreferences.setOnboardingState(email, "COMPLETED")
            _onboardingDismissedInSession.value = _onboardingDismissedInSession.value + email
        }
    }

    fun skipOnboardingForActiveUser() {
        val email = userEmail.value.lowercase().trim()
        if (email.isNotBlank()) {
            com.example.data.AppPreferences.setOnboardingState(email, "SKIPPED")
            _onboardingDismissedInSession.value = _onboardingDismissedInSession.value + email
        }
    }

    // --- NEW REPO EXPOSURES ---
    val instagramPosts: StateFlow<List<InstagramPost>> = communityRepository.getInstagramPosts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val thoughtQuotes: StateFlow<List<ThoughtQuote>> = knowledgeRepository.getThoughtQuotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<Announcement>> = knowledgeRepository.getAnnouncements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- NOTIFICATION BELL FLOW ---

    private val _notifications = MutableStateFlow<List<com.example.data.AppNotification>>(emptyList())
    val notifications: StateFlow<List<com.example.data.AppNotification>> = _notifications

    private val _hasNewNotifications = MutableStateFlow(false)
    val hasNewNotifications: StateFlow<Boolean> = _hasNewNotifications

    fun toggleBgChant() {
        com.example.data.AppPreferences.toggleBgChant()
    }

    fun setBgChantVolume(vol: Float) {
        com.example.data.AppPreferences.setBgChantVolume(vol)
    }

    private val _aartiSessionLogs = MutableStateFlow<List<String>>(emptyList())
    val aartiSessionLogs: StateFlow<List<String>> = _aartiSessionLogs
    private val _currentSessionAartiCount = MutableStateFlow(0)
    val currentSessionAartiCount: StateFlow<Int> = _currentSessionAartiCount
    private var sessionStartTimeStr = java.text.SimpleDateFormat("MMM dd, hh:mm a", java.util.Locale.getDefault()).format(System.currentTimeMillis())

    fun incrementAartiBell() {
        _currentSessionAartiCount.value += 1
        val count = _currentSessionAartiCount.value
        val msg = "Session ($sessionStartTimeStr): You rung bell $count times"
        val currentLogs = _aartiSessionLogs.value.toMutableList()
        
        // Remove the log for current session if it exists, to replace it
        currentLogs.removeAll { it.startsWith("Session ($sessionStartTimeStr)") }
        currentLogs.add(msg)
        _aartiSessionLogs.value = currentLogs
    }

    init {
        _notifications.value = listOf()

        
        
        // Read initial notification flag and preferences
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        _hasNewNotifications.value = prefs.getBoolean("has_new_notifications", false)
        
        val schedPrefs = context.getSharedPreferences(com.example.notifications.SadhakReminderScheduler.PREFS_NAME, android.content.Context.MODE_PRIVATE)
        _notificationsEnabled.value = schedPrefs.getBoolean(com.example.notifications.SadhakReminderScheduler.KEY_MASTER_NOTIFICATIONS, true)
        
        val isReminderOn = schedPrefs.getBoolean(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_ENABLED, prefs.getBoolean("reminder_enabled", false))
        _reminderEnabled.value = isReminderOn
        _reminderHour.value = schedPrefs.getInt(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_HOUR, prefs.getInt("reminder_hour", 8))
        _reminderMinute.value = schedPrefs.getInt(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_MINUTE, prefs.getInt("reminder_minute", 0))
        _reminderType.value = schedPrefs.getString(com.example.notifications.SadhakReminderScheduler.KEY_SHLOKA_PANCHANG_TYPE, prefs.getString("reminder_type", "Both")) ?: "Both"
        
        // Auto-login
        viewModelScope.launch {
            val rememberedUserEmail = userRepository.getRememberedUserEmail()
            if (!rememberedUserEmail.isNullOrEmpty()) {
                val user = userRepository.authenticateUserByEmailOnly(rememberedUserEmail)
                if (user != null) {
                    val isSuper = user.email.lowercase().trim() == "kunalpareekusa@gmail.com" || user.email.lowercase().trim() == "ashishpareekbhl@gmail.com"
                    val finalUser = user.copy(
                        role = if (isSuper) "SuperUser" else "Regular",
                        avatar = if (isSuper) 4 else user.avatar
                    )
                    userRepository.loginAsUser(finalUser)
                }
            }
        }
        
        // Auto-run YouTube sync once per day on open
        

        viewModelScope.launch {
            com.example.ui.audio.SadhakAudioManager.progress.collect { p ->
                _globalAudioProgress.value = p
            }
        }
    }

    fun markNotificationAsRead(id: String) {
        _notifications.value = _notifications.value.map { if (it.id == id) it.copy(isRead = true) else it }
    }

    fun markAllNotificationsAsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
        setHasNewNotifications(false)
    }

    fun clearAllNotifications() {
        _notifications.value = emptyList()
        setHasNewNotifications(false)
    }

    fun setHasNewNotifications(value: Boolean) {
        _hasNewNotifications.value = value
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean("has_new_notifications", value).apply()
    }

    fun publishInstagramPost(url: String, caption: String) {
        viewModelScope.launch {
            val post = InstagramPost(url = url, caption = caption)
            communityRepository.addInstagramPost(post)
            setHasNewNotifications(true)
        }
    }

    fun updateInstagramPost(post: InstagramPost) {
        viewModelScope.launch {
            communityRepository.updateInstagramPost(post)
        }
    }

    fun deleteInstagramPost(id: Int) {
        viewModelScope.launch {
            communityRepository.deleteInstagramPostById(id)
        }
    }

    // --- THOUGHT CRUD ---
    fun addThoughtQuote(textEn: String, textHi: String) {
        viewModelScope.launch {
            val quote = ThoughtQuote(textEn = textEn, textHi = textHi)
            knowledgeRepository.addThoughtQuote(quote)
        }
    }

    fun updateThoughtQuote(quote: ThoughtQuote) {
        viewModelScope.launch {
            knowledgeRepository.updateThoughtQuote(quote)
        }
    }

    fun deleteThoughtQuote(id: Int) {
        viewModelScope.launch {
            knowledgeRepository.deleteThoughtQuoteById(id)
        }
    }

    // --- ANNOUNCEMENT CRUD ---
    fun addAnnouncement(textEn: String, textHi: String, isVisible: Boolean = true) {
        viewModelScope.launch {
            val ann = Announcement(textEn = textEn, textHi = textHi, isVisible = isVisible)
            knowledgeRepository.addAnnouncement(ann)
        }
    }

    fun updateAnnouncement(announcement: Announcement) {
        viewModelScope.launch {
            knowledgeRepository.updateAnnouncement(announcement)
        }
    }

    fun deleteAnnouncement(id: Int) {
        viewModelScope.launch {
            knowledgeRepository.deleteAnnouncementById(id)
        }
    }

    // --- ARTICLE CRUD EXTRA ---
    fun updateKnowledgeArticle(article: KnowledgeArticle) {
        viewModelScope.launch {
            knowledgeRepository.updateArticle(article)
        }
    }

    fun deleteKnowledgeArticle(id: Int) {
        viewModelScope.launch {
            knowledgeRepository.deleteArticle(id)
        }
    }

    // --- VIDEO CRUD EXTRA ---
    fun addInstagramPost(url: String, caption: String, category: String) {
        viewModelScope.launch {
            communityRepository.addInstagramPost(InstagramPost(url = url, caption = caption, category = category))
        }
    }

    // --- USER MANAGEMENT PERMISSIONS ---
    fun updateUserCanPost(email: String, canPost: Boolean) {
        viewModelScope.launch {
            userRepository.updateUserCanPost(email, canPost)
        }
    }

    fun updateUserRole(email: String, role: String) {
        viewModelScope.launch {
            userRepository.updateUserRole(email, role)
        }
    }

    // --- COMMENT DELETION EXTRA ---
    fun deletePost(id: Int) {
        viewModelScope.launch {
            communityRepository.deletePost(id)
        }
    }


    fun deletePostComment(id: Int) {
        viewModelScope.launch {
            communityRepository.deleteComment(id)
        }
    }

    // --- REVENUE STATS (Mocked) ---
    val statsPostsCount = MutableStateFlow(12)
    val statsLikesCount = MutableStateFlow(248)
    val statsCommentsCount = MutableStateFlow(54)

    // --- OBSERVABLE STATE FLOWS ---

    private val _knowledgeSortBy = MutableStateFlow("Newest") // "Newest", "A-Z"
    val knowledgeSortBy: StateFlow<String> = _knowledgeSortBy

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _knowledgeFormat = MutableStateFlow("All")
    val knowledgeFormat: StateFlow<String> = _knowledgeFormat

    private val _knowledgeDifficulty = MutableStateFlow("All")
    val knowledgeDifficulty: StateFlow<String> = _knowledgeDifficulty

    fun setKnowledgeSort(sort: String) {
        _knowledgeSortBy.value = sort
    }

    fun setKnowledgeSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setKnowledgeFormat(format: String) {
        _knowledgeFormat.value = format
    }

    fun setKnowledgeDifficulty(difficulty: String) {
        _knowledgeDifficulty.value = difficulty
    }

data class FilterParams(val eng: Boolean, val cat: String, val sort: String, val query: String, val format: String, val difficulty: String)

    // 1. Articles (Reactively updates when language, category, sort, search, format, or difficulty changes)
    val articles: StateFlow<List<KnowledgeArticle>> = combine(
        com.example.data.AppPreferences.isEnglish as Flow<Any>,
        _selectedKnowledgeCategory as Flow<Any>,
        _knowledgeSortBy as Flow<Any>,
        _searchQuery as Flow<Any>,
        _knowledgeFormat as Flow<Any>,
        _knowledgeDifficulty as Flow<Any>
    ) { args ->
        FilterParams(
            eng = args[0] as Boolean,
            cat = args[1] as String,
            sort = args[2] as String,
            query = args[3] as String,
            format = args[4] as String,
            difficulty = args[5] as String
        )
    }.flatMapLatest { params ->
        val lang = if (params.eng) "en" else "hi"
        knowledgeRepository.getArticles(lang).map { list ->
            list.filter { article ->
                (params.cat == "All" || article.category == params.cat) &&
                (params.format == "All" || article.format == params.format) &&
                (params.difficulty == "All" || article.difficulty == params.difficulty) &&
                (params.query.isEmpty() || article.title.contains(params.query, ignoreCase = true) || 
                                   article.summary.contains(params.query, ignoreCase = true) || 
                                   article.content.contains(params.query, ignoreCase = true))
            }.sortedWith(
                when (params.sort) {
                    "A-Z" -> compareBy { it.title }
                    else -> compareByDescending { it.id } // Newest
                }
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<KnowledgeArticle>())

    // Knowledge Hub Search
    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 5) current.removeAt(current.size - 1)
        _recentSearches.value = current
    }

    // 3. Posts (Reactively updates when sorting changes)
    val posts: StateFlow<List<CommunityPost>> = communityRepository.getPosts()
        .combine(_communitySortBy) { list, sort ->
            val sortedList = when (sort) {
                "Top" -> list.sortedByDescending { it.upvotes - it.downvotes }
                "Discussed" -> list.sortedByDescending { it.commentsCount }
                else -> list.sortedByDescending { it.createdAt }
            }
            sortedList.sortedWith(compareByDescending { it.isPinned })
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allComments: StateFlow<List<PostComment>> = communityRepository.getAllComments().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 4. Comments for active post
    val activeComments: StateFlow<List<PostComment>> = _selectedPostId.flatMapLatest { id ->
        communityRepository.getComments(id ?: -1)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 5. Karma logs
    val karmaLogs: StateFlow<List<KarmaLog>> = profileRepository.getKarmaLogs().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sankalpaLogs: StateFlow<List<com.example.data.SankalpaLog>> = meditationRepository.getSankalpaLogs().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    fun addSankalpaLog(intention: String, progressNote: String, progressValue: Int, date: String) {
        viewModelScope.launch {
            meditationRepository.addSankalpaLog(com.example.data.SankalpaLog(intention = intention, progressNote = progressNote, progressValue = progressValue, date = date))
        }
    }

    val gratitudeLogs: StateFlow<List<GratitudeLog>> = profileRepository.getGratitudeLogs().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val karmaPoints: StateFlow<Int> = combine(karmaLogs, gratitudeLogs) { k, g ->
        (k.size * 10) + (g.size * 5)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dailyStreak: StateFlow<Int> = karmaLogs.map { if (it.isNotEmpty()) it.first().streak else 0 }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    
    private val _guestQuestionCount = MutableStateFlow(0)
    val guestQuestionCount: StateFlow<Int> = _guestQuestionCount

    // 8. Favorite Mantras
    // 9. Designated Super User
    val designatedSuperUser: StateFlow<String> = userRepository.designatedSuperUser

    private val _guestActionCount = MutableStateFlow(0)
    val guestActionCount = _guestActionCount

    private val _showLoginWall = MutableStateFlow(false)
    val showLoginWall = _showLoginWall

    private val _bhaktiTab = MutableStateFlow("Mantras")
    val bhaktiTab = _bhaktiTab

    // --- INTERACTIVE ACTIONS ---

        fun registerGuestAction(): Boolean {
        if (userRole.value == "Guest") {
            if (_guestActionCount.value >= 10) {
                _showLoginWall.value = true
                return false
            }
            _guestActionCount.value += 1
        }
        return true
    }

    fun requireLoginAction(): Boolean {
        if (userRole.value == "Guest") {
            _showLoginWall.value = true
            return false
        }
        return true
    }

    fun dismissLoginWall() {
        _showLoginWall.value = false
    }

    private val _navigationHistory = MutableStateFlow<List<String>>(listOf("Home"))
    val navigationHistory: StateFlow<List<String>> = _navigationHistory

    fun setScreen(screen: String) {
        com.example.ui.audio.SadhakAudioManager.stop()
        if (screen == "Bhakti" && _bhaktiTab.value !in listOf("Dashboard", "Mantras", "Timer", "My Mantras")) {
            _bhaktiTab.value = "Dashboard"
        } else if (screen == "BhaktiTools" && _bhaktiTab.value !in listOf("Bell", "Counter", "KarmaLog", "Gratitude", "TempleFinder")) {
            _bhaktiTab.value = "Counter"
        }

        // Manage navigation backstack cleanly
        val currentHistory = _navigationHistory.value.toMutableList()
        if (screen == "Home") {
            _navigationHistory.value = listOf("Home")
        } else {
            if (currentHistory.isEmpty() || currentHistory.last() != screen) {
                currentHistory.add(screen)
                _navigationHistory.value = currentHistory
            }
        }

        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        val currentHistory = _navigationHistory.value.toMutableList()
        if (currentHistory.size > 1) {
            currentHistory.removeAt(currentHistory.size - 1)
            _navigationHistory.value = currentHistory
            
            val prevScreen = currentHistory.last()
            com.example.ui.audio.SadhakAudioManager.stop()
            if (prevScreen == "Bhakti" && _bhaktiTab.value !in listOf("Dashboard", "Mantras", "Timer", "My Mantras")) {
                _bhaktiTab.value = "Dashboard"
            } else if (prevScreen == "BhaktiTools" && _bhaktiTab.value !in listOf("Bell", "Counter", "KarmaLog", "Gratitude", "TempleFinder")) {
                _bhaktiTab.value = "Counter"
            }
            _currentScreen.value = prevScreen
            return true
        }
        return false
    }

    fun navigateToBhakti(tab: String) {
        com.example.ui.audio.SadhakAudioManager.stop()
        _bhaktiTab.value = tab
        if (tab in listOf("Dashboard", "Mantras", "Timer", "My Mantras")) {
            setScreen("Bhakti")
        } else {
            setScreen("BhaktiTools")
        }
    }


    fun toggleLanguage() {
        com.example.data.AppPreferences.toggleLanguage()
    }

    fun setThemePreference(pref: Int) {
        com.example.data.AppPreferences.setThemePreference(pref)
    }


    fun setKnowledgeCategory(category: String) {
        _selectedKnowledgeCategory.value = category
    }

    fun setVideoCategory(category: String) {
        _selectedVideoCategory.value = category
    }

    fun setCommunitySort(sort: String) {
        _communitySortBy.value = sort
    }

    fun selectPost(postId: Int?) {
        _selectedPostId.value = postId
    }

    // --- CREATE ACTIONS ---

    fun createPost(title: String, content: String, category: String) {
        viewModelScope.launch {
            val authorName = if (userRole.value == "Guest") "Guest Sadhak" else userName.value
            val newPost = CommunityPost(
                category = category,
                author = authorName,
                title = title,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            communityRepository.addPost(newPost)
            statsPostsCount.value += 1
        }
    }

    fun addPostComment(postId: Int, content: String) {
        viewModelScope.launch {
            val authorName = if (userRole.value == "Guest") "Guest Sadhak" else userName.value
            val newComment = PostComment(
                postId = postId,
                author = authorName,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            communityRepository.addComment(newComment)
            
            val currentPosts = posts.value
            val targetPost = currentPosts.find { it.id == postId }
            if (targetPost != null) {
                communityRepository.updatePost(targetPost.copy(commentsCount = targetPost.commentsCount + 1))
            }
            statsCommentsCount.value += 1
        }
    }

    fun addKnowledgeArticle(title: String, summary: String, content: String, category: String, lang: String) {
        viewModelScope.launch {
            val newArticle = KnowledgeArticle(
                category = category,
                title = title,
                summary = summary,
                content = content,
                relatedVideos = "zHzPEdLJEvT",
                language = lang
            )
            knowledgeRepository.addArticle(newArticle)
        }
    }

    fun setPublicPosting(enabled: Boolean) {
        viewModelScope.launch {
            userRepository.setPublicPostingEnabled(enabled)
        }
    }

    // --- KARMA & GRATITUDE WRITERS ---

    fun logKarmaDeed(deed: String) {
        viewModelScope.launch {
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val todayDate = java.util.Date()
            val today = dateFormat.format(todayDate)
            
            val lastLogs = karmaLogs.value
            val lastLog = lastLogs.firstOrNull()

            val newStreak = if (lastLog != null) {
                if (lastLog.date == today) {
                    lastLog.streak // Already logged today
                } else {
                    val lastDate = dateFormat.parse(lastLog.date)
                    val diffInMs = todayDate.time - (lastDate?.time ?: 0)
                    val diffInDays = diffInMs / (1000 * 60 * 60 * 24)
                    
                    if (diffInDays == 1L) {
                        lastLog.streak + 1
                    } else {
                        1
                    }
                }
            } else {
                1
            }

            val log = KarmaLog(
                date = today,
                deed = deed,
                streak = newStreak
            )
            profileRepository.addKarmaLog(log)
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordDailyDharma(effectiveId, deed)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun logGratitude(content: String) {
        viewModelScope.launch {
            val log = GratitudeLog(
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                content = content
            )
            profileRepository.addGratitudeLog(log)
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordDailyDharma(effectiveId, content)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun signUp(email: String, pass: String, name: String, city: String, state: String, mobile: String = "", onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val emailClean = email.lowercase().trim()
            val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
            val newUser = com.example.data.User(
                email = emailClean,
                fullName = name,
                mobileNumber = mobile,
                city = city,
                state = state,
                password = pass,
                avatar = if (isSuper) 4 else 1,
                role = if (isSuper) "SuperUser" else "Regular",
                profileImageUri = ""
            )
            val success = userRepository.registerUser(newUser)
            _isLoading.value = false
            if (success) {
                userRepository.loginAsUser(newUser)
                userRepository.setRememberedUser(newUser.email)
                _showLanguagePrefDialog.value = true
                onResult(true, "Successfully Registered and Logged In!")
            } else {
                onResult(false, "Sadhak Email is already registered.")
            }
        }
    }

    fun login(email: String, pass: String, rememberMe: Boolean = false, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val emailClean = email.lowercase().trim()
            
            val user = userRepository.authenticateUser(emailClean, pass)
            _isLoading.value = false
            if (user != null) {
                val isSuper = user.email.lowercase().trim() == "kunalpareekusa@gmail.com" || user.email.lowercase().trim() == "ashishpareekbhl@gmail.com"
                val finalUser = user.copy(
                    role = if (isSuper) "SuperUser" else "Regular",
                    avatar = if (isSuper) 4 else user.avatar
                )
                userRepository.loginAsUser(finalUser)
                if (rememberMe) {
                    userRepository.setRememberedUser(finalUser.email)
                } else {
                    userRepository.clearRememberedUser()
                }
                _showLanguagePrefDialog.value = true
                onResult(true, "Pranam! Logged in successfully.")
            } else {
                // Check if it's super user to create them dynamically
                val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
                if (isSuper) {
                    val defaultSuper = com.example.data.User(
                        email = emailClean,
                        fullName = if (emailClean.contains("kunal")) "Kunal Pareek" else "Ashish Pareek",
                        mobileNumber = "9999999999",
                        city = "Ajmer",
                        state = "Rajasthan",
                        password = pass,
                        avatar = 4,
                        role = "SuperUser"
                    )
                    userRepository.registerUser(defaultSuper)
                    userRepository.loginAsUser(defaultSuper)
                    if (rememberMe) {
                        userRepository.setRememberedUser(defaultSuper.email)
                    } else {
                        userRepository.clearRememberedUser()
                    }
                    _showLanguagePrefDialog.value = true
                    onResult(true, "Pranam! Super User Account Initialized.")
                } else {
                    onResult(false, "Incorrect Sadhak Email/Mobile or Password.")
                }
            }
        }
    }

    fun sendPasswordReset(email: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val emailClean = email.lowercase().trim()
            if (emailClean.isBlank()) {
                onResult(false, if (isEnglish.value) "Email cannot be empty" else "ईमेल खाली नहीं हो सकता")
                return@launch
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailClean).matches()) {
                onResult(false, if (isEnglish.value) "Please enter a valid email" else "कृपया एक मान्य ईमेल दर्ज करें")
                return@launch
            }
            val firebaseSuccess = userRepository.firebaseService.sendPasswordResetEmail(emailClean)
            val user = userRepository.getUserByEmail(emailClean)
            if (firebaseSuccess || user != null || emailClean.contains("@")) {
                onResult(
                    true,
                    if (isEnglish.value) 
                        "Password reset email sent to $emailClean. Please check your inbox."
                    else 
                        "$emailClean पर पासवर्ड रीसेट लिंक भेज दिया गया है। कृपया अपना इनबॉक्स देखें।"
                )
            } else {
                onResult(
                    false,
                    if (isEnglish.value) "No registered account found with this email" else "इस ईमेल से कोई पंजीकृत खाता नहीं मिला"
                )
            }
        }
    }

    fun upvotePost(post: com.example.data.CommunityPost) {
        if (userRole.value == "Guest") return
        val email = userEmail.value
        val upvotedList = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        val downvotedList = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        
        var newUpvotes = post.upvotes
        var newDownvotes = post.downvotes

        if (upvotedList.contains(email)) {
            upvotedList.remove(email)
            newUpvotes--
        } else {
            upvotedList.add(email)
            newUpvotes++
            if (downvotedList.contains(email)) {
                downvotedList.remove(email)
                newDownvotes--
            }
        }
        
        val updatedPost = post.copy(
            upvotes = newUpvotes,
            downvotes = newDownvotes,
            upvotedByEmails = upvotedList.joinToString(","),
            downvotedByEmails = downvotedList.joinToString(",")
        )
        viewModelScope.launch {
            communityRepository.updatePost(updatedPost)
        }
    }

    fun downvotePost(post: com.example.data.CommunityPost) {
        if (userRole.value == "Guest") return
        val email = userEmail.value
        val upvotedList = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        val downvotedList = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        
        var newUpvotes = post.upvotes
        var newDownvotes = post.downvotes

        if (downvotedList.contains(email)) {
            downvotedList.remove(email)
            newDownvotes--
        } else {
            downvotedList.add(email)
            newDownvotes++
            if (upvotedList.contains(email)) {
                upvotedList.remove(email)
                newDownvotes--
            }
        }
        
        val updatedPost = post.copy(
            upvotes = newUpvotes,
            downvotes = newDownvotes,
            upvotedByEmails = upvotedList.joinToString(","),
            downvotedByEmails = downvotedList.joinToString(",")
        )
        viewModelScope.launch {
            communityRepository.updatePost(updatedPost)
        }
    }

    fun togglePinPost(post: com.example.data.CommunityPost) {
        if (isAdmin.value) {
            val updatedPost = post.copy(isPinned = !post.isPinned)
            viewModelScope.launch {
                communityRepository.updatePost(updatedPost)
            }
        }
    }

    fun deletePost(post: com.example.data.CommunityPost) {
        if (isAdmin.value || post.author == userName.value) {
            viewModelScope.launch {
                communityRepository.deletePost(post.id)
            }
        }
    }

    fun toggleLikePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (upEmails.contains(email)) {
                upEmails.remove(email)
            } else {
                upEmails.add(email)
                downEmails.remove(email)
            }
            communityRepository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun toggleDislikePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (downEmails.contains(email)) {
                downEmails.remove(email)
            } else {
                downEmails.add(email)
                upEmails.remove(email)
            }
            communityRepository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun pinPost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            communityRepository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            communityRepository.deleteComment(id)
        }
    }

    fun toggleLikeComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (upEmails.contains(email)) {
                upEmails.remove(email)
            } else {
                upEmails.add(email)
                downEmails.remove(email)
            }
            communityRepository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun toggleDislikeComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (downEmails.contains(email)) {
                downEmails.remove(email)
            } else {
                downEmails.add(email)
                upEmails.remove(email)
            }
            communityRepository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    // --- USER FAVORITES FUNCTIONS ---
    fun getUserFavorites(userId: String): Flow<List<com.example.data.UserFavorite>> = profileRepository.getUserFavorites(userId)
    fun addUserFavorite(favorite: com.example.data.UserFavorite) = viewModelScope.launch { profileRepository.addUserFavorite(favorite) }
    fun deleteUserFavorite(userId: String, targetId: String, type: String) = viewModelScope.launch { profileRepository.deleteUserFavorite(userId, targetId, type) }

    val favoritePostIds: StateFlow<Set<Int>> = userEmail.flatMapLatest { email ->
        val uid = email.ifEmpty { "guest" }
        profileRepository.getUserFavorites(uid).map { list ->
            list.filter { it.type == "community_post" }.mapNotNull { it.targetId.toIntOrNull() }.toSet()
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    val favoriteVideoIds: StateFlow<Set<String>> = userEmail.flatMapLatest { email ->
        val uid = email.ifEmpty { "guest" }
        profileRepository.getUserFavorites(uid).map { list ->
            list.filter { it.type == "bhakti_video" }.map { it.targetId }.toSet()
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    fun toggleFavoritePost(postId: Int) {
        viewModelScope.launch {
            val uid = userEmail.value.ifEmpty { "guest" }
            val currentFavorites = favoritePostIds.value
            if (currentFavorites.contains(postId)) {
                profileRepository.deleteUserFavorite(uid, postId.toString(), "community_post")
            } else {
                profileRepository.addUserFavorite(
                    com.example.data.UserFavorite(
                        userId = uid,
                        targetId = postId.toString(),
                        type = "community_post"
                    )
                )
            }
        }
    }

    suspend fun toggleFavoriteVideoSync(videoId: String) {
        if (videoId.isBlank()) return
        val uid = userEmail.value.ifEmpty { "guest" }
        val currentFavorites = favoriteVideoIds.value
        if (currentFavorites.contains(videoId)) {
            profileRepository.deleteUserFavorite(uid, videoId, "bhakti_video")
        } else {
            profileRepository.addUserFavorite(
                com.example.data.UserFavorite(
                    userId = uid,
                    targetId = videoId,
                    type = "bhakti_video"
                )
            )
        }
    }

    fun toggleFavoriteVideo(videoId: String) {
        if (videoId.isBlank()) return
        viewModelScope.launch {
            toggleFavoriteVideoSync(videoId)
        }
    }

    // --- READING PROGRESS FUNCTIONS ---
    suspend fun getReadingProgress(userId: String, articleId: Int): com.example.data.ReadingProgress? = knowledgeRepository.getReadingProgress(userId, articleId)
    fun saveReadingProgress(progress: com.example.data.ReadingProgress) = viewModelScope.launch { knowledgeRepository.saveReadingProgress(progress) }

    // --- FESTIVAL FUNCTIONS ---
    suspend fun getFestival(date: String): com.example.data.FestivalEntity? = panchangRepository.getFestival(date)
    fun saveFestival(festival: com.example.data.FestivalEntity) = viewModelScope.launch { panchangRepository.saveFestival(festival) }
    fun getAllFestivals(): Flow<List<com.example.data.FestivalEntity>> = panchangRepository.getAllFestivals()

    // --- SADHANA BADGING, RANKING & LEADERBOARD SYSTEM ---
    private val _activeMilestoneCelebration = MutableStateFlow<MilestoneCelebration?>(null)
    val activeMilestoneCelebration: StateFlow<MilestoneCelebration?> = _activeMilestoneCelebration.asStateFlow()

    private val _leaderboardPeriod = MutableStateFlow("All Time") // "Weekly", "Monthly", "All Time"
    val leaderboardPeriod: StateFlow<String> = _leaderboardPeriod.asStateFlow()

    val sadhanaProfile: StateFlow<SadhanaProfileEntity> = userEmail.flatMapLatest { email ->
        val effectiveId = if (email.isBlank()) "guest_devotee" else email.trim().lowercase()
        sadhanaRepository.getProfileFlow(effectiveId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SadhanaProfileEntity(userId = "guest_devotee")
    )

    val sadhanaBadges: StateFlow<List<SadhanaBadge>> = userEmail.flatMapLatest { email ->
        val effectiveId = if (email.isBlank()) "guest_devotee" else email.trim().lowercase()
        sadhanaRepository.getBadgesFlow(effectiveId)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        sadhanaRepository.allBadgesCatalog
    )

    val leaderboardEntries: StateFlow<List<LeaderboardEntry>> = combine(
        userEmail,
        _leaderboardPeriod
    ) { email, period ->
        Pair(if (email.isBlank()) "guest_devotee" else email.trim().lowercase(), period)
    }.flatMapLatest { (effectiveId, period) ->
        sadhanaRepository.getLeaderboard(effectiveId, period)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setLeaderboardPeriod(period: String) {
        _leaderboardPeriod.value = period
    }

    fun dismissMilestoneCelebration() {
        _activeMilestoneCelebration.value = null
    }

    fun recordJapaMalaCompletion(malaCount: Int = 1) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordJapaMala(effectiveId, malaCount)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun recordMeditationMinutes(minutes: Int) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordMeditationMinutes(effectiveId, minutes)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun recordScriptureRead(articleId: Int, title: String) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordScriptureReading(effectiveId, articleId, title)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun recordDailyDharmaRead(category: String) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordDailyDharma(effectiveId, category)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun recordMantraSaved(title: String) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.recordSavedMantra(effectiveId, title)
            val profile = sadhanaRepository.getOrCreateProfile(effectiveId)
            val celebration = sadhanaRepository.checkForMilestoneCelebration(profile)
            if (celebration != null) {
                _activeMilestoneCelebration.value = celebration
            }
        }
    }

    fun setShowcaseBadge(badgeId: String) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.setShowcaseBadge(effectiveId, badgeId)
        }
    }

    fun setLeaderboardOptIn(optIn: Boolean) {
        viewModelScope.launch {
            val effectiveId = if (userEmail.value.isBlank()) "guest_devotee" else userEmail.value.trim().lowercase()
            sadhanaRepository.setLeaderboardOptIn(effectiveId, optIn)
        }
    }
}

