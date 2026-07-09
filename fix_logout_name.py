import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = content.replace('Text(text = if (isEng) "Logout" else "लॉग आउट करें", color = Color.White)', 'Text(text = if (isEng) "Logout from Temple" else "मंदिर से प्रस्थान करें", color = Color.White)')

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
