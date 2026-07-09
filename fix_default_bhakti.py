import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

replacement = """    fun setScreen(screen: String) {
        if (screen == "Knowledge" || screen == "Community" || screen == "Bhakti" || screen == "Videos") {
            if (!registerGuestAction()) return
        }
        if (screen == "Bhakti") {
            _bhaktiTab.value = "Mantras"
        }
        _currentScreen.value = screen
    }"""

content = re.sub(r'fun setScreen\(screen: String\) \{.*?\n    \}', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
