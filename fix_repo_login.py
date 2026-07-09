with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

bad = """    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        _userMobileState.value = user.mobileNumber
        _userCityState.value = user.city
        _userStateProvinceState.value = user.state
    }"""

good = """    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        _userMobileState.value = user.mobileNumber
        _userCityState.value = user.city
        _userStateProvinceState.value = user.state
        _userProfileImageUriState.value = user.profileImageUri
    }"""

content = content.replace(bad, good)

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
