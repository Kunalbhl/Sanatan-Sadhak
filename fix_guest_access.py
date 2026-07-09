import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

# Allow viewing, but block adding
replacement_screen = """    fun setScreen(screen: String) {
        if (screen == "Bhakti") {
            _bhaktiTab.value = "Mantras"
        }
        _currentScreen.value = screen
    }

    fun navigateToBhakti(tab: String) {
        _bhaktiTab.value = tab
        _currentScreen.value = "Bhakti"
    }"""

content = re.sub(r'    fun setScreen.*?fun navigateToBhakti.*?_currentScreen\.value = "Bhakti"\n    \}', replacement_screen, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
