import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

replacement = """
                val viewModel: SadhakViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                
                val currentScreen by viewModel.currentScreen.collectAsState()
                val isEng by viewModel.isEnglish.collectAsState()
                val themePref by viewModel.themePreference.collectAsState()
                val isDark = when(themePref) {
                    1 -> false
                    2 -> true
                    else -> androidx.compose.foundation.isSystemInDarkTheme()
                }

                MyApplicationTheme(darkTheme = isDark) {
"""

content = content.replace("""            MyApplicationTheme {
                // Initialize modern Viewmodel directly from compose viewmodel library
                val viewModel: SadhakViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                
                val currentScreen by viewModel.currentScreen.collectAsState()
                val isEng by viewModel.isEnglish.collectAsState()""", replacement)

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)
