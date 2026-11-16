package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FavoriteMantra
import com.example.ui.audio.DevotionalListenButton
import com.example.ui.audio.SadhakTextReader
import com.example.ui.components.SacredCard
import com.example.ui.components.SacredHeader
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SageGreen
import com.example.ui.viewmodel.SadhakViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMantrasView(
    viewModel: SadhakViewModel,
    isEng: Boolean,
    onMantraClick: (String) -> Unit = {}
) {
    val favoriteMantras by viewModel.favoriteMantras.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var mantraToDelete by remember { mutableStateOf<FavoriteMantra?>(null) }

    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var addError by remember { mutableStateOf<String?>(null) }

    // Delete Confirmation Dialog
    if (mantraToDelete != null) {
        AlertDialog(
            onDismissRequest = { mantraToDelete = null },
            title = {
                Text(
                    text = if (isEng) "Remove Saved Mantra" else "सहेजा गया मंत्र हटाएं",
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredSecondary
                )
            },
            text = {
                Text(
                    text = if (isEng)
                        "Are you sure you want to remove '${mantraToDelete?.title}' from your saved mantras?"
                    else
                        "क्या आप निश्चित रूप से '${mantraToDelete?.title}' को अपनी सहेजी गई सूची से हटाना चाहते हैं?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mantraToDelete?.id?.let { viewModel.deleteFavoriteMantra(it) }
                        mantraToDelete = null
                    }
                ) {
                    Text(
                        text = if (isEng) "Remove" else "हटाएं",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mantraToDelete = null }) {
                    Text(text = if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Add Custom Mantra Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                addError = null
            },
            title = {
                Text(
                    text = if (isEng) "Add Personal Sadhana Mantra" else "व्यक्तिगत साधना मंत्र जोड़ें",
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredSecondary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = {
                            newTitle = it
                            addError = null
                        },
                        label = { Text(if (isEng) "Mantra / Prayer Name" else "मंत्र / प्रार्थना का नाम") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = newContent,
                        onValueChange = {
                            newContent = it
                            addError = null
                        },
                        label = { Text(if (isEng) "Sacred Verse / Text / Meaning" else "पवित्र श्लोक / मंत्र पाठ / अर्थ") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                    addError?.let { err ->
                        Text(
                            text = err,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isBlank()) {
                            addError = if (isEng) "Please enter a title." else "कृपया मंत्र का नाम दर्ज करें।"
                            return@Button
                        }
                        if (newContent.isBlank()) {
                            addError = if (isEng) "Please enter the mantra verse." else "कृपया मंत्र पाठ दर्ज करें।"
                            return@Button
                        }
                        viewModel.addFavoriteMantra(newTitle.trim(), newContent.trim())
                        newTitle = ""
                        newContent = ""
                        addError = null
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary)
                ) {
                    Text(if (isEng) "Save Mantra" else "मंत्र सहेजें", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddDialog = false
                    addError = null
                }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("my_mantras_screen_view"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Header Row with Add Action
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isEng) "My Mantra Sadhana" else "मेरी मंत्र साधना",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredSecondary,
                        fontSize = 17.sp
                    )
                )
                Text(
                    text = if (isEng) "Your bookmarked & personal daily mantras" else "आपके द्वारा सहेजे गए दैनिक साधना मंत्र",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SadhakThemeColors.sacredTextSecondary,
                        fontSize = 12.sp
                    )
                )
            }

            Button(
                onClick = { showAddDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier.testTag("btn_add_personal_mantra")
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isEng) "Add" else "जोड़ें",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        // Check if Empty
        if (favoriteMantras.isEmpty()) {
            // Devotional Empty State
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .testTag("my_mantras_empty_state")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(SadhakThemeColors.sacredIconBackground)
                            .border(1.5.dp, SadhakThemeColors.sacredGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🕉", fontSize = 36.sp)
                    }

                    Text(
                        text = if (isEng) "Your Mantra Sadhana" else "आपकी मंत्र साधना",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = if (isEng)
                            "Save your favorite sacred mantras, stotras, and daily prayers here for focused daily chanting."
                        else
                            "दैनिक जप और साधना के लिए अपने प्रिय मंत्रों, स्तोत्रों और प्रार्थनाओं को यहाँ सहेजें।",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.navigateToBhakti("Mantras") },
                            colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier.testTag("btn_explore_mantras")
                        ) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEng) "Explore Mantras" else "मंत्र संग्रह देखें",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { showAddDialog = true },
                            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredPrimary),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp), tint = SadhakThemeColors.sacredPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isEng) "Add Custom" else "कस्टम जोड़ें",
                                color = SadhakThemeColors.sacredPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        } else {
            // Render Saved Mantras List
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                favoriteMantras.forEachIndexed { index, mantra ->
                    SacredCard(
                        backgroundColor = SadhakThemeColors.sacredSurface,
                        borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.35f),
                        modifier = Modifier.testTag("my_mantra_item_${mantra.id}")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Mantra Header Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(SadhakThemeColors.sacredIconBackground)
                                            .border(1.dp, SadhakThemeColors.sacredGold, CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🕉", fontSize = 16.sp)
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = mantra.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SadhakThemeColors.sacredSecondary,
                                            fontSize = 15.sp
                                        ),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Divine Text Reader Button
                                    DevotionalListenButton(
                                        id = "my_mantra_${mantra.id}",
                                        text = "${mantra.title}.\n${mantra.content}",
                                        isEnglish = isEng,
                                        testTag = "listen_my_mantra_${mantra.id}"
                                    )

                                    // Delete Action
                                    IconButton(
                                        onClick = { mantraToDelete = mantra },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            // Mantra Verse Content
                            if (mantra.content.isNotBlank()) {
                                Text(
                                    text = mantra.content,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = SadhakThemeColors.sacredTextPrimary,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    ),
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Action Bar: Japa Chanting & Timer
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEng) "Daily Sadhana Active" else "दैनिक साधना में सम्मिलित",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SageGreen,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    )
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(
                                        onClick = { viewModel.navigateToBhakti("Counter") },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredPrimary)
                                    ) {
                                        Icon(Icons.Default.SelfImprovement, contentDescription = null, modifier = Modifier.size(14.dp), tint = SadhakThemeColors.sacredPrimary)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (isEng) "Japa Mala" else "जप माला", fontSize = 11.sp, color = SadhakThemeColors.sacredPrimary, fontWeight = FontWeight.Bold)
                                    }

                                    Button(
                                        onClick = { viewModel.navigateToBhakti("Timer") },
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary)
                                    ) {
                                        Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.White)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(if (isEng) "Meditation" else "ध्यान", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
