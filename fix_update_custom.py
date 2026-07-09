import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

replacement = """    fun updateCustomThought(en: String, hi: String) {
        val prefs = getApplication<android.app.Application>()
            .getSharedPreferences("panchang_prefs", android.content.Context.MODE_PRIVATE)
        prefs.edit()
            .putString("custom_thought_en", en)
            .putString("custom_thought_hi", hi)
            .apply()
        if (en.isNotEmpty()) _thoughtEn.value = en
        if (hi.isNotEmpty()) _thoughtHi.value = hi
        if (en.isEmpty() && hi.isEmpty()) {
            loadDailyPanchangAndThought()
        }
    }

    fun updateCustomPanchang"""

content = content.replace("    fun updateCustomPanchang", replacement)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
