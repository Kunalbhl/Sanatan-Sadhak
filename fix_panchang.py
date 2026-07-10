with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

target = """    val todayDetailedPanchang = MutableStateFlow<DetailedPanchang?>(null)"""
replacement = """    val todayDetailedPanchang = MutableStateFlow<DetailedPanchang?>(null)
    val panchangError = MutableStateFlow<String?>(null)
    val panchangLastUpdated = MutableStateFlow<String>("")"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
