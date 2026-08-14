import re

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'r') as f:
    text = f.read()

text = text.replace(
    "class VideoViewModel(\n    application: Application,\n    private val repository: SadhakRepository\n) : AndroidViewModel(application) {",
    "class VideoViewModel(application: Application) : AndroidViewModel(application) {\n    private val repository = SadhakRepository(application)"
)

with open('app/src/main/java/com/example/ui/viewmodel/VideoViewModel.kt', 'w') as f:
    f.write(text)

