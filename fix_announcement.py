import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Add showVerifiedPrompt
replacement_top = """@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    var showVerifiedPrompt by remember { mutableStateOf(true) }
    val posts by viewModel.posts.collectAsState()"""

content = content.replace("""@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val posts by viewModel.posts.collectAsState()""", replacement_top)

replacement_banner = """        // Announcement Banner if any
        if (showVerifiedPrompt) {
            item {
                SacredCard(
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                    borderColor = MaterialTheme.colorScheme.tertiary
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = "Verified Channel Update",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isEng) "Verified Channel Update: Daily visual aarti from Maa Annapurna shrine added! Visit the Video Hub." else "चैनल अपडेट: माँ अन्नपूर्णा मंदिर से दैनिक आरती जोड़ी गई! वीडियो हब देखें।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { showVerifiedPrompt = false }, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }"""

# Need to replace the old Announcement Banner
old_banner = """        // Announcement Banner if any
        item {
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                borderColor = MaterialTheme.colorScheme.tertiary
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Announcement,
                        contentDescription = "Announcement",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEng) {
                            "Verified Channel Update: Daily visual aarti from Maa Annapurna shrine added! Visit the Video Hub."
                        } else {
                            "चैनल अपडेट: माँ अन्नपूर्णा मंदिर से दैनिक आरती जोड़ी गई! वीडियो हब देखें।"
                        },
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }"""

content = content.replace(old_banner, replacement_banner)

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)

