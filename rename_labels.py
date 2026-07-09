import re

# MainActivity.kt
with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    main = f.read()

main = main.replace('NavigationItem("Bhakti", if (isEnglish) "Bhakti" else "साधना", Icons.Default.SelfImprovement)',
                    'NavigationItem("Bhakti", if (isEnglish) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement)')
main = main.replace('NavigationItem("Chat", if (isEnglish) "Guide AI" else "मार्गदर्शक", Icons.Default.Chat)',
                    'NavigationItem("Chat", if (isEnglish) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat)')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(main)

# AllScreens.kt
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    screens = f.read()

screens = screens.replace('Triple(if (isEng) "Knowledge" else "ज्ञान गंगा", Icons.Default.MenuBook, "Knowledge")',
                          'Triple(if (isEng) "Knowledge Hub" else "ज्ञान गंगा", Icons.Default.MenuBook, "Knowledge")')
screens = screens.replace('Triple(if (isEng) "Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement, "Bhakti")',
                          'Triple(if (isEng) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement, "Bhakti")')
screens = screens.replace('Triple(if (isEng) "Sadhak Mitra" else "साधक मित्र AI", Icons.Default.Chat, "Chat")',
                          'Triple(if (isEng) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat, "Chat")')

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(screens)
