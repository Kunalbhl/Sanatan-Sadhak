import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    lines = f.readlines()

new_lines = []
for i, line in enumerate(lines):
    if "val chatMessages: StateFlow<List<com.example.data.ChatMessage>> =" in line and i > 500:
        continue # skip the second declaration
    new_lines.append(line)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.writelines(new_lines)
