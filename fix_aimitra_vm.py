import re

with open('app/src/main/java/com/example/ui/viewmodel/AiMitraViewModel.kt', 'r') as f:
    content = f.read()

content = content.replace('import kotlinx.coroutines.flow.StateFlow', 'import kotlinx.coroutines.flow.StateFlow\nimport kotlinx.coroutines.flow.stateIn\nimport kotlinx.coroutines.flow.SharingStarted')

content = content.replace('val chatMessages: StateFlow<List<ChatMessage>> = repository.getChatMessages()', 'val chatMessages: StateFlow<List<ChatMessage>> = repository.getChatMessages().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')

with open('app/src/main/java/com/example/ui/viewmodel/AiMitraViewModel.kt', 'w') as f:
    f.write(content)
