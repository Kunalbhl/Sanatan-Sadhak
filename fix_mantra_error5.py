import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

if "import androidx.compose.material.icons.filled.Close" not in content:
    content = content.replace("import androidx.compose.material.icons.filled.PlayArrow", "import androidx.compose.material.icons.filled.PlayArrow\nimport androidx.compose.material.icons.filled.Close")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
