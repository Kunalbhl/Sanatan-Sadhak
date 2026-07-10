import sys
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target = """                                    fontSize = 12.sp
                                )
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
                    }
                    androidx.compose.material3.Divider(color = MaterialTheme.colorScheme.outline.copy(alpha=0.5f))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Progress Indicator
                    val progress = if (scrollState.maxValue > 0) {
                        scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                    } else 1f
                    
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth().height(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Scrollable text
                    Box(modifier = Modifier.height(60.dp).verticalScroll(scrollState)) {
                        Text(
                            text = if (isEng) dailyShloka.second else "भावार्थ: (Gemini Translation / Offline) - ${dailyShloka.second}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontStyle = FontStyle.Italic
                            )
                        )"""

replacement = """                                    fontSize = 12.sp
                                )
                            )
                        }
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(36.dp).background(Color.White.copy(alpha=0.2f), CircleShape)
                        ) {
                            Icon(Icons.Default.Notifications, contentDescription = "Aarti Bell", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))"""

if target in text:
    text = text.replace(target, replacement)
    print("Fixed shrine")
else:
    print("Shrine target not found")

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)

