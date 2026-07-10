import sys

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

old_shloka = """                    Text(
                        text = dailyShloka.first,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 28.sp
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )"""

new_shloka = """                    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                        Text(
                            text = dailyShloka.first,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                lineHeight = 28.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        if (isTtsReady) {
                            IconButton(
                                onClick = {
                                    tts?.speak(dailyShloka.first, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
                                },
                                modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.primary.copy(alpha=0.1f), CircleShape)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Play Shloka", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            }
                        }
                    }"""

if old_shloka in content:
    content = content.replace(old_shloka, new_shloka)
    open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w").write(content)
    print("Shloka replaced successfully")
else:
    print("Old Shloka not found")
