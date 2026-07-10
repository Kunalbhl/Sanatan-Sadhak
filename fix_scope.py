with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target1 = """                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {"""

replacement1 = """                    val parts = currentThought.split("\\n")
                    val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {"""

target2 = """                        val parts = currentThought.split("\\n")
                        val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
"""
replacement2 = ""

text = text.replace(target1, replacement1)
text = text.replace(target2, replacement2)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

