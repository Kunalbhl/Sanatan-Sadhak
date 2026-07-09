import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# 1. Fix Icons
content = content.replace("Icons.Default.VerifiedUser", "Icons.Default.Verified")
# Add import for Close if not exists
if 'import androidx.compose.material.icons.filled.Close' not in content:
    content = content.replace('import androidx.compose.material.icons.filled.Verified', 
                              'import androidx.compose.material.icons.filled.Verified\nimport androidx.compose.material.icons.filled.Close')
    # If Verified is not there, add it at the top
    if 'import androidx.compose.material.icons.filled.Close' not in content:
        content = content.replace('import androidx.compose.material.icons.filled.Add', 
                                  'import androidx.compose.material.icons.filled.Add\nimport androidx.compose.material.icons.filled.Close\nimport androidx.compose.material.icons.filled.Verified')

# 2. Add userRole to CommunityScreen
community_def = "fun CommunityScreen(viewModel: SadhakViewModel) {"
community_rep = "fun CommunityScreen(viewModel: SadhakViewModel) {\n    val userRole by viewModel.userRole.collectAsState()"
content = content.replace(community_def, community_rep)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

