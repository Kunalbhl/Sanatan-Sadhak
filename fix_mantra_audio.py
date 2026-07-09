import re

with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "r") as f:
    content = f.read()

bad = """                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { isPlaying = !isPlaying },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier.weight(1f).height(6.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                        }"""

good = """                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { 
                                    isPlaying = !isPlaying 
                                    if (isPlaying) {
                                        com.example.ui.components.AudioEngine.playAmbientSound(2, 0.8f)
                                    } else {
                                        com.example.ui.components.AudioEngine.stopAmbientSound()
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) androidx.compose.material.icons.filled.Stop else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier.weight(1f).height(6.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                        }"""

content = content.replace("androidx.compose.material.icons.filled.PlayArrow", "androidx.compose.material.icons.filled.PlayArrow\nimport androidx.compose.material.icons.filled.Stop")

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/screens/MantraDetailScreen.kt", "w") as f:
        f.write(content)
    print("Replaced Mantra Audio")
else:
    print("Not found")

