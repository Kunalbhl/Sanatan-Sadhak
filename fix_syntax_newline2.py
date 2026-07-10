import os

def fix_pkg(filename):
    with open(filename, "r") as f:
        text = f.read()
    
    text = text.replace("package com.example.ui.screensimport", "package com.example.ui.screens\n\nimport")
    
    with open(filename, "w") as f:
        f.write(text)

fix_pkg("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt")
fix_pkg("app/src/main/java/com/example/ui/screens/MissingScreens.kt")
