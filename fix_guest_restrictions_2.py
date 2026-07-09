import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

replacement = """    fun registerGuestAction(): Boolean {
        if (userRole.value == "Guest") {
            if (_guestActionCount.value >= 10) {
                _showLoginWall.value = true
                return false
            }
            _guestActionCount.value += 1
        }
        return true
    }

    fun requireLoginAction(): Boolean {
        if (userRole.value == "Guest") {
            _showLoginWall.value = true
            return false
        }
        return true
    }"""

content = re.sub(r'fun registerGuestAction\(\): Boolean \{.*?\n    \}', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
