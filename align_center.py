with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    text = f.read()

target = """                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)"""

replacement = """                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(text)

