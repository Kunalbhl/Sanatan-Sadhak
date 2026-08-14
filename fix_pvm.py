import re

with open('app/src/main/java/com/example/ui/viewmodel/PanchangViewModel.kt', 'r') as f:
    content = f.read()

content = content.replace('import kotlinx.coroutines.flow.combine', 'import kotlinx.coroutines.flow.combine\nimport kotlinx.coroutines.flow.stateIn\nimport kotlinx.coroutines.flow.SharingStarted')

content = content.replace('.kotlinx.coroutines.flow.stateIn', '.stateIn')
content = content.replace('kotlinx.coroutines.flow.SharingStarted.WhileSubscribed', 'SharingStarted.WhileSubscribed')

with open('app/src/main/java/com/example/ui/viewmodel/PanchangViewModel.kt', 'w') as f:
    f.write(content)
