import re

with open('app/src/main/java/com/example/ui/screens/video/VideoScreens.kt', 'r') as f:
    text = f.read()

replacement = """    val isEng by viewModel.isEnglish.collectAsState()
    
    val currentFestival by viewModel.currentFestival.collectAsState()
    androidx.compose.runtime.LaunchedEffect(currentFestival) {
        videoViewModel.setCurrentFestival(currentFestival)
    }
"""

text = text.replace("    val isEng by viewModel.isEnglish.collectAsState()", replacement)

with open('app/src/main/java/com/example/ui/screens/video/VideoScreens.kt', 'w') as f:
    f.write(text)
