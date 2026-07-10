with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEng,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id }
            )"""

replacement = """            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEng,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id },
                viewModel = viewModel
            )"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
