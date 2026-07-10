import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {"""

replacement = """                    val parts = currentThought.split("\\n")
                    val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {"""

# Also remove the one we just added inside Row
target2 = """                        val parts = currentThought.split("\\n")
                        val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought"""

if target in text:
    text = text.replace(target, replacement, 1) # first occurrence is the Shloka widget
if target2 in text:
    text = text.replace(target2, "")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

