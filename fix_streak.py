import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = "val dailyStreak: StateFlow<Int> = MutableStateFlow(0)"
good = "val dailyStreak: StateFlow<Int> = karmaLogs.map { if (it.isNotEmpty()) it.first().streak else 0 }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)"

content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
