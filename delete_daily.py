import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Delete lines from _dailyMantra up to multipleInspirations
pattern = r'    private val _dailyMantra = MutableStateFlow\(.*?val multipleInspirations: StateFlow<List<Pair<String, String>>> = _multipleInspirations'
text = re.sub(pattern, '', text, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
