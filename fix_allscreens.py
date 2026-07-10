with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

text = text.replace("package com.example.ui.screensimport com.example.SanatanSadhakLogoimport", "package com.example.ui.screens\nimport com.example.SanatanSadhakLogo\nimport")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

