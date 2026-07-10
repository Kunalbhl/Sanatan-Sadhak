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
        "अहं ब्रह्मास्मि — मैं ही अनंत सत्य हूँ। अपने हृदय के भीतर विराजमान पवित्र, परम दिव्य प्रकाश को पहचानो।",
        "मन चंचल है और इसे नियंत्रित करना कठिन है, लेकिन निरंतर अभ्यास (अभ्यास) और वैराग्य द्वारा इसे वश में किया जा सकता है।",
        "अपने हृदय को भक्ति से भर लो। सभी से प्रेम करो, सभी की सेवा करो, क्योंकि प्रत्येक जीव में एक ही परमात्मा का वास है।",
        "शांति भीतर से आती है। बाहर मत खोजो। आंतरिक प्रसन्नता ही साधक का सच्चा धन है।"
    )

    val defaultArticles = listOf(
        // --- ENGLISH ARTICLES ---
        // --- NEW TAXONOMY ARTICLES ---
        KnowledgeArticle(category = "Vedas & Granths", title = "Rigveda Overview", summary = "The Veda of Mantras.", content = "...", relatedVideos = "", language = "en", difficulty = "Advanced", readTimeMinutes = 15, relatedTopics = "Mantras,Rishis"),
        KnowledgeArticle(category = "Puranas", title = "Shiv Purana", summary = "Stories of Shiva.", content = "...", relatedVideos = "", language = "en", difficulty = "Intermediate", readTimeMinutes = 10, relatedTopics = "Mahadev"),
        KnowledgeArticle(category = "Itihasa (Epics)", title = "Ramayana", summary = "The epic of Rama.", content = "...", relatedVideos = "", language = "en", difficulty = "Beginner", readTimeMinutes = 20, relatedTopics = "Rama,Dharma"),
        KnowledgeArticle(category = "Deities", title = "Maa Durga", summary = "Mother of strength.", content = "...", relatedVideos = "", language = "en", difficulty = "Beginner", readTimeMinutes = 8, relatedTopics = "Shakti"),
        KnowledgeArticle(category = "Temples & Tirtha", title = "Kashi Vishwanath", summary = "Sacred Shiva temple.", content = "...", relatedVideos = "", language = "en", difficulty = "Intermediate", readTimeMinutes = 12, relatedTopics = "Shiva,Pilgrimage"),
        KnowledgeArticle(category = "Mantras & Stotras", title = "Gayatri Mantra", summary = "The supreme mantra.", content = "...", relatedVideos = "", language = "en", difficulty = "Beginner", readTimeMinutes = 5, relatedTopics = "Vedas"),
        KnowledgeArticle(category = "Puja & Path Vidhi", title = "Daily Puja Steps", summary = "Step by step puja.", content = "...", relatedVideos = "", language = "en", difficulty = "Beginner", readTimeMinutes = 7, relatedTopics = "Rituals"),
        KnowledgeArticle(category = "Kriya & Sadhana", title = "Pranayama Basics", summary = "Breath control.", content = "...", relatedVideos = "", language = "en", difficulty = "Intermediate", readTimeMinutes = 10, relatedTopics = "Yoga"),
        KnowledgeArticle(category = "Festivals & Vrats", title = "Diwali", summary = "Festival of lights.", content = "...", relatedVideos = "", language = "en", difficulty = "Beginner", readTimeMinutes = 6, relatedTopics = "Rama,Lakshmi"),
        KnowledgeArticle(category = "Concepts & Gyaan", title = "Karma Explained", summary = "Law of cause.", content = "...", relatedVideos = "", language = "en", difficulty = "Intermediate", readTimeMinutes = 9, relatedTopics = "Dharma"),
        KnowledgeArticle(category = "Saints & Gurus", title = "Adi Shankaracharya", summary = "The great teacher.", content = "...", relatedVideos = "", language = "en", difficulty = "Advanced", readTimeMinutes = 14, relatedTopics = "Advaita,Vedanta"),
        KnowledgeArticle(
            category = "Deities",
            title = "Sindari ke Balaji",
            summary = "The powerful, merciful form of Lord Hanuman, the destroyer of all evils and obstacle-remover.",
            content = "Sindari ke Balaji is a highly revered shrine and form of Lord Hanuman, known for immense spiritual energy, protective grace, and answering the sincere prayers of devotees. Lord Balaji represents absolute devotion (Bhakti), strength, and selfless service. Worshipers recite the Hanuman Chalisa or Sundarkand with total surrender. The divine darshan of Balaji fills the heart with unparalleled fearlessness, inner peace, and happiness, wiping out doubts and negative energies.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Deities",
            title = "Mahadev - Lord Shiva",
            summary = "The Supreme Cosmic Yogi, the Transformer, and the source of ultimate liberation (Moksha).",
            content = "Mahadev, or Shiva, is the destroyer of ignorance and ego, the ultimate anchor of meditation and cosmic silence. He resides on Mount Kailash, wearing ashes, holding a trident (Trishul), and radiating infinite peace. He is Ashutosh — one who is easily pleased by pure water, bilva leaves, and chanting of 'Om Namah Shivaya'. Shiva represents the pure, unchanging consciousness that underlies all existence.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Scriptures",
            title = "Srimad Bhagavad Gita",
            summary = "The divine song of God, containing 700 verses spoken by Lord Krishna to Arjuna on the battlefield.",
            content = "The Bhagavad Gita is the ultimate handbook for karma, self-mastery, and spiritual awakening. Spoken on the battlefield of Kurukshetra, it addresses Arjuna's deep despair and offers universal wisdom. It details three paths to liberation: Karma Yoga (Selfless Action), Bhakti Yoga (Loving Devotion), and Jnana Yoga (Sacred Knowledge). It teaches that soul is immortal, and performing duty without attachment leads to supreme joy.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Concepts",
            title = "The Law of Karma",
            summary = "The universal law of cause and effect: as you sow, so shall you reap.",
            content = "Karma is not punishment, but a neutral mirror of our actions, thoughts, and intentions. Every positive action (Nishkama Karma — action done selflessly) purifies our heart and mind. Negative actions, driven by ego, anger, or greed, create bondage. By focusing on helpful deeds, kind words, and surrender to the Divine, a sadhak rises above suffering and achieves inner happiness and liberation.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),

        KnowledgeArticle(
            category = "Rituals",
            title = "Home Pooja Guide & Spiritual Remedies (घरेलू पूजा और उपाय)",
            summary = "Step-by-step guide to perform daily Pooja at home with required items (Samagri) and simple spiritual remedies for peace and prosperity.",
            content = "PERFORMING POOJA AT HOME:\n\n1. Required Items (Samagri / सामग्री):\n• Kumkum/Roli, Sandalwood paste (चंदन)\n• Unbroken Rice (Akshat / अक्षत)\n• Fresh Flowers and Garland (पुष्प)\n• Incense sticks (Agarbatti/Dhoop) & Ghee Lamp (Diya)\n• Bell (Ghanti) & Conch shell (Shankh)\n• Camphor (Kapur) for Aarti\n• Naivedya (Fruit/Sweets for offering)\n• Ganga Jal (Holy water)\n\n2. Preparation (तैयारी):\nClean the area and yourself. Wear clean clothes. Sit on an Asana facing East or North. Place the deity's idol/picture respectfully.\n\n3. Pooja Steps (पूजा विधि):\n• Aachaman: Sip water 3 times chanting 'Om Keshavaya Namah, Om Narayanaya Namah, Om Madhavaya Namah'.\n• Sankalpa: Take a pledge of the Pooja.\n• Dhyana & Avahana: Invite the deity to be present in your heart and the idol.\n• Abhishek/Snana: Offer water (or panchamrit) to the deity.\n• Vastra/Chandan/Akshat: Offer clothes (or sacred thread), sandalwood, and rice.\n• Pushpa & Naivedya: Offer flowers and food (Bhog).\n• Aarti: Light the camphor or ghee lamp, ring the bell, and sing the Aarti with devotion.\n• Kshamaprarthana: Ask for forgiveness for any mistakes.\n\nSPIRITUAL REMEDIES (अध्यात्मिक उपाय):\n• For Peace of Mind: Chant 'Om Namah Shivaya' 108 times daily and offer water to Shivling on Mondays.\n• For Removing Obstacles: Recite Hanuman Chalisa daily and light a jasmine oil lamp on Tuesdays.\n• For Prosperity: Recite Sri Suktam or Lakshmi Ashtothram on Fridays. Always keep the Northeast corner of the house clean.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        // --- HINDI ARTICLES ---
        KnowledgeArticle(
            category = "Deities",
            title = "माँ अन्नपूर्णा महारानी",
            summary = "पोषण, भोजन और प्रचुरता की दिव्य देवी, जो संपूर्ण ब्रह्मांड का पोषण करती हैं।",
            content = "माँ अन्नपूर्णा माता पार्वती का अवतार हैं। वे प्रकृति और दिव्य कृपा की अनंत पोषण शक्ति का प्रतिनिधित्व करती हैं। स्वयं भगवान शिव ने ब्रह्मांडीय संतुलन के लिए भोजन मांगते हुए उनके सामने भिक्षापात्र रखा था। 'अन्नपूर्णा' का अर्थ है 'अन्न प्रदान करने वाली'। उनका मुख्य मंदिर काशी (वाराणसी) में स्थित है। माँ अन्नपूर्णा की भक्ति से साधक का घर और मन सदैव तृप्त रहता है।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        KnowledgeArticle(
            category = "Deities",
            title = "सिणधरी के बालाजी",
            summary = "भगवान हनुमान का शक्तिशाली और दयालु स्वरूप, जो सभी संकटों को हरने वाले हैं।",
            content = "सिणधरी के बालाजी महाराज का मंदिर श्रद्धा और भक्ति का एक मुख्य केंद्र है। संकटमोचन हनुमान जी का यह स्वरूप भक्तों के कष्टों को तुरंत दूर करता है। बालाजी की आराधना से जीवन में साहस, निडरता और सच्ची सुख-शांति का संचार होता है। हनुमान चालीसा और सुंदरकांड का पाठ करने से भक्तों के सभी मनोरथ पूर्ण होते हैं।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        KnowledgeArticle(
            category = "Deities",
            title = "महादेव - भगवान शिव",
            summary = "परम योगी, अज्ञानता के संहारक और परम मोक्ष के प्रदाता।",
            content = "भगवान शिव अनंत चेतना हैं। वे कैलाश पर्वत पर ध्यानमग्न रहते हैं और भस्म, त्रिशूल तथा डमरू धारण करते हैं। वे आशुतोष हैं — जो केवल जल और बेलपत्र चढ़ाने से भी प्रसन्न हो जाते हैं। 'ॐ नमः शिवाय' मंत्र का जाप करने से मन शांत होता है और अंतर्मन में दिव्य प्रकाश जागृत होता है।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        KnowledgeArticle(
            category = "Scriptures",
            title = "श्रीमद्भगवद्गीता",
            summary = "भगवान श्री कृष्ण द्वारा कुरुक्षेत्र के युद्ध मैदान में अर्जुन को दिया गया दिव्य उपदेश।",
            content = "श्रीमद्भगवद्गीता में १८ अध्याय और ७०० श्लोक हैं। यह कर्म, ज्ञान और भक्ति का अद्भुत समन्वय है। भगवान कृष्ण अर्जुन के माध्यम से संपूर्ण मानवता को सिखाते हैं कि अपने कर्तव्यों का निष्काम भाव से पालन करना ही सच्चा योग है। आत्मा अमर है, इसलिए भय को त्यागकर सत्य के मार्ग पर चलना चाहिए।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        // --- BHAGAVAD GITA CATEGORY ---
        KnowledgeArticle(
            category = "Bhagavad Gita",
            title = "Srimad Bhagavad Gita - Chapter 1 to 18 Summary",
            summary = "A comprehensive overview of the 18 chapters of the Gita, explaining Karma, Bhakti, and Jnana Yoga.",
            content = "The Bhagavad Gita consists of 18 Chapters and 700 verses. Here is a brief summary of the chapters:\n\n1. Arjuna Vishada Yoga: Arjuna’s sorrow on seeing his relatives on the battlefield.\n2. Sankhya Yoga: Lord Krishna instructs Arjuna on the immortality of the soul and the duty of a warrior.\n3. Karma Yoga: The path of selfless action without attachment to fruits.\n4. Jnana Karma Sanyasa Yoga: The path of divine knowledge and performing action as an offering.\n5. Karma Sanyasa Yoga: Outwardly acting while inwardly renouncing ownership.\n6. Dhyana Yoga: The practice of meditation and mind control.\n7. Jnana Vijnana Yoga: Knowing God through spiritual wisdom and physical science.\n8. Akshara Brahma Yoga: The path of the eternal Brahman and attaining liberation upon leaving the body.\n9. Raja Vidya Raja Guhya Yoga: The sovereign science and secret of devotion.\n10. Vibhuti Yoga: Discovering God's infinite manifestations in nature and greatness.\n11. Vishwarupa Darshana Yoga: Krishna reveals His magnificent, all-encompassing Cosmic Form.\n12. Bhakti Yoga: The path of absolute, loving devotion and surrender.\n13. Kshetra Kshetrajna Vibhaga Yoga: Distinguishing between the field (body) and the knower of the field (soul).\n14. Gunatraya Vibhaga Yoga: Rising above the three modes of material nature (Sattva, Rajas, Tamas).\n15. Purushottama Yoga: Understanding the supreme personal aspect of God.\n16. Daivasura Sampad Vibhaga Yoga: Discerning divine characteristics from demoniac traits.\n17. Shraddhatraya Vibhaga Yoga: Understanding three divisions of faith (food, sacrifice, penance).\n18. Moksha Sanyasa Yoga: The ultimate path of liberation, renunciation, and complete surrender to Krishna.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Bhagavad Gita",
            title = "श्रीमद्भगवद्गीता - अध्याय १ से १८ सारांश",
            summary = "श्रीमद्भगवद्गीता के सभी १८ अध्यायों का संक्षिप्त और अत्यंत ज्ञानवर्धक हिंदी सारांश।",
            content = "श्रीमद्भगवद्गीता के १८ अध्यायों का संक्षिप्त सारांश इस प्रकार है:\n\n१. अर्जुनविषादयोग: कुरुक्षेत्र के मैदान में सगे-संबंधियों को युद्ध में देखकर अर्जुन का शोकग्रस्त होना।\n२. सांख्ययोग: आत्मा की अमरता, निष्काम कर्तव्य भावना और स्थितप्रज्ञ पुरुष के लक्षण।\n३. कर्मयोग: फल की इच्छा के बिना कर्तव्य कर्म करना ही कल्याणकारी है।\n४. ज्ञानकर्मसंन्यासयोग: दिव्य ज्ञान और कर्मों की यज्ञरूपता।\n५. कर्मसंन्यासयोग: सकाम कर्मों का त्याग और ज्ञान की साधना।\n६. आत्मसंयमयोग: मन को नियंत्रित करने के लिए ध्यान और प्राणायाम की विधि।\n७. ज्ञानविज्ञानयोग: परमात्मा की परा और अपरा प्रकृति का ज्ञान।\n८. अक्षरब्रह्मयोग: अविनाशी ब्रह्म का स्वरूप और अंत समय में परमात्मा का स्मरण।\n९. राजविद्याराजगुह्ययोग: परम गोपनीय भक्ति विज्ञान और ईश्वर की दिव्य सत्ता।\n१०. विभूतियोग: सृष्टि के कण-कण में परमात्मा के ऐश्वर्य और महिमा का दर्शन।\n११. विश्वरूपदर्शनयोग: अर्जुन को दिव्य नेत्र प्रदान कर श्रीकृष्ण द्वारा अपने विराट स्वरूप का दर्शन कराना।\n१२. भक्तियोग: भगवान के साकार और निराकार रूपों की भक्ति और प्यारे भक्तों के उत्तम लक्षण।\n१३. क्षेत्र-क्षेत्रज्ञविभागयोग: शरीर (क्षेत्र) और आत्मा (क्षेत्रज्ञ) के बीच का भेद।\n१४. गुणत्रयविभागयोग: प्रकृति के तीन गुणों - सत्त्व, रज और तम की कार्यप्रणाली।\n१५. पुरुषोत्तमयोग: संसार रूपी अश्वत्थ वृक्ष और सर्वोपरि पुरुषोत्तम तत्त्व।\n१६. दैवासुरसम्पद्विभागयोग: देवी और आसुरी प्रवृत्तियों की पहचान।\n१७. श्रद्धात्रयविभागयोग: आहार, यज्ञ, तप और दान की त्रिगुणमयता।\n१८. मोक्षसंन्यासयोग: त्याग और संन्यास की पराकाष्ठा, तथा भगवान के चरणों में पूर्ण शरणागति।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        // --- RAMCHARITMANAS CATEGORY ---
        KnowledgeArticle(
            category = "Ramcharitmanas",
            title = "Sri Ramcharitmanas - The Seven Kandas",
            summary = "The legendary spiritual epic composed by Goswami Tulsidas, structured in seven divine chapters.",
            content = "Sri Ramcharitmanas is a deeply emotional and spiritual epic written in Awadhi by Goswami Tulsidas. It is structured into seven distinct sections or 'Kandas':\n\n1. Bala Kanda: Narrates the childhood of Sri Rama, the divine lineage, marriage with Maa Sita, and teachings on dharma.\n2. Ayodhya Kanda: The preparation for Rama's coronation, his exile to the forest, and Bharat's deep, selfless love.\n3. Aranya Kanda: The forest life of Rama, Sita, and Lakshmana, the encounters with sages, and the abduction of Sita by Ravana.\n4. Kishkindha Kanda: Meeting with Hanuman ji, friendship with Sugriva, and the search for Sita.\n5. Sundara Kanda: Focused on Hanuman ji's heroic leap to Lanka, meeting Sita, burning Lanka, and demonstrating absolute devotion. Chanting Sundarakand brings tremendous mental strength and destroys all obstacles.\n6. Lanka Kanda: The construction of the Ram Setu, the legendary war between Rama and Ravana, the victory of truth over evil, and Sita's rescue.\n7. Uttara Kanda: Sri Rama's coronation in Ayodhya, the establishment of 'Ram Rajya' (the ideal righteous state), and Kakbhashundi's discourses on Bhakti and spiritual wisdom.",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Ramcharitmanas",
            title = "श्रीरामचरितमानस - सप्त सोपान (सात कांड)",
            summary = "गोस्वामी तुलसीदास जी द्वारा रचित पवित्र महाकाव्य श्रीरामचरितमानस के सात अध्यायों का आध्यात्मिक महत्त्व।",
            content = "गोस्वामी तुलसीदास जी द्वारा अवधी भाषा में रचित श्रीरामचरितमानस भक्ति और नीति का अद्वितीय महाकाव्य है। यह सात कांडों (सोपानों) में विभाजित है:\n\n१. बालकाण्ड: प्रभु श्रीराम का जन्म, बाललीला, ताड़का वध, धनुष यज्ञ और सीता जी के साथ पावन विवाह का वर्णन।\n२. अयोध्याकाण्ड: श्रीराम के राज्याभिषेक की तैयारी, कैकेयी का वरदान, वनगमन, दशरथ का प्राण त्याग और भरत का निश्छल भ्रातृप्रेम।\n३. अरण्यकाण्ड: वन जीवन, शूर्पणखा प्रसंग, खर-दूषण वध, मारीच प्रसंग और रावण द्वारा सीता जी का कपटपूर्वक हरण।\n४. किष्किन्धाकाण्ड: हनुमान जी से पहली भेंट, सुग्रीव से मित्रता, बालि वध और सीता जी की खोज का आरंभ।\n५. सुंदरकाण्ड: हनुमान जी द्वारा समुद्र लांघकर लंका जाना, सीता माता से मिलना, लंका दहन और विभीषण शरणागति। सुंदरकाण्ड का पाठ भय को दूर कर विजय प्रदान करता है।\n६. लंकाकाण्ड: रामेश्वरम की स्थापना, राम सेतु निर्माण, प्रभु राम और रावण का महायुद्ध, रावण वध और अधर्म पर धर्म की महान विजय।\n७. उत्तरकाण्ड: श्रीराम का सानंद अयोध्या लौटना, राज्याभिषेक, रामराज्य की महिमा, और काकभुशुंडि-गरुड़ जी का भक्ति और ज्ञान विषयक संवाद।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        // --- SHIV PURAN CATEGORY ---
        KnowledgeArticle(
            category = "Shiv Puran",
            title = "Shiv Mahapuran - The Divine Glories of Lord Shiva",
            summary = "Discover the sacred stories, cosmology, and the spiritual secrets of Shiva worship as detailed in the Shiva Purana.",
            content = "The Shiva Purana is one of the eighteen major Purana scriptures, dedicated entirely to Lord Shiva and His consort Parvati. It contains rich cosmological explanations, spiritual values, and moral stories:\n\n1. Rudra Samhita: Details the creation of the cosmos, the penance and divine marriage of Parvati with Shiva, the birth of Kartikeya and Ganesha, and the destruction of the demon Tarakasura.\n2. Shatarudra Samhita: Explains the various divine incarnations of Shiva, including the furious Virabhadra, Sharabha, and the peaceful avatar Hanuman.\n3. Koti Rudra Samhita: Celebrates the twelve sacred Jyotirlingas, their locations (like Somnath, Vishwanath, Mahakaleshwar), and the incredible healing powers associated with worshiping these energetic shrines.\n4. Uma Samhita: Discusses the virtues of charity, penance, dharma, and the spiritual power of chanting Shiva's names.\n5. Kailash Samhita: Elaborates on the deeper meditative practices, Yoga, and the sacred realization of 'Aham Brahmasmi' (the inner divine reality).\n\nWorshiping Shiva through Shiva Purana reading helps clear past karmic blockages, stabilizes restless minds, and installs deep spiritual peace and ultimate liberation (Moksha).",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Shiv Puran",
            title = "शिव महापुराण - भगवान शिव की दिव्य लीलाएं",
            summary = "शिव महापुराण के मुख्य प्रसंगों, बारह ज्योतिर्लिंगों के महत्त्व और शिव साधना के रहस्यमयी ज्ञान का परिचय।",
            content = "शिव महापुराण अट्ठारह पुराणों में से एक अत्यंत पावन ग्रन्थ है, जो भगवान सदाशिव की भक्ति और ब्रह्मांडीय रहस्यों से परिपूर्ण है। इसके मुख्य सोपान इस प्रकार हैं:\n\n१. रुद्र संहिता: सृष्टि की रचना, माता सती और पार्वती का पावन चरित्र, भगवान शिव के साथ विवाह, कार्तिकेय और श्री गणेश जी का जन्म।\n२. शतरुद्र संहिता: भगवान शिव के विभिन्न दिव्य अवतारों (जैसे वीरभद्र, शरभ, पिप्पलाद और भक्त शिरोमणि हनुमान) का पावन वर्णन।\n३. कोटिरुद्र संहिता: भारतवर्ष में प्रतिष्ठित बारह ज्योतिर्लिंगों (सोमनाथ, मल्लिकार्जुन, महाकालेश्वर, ओंकारेश्वर, केदारनाथ, भीमाशंकर, विश्वनाथ, त्र्यंबकेश्वर, वैद्यनाथ, नागेश्वर, रामेश्वरम, घृष्णेश्वर) की दिव्य कथाएं और माहात्म्य।\n४. उमा संहिता: दान, तप, धर्म, और शिव नाम जप की आध्यात्मिक शक्ति का वर्णन।\n५. कैलाश संहिता: शिव-साधना की आंतरिक ध्यान विधियां, योग और वेदांत दर्शन का उत्कृष्ट निरूपण।\n\nशिव पुराण के श्रवण और पाठ से साधक के अंतःकरण की शुद्धि होती है, भय नष्ट होता है और अखंड सुख-समृद्धि एवं परम मोक्ष की प्राप्ति होती है।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        ),
        // --- VEDAS & GRANTHS CATEGORY ---
        KnowledgeArticle(
            category = "Vedas & Granths",
            title = "The Four Sacred Vedas & Upanishads",
            summary = "An introduction to the foundational pillars of Sanatan Dharma: Rigveda, Samaveda, Yajurveda, and Atharvaveda.",
            content = "The Vedas are the ancient-most spiritual scriptures known to humanity. They represent revealed, eternal knowledge (Shruti) heard by ancient rishis in deep states of meditation.\n\n1. Rigveda: The Veda of Mantras. It consists of 1,028 hymns (suktas) dedicated to natural elements and deities representing the Supreme Cosmic Power. It contains the divine Gayatri Mantra.\n2. Samaveda: The Veda of Melodies. It sets the Rigvedic verses to musical notes, forming the ancient-most foundation of Indian classical music.\n3. Yajurveda: The Veda of Rituals and Yajnas. It provides instructions on performing sacred fires, sacrifices, and maintaining physical and mental hygiene.\n4. Atharvaveda: The Veda of daily living, science, medicine (Ayurveda), and prayers for protection, harmony, and prosperity.\n\nUPANISHADS (VEDANTA):\nThe Upanishads are the philosophical essence of the Vedas. They shift focus from rituals to inner realization, answering fundamental questions: 'Who am I?', 'What is the nature of the Supreme reality?'. Concepts like the Soul (Atman) and Brahman being identical represent the highest peak of non-dual philosophy (Advaita).",
            relatedVideos = "zHzPEdLJEvT",
            language = "en"
        ),
        KnowledgeArticle(
            category = "Vedas & Granths",
            title = "चार पवित्र वेद और उपनिषद ज्ञान",
            summary = "सनातन धर्म के आदि स्तम्भों - ऋग्वेद, सामवेद, यजुर्वेद, और अथर्ववेद का प्रामाणिक और सरल परिचय।",
            content = "वेद सनातन धर्म के सबसे प्राचीन और प्रामाणिक धर्मग्रन्थ हैं। 'वेद' शब्द का अर्थ है 'ज्ञान'। ऋषियों ने गहरे ध्यान में इस ईश्वरीय वाणी (श्रुति) का साक्षात्कार किया था:\n\n१. ऋग्वेद: यह सबसे प्राचीन वेद है। इसमें १०,५५२ मंत्र और १,०२८ सूक्त हैं जो देवताओं की स्तुति और ब्रह्मांडीय सत्य को प्रकट करते हैं। पवित्र 'गायत्री मंत्र' इसी वेद का हिस्सा है।\n२. सामवेद: यह संगीत का वेद है। ऋग्वेद की ऋचाओं को मधुर स्वरों में कैसे गाया जाए, इसका ज्ञान इसी में है। यह भारतीय शास्त्रीय संगीत का आदि स्रोत है।\n३. यजुर्वेद: यह यज्ञ और कर्मकांड का वेद है। इसमें विभिन्न पूजा-विधियों, यज्ञों और जीवन जीने के व्यावहारिक नियमों का उल्लेख है।\n४. अथर्ववेद: इसमें दैनिक जीवन के विज्ञान, औषधि शास्त्र (आयुर्वेद), गणित, और समाज कल्याण से संबंधित ज्ञान संग्रहित है।\n\nउपनिषद (वेदांत):\nउपनिषद वेदों का दार्शनिक शिखर हैं। 'उपनिषद' का अर्थ है - गुरु के निकट निष्ठापूर्वक बैठना। ये ग्रन्थ बाहरी कर्मकांडों से ध्यान हटाकर आंतरिक आत्मज्ञान की ओर ले जाते हैं। इनमें 'अहं ब्रह्मास्मि' और 'तत् त्वम् असि' जैसे महावाक्यों द्वारा जीव और ब्रह्म की एकता को प्रतिपादित किया गया है।",
            relatedVideos = "zHzPEdLJEvT",
            language = "hi"
        )
    )

    val defaultVideos = listOf(
        BhaktiVideo(
            title = "Maa Annapurna Maharani Shringar Darshan",
            description = "Experience the divine shringar, puja, and aarti of Maa Annapurna Maharani. May her grace bring happiness and abundance to your home.",
            category = "Darshan & Puja",
            videoId = "zHzPEdLJEvT",
            isCustom = false
        ),
        BhaktiVideo(
            title = "Sindari ke Balaji Hanuman Chalisa Path",
            description = "Powerful Hanuman Chalisa recitation with divine darshan of Sindari ke Balaji. Destroy all negative energies and obstacles.",
            category = "Aarti & Abhishek",
            videoId = "zHzPEdLJEvT",
            isCustom = false
        ),
        BhaktiVideo(
            title = "Mahadev Shivling Rudrabhishek Puja",
            description = "Sacred Shivling Rudrabhishek performed on Monday. Connect with the tranquil and blissful chants of Om Namah Shivaya.",
            category = "Darshan & Puja",
            videoId = "zHzPEdLJEvT",
            isCustom = false
        ),
        BhaktiVideo(
            title = "Aarti Sangrah of Maa Annapurna & Mahadev",
            description = "Chant along with the divine evening aarti of Maa Annapurna and Lord Shiva. A calm prayer for inner peace.",
            category = "Aarti & Abhishek",
            videoId = "zHzPEdLJEvT",
            isCustom = false
        ),
        BhaktiVideo(
            title = "Sacred Visit: Historic Temples of India",
            description = "Join us as we explore ancient, highly energetic temples of Sanatan Dharma, learning their stories and teachings.",
            category = "Temple Visits",
            videoId = "zHzPEdLJEvT",
            isCustom = false
        )
    )

    val defaultPosts = listOf(
        CommunityPost(
            category = "Bhakti Experiences",
            author = "Ram_Devotee",
            title = "How chanting 'Om Namah Shivaya' healed my anxiety",
            content = "I started practicing 15 minutes of silent meditation combined with chanting the Shiva Panchakshari mantra every morning. The mental silence and inner peace it brought me is incredible. Has anyone else experienced this deep tranquility?",
            upvotes = 12,
            downvotes = 0,
            commentsCount = 2,
            isPinned = true
        ),
        CommunityPost(
            category = "Doubts & Discussions",
            author = "Sadhak_Kunal",
            title = "Understanding Nishkama Karma in daily office work",
            content = "The Gita says 'perform action without attachment to fruits.' How can we apply this practically in our jobs where we are evaluated on targets, promotions, and numbers? Looking forward to your thoughts and guidance.",
            upvotes = 8,
            downvotes = 1,
            commentsCount = 3,
            isPinned = false
        ),
        CommunityPost(
            category = "Daily Karma Journal",
            author = "Ananya_S",
            title = "Feeding stray animals is indeed a form of worship",
            content = "Today I fed some stray dogs in my area. Watching their eyes light up made me realize Maa Annapurna feeds through our hands. Let us try to make at least one life comfortable every single day.",
            upvotes = 15,
            downvotes = 0,
            commentsCount = 1,
            isPinned = false
        )
    )

    val defaultComments = listOf(
        PostComment(
            postId = 1,
            author = "GitaStudent",
            content = "Yes, brother! Chanting Shiva mantra is known to slow down brain waves and bring absolute cosmic alignment. Har Har Mahadev!"
        ),
        PostComment(
            postId = 1,
            author = "ShantiGiver",
            content = "Wonderful experience! I chant Hanuman Chalisa whenever I feel fearful, and it immediately restores inner strength."
        ),
        PostComment(
            postId = 2,
            author = "KarmaYogi",
            content = "Applying Nishkama Karma at work means: give your 100% effort to the code/work because that is your duty. Do not stress over what ranking or feedback you will get. True peace lies in quality of action, not the bonus!"
        ),
        PostComment(
            postId = 2,
            author = "SimpleSadhak",
            content = "Exactly. Treat your job as service (Seva) to the universe, and perform it as an offering to God. This completely shifts the work anxiety."
        )
    )
}
