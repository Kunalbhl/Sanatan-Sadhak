import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

content = content.replace("Icons.AutoMirrored.Filled.Chat", "Icons.Default.Chat")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)


with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content_vm = f.read()

if "import kotlinx.coroutines.flow.map" not in content_vm:
    content_vm = content_vm.replace("import kotlinx.coroutines.flow.MutableStateFlow", "import kotlinx.coroutines.flow.MutableStateFlow\nimport kotlinx.coroutines.flow.map")

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content_vm)
