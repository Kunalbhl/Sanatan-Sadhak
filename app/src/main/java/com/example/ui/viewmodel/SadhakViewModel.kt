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
import com.example.data.SadhakRepository
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
    val url: String
)

val devotionalTracks = listOf(
    DevotionalTrack(
        id = 1,
        titleEn = "Mahamrityunjaya Mantra Chants",
        titleHi = "महामृत्युंजय मंत्र जाप",
        descriptionEn = "Sacred sound vibrations of Lord Shiva for protection and healing.",
        descriptionHi = "मोक्ष और आरोग्यता के लिए भगवान शिव का महामंत्र जाप।",
        artistEn = "Aum Vedic Chants",
        artistHi = "ॐ वैदिक साधक",
        url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    ),
    DevotionalTrack(
        id = 2,
        titleEn = "Gayatri Mantra Meditation",
        titleHi = "गायत्री मंत्र ध्यान",
        descriptionEn = "Universal cosmic sound for divine wisdom, intellect, and light.",
        descriptionHi = "दिव्य बुद्धि और प्रकाश के लिए सार्वभौमिक ब्रह्मांडीय मंत्र ध्यान।",
        artistEn = "Sadhana Choir",
        artistHi = "साधना मंडल",
        url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"
    ),
    DevotionalTrack(
        id = 3,
        titleEn = "Divine Flute & Chant Ambient",
        titleHi = "बांसुरी और मंत्र ध्यान",
        descriptionEn = "Peaceful flute music with background chants for deep dhyana.",
        descriptionHi = "गहरे ध्यान और शांति के लिए पृष्ठभूमि मंत्रों के साथ बांसुरी वादन।",
        artistEn = "Vrindavan Devotees",
        artistHi = "वृंदावन वंशी",
        url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3"
    )
)


class SadhakViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SadhakRepository(application)

    // Current Active Screen
    private val _currentScreen = MutableStateFlow("Home") // "Home", "Knowledge", "Community", "Bhakti", "Videos", "Chat", "Profile", "Admin"
    val currentScreen: StateFlow<String> = _currentScreen

    // Bilingual UI Toggle

    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    // Daily Mantra and Shloka
    private val _dailyMantra = MutableStateFlow(
        DailyMantra(
            title = "Thought of the Day",
            content = "In the attitude of silence the soul finds the path in a clearer light, and what is elusive and deceptive resolves itself into crystal clearness. - Mahatma Gandhi",
            meaning = "True clarity and understanding come from within, through silence and reflection.",
            audioUrl = ""
        )
    )
    val dailyMantra: StateFlow<DailyMantra> = _dailyMantra.asStateFlow()

    fun setDailyMantra(newMantra: DailyMantra) {
        _dailyMantra.value = newMantra
    }

    private val _shlokaOfTheDay = MutableStateFlow(
        Shloka(
            verse = "यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥",
            meaning = "Whenever righteousness declines...",
            source = "Bhagavad Gita 4.7"
        )
    )
    val shlokaOfTheDay: StateFlow<Shloka> = _shlokaOfTheDay.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _hasNotifications = MutableStateFlow(false)
    val hasNotifications: StateFlow<Boolean> = _hasNotifications.asStateFlow()

    fun setNotifications(has: Boolean) {
        _hasNotifications.value = has
    }

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // Theme Preference (0 = System, 1 = Light, 2 = Dark)
    private val _themePreference = MutableStateFlow(1)
    val themePreference: StateFlow<Int> = _themePreference

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
        _notificationsEnabled.value = !_notificationsEnabled.value
    }

    // --- DEVOTIONAL MUSIC PLAYER ---
    private var mediaPlayer: android.media.MediaPlayer? = null

    private val _isAudioPlaying = MutableStateFlow(false)
    val isAudioPlaying: StateFlow<Boolean> = _isAudioPlaying.asStateFlow()

    private val _currentAudioTrackIndex = MutableStateFlow(0)
    val currentAudioTrackIndex: StateFlow<Int> = _currentAudioTrackIndex.asStateFlow()

    private val _isAudioPlayerVisible = MutableStateFlow(true)
    val isAudioPlayerVisible: StateFlow<Boolean> = _isAudioPlayerVisible.asStateFlow()

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
        if (_isAudioPlaying.value) {
            pauseAudio()
        } else {
            playAudio()
        }
    }

    fun playAudio() {
        viewModelScope.launch {
            try {
                _isAudioPlaying.value = true
                _isAudioPlayerVisible.value = true
                if (mediaPlayer == null) {
                    initMediaPlayer()
                } else {
                    try {
                        mediaPlayer?.start()
                    } catch (e: IllegalStateException) {
                        // Media player might be in preparing state; the OnPreparedListener will start it.
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun pauseAudio() {
        try {
            mediaPlayer?.pause()
            _isAudioPlaying.value = false
        } catch (e: Exception) {
            e.printStackTrace()
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
        _isAudioPlaying.value = true
        playAudio()
    }

    private fun initMediaPlayer() {
        try {
            mediaPlayer = android.media.MediaPlayer().apply {
                setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)
                setDataSource(devotionalTracks[_currentAudioTrackIndex.value].url)
                setOnPreparedListener {
                    if (_isAudioPlaying.value) {
                        it.start()
                    }
                }
                setOnCompletionListener {
                    nextAudioTrack()
                }
                prepareAsync()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopAndReleasePlayer() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- DAILY SHLOKA & PANCHANG REMINDERS ---
    private val _reminderEnabled = MutableStateFlow(false)
    val reminderEnabled: StateFlow<Boolean> = _reminderEnabled.asStateFlow()

    private val _reminderHour = MutableStateFlow(8)
    val reminderHour: StateFlow<Int> = _reminderHour.asStateFlow()

    private val _reminderMinute = MutableStateFlow(0)
    val reminderMinute: StateFlow<Int> = _reminderMinute.asStateFlow()

    private val _reminderType = MutableStateFlow("Both")
    val reminderType: StateFlow<String> = _reminderType.asStateFlow()

    fun scheduleShlokaPanchangReminder(hour: Int, minute: Int, type: String) {
        val prefs = getApplication<android.app.Application>()
            .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putInt("reminder_hour", hour)
            .putInt("reminder_minute", minute)
            .putString("reminder_type", type)
            .putBoolean("reminder_enabled", true)
            .apply()

        _reminderHour.value = hour
        _reminderMinute.value = minute
        _reminderType.value = type
        _reminderEnabled.value = true

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
            .setInputData(androidx.work.Data.Builder().putString("notification_type", type).build())
            .build()
            
        androidx.work.WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "daily_spiritual_reminder",
            androidx.work.ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    fun cancelShlokaPanchangReminder() {
        val prefs = getApplication<android.app.Application>()
            .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean("reminder_enabled", false)
            .apply()

        _reminderEnabled.value = false
        androidx.work.WorkManager.getInstance(getApplication()).cancelUniqueWork("daily_spiritual_reminder")
    }

    fun scheduleDailyPrayerReminder(hour: Int, minute: Int) {
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
            .build()
            
        androidx.work.WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
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
    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading

    // Dialog control states
    private val _showLanguagePrefDialog = MutableStateFlow(false)
    val showLanguagePrefDialog: StateFlow<Boolean> = _showLanguagePrefDialog

    private val _showGuestPromoDialog = MutableStateFlow(false)
    val showGuestPromoDialog: StateFlow<Boolean> = _showGuestPromoDialog

    // User Profile & Roles (linked to repository flow)
    val userRole: StateFlow<String> = repository.userRoleState
    val userName: StateFlow<String> = repository.userNameState
    val userAvatar: StateFlow<Int> = repository.userAvatarState
    val userProfileImageUri: StateFlow<String> = repository.userProfileImageUriState
    val isPublicPostingEnabled: StateFlow<Boolean> = repository.isPublicPostingEnabled

    val userEmail: StateFlow<String> = repository.userEmailState
    val userMobile: StateFlow<String> = repository.userMobileState
    val userCity: StateFlow<String> = repository.userCityState
    val userStateProvince: StateFlow<String> = repository.userStateProvinceState

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

    val currentTithi: StateFlow<String> = combine(_isEnglish, _tithiEn, _tithiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Shukla Paksha Ekadashi")
    
    val currentNakshatra: StateFlow<String> = combine(_isEnglish, _nakshatraEn, _nakshatraHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Rohini Nakshatra")

    val currentYoga: StateFlow<String> = combine(_isEnglish, _yogaEn, _yogaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Siddha Yoga")

    val currentFestival: StateFlow<String> = combine(_isEnglish, _festivalEn, _festivalHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Devotional Puja Muhurat")

    val currentThought: StateFlow<String> = combine(_isEnglish, _thoughtEn, _thoughtHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Perform action without attachment to outcomes.")

    val currentKarana: StateFlow<String> = combine(_isEnglish, _karanaEn, _karanaHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Bava Karana")

    val currentRashi: StateFlow<String> = combine(_isEnglish, _rashiEn, _rashiHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Taurus (Vrishabha)")

    val currentSunrise: StateFlow<String> = _sunrise
    val currentSunset: StateFlow<String> = _sunset
    val currentRahukaal: StateFlow<String> = _rahukaal
    val currentAbhijeet: StateFlow<String> = _abhijeet

    val currentDetails: StateFlow<String> = combine(_isEnglish, _detailsEn, _detailsHi) { eng, en, hi -> if (eng) en else hi }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "This day is auspicious for devotional activities.")

    val panchangSource = MutableStateFlow("Offline") // "API", "Gemini", or "Offline"
    val todayDetailedPanchang = MutableStateFlow<DetailedPanchang?>(null)
    val panchangError = MutableStateFlow<String?>(null)
    val panchangTechnicalError = MutableStateFlow<String?>(null)
    val panchangLastUpdated = MutableStateFlow<String>("")

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
    val isAdmin: StateFlow<Boolean> = userEmail.map { email ->
        email == "kunalpareekusa@gmail.com" || email == "ashishpareekbhl@gmail.com"
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val allUsers: StateFlow<List<User>> = repository.getAllUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val currentUser: StateFlow<User?> = userEmail.flatMapLatest { email ->
        if (email.isEmpty()) flowOf(null)
        else {
            repository.getAllUsers().map { users -> users.find { it.email.equals(email, ignoreCase = true) } }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val canUserPost: StateFlow<Boolean> = combine(userEmail, currentUser, isPublicPostingEnabled) { email, user, globalEnabled ->
        val isHardcodedAdmin = email == "kunalpareekusa@gmail.com" || email == "ashishpareekbhl@gmail.com"
        isHardcodedAdmin || globalEnabled || (user?.canPost ?: false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // --- NEW REPO EXPOSURES ---
    val instagramPosts: StateFlow<List<InstagramPost>> = repository.getInstagramPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val thoughtQuotes: StateFlow<List<ThoughtQuote>> = repository.getThoughtQuotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val announcements: StateFlow<List<Announcement>> = repository.getAnnouncements()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- NOTIFICATION BELL FLOW ---
    private val _hasNewNotifications = MutableStateFlow(false)
    val hasNewNotifications: StateFlow<Boolean> = _hasNewNotifications

    private val _isSyncingVideos = MutableStateFlow(false)
    val isSyncingVideos: StateFlow<Boolean> = _isSyncingVideos

    private val _notifications = MutableStateFlow<List<com.example.data.AppNotification>>(emptyList())
    val notifications: StateFlow<List<com.example.data.AppNotification>> = _notifications

    private val _bgChantEnabled = MutableStateFlow(false)
    val bgChantEnabled: StateFlow<Boolean> = _bgChantEnabled

    private val _bgChantVolume = MutableStateFlow(0.5f)
    val bgChantVolume: StateFlow<Float> = _bgChantVolume

    fun toggleBgChant() {
        _bgChantEnabled.value = !_bgChantEnabled.value
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().putBoolean("bg_chant", _bgChantEnabled.value).apply()
    }

    fun setBgChantVolume(vol: Float) {
        _bgChantVolume.value = vol
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit().putFloat("bg_chant_vol", vol).apply()
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
        // load initial fake notifications
        _notifications.value = listOf(
            com.example.data.AppNotification(title = "New Video Added", message = "A new Darshan video is available.", type = "video"),
            com.example.data.AppNotification(title = "Spiritual Article", message = "Read the latest article on Bhagavad Gita.", type = "article")
        )

        loadDailyPanchangAndThought()
        
        // Read initial notification flag from SharedPreferences
        val context = getApplication<android.app.Application>()
        val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        _hasNewNotifications.value = prefs.getBoolean("has_new_notifications", false)
        _bgChantEnabled.value = prefs.getBoolean("bg_chant", false)
        _bgChantVolume.value = prefs.getFloat("bg_chant_vol", 0.5f)
        
        _reminderEnabled.value = prefs.getBoolean("reminder_enabled", false)
        _reminderHour.value = prefs.getInt("reminder_hour", 8)
        _reminderMinute.value = prefs.getInt("reminder_minute", 0)
        _reminderType.value = prefs.getString("reminder_type", "Both") ?: "Both"
        
        // Auto-login
        viewModelScope.launch {
            val rememberedUserEmail = repository.getRememberedUserEmail()
            if (!rememberedUserEmail.isNullOrEmpty()) {
                val user = repository.authenticateUserByEmailOnly(rememberedUserEmail)
                if (user != null) {
                    val isSuper = user.email.lowercase().trim() == "kunalpareekusa@gmail.com" || user.email.lowercase().trim() == "ashishpareekbhl@gmail.com"
                    val finalUser = user.copy(
                        role = if (isSuper) "SuperUser" else "Regular",
                        avatar = if (isSuper) 4 else user.avatar
                    )
                    repository.loginAsUser(finalUser)
                }
            }
        }
        
        // Auto-run YouTube sync once per day on open
        autoSyncYoutubeVideosIfNeeded()
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

    fun autoSyncYoutubeVideosIfNeeded() {
        viewModelScope.launch {
            try {
                val context = getApplication<android.app.Application>()
                val prefs = context.getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
                val lastSync = prefs.getLong("last_youtube_sync_timestamp", 0L)
                val now = System.currentTimeMillis()
                
                val sdf = java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault())
                val lastSyncDate = if (lastSync == 0L) "" else sdf.format(java.util.Date(lastSync))
                val todayDate = sdf.format(java.util.Date(now))
                
                if (lastSync == 0L || lastSyncDate != todayDate) {
                    val apiKey = com.example.BuildConfig.YOUTUBE_API_KEY
                    val fetched = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        YoutubeFetcher.fetchVideosFromChannel(apiKey)
                    }
                    if (fetched.isNotEmpty()) {
                        val currentVideos = repository.getVideos().first()
                        val existingIds = currentVideos.map { it.videoId }.toSet()
                        val newVideos = fetched.filter { it.videoId !in existingIds }
                        if (newVideos.isNotEmpty()) {
                            newVideos.forEach { video ->
                                repository.addVideo(video)
                            }
                            setHasNewNotifications(true)
                        }
                    }
                    prefs.edit().putLong("last_youtube_sync_timestamp", now).apply()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun syncYoutubeVideos(onComplete: (Int) -> Unit = {}) {
        viewModelScope.launch {
            _isSyncingVideos.value = true
            var addedCount = 0
            try {
                val apiKey = com.example.BuildConfig.YOUTUBE_API_KEY
                val fetched = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                    YoutubeFetcher.fetchVideosFromChannel(apiKey)
                }
                if (fetched.isNotEmpty()) {
                    val currentVideos = repository.getVideos().first()
                    val existingIds = currentVideos.map { it.videoId }.toSet()
                    val newVideos = fetched.filter { it.videoId !in existingIds }
                    if (newVideos.isNotEmpty()) {
                        newVideos.forEach { video ->
                            repository.addVideo(video)
                        }
                        addedCount = newVideos.size
                        setHasNewNotifications(true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isSyncingVideos.value = false
                onComplete(addedCount)
            }
        }
    }

    val favoriteMantras: StateFlow<List<com.example.data.FavoriteMantra>> = repository.getFavoriteMantras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- INSTAGRAM CRUD ---
    fun publishInstagramPost(url: String, caption: String) {
        viewModelScope.launch {
            val post = InstagramPost(url = url, caption = caption)
            repository.addInstagramPost(post)
            setHasNewNotifications(true)
        }
    }

    fun updateInstagramPost(post: InstagramPost) {
        viewModelScope.launch {
            repository.updateInstagramPost(post)
        }
    }

    fun deleteInstagramPost(id: Int) {
        viewModelScope.launch {
            repository.deleteInstagramPostById(id)
        }
    }

    // --- THOUGHT CRUD ---
    fun addThoughtQuote(textEn: String, textHi: String) {
        viewModelScope.launch {
            val quote = ThoughtQuote(textEn = textEn, textHi = textHi)
            repository.addThoughtQuote(quote)
        }
    }

    fun updateThoughtQuote(quote: ThoughtQuote) {
        viewModelScope.launch {
            repository.updateThoughtQuote(quote)
        }
    }

    fun deleteThoughtQuote(id: Int) {
        viewModelScope.launch {
            repository.deleteThoughtQuoteById(id)
        }
    }

    // --- ANNOUNCEMENT CRUD ---
    fun addAnnouncement(textEn: String, textHi: String, isVisible: Boolean = true) {
        viewModelScope.launch {
            val ann = Announcement(textEn = textEn, textHi = textHi, isVisible = isVisible)
            repository.addAnnouncement(ann)
        }
    }

    fun updateAnnouncement(announcement: Announcement) {
        viewModelScope.launch {
            repository.updateAnnouncement(announcement)
        }
    }

    fun deleteAnnouncement(id: Int) {
        viewModelScope.launch {
            repository.deleteAnnouncementById(id)
        }
    }

    // --- ARTICLE CRUD EXTRA ---
    fun updateKnowledgeArticle(article: KnowledgeArticle) {
        viewModelScope.launch {
            repository.updateArticle(article)
        }
    }

    fun deleteKnowledgeArticle(id: Int) {
        viewModelScope.launch {
            repository.deleteArticle(id)
        }
    }

    // --- VIDEO CRUD EXTRA ---
    fun updateBhaktiVideo(video: BhaktiVideo) {
        viewModelScope.launch {
            repository.updateVideo(video)
        }
    }

    fun deleteBhaktiVideo(id: Int) {
        viewModelScope.launch {
            repository.deleteVideo(id)
        }
    }

    // --- INSTAGRAM CRUD ---
    fun addInstagramPost(url: String, caption: String, category: String) {
        viewModelScope.launch {
            repository.addInstagramPost(InstagramPost(url = url, caption = caption, category = category))
        }
    }

    // --- USER MANAGEMENT PERMISSIONS ---
    fun updateUserCanPost(email: String, canPost: Boolean) {
        viewModelScope.launch {
            repository.updateUserCanPost(email, canPost)
        }
    }

    fun updateUserRole(email: String, role: String) {
        viewModelScope.launch {
            repository.updateUserRole(email, role)
        }
    }

    // --- COMMENT DELETION EXTRA ---
    fun deletePost(id: Int) {
        viewModelScope.launch {
            repository.deletePost(id)
        }
    }


    fun deletePostComment(id: Int) {
        viewModelScope.launch {
            repository.deleteComment(id)
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
        _isEnglish as Flow<Any>,
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
        repository.getArticles(lang).map { list ->
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Videos (Reactively updates when video category changes)
    val videos: StateFlow<List<BhaktiVideo>> = repository.getVideos()
        .combine(_selectedVideoCategory) { list, cat ->
            if (cat == "All") list else list.filter { it.category.equals(cat, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Curated dynamic playlist based on current day of the week or active festival
    val curatedPlaylistVideos: StateFlow<List<BhaktiVideo>> = combine(
        repository.getVideos(),
        currentFestival
    ) { allVideos, festival ->
        val cal = java.util.Calendar.getInstance()
        val dow = cal.get(java.util.Calendar.DAY_OF_WEEK) // 1=Sunday, 2=Monday, ..., 7=Saturday
        
        // Day of Week Keywords
        val dayKeywords = when (dow) {
            1 -> listOf("surya", "ram", "rama", "sun", "aarati", "aarti", "रविवार", "राम", "सूर्य")
            2 -> listOf("shiva", "shiv", "mahadev", "bholenath", "somwar", "somvar", "shankar", "सोमवार", "शिव", "महादेव", "भोलेनाथ")
            3 -> listOf("hanuman", "ganesh", "ganpati", "mangal", "mangalwar", "mangalvar", "हनुमान", "गणेश", "गणपति", "मंगलवार")
            4 -> listOf("krishna", "ganesh", "ganpati", "budh", "budhwar", "budhvar", "बुधवार", "कृष्ण", "गणेश", "गणपति")
            5 -> listOf("vishnu", "guru", "hari", "guruwar", "guruvar", "narayan", "विष्णु", "गुरु", "गुरुवार", "नारायण")
            6 -> listOf("devi", "durga", "lakshmi", "laxmi", "shukra", "shukrawar", "shukravar", "शुक्रवार", "दुर्गा", "लक्ष्मी", "देवी")
            7 -> listOf("hanuman", "shani", "shaniwar", "shanivar", "हनुमान", "शनि")
            else -> emptyList()
        }

        // Active festival keywords
        val festKeywords = if (festival.isNotEmpty()) {
            festival.lowercase().split(" ", "-", "_")
        } else {
            emptyList()
        }

        // Filter and Score videos
        val scoredVideos = allVideos.map { video ->
            val titleLower = video.title.lowercase()
            val descLower = video.description.lowercase()
            var score = 0
            
            // Score for day of the week keywords
            dayKeywords.forEach { kw ->
                if (titleLower.contains(kw)) score += 5
                if (descLower.contains(kw)) score += 2
            }
            
            // Score for festival keywords (high priority)
            festKeywords.forEach { kw ->
                if (kw.length > 2) {
                    if (titleLower.contains(kw)) score += 15
                    if (descLower.contains(kw)) score += 5
                }
            }
            
            Pair(video, score)
        }

        // Return matched videos sorted by relevance, or fallback to first 6 videos
        val matched = scoredVideos.filter { it.second > 0 }.sortedByDescending { it.second }.map { it.first }
        if (matched.isNotEmpty()) {
            matched
        } else {
            allVideos.take(6)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getCuratedPlaylistTitle(isEng: Boolean): String {
        val cal = java.util.Calendar.getInstance()
        val dow = cal.get(java.util.Calendar.DAY_OF_WEEK)
        val festival = currentFestival.value
        if (festival.isNotEmpty()) {
            return if (isEng) "Festival Special: $festival Devotion" else "उत्सव विशेष: $festival आराधना"
        }
        return when (dow) {
            1 -> if (isEng) "Sunday Special: Surya Dev & Lord Rama" else "रविवार विशेष: सूर्य देव व श्री राम आराधना"
            2 -> if (isEng) "Monday Special: Lord Shiva Bhakti" else "सोमवार विशेष: शिव भक्ति व आराधना"
            3 -> if (isEng) "Tuesday Special: Lord Hanuman & Ganesha" else "मंगलवार विशेष: हनुमान जी व श्री गणेश वंदना"
            4 -> if (isEng) "Wednesday Special: Lord Ganesha & Krishna" else "बुधवार विशेष: श्री गणेश व कृष्ण आराधना"
            5 -> if (isEng) "Thursday Special: Lord Vishnu & Guru Dev" else "गुरुवार विशेष: श्री हरि विष्णु व गुरु आराधना"
            6 -> if (isEng) "Friday Special: Goddess Durga & Lakshmi" else "शुक्रवार विशेष: माँ दुर्गा व महालक्ष्मी उपासना"
            7 -> if (isEng) "Saturday Special: Lord Shani Dev & Hanuman" else "शनिवार विशेष: शनि देव व संकटमोचन हनुमान"
            else -> if (isEng) "Daily Devotional Curated" else "दैनिक भक्ति आराधना"
        }
    }

    // Knowledge Hub Search
    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 5) current.removeAt(current.size - 1)
        _recentSearches.value = current
    }

    // 3. Posts (Reactively updates when sorting changes)
    val posts: StateFlow<List<CommunityPost>> = repository.getPosts()
        .combine(_communitySortBy) { list, sort ->
            val sortedList = when (sort) {
                "Top" -> list.sortedByDescending { it.upvotes - it.downvotes }
                "Discussed" -> list.sortedByDescending { it.commentsCount }
                else -> list.sortedByDescending { it.createdAt }
            }
            sortedList.sortedWith(compareByDescending { it.isPinned })
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allComments: StateFlow<List<PostComment>> = repository.getAllComments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 4. Comments for active post
    val activeComments: StateFlow<List<PostComment>> = _selectedPostId.flatMapLatest { id ->
        repository.getComments(id ?: -1)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 5. Karma logs
    val karmaLogs: StateFlow<List<KarmaLog>> = repository.getKarmaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sankalpaLogs: StateFlow<List<com.example.data.SankalpaLog>> = repository.getSankalpaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    fun addSankalpaLog(intention: String, progressNote: String, progressValue: Int, date: String) {
        viewModelScope.launch {
            repository.addSankalpaLog(com.example.data.SankalpaLog(intention = intention, progressNote = progressNote, progressValue = progressValue, date = date))
        }
    }

    val gratitudeLogs: StateFlow<List<GratitudeLog>> = repository.getGratitudeLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val karmaPoints: StateFlow<Int> = combine(karmaLogs, gratitudeLogs) { k, g ->
        (k.size * 10) + (g.size * 5)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dailyStreak: StateFlow<Int> = karmaLogs.map { if (it.isNotEmpty()) it.first().streak else 0 }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // 6. Gratitude logs

    // 7. Chatbot messages
    val chatMessages: StateFlow<List<ChatMessage>> = repository.getChatMessages()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _guestQuestionCount = MutableStateFlow(0)
    val guestQuestionCount: StateFlow<Int> = _guestQuestionCount.asStateFlow()

    // 8. Favorite Mantras
    // 9. Designated Super User
    val designatedSuperUser: StateFlow<String> = repository.designatedSuperUser

    private val _guestActionCount = MutableStateFlow(0)
    val guestActionCount = _guestActionCount.asStateFlow()

    private val _showLoginWall = MutableStateFlow(false)
    val showLoginWall = _showLoginWall.asStateFlow()

    private val _bhaktiTab = MutableStateFlow("Mantras")
    val bhaktiTab = _bhaktiTab.asStateFlow()

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

    fun setScreen(screen: String) {
        if (screen == "Bhakti") {
            _bhaktiTab.value = "Mantras"
        } else if (screen == "BhaktiTools") {
            _bhaktiTab.value = "Counter"
        }
        _currentScreen.value = screen
    }

    fun navigateToBhakti(tab: String) {
        _bhaktiTab.value = tab
        if (tab == "Mantras" || tab == "My Mantras" || tab == "Timer") {
            _currentScreen.value = "Bhakti"
        } else {
            _currentScreen.value = "BhaktiTools"
        }
    }


    fun toggleLanguage() {
        _isEnglish.value = !_isEnglish.value
    }

    fun setThemePreference(pref: Int) {
        _themePreference.value = pref
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
            repository.addPost(newPost)
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
            repository.addComment(newComment)
            
            // Increment local comment count inside the post
            // We can fetch and update the post locally
            val currentPosts = posts.value
            val targetPost = currentPosts.find { it.id == postId }
            if (targetPost != null) {
                repository.updatePost(targetPost.copy(commentsCount = targetPost.commentsCount + 1))
            }
            statsCommentsCount.value += 1
        }
    }

    fun addBhaktiVideo(title: String, description: String, category: String, videoId: String) {
        viewModelScope.launch {
            val newVideo = BhaktiVideo(
                title = title,
                description = description,
                category = category,
                videoId = videoId,
                isCustom = true
            )
            repository.addVideo(newVideo)
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
            repository.addArticle(newArticle)
        }
    }






    fun setPublicPosting(enabled: Boolean) {
        viewModelScope.launch {
            repository.setPublicPostingEnabled(enabled)
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
            repository.addKarmaLog(log)
        }
    }

    fun logGratitude(content: String) {
        viewModelScope.launch {
            val log = GratitudeLog(
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                content = content
            )
            repository.addGratitudeLog(log)
        }
    }

    // --- GEMINI CHAT ACTION ---

    fun sendChatPrompt(prompt: String) {
        if (prompt.isBlank()) return
        if (!registerGuestAction()) return
        viewModelScope.launch {
            // 1. Add user message
            val userMsg = ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            _isChatLoading.value = true

            // 2. Get history to pass to Gemini
            val currentHistory = chatMessages.value

            // 3. Request Gemini response
            val reply = GeminiService.generateResponse(currentHistory, prompt)

            // 4. Add model reply
            val modelMsg = ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }

    fun resetChat() {
        viewModelScope.launch {
            repository.clearChat()
        }
    }

    // --- PANCHANG & DAILY THOUGHT ACTIONS ---

    private var lastPanchangFetchDate = ""



    fun loadDailyPanchangAndThought() {
        viewModelScope.launch {
            val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val dateStr = sdf.format(java.util.Date())
            
            val prefs = getApplication<android.app.Application>()
                .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)

            val customThoughtEn = prefs.getString("custom_thought_en", "") ?: ""
            val customThoughtHi = prefs.getString("custom_thought_hi", "") ?: ""

            if (customThoughtEn.isNotEmpty() && customThoughtHi.isNotEmpty()) {
                _thoughtEn.value = customThoughtEn
                _thoughtHi.value = customThoughtHi
            } else {
                val dailyShloka = com.example.data.GeminiService.fetchDailyShloka()
                if (dailyShloka != null) {
                    _thoughtEn.value = dailyShloka
                    _thoughtHi.value = dailyShloka
                } else {
                    val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt()
                    val thoughtsEnList = listOf(
                        "Perform action without attachment to outcomes.",
                        "You have the right to work, but never to the fruit of work.",
                        "When meditation is mastered, the mind is unwavering like the flame of a lamp in a windless place.",
                        "There is nothing lost or wasted in this life.",
                        "A person is shaped by their belief. As they believe, so they become."
                    )
                    val thoughtsHiList = listOf(
                        "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन। (परिणामों की आसक्ति के बिना कर्म करो।)",
                        "तुम्हारा अधिकार कर्म पर है, उसके फलों पर नहीं।",
                        "जिस प्रकार वायु रहित स्थान में दीपक की लौ नहीं हिलती, उसी प्रकार ध्यान में योगी का चित्त स्थिर रहता है।",
                        "इस जीवन में कुछ भी खोता या व्यर्थ नहीं होता।",
                        "मनुष्य अपने विश्वास से निर्मित होता है। जैसा वह विश्वास करता है, वैसा ही वह बन जाता है।"
                    )
                    _thoughtEn.value = thoughtsEnList[dayIndex % thoughtsEnList.size]
                    _thoughtHi.value = thoughtsHiList[dayIndex % thoughtsHiList.size]
                }
            }

            // Fetch Real Panchang from Free Astrology API
            panchangError.value = null
            
            val cal = java.util.Calendar.getInstance()
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val m = cal.get(java.util.Calendar.MONTH) // 0-Indexed
            val y = cal.get(java.util.Calendar.YEAR)
            val hh = cal.get(java.util.Calendar.HOUR_OF_DAY)
            val mm = cal.get(java.util.Calendar.MINUTE)
            val ss = cal.get(java.util.Calendar.SECOND)

            try {
                val dateStr = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, hh, mm, ss)
                if (realPanchang.data != null) {
                    todayDetailedPanchang.value = realPanchang.data
                    // Save to cache/repository as a history log, but never use it to skip API calls
                    repository.savePanchang(com.example.data.PanchangEntity(
                        date = dateStr,
                        tithi = realPanchang.data.tithi,
                        paksha = realPanchang.data.paksha,
                        nakshatra = realPanchang.data.nakshatra,
                        yoga = realPanchang.data.yoga,
                        karana = realPanchang.data.karana,
                        sunrise = realPanchang.data.sunrise,
                        sunset = realPanchang.data.sunset,
                        rahuKaal = realPanchang.data.rahukaal,
                        abhijeet = realPanchang.data.abhijeet,
                        festival = realPanchang.data.festival,
                        details = realPanchang.data.details
                    ))
                    updatePanchangWithEntity(com.example.data.PanchangEntity(
                        date = dateStr,
                        tithi = realPanchang.data.tithi,
                        paksha = realPanchang.data.paksha,
                        nakshatra = realPanchang.data.nakshatra,
                        yoga = realPanchang.data.yoga,
                        karana = realPanchang.data.karana,
                        sunrise = realPanchang.data.sunrise,
                        sunset = realPanchang.data.sunset,
                        rahuKaal = realPanchang.data.rahukaal,
                        abhijeet = realPanchang.data.abhijeet,
                        festival = realPanchang.data.festival,
                        details = realPanchang.data.details
                    ))
                    panchangSource.value = "API"
                    panchangLastUpdated.value = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault()).format(java.util.Date())
                } else {
                    panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                    panchangTechnicalError.value = "Error: ${realPanchang.error}\nURL: ${realPanchang.url}\nStatus Code: ${realPanchang.statusCode}\nRequest Body: ${realPanchang.requestBody}\nRaw Response: ${realPanchang.rawResponseBody}\nToday: ${java.util.Date().toString()}"
                }
            } catch (e: Exception) {
                panchangError.value = "Unable to load today's Panchang. Please check your connection or try again shortly."
                panchangTechnicalError.value = "Exception: ${e.message}\nToday: ${java.util.Date().toString()}"
            }
        }
    }

    val todayDetailedPanchangForDetail = MutableStateFlow<DetailedPanchang?>(null)

    fun loadDetailPanchang() {
        viewModelScope.launch {
            val cal = java.util.Calendar.getInstance()
            val d = cal.get(java.util.Calendar.DAY_OF_MONTH)
            val m = cal.get(java.util.Calendar.MONTH) // 0-Indexed
            val y = cal.get(java.util.Calendar.YEAR)
            val hh = cal.get(java.util.Calendar.HOUR_OF_DAY)
            val mm = cal.get(java.util.Calendar.MINUTE)
            val ss = cal.get(java.util.Calendar.SECOND)
            try {
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(d, m + 1, y, hh, mm, ss)
                if (realPanchang.data != null) {
                    todayDetailedPanchangForDetail.value = realPanchang.data
                }
            } catch (e: Exception) {
                android.util.Log.e("Panchang", "Failed to fetch fresh detail modal panchang", e)
            }
        }
    }

    fun getDetailedPanchangForDate(day: Int, month: Int, year: Int, isEng: Boolean, callback: (DetailedPanchang?) -> Unit) {
        viewModelScope.launch {
            // Build fresh POST body with year/month/date and hours=6, minutes=0, seconds=0
            val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(day, month + 1, year, 6, 0, 0)
            callback(realPanchang.data)
        }
    }

    fun updateCustomThought(en: String, hi: String) {
        val prefs = getApplication<android.app.Application>()
            .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putString("custom_thought_en", en)
            .putString("custom_thought_hi", hi)
            .apply()
        if (en.isNotEmpty()) _thoughtEn.value = en
        if (hi.isNotEmpty()) _thoughtHi.value = hi
        if (en.isEmpty() && hi.isEmpty()) {
            loadDailyPanchangAndThought()
        }
    }

    fun updateCustomPanchang(tEn: String, tHi: String, nEn: String, nHi: String, yEn: String, yHi: String, fEn: String, fHi: String) {
        val prefs = getApplication<Application>()
            .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putString("tithi_en", tEn)
            .putString("tithi_hi", tHi)
            .putString("nakshatra_en", nEn)
            .putString("nakshatra_hi", nHi)
            .putString("yoga_en", yEn)
            .putString("yoga_hi", yHi)
            .putString("festival_en", fEn)
            .putString("festival_hi", fHi)
            .apply()

        _tithiEn.value = tEn
        _tithiHi.value = tHi
        _nakshatraEn.value = nEn
        _nakshatraHi.value = nHi
        _yogaEn.value = yEn
        _yogaHi.value = yHi
        _festivalEn.value = fEn
        _festivalHi.value = fHi
    }

    // --- AUTH ACTIONS ---

    fun signupAndLogin(user: User, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val emailClean = user.email.lowercase().trim()
            val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
            val finalUser = user.copy(
                email = emailClean,
                role = if (isSuper) "SuperUser" else "Regular",
                avatar = if (isSuper) 4 else 1
            )
            val success = repository.registerUser(finalUser)
            if (success) {
                repository.loginAsUser(finalUser)
                _showLanguagePrefDialog.value = true
                onResult(true, "Successfully Registered and Logged In!")
            } else {
                onResult(false, "Sadhak Email is already registered.")
            }
        }
    }

    fun loginCredentialBased(emailStr: String, passwordStr: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val emailClean = emailStr.lowercase().trim()
            
            // Check for direct SuperUser default credential creation if they register or login
            val user = repository.authenticateUser(emailClean, passwordStr)
            if (user != null) {
                repository.loginAsUser(user)
                _showLanguagePrefDialog.value = true
                onResult(true, "Pranam! Logged in successfully.")
            } else {
                // If it's kunalpareekusa@gmail.com or ashishpareekbhl@gmail.com, and doesn't exist yet, we can create them dynamically to allow seamless login!
                val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
                if (isSuper) {
                    val defaultSuper = User(
                        email = emailClean,
                        fullName = if (emailClean.contains("kunal")) "Kunal Pareek" else "Ashish Pareek",
                        mobileNumber = "9999999999",
                        city = "Ajmer",
                        state = "Rajasthan",
                        password = passwordStr, // automatically create on first login attempt to make experience seamless
                        avatar = 4,
                        role = "SuperUser"
                    )
                    repository.registerUser(defaultSuper)
                    repository.loginAsUser(defaultSuper)
                    _showLanguagePrefDialog.value = true
                    onResult(true, "Pranam! Super User Account Initialized and Logged In.")
                } else {
                    onResult(false, "Incorrect Sadhak Email or Password.")
                }
            }
        }
    }

    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isNotEmpty()) {
                repository.updateUserProfile(email, fullName, mobileNumber, city, state, avatar, imageUri)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

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
        repository.logout()
    }

    fun setDesignatedSuperUser(email: String) {
        viewModelScope.launch {
            repository.setDesignatedSuperUser(email)
        }
    }



    fun addFavoriteMantra(title: String, content: String, youtubeId: String) {
        if (!requireLoginAction()) return
        viewModelScope.launch {
            val newMantra = com.example.data.FavoriteMantra(
                title = title,
                content = content,
                youtubeId = youtubeId
            )
            repository.addFavoriteMantra(newMantra)
        }
    }

    fun deleteFavoriteMantra(id: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteMantra(id)
        }
    }

    val isLoading = MutableStateFlow(false)






    fun signUp(email: String, pass: String, name: String, city: String, state: String, mobile: String = "", onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
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
            val success = repository.registerUser(newUser)
            isLoading.value = false
            if (success) {
                repository.loginAsUser(newUser)
                repository.setRememberedUser(newUser.email)
                _showLanguagePrefDialog.value = true
                onResult(true, "Successfully Registered and Logged In!")
            } else {
                onResult(false, "Sadhak Email is already registered.")
            }
        }
    }

    fun login(email: String, pass: String, rememberMe: Boolean = false, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            val emailClean = email.lowercase().trim()
            
            val user = repository.authenticateUser(emailClean, pass)
            isLoading.value = false
            if (user != null) {
                val isSuper = user.email.lowercase().trim() == "kunalpareekusa@gmail.com" || user.email.lowercase().trim() == "ashishpareekbhl@gmail.com"
                val finalUser = user.copy(
                    role = if (isSuper) "SuperUser" else "Regular",
                    avatar = if (isSuper) 4 else user.avatar
                )
                repository.loginAsUser(finalUser)
                if (rememberMe) {
                    repository.setRememberedUser(finalUser.email)
                } else {
                    repository.clearRememberedUser()
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
                    repository.registerUser(defaultSuper)
                    repository.loginAsUser(defaultSuper)
                    if (rememberMe) {
                        repository.setRememberedUser(defaultSuper.email)
                    } else {
                        repository.clearRememberedUser()
                    }
                    _showLanguagePrefDialog.value = true
                    onResult(true, "Pranam! Super User Account Initialized.")
                } else {
                    onResult(false, "Incorrect Sadhak Email/Mobile or Password.")
                }
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
            repository.updatePost(updatedPost)
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
                newUpvotes--
            }
        }
        
        val updatedPost = post.copy(
            upvotes = newUpvotes,
            downvotes = newDownvotes,
            upvotedByEmails = upvotedList.joinToString(","),
            downvotedByEmails = downvotedList.joinToString(",")
        )
        viewModelScope.launch {
            repository.updatePost(updatedPost)
        }
    }

    fun togglePinPost(post: com.example.data.CommunityPost) {
        if (isAdmin.value) {
            val updatedPost = post.copy(isPinned = !post.isPinned)
            viewModelScope.launch {
                repository.updatePost(updatedPost)
            }
        }
    }

    fun deletePost(post: com.example.data.CommunityPost) {
        if (isAdmin.value || post.author == userName.value) {
            viewModelScope.launch {
                repository.deletePost(post.id)
            }
        }
    }

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            if (userRole.value == "Guest") {
                if (_guestQuestionCount.value >= 10) {
                    _showLoginWall.value = true
                    return@launch
                }
                _guestQuestionCount.value += 1
            }
            _isChatLoading.value = true
            val userMsg = com.example.data.ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            
            val reply = getMockChatResponse(prompt)
            
            val modelMsg = com.example.data.ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }

    private fun getMockChatResponse(prompt: String): String {
        val p = prompt.lowercase()
        return when {
            p.contains("panchang") -> {
                "Today's Panchang highlights an auspicious Tithi and Nakshatra, creating a favorable energy for starting new spiritual practices. The planetary alignments suggest dedicating time to meditation and inner reflection.\n\nTry this today: Light a diya during Sandhya time and chant the Gayatri Mantra.\n[CHIP:View Daily Panchang:Home]\n[FOLLOWUP:How do I start a daily puja?]\n[FOLLOWUP:Tell me about Mahadev]"
            }
            p.contains("karma") -> {
                "Karma is the universal principle of cause and effect, where your actions, thoughts, and intentions shape your spiritual journey. It is not a system of punishment, but a path of learning and balancing. Nishkama Karma, performing your duties without attachment to the results, is considered the highest form of action.\n\nTry this today: Perform one act of kindness without expecting anything in return.\n[CHIP:Explore Karma & Dharma:Knowledge]\n[FOLLOWUP:Explain Dharma simply]\n[FOLLOWUP:What is Nishkama Karma?]"
            }
            p.contains("mahadev") || p.contains("shiva") -> {
                "Mahadev, the Supreme Being in Shaivism, represents the pure consciousness that transcends space and time. He is the destroyer of ignorance and ego, clearing the path for new creation and spiritual enlightenment. His ascetic form reminds us to find peace within, detached from material desires.\n\nTry this today: Offer a simple prayer of gratitude to Mahadev for the breath of life.\n[CHIP:Read about Mahadev:Knowledge]\n[CHIP:Shiva Aarti:Bhakti]\n[FOLLOWUP:What does the Trishul symbolize?]\n[FOLLOWUP:Tell me about Parvati Mata]"
            }
            p.contains("gita") || p.contains("shloka") -> {
                "As shared in the Bhagavad Gita, Chapter 2: 'You have a right to perform your prescribed duty, but you are not entitled to the fruits of action.' This teaching, known as Karma Yoga, encourages us to act with dedication while surrendering the outcome to the Divine.\n\nTry this today: When facing a task, focus entirely on doing it well, without worrying about success or failure.\n[CHIP:Read Bhagavad Gita:Knowledge]\n[FOLLOWUP:Explain Karma Yoga]\n[FOLLOWUP:What does Chapter 3 say?]"
            }
            p.contains("puja") -> {
                "Starting a daily puja is a beautiful way to center yourself. Begin by setting aside a small, clean space. Light a diya or incense, offer fresh flowers or water, and recite a simple mantra or prayer. Consistency and devotion are more important than complex rituals.\n\nTry this today: Spend 5 minutes in your quiet space simply breathing and feeling the Divine presence.\n[CHIP:Daily Rituals Guide:Knowledge]\n[CHIP:Explore Mantras:Bhakti]\n[FOLLOWUP:What is the best time for Puja?]\n[FOLLOWUP:How to use a Mala?]"
            }
            p.contains("sindari") || p.contains("balaji") -> {
                "Sindari ke Balaji is a revered form of Lord Hanuman, known for his immense strength, unwavering devotion to Lord Rama, and his power to dispel negative energies and grant boons to his devotees. Temples dedicated to Him often draw large crowds seeking blessings for courage and protection.\n\nTry this today: Chant 'Om Shri Hanumate Namah' to invoke His strength when you feel anxious.\n[CHIP:Read about Hanumanji:Knowledge]\n[CHIP:Hanuman Chalisa:Bhakti]\n[FOLLOWUP:Tell me a story about Hanuman]\n[FOLLOWUP:How to overcome fear?]"
            }
            else -> {
                "I appreciate your question. My purpose as Sadhak Mitra is to guide you on your spiritual path through the wisdom of Sanatan Dharma. While I cannot answer that directly, I can share teachings on inner peace, devotion, or understanding life's challenges through a dharmic lens. How can I assist your spiritual journey today?\n[FOLLOWUP:Explain Karma simply]\n[FOLLOWUP:Tell me about Mahadev]"
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
            repository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
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
            repository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun pinPost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }


    fun deleteComment(id: Int) {
        viewModelScope.launch {
            repository.deleteComment(id)
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
            repository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
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
            repository.updateComment(comment.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    private fun updatePanchangWithEntity(panchang: com.example.data.PanchangEntity) {
        _tithiEn.value = panchang.tithi
        _tithiHi.value = panchang.tithi
        _nakshatraEn.value = panchang.nakshatra
        _nakshatraHi.value = panchang.nakshatra
        _yogaEn.value = panchang.yoga
        _yogaHi.value = panchang.yoga
        _karanaEn.value = panchang.karana
        _karanaHi.value = panchang.karana
        _sunrise.value = panchang.sunrise
        _sunset.value = panchang.sunset
        _rahukaal.value = panchang.rahuKaal
        _abhijeet.value = panchang.abhijeet
        _festivalEn.value = panchang.festival
        _festivalHi.value = panchang.festival
        _detailsEn.value = panchang.details
        _detailsHi.value = panchang.details
    }
}
