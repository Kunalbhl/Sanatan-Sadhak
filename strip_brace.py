import re

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'r') as f:
    text = f.read()

# I added 2 braces so I should strip 2 trailing ones.
text = re.sub(r'\}\s*\}\s*$', '', text)
text += '\n'

with open('app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt', 'w') as f:
    f.write(text)
