with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    text = f.read()

text = text.replace("prayerData.sanskritOrHindi", "prayerData.contentHi")

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(text)
