import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for i, line in enumerate(lines):
    new_lines.append(line)
    # Fix posts
    if "sortedList.sortedWith(compareByDescending { it.isPinned })" in line:
        new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
    # Fix activeComments
    if "repository.getComments(id ?: -1)" in line:
        new_lines.append("    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
    # Fix simple missing stateIns for others
    if line.strip() == "val allComments: StateFlow<List<PostComment>> = repository.getAllComments()":
        new_lines[-1] = line.replace('()', '().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if line.strip() == "val karmaLogs: StateFlow<List<KarmaLog>> = repository.getKarmaLogs()":
        new_lines[-1] = line.replace('()', '().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if line.strip() == "val sankalpaLogs: StateFlow<List<com.example.data.SankalpaLog>> = repository.getSankalpaLogs()":
        new_lines[-1] = line.replace('()', '().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
