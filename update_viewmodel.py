import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

# Add theme preference state
theme_state = """
    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    // Theme Preference (0 = System, 1 = Light, 2 = Dark)
    private val _themePreference = MutableStateFlow(1)
    val themePreference: StateFlow<Int> = _themePreference
"""
content = content.replace("    private val _isEnglish = MutableStateFlow(true)\n    val isEnglish: StateFlow<Boolean> = _isEnglish", theme_state)

theme_action = """
    fun toggleLanguage() {
        _isEnglish.value = !_isEnglish.value
    }

    fun setThemePreference(pref: Int) {
        _themePreference.value = pref
    }
"""
content = content.replace("    fun toggleLanguage() {\n        _isEnglish.value = !_isEnglish.value\n    }", theme_action)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
