import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = """                    val thoughtEn = responseJson.optString("thought_en")
                    val thoughtHi = responseJson.optString("thought_hi")
                    if (thoughtEn.isNotBlank() && thoughtHi.isNotBlank()) {
                        _currentThought.value = Pair(thoughtEn, thoughtHi)
                    }"""

good = """                    val thoughtEn = responseJson.optString("thought_en")
                    val thoughtHi = responseJson.optString("thought_hi")
                    if (thoughtEn.isNotBlank() && thoughtHi.isNotBlank()) {
                        _thoughtEn.value = thoughtEn
                        _thoughtHi.value = thoughtHi
                    }"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(content)
    print("Replaced thought assignments")
else:
    print("Not found")
