import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """        @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
        androidx.compose.foundation.layout.FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tabs = listOf("Mantras", "Timer", "KarmaLog", "Gratitude")
            tabs.forEach { tab ->
                val label = when(tab) {
                    "Mantras" -> if (isEng) "Mantras" else "मंत्र"
                    "Timer" -> if (isEng) "Meditation" else "ध्यान"
                    "KarmaLog" -> if (isEng) "Karma" else "कर्म"
                    else -> if (isEng) "Gratitude" else "आभार"
                }
                Box(
                    modifier = Modifier
                        .clickable { viewModel.setBhaktiTab(tab) }
                        .background(
                            if (selectedToolSubTab == tab) MaterialTheme.colorScheme.primary else Color.Transparent,
                            RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            if (selectedToolSubTab == tab) Color.Transparent else MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        color = if (selectedToolSubTab == tab) Color.White else MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content Area
        Box(modifier = Modifier.weight(1f)) {
            when (selectedToolSubTab) {
                "Mantras" -> MantraLibraryView(viewModel, isEng)
                "Timer" -> MeditationTimerView(isEng)
                "KarmaLog" -> KarmaTrackerView(viewModel, isEng)
                "Gratitude" -> GratitudeJournalView(viewModel, isEng)
            }
        }
    }
}"""

good = """        @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
        androidx.compose.foundation.layout.FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tabs = listOf("Mantras", "JapaMala", "Timer", "KarmaLog", "Gratitude")
            tabs.forEach { tab ->
                val label = when(tab) {
                    "Mantras" -> if (isEng) "Mantras" else "मंत्र"
                    "JapaMala" -> if (isEng) "Japa Mala" else "जप माला"
                    "Timer" -> if (isEng) "Meditation" else "ध्यान"
                    "KarmaLog" -> if (isEng) "Karma" else "कर्म"
                    else -> if (isEng) "Gratitude" else "आभार"
                }
                Box(
                    modifier = Modifier
                        .clickable { viewModel.setBhaktiTab(tab) }
                        .background(
                            if (selectedToolSubTab == tab) MaterialTheme.colorScheme.primary else Color.Transparent,
                            RoundedCornerShape(20.dp)
                        )
                        .border(
                            1.dp,
                            if (selectedToolSubTab == tab) Color.Transparent else MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = label,
                        color = if (selectedToolSubTab == tab) Color.White else MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content Area
        Box(modifier = Modifier.weight(1f)) {
            when (selectedToolSubTab) {
                "Mantras" -> MantraLibraryView(viewModel, isEng)
                "JapaMala" -> JapaMalaCounterView(isEng)
                "Timer" -> MeditationTimerView(isEng)
                "KarmaLog" -> KarmaTrackerView(viewModel, isEng)
                "Gratitude" -> GratitudeJournalView(viewModel, isEng)
            }
        }
    }
}

@Composable
fun JapaMalaCounterView(isEnglish: Boolean) {
    var count by remember { mutableStateOf(0) }
    var malas by remember { mutableStateOf(0) }
    
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEnglish) "Japa Mala Counter" else "जप माला काउंटर",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 18.sp
                )
                Text(
                    text = if (isEnglish) "Track your daily recitations" else "अपने दैनिक जाप को ट्रैक करें",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .clickable { 
                            count++
                            if (count >= 108) {
                                malas++
                                count = 0
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$count",
                            fontSize = 64.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "/ 108",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = if (isEnglish) "Total Malas" else "कुल मालाएँ", color = Color.Gray, fontSize = 12.sp)
                        Text(text = "$malas", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 24.sp)
                    }
                    
                    androidx.compose.material3.Button(
                        onClick = { count = 0; malas = 0 },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = if (isEnglish) "Reset" else "रीसेट करें")
                    }
                }
            }
        }
    }
}"""

content = content.replace(bad, good)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
