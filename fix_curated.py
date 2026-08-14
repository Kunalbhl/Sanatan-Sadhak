with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for i, line in enumerate(lines):
    if "    fun getCuratedPlaylistTitle(isEng: Boolean): String {" in line:
        if new_lines[-1] == "\n" and new_lines[-2] == "        }\n":
            new_lines[-2] = "        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n"
    new_lines.append(line)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
