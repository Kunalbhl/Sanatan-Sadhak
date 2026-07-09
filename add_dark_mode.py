import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

replacement = """    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }"""

content = content.replace("""    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish""", replacement)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
