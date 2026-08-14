import sys
content = sys.stdin.read()
import re
target = """                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Date Icon",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                val displayTithi = todayPanchang?.tithi ?: currentTithi"""
replacement = """                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Date Icon",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = formattedDate,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 15.sp
                                    )
                                }
                                val panchangSourceVal by viewModel.panchangSource.collectAsState()
                                SacredBadge(text = if (panchangSourceVal == "API") "Live" else "Offline", color = if (panchangSourceVal == "API") SageGreen else Terracotta)
                            }

                            val displayTithi = todayPanchang?.tithi ?: currentTithi"""
if target in content:
    with open('app/src/main/java/com/example/ui/screens/AllScreens.kt', 'w') as f:
        f.write(content.replace(target, replacement))
    print("Fixed panchang in AllScreens")
else:
    print("Target not found")
