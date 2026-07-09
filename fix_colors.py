import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Replace hardcoded colors with MaterialTheme colors
content = content.replace("SoftGoldBg", "MaterialTheme.colorScheme.surfaceVariant")
content = content.replace("LightThoughtBg", "MaterialTheme.colorScheme.primaryContainer")
content = content.replace("WarmBorder", "MaterialTheme.colorScheme.outline")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()
content = content.replace("WarmBorder", "MaterialTheme.colorScheme.outline")
with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()
content = content.replace("SoftGoldBg", "MaterialTheme.colorScheme.surfaceVariant")
with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)

