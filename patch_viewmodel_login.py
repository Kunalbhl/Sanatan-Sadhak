import sys
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

target = """            _isAdmin.value = finalUser.role == "SuperUser" || finalUser.role == "Admin"
            _canUserPost.value = finalUser.canPost"""

replacement = """            _isAdmin.value = finalUser.role == "SuperUser" || finalUser.role == "Admin"
            _canUserPost.value = finalUser.canPost
            sessionStartTimeStr = java.text.SimpleDateFormat("MMM dd, hh:mm a", java.util.Locale.getDefault()).format(System.currentTimeMillis())
            _currentSessionAartiCount.value = 0"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(text)
    print("Patched Login logic for session")
else:
    print("Target not found for login")
