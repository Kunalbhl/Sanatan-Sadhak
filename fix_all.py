import re

with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    text = f.read()
text = text.replace(".alpha(", ".androidx.compose.ui.draw.alpha(")
text = text.replace(".androidx.compose.ui.draw.alpha(", ".alpha(") # wait
text = text.replace("import androidx.compose.animation.core.animateFloat", "import androidx.compose.animation.core.animateFloat\nimport androidx.compose.ui.draw.alpha\n")
with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(text)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                        if (isTtsReady) {
                            IconButton(
                                onClick = {
                                    tts?.speak(sanskritVerse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
                                }"""

replacement = """                        val parts = currentThought.split("\\n")
                        val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
                        if (isTtsReady) {
                            IconButton(
                                onClick = {
                                    tts?.speak(sanskritVerse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
                                }"""

if target in text:
    text = text.replace(target, replacement)
    print("Fixed sanskrit parts in AllScreens")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
