import re

files = ["app/src/main/java/com/example/ui/screens/AllScreens.kt", "app/src/main/java/com/example/MainActivity.kt", "app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt"]

for file in files:
    with open(file, "r") as f:
        content = f.read()
    
    content = re.sub(r'import com\.example\.ui\.theme\.MaterialTheme\..*\n', '', content)
    content = content.replace('import com.example.ui.viewmodel.allPrayersList', 'import com.example.ui.viewmodel.PrayerData\nimport com.example.ui.viewmodel.allPrayersList')
    with open(file, "w") as f:
        f.write(content)
