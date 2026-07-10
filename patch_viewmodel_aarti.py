import sys
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

target = """    private val _aartiLogs = MutableStateFlow<List<Int>>(emptyList())
    val aartiLogs: StateFlow<List<Int>> = _aartiLogs

    fun addAartiLog(count: Int) {
        if (count > 0) {
            val current = _aartiLogs.value.toMutableList()
            current.add(count)
            _aartiLogs.value = current
            // Also log to Karma to update Bhakti Streak
            logKarmaDeed("Aarti Bell Rung $count times")
        }
    }"""

replacement = """    private val _aartiSessionLogs = MutableStateFlow<List<String>>(emptyList())
    val aartiSessionLogs: StateFlow<List<String>> = _aartiSessionLogs
    private val _currentSessionAartiCount = MutableStateFlow(0)
    val currentSessionAartiCount: StateFlow<Int> = _currentSessionAartiCount
    private var sessionStartTimeStr = java.text.SimpleDateFormat("MMM dd, hh:mm a", java.util.Locale.getDefault()).format(System.currentTimeMillis())

    fun incrementAartiBell() {
        _currentSessionAartiCount.value += 1
        val count = _currentSessionAartiCount.value
        val msg = "Session ($sessionStartTimeStr): You rung bell $count times"
        val currentLogs = _aartiSessionLogs.value.toMutableList()
        
        // Remove the log for current session if it exists, to replace it
        currentLogs.removeAll { it.startsWith("Session ($sessionStartTimeStr)") }
        currentLogs.add(msg)
        _aartiSessionLogs.value = currentLogs
    }"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(text)
    print("Patched Aarti Bell logs")
else:
    print("Target not found")
