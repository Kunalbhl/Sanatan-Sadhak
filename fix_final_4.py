import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

lines = text.split('\n')
new_lines = []

i = 0
while i < len(lines):
    line = lines[i]
    stripped = line.strip()
    if stripped == "}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())":
        # Look ahead for up to 3 lines
        found_videos = False
        found_playlist = False
        for j in range(1, 4):
            if i + j < len(lines):
                if "2. Videos" in lines[i+j]:
                    found_videos = True
                elif "Curated dynamic playlist" in lines[i+j]:
                    found_playlist = True
        
        if found_videos:
            new_lines.append("        }")
            new_lines.append("    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<KnowledgeArticle>())")
        elif found_playlist:
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
