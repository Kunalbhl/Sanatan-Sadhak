import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    new_line = line
    
    # 820 FavoriteMantra
    if "val favoriteMantras: StateFlow<List<FavoriteMantra>> =" in new_line and ".stateIn(" not in new_line:
        new_line = new_line.replace('repository.getUserFavorites(userId)', 'repository.getUserFavorites(userId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    
    # 981 KnowledgeArticle
    if "repository.getKnowledgeArticles(cat)" in new_line:
        new_lines.append(new_line)
        new_lines.append("    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
        continue

    # 1016 & 1028 BhaktiVideo
    if "repository.getBhaktiVideos(cat)" in new_line:
        new_lines.append(new_line)
        new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
        continue
    
    if "sortedList.sortedWith(compareByDescending { it.views })" in new_line:
        new_lines.append(new_line)
        new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
        continue

    new_lines.append(new_line)

# Add missing braces at the end
# The script previously found open: 339, close: 334. We need to add 5 closing braces.
for i in range(5):
    new_lines.append("}\n")

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
