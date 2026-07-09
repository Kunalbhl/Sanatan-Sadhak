import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

replacement = r'''        val similar = if (isEnglish) listOf(
            Pair("Durga Suktam", "Om Jatavedase Sunavama Somam... A powerful prayer to Goddess Durga for removing obstacles and providing safe passage through difficulties."),
            Pair("Mahamrityunjaya Mantra", "Om Tryambakam Yajamahe... The great death-conquering mantra of Lord Shiva for health and longevity."),
            Pair("Shiva Tandava Stotram", "Jatatavigalajjala Pravahapavitasthale... A magnificent hymn describing Lord Shiva's cosmic dance, invoking energy and power."),
            Pair("Vishnu Sahasranama", "Shuklam Bharadharam Vishnum... The thousand names of Lord Vishnu, bringing peace, prosperity and purity of mind.")
        ) else listOf(
            Pair("श्री दुर्गा सूक्तम्", "ॐ जातवेदसे सुनवाम सोमम्... माँ दुर्गा से बाधाओं को दूर करने और सुरक्षित मार्ग प्रदान करने के लिए एक शक्तिशाली प्रार्थना।"),
            Pair("महामृत्युंजय मंत्र", "ॐ त्र्यम्बकं यजामहे... स्वास्थ्य और दीर्घायु के लिए भगवान शिव का महान मृत्यु-विजयी मंत्र।"),
            Pair("शिव तांडव स्तोत्र", "जटाटवीगलज्जल प्रवाहपावितस्थले... भगवान शिव के लौकिक नृत्य का वर्णन करने वाला एक शानदार भजन, जो ऊर्जा और शक्ति का आह्वान करता है।"),
            Pair("विष्णु सहस्रनाम", "शुक्लाम्बरधरं विष्णुं... भगवान विष्णु के हजार नाम, जो मन में शांति, समृद्धि और पवित्रता लाते हैं।")
        )'''

content = re.sub(r'val similar = if \(isEnglish\) listOf\(.*?    \)', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
