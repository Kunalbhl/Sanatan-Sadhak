import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

replacement = """    val karmaLogs: StateFlow<List<KarmaLog>> = repository.getKarmaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gratitudeLogs: StateFlow<List<GratitudeLog>> = repository.getGratitudeLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val karmaPoints: StateFlow<Int> = combine(karmaLogs, gratitudeLogs) { k, g ->
        (k.size * 10) + (g.size * 5)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dailyStreak: StateFlow<Int> = MutableStateFlow(0)"""

content = re.sub(r'    val karmaLogs.*?emptyList\(\)\)', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)

