import re

with open("app/src/main/java/com/example/data/Models.kt", "r") as f:
    content = f.read()

bad_user = """@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val fullName: String,
    val mobileNumber: String,
    val city: String,
    val state: String,
    val password: String,
    val avatar: Int = 1,
    val role: String = "Regular"
)"""

good_user = """@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val fullName: String,
    val mobileNumber: String,
    val city: String,
    val state: String,
    val password: String,
    val avatar: Int = 1,
    val role: String = "Regular",
    val profileImageUri: String = ""
)"""

content = content.replace(bad_user, good_user)

with open("app/src/main/java/com/example/data/Models.kt", "w") as f:
    f.write(content)
