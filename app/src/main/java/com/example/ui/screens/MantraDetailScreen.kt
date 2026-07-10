package com.example.ui.screens




import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.filled.Close

import com.example.ui.viewmodel.allPrayersList

import androidx.compose.material.icons.Icons

import androidx.compose.ui.Alignment

import androidx.compose.foundation.clickable

import androidx.compose.ui.text.font.FontStyle

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.*

import androidx.compose.material.icons.filled.Favorite

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.layout.*

import androidx.compose.ui.Modifier

import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.PlayArrow

import com.example.ui.viewmodel.PrayerData

import com.example.ui.components.ScrollToTopButton

import androidx.compose.foundation.verticalScroll

import androidx.compose.foundation.rememberScrollState

import com.example.ui.theme.*

import com.example.ui.components.SacredCard

import androidx.compose.ui.graphics.Color

import androidx.compose.material.icons.filled.FavoriteBorder

import kotlinx.coroutines.launch

import androidx.compose.foundation.background

import androidx.compose.material3.*

@Composable
fun MantraDetailScreen(
    prayerData: PrayerData,
    isEnglish: Boolean,
    onBack: () -> Unit,
    onRelatedClick: (String) -> Unit,
    viewModel: com.example.ui.viewmodel.SadhakViewModel? = null
) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    
    // Toggle for language inside the view
    // Options: English Transliteration (EN) vs Sanskrit Original (SA)
    var selectedLanguage by remember { mutableStateOf("SA") }

    val displayTitle = if (selectedLanguage == "EN") prayerData.titleEn else prayerData.titleHi
    val displayContent = if (selectedLanguage == "EN") prayerData.contentEn else prayerData.contentHi

    LaunchedEffect(isPlaying, prayerData.id) {
        if (isPlaying) {
            while (isPlaying) {
                kotlinx.coroutines.delay(1000)
                progress += 0.02f
                if (progress >= 1f) progress = 0f
            }
        }
    }
    
    DisposableEffect(Unit) {
        onDispose {
            com.example.ui.components.AudioEngine.stopAmbientSound()
        }
    }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = displayTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.weight(1f)
            )
            
            // Bookmark Button
            if (viewModel != null) {
                val favoriteMantras by viewModel.favoriteMantras.collectAsState(initial = emptyList())
                val isBookmarked = favoriteMantras.any { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                IconButton(onClick = {
                    if (isBookmarked) {
                        val toDel = favoriteMantras.find { it.title == prayerData.titleEn || it.title == prayerData.titleHi }
                        if (toDel != null) {
                            viewModel.deleteFavoriteMantra(toDel.id)
                        }
                    } else {
                        viewModel.addFavoriteMantra(
                            title = if (isEnglish) prayerData.titleEn else prayerData.titleHi,
                            content = prayerData.contentHi.take(100),
                            youtubeId = prayerData.youtubeId
                        )
                    }
                }) {
                    androidx.compose.material3.Icon(
                        imageVector = if (isBookmarked) androidx.compose.material.icons.Icons.Filled.Favorite else androidx.compose.material.icons.Icons.Default.FavoriteBorder,
                        contentDescription = "Bookmark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Language Toggle
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.clickable { selectedLanguage = "SA" }.background(if (selectedLanguage == "SA") MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text(text = "Sanskrit", fontSize = 12.sp, color = if (selectedLanguage == "SA") Color.White else MaterialTheme.colorScheme.primary)
                }
                Box(modifier = Modifier.clickable { selectedLanguage = "EN" }.background(if (selectedLanguage == "EN") MaterialTheme.colorScheme.primary else Color.Transparent, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text(text = "English", fontSize = 12.sp, color = if (selectedLanguage == "EN") Color.White else MaterialTheme.colorScheme.primary)
                }
            }
        }

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Audio Player Simulation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (isEnglish) "Transcribe Read Aloud / Listen with Correct Pronunciation" else "पढ़ें और सही उच्चारण के साथ सुनें",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { 
                                    isPlaying = !isPlaying 
                                    if (isPlaying) {
                                        com.example.ui.components.AudioEngine.playAmbientSound(2, 0.8f)
                                    } else {
                                        com.example.ui.components.AudioEngine.stopAmbientSound()
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(24.dp))
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            androidx.compose.material3.LinearProgressIndicator(
                                progress = progress,
                                modifier = Modifier.weight(1f).height(6.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            )
                        }
                        if (isPlaying) {
                            Text(
                                text = if (isEnglish) "Playing audio..." else "ऑडियो चल रहा है...",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = displayContent,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 26.sp,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = if (isEnglish) "Similar Mantras & Chants" else "समान मंत्र और चालीसा",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )
        
        val similar = allPrayersList.filter { it.id != prayerData.id }
        
        similar.forEach { sim ->
            SacredCard(backgroundColor = MaterialTheme.colorScheme.background) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onRelatedClick(sim.id) }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = if (isEnglish) sim.titleEn else sim.titleHi, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        Text(text = if (isEnglish) sim.descEn else sim.descHi, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, maxLines = 2)
                    }
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Scroll to top button
        if (scrollState.value > 150) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                ScrollToTopButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
    }
}
