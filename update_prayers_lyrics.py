import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = r'''    val prayers = if (isEnglish) listOf(
        Pair("Hanuman Chalisa", """Shri Guru Charan Saroj Raj, Nij Manu Mukur Sudhari.
Barnaum Raghuvar Bimal Jasu, Jo Dayaku Phal Chari.
Budhi Heen Tanu Janike, Sumirow Pavan Kumar.
Bal Buddhi Vidya Dehu Mohi, Harahu Kalesh Bikaar.

Jai Hanuman Gyan Gun Sagar. Jai Kapis Tihun Lok Ujagar.
Ram Doot Atulit Bal Dhama. Anjani Putra Pavan Sut Nama.
Mahabir Bikram Bajrangi. Kumati Nivar Sumati Ke Sangi.
Kanchan Baran Viraj Subesa. Kanan Kundal Kunchit Kesa.

Hath Vajra Aur Dhvaja Viraje. Kandhe Moonj Janeu Saje.
Sankar Suvan Kesri Nandan. Tej Pratap Maha Jag Vandan.
Vidyavan Guni Ati Chatur. Ram Kaj Karibe Ko Atur.
Prabhu Charitra Sunibe Ko Rasiya. Ram Lakhan Sita Man Basiya.

(This is a summarized short version of the complete Hanuman Chalisa reciting the glorious life and power of Lord Hanuman.)"""),
        Pair("Maa Annapurna Stotram", """Nityanandakari Varabhayakari Saundaryaratnakari
Nirdhutakhilaghorapavanakari Pratyakshamaheshwari.
Praleyachala Vamsha Pavanakari Kashipuradhishwari
Bhiksham Dehi Kripavalambanakari Mata Annapurneshwari.

Nanaratnavichitrabhushanakarim Hemambaradambarim
Muktaharamavilambamana Vilasadvakshojakumbhantarim.
Kashmiraagaru Vasitangiruchiram Kashipuradhishwari
Bhiksham Dehi Kripavalambanakari Mata Annapurneshwari.

(The Stotram praises Maa Annapurna, Goddess of Kashi, seeking her grace for physical and spiritual nourishment.)"""),
        Pair("Shiva Panchakshari Mantra", """Nagendra Haraya Trilochanaya,
Bhasmanga Ragaya Maheshwaraya.
Nityaya Shuddhaya Digambaraya,
Tasmai 'Na' Karaya Namah Shivaya.

Mandakini Salila Chandana Charchitaya,
Nandishvara Pramatha Natha Maheshwaraya.
Mandarapushpa Bahupushpa Supujitaya,
Tasmai 'Ma' Karaya Namah Shivaya.

Shivaaya Gauri Vadanabjavrunda,
Suryaya Dakshadhvara Nashakaya.
Shri Neelakanthaya Vrushadhvajaya,
Tasmai 'Shi' Karaya Namah Shivaya.

(This supreme 5-syllable chant of Mahadev Shiva cleanses consciousness.)"""),
        Pair("Maha Mrityunjaya Mantra", """Om Tryambakam Yajamahe
Sugandhim Pushti-Vardhanam
Urvarukamiva Bandhanan
Mrityor Mukshiya Maamritat

(A life-giving prayer to Lord Shiva to overcome the fear of death and grant spiritual liberation.)""")
    ) else listOf(
        Pair("श्री हनुमान चालीसा", """श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।
बरनऊं रघुबर बिमल जसु, जो दायकु फल चारि॥
बुद्धिहीन तनु जानिके, सुमिरौं पवन-कुमार।
बल बुद्धि बिद्या देहु मोहिं, हरहु कलेस बिकार॥

जय हनुमान ज्ञान गुन सागर। जय कपीस तिहुं लोक उजागर॥
रामदूत अतुलित बल धामा। अंजनि-पुत्र पवनसुत नामा॥
महाबीर बिक्रम बजरंगी। कुमति निवार सुमति के संगी॥
कंचन बरन बिराज सुबेसा। कानन कुंडल कुंचित केसा॥

हाथ बज्र औ ध्वजा बिराजै। कांधे मूंज जनेऊ साजै॥
संकर सुवन केसरीनंदन। तेज प्रताप महा जग बंदन॥
बिद्यावान गुनी अति चातुर। राम काज करिबे को आतुर॥
प्रभु चरित्र सुनिबे को रसिया। राम लखन सीता मन बसिया॥

(यह श्री हनुमान चालीसा का संक्षिप्त रूप है जो भगवान हनुमान की महिमा और शक्ति का वर्णन करता है।)"""),
        Pair("श्री अन्नपूर्णा स्तोत्रम्", """नित्यानन्दकरी वराभयकरी सौन्दर्यरत्नाकरी
निर्धूताखिलघोरपावनकरी प्रत्यक्षमाहेश्वरी।
प्रालेयाचलवंशपावनकरी काशीपुराधीश्वरी
भिक्षां देहि कृपावलम्बनकरी मातान्नपूर्णेश्वरी॥

नानारत्नविचित्रभूषणकरी हेमम्बराडम्बरी
मुक्ताहारविलम्बमान विलसद्वक्षोजकुम्भान्तरी।
काश्मीरागरुवासिताङ्गिरुचिरा काशीपुराधीश्वरी
भिक्षां देहि कृपावलम्बनकरी मातान्नपूर्णेश्वरी॥

(माता अन्नपूर्णा का वंदन, जो काशी की देवी हैं। यह स्तोत्र शारीरिक और आध्यात्मिक पोषण के लिए उनकी कृपा का आह्वान करता है।)"""),
        Pair("श्री शिव पंचाक्षर स्तोत्र", """नागेन्द्रहाराय त्रिलोचनाय,
भस्माङ्गरागाय महेश्वराय।
नित्याय शुद्धाय दिगम्बराय,
तस्मै नकाराय नमः शिवाय॥

मन्दाकिनी सलिल चन्दन चर्चिताय,
नन्दीश्वर प्रमथनाथ महेश्वराय।
मन्दारपुष्प बहुपुष्प सुपूजिताय,
तस्मै मकाराय नमः शिवाय॥

शिवाय गौरी वदनाब्जवृन्द,
सूर्याय दक्षाध्वरनाशकाय।
श्री नीलकण्ठाय वृषध्वजाय,
तस्मै शिकाराय नमः शिवाय॥

(भगवान शिव का सर्वोच्च 5-अक्षरी मंत्र जो चेतना को शुद्ध करता है।)"""),
        Pair("महामृत्युंजय मंत्र", """ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।
उर्वारुकमिव बन्धनान् मृत्योर्मुक्षीय मामृतात्॥

(भगवान शिव से प्रार्थना जो मृत्यु के भय को दूर कर मोक्ष प्रदान करती है।)""")
    )'''

content = re.sub(r'val prayers = if \(isEnglish\) listOf\(.*?(?=\n    if \(activePlayingMantraTitle != null\))', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
