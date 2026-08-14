import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# I will use a literal script to rewrite the whole file line by line for these sections
lines = text.split('\n')
new_lines = []

i = 0
while i < len(lines):
    line = lines[i]
    if "    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())" in line:
        if i + 1 < len(lines) and "// 2. Videos (Reactively updates when video category changes)" in lines[i+1]:
            new_lines.append("        }")
            new_lines.append("    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<KnowledgeArticle>())")
        else:
            new_lines.append(line)
    elif "        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())" in line:
        if i + 1 < len(lines) and "// Curated dynamic playlist based on current day of the week or active festival" in lines[i+1]:
            new_lines.append("            }")
            new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())")
        else:
            new_lines.append(line)
    else:
        new_lines.append(line)
    i += 1

text = '\n'.join(new_lines)
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
