import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """                    val karmaPoints by viewModel.karmaPoints.collectAsState()
                    val dailyStreak by viewModel.dailyStreak.collectAsState()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Karma Points", fontWeight = FontWeight.Bold, color = SageGreen, fontSize = 11.sp)
                            Text(text = "$karmaPoints ✨", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Daily Streak", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                            Text(text = "$dailyStreak Days 🔥", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp)
                        }
                    }"""

content = re.sub(r'                    Row\(\s*modifier = Modifier\.fillMaxWidth\(\),\s*horizontalArrangement = Arrangement\.SpaceEvenly\s*\)\s*\{\s*Column\(horizontalAlignment = Alignment\.CenterHorizontally\)\s*\{\s*Text\(text = "Karma Points",.*?\)\s*Text\(text = "108 ✨",.*?\)\s*\}\s*Column\(horizontalAlignment = Alignment\.CenterHorizontally\)\s*\{\s*Text\(text = "Daily Streak",.*?\)\s*Text\(text = "5 Days 🔥",.*?\)\s*\}\s*\}', replacement, content, flags=re.DOTALL)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
