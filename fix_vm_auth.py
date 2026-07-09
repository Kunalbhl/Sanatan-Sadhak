import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

auth_logic = """
    val isLoading = MutableStateFlow(false)

    fun signUp(email: String, pass: String, name: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val newUser = com.example.data.User(email, name, "Devotee", 1, "", "", "")
            repository.loginAsUser(newUser)
            isLoading.value = false
            onResult(true, "")
        }
    }

    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val user = com.example.data.User(email, "Devotee Name", "Devotee", 1, "", "", "")
            repository.loginAsUser(user)
            isLoading.value = false
            onResult(true, "")
        }
    }
"""

if "fun signUp(" not in content:
    # insert before the last brace
    content = content.rstrip()[:-1] + auth_logic + "\n}"

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
