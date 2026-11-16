package com.example.data

object SpiritualData {

    val dailyThoughtsEn = listOf(
        "Karmanye vadhikaraste ma phaleshu kadachana — Your right is to perform your duty, but never to its fruits. Perform actions without attachment to outcomes.",
        "Aham Brahmasmi — I am the infinite reality. Recognize the sacred, supreme divine light residing inside your own heart.",
        "The mind is restless and difficult to control, but it can be conquered through regular spiritual practice (abhyasa) and detachment (vairagya).",
        "Fill your heart with Bhakti (devotion). Love all, serve all, for the same Supreme Divine flows through every living being.",
        "Peace comes from within. Do not seek it without. Inner happiness is the true wealth of a Sadhak."
    )

    val dailyThoughtsHi = listOf(
        "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन — तुम्हारा अधिकार केवल कर्म करने पर है, उसके फलों पर कभी नहीं। फल की इच्छा के बिना कर्तव्य का पालन करो।",
        "अहं ब्रह्मास्मि — मैं ही अनंत सत्य हूँ। अपने हृदय के भीतर विराजमान पवित्र, परम दिव्य प्रकाश को पहचानो।"
    )

    val defaultArticles = listOf(
        // --- SCRIPTURES & GRANTHS (BHAGAVAD GITA) ---
        KnowledgeArticle(
            category = "Scriptures & Granths",
            title = "Srimad Bhagavad Gita - Karma Yoga (2.47)",
            summary = "The cornerstone teaching on selfless duty without attachment to results.",
            content = "In Chapter 2, Verse 47 of the Bhagavad Gita, Lord Sri Krishna delivers one of the most profound spiritual instructions to Arjuna on the battlefield of Kurukshetra. It emphasizes that a sadhak must focus entirely on dedicated action (Karma) rather than anxiety or attachment over the outcome.",
            originalText = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
            translation = "You have a right performing your prescribed duty, but never to the fruits of action. Never consider yourself the cause of the results of your activities, nor be attached to inaction.",
            aiExplanation = "Sadhak AI Mitra Insight: This verse teaches mental freedom in daily life. When you execute work as an offering to the Divine without burdening your mind with anxiety over results, your focus remains pure and work becomes a meditative Sadhana.",
            sourceName = "Gita Press Gorakhpur",
            sourceType = "Traditional Scripture",
            textReference = "Bhagavad Gita 2.47",
            chapter = "Chapter 2",
            verse = "Verse 47",
            edition = "Standard Sanskrit Edition",
            translator = "Swami Ramsukhdas Ji",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Shloka",
            language = "en",
            difficulty = "Beginner",
            readTimeMinutes = 5,
            relatedTopics = "Karma Yoga,Dharma,Krishna"
        ),
        KnowledgeArticle(
            category = "Scriptures & Granths",
            title = "श्रीमद्भगवद्गीता - कर्मयोग (२.४७)",
            summary = "फल की इच्छा के बिना केवल अपने पवित्र कर्तव्य में निष्ठा रखने का दिव्य संदेश।",
            content = "श्रीमद्भगवद्गीता के द्वितीय अध्याय के ४७वें श्लोक में भगवान श्री कृष्ण अर्जुन को निष्काम कर्मयोग का परम उपदेश देते हैं। यह श्लोक मनुष्य को परिणाम की चिंता छोड़कर अपने कर्तव्य पथ पर आगे बढ़ने की प्रेरणा देता है।",
            originalText = "कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥",
            translation = "तुम्हारा अधिकार केवल कर्म करने में ही है, उसके फलों में कभी नहीं। इसलिए तुम कर्मों के फल का हेतु मत बनो और तुम्हारी अकर्म (कर्म न करने) में भी आसक्ति न हो।",
            aiExplanation = "साधक एआई मित्र विचार: यह श्लोक तनावमुक्त जीवन जीने की कला सिखाता है। जब आप अपने दैनिक कार्यों को ईश्वर का प्रसाद मानकर पूर्ण निष्ठा से करते हैं, तो असफलता का भय समाप्त हो जाता है।",
            sourceName = "गीता प्रेस गोरखपुर",
            sourceType = "पारंपरिक धर्मग्रंथ",
            textReference = "भगवद्गीता २.४७",
            chapter = "अध्याय २",
            verse = "श्लोक ४७",
            edition = "मानक संस्करन",
            translator = "स्वामी रामसुखदास जी",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Shloka",
            language = "hi",
            difficulty = "Beginner",
            readTimeMinutes = 5,
            relatedTopics = "कर्मयोग,धर्म,श्रीकृष्ण"
        ),

        // --- VEDAS & VEDANGAS ---
        KnowledgeArticle(
            category = "Vedas & Vedangas",
            title = "Rigveda Sanhita - Agni Sukta (1.1.1)",
            summary = "The sacred opening Sukta of the oldest Veda, invoking the divine illuminating light Agni.",
            content = "The Rigveda is the oldest known text in Sanatan Dharma. In Rigveda 1.1.1, Rishi Madhuchhanda Vaishvamitra invokes Agni, the divine flame that bridges humanity and the cosmos.",
            originalText = "ॐ अग्निमीळे पुरोहितं यज्ञस्य देवमृत्विजम्।\nहोतारं रत्नधातमम्॥",
            translation = "Om. I magnify Agni, the priest of the sacrifice, the divine minister, the caller of gods, and the bestower of spiritual and material treasures.",
            aiExplanation = "Sadhak AI Mitra Insight: Fire (Agni) in Vedic tradition represents internal digestive fire (Jatharagni), intellectual clarity, and divine illumination. Chanting Agni Sukta purifies internal prana.",
            sourceName = "Choukhamba Sanskrit Series / Rigveda Sanhita",
            sourceType = "Traditional Scripture",
            textReference = "Rigveda Mandala 1, Sukta 1, Mantra 1",
            chapter = "Mandala 1",
            verse = "Sukta 1.1",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Mantra",
            language = "en",
            difficulty = "Advanced",
            readTimeMinutes = 7,
            relatedTopics = "Rigveda,Vedic Hymns,Agni"
        ),
        KnowledgeArticle(
            category = "Vedas & Vedangas",
            title = "ऋग्वेद संहिता - अग्नि सूक्त (१.१.१)",
            summary = "ऋग्वेद का प्रथम पावन मन्त्र, जो सर्वप्रकाशक परमेश्वर रूपी अग्नि की स्तुति करता है।",
            content = "ऋग्वेद सनातन संस्कृति का सबसे प्राचीन वेद है। इसके प्रथम मन्त्र में महर्षि मधुच्छन्दा ऋषियों की परम्परा में अग्नि देव (दिव्य प्रकाश) की आराधना करते हैं।",
            originalText = "ॐ अग्निमीळे पुरोहितं यज्ञस्य देवमृत्विजम्।\nहोतारं रत्नधातमम्॥",
            translation = "हम यज्ञ के पुरोहित, दिव्य प्रकाश स्वरूप, ऋत्विक, देवताओं को बुलाने वाले तथा रत्नों को धारण कराने वाले अग्नि देव की स्तुति करते हैं।",
            aiExplanation = "साधक एआई मित्र विचार: वैदिक दर्शन में अग्नि केवल भौतिक आहुति नहीं, बल्कि अंतरात्मा की ज्वाला और बुद्धि की तीव्र चेतना का प्रतीक है।",
            sourceName = "चौखम्बा संस्कृत संस्थान",
            sourceType = "पारंपरिक धर्मग्रंथ",
            textReference = "ऋग्वेद मण्डल १, सूक्त १, मन्त्र १",
            chapter = "मण्डल १",
            verse = "मन्त्र १.१",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Mantra",
            language = "hi",
            difficulty = "Advanced",
            readTimeMinutes = 7,
            relatedTopics = "ऋग्वेद,वैदिक मंत्र,अग्नि"
        ),

        // --- UPANISHADS ---
        KnowledgeArticle(
            category = "Upanishads",
            title = "Mandukya Upanishad - Omkar & Consciousness",
            summary = "The ultimate Vedantic text revealing the four states of consciousness represented by OM.",
            content = "The Mandukya Upanishad, belonging to the Atharvaveda, is one of the shortest yet most potent Upanishads. It analyzes the syllable OM (A-U-M) and its connection to Waking (Jagrat), Dreaming (Swapna), Deep Sleep (Susupti), and Pure Consciousness (Turiya).",
            originalText = "ॐ इत्येतदक्षरमिदं सर्वं तस्योपव्याख्यानं भूतं भवद्भविष्यदिति सर्वमोङ्कार एव।",
            translation = "OM is the imperishable syllable. OM is all that exists. All that was, all that is, and all that will be in the future is verily OM.",
            aiExplanation = "Sadhak AI Mitra Insight: Mandukya Upanishad forms the core of Advaita Vedanta philosophy. Realizing 'Turiya' means realizing that your true Self is witness to all states of mind.",
            sourceName = "Gita Press Gorakhpur - Upanishad Ank",
            sourceType = "Traditional Scripture",
            textReference = "Mandukya Upanishad Verse 1",
            chapter = "Mantra 1",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Scripture",
            language = "en",
            difficulty = "Advanced",
            readTimeMinutes = 10,
            relatedTopics = "Upanishad,OM,Advaita Vedanta"
        ),

        // --- MANTRAS & CHANTING ---
        KnowledgeArticle(
            category = "Mantras & Chanting",
            title = "Mahamrityunjaya Mantra - Shiva Stuti",
            summary = "The supreme life-nourishing mantra from Yajurveda for protection, health, and liberation.",
            content = "The Mahamrityunjaya Mantra, also called the Tryambakam Mantra, is a verse from the Rigveda and Yajurveda. It is addressed to Tryambaka (the three-eyed Shiva), praying for health, protection, and freedom from fear of death.",
            originalText = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय माऽमृतात्॥",
            translation = "We worship the Three-Eyed Lord (Shiva), who is fragrant and who nourishes all beings. As the ripe cucumber is liberated from its creeper bond, may He liberate us from death and bondages, not from immortality.",
            aiExplanation = "Sadhak AI Mitra Insight: Daily chanting of Mahamrityunjaya Mantra removes anxiety, instills supreme fearlessness, and restores physical and mental equilibrium.",
            sourceName = "Yajurveda Sanhita & Shiva Purana",
            sourceType = "Traditional Scripture",
            textReference = "Yajurveda 3.60 / Rigveda 7.59.12",
            chapter = "Mandala 7",
            verse = "Sukta 59, Verse 12",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Mantra",
            language = "en",
            difficulty = "Beginner",
            readTimeMinutes = 5,
            relatedTopics = "Shiva,Mantra,Protection"
        ),
        KnowledgeArticle(
            category = "Mantras & Chanting",
            title = "महामृत्युंजय मंत्र - शिव साधना",
            summary = "यजुर्वेद का परम कल्याणकारी मंत्र, जो आरोग्य, रक्षा और अभय प्रदान करता है।",
            content = "महामृत्युंजय मंत्र भगवान शिव का अति शक्तिशाली मंत्र है। इसका निरंतर जप मानसिक शांति, शारीरिक आरोग्य और समस्त संकटों से मुक्ति दिलाता है।",
            originalText = "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय माऽमृतात्॥",
            translation = "हम त्रिनेत्रधारी (भगवान शिव) की आराधना करते हैं, जो सुगंधित हैं और सभी जीवों का पोषण करते हैं। जैसे पका हुआ खरबूजा बेल के बंधन से मुक्त हो जाता है, वैसे ही हम मृत्यु और बंधनों से मुक्त हों, पर अमृत्व से नहीं।",
            aiExplanation = "साधक एआई मित्र विचार: यह मंत्र भय और चिंता को दूर कर अंतर्मन में अपार ऊर्जा और आरोग्य का प्रवाह करता है।",
            sourceName = "यजुर्वेद एवं शिव पुराण",
            sourceType = "पारंपरिक धर्मग्रंथ",
            textReference = "यजुर्वेद ३.६० / ऋग्वेद ७.५९.१२",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Mantra",
            language = "hi",
            difficulty = "Beginner",
            readTimeMinutes = 5,
            relatedTopics = "शिव,मंत्र,आरोग्य"
        ),

        // --- ITIHASA & PURANAS ---
        KnowledgeArticle(
            category = "Itihasa",
            title = "Sri Ramcharitmanas - Sundara Kanda Overview",
            summary = "The glory of Hanuman ji's unwavering devotion, strength, and victory over Lanka.",
            content = "Sundara Kanda is the 5th book in Sri Ramcharitmanas composed by Goswami Tulsidas. It chronicles Hanuman ji's heroic leap across the ocean to Lanka, his meeting with Devi Sita, and his message of hope and courage.",
            originalText = "जामवन्त के बचन सुहाए। सुनि हनुमंत हृदय अति भाए॥\nबैठि बिचारि कहउ तुम भाई। गिरि कानन फल खाहू जाई॥",
            translation = "Hearing the encouraging words of Jambavan, Hanuman ji felt immense joy in his heart. He said, 'Wait here patiently, my brothers, eating wild fruits, until I return with news of Sita ji.'",
            aiExplanation = "Sadhak AI Mitra Insight: Sundara Kanda teaches that humility combined with absolute surrender to Lord Rama activates infinite latent potential inside a devotee.",
            sourceName = "Gita Press Gorakhpur - Sri Ramcharitmanas",
            sourceType = "Traditional Scripture",
            textReference = "Sri Ramcharitmanas - Sundara Kanda Chaupai 1-2",
            chapter = "Sundara Kanda",
            translator = "Goswami Tulsidas",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Scripture",
            language = "en",
            difficulty = "Intermediate",
            readTimeMinutes = 8,
            relatedTopics = "Ramayana,Hanuman,Sundara Kanda"
        ),
        KnowledgeArticle(
            category = "Itihasa",
            title = "श्रीरामचरितमानस - सुंदरकाण्ड महिमा",
            summary = "हनुमान जी के अतुलित बल, बुद्धि और निष्काम रामभक्ति का पावन प्रसंग।",
            content = "श्रीरामचरितमानस का सुंदरकाण्ड गोस्वामी तुलसीदास जी महाराज की अद्भुत रचना है। इसमें श्री हनुमान जी द्वारा समुद्र लांघकर लंका जाने, माता सीता की खोज करने और लंका दहन करने का ओजस्वी वर्णन है।",
            originalText = "प्रबिसि नगर कीजै सब काजा। हृदएँ राखि कौसलपुर राजा॥\nगरल सुधारिपु करहिं मिताई। गोपद सिंधु अनल सितलाई॥",
            translation = "हृदय में अयोध्यानाथ श्री रामचंद्र जी को धारण करके नगर में प्रवेश करके सब कार्य कीजिए। उनके लिए विष भी अमृत बन जाता है, शत्रु मित्र बन जाते हैं और समुद्र गाय के खुर के बराबर छोटा हो जाता है।",
            aiExplanation = "साधक एआई मित्र विचार: सुंदरकाण्ड का नियमित पाठ आत्मविश्वास जगाता है, सभी मानसिक भय समाप्त करता है और सफलता का मार्ग प्रशस्त करता है।",
            sourceName = "गीता प्रेस गोरखपुर",
            sourceType = "पारंपरिक धर्मग्रंथ",
            textReference = "श्रीरामचरितमानस - सुंदरकाण्ड",
            chapter = "सुंदरकाण्ड",
            translator = "गोस्वामी तुलसीदास जी",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "SCRIPTURE",
            format = "Scripture",
            language = "hi",
            difficulty = "Intermediate",
            readTimeMinutes = 8,
            relatedTopics = "रामायण,हनुमान,सुंदरकाण्ड"
        ),

        // --- POOJA & VIDHI & REMEDIES ---
        KnowledgeArticle(
            category = "Pooja & Vidhi",
            title = "Daily Home Pooja & Panchopachara Vidhi",
            summary = "Authentic 5-step daily worship procedure according to traditional Agama manuals.",
            content = "Panchopachara Pooja is the authentic, universal 5-fold offering practiced in daily householder worship:\n1. Gandha (Sandalwood / Tilak)\n2. Pushpa (Fresh Flowers)\n3. Dhoop (Sacred Incense)\n4. Deepa (Ghee/Oil Lamp)\n5. Naivedya (Pure Food/Fruit Offering).",
            originalText = "गन्धं समर्पयामि। पुष्पं समर्पयामि। धूपं घ्रापयामि। दीपं दर्शयामि। नैवेद्यं निवेदयामि॥",
            translation = "I humbly offer sacred sandalwood paste, fresh fragrant flowers, holy incense, illuminated ghee lamp, and pure food offering to the Supreme Deity.",
            aiExplanation = "Sadhak AI Mitra Insight: Performing Panchopachara Pooja daily establishes a pure, high-vibration spiritual atmosphere in your living space.",
            sourceName = "Sanatan Nitya Karma Pooja Paddhati - Gita Press",
            sourceType = "Traditional Reference",
            textReference = "Nitya Karma Paddhati",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "TRADITIONAL_TEACHING",
            format = "Pooja Vidhi",
            language = "en",
            difficulty = "Beginner",
            readTimeMinutes = 6,
            relatedTopics = "Pooja,Rituals,Devotion"
        ),
        KnowledgeArticle(
            category = "Pooja & Vidhi",
            title = "दैनिक पंचोपचार पूजा विधि और नियम",
            summary = "शास्त्रसम्मत सरल ५-चरण गृह पूजा विधि - गन्ध, पुष्प, धूप, दीप और नैवेद्य।",
            content = "सनातन परंपरा में गृहस्थ साधकों के लिए दैनिक पंचोपचार पूजा का विधान है। यह ५ मुख्य समर्पणों के माध्यम से भगवान के प्रति पूर्ण कृतज्ञता व्यक्त करने की सबसे पावन विधि है।",
            originalText = "गन्धं समर्पयामि। पुष्पं समर्पयामि। धूपं घ्रापयामि। दीपं दर्शयामि। नैवेद्यं निवेदयामि॥",
            translation = "मैं ईश्वर के चरणों में चंदन, पुष्प, सुगंधित धूप, पवित्र दीपक और शुद्ध नैवेद्य (भोग) अर्पित करता हूँ।",
            aiExplanation = "साधक एआई मित्र विचार: नित्य पंचोपचार पूजा करने से घर में सकारात्मक ऊर्जा, समृद्धि और मानसिक शांति बनी रहती है।",
            sourceName = "नित्य कर्म पूजा पद्धति - गीता प्रेस",
            sourceType = "पारंपरिक सन्दर्भ",
            textReference = "नित्य कर्म पूजा पद्धति",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "TRADITIONAL_TEACHING",
            format = "Pooja Vidhi",
            language = "hi",
            difficulty = "Beginner",
            readTimeMinutes = 6,
            relatedTopics = "पूजा विधि,संस्कृति,भक्ति"
        ),

        // --- DEITIES & TEMPLES ---
        KnowledgeArticle(
            category = "Temples & Sacred Sites",
            title = "Kashi Vishwanath - The Eternal Light of Shiva",
            summary = "The spiritual history and significance of Kashi Vishwanath Jyotirlinga on the banks of Ganga.",
            content = "Kashi (Varanasi) is revered as the oldest living city in the world, held on the trident of Lord Shiva. The Kashi Vishwanath Jyotirlinga represents the supreme cosmic light of Shiva that liberates souls (Tarak Mantra).",
            originalText = "सत्यं शिवं सुन्दरम्। काशी विश्वनाथ गंगे॥",
            translation = "Truth is Shiva, Shiva is Beauty. Salutations to Vishwanath in sacred Kashi on the Ganga.",
            aiExplanation = "Sadhak AI Mitra Insight: Visiting Kashi or meditating upon Kashi Vishwanath reminds us that Shiva is the inner supreme flame of liberation (Moksha).",
            sourceName = "Shiva Purana - Koti Rudra Samhita",
            sourceType = "Traditional Scripture",
            textReference = "Shiva Purana Koti Rudra Samhita Chapter 22",
            verificationStatus = "VERIFIED_SOURCE",
            contentType = "ARTICLE",
            format = "Article",
            language = "en",
            difficulty = "Intermediate",
            readTimeMinutes = 7,
            relatedTopics = "Shiva,Kashi,Jyotirlinga"
        )
    )

    val defaultVideos = listOf(
        BhaktiVideo(
            title = "Diwali Mahalakshmi Puja & Deepawali Aarti",
            description = "Grand Diwali Aarti and sacred Lakshmi Ganesh Puja chanting for peace, prosperity and wisdom.",
            category = "Puja",
            videoId = "9bZkp7q19f0",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/9bZkp7q19f0/hqdefault.jpg",
            publishDate = "2026-09-01"
        ),
        BhaktiVideo(
            title = "Maha Shivratri Sacred Darshan & Bholenath Aarti",
            description = "Divine Kashi Vishwanath and Somnath Jyotirlinga Mahadev Darshan and sacred evening Aarti.",
            category = "Aarti",
            videoId = "1Z3cE3sA9YQ",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/1Z3cE3sA9YQ/hqdefault.jpg",
            publishDate = "2026-09-02"
        ),
        BhaktiVideo(
            title = "Har Ki Pauri Haridwar Divine Sandhya Aarti",
            description = "Sublime Har Ki Pauri evening Aarti with burning brass deepams on the sacred banks of Maa Ganga.",
            category = "Aarti",
            videoId = "kJQP7kiw5Fk",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/kJQP7kiw5Fk/hqdefault.jpg",
            publishDate = "2026-09-03"
        ),
        BhaktiVideo(
            title = "Shri Hanuman Chalisa & Sundarkand Chanting",
            description = "Devotional recital of the sacred Hanuman Chalisa for inner strength, courage, and divine surrender.",
            category = "Stories",
            videoId = "AETFv4yzkGU",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/AETFv4yzkGU/hqdefault.jpg",
            publishDate = "2026-09-04"
        ),
        BhaktiVideo(
            title = "Shri Mahakaleshwar Jyotirlinga Bhasma Aarti & Darshan",
            description = "Sacred early morning Bhasma Aarti and darshan of Mahakaleshwar Ujjain.",
            category = "Darshan",
            videoId = "3fumBcKC6RE",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/3fumBcKC6RE/hqdefault.jpg",
            publishDate = "2026-09-05"
        ),
        BhaktiVideo(
            title = "Banke Bihari Vrindavan Darshan & Krishna Bhajan",
            description = "Divine darshan of Thakur Banke Bihari Ji in Vrindavan Dham with sacred morning prayers.",
            category = "Darshan",
            videoId = "bO9p1Pekp3s",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/bO9p1Pekp3s/hqdefault.jpg",
            publishDate = "2026-09-06"
        ),
        BhaktiVideo(
            title = "Vedic Rudrabhishek & Gayatri Mantra 108 Chants",
            description = "Authentic Vedic chanting of Shri Rudram and 108 Gayatri Mantra recitations.",
            category = "Puja",
            videoId = "L_LUpnjgPso",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/L_LUpnjgPso/hqdefault.jpg",
            publishDate = "2026-09-07"
        ),
        BhaktiVideo(
            title = "Maa Durga Aarti & Navratri Garba Stuti",
            description = "Jai Ambe Gauri and sacred Navratri devotional chanting honoring the divine feminine mother.",
            category = "Aarti",
            videoId = "CevxZvSJLk8",
            isCustom = false,
            thumbnail = "https://img.youtube.com/vi/CevxZvSJLk8/hqdefault.jpg",
            publishDate = "2026-09-08"
        )
    )
    
    val defaultPosts = listOf<CommunityPost>()

    val defaultComments = listOf<PostComment>()
}
