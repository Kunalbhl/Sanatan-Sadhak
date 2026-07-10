with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

vm_code = """
    val sankalpaLogs: StateFlow<List<com.example.data.SankalpaLog>> = repository.getSankalpaLogs()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
        
    fun addSankalpaLog(intention: String, progressNote: String, progressValue: Int, date: String) {
        viewModelScope.launch {
            repository.addSankalpaLog(com.example.data.SankalpaLog(intention = intention, progressNote = progressNote, progressValue = progressValue, date = date))
        }
    }
"""

if "sankalpaLogs" not in text:
    text = text.replace("    val karmaLogs: StateFlow<List<com.example.data.KarmaLog>>", vm_code + "\n    val karmaLogs: StateFlow<List<com.example.data.KarmaLog>>")
    
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(text)
