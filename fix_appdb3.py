with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    text = f.read()

text = text.replace("entities = [", "entities = [\n        SankalpaLog::class,")

with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
    f.write(text)
