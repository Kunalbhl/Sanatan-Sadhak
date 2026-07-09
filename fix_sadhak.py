import re
with open("app/src/main/java/com/example/MainActivity.kt", "r") as f:
    content = f.read()

content = content.replace('"Devotee"', '"Sadhak"')
content = content.replace('"Bhakti"', '"Bhakti Sadhana"')

with open("app/src/main/java/com/example/MainActivity.kt", "w") as f:
    f.write(content)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content2 = f.read()

content2 = content2.replace('"Devotee"', '"Sadhak"')
content2 = content2.replace('"Verified Devotee"', '"Verified Sadhak"')

# Also let's fix the Theme settings and Dark Mode option in Profile
theme_ui = """                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isEng) "Theme Preference" else "थीम वरीयता",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        val themePref by viewModel.themePreference.collectAsState()
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { viewModel.setThemePreference(1) },
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = if (themePref == 1) MaterialTheme.colorScheme.primary.copy(alpha=0.1f) else Color.Transparent)
                            ) { Text(text = "Light") }
                            OutlinedButton(
                                onClick = { viewModel.setThemePreference(2) },
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = if (themePref == 2) MaterialTheme.colorScheme.primary.copy(alpha=0.1f) else Color.Transparent)
                            ) { Text(text = "Dark") }
                            OutlinedButton(
                                onClick = { viewModel.setThemePreference(0) },
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = if (themePref == 0) MaterialTheme.colorScheme.primary.copy(alpha=0.1f) else Color.Transparent)
                            ) { Text(text = "System") }
                        }
                    }"""

# Insert theme_ui before the Sign Out button
signout_target = """                    Button(
                        onClick = { viewModel.logout() }"""
content2 = content2.replace(signout_target, theme_ui + "\n" + signout_target)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content2)

