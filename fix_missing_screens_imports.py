with open("app/src/main/java/com/example/ui/screens/MissingScreens.kt", "r") as f:
    content = f.read()

imports = """
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.data.ChatMessage
"""

content = content.replace("import com.example.ui.components.SacredCard", imports + "import com.example.ui.components.SacredCard")

with open("app/src/main/java/com/example/ui/screens/MissingScreens.kt", "w") as f:
    f.write(content)
