with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    text = f.read()

text = text.replace("youtubeUrl = prayerData.youtubeUrl", "youtubeUrl = prayerData.youtubeId")
with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
    f.write(text)

with open("app/src/main/java/com/example/ui/screens/MyMantrasView.kt", "r") as f:
    text = f.read()

text = text.replace("prayer.youtubeUrl", "prayer.youtubeId")
with open("app/src/main/java/com/example/ui/screens/MyMantrasView.kt", "w") as f:
    f.write(text)
