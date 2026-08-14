import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# Fix curatedPlaylistVideos
text = re.sub(
    r'(\s*allVideos\.take\(6\)\s*\})\.stateIn\(viewModelScope, SharingStarted\.WhileSubscribed\(5000\), emptyList\(\)\)(\s*fun getCuratedPlaylistTitle)',
    r'\1\n    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())\2',
    text
)

# And emptyList() error for videos and articles
text = re.sub(
    r'(\}\.stateIn\(viewModelScope, SharingStarted\.WhileSubscribed\(5000\), )emptyList\(\)(\)\s*// 2\. Videos)',
    r'\1emptyList<KnowledgeArticle>()\2',
    text
)

text = re.sub(
    r'(\}\.stateIn\(viewModelScope, SharingStarted\.WhileSubscribed\(5000\), )emptyList\(\)(\)\s*// Curated dynamic playlist)',
    r'\1emptyList<BhaktiVideo>()\2',
    text
)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)

print(text.count('{'), text.count('}'))
