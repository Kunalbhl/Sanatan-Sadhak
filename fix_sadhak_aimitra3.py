import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    content = f.read()

# I will find the leftover `else { return when {` and delete everything up to `fun toggleLikePost`
content = re.sub(r'\} else \{\s*return when \{[\s\S]*?fun toggleLikePost', 'fun toggleLikePost', content)

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(content)
