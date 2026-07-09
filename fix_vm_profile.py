import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

avatar_bad = """    val userName: StateFlow<String> = repository.userNameState
    val userAvatar: StateFlow<Int> = repository.userAvatarState
    val isPublicPostingEnabled: StateFlow<Boolean> = repository.isPublicPostingEnabled"""

avatar_good = """    val userName: StateFlow<String> = repository.userNameState
    val userAvatar: StateFlow<Int> = repository.userAvatarState
    val userProfileImageUri: StateFlow<String> = repository.userProfileImageUriState
    val isPublicPostingEnabled: StateFlow<Boolean> = repository.isPublicPostingEnabled"""

update_bad = """    fun updateProfile(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int) {
        repository.updateUserDetails(userEmail.value, fullName, mobileNumber, city, state, avatar)
    }"""

update_good = """    fun updateProfile(fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {
        repository.updateUserDetails(userEmail.value, fullName, mobileNumber, city, state, avatar, imageUri)
    }"""

content = content.replace(avatar_bad, avatar_good)
content = content.replace(update_bad, update_good)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content)
