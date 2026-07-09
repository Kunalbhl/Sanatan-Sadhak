with open("app/src/main/java/com/example/data/AppDatabase.kt", "r") as f:
    content = f.read()

bad_query = """@Query("UPDATE users SET fullName = :fullName, mobileNumber = :mobileNumber, city = :city, state = :state, avatar = :avatar WHERE email = :email")
    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int)"""

good_query = """@Query("UPDATE users SET fullName = :fullName, mobileNumber = :mobileNumber, city = :city, state = :state, avatar = :avatar, profileImageUri = :profileImageUri WHERE email = :email")
    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, profileImageUri: String)"""

content = content.replace(bad_query, good_query)
content = content.replace("version = 3,", "version = 4,")

with open("app/src/main/java/com/example/data/AppDatabase.kt", "w") as f:
    f.write(content)
