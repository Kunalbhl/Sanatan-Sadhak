import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

auth_logic = """
    fun signUp(email: String, pass: String, name: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val newUser = com.example.data.User(email, name, "", "", "", pass, 1, "Devotee")
            repository.loginAsUser(newUser)
            isLoading.value = false
            onResult(true, "")
        }
    }

    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val user = com.example.data.User(email, "Devotee Name", "", "", "", pass, 1, "Devotee")
            repository.loginAsUser(user)
            isLoading.value = false
            onResult(true, "")
        }
    }

    val chatMessages: StateFlow<List<com.example.data.ChatMessage>> = repository.getChatMessages().stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000), emptyList())

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            _isChatLoading.value = true
            val userMsg = com.example.data.ChatMessage(sender = "user", content = prompt)
            repository.addChatMessage(userMsg)
            
            val currentHistory = chatMessages.value
            val reply = com.example.data.GeminiService.generateResponse(currentHistory, prompt)
            
            val modelMsg = com.example.data.ChatMessage(sender = "gemini", content = reply)
            repository.addChatMessage(modelMsg)
            _isChatLoading.value = false
        }
    }
"""

# Replace the incorrect signUp and login
bad_signup = """    fun signUp(email: String, pass: String, name: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val newUser = com.example.data.User(email, name, "Devotee", 1, "", "", "")
            repository.loginAsUser(newUser)
            isLoading.value = false
            onResult(true, "")
        }
    }"""
content = content.replace(bad_signup, "")

bad_login = """    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            kotlinx.coroutines.delay(1000)
            val user = com.example.data.User(email, "Devotee Name", "Devotee", 1, "", "", "")
            repository.loginAsUser(user)
            isLoading.value = false
            onResult(true, "")
        }
    }"""
content = content.replace(bad_login, "")

content = content.rstrip()[:-1] + auth_logic + "\n}"

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
