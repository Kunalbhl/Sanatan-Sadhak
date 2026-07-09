import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """                    if (userRole == "SuperUser") {
                        Button(
                            onClick = onNavigateAdmin,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                        ) {
                            Text(text = if (isEng) "Admin Control Panel" else "एडमिन कंट्रोल पैनल", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { viewModel.logoutUser() },"""

good = """                    if (userRole == "SuperUser") {
                        Button(
                            onClick = onNavigateAdmin,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                        ) {
                            Text(text = if (isEng) "Admin Control Panel" else "एडमिन कंट्रोल पैनल", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val currentThemePref by viewModel.themePreference.collectAsState()
                    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)).padding(16.dp)) {
                        Text(
                            text = if (isEng) "App Theme" else "ऐप थीम", 
                            fontWeight = FontWeight.Bold, 
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            androidx.compose.material3.FilterChip(
                                selected = currentThemePref == 0,
                                onClick = { viewModel.setThemePreference(0) },
                                label = { Text(if (isEng) "System" else "सिस्टम") }
                            )
                            androidx.compose.material3.FilterChip(
                                selected = currentThemePref == 1,
                                onClick = { viewModel.setThemePreference(1) },
                                label = { Text(if (isEng) "Light" else "लाइट") }
                            )
                            androidx.compose.material3.FilterChip(
                                selected = currentThemePref == 2,
                                onClick = { viewModel.setThemePreference(2) },
                                label = { Text(if (isEng) "Dark" else "डार्क") }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { viewModel.logoutUser() },"""

content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
