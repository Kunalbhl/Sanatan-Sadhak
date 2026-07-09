import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = r'''    val prayers = if (isEnglish) listOf(
        Pair("Hanuman Chalisa", """Shri Guru Charan Saroj Raj, Nij Manu Mukur Sudhari...

(Full detail text for Hanuman Chalisa: It invokes courage, removes fear, and brings divine protection. Complete verses recite the glorious life of Lord Hanuman.)"""),
        Pair("Maa Annapurna Stotram", """Nityanandakari Varabhayakari Saundaryaratnakari...

(Full detail text: A beautiful prayer honoring Maa Annapurna, Goddess of Kashi. It brings satisfaction and abundance.)"""),
        Pair("Shiva Panchakshari Mantra", """Nagendra Haraya Trilochanaya, Bhasmanga Ragaya Maheshwaraya...

(Full detail text: The supreme 5-syllable chant of Mahadev Shiva that cleanses consciousness.)"""),
        Pair("Maha Mrityunjaya Mantra", """Om Tryambakam Yajamahe Sugandhim Pushti-Vardhanam...
Urvarukamiva Bandhanan Mrityor Mukshiya Maamritat

(Full detail text: A life-giving prayer to Lord Shiva to overcome the fear of death and grant spiritual liberation.)""")
    ) else listOf(
        Pair("श्री हनुमान चालीसा", """श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।
बरनऊं रघुबर बिमल जसु, जो दायकु फल चारि॥

(पूरा पाठ: यह स्तोत्र साहस जगाता है, भय दूर करता है, और ईश्वरीय सुरक्षा लाता है।)"""),
        Pair("श्री अन्नपूर्णा स्तोत्रम्", """नित्यानन्दकरी वराभयकरी सौन्दर्यरत्नाकरी
निर्धूताखिलघोरपावनकरी प्रत्यक्षमाहेश्वरी...

(पूरा पाठ: माता अन्नपूर्णा का वंदन, जो काशी की देवी हैं। यह संतुष्टि और प्रचुरता प्रदान करता है।)"""),
        Pair("श्री शिव पंचाक्षर स्तोत्र", """नागेन्द्रहाराय त्रिलोचनाय,
भस्माङ्गरागाय महेश्वराय।

(पूरा पाठ: भगवान शिव का सर्वोच्च 5-अक्षरी मंत्र जो चेतना को शुद्ध करता है।)"""),
        Pair("महामृत्युंजय मंत्र", """ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।
उर्वारुकमिव बन्धनान् मृत्योर्मुक्षीय मामृतात्॥

(पूरा पाठ: भगवान शिव से प्रार्थना जो मृत्यु के भय को दूर कर मोक्ष प्रदान करती है।)""")
    )'''

content = re.sub(r'val prayers = if \(isEnglish\) listOf\(.*?    \)', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
