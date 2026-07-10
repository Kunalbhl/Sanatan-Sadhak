import re
with open("app/src/main/java/com/example/data/Models.kt", "r") as f:
    text = f.read()

text = text.replace("data class SankalpaLog(", "@Entity(tableName = \"sankalpa_logs\")\ndata class SankalpaLog(")

with open("app/src/main/java/com/example/data/Models.kt", "w") as f:
    f.write(text)
