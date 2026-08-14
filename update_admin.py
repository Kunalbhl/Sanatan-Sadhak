import re

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "fun AdminScreen(viewModel: SadhakViewModel, onBack: () -> Unit) {",
    "fun AdminScreen(viewModel: SadhakViewModel, onBack: () -> Unit, videoViewModel: com.example.ui.viewmodel.VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {"
)

text = text.replace(
    "val videos by viewModel.videos.collectAsState()",
    "val videos by videoViewModel.videos.collectAsState()"
)

text = text.replace(
    "viewModel.addBhaktiVideo",
    "videoViewModel.addBhaktiVideo"
)
text = text.replace(
    "viewModel.updateBhaktiVideo",
    "videoViewModel.updateBhaktiVideo"
)
text = text.replace(
    "viewModel.deleteBhaktiVideo",
    "videoViewModel.deleteBhaktiVideo"
)
text = text.replace(
    "viewModel.isSyncingVideos",
    "videoViewModel.isSyncingVideos"
)

text = text.replace(
    "viewModel.syncYoutubeVideos",
    "videoViewModel.syncYoutubeVideos"
)

with open('app/src/main/java/com/example/ui/screens/AdminScreen.kt', 'w') as f:
    f.write(text)

