with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    text = f.read()

text = text.replace("abstract fun chatMessageDao(): ChatMessageDao", "abstract fun chatMessageDao(): ChatMessageDao\n    abstract fun sankalpaDao(): SankalpaDao")

with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
    f.write(text)
