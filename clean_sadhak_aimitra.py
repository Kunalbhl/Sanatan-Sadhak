import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# Remove the two chat functions and getMockChatResponse
content = re.sub(r'// --- GEMINI CHAT ACTION ---[\s\S]*?fun resetChat\(\) \{[\s\S]*?\}\s*\}', '', content)
content = re.sub(r'fun sendMessage\(prompt: String\) \{[\s\S]*?\}\s*\}', '', content)
content = re.sub(r'private fun getMockChatResponse\([\s\S]*?\}\s*\}\s*\}', '', content)
content = re.sub(r'private val _isChatLoading = MutableStateFlow\(false\)[\s\S]*?val isChatLoading: StateFlow<Boolean> = _isChatLoading', '', content)
content = re.sub(r'// 7\. Chatbot messages[\s\S]*?val chatMessages: StateFlow<List<ChatMessage>> = repository\.getChatMessages\(\)', '', content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
