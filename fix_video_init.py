import re

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "class VideoViewModel(application: Application) : AndroidViewModel(application) {\n\n    private val repository = SadhakRepository(application)",
    "class VideoViewModel(application: Application) : AndroidViewModel(application) {\n\n    private val repository = SadhakRepository(application)\n\n    init {\n        autoSyncYoutubeVideosIfNeeded {}\n    }"
)

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'w') as f:
    f.write(text)

