import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Replace _isEnglish definition
content = re.sub(
    r'private val _isEnglish = MutableStateFlow\(true\)\s*val isEnglish: StateFlow<Boolean> = _isEnglish',
    r'val isEnglish: StateFlow<Boolean> = com.example.data.AppPreferences.isEnglish',
    content
)

# Replace _isEnglish references with AppPreferences.isEnglish
content = content.replace('_isEnglish.value', 'com.example.data.AppPreferences.isEnglish.value')
content = content.replace('_isEnglish', 'com.example.data.AppPreferences.isEnglish')

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
