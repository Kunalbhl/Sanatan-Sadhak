import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Remove the block defining dailyMantra, shlokaOfTheDay, etc.
content = re.sub(r'private val _dailyMantra = MutableStateFlow\([\s\S]*?val dailyMantra: StateFlow<DailyMantra> = _dailyMantra', '', content)
content = re.sub(r'private val _shlokaOfTheDay = MutableStateFlow\([\s\S]*?val shlokaOfTheDay: StateFlow<Shloka> = _shlokaOfTheDay', '', content)
content = re.sub(r'private val _multipleShlokas.*?\n\s*val multipleShlokas: StateFlow.*?\n', '', content)
content = re.sub(r'private val _multipleThoughts.*?\n\s*val multipleThoughts: StateFlow.*?\n', '', content)
content = re.sub(r'private val _multipleInspirations.*?\n\s*val multipleInspirations: StateFlow.*?\n', '', content)

# Remove their initializations in init block
content = re.sub(r'_multipleShlokas\.value = listOf\([\s\S]*?\n\s*\)\s*\n', '', content)
content = re.sub(r'_multipleThoughts\.value = listOf\([\s\S]*?\n\s*\)\s*\n', '', content)
content = re.sub(r'_multipleInspirations\.value = listOf\([\s\S]*?\n\s*\)\s*\n', '', content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)

