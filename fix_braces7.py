with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'r') as f:
    text = f.read()

import re
# Replace the very last closing brace in the file
text = re.sub(r'}([^}]*)$', r'\1', text)

with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
    f.write(text)
print("Removed last brace")
