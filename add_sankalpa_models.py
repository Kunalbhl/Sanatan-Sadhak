with open("app/src/main/java/com/example/data/Models.kt", "r") as f:
    text = f.read()

replacement = """@Entity(tableName = "sankalpa_logs")
data class SankalpaLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val intention: String,
    val progressNote: String,
    val progressValue: Int, // e.g., 0-100 or number of rounds
    val date: String, // yyyy-MM-dd
    val timestamp: Long = System.currentTimeMillis()
)
"""

if "SankalpaLog" not in text:
    with open("app/src/main/java/com/example/data/Models.kt", "a") as f:
        f.write("\n" + replacement)
