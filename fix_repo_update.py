import re

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

bad_update = """    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int) {
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            val updatedUser = user.copy(
                fullName = fullName,
                mobileNumber = mobileNumber,
                city = city,
                state = state,
                avatar = avatar
            )
            userDao.updateUser(updatedUser)
            // If updating current user, sync flow
            if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
                _userNameState.value = fullName
                _userMobileState.value = mobileNumber
                _userCityState.value = city
                _userStateProvinceState.value = state
                _userAvatarState.value = avatar
            }
        }
    }"""

good_update = """    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {
        val user = userDao.getUserByEmail(email)
        if (user != null) {
            val updatedUser = user.copy(
                fullName = fullName,
                mobileNumber = mobileNumber,
                city = city,
                state = state,
                avatar = avatar
            )
            userDao.updateUser(updatedUser)
            // If updating current user, sync flow
            if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
                _userNameState.value = fullName
                _userMobileState.value = mobileNumber
                _userCityState.value = city
                _userStateProvinceState.value = state
                _userAvatarState.value = avatar
                _userProfileImageUriState.value = imageUri
            }
        }
    }"""

content = content.replace(bad_update, good_update)

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
