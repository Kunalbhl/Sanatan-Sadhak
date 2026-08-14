with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for i, line in enumerate(lines):
    if "// Curated dynamic playlist based on current day of the week or active festival" in line:
        prev = new_lines[-1]
        if prev.strip() == "}":
            new_lines[-1] = "        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n"
    new_lines.append(line)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
