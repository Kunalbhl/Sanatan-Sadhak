with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# For videos
text = text.replace("}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())", "}.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList<BhaktiVideo>())", 2)
# Wait, let's just do it directly.

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
