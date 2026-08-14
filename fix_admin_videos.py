import re

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "fun AdminVideosView(viewModel: SadhakViewModel, isEng: Boolean) {",
    "fun AdminVideosView(viewModel: SadhakViewModel, isEng: Boolean, videoViewModel: com.example.ui.viewmodel.VideoViewModel) {"
)

text = text.replace(
    "AdminVideosView(viewModel, isEng)",
    "AdminVideosView(viewModel, isEng, videoViewModel)"
)

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'w') as f:
    f.write(text)

