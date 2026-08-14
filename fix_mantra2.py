import re
with open('app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt', 'r') as f:
    text = f.read()

text = re.sub(
    r'LaunchedEffect\(isPlaying, prayerData\.id\) \{\s*if \(isPlaying\) \{\s*while \(isPlaying\) \{\s*kotlinx\.coroutines\.delay\(1000\)\s*progress \+= 0\.02f\s*if \(progress >= 1f\) progress = 0f\s*\}\s*\}\s*\}',
    '',
    text
)

with open('app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt', 'w') as f:
    f.write(text)
