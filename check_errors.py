import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    content = f.read()

print("updateUserProfileDetails" in content)
print("fun logout" in content)
print("fun sendMessage" in content)

with open("app/src/main/java/com/example/data/User.kt", "r") as f:
    print(f.read())
