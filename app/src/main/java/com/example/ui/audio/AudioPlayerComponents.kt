package com.example.ui.audio

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.SadhakThemeColors

@Composable
fun PersistentMiniPlayer(
    isEnglish: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val currentTrack by SadhakAudioManager.currentTrack.collectAsState()
    val isPlaying by SadhakAudioManager.isPlaying.collectAsState()
    val progress by SadhakAudioManager.progress.collectAsState()
    val isBuffering by SadhakAudioManager.isBuffering.collectAsState()

    AnimatedVisibility(
        visible = currentTrack != null,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        val track = currentTrack ?: return@AnimatedVisibility

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .clickable { SadhakAudioManager.openFullPlayer() }
                .testTag("persistent_mini_player"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = SadhakThemeColors.sacredSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Mini progress bar on top of the card
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(3.dp),
                    color = SadhakThemeColors.sacredPrimary,
                    trackColor = SadhakThemeColors.sacredGold.copy(alpha = 0.2f),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icon
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SadhakThemeColors.sacredIconBackground)
                            .border(1.dp, SadhakThemeColors.sacredGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Audio Playing",
                            tint = SadhakThemeColors.sacredSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Title & Category
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEnglish) track.titleEn else track.titleHi,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredPrimary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = if (isEnglish) track.subtitleEn else track.subtitleHi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextSecondary,
                                fontSize = 11.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    // Play/Pause Button
                    IconButton(
                        onClick = {
                            if (isPlaying) SadhakAudioManager.pause() else SadhakAudioManager.resume()
                        },
                        modifier = Modifier.size(36.dp).testTag("mini_player_play_pause_button")
                    ) {
                        if (isBuffering) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = SadhakThemeColors.sacredPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Next Button
                    IconButton(
                        onClick = { SadhakAudioManager.playNext(context) },
                        modifier = Modifier.size(36.dp).testTag("mini_player_next_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next Track",
                            tint = SadhakThemeColors.sacredSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Close Button
                    IconButton(
                        onClick = { SadhakAudioManager.stop() },
                        modifier = Modifier.size(32.dp).testTag("mini_player_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Stop & Close",
                            tint = SadhakThemeColors.sacredTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FullAudioPlayerModal(
    isEnglish: Boolean
) {
    val showModal by SadhakAudioManager.showFullPlayer.collectAsState()
    if (!showModal) return

    val context = LocalContext.current
    val currentTrack by SadhakAudioManager.currentTrack.collectAsState()
    val isPlaying by SadhakAudioManager.isPlaying.collectAsState()
    val progress by SadhakAudioManager.progress.collectAsState()
    val currentPosStr by SadhakAudioManager.currentPositionStr.collectAsState()
    val durationStr by SadhakAudioManager.durationStr.collectAsState()
    val isRepeat by SadhakAudioManager.isRepeatEnabled.collectAsState()
    val isBuffering by SadhakAudioManager.isBuffering.collectAsState()
    var selectedCategory by remember { mutableStateOf<AudioCategory?>(null) }

    Dialog(
        onDismissRequest = { SadhakAudioManager.closeFullPlayer() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = SadhakThemeColors.sacredBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { SadhakAudioManager.closeFullPlayer() },
                        modifier = Modifier.testTag("full_player_dismiss_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Minimize",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (isEnglish) "Devotional Audio" else "भक्ति संगीत एवं मंत्र",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredPrimary
                        )
                    )

                    IconButton(
                        onClick = { SadhakAudioManager.toggleRepeat() },
                        modifier = Modifier.testTag("full_player_repeat_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "Repeat",
                            tint = if (isRepeat) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Big Hero Icon / Artwork
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SadhakThemeColors.sacredSurface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, SadhakThemeColors.sacredGold)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = "Sacred Resonance",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(96.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Track Title and Subtitle
                Text(
                    text = currentTrack?.let { if (isEnglish) it.titleEn else it.titleHi } ?: if (isEnglish) "Select Track" else "धुन चुनें",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredPrimary,
                        fontSize = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentTrack?.let { if (isEnglish) it.subtitleEn else it.subtitleHi } ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SadhakThemeColors.sacredTextSecondary
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Seek Bar Slider
                Slider(
                    value = progress,
                    onValueChange = { SadhakAudioManager.seekTo(it) },
                    modifier = Modifier.fillMaxWidth().testTag("full_player_seek_slider"),
                    colors = SliderDefaults.colors(
                        thumbColor = SadhakThemeColors.sacredPrimary,
                        activeTrackColor = SadhakThemeColors.sacredPrimary,
                        inactiveTrackColor = SadhakThemeColors.sacredGold.copy(alpha = 0.3f)
                    )
                )

                // Time labels
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentPosStr,
                        style = MaterialTheme.typography.labelSmall.copy(color = SadhakThemeColors.sacredTextSecondary)
                    )
                    Text(
                        text = durationStr,
                        style = MaterialTheme.typography.labelSmall.copy(color = SadhakThemeColors.sacredTextSecondary)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Main Controls Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Restart
                    IconButton(
                        onClick = { SadhakAudioManager.restart() },
                        modifier = Modifier.size(48.dp).testTag("full_player_restart_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "Restart",
                            tint = SadhakThemeColors.sacredSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    // Previous
                    IconButton(
                        onClick = { SadhakAudioManager.playPrevious(context) },
                        modifier = Modifier.size(48.dp).testTag("full_player_prev_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Main Play/Pause Button
                    Button(
                        onClick = {
                            if (isPlaying) SadhakAudioManager.pause() else SadhakAudioManager.resume()
                        },
                        modifier = Modifier.size(64.dp).testTag("full_player_play_pause_button"),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SadhakThemeColors.sacredPrimary,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        if (isBuffering) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(28.dp),
                                color = Color.White,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Play/Pause" else "Play",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    // Next
                    IconButton(
                        onClick = { SadhakAudioManager.playNext(context) },
                        modifier = Modifier.size(48.dp).testTag("full_player_next_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Mute / Unmute
                    val isMuted by SadhakAudioManager.isMuted.collectAsState()
                    IconButton(
                        onClick = { SadhakAudioManager.toggleMute() },
                        modifier = Modifier.size(48.dp).testTag("full_player_mute_button")
                    ) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute" else "Mute",
                            tint = if (isMuted) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredSecondary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Library / Queue Category Filter Chips
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null },
                            label = { Text(if (isEnglish) "All Tracks" else "सभी धुन") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SadhakThemeColors.sacredGold,
                                selectedLabelColor = SadhakThemeColors.sacredPrimary
                            )
                        )
                    }
                    items(AudioCategory.entries) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(if (isEnglish) cat.titleEn else cat.titleHi) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SadhakThemeColors.sacredGold,
                                selectedLabelColor = SadhakThemeColors.sacredPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Library List
                val filteredTracks = AudioTrackId.playableTracks().filter {
                    selectedCategory == null || it.category == selectedCategory
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredTracks) { track ->
                        val isCurrent = track == currentTrack

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    SadhakAudioManager.playTrack(context, track)
                                }
                                .testTag("track_item_${track.rawName}"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrent) SadhakThemeColors.sacredIconBackground else SadhakThemeColors.sacredSurface
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                if (isCurrent) 1.5.dp else 0.5.dp,
                                if (isCurrent) SadhakThemeColors.sacredGold else SadhakThemeColors.sacredGold.copy(alpha = 0.3f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isCurrent && isPlaying) Icons.Default.GraphicEq else Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = if (isCurrent) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredSecondary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isEnglish) track.titleEn else track.titleHi,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                            color = SadhakThemeColors.sacredPrimary
                                        )
                                    )
                                    Text(
                                        text = if (isEnglish) track.subtitleEn else track.subtitleHi,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SadhakThemeColors.sacredTextSecondary,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                                if (isCurrent && isPlaying) {
                                    Text(
                                        text = if (isEnglish) "Playing" else "प्रारंभ",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SadhakThemeColors.sacredPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
