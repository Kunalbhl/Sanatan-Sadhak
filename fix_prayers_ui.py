import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Remove the inline prayers list
start_idx = content.find("val prayers = if (isEnglish) listOf(")
end_idx = content.find(")", start_idx)
# wait, there are two lists inside the if-else, then a final bracket. Let's just replace the whole thing.

# Wait, the inline list is huge. Let's use re to replace it.
content = re.sub(r'val prayers = if \(isEnglish\) listOf\([\s\S]*?\}\s*\}\s*Spacer\(modifier = Modifier\.height\(24\.dp\)\)', 
"""val prayers = com.example.ui.viewmodel.allPrayersList
                    prayers.forEach { p ->
                        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        activePlayingMantraTitle = if (isEnglish) p.titleEn else p.titleHi
                                        activePlayingMantraContent = if (isEnglish) p.contentEn else p.contentHi
                                        // To pass to MantraDetailScreen, we can just use id or activePlayingMantraTitle
                                    }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = if (isEnglish) p.titleEn else p.titleHi, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                    Text(text = if (isEnglish) p.descEn else p.descHi, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, maxLines = 1)
                                }
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))""", content)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
