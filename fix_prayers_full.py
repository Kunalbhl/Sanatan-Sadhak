import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """    val prayers = if (isEnglish) listOf(
        Pair("Hanuman Chalisa", "Shri Guru Charan Saroj Raj, Nij Manu Mukur Sudhari...\n\n(Full detail text for Hanuman Chalisa: It invokes courage, removes fear, and brings divine protection. Complete verses recite the glorious life of Lord Hanuman.)"),
        Pair("Maa Annapurna Stotram", "Nityanandakari Varabhayakari Saundaryaratnakari...\n\n(Full detail text: A beautiful prayer honoring Maa Annapurna, Goddess of Kashi. It brings satisfaction and abundance.)"),
        Pair("Shiva Panchakshari Mantra", "Nagendra Haraya Trilochanaya, Bhasmanga Ragaya Maheshwaraya...\n\n(Full detail text: The supreme 5-syllable chant of Mahadev Shiva that cleanses consciousness.)"),
        Pair("Maha Mrityunjaya Mantra", "Om Tryambakam Yajamahe Sugandhim Pushti-Vardhanam...\n\n(Full detail text: A life-giving prayer to Lord Shiva to overcome the fear of death and grant spiritual liberation.)")
    ) else listOf(
        Pair("श्री हनुमान चालीसा", "श्रीगुरु चरन सरोज रज, निज मनु मुकुरु सुधारि।\nबरनऊं रघुबर बिमल जसु, जो दायकु फल चारि॥\n\n(पूरा पाठ: यह स्तोत्र साहस जगाता है, भय दूर करता है, और ईश्वरीय सुरक्षा लाता है।)"),
        Pair("श्री अन्नपूर्णा स्तोत्रम्", "नित्यानन्दकरी वराभयकरी सौन्दर्यरत्नाकरी\nनिर्धूताखिलघोरपावनकरी प्रत्यक्षमाहेश्वरी...\n\n(पूरा पाठ: माता अन्नपूर्णा का वंदन, जो काशी की देवी हैं। यह संतुष्टि और प्रचुरता प्रदान करता है।)"),
        Pair("श्री शिव पंचाक्षर स्तोत्र", "नागेन्द्रहाराय त्रिलोचनाय,\nभस्माङ्गरागाय महेश्वराय।\n\n(पूरा पाठ: भगवान शिव का सर्वोच्च 5-अक्षरी मंत्र जो चेतना को शुद्ध करता है।)"),
        Pair("महामृत्युंजय मंत्र", "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्।\nउर्वारुकमिव बन्धनान् मृत्योर्मुक्षीय मामृतात्॥\n\n(पूरा पाठ: भगवान शिव से प्रार्थना जो मृत्यु के भय को दूर कर मोक्ष प्रदान करती है।)")
    )"""

content = re.sub(r'val prayers = listOf\(.*?Pair\(\n\s*"Shiva Panchakshari Mantra.*?\n\s*\)\n\s*\)', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
