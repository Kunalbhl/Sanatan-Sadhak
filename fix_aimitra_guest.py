import re

with open('app/src/main/java/com/example/ui/viewmodel/AiMitraViewModel.kt', 'r') as f:
    content = f.read()

content = content.replace('private val _guestQuestionCount = MutableStateFlow(0)', 'private val _guestQuestionCount = MutableStateFlow(0)\n    val guestQuestionCount: StateFlow<Int> = _guestQuestionCount')

with open('app/src/main/java/com/example/ui/viewmodel/AiMitraViewModel.kt', 'w') as f:
    f.write(content)
