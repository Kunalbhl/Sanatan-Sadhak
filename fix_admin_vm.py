import re

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "fun AdminScreen(viewModel: SadhakViewModel) {",
    "fun AdminScreen(viewModel: SadhakViewModel, videoViewModel: com.example.ui.viewmodel.VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {"
)

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'w') as f:
    f.write(text)

