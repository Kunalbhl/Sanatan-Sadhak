import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    text = f.read()

text = re.sub(r"import com\.example\.ui\.screens\..+\n", "", text)
text = "import com.example.ui.screens.*\n" + text

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(text)
print("Imports replaced")
