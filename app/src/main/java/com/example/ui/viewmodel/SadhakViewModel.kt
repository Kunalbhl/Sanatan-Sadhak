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
import com.example.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    val descHi: String
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


class SadhakViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SadhakRepository(application)

    // Current Active Screen
    private val _currentScreen = MutableStateFlow("Home") // "Home", "Knowledge", "Community", "Bhakti", "Videos", "Chat", "Profile", "Admin"
    val currentScreen: StateFlow<String> = _currentScreen

    // Bilingual UI Toggle

    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }

    // Theme Preference (0 = System, 1 = Light, 2 = Dark)
    private val _themePreference = MutableStateFlow(1)
    val themePreference: StateFlow<Int> = _themePreference

    // Notifications toggle state
    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled

    fun toggleNotifications() {
        _notificationsEnabled.value = !_notificationsEnabled.value
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

    init {
        loadDailyPanchangAndThought()
    }

    // --- REVENUE STATS (Mocked) ---
    val statsPostsCount = MutableStateFlow(12)
    val statsLikesCount = MutableStateFlow(248)
    val statsCommentsCount = MutableStateFlow(54)

    // --- OBSERVABLE STATE FLOWS ---

    private val _knowledgeSortBy = MutableStateFlow("Newest") // "Newest", "A-Z"
    val knowledgeSortBy: StateFlow<String> = _knowledgeSortBy

    fun setKnowledgeSort(sort: String) {
        _knowledgeSortBy.value = sort
    }

    // 1. Articles (Reactively updates when language, category or sort changes)
    val articles: StateFlow<List<KnowledgeArticle>> = combine(_isEnglish, _selectedKnowledgeCategory, _knowledgeSortBy) { eng, cat, sort ->
        Triple(if (eng) "en" else "hi", cat, sort)
    }.flatMapLatest { (lang, cat, sort) ->
        repository.getArticles(lang).map { list ->
            val filtered = if (cat == "All") list else list.filter { it.category.equals(cat, ignoreCase = true) }
            when (sort) {
                "A-Z" -> filtered.sortedBy { it.title }
                else -> filtered.sortedByDescending { it.id } // Assuming higher id is newer
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 2. Videos (Reactively updates when video category changes)
    val videos: StateFlow<List<BhaktiVideo>> = repository.getVideos()
        .combine(_selectedVideoCategory) { list, cat ->
            if (cat == "All") list else list.filter { it.category.equals(cat, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
            when (sort) {
                "Top" -> list.sortedByDescending { it.upvotes - it.downvotes }
                "Discussed" -> list.sortedByDescending { it.commentsCount }
                else -> list.sortedByDescending { it.createdAt }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 4. Comments for active post
    val activeComments: StateFlow<List<PostComment>> = _selectedPostId.flatMapLatest { id ->
        repository.getComments(id ?: -1)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 5. Karma logs
    val karmaLogs: StateFlow<List<KarmaLog>> = repository.getKarmaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
    val favoriteMantras: StateFlow<List<com.example.data.FavoriteMantra>> = repository.getFavoriteMantras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
        if (tab == "Mantras" || tab == "Timer") {
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

    fun upvotePost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(upvotes = post.upvotes + 1))
            statsLikesCount.value += 1
        }
    }

    fun downvotePost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(downvotes = post.downvotes + 1))
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
            
            if (dateStr == lastPanchangFetchDate) {
                return@launch // Already fetched for today
            }

            val prefs = getApplication<android.app.Application>()
                .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)

            val customThoughtEn = prefs.getString("custom_thought_en", "") ?: ""
            val customThoughtHi = prefs.getString("custom_thought_hi", "") ?: ""

            if (customThoughtEn.isNotEmpty()) {
                _thoughtEn.value = customThoughtEn
            }
            if (customThoughtHi.isNotEmpty()) {
                _thoughtHi.value = customThoughtHi
            }

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
            
            if (customThoughtEn.isEmpty()) {
                _thoughtEn.value = thoughtsEnList[dayIndex % thoughtsEnList.size]
            }
            if (customThoughtHi.isEmpty()) {
                _thoughtHi.value = thoughtsHiList[dayIndex % thoughtsHiList.size]
            }

            // Fallback deterministic offline arrays
            val tithisEn = listOf("Pratipada", "Dwitiya", "Tritiya", "Chaturthi", "Panchami", "Shashthi", "Saptami", "Ashtami", "Navami", "Dashami", "Ekadashi", "Dwadashi", "Trayodashi", "Chaturdashi", "Purnima")
            val tithisHi = listOf("प्रतिपदा", "द्वितीया", "तृतीया", "चतुर्थी", "पंचमी", "षष्ठी", "सप्तमी", "अष्टमी", "नवमी", "दशमी", "एकादशी", "द्वादशी", "त्रयोदशी", "चतुर्दशी", "पूर्णिमा")
            val pakshasEn = listOf("Shukla Paksha", "Krishna Paksha")
            val pakshasHi = listOf("शुक्ल पक्ष", "कृष्ण पक्ष")
            val nakshatrasEn = listOf("Ashwini", "Bharani", "Krittika", "Rohini", "Mrigashira", "Ardra", "Punarvasu", "Pushya", "Ashlesha", "Magha", "Purva Phalguni", "Uttara Phalguni", "Hasta", "Chitra", "Swati", "Vishakha", "Anuradha", "Jyeshtha", "Mula", "Purva Ashadha", "Uttara Ashadha", "Shravana", "Dhanishta", "Shatabhisha", "Purva Bhadrapada", "Uttara Bhadrapada", "Revati")
            val nakshatrasHi = listOf("अश्विनी", "भरणी", "कृत्तिका", "रोहिणी", "मृगशिरा", "आर्द्रा", "पुनर्वसु", "पुष्य", "आश्लेषा", "मघा", "पूर्वाफाल्गुनी", "उत्तराफाल्गुनी", "हस्त", "चित्रा", "स्वाती", "विशाखा", "अनुराधा", "ज्येष्ठा", "मूल", "पूर्वाषाढ़ा", "उत्तराषाढ़ा", "श्रवण", "धनिष्ठा", "शतभिषा", "पूर्वाभाद्रपद", "उत्तराभाद्रपद", "रेवती")
            
            val tithiIndex = dayIndex % tithisEn.size
            val pakshaIndex = (dayIndex / tithisEn.size) % 2
            val nakshatraIndex = dayIndex % nakshatrasEn.size
            
            _tithiEn.value = "${tithisEn[tithiIndex]} (${pakshasEn[pakshaIndex]})"
            _tithiHi.value = "${tithisHi[tithiIndex]} (${pakshasHi[pakshaIndex]})"
            _nakshatraEn.value = nakshatrasEn[nakshatraIndex]
            _nakshatraHi.value = nakshatrasHi[nakshatraIndex]
            _yogaEn.value = "Shiva Yoga"
            _yogaHi.value = "शिव योग"
            
            if (tithiIndex == 10) {
                _festivalEn.value = "Ekadashi Vrat"
                _festivalHi.value = "एकादशी व्रत"
            } else if (tithiIndex == 14 && pakshaIndex == 0) {
                _festivalEn.value = "Purnima Puja"
                _festivalHi.value = "पूर्णिमा पूजा"
            } else {
                _festivalEn.value = ""
                _festivalHi.value = ""
            }
            lastPanchangFetchDate = dateStr
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



    fun addFavoriteMantra(title: String, content: String, youtubeUrl: String) {
        if (!requireLoginAction()) return
        viewModelScope.launch {
            val newMantra = com.example.data.FavoriteMantra(
                title = title,
                content = content,
                youtubeUrl = youtubeUrl
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






    fun signUp(email: String, pass: String, name: String, city: String, state: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            val emailClean = email.lowercase().trim()
            val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
            val newUser = com.example.data.User(
                email = emailClean,
                fullName = name,
                mobileNumber = "",
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

    
    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            if (userRole.value == "Guest") {
                if (_guestQuestionCount.value >= 10) {
                    _showGuestPromoDialog.value = true
                    return@launch
                }
                _guestQuestionCount.value += 1
            }
            _isChatLoading.value = true
            val userMsg = com.example.data.ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            
            val currentHistory = chatMessages.value
            val reply = com.example.data.GeminiService.generateResponse(currentHistory, prompt)
            
            val modelMsg = com.example.data.ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }

}