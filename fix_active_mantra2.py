import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad_block = """    if (activePlayingMantraId != null) {
        MantraDetailScreen(
            title = activePlayingMantraTitle!!,
            content = activePlayingMantraText,
            isEnglish = isEnglish,
            onBack = { activePlayingMantraId = null },
            onRelatedClick = { t, c -> 
                activePlayingMantraId = null
                activePlayingMantraText = c
            }
        )
    } else {"""

good_block = """    if (activePlayingMantraId != null) {
        val prayerData = com.example.ui.viewmodel.allPrayersList.find { it.id == activePlayingMantraId }
        if (prayerData != null) {
            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEnglish,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id }
            )
        }
        return
    }"""

# I need to be careful with the else brace. If I add return, I don't need else. But wait, if there's an else, replacing `} else {` with `return` means I have a hanging `}` at the end of the file/block.
# Actually, the original code had `if (activePlayingMantraTitle != null) { MantraDetailScreen(...) } else { Column(...) }`.
# Let's replace the whole block up to the else, and let the else become the main column.
