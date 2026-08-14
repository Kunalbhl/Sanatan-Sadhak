import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# _isDarkMode
content = re.sub(
    r'private val _isDarkMode = MutableStateFlow\(false\)\s*val isDarkMode: StateFlow<Boolean> = _isDarkMode',
    r'val isDarkMode: StateFlow<Boolean> = com.example.data.AppPreferences.isDarkMode',
    content
)
content = content.replace('_isDarkMode.value = !_isDarkMode.value', 'com.example.data.AppPreferences.toggleDarkMode()')

# _themePreference
content = re.sub(
    r'private val _themePreference = MutableStateFlow\(1\)\s*val themePreference: StateFlow<Int> = _themePreference',
    r'val themePreference: StateFlow<Int> = com.example.data.AppPreferences.themePreference',
    content
)
content = content.replace('_themePreference.value = pref', 'com.example.data.AppPreferences.setThemePreference(pref)')

# _bgChantEnabled
content = re.sub(
    r'private val _bgChantEnabled = MutableStateFlow\(false\)\s*val bgChantEnabled: StateFlow<Boolean> = _bgChantEnabled',
    r'val bgChantEnabled: StateFlow<Boolean> = com.example.data.AppPreferences.bgChantEnabled',
    content
)
content = content.replace('_bgChantEnabled.value = !_bgChantEnabled.value', 'com.example.data.AppPreferences.toggleBgChant()')

# _bgChantVolume
content = re.sub(
    r'private val _bgChantVolume = MutableStateFlow\(0.3f\)\s*val bgChantVolume: StateFlow<Float> = _bgChantVolume',
    r'val bgChantVolume: StateFlow<Float> = com.example.data.AppPreferences.bgChantVolume',
    content
)
content = content.replace('_bgChantVolume.value = vol', 'com.example.data.AppPreferences.setBgChantVolume(vol)')

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
