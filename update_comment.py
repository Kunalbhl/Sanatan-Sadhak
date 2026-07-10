with open("app/src/main/java/com/example/data/Models.kt", "r") as f:
    text = f.read()

target = """data class PostComment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val author: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)"""

replacement = """data class PostComment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val author: String,
    val content: String,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
    val upvotedByEmails: String = "",
    val downvotedByEmails: String = "",
    val createdAt: Long = System.currentTimeMillis()
)"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/data/Models.kt", "w") as f:
    f.write(text)
