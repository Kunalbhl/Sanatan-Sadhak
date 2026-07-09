import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

content = content.replace("import Icons.Default.Close", "import androidx.compose.material.icons.filled.Close")
content = content.replace("import Icons.Default.PlayArrow", "import androidx.compose.material.icons.filled.PlayArrow")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
