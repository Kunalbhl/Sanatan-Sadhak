import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

bad_update = """    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isNotEmpty()) {
                repository.updateUserProfile(email, fullName, mobileNumber, city, state, avatar)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }"""

good_update = """    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isNotEmpty()) {
                repository.updateUserDetails(email, fullName, mobileNumber, city, state, avatar, imageUri)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }"""

content = content.replace(bad_update, good_update)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
