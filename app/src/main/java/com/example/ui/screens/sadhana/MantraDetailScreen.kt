package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.DevotionalTextReaderBar
import com.example.ui.audio.SadhakAudioManager
import com.example.ui.audio.SadhakTextReader
import com.example.ui.components.SacredCard
import com.example.ui.components.ScrollToTopButton
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.viewmodel.PrayerData
import com.example.ui.viewmodel.allPrayersList
import kotlinx.coroutines.launch

@Composable
fun MantraDetailScreen(
    prayerData: PrayerData,
    isEnglish: Boolean,
    onBack: () -> Unit,
    onRelatedClick: (String) -> Unit,
    viewModel: com.example.ui.viewmodel.SadhakViewModel? = null
) {
    val isPlaying by SadhakAudioManager.isPlaying.collectAsState()
    val isBuffering by SadhakAudioManager.isBuffering.collectAsState()
    val progress by SadhakAudioManager.progress.collectAsState()
    val currentPos by SadhakAudioManager.currentPositionStr.collectAsState()
    val totalDur by SadhakAudioManager.durationStr.collectAsState()
    val context = LocalContext.current
    
    // Toggle for language inside the view: SA (Sanskrit) vs EN (English Transliteration)
    var selectedLanguage by remember { mutableStateOf("SA") }

    val displayTitle = if (selectedLanguage == "EN") prayerData.titleEn else prayerData.titleHi
    val displayContent = if (selectedLanguage == "EN") prayerData.contentEn else prayerData.contentHi

    DisposableEffect(prayerData.id) {
        onDispose {
            SadhakAudioManager.stop()
            SadhakTextReader.stop()
        }
    }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val matchedAudioTrack: AudioTrackId? = when (prayerData.id.lowercase()) {
        "hanuman" -> AudioTrackId.HANUMAN_CHALISA
        "mrityunjaya", "mahamrityunjaya" -> AudioTrackId.MAHA_MRITYUNJAYA
        "shiva" -> AudioTrackId.SHIVA_TANDAV
        "gayatri" -> AudioTrackId.GAYATRI_MANTRA
        "vishnu" -> AudioTrackId.VISHNU_SAHASRANAMAM
        else -> null
    }?.takeIf { com.example.ui.audio.AudioSynth.supports(it.rawName) }

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
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = SadhakThemeColors.sacredSecondary
                    )
                }
                Text(
                    text = displayTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredSecondary
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
                                content = prayerData.contentHi.take(150),
                                youtubeId = prayerData.youtubeId
                            )
                        }
                    }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Bookmark",
                            tint = SadhakThemeColors.sacredPrimary
                        )
                    }
                }

                // Language Toggle
                Row(
                    modifier = Modifier.background(
                        SadhakThemeColors.sacredPrimary.copy(alpha = 0.1f),
                        RoundedCornerShape(20.dp)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { selectedLanguage = "SA" }
                            .background(
                                if (selectedLanguage == "SA") SadhakThemeColors.sacredPrimary else Color.Transparent,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Sanskrit",
                            fontSize = 12.sp,
                            color = if (selectedLanguage == "SA") Color.White else SadhakThemeColors.sacredPrimary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clickable { selectedLanguage = "EN" }
                            .background(
                                if (selectedLanguage == "EN") SadhakThemeColors.sacredPrimary else Color.Transparent,
                                RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "English",
                            fontSize = 12.sp,
                            color = if (selectedLanguage == "EN") Color.White else SadhakThemeColors.sacredPrimary
                        )
                    }
                }
            }

            // Divine Text-to-Speech Reader Bar
            DevotionalTextReaderBar(
                id = "mantra_text_reader_${prayerData.id}_$selectedLanguage",
                title = displayTitle,
                textToRead = "$displayTitle.\n$displayContent",
                isEnglish = selectedLanguage == "EN"
            )

            // Real recordings by devotional artists live on YouTube: open the search in the YouTube app
            if (matchedAudioTrack == null) {
                SacredCard(
                    backgroundColor = SadhakThemeColors.sacredSurface,
                    borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEnglish) "Hear it chanted" else "श्रवण करें",
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 13.sp
                        )
                        Button(
                            onClick = { com.example.ui.screens.video.openYouTubeSearch(context, "$displayTitle mantra") },
                            colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isEnglish) "Listen on YouTube" else "YouTube पर सुनें", color = Color.White)
                        }
                    }
                }
            }

            // Bundled Authentic Audio Player (if dedicated audio track is available)
            if (matchedAudioTrack != null) {
                SacredCard(
                    backgroundColor = SadhakThemeColors.sacredSurface,
                    borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isEnglish) "Devotional Chant Audio" else "पावन चालीसा / मंत्र ध्वनि",
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(
                                onClick = { 
                                    if (isPlaying) {
                                        SadhakAudioManager.pause()
                                    } else {
                                        SadhakAudioManager.playTrack(context, matchedAudioTrack, isLooping = true, volume = 0.85f)
                                    }
                                },
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(SadhakThemeColors.sacredPrimary, RoundedCornerShape(22.dp))
                            ) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "Pause" else "Play",
                                    tint = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            androidx.compose.material3.LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp),
                                color = SadhakThemeColors.sacredPrimary,
                                trackColor = SadhakThemeColors.sacredGold.copy(alpha = 0.25f)
                            )
                        }
                        if (isBuffering) {
                            Text(
                                text = if (isEnglish) "Loading chant audio..." else "ऑडियो लोड हो रहा है...",
                                fontSize = 11.sp,
                                color = SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        } else if (isPlaying || progress > 0f) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = currentPos, fontSize = 11.sp, color = SadhakThemeColors.sacredSecondary)
                                Text(text = totalDur, fontSize = 11.sp, color = SadhakThemeColors.sacredSecondary)
                            }
                        }
                    }
                }
            }

            // Sacred Text Card
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = displayContent,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = SadhakThemeColors.sacredTextPrimary,
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
                color = SadhakThemeColors.sacredSecondary,
                style = MaterialTheme.typography.titleMedium
            )
            
            val similar = allPrayersList.filter { it.id != prayerData.id }
            
            similar.forEach { sim ->
                SacredCard(
                    backgroundColor = SadhakThemeColors.sacredSurface,
                    borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.3f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRelatedClick(sim.id) }
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                            Text(
                                text = if (isEnglish) sim.titleEn else sim.titleHi,
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 14.sp
                            )
                            Text(
                                text = if (isEnglish) sim.descEn else sim.descHi,
                                fontSize = 12.sp,
                                color = SadhakThemeColors.sacredTextSecondary,
                                maxLines = 2
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Read",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(20.dp)
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
