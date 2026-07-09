import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

replacement = """        prayers.forEach { prayer ->
            SacredCard(
                modifier = Modifier.clickable {
                    activePlayingMantraTitle = prayer.first
                    activePlayingMantraText = prayer.second
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {"""

content = re.sub(r'prayers\.forEach \{ prayer ->\n\s*SacredCard\(\n\s*backgroundColor = MaterialTheme\.colorScheme\.surface,\n\s*borderColor = MaterialTheme\.colorScheme\.primary\.copy\(alpha = 0\.3f\)\n\s*\) \{\n\s*Column\(modifier = Modifier\.padding\(16\.dp\)\) \{', replacement, content)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
