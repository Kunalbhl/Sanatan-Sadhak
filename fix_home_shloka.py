import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = if (isEng) "Community Sparks" else "समुदाय की झलकियाँ",
                        fontWeight = FontWeight.Bold,"""

good = """                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // --- DARSHAN COMPONENT ---
                    Text(
                        text = if (isEng) "Daily Darshan" else "दैनिक दर्शन",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column {
                            Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Brush.verticalGradient(deityColors))) {
                                // Placeholder for deity image (we use icon for now)
                                Icon(
                                    imageVector = Icons.Default.SelfImprovement,
                                    contentDescription = currentDeity,
                                    tint = Color.White.copy(alpha = 0.5f),
                                    modifier = Modifier.fillMaxSize().padding(32.dp)
                                )
                                Box(
                                    modifier = Modifier.fillMaxSize().background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                            startY = 100f
                                        )
                                    )
                                )
                                Column(
                                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
                                ) {
                                    Text(text = currentDeity, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Text(text = deitySummary, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // --- SHLOKA OF THE DAY COMPONENT ---
                    Text(
                        text = if (isEng) "Shloka of the Day" else "आज का श्लोक",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    val shlokaList = listOf(
                        Pair("कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥", "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions."),
                        Pair("यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥", "Whenever there is a decline in righteousness and an increase in unrighteousness, O Arjuna, at that time I manifest myself on earth."),
                        Pair("श्रद्धावान् लभते ज्ञानं तत्परः संयतेन्द्रियः।\nज्ञानं लब्ध्वा परां शान्तिमचिरेणाधिगच्छति॥", "Those who are full of faith, fully devoted, and have controlled their senses, attain this knowledge. Having attained it, they quickly achieve supreme peace.")
                    )
                    val dailyShloka = shlokaList[dayIndex % shlokaList.size]
                    
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = dailyShloka.first,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    lineHeight = 28.sp
                                ),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            androidx.compose.material3.Divider(color = MaterialTheme.colorScheme.outline.copy(alpha=0.5f))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (isEng) dailyShloka.second else "भावार्थ: (Gemini Translation / Offline)",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = if (isEng) "Community Sparks" else "समुदाय की झलकियाँ",
                        fontWeight = FontWeight.Bold,"""

content = content.replace(bad, good)
with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
