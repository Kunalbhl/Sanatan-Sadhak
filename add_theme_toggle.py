import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Theme Switcher
                    val themePref by viewModel.themePreference.collectAsState()
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEng) "App Theme Color" else "ऐप थीम का रंग",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CategoryTab(
                                text = if (isEng) "System" else "सिस्टम",
                                isSelected = themePref == 0,
                                onClick = { viewModel.setThemePreference(0) }
                            )
                            CategoryTab(
                                text = if (isEng) "Light" else "हल्का",
                                isSelected = themePref == 1,
                                onClick = { viewModel.setThemePreference(1) }
                            )
                            CategoryTab(
                                text = if (isEng) "Dark" else "गहरा",
                                isSelected = themePref == 2,
                                onClick = { viewModel.setThemePreference(2) }
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.logoutUser() },"""

content = content.replace("""                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { viewModel.logoutUser() },""", replacement)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

