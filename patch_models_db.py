import sys
with open("app/src/main/java/com/example/data/Models.kt", "r") as f:
    text = f.read()

target = """@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Bhakti Experiences, Doubts & Discussions, Daily Karma Journal, Temple Stories
    val author: String,
    val title: String,
    val content: String,
    val upvotes: Int = 1,
    val downvotes: Int = 0,
    val commentsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false
)"""

replacement = """@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Bhakti Experiences, Doubts & Discussions, Daily Karma Journal, Temple Stories
    val author: String,
    val title: String,
    val content: String,
    val upvotes: Int = 1,
    val downvotes: Int = 0,
    val upvotedByEmails: String = "",
    val downvotedByEmails: String = "",
    val commentsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false
)"""

if target in text:
    text = text.replace(target, replacement)
    with open("app/src/main/java/com/example/data/Models.kt", "w") as f:
        f.write(text)
    print("Models.kt updated")
else:
    print("Models.kt target not found")

with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    db_text = f.read()

db_target = "version = 7,"
db_replacement = "version = 8,"
if db_target in db_text:
    db_text = db_text.replace(db_target, db_replacement)
    with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
        f.write(db_text)
    print("AppDatabase.kt updated")
else:
    print("AppDatabase.kt target not found")
