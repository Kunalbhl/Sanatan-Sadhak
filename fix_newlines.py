def fix_file(filename):
    with open(filename, "r") as f:
        text = f.read()
    
    # Simple replace
    text = text.replace("import ", "\nimport ")
    text = text.replace("package com.example.ui.screens", "package com.example.ui.screens\n\n")
    
    with open(filename, "w") as f:
        f.write(text)

fix_file("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt")
fix_file("app/src/main/java/com/example/ui/screens/MissingScreens.kt")
fix_file("app/src/main/java/com/example/ui/screens/MyMantrasView.kt")
