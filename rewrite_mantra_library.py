import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

# Find MantraLibraryView
start_idx = content.find("fun MantraLibraryView(")
if start_idx == -1:
    print("Not found")
    exit(1)

# Find next @Composable
end_idx = content.find("@Composable", start_idx)

if end_idx == -1:
    end_idx = len(content)

new_mantra_library = """fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val userRole by viewModel.userRole.collectAsState()
    val favoriteMantras by viewModel.favoriteMantras.collectAsState()
    val uriHandler = LocalUriHandler.current

    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var newYoutubeUrl by remember { mutableStateOf("") }
    var showAddCustomMantra by remember { mutableStateOf(false) }
    
    var activePlayingMantraId by remember { mutableStateOf<String?>(null) }
    
    if (activePlayingMantraId != null) {
        val prayerData = com.example.ui.viewmodel.allPrayersList.find { it.id == activePlayingMantraId }
        if (prayerData != null) {
            import com.example.ui.screens.MantraDetailScreen
            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEnglish,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id }
            )
        } else {
            activePlayingMantraId = null
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (showAddCustomMantra) {
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isEnglish) "Add Personal Prayer" else "व्यक्तिगत प्रार्थना जोड़ें",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text(if (isEnglish) "Title" else "शीर्षक") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newContent,
                        onValueChange = { newContent = it },
                        label = { Text(if (isEnglish) "Prayer Lyrics" else "प्रार्थना के बोल") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        ),
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = newYoutubeUrl,
                        onValueChange = { newYoutubeUrl = it },
                        label = { Text(if (isEnglish) "YouTube URL (Optional)" else "यूट्यूब URL (वैकल्पिक)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { showAddCustomMantra = false }) {
                            Text(if (isEnglish) "Cancel" else "रद्द करें", color = MaterialTheme.colorScheme.secondary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (userRole == com.example.data.UserRole.GUEST) {
                                    viewModel.triggerGuestPromo("Add Custom Prayers")
                                } else {
                                    if (newTitle.isNotBlank() && newContent.isNotBlank()) {
                                        viewModel.addFavoriteMantra(newTitle, newContent, newYoutubeUrl)
                                        newTitle = ""
                                        newContent = ""
                                        newYoutubeUrl = ""
                                        showAddCustomMantra = false
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(if (isEnglish) "Save" else "सहेजें")
                        }
                    }
                }
            }
        } else {
            OutlinedButton(
                onClick = { showAddCustomMantra = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isEnglish) "Add Personal Prayer" else "व्यक्तिगत प्रार्थना जोड़ें")
            }
        }

        Text(
            text = if (isEnglish) "Classical Scriptural Chants:" else "शास्त्रीय वेद मंत्र व आरती:",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 12.dp)
        )
        
        com.example.ui.viewmodel.allPrayersList.forEach { prayer ->
            SacredCard(
                modifier = Modifier.clickable {
                    activePlayingMantraId = prayer.id
                },
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isEnglish) prayer.titleEn else prayer.titleHi,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isEnglish) prayer.descEn else prayer.descHi,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        if (favoriteMantras.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isEnglish) "My Personal Prayers:" else "मेरी व्यक्तिगत प्रार्थनाएं:",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            favoriteMantras.forEach { fav ->
                SacredCard(
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Text(
                                text = fav.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { viewModel.deleteFavoriteMantra(fav.id) }) {
                                Icon(imageVector = androidx.compose.material.icons.Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = fav.content, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        if (fav.youtubeUrl.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = {
                                    try {
                                        uriHandler.openUri(fav.youtubeUrl)
                                    } catch (e: Exception) {
                                        // Ignore
                                    }
                                }
                            ) {
                                Text(if (isEnglish) "Listen on YouTube" else "यूट्यूब पर सुनें")
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
"""

new_content = content[:start_idx] + new_mantra_library + "\n" + content[end_idx:]

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(new_content)
