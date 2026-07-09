import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """                            Row(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = { viewModel.setLanguage(true) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (isEng) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "English", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { viewModel.setLanguage(false) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (!isEng) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "हिंदी", color = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))"""

good = """                            Row(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = { viewModel.setLanguage(true) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (isEng) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "English", color = Color.White)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Button(
                                    onClick = { viewModel.setLanguage(false) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (!isEng) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = "हिंदी", color = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            val themePref by viewModel.themePreference.collectAsState()
                            Text(text = if (isEng) "Theme" else "थीम", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(bottom = 8.dp))
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = { viewModel.setTheme(1) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (themePref == 1) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = if (isEng) "Light" else "प्रकाश", color = Color.White, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = { viewModel.setTheme(2) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (themePref == 2) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = if (isEng) "Dark" else "अंधेरा", color = Color.White, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = { viewModel.setTheme(0) },
                                    colors = ButtonDefaults.buttonColors(containerColor = if (themePref == 0) MaterialTheme.colorScheme.primary else Color.Gray),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = if (isEng) "System" else "सिस्टम", color = Color.White, fontSize = 12.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))"""

content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
