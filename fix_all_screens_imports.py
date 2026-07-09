import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

if "import androidx.compose.runtime.DisposableEffect" not in content:
    content = content.replace("import androidx.compose.runtime.Composable", "import androidx.compose.runtime.Composable\nimport androidx.compose.runtime.DisposableEffect")

content = content.replace("Icons.Default.Edit", "androidx.compose.material.icons.filled.Edit")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
