import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad = "if (apiKey.isNotBlank()) {"
good = 'if (apiKey.isNotBlank() && apiKey != "YOUR_VEDIC_ASTRO_API_KEY") {'

content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
