with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

bad = """    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int) {"""
good = """    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {"""

content = content.replace(bad, good)

# Also there was a bug where I used `updateUserDetails` which is not suspend? Let's just fix updateUserProfile.

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
