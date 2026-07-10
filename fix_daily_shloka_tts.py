import sys

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

target = """                        Text(
                            text = if (isEng) "DAILY SHLOKA" else "आज का श्लोक",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrassTan,
                                letterSpacing = 1.sp,
                                fontSize = 11.sp
                            )
                        )
                    }
                    
                    val parts = currentThought.split("\\n")"""

replacement = """                        Text(
                            text = if (isEng) "DAILY SHLOKA" else "आज का श्लोक",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrassTan,
                                letterSpacing = 1.sp,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        
                        val parts = currentThought.split("\\n")
                        val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
                        
                        if (isTtsReady) {
                            IconButton(
                                onClick = {
                                    tts?.speak(sanskritVerse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
                                },
                                modifier = Modifier.size(28.dp).background(MaterialTheme.colorScheme.primary.copy(alpha=0.1f), CircleShape)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Play Shloka", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                    }"""

if target in content:
    content = content.replace(target, replacement)
    open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w").write(content)
    print("TTS added to DAILY SHLOKA")
else:
    print("Target not found")
