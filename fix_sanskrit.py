import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                        if (isTtsReady) {"""

replacement = """                        val parts = currentThought.split("\\n")
                        val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
                        if (isTtsReady) {"""

if target in text:
    text = text.replace(target, replacement, 1) # only replace the first occurrence
    print("Fixed sanskrit")
else:
    print("Sanskrit target not found")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

