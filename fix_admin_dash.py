import re

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "fun AdminDashboardView(viewModel: SadhakViewModel, isEng: Boolean) {",
    "fun AdminDashboardView(viewModel: SadhakViewModel, isEng: Boolean, videoViewModel: com.example.ui.viewmodel.VideoViewModel) {"
)

text = text.replace(
    "\"Dashboard\" -> AdminDashboardView(viewModel, isEng)",
    "\"Dashboard\" -> AdminDashboardView(viewModel, isEng, videoViewModel)"
)

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'w') as f:
    f.write(text)

