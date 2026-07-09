import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

bad = """    LaunchedEffect(isPlaying, prayerData.id) {
        if (isPlaying) {
            while (isPlaying) {
                kotlinx.coroutines.delay(1000)
                progress += 0.02f
                if (progress >= 1f) progress = 0f
            }
        }
    }"""

good = """    LaunchedEffect(isPlaying, prayerData.id) {
        if (isPlaying) {
            while (isPlaying) {
                kotlinx.coroutines.delay(1000)
                progress += 0.02f
                if (progress >= 1f) progress = 0f
            }
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            com.example.ui.components.AudioEngine.stopAmbientSound()
        }
    }"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
        f.write(content)
    print("Replaced Mantra Cleanup")
else:
    print("Not found")
