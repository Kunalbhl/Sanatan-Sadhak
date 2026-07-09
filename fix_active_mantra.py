import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = content.replace("var activePlayingMantraTitle by remember { mutableStateOf<String?>(null) }", "var activePlayingMantraId by remember { mutableStateOf<String?>(null) }")
content = content.replace("var activePlayingMantraContent by remember { mutableStateOf<String?>(null) }", "")
content = content.replace("activePlayingMantraTitle = if (isEnglish) p.titleEn else p.titleHi\n                                        activePlayingMantraContent = if (isEnglish) p.contentEn else p.contentHi", "activePlayingMantraId = p.id")

replace_if = """    if (activePlayingMantraId != null) {
        val prayerData = com.example.ui.viewmodel.allPrayersList.find { it.id == activePlayingMantraId }
        if (prayerData != null) {
            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEng,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id }
            )
        }
        return
    }"""
content = re.sub(r'    if \(activePlayingMantraTitle != null.*?return\s*\}', replace_if, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
