package com.example.data

import com.example.ui.audio.AudioTrackId
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DailyShlokaItem(
    val id: String,
    val category: String, // Bhagavad Gita, Upanishad, Veda, Ramayana, Bhakti Yoga, Karma Yoga, Jnana
    val verseSanskrit: String,
    val transliteration: String,
    val meaningHi: String,
    val meaningEn: String,
    val sourceScriptureEn: String,
    val sourceScriptureHi: String,
    val chapterVerse: String,
    val deityOrTheme: String,
    val practicalWisdomEn: String,
    val practicalWisdomHi: String
)

data class DailyMantraItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val sanskrit: String,
    val transliteration: String,
    val meaningHi: String,
    val meaningEn: String,
    val deityEn: String,
    val deityHi: String,
    val purposeEn: String,
    val purposeHi: String,
    val source: String,
    val audioTrack: AudioTrackId? = null
)

object DailyDharmaRepository {

    val SHLOKAS_LIBRARY: List<DailyShlokaItem> = listOf(
        DailyShlokaItem(
            id = "gita_2_47",
            category = "Bhagavad Gita • Karma Yoga",
            verseSanskrit = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
            transliteration = "karmaṇy-evādhikāras te mā phaleṣhu kadāchana | mā karma-phala-hetur bhūr mā te saṅgo 'stvakarmaṇi",
            meaningHi = "तुम्हारा अधिकार केवल कर्म करने में ही है, उसके फलों में कभी नहीं। अतः फल की वासना से रहित होकर कर्म करो और कर्म न करने में भी आसक्ति न हो।",
            meaningEn = "You have a right to perform your prescribed duty, but you are not entitled to the fruits of action. Never consider yourself the cause of results, nor be attached to inaction.",
            sourceScriptureEn = "Srimad Bhagavad Gita",
            sourceScriptureHi = "श्रीमद्भगवद्गीता",
            chapterVerse = "2.47",
            deityOrTheme = "Shri Krishna • Nishkama Karma",
            practicalWisdomEn = "Perform every action with complete devotion and excellence, without mental anxiety about the outcome.",
            practicalWisdomHi = "परिणाम की चिंता त्यागकर अपने प्रत्येक कार्य को ईश्वरार्पण बुद्धि से श्रेष्ठतम रूप में करें।"
        ),
        DailyShlokaItem(
            id = "gita_4_7",
            category = "Bhagavad Gita • Dharma Sansthapana",
            verseSanskrit = "यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥",
            transliteration = "yadā yadā hi dharmasya glānir bhavati bhārata | abhyutthānam adharmasya tadātmānaṁ sṛijāmyaham",
            meaningHi = "हे भारत! जब-जब धर्म की हानि और अधर्म का उत्थान होता है, तब-तब मैं अपने रूप को रचता हूँ अर्थात साकार रूप से प्रकट होता हूँ।",
            meaningEn = "Whenever there is a decline in righteousness and an increase in unrighteousness, O Bharata, at that time I manifest Myself on earth.",
            sourceScriptureEn = "Srimad Bhagavad Gita",
            sourceScriptureHi = "श्रीमद्भगवद्गीता",
            chapterVerse = "4.7",
            deityOrTheme = "Shri Krishna • Divine Incarnation",
            practicalWisdomEn = "Trust in the ultimate triumph of Truth and Dharma. Live righteously in every sphere of life.",
            practicalWisdomHi = "सत्य और धर्म की अंतिम विजय पर अडिग विश्वास रखें और धर्मसम्मत जीवन जिएं।"
        ),
        DailyShlokaItem(
            id = "gita_9_22",
            category = "Bhagavad Gita • Ananya Bhakti",
            verseSanskrit = "अनन्याश्चिन्तयन्तो मां ये जनाः पर्युपासते।\nतेषां नित्याभियुक्तानां योगक्षेमं वहाम्यहम्॥",
            transliteration = "ananyāśh chintayanto māṁ ye janāḥ paryupāsate | teṣhāṁ nityābhiyuktānāṁ yoga-kṣhemaṁ vahāmyaham",
            meaningHi = "जो अनन्य भक्त केवल मेरा ही चिंतन करते हुए निष्काम भाव से मेरी उपासना करते हैं, उन नित्य युक्त भक्तों के योगक्षेम (अप्राप्त की प्राप्ति और प्राप्त की रक्षा) का वहन मैं स्वयं करता हूँ।",
            meaningEn = "To those who always remember Me with undivided devotion, meditating on My transcendental form, I supply what they lack and preserve what they have.",
            sourceScriptureEn = "Srimad Bhagavad Gita",
            sourceScriptureHi = "श्रीमद्भगवद्गीता",
            chapterVerse = "9.22",
            deityOrTheme = "Shri Krishna • Divine Protection",
            practicalWisdomEn = "Surrender your worries to the Divine. Total faith brings total protection and peace of mind.",
            practicalWisdomHi = "अपनी चिंताओं को प्रभु के चरणों में समर्पित करें। अनन्य श्रद्धा से आत्मिक शांति प्राप्त होती है।"
        ),
        DailyShlokaItem(
            id = "upanishad_isha_1",
            category = "Ishopanishad • Divine Omnipresence",
            verseSanskrit = "ईशा वास्यमिदँ सर्वं यत्किञ्च जगत्यां जगत्।\nतेन त्यक्तेन भुञ्जीथा मा गृधः कस्यस्विद्धनम्॥",
            transliteration = "īśā vāsyam idaṁ sarvaṁ yat kiñca jagatyāṁ jagat | tena tyaktena bhuñjīthā mā gṛdhaḥ kasya svid dhanam",
            meaningHi = "इस चराचर जगत में जो कुछ भी है, वह सब ईश्वर से व्याप्त है। इसलिए त्यागपूर्वक उसका उपभोग करो, किसी के धन का लोभ मत करो।",
            meaningEn = "Everything animate or inanimate in this universe is enveloped by the Divine. Therefore, accept only those things necessary for yourself, which are set aside as your quota, and do not covet others' wealth.",
            sourceScriptureEn = "Isha Upanishad",
            sourceScriptureHi = "ईशावास्योपनिषद्",
            chapterVerse = "Mantra 1",
            deityOrTheme = "Supreme Brahman • Non-Greed",
            practicalWisdomEn = "See the Divine in everything and practice contentment with what you have earned through righteous means.",
            practicalWisdomHi = "प्रत्येक वस्तु में ईश्वर का वास देखें और सादगी एवं संतोष का अभ्यास करें।"
        ),
        DailyShlokaItem(
            id = "ramayana_dharma",
            category = "Valmiki Ramayana • Truth & Dharma",
            verseSanskrit = "सत्यमेवेश्वरो लोके सत्यं पद्मा समाश्रिता।\nसत्यमूलानि सर्वाणि सत्यान्नास्ति परं पदम्॥",
            transliteration = "satyameveśvaro loke satyaṁ padmā samāśritā | satyamūlāni sarvāṇi satyānnāsti paraṁ padam",
            meaningHi = "सत्य ही इस संसार में ईश्वर है, सत्य में ही लक्ष्मी जी का वास है, सभी सद्गुणों का मूल सत्य है और सत्य से श्रेष्ठ कोई पद नहीं है।",
            meaningEn = "Truth alone is God in this world, prosperity abides in truth, all virtues are rooted in truth, and there is no state higher than truth.",
            sourceScriptureEn = "Valmiki Ramayana",
            sourceScriptureHi = "वाल्मीकि रामायण",
            chapterVerse = "Ayodhya Kanda 109.13",
            deityOrTheme = "Maryada Purushottam Shri Ram • Satya",
            practicalWisdomEn = "Anchor every word, thought, and deed in truth. Truth creates unbreakable moral power.",
            practicalWisdomHi = "अपने विचारों, वचनों और कर्मों में सत्यनिष्ठ रहें। सत्य ही मनुष्य का सबसे बड़ा संबल है।"
        ),
        DailyShlokaItem(
            id = "gita_6_5",
            category = "Bhagavad Gita • Self Upliftment",
            verseSanskrit = "उद्धरेदात्मनात्मानं नात्मानमवसादयेत्।\nआत्मैव ह्यात्मनो बन्धुरात्मैव रिपुरात्मनः॥",
            transliteration = "uddhared ātmanātmānaṁ nātmānam avasādayet | ātmaiva hyātmano bandhur ātmaiva ripur ātmanaḥ",
            meaningHi = "मनुष्य को चाहिए कि वह अपने मन के द्वारा अपना उद्धार करे, अपने को अधोगति में न डाले; क्योंकि यह मन ही आत्मा का मित्र है और मन ही आत्मा का शत्रु है।",
            meaningEn = "One must elevate oneself by one's own mind, not degrade oneself. The mind is the friend of the conditioned soul, and its enemy as well.",
            sourceScriptureEn = "Srimad Bhagavad Gita",
            sourceScriptureHi = "श्रीमद्भगवद्गीता",
            chapterVerse = "6.5",
            deityOrTheme = "Dhyana Yoga • Self Mastery",
            practicalWisdomEn = "Train your mind through daily Sadhana, noble thoughts, and self-discipline to become your greatest ally.",
            practicalWisdomHi = "नित्य साधना, शुभ विचार और आत्मसंयम द्वारा अपने मन को अपना श्रेष्ठ मित्र बनाएं।"
        ),
        DailyShlokaItem(
            id = "veda_shanti_mantra",
            category = "Vedas • Universal Peace",
            verseSanskrit = "ॐ द्यौः शान्तिरन्तरिक्षँ शान्तिः\nपृथिवी शान्तिरापः शान्तिरोषधयः शान्तिः।\nवनस्पतयः शान्तिर्विश्वेदेवाः शान्तिर्ब्रह्म शान्तिः\nसर्वँ शान्तिः शान्तिरेव शान्तिः सा मा शान्तिरेधि॥\nॐ शान्तिः शान्तिः शान्तिः॥",
            transliteration = "oṁ dyauḥ śāntir antarikṣaṁ śāntiḥ pṛthivī śāntir āpaḥ śāntir oṣadhayaḥ śāntiḥ | vanaspatayaḥ śāntir viśvedevāḥ śāntir brahma śāntiḥ sarvaṁ śāntiḥ śāntir eva śāntiḥ sā mā śāntir edhi",
            meaningHi = "द्युलोक शांत हो, अंतरिक्ष शांत हो, पृथ्वी शांत हो, जल शांत हो, औषधियां शांत हों, वनस्पतियां शांत हों, सभी देव शांत हों, ब्रह्म शांत हो, सब कुछ शांत हो। वह शांति मुझमें प्रविष्ट हो।",
            meaningEn = "May peace radiate there in the whole sky as well as in the vast ethereal space everywhere. May peace reign all over this earth, in water, in herbs, trees, and in all cosmic beings. May peace fill my soul.",
            sourceScriptureEn = "Yajurveda",
            sourceScriptureHi = "यजुर्वेद",
            chapterVerse = "36.17",
            deityOrTheme = "Universal Harmony • Shanti",
            practicalWisdomEn = "Cultivate harmony with nature and all living beings. Outer peace begins with inner serenity.",
            practicalWisdomHi = "प्रकृति और सभी प्राणियों के प्रति सद्भाव रखें। बाह्य शांति का आरंभ अंतर्मन की शांति से होता है।"
        )
    )

    val MANTRAS_LIBRARY: List<DailyMantraItem> = listOf(
        DailyMantraItem(
            id = "shiva_panchakshara",
            nameEn = "Shiva Panchakshara Mantra",
            nameHi = "शिव पंचाक्षर मंत्र",
            sanskrit = "ॐ नमः शिवाय",
            transliteration = "Om Namaḥ Śivāya",
            meaningHi = "मैं कल्याणकारी, मंगलमय भगवान शिव को नमन करता हूँ। यह पांच अक्षरों (न, म, शि, वा, य) से युक्त पंचमहाभूतों को शुद्ध करने वाला महामंत्र है।",
            meaningEn = "I bow to the auspicious Lord Shiva. This sacred five-syllable mantra purifies the five elements within and brings profound inner stillness.",
            deityEn = "Lord Shiva / Mahakaal",
            deityHi = "भगवान शिव / महाकाल",
            purposeEn = "Inner peace • Shiva Bhakti • Meditation",
            purposeHi = "आंतरिक शांति • शिव भक्ति • दिव्य ध्यान",
            source = "Yajurveda / Shri Rudram",
            audioTrack = AudioTrackId.OM_AMBIENT
        ),
        DailyMantraItem(
            id = "gayatri_mantra",
            nameEn = "Gayatri Maha Mantra",
            nameHi = "गायत्री महामंत्र",
            sanskrit = "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं\nभर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्॥",
            transliteration = "oṁ bhūr bhuvaḥ svaḥ tat savitur vareṇyaṁ bhargo devasya dhīmahi dhiyo yo naḥ pracodayāt",
            meaningHi = "हम उस प्राणस्वरूप, दुःखनाशक, सुखस्वरूप, श्रेष्ठ, तेजस्वी, पापनाशक, देवस्वरूप परमात्मा का ध्यान करते हैं, जो हमारी बुद्धि को सन्मार्ग पर प्रेरित करे।",
            meaningEn = "We meditate on the glorious splendor of the divine Sun, the creator of the universe. May that Supreme Light illuminate our intellect and guide us on the righteous path.",
            deityEn = "Maa Savitri & Surya Dev",
            deityHi = "माँ गायत्री एवं सूर्य देव",
            purposeEn = "Intellectual clarity • Spiritual illumination • Wisdom",
            purposeHi = "बुद्धि की प्रखरता • आत्मिक प्रकाश • दिव्य विवेक",
            source = "Rigveda 3.62.10",
            audioTrack = AudioTrackId.GAYATRI_MANTRA
        ),
        DailyMantraItem(
            id = "maha_mrityunjaya",
            nameEn = "Maha Mrityunjaya Mantra",
            nameHi = "महामृत्युंजय मंत्र",
            sanskrit = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥",
            transliteration = "oṁ tryambakaṁ yajāmahe sugandhiṁ puṣṭi-vardhanam | urvārukam iva bandhanān mṛtyor mukṣīya māmṛtāt",
            meaningHi = "हम त्रिनेत्रधारी, सुगंधित और पुष्टि का संवर्धन करने वाले भगवान शिव की वंदना करते हैं। जिस प्रकार पका हुआ खरबूजा बेल से स्वतः मुक्त हो जाता है, वैसे ही हम मृत्यु और जन्म के बंधनों से मुक्त होकर अमृतत्व को प्राप्त हों।",
            meaningEn = "We worship the Three-Eyed Lord Shiva, who is fragrant and nourishes all beings. As the ripe cucumber is released from the bondage of the vine, may He liberate us from death and fear for the sake of immortality.",
            deityEn = "Lord Shiva / Tryambakam",
            deityHi = "भगवान शिव / त्र्यंबक",
            purposeEn = "Health • Fearlessness • Liberation • Longevity",
            purposeHi = "आरोग्य • निर्भयता • मोक्ष • दीर्घायु",
            source = "Rigveda 7.59.12",
            audioTrack = AudioTrackId.MAHA_MRITYUNJAYA
        ),
        DailyMantraItem(
            id = "hare_krishna_mahamantra",
            nameEn = "Hare Krishna Maha Mantra",
            nameHi = "हरे कृष्ण महामंत्र",
            sanskrit = "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे।\nहरे राम हरे राम राम राम हरे हरे॥",
            transliteration = "hare kṛṣṇa hare kṛṣṇa kṛṣṇa kṛṣṇa hare hare | hare rāma hare rāma rāma rāma hare hare",
            meaningHi = "हे भगवान की आल्हादिनी शक्ति (हरा), हे सर्व-आकर्षक श्रीकृष्ण, हे परमानंद स्वरूप श्रीराम! कृपया मुझे अपनी दिव्य प्रेमाभक्ति में संलग्न करें।",
            meaningEn = "O Divine Energy of the Lord, O all-attractive Krishna, O Supreme Blissful Rama! Please engage my soul in Your loving devotional service.",
            deityEn = "Shri Krishna & Shri Ram",
            deityHi = "भगवान श्री कृष्ण एवं श्री राम",
            purposeEn = "Pure devotion • Joy • Destruction of Kali Yuga sins",
            purposeHi = "परम भक्ति • आत्मिक आनंद • कलिकल्मष नाश",
            source = "Kali Santarana Upanishad",
            audioTrack = AudioTrackId.FLUTE_AMBIENT
        ),
        DailyMantraItem(
            id = "ganesh_mool_mantra",
            nameEn = "Ganesh Mool Mantra",
            nameHi = "श्री गणेश मूल मंत्र",
            sanskrit = "ॐ गं गणपतये नमः",
            transliteration = "Om Gaṁ Gaṇapataye Namaḥ",
            meaningHi = "मैं सभी गणों के स्वामी, विघ्नहर्ता भगवान श्री गणेश को सादर प्रणाम करता हूँ।",
            meaningEn = "I bow to Lord Ganesha, the lord of all celestial retinues and the remover of all obstacles.",
            deityEn = "Lord Ganesha",
            deityHi = "भगवान श्री गणेश",
            purposeEn = "Removal of obstacles • Auspicious beginnings • Success",
            purposeHi = "विघ्नों का निवारण • शुभ कार्य का आरंभ • सफलता",
            source = "Ganapati Atharvashirsha",
            audioTrack = AudioTrackId.TEMPLE_BELL
        ),
        DailyMantraItem(
            id = "hanuman_beej_mantra",
            nameEn = "Hanuman Beej & Stuti Mantra",
            nameHi = "हनुमान बीज एवं स्तुति मंत्र",
            sanskrit = "ॐ हं हनुमते नमः।\nमनोजवं मारुततुल्यवेगं जितेन्द्रियं बुद्धिमतां वरिष्ठम्।\nवातात्मजं वानरयूथमुख्यं श्रीरामदूतं शरणं प्रपद्ये॥",
            transliteration = "oṁ haṁ hanumate namaḥ | manojavaṁ māruta-tulya-vegaṁ jitendriyaṁ buddhimatāṁ variṣṭham | vātātmajaṁ vānara-yūtha-mukhyaṁ śrī-rāma-dūtaṁ śaraṇaṁ prapadye",
            meaningHi = "मन के समान तीव्रगामी, वायु के समान वेगवान, इंद्रियों को जीतने वाले, बुद्धिमानों में श्रेष्ठ, पवनपुत्र, वानरसेना के प्रमुख और श्रीरामदूत श्री हनुमान की मैं शरण लेता हूँ।",
            meaningEn = "I take refuge in Shri Hanuman, the divine messenger of Shri Rama, who is swift as thought, fast as the wind, master of the senses, foremost among the wise, and leader of the monkey armies.",
            deityEn = "Lord Hanuman / Bajrangbali",
            deityHi = "श्री हनुमान / बजरंगबली",
            purposeEn = "Strength • Protection from fear • Courage",
            purposeHi = "आत्मबल • भय मुक्ति • साहस एवं सेवा",
            source = "Ramcharitmanas & Shrimad Valmiki Ramayana",
            audioTrack = AudioTrackId.HANUMAN_CHALISA
        ),
        DailyMantraItem(
            id = "narayana_ashtakshara",
            nameEn = "Narayana Ashtakshara Mantra",
            nameHi = "नारायण अष्टाक्षर मंत्र",
            sanskrit = "ॐ नमो नारायणाय",
            transliteration = "Om Namo Nārāyaṇāya",
            meaningHi = "मैं समस्त जीवों के आश्रयदाता, परमपिता भगवान नारायण (श्री हरि विष्णु) को नमस्कार करता हूँ।",
            meaningEn = "I bow to Lord Narayana, the Supreme Divine Sustainer who is the ultimate refuge of all souls.",
            deityEn = "Lord Vishnu / Narayana",
            deityHi = "भगवान श्री हरि विष्णु",
            purposeEn = "Peace • Sustenance • Spiritual refuge",
            purposeHi = "शांति • पोषण • मोक्ष एवं शरण",
            source = "Narayana Upanishad",
            audioTrack = AudioTrackId.VISHNU_SAHASRANAMAM
        )
    )

    fun getDailyShloka(dateStr: String = getCurrentDateKey()): DailyShlokaItem {
        val hash = Math.abs(dateStr.hashCode())
        return SHLOKAS_LIBRARY[hash % SHLOKAS_LIBRARY.size]
    }

    fun getDailyMantra(dateStr: String = getCurrentDateKey()): DailyMantraItem {
        val hash = Math.abs(dateStr.hashCode() + 108)
        return MANTRAS_LIBRARY[hash % MANTRAS_LIBRARY.size]
    }

    private fun getCurrentDateKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(Date())
    }
}
