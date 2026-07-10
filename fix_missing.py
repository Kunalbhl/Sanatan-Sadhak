with open("app/src/main/java/com/example/ui/screens/MissingScreens.kt", "r") as f:
    text = f.read()

text = text.replace("import androidx.compose.runtime.collectAsState", "")
text = "import androidx.compose.runtime.collectAsState\nimport androidx.compose.runtime.collectAsState\nimport androidx.compose.runtime.getValue\nimport androidx.compose.runtime.setValue\n" + text

with open("app/src/main/java/com/example/ui/screens/MissingScreens.kt", "w") as f:
    f.write(text)
