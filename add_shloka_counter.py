import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

services_target = """            val services = listOf(
                Triple(if (isEng) "Knowledge Hub" else "ज्ञान गंगा", Icons.Default.MenuBook, "Knowledge"),
                Triple(if (isEng) "Community" else "साधक संघ", Icons.Default.Forum, "Community"),
                Triple(if (isEng) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement, "Bhakti"),
                Triple(if (isEng) "Video Hub" else "दर्शन तरंगिणी", Icons.Default.VideoLibrary, "Videos"),
                Triple(if (isEng) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat, "Chat")
            )"""

services_replacement = """            val services = listOf(
                Triple(if (isEng) "Knowledge Hub" else "ज्ञान गंगा", Icons.Default.MenuBook, "Knowledge"),
                Triple(if (isEng) "Community" else "साधक संघ", Icons.Default.Forum, "Community"),
                Triple(if (isEng) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement, "Bhakti"),
                Triple(if (isEng) "Video Hub" else "दर्शन तरंगिणी", Icons.Default.VideoLibrary, "Videos"),
                Triple(if (isEng) "Bhakti Tools" else "भक्ति उपकरण", Icons.Default.Build, "BhaktiTools"),
                Triple(if (isEng) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat, "Chat")
            )"""
content = content.replace(services_target, services_replacement)

# Remove the duplicate Knowledge Hub and Community at the bottom of HomeScreen
dup_target = """        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Knowledge Hub
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.setScreen("Knowledge") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.MenuBook, contentDescription = "Knowledge", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Knowledge Hub" else "ज्ञान सागर", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
                
                // Community
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.setScreen("Community") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Forum, contentDescription = "Community", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Community" else "सत्संग", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }"""
content = content.replace(dup_target, "")

recent_reflections_target = """        // 4. Latest Posts Preview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),"""

shloka_addition = """        // --- SHLOKA OF THE DAY COMPONENT ---
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isEng) "Shloka of the Day" else "आज का श्लोक",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            val shlokaList = listOf(
                Pair("कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥", "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions. Never consider yourself to be the cause of the results of your activities, nor be attached to inaction. This verse is the essence of Karma Yoga, teaching us to focus on the work itself rather than the outcome. Let the divine handle the results."),
                Pair("यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥", "Whenever there is a decline in righteousness and an increase in unrighteousness, O Arjuna, at that time I manifest myself on earth. To protect the righteous, to annihilate the wicked, and to reestablish the principles of dharma, I appear millennium after millennium."),
                Pair("श्रद्धावान् लभते ज्ञानं तत्परः संयतेन्द्रियः।\nज्ञानं लब्ध्वा परां शान्तिमचिरेणाधिगच्छति॥", "Those who are full of faith, fully devoted, and have controlled their senses, attain this knowledge. Having attained it, they quickly achieve supreme peace. Faith is the foundation of spiritual journey.")
            )
            val dailyShloka = shlokaList[dayIndex % shlokaList.size]
            val scrollState = rememberScrollState()
            
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
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // 4. Latest Posts Preview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),"""
                
content = content.replace(recent_reflections_target, shloka_addition)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
