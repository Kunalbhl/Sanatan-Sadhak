with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    text = f.read()

target = """                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {"""

replacement = """                val backgroundChantEnabled by viewModel.backgroundChantEnabled.collectAsState()
                val backgroundChantVolume by viewModel.backgroundChantVolume.collectAsState()
                
                androidx.compose.runtime.LaunchedEffect(backgroundChantEnabled, backgroundChantVolume) {
                    if (backgroundChantEnabled) {
                        com.example.ui.components.AudioEngine.playAmbientSound(2, backgroundChantVolume)
                    } else {
                        com.example.ui.components.AudioEngine.stopAmbientSound()
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {"""

if target in text:
    text = text.replace(target, replacement)
    print("Replaced in MainActivity")

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(text)

