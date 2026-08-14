import re

with open('app/src/main/java/com/example/ui/screens/common/CommonScreens.kt', 'r') as f:
    content = f.read()

content = content.replace('fun ChatScreen(viewModel: SadhakViewModel) {', 'fun ChatScreen(viewModel: SadhakViewModel, aiViewModel: com.example.ui.viewmodel.AiMitraViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {')
content = content.replace('aiViewModel.chatMessages.collectAsState(initial = emptyList())', 'aiViewModel.chatMessages.collectAsState()')

with open('app/src/main/java/com/example/ui/screens/common/CommonScreens.kt', 'w') as f:
    f.write(content)
