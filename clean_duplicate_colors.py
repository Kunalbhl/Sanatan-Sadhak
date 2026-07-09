import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = re.sub(r'focusedBorderColor = MaterialTheme\.colorScheme\.primary, \s*focusedBorderColor = MaterialTheme\.colorScheme\.primary,', 'focusedBorderColor = MaterialTheme.colorScheme.primary,', content)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
