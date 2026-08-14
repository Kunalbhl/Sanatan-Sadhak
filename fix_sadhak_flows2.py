import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    new_line = line
    # Simple getX() replace
    if "repository.getAllUsers()" in new_line and ".stateIn(" not in new_line:
        new_line = new_line.replace('repository.getAllUsers()', 'repository.getAllUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if "repository.getInstagramPosts()" in new_line and ".stateIn(" not in new_line:
        new_line = new_line.replace('repository.getInstagramPosts()', 'repository.getInstagramPosts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if "repository.getThoughtQuotes()" in new_line and ".stateIn(" not in new_line:
        new_line = new_line.replace('repository.getThoughtQuotes()', 'repository.getThoughtQuotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if "repository.getAnnouncements()" in new_line and ".stateIn(" not in new_line:
        new_line = new_line.replace('repository.getAnnouncements()', 'repository.getAnnouncements().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    if "repository.getUserFavorites(userId)" in new_line and "val favoriteMantras: StateFlow" in new_line:
        new_line = new_line.replace('repository.getUserFavorites(userId)', 'repository.getUserFavorites(userId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())')
    
    # Missing end bracket + stateIn
    if "article.copy(isFavorite = favs.any { it.targetId == article.id.toString() })" in new_line:
        new_lines.append(new_line)
        new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
        continue

    if "video.copy(isFavorite = favs.any { it.targetId == video.videoId })" in new_line:
        new_lines.append(new_line)
        new_lines.append("        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\n")
        continue

    new_lines.append(new_line)
    
with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.writelines(new_lines)
