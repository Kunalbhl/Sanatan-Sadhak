import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# We will remove the Shloka and Mantra shuffling logic, but keep listEn and listHi
# because they are used for _thoughtEn and _thoughtHi.

start_shloka = "// 1. Select random Shloka of the day"
end_shloka = "val listEn = listOf("

# Find the block and replace it with just "val listEn = listOf("
content = re.sub(r'// 1\. Select random Shloka of the day[\s\S]*?val listEn = listOf\(', 'val listEn = listOf(', content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
