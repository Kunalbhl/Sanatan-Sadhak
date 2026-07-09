import re
with open("app/src/main/AndroidManifest.xml", "r") as f:
    content = f.read()

content = content.replace('<application', '<uses-permission android:name="android.permission.INTERNET" />\n    <application')

with open("app/src/main/AndroidManifest.xml", "w") as f:
    f.write(content)
