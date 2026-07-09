import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

if "import androidx.compose.material.icons.filled.Edit" not in content:
    content = content.replace("import androidx.compose.material.icons.filled.Favorite", "import androidx.compose.material.icons.filled.Favorite\nimport androidx.compose.material.icons.filled.Edit")
else:
    # If Favorite is not there, just put it anywhere at the top
    pass

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

