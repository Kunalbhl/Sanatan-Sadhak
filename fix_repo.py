with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

bad = "userDao.updateUserProfile(email.lowercase().trim(), fullName, mobileNumber, city, state, avatar)"
good = "userDao.updateUserProfile(email.lowercase().trim(), fullName, mobileNumber, city, state, avatar, imageUri)"

content = content.replace(bad, good)

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
