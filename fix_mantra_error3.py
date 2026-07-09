import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

content = content.replace("androidx.compose.material.icons.filled.Clear", "androidx.compose.material.icons.filled.Close")
content = content.replace("Icons.Default.Clear", "Icons.Default.Close")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
