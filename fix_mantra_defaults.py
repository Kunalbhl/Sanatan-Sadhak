import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

content = content.replace('var selectedLanguage by remember { mutableStateOf(if (isEnglish) "EN" else "SA") }', 'var selectedLanguage by remember { mutableStateOf("SA") }')

text1 = 'text = if (isEnglish) "Listen with Correct Pronunciation" else "सही उच्चारण के साथ सुनें",'
text2 = 'text = if (isEnglish) "Transcribe Read Aloud / Listen with Correct Pronunciation" else "पढ़ें और सही उच्चारण के साथ सुनें",'
content = content.replace(text1, text2)

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(content)
