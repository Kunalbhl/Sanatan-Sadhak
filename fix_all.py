import re

# Fix AllScreens.kt imports
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content_all = f.read()

content_all = content_all.replace("import androidx.compose.material3.CircularProgressIndicator\nimport androidx.compose.material3.CircularProgressIndicator", "import androidx.compose.material3.CircularProgressIndicator")
if "import androidx.compose.material3.CircularProgressIndicator" not in content_all:
    content_all = content_all.replace("import androidx.compose.material3.*", "import androidx.compose.material3.*\nimport androidx.compose.material3.CircularProgressIndicator")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content_all)

# Fix SadhakViewModel.kt
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content_vm = f.read()

# Remove one of the chatMessages declarations
content_vm = re.sub(r'val chatMessages: StateFlow<List<com.example.data.ChatMessage>> = repository.getChatMessages\(\)\.stateIn\(viewModelScope, kotlinx\.coroutines\.flow\.SharingStarted\.WhileSubscribed\(5000\), emptyList\(\)\)\n', '', content_vm, count=1)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(content_vm)

# Check Repository for updateUserDetails
with open("app/src/main/java/com/example/data/SadhakRepository.kt", "r") as f:
    content_repo = f.read()

print("updateUserDetails in Repo:", "fun updateUserDetails(" in content_repo)
