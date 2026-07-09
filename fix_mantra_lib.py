import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

content = content.replace("import com.example.ui.screens.MantraDetailScreen", "")
content = content.replace('if (userRole == com.example.data.UserRole.GUEST) {', 'if (userRole == "GUEST") {')
content = content.replace('viewModel.triggerGuestPromo("Add Custom Prayers")', 'viewModel.triggerGuestPromo()')

# Add TextButton import if missing
if 'import androidx.compose.material3.TextButton' not in content:
    content = content.replace('import androidx.compose.material3.Text', 'import androidx.compose.material3.TextButton\nimport androidx.compose.material3.Text', 1)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
