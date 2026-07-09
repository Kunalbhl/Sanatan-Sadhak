import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = content.replace("androidx.compose.material.icons.filled.Edit", "Icons.Filled.Edit")
content = content.replace("Icons.Default.Edit", "Icons.Filled.Edit")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
