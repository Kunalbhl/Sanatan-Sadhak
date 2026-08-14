import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

states_to_add = """
    private val _dailyMantra = MutableStateFlow(
        com.example.data.DailyMantra(
            "Om Namah Shivaya",
            "I bow to Lord Shiva. A powerful mantra for inner peace and destruction of negative energies.",
            "ॐ नमः शिवाय"
        )
    )
    val dailyMantra: StateFlow<com.example.data.DailyMantra> = _dailyMantra

    private val _shlokaOfTheDay = MutableStateFlow(
        com.example.data.Shloka(
            "Karmanye Vadhikaraste, Ma phaleshou kada chana",
            "Bhagavad Gita 2.47",
            "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions."
        )
    )
    val shlokaOfTheDay: StateFlow<com.example.data.Shloka> = _shlokaOfTheDay

    private val _multipleShlokas = MutableStateFlow<List<com.example.data.Shloka>>(emptyList())
    val multipleShlokas: StateFlow<List<com.example.data.Shloka>> = _multipleShlokas

    private val _multipleThoughts = MutableStateFlow<List<com.example.data.DailyMantra>>(emptyList())
    val multipleThoughts: StateFlow<List<com.example.data.DailyMantra>> = _multipleThoughts

    private val _multipleInspirations = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val multipleInspirations: StateFlow<List<Pair<String, String>>> = _multipleInspirations

"""

# find insertion point (after isEnglish)
content = re.sub(r'(val isEnglish: StateFlow<Boolean> = com\.example\.data\.AppPreferences\.isEnglish)', r'\1\n' + states_to_add, content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)

