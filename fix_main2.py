import re
with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    text = f.read()

text = text.replace("import com.example.ui.screens.*\npackage com.example\n", "package com.example\nimport com.example.ui.screens.*\n")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(text)

