import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

update_bad = """    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.updateUserDetails(userEmail.value, fullName, mobileNumber, city, state, avatar)
            onResult(true)
        }
    }"""

update_good = """    fun updateUserProfileDetails(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.updateUserDetails(userEmail.value, fullName, mobileNumber, city, state, avatar, imageUri)
            onResult(true)
        }
    }"""

content = content.replace(update_bad, update_good)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
