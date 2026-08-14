import re
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Replace getDetailedPanchangForDate
replacement1 = '''fun getDetailedPanchangForDate(day: Int, month: Int, year: Int, isEng: Boolean, callback: (DetailedPanchang?) -> Unit) {
        viewModelScope.launch {
            try {
                // AstrologyService.fetchRealPanchang expects 1-indexed month (1 for Jan, 12 for Dec).
                // But the UI calendar passes month 0-11. We need to pass month + 1
                val realPanchang = com.example.data.AstrologyService.fetchRealPanchang(day, month + 1, year, 6, 0, 0)
                if (realPanchang.data != null) {
                    callback(realPanchang.data)
                } else {
                    callback(null)
                }
            } catch (e: Exception) {
                callback(null)
            }
        }
    }'''

text = re.sub(r'fun getDetailedPanchangForDate.*?\}\s*\}\s*\}', replacement1, text, flags=re.DOTALL)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
