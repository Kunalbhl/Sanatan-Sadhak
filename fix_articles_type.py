with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

old1 = "        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())\n    // 2. Videos"
new1 = "        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<KnowledgeArticle>())\n    // 2. Videos"
text = text.replace(old1, new1)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
