import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# I will just match all the way from the remaining parts of getMockChatResponse to before toggleLikePost
content = re.sub(r'val p = prompt\.lowercase\(\)[\s\S]*?fun toggleLikePost', 'fun toggleLikePost', content)

# But wait, did it leave `val p = prompt.lowercase()`? Let's check lines 1707 to 1724.
# It says Unresolved reference 'p'.
