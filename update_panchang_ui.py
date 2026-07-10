import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    text = f.read()

target1 = """    val todayPanchang by viewModel.todayDetailedPanchang.collectAsState()"""
replacement1 = """    val todayPanchang by viewModel.todayDetailedPanchang.collectAsState()
    val panchangError by viewModel.panchangError.collectAsState()
    val panchangLastUpdated by viewModel.panchangLastUpdated.collectAsState()"""
text = text.replace(target1, replacement1)

target2 = """            Box(modifier = Modifier.clickable { showPanchangDetails = true }) {
                SacredCard("""
replacement2 = """            if (panchangError != null) {
                SacredCard(backgroundColor = MaterialTheme.colorScheme.errorContainer) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = panchangError ?: "", color = MaterialTheme.colorScheme.onErrorContainer, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadDailyPanchangAndThought() }) {
                            Text(text = if (isEng) "Retry" else "पुनः प्रयास करें")
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.clickable { showPanchangDetails = true }) {
                    SacredCard("""
text = text.replace(target2, replacement2)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(text)
