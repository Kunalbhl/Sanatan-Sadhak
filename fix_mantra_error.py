import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

content = content.replace("androidx.compose.material.icons.filled.Stop", "androidx.compose.material.icons.filled.Close")
content = content.replace("Icons.Default.Stop", "Icons.Default.Close")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
