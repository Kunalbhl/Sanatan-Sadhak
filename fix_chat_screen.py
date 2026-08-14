import re

with open('app/src/main/java/com/example/ui/screens/ChatScreen.kt', 'r') as f:
    content = f.read()

content = content.replace('fun ChatScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, isEng: Boolean) {', 'fun ChatScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, aiViewModel: com.example.ui.viewmodel.AiMitraViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), isEng: Boolean) {')

content = content.replace('viewModel.chatMessages.collectAsState', 'aiViewModel.chatMessages.collectAsState')
content = content.replace('viewModel.isChatLoading.collectAsState', 'aiViewModel.isChatLoading.collectAsState')
content = content.replace('viewModel.sendMessage(currentInput)', 'aiViewModel.sendMessage(currentInput, viewModel.userRole.value)')
content = content.replace('viewModel.resetChat()', 'aiViewModel.resetChat()')
content = content.replace('viewModel.sendChatPrompt', 'aiViewModel.sendMessage') # just in case

with open('app/src/main/java/com/example/ui/screens/ChatScreen.kt', 'w') as f:
    f.write(content)
