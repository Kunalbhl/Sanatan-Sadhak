import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

content = content.replace("androidx.compose.material.icons.filled.Close", "androidx.compose.material.icons.filled.Clear")
content = content.replace("Icons.Default.Close", "Icons.Default.Clear")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
