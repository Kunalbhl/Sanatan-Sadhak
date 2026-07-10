with open("app/src/main/java/com/example/ui/screens/CounterView.kt", "r") as f:
    text = f.read()

target = """                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)"""

replacement = """                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/screens/CounterView.kt", "w") as f:
    f.write(text)

