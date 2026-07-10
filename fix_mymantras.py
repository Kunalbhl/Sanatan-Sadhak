with open("app/src/main/java/com/example/ui/screens/MyMantrasView.kt", "r") as f:
    text = f.read()

text = text.replace("prayer.youtubeId", "prayer.youtubeUrl")

with open("app/src/main/java/com/example/ui/screens/MyMantrasView.kt", "w") as f:
    f.write(text)
