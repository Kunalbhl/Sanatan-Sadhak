import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

tabs_target = """            val tabs = listOf("Mantras", "Timer", "KarmaLog", "Gratitude")"""
tabs_replacement = """            val tabs = listOf("Mantras", "Timer", "KarmaLog", "Gratitude", "Counter")"""
content = content.replace(tabs_target, tabs_replacement)

labels_target = """                    "KarmaLog" -> if (isEng) "Karma Tracker" else "कर्म चक्र"
                    else -> if (isEng) "Gratitude" else "कृतज्ञता"
                }"""
labels_replacement = """                    "KarmaLog" -> if (isEng) "Karma Tracker" else "कर्म चक्र"
                    "Gratitude" -> if (isEng) "Gratitude" else "कृतज्ञता"
                    else -> if (isEng) "Mantra Counter" else "मंत्र जाप"
                }"""
content = content.replace(labels_target, labels_replacement)

when_target = """        // Sub Section Content
        when (selectedToolSubTab) {
            "Mantras" -> MantrasView(viewModel, isEng)
            "Timer" -> TimerView(viewModel, isEng)
            "KarmaLog" -> KarmaLogView(viewModel, isEng)
            "Gratitude" -> GratitudeView(viewModel, isEng)
        }"""
when_replacement = """        // Sub Section Content
        when (selectedToolSubTab) {
            "Mantras" -> MantrasView(viewModel, isEng)
            "Timer" -> TimerView(viewModel, isEng)
            "KarmaLog" -> KarmaLogView(viewModel, isEng)
            "Gratitude" -> GratitudeView(viewModel, isEng)
            "Counter" -> CounterView(isEng)
        }"""
content = content.replace(when_target, when_replacement)

# Add CounterView
counter_view_code = """
@Composable
fun CounterView(isEng: Boolean) {
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isEng) "Japa Mala Counter" else "जाप माला काउंटर",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clickable { count++ },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = if (isEng) "Tap to count" else "गिनने के लिए टैप करें",
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { count = 0 },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Reset")
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isEng) "Reset Counter" else "काउंटर रीसेट करें")
        }
    }
}
"""
content = content + counter_view_code

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
