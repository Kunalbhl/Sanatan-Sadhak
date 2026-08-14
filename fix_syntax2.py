import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Fix articles
text = re.sub(
    r'\s*\}\.stateIn\(viewModelScope, SharingStarted\.WhileSubscribed\(5000\), emptyList<BhaktiVideo>\(\)\)(\s*// 2\. Videos)',
    r'\n    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\1',
    text
)

# Fix videos
text = re.sub(
    r'\s*\}\.stateIn\(viewModelScope, SharingStarted\.WhileSubscribed\(5000\), emptyList<BhaktiVideo>\(\)\)(\s*// Curated dynamic playlist)',
    r'\n        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())\1',
    text
)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
