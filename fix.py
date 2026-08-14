import re

with open('app/src/main/java/com/example/MainActivity.kt', 'r') as f:
    content = f.read()

# Fix the duplicate onDestroy
content = re.sub(r"    override fun onDestroy\(\) \{\n        super.onDestroy\(\)\n        com.example.ui.components.AudioEngine.releaseAll\(\)\n    \}\n    override fun onDestroy\(\) \{\n        super.onDestroy\(\)\n        com.example.ui.components.AudioEngine.releaseAll\(\)\n    \}", r"    override fun onDestroy() {\n        super.onDestroy()\n        com.example.ui.components.AudioEngine.releaseAll()\n    }", content)

# Fix the trailing }
content = content.rstrip()
if content.endswith('}'):
    content = content[:-1].rstrip()

with open('app/src/main/java/com/example/MainActivity.kt', 'w') as f:
    f.write(content)
