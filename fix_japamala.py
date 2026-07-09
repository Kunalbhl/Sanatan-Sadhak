import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

bad = """@Composable
fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (isEng) "Bhakti Tools" else "भक्ति उपकरण",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        
        MantraLibraryView(viewModel, isEng)
        
        MeditationTimerView(isEng)
        
        KarmaTrackerView(viewModel, isEng)
        
        GratitudeJournalView(viewModel, isEng)
    }
}"""

good = """@Composable
fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (isEng) "Bhakti Tools" else "भक्ति उपकरण",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        
        JapaMalaCounterView(isEng)
        
        MantraLibraryView(viewModel, isEng)
        
        MeditationTimerView(isEng)
        
        KarmaTrackerView(viewModel, isEng)
        
        GratitudeJournalView(viewModel, isEng)
    }
}

@Composable
fun JapaMalaCounterView(isEnglish: Boolean) {
    var count by remember { mutableStateOf(0) }
    var malas by remember { mutableStateOf(0) }
    
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
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
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
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "/ 108",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if (isEnglish) "Total Malas:" else "कुल मालाएँ:"} $malas",
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
                
                androidx.compose.material3.TextButton(
                    onClick = { count = 0; malas = 0 }
                ) {
                    Text(
                        text = if (isEnglish) "Reset" else "रीसेट करें",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
        f.write(content)
    print("Replaced JapaMala")
else:
    print("Not found")

