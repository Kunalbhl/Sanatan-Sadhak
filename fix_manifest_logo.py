import re

with open("app/src/main/AndroidManifest.xml", "r") as f:
    content = f.read()

content = content.replace('android:icon="@mipmap/ic_launcher"', 'android:icon="@drawable/app_logo"')
content = content.replace('android:roundIcon="@mipmap/ic_launcher_round"', 'android:roundIcon="@drawable/app_logo"')

with open("app/src/main/AndroidManifest.xml", "w") as f:
    f.write(content)
