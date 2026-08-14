import re

with open('app/src/main/java/com/example/ui/screens/video/VideoScreens.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "fun VideoHubScreen(viewModel: SadhakViewModel) {",
    "fun VideoHubScreen(viewModel: SadhakViewModel, videoViewModel: com.example.ui.viewmodel.VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {"
)

text = text.replace(
    "viewModel.videos.collectAsState()",
    "videoViewModel.videos.collectAsState()"
)

text = text.replace(
    "viewModel.selectedVideoCategory.collectAsState()",
    "videoViewModel.selectedVideoCategory.collectAsState()"
)

text = text.replace(
    "viewModel.setVideoCategory",
    "videoViewModel.setVideoCategory"
)

text = text.replace(
    "viewModel.curatedPlaylistVideos.collectAsState()",
    "videoViewModel.curatedPlaylistVideos.collectAsState()"
)

text = text.replace(
    "viewModel.getCuratedPlaylistTitle",
    "videoViewModel.getCuratedPlaylistTitle"
)

with open('app/src/main/java/com/example/ui/screens/video/VideoScreens.kt', 'w') as f:
    f.write(text)

