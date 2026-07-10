with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

start_idx = text.find("    fun getDetailedPanchangForDate(")
end_idx = text.find("    fun updateCustomThought(", start_idx)

replacement = """    fun getDetailedPanchangForDate(day: Int, month: Int, year: Int, isEng: Boolean, callback: (DetailedPanchang?) -> Unit) {
        viewModelScope.launch {
            val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(day, month + 1, year)
            callback(realPanchang)
        }
    }

"""

if start_idx != -1 and end_idx != -1:
    text = text[:start_idx] + replacement + text[end_idx:]

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)

