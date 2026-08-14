import re

with open('app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt', 'r') as f:
    text = f.read()

# Replace the "var isPlaying by remember" and progress states with AudioEngine flows
text = re.sub(
    r'var isPlaying by remember \{ mutableStateOf\(false\) \}\s*var progress by remember \{ mutableStateOf\(0f\) \}',
    '''val isPlaying by com.example.ui.components.AudioEngine.isPlaying.collectAsState()
    val isBuffering by com.example.ui.components.AudioEngine.isBuffering.collectAsState()
    val progress by com.example.ui.components.AudioEngine.progress.collectAsState()
    val currentPos by com.example.ui.components.AudioEngine.currentPositionStr.collectAsState()
    val totalDur by com.example.ui.components.AudioEngine.durationStr.collectAsState()''',
    text
)

text = re.sub(
    r'isPlaying = !isPlaying\s*if \(isPlaying\) \{\s*com\.example\.ui\.components\.AudioEngine\.playAmbientSound\(2, 0\.8f\)\s*\} else \{\s*com\.example\.ui\.components\.AudioEngine\.stopAmbientSound\(\)\s*\}',
    '''if (isPlaying) {
                                        com.example.ui.components.AudioEngine.pauseAmbientSound()
                                    } else {
                                        com.example.ui.components.AudioEngine.playAmbientSound(2, 0.8f)
                                    }''',
    text
)

text = re.sub(
    r'if \(isPlaying\) \{\s*Text\(\s*text = if \(isEnglish\) "Playing audio..." else "ऑडियो चल रहा है...",\s*fontSize = 11\.sp,\s*color = MaterialTheme\.colorScheme\.primary,\s*modifier = Modifier\.padding\(top = 8\.dp\)\s*\)\s*\}',
    '''if (isBuffering) {
                            Text(
                                text = if (isEnglish) "Buffering audio..." else "ऑडियो लोड हो रहा है...",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        } else if (isPlaying || progress > 0f) {
                            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = currentPos, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Text(text = totalDur, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            }
                        }''',
    text
)

# And if there is any DisposableEffect we should pause on dispose
if "DisposableEffect" not in text:
    text = re.sub(
        r'val scrollState = rememberScrollState\(\)',
        '''val scrollState = rememberScrollState()
    DisposableEffect(Unit) {
        onDispose {
            com.example.ui.components.AudioEngine.stopAmbientSound()
        }
    }''',
        text
    )

with open('app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt', 'w') as f:
    f.write(text)
