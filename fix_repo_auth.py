import re

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

auth_target = """    suspend fun authenticateUser(identifier: String, passwordText: String): User? {
        val idClean = identifier.lowercase().trim()
        var user = userDao.getUserByEmail(idClean)
        if (user == null) {
            user = userDao.getAllUsersSync().find { it.mobileNumber == idClean }
        }
        if (user != null && user.password == passwordText) {
            return user
        }
        return null
    }"""
    
auth_replacement = """    suspend fun authenticateUser(identifier: String, passwordText: String): User? {
        val idClean = identifier.lowercase().trim()
        var user = userDao.getUserByEmail(idClean)
        if (user == null) {
            user = userDao.getUserByMobile(idClean)
        }
        if (user != null && user.password == passwordText) {
            return user
        }
        return null
    }"""

content = content.replace(auth_target, auth_replacement)

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
