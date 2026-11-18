package com.example.ui.screens.sadhana

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.*
import com.example.ui.components.DiyaDecoration
import com.example.ui.theme.*

/**
 * Sacred Sadhana Profile & Rank Card
 */
@Composable
fun SadhanaRankProfileCard(
    profile: SadhanaProfileEntity,
    isEnglish: Boolean,
    onViewAllBadges: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rankLevel = SadhanaRankCalculator.getLevelForScore(profile.totalScore)
    val nextThreshold = SadhanaRankCalculator.getNextLevelThreshold(profile.totalScore)
    val currentLevelMin = rankLevel.minScore
    val progress = if (nextThreshold > currentLevelMin) {
        ((profile.totalScore - currentLevelMin).toFloat() / (nextThreshold - currentLevelMin).toFloat()).coerceIn(0f, 1f)
    } else 1f

    val title = if (isEnglish) rankLevel.titleEn else rankLevel.titleHi

    val infiniteTransition = rememberInfiniteTransition(label = "diyaGlow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .testTag("sadhana_rank_profile_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.5.dp,
            Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.tertiary))
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f),
                            SadhakThemeColors.sacredSurface
                        )
                    )
                )
                .padding(16.dp)
        ) {
            // Top Row: Rank title, Level Badge & Diya
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .scale(glowScale)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rankLevel.icon,
                            fontSize = 20.sp
                        )
                    }
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = if (isEnglish) "Spiritual Sadhana Rank" else "सनातन साधना उपाधि",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                // Streak Badge with Diya
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Terracotta.copy(alpha = 0.15f))
                        .border(1.dp, Terracotta.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = "🔥 ", fontSize = 14.sp)
                    Text(
                        text = "${profile.currentStreak} ${if (isEnglish) "Days" else "दिन"}",
                        fontWeight = FontWeight.Bold,
                        color = Terracotta,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Points Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEnglish) "Spiritual Merit (पुण्य अंक)" else "साधना पुण्य अंक",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "${profile.totalScore} / $nextThreshold ${if (isEnglish) "Pts" else "अंक"}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .testTag("sadhana_level_progress"),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 4-Pillar Sadhana Metrics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricChip(
                    icon = "📿",
                    label = if (isEnglish) "Japa Malas" else "माला जाप",
                    value = "${profile.totalMalaCount}",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    icon = "🧘",
                    label = if (isEnglish) "Dhyan Mins" else "ध्यान मिनट",
                    value = "${profile.totalMeditationMinutes}",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    icon = "📖",
                    label = if (isEnglish) "Scriptures" else "शास्त्र पठन",
                    value = "${profile.totalReadingsCount}",
                    modifier = Modifier.weight(1f)
                )
                MetricChip(
                    icon = "🌸",
                    label = if (isEnglish) "Mantras" else "मंत्र संग्रह",
                    value = "${profile.totalSavedMantrasCount}",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun MetricChip(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = icon, fontSize = 14.sp)
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * Sacred Badges Showcase Section
 */
@Composable
fun SadhanaBadgesSection(
    badges: List<SadhanaBadge>,
    isEnglish: Boolean,
    onBadgeClick: (SadhanaBadge) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("sadhana_badges_section"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEnglish) "Devotional Badges & Accomplishments" else "साधना उपाधियां एवं पावन प्रतीक",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            val unlockedCount = badges.count { it.unlocked }
            Text(
                text = "$unlockedCount / ${badges.size}",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(badges) { badge ->
                BadgeItemCard(
                    badge = badge,
                    isEnglish = isEnglish,
                    onClick = { onBadgeClick(badge) }
                )
            }
        }
    }
}

@Composable
fun BadgeItemCard(
    badge: SadhanaBadge,
    isEnglish: Boolean,
    onClick: () -> Unit
) {
    val title = if (isEnglish) badge.title else badge.titleHi
    val borderColor = if (badge.unlocked) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val bgColor = if (badge.unlocked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)

    Card(
        modifier = Modifier
            .width(100.dp)
            .clickable { onClick() }
            .testTag("badge_item_${badge.badgeId}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        if (badge.unlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                        else Color.Gray.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (badge.unlocked) {
                    Text(text = badge.icon, fontSize = 22.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (badge.unlocked) FontWeight.Bold else FontWeight.Normal,
                color = if (badge.unlocked) MaterialTheme.colorScheme.onSurface else Color.Gray,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (!badge.unlocked && badge.requiredValue > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = (badge.currentValue.toFloat() / badge.requiredValue.toFloat()).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    trackColor = Color.Gray.copy(alpha = 0.2f)
                )
            }
        }
    }
}

/**
 * Devotional Community Leaderboard View (Sadhana Mandala)
 */
@Composable
fun SadhanaMandalaLeaderboardView(
    entries: List<LeaderboardEntry>,
    currentProfile: SadhanaProfileEntity,
    selectedPeriod: String,
    onPeriodSelected: (String) -> Unit,
    isEnglish: Boolean,
    onToggleLeaderboardParticipation: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp))
            .testTag("sadhana_leaderboard_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header & Diya
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEnglish) "Sadhana Mandala (Community)" else "साधना मंडल (सत्संग व प्रेरणा)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = if (isEnglish) "Inspiration for daily spiritual discipline" else "परस्पर आध्यात्मिक प्रेरणा व सद्भाव",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }

                DiyaDecoration(size = 28.dp)
            }

            // Timeframe Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf(
                    "Weekly" to if (isEnglish) "Weekly" else "साप्ताहिक",
                    "Monthly" to if (isEnglish) "Monthly" else "मासिक",
                    "All Time" to if (isEnglish) "All Time" else "सर्वकालिक"
                ).forEach { (key, label) ->
                    val isSelected = selectedPeriod == key
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                            .clickable { onPeriodSelected(key) }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SadhakThemeColors.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Privacy Option Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEnglish) "Share Sadhana in Community Mandala" else "मंडल में साधना प्रगति साझा करें",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                com.example.ui.components.SadhakSwitch(
                    checked = currentProfile.isLeaderboardOptedIn,
                    onCheckedChange = { onToggleLeaderboardParticipation(it) },
                    modifier = Modifier.scale(0.8f).testTag("leaderboard_privacy_toggle")
                )
            }

            // Standings List
            if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEnglish) "No active sadhana records yet. Complete your first Japa or Dhyana!" else "अभी कोई साधना प्रविष्टि नहीं है। प्रथम जाप या ध्यान से शुरुआत करें!",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    entries.take(10).forEachIndexed { index, entry ->
                        LeaderboardRowItem(
                            rank = index + 1,
                            entry = entry,
                            isCurrentUser = entry.isCurrentUser,
                            isEnglish = isEnglish
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LeaderboardRowItem(
    rank: Int,
    entry: LeaderboardEntry,
    isCurrentUser: Boolean,
    isEnglish: Boolean
) {
    val rankBadgeBg = when (rank) {
        1 -> Color(0xFFFFD700).copy(alpha = 0.25f) // Gold
        2 -> Color(0xFFC0C0C0).copy(alpha = 0.25f) // Silver
        3 -> Color(0xFFCD7F32).copy(alpha = 0.25f) // Bronze
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    }

    val rankIcon = when (rank) {
        1 -> "🥇"
        2 -> "🥈"
        3 -> "🥉"
        else -> "#$rank"
    }

    val rowBorder = if (isCurrentUser) {
        androidx.compose.foundation.BorderStroke(1.5.dp, SadhakThemeColors.sacredPrimary)
    } else androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredDivider)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isCurrentUser) SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f) else SadhakThemeColors.sacredSurface)
            .then(Modifier.border(rowBorder, RoundedCornerShape(12.dp)))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Rank Badge
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(rankBadgeBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = rankIcon,
                fontWeight = FontWeight.Bold,
                fontSize = if (rank <= 3) 14.sp else 11.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Devotee Name & Devotional Title
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = entry.displayName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = if (isCurrentUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (entry.showcaseBadgeIcon.isNotBlank()) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = entry.showcaseBadgeIcon, fontSize = 12.sp)
                }
            }
            Text(
                text = "${entry.showcaseBadgeTitle} • ${entry.streak} ${if (isEnglish) "Day Streak" else "दिन निरंतर"}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Points
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${entry.score}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = if (isEnglish) "Pts" else "अंक",
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Sacred Milestone / Badge Unlocked Celebration Dialog
 */
@Composable
fun SadhanaMilestoneCelebrationDialog(
    celebration: MilestoneCelebration,
    isEnglish: Boolean,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("sadhana_celebration_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(
                2.dp,
                Brush.verticalGradient(listOf(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.primary))
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sacred Icon / Om with breathing scale
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                        .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = celebration.icon, fontSize = 32.sp)
                }

                Text(
                    text = if (isEnglish) "Sacred Milestone Achieved! 🪷" else "दिव्य साधना उपलब्धि प्राप्त! 🪷",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isEnglish) celebration.badgeTitle else celebration.badgeTitleHi,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = if (isEnglish) celebration.messageEn else celebration.messageHi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth().testTag("celebration_dialog_accept_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Accept with Gratitude (स्वीकारें)" else "सप्रेम स्वीकार करें",
                        color = SadhakThemeColors.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Single Badge Detail Dialog
 */
@Composable
fun BadgeDetailDialog(
    badge: SadhanaBadge,
    isEnglish: Boolean,
    onSetShowcase: () -> Unit,
    onDismiss: () -> Unit
) {
    val title = if (isEnglish) badge.title else badge.titleHi
    val desc = if (isEnglish) badge.description else badge.descriptionHi

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            if (badge.unlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else Color.Gray.copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (badge.unlocked) {
                        Text(text = badge.icon, fontSize = 28.sp)
                    } else {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray, modifier = Modifier.size(28.dp))
                    }
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                if (badge.unlocked) {
                    Text(
                        text = if (isEnglish) "Status: Unlocked 🪷" else "स्थिति: प्राप्त 🪷",
                        fontWeight = FontWeight.Bold,
                        color = SageGreen,
                        fontSize = 12.sp
                    )
                } else {
                    Text(
                        text = "${if (isEnglish) "Progress: " else "प्रगति: "}${badge.currentValue} / ${badge.requiredValue}",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (badge.unlocked) {
                        OutlinedButton(
                            onClick = onSetShowcase,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = if (isEnglish) "Showcase" else "मुख्य उपाधि", fontSize = 12.sp)
                        }
                    }
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = if (isEnglish) "Close" else "बंद करें", color = SadhakThemeColors.onPrimary)
                    }
                }
            }
        }
    }
}
