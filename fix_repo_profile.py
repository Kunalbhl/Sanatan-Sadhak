import re

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content = f.read()

avatar_state_bad = """    private val _userAvatarState = MutableStateFlow(1) // Avatar 1-4
    val userAvatarState: StateFlow<Int> = _userAvatarState"""

avatar_state_good = """    private val _userAvatarState = MutableStateFlow(1) // Avatar 1-4
    val userAvatarState: StateFlow<Int> = _userAvatarState

    private val _userProfileImageUriState = MutableStateFlow("")
    val userProfileImageUriState: StateFlow<String> = _userProfileImageUriState"""

update_user_bad = """    fun updateUserDetails(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int) {
        // Find user and update... in a real app, update SQLite/Firebase
        val idx = mockedUsers.indexOfFirst { it.email == email }
        if (idx != -1) {
            val updated = mockedUsers[idx].copy(
                fullName = fullName,
                mobileNumber = mobileNumber,
                city = city,
                state = state,
                avatar = avatar
            )
            mockedUsers[idx] = updated
        }
        
        // Update flows if it's the currently logged-in user
        if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
            _userNameState.value = fullName
            _userMobileState.value = mobileNumber
            _userCityState.value = city
            _userStateProvinceState.value = state
            _userAvatarState.value = avatar
        }
    }"""

update_user_good = """    fun updateUserDetails(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {
        val idx = mockedUsers.indexOfFirst { it.email == email }
        if (idx != -1) {
            val updated = mockedUsers[idx].copy(
                fullName = fullName,
                mobileNumber = mobileNumber,
                city = city,
                state = state,
                avatar = avatar
            )
            mockedUsers[idx] = updated
        }
        
        if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
            _userNameState.value = fullName
            _userMobileState.value = mobileNumber
            _userCityState.value = city
            _userStateProvinceState.value = state
            _userAvatarState.value = avatar
            _userProfileImageUriState.value = imageUri
        }
    }"""

login_bad = """    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        _userMobileState.value = user.mobileNumber
        _userCityState.value = user.city
        _userStateProvinceState.value = user.state
    }"""

login_good = """    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        _userMobileState.value = user.mobileNumber
        _userCityState.value = user.city
        _userStateProvinceState.value = user.state
        // imageUri would be loaded from persistence in real app
    }"""
    
logout_bad = """    fun logout() {
        _userEmailState.value = ""
        _userNameState.value = "Guest Sadhak"
        _userRoleState.value = "Guest"
        _userAvatarState.value = 1
        _userMobileState.value = ""
        _userCityState.value = ""
        _userStateProvinceState.value = ""
        firebaseService.signOut()
    }"""

logout_good = """    fun logout() {
        _userEmailState.value = ""
        _userNameState.value = "Guest Sadhak"
        _userRoleState.value = "Guest"
        _userAvatarState.value = 1
        _userProfileImageUriState.value = ""
        _userMobileState.value = ""
        _userCityState.value = ""
        _userStateProvinceState.value = ""
        firebaseService.signOut()
    }"""

content = content.replace(avatar_state_bad, avatar_state_good)
content = content.replace(update_user_bad, update_user_good)
content = content.replace(login_bad, login_good)
content = content.replace(logout_bad, logout_good)

with open("app/src/main/java/com/example/data/SadhakRepository.kt", "w") as f:
    f.write(content)
