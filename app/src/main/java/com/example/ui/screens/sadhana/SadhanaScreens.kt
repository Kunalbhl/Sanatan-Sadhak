package com.example.ui.screens

import com.example.ui.theme.SadhakThemeColors
import com.example.SanatanSadhakLogo
import com.example.ui.designsystem.SadhakFeatureTile
import com.example.ui.designsystem.SadhakSectionHeader
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.awaitFirstDown

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Pause
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import com.example.ui.components.SettingToggleTile
import com.example.ui.components.SettingActionTile
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.BhaktiVideo
import com.example.data.ChatMessage
import com.example.data.CommunityPost
import com.example.data.KnowledgeArticle
import com.example.ui.components.CategoryTab
import com.example.ui.components.DiyaDecoration
import com.example.ui.components.SacredCard
import com.example.ui.components.SacredHeader
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TempleDarkBackground
import androidx.compose.foundation.layout.widthIn
import com.example.ui.viewmodel.SadhakViewModel
import com.example.data.DetailedPanchang
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import java.util.Date
import com.example.ui.theme.BrassTan
import com.example.ui.theme.Terracotta
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import com.example.utils.HapticFeedbackManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.animation.core.*
import com.example.data.DailyMantra
import com.example.data.Shloka

import androidx.compose.ui.composed
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.interaction.MutableInteractionSource

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun MantraCard(mantras: List<com.example.data.DailyMantra>, isEng: Boolean) {
    var currentIndex by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Thoughts & Motivation" else "आज के सुविचार",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (mantras.isNotEmpty()) {
                val mantra = mantras[currentIndex.coerceIn(0, mantras.size - 1)]
                Column {
                    Text(
                        text = mantra.title,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = mantra.content, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = (if (isEng) "Meaning: " else "अर्थ: ") + mantra.meaning, style = MaterialTheme.typography.bodySmall)
                }

                if (mantras.size > 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                currentIndex = (currentIndex - 1 + mantras.size) % mantras.size
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Previous Thought",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Text(
                            text = "${currentIndex + 1} / ${mantras.size}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        IconButton(
                            onClick = {
                                currentIndex = (currentIndex + 1) % mantras.size
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next Thought",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = if (isEng) "No thoughts available today." else "आज कोई विचार उपलब्ध नहीं हैं।",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun AartiBellWidget(isEng: Boolean, viewModel: SadhakViewModel? = null) {
    var isSwinging by remember { mutableStateOf(false) }
    var pulseTrigger by remember { mutableStateOf(0) }
    var bellCount by remember { mutableStateOf(0) }
    var lastRingTime by remember { mutableStateOf(0L) }
    val bellLogs = remember { mutableStateListOf<String>() }
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val context = androidx.compose.ui.platform.LocalContext.current

    // Swing angle animation: nice decaying pendulum swing when clicked
    val swingAngle by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isSwinging) 22f else 0f,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioHighBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessLow
        ),
        finishedListener = {
            if (isSwinging) {
                isSwinging = false
            }
        }
    )

    // Visual ripple pulse effect for the haptic feedback simulation
    val rippleScale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (pulseTrigger > 0) 1.6f else 1.0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500, easing = androidx.compose.animation.core.FastOutSlowInEasing)
    )
    val rippleAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (pulseTrigger > 0) 0f else 0.4f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)
    )

    // Reset ripple trigger after animation finishes
    LaunchedEffect(pulseTrigger) {
        if (pulseTrigger > 0) {
            kotlinx.coroutines.delay(500)
            pulseTrigger = 0
        }
    }

    SacredCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isEng) "Sacred Aarti Bell" else "दिव्य आरती घंटी",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "${if (isEng) "Total Rings" else "कुल आरती नाद"}: $bellCount",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .testTag("aarti_bell_interactive_widget")
                    .clickable(
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        indication = null
                    ) {
                        val now = System.currentTimeMillis()
                        if (now - lastRingTime >= 200L) {
                            lastRingTime = now
                            isSwinging = true
                            pulseTrigger += 1
                            bellCount += 1
                            viewModel?.incrementAartiBell()
                            com.example.utils.HapticFeedbackManager.performHaptic(haptic, com.example.utils.HapticFeedbackManager.FeedbackType.BELL_RING)
                            com.example.utils.HapticFeedbackManager.vibrate(context, com.example.utils.HapticFeedbackManager.FeedbackType.BELL_RING)
                            
                            // Play original temple bell sound
                            playTempleBellSound()

                            // Log the event
                            val sdf = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())
                            val timeStr = sdf.format(java.util.Date())
                            val logMsg = if (isEng) "Bell Rung at $timeStr (Ring #$bellCount)" else "आरती घंटी नाद समय $timeStr (संख्या #$bellCount)"
                            bellLogs.add(0, logMsg)
                        }
                    }
            ) {
                // Radiating visual pulse waves representing sound propagation
                if (pulseTrigger > 0) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .graphicsLayer {
                                scaleX = rippleScale
                                scaleY = rippleScale
                                alpha = rippleAlpha
                            }
                            .border(3.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .graphicsLayer {
                                scaleX = rippleScale * 1.3f
                                scaleY = rippleScale * 1.3f
                                alpha = rippleAlpha * 0.5f
                            }
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                }

                // Rotating / swinging Gold Temple Bell icon/drawing
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .graphicsLayer {
                            rotationZ = swingAngle
                            transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 0.1f) // Pivot near the top hanger
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Hanger loop at top
                        drawCircle(
                            color = Color(0xFFD4AF37), // Metallic Gold
                            radius = 7.dp.toPx(),
                            center = Offset(w / 2, h * 0.15f),
                            style = Stroke(width = 3.dp.toPx())
                        )

                        // Bell crown / body top
                        val path = Path().apply {
                            moveTo(w * 0.4f, h * 0.25f)
                            quadraticTo(w * 0.5f, h * 0.22f, w * 0.6f, h * 0.25f)
                            lineTo(w * 0.7f, h * 0.65f)
                            // flared bottom lip
                            quadraticTo(w * 0.85f, h * 0.75f, w * 0.8f, h * 0.8f)
                            quadraticTo(w * 0.5f, h * 0.83f, w * 0.2f, h * 0.8f)
                            quadraticTo(w * 0.15f, h * 0.75f, w * 0.3f, h * 0.65f)
                            close()
                        }
                        drawPath(
                            path = path,
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFD700), Color(0xFFC5A029), Color(0xFF8B6508))
                            )
                        )

                        // Clapper (hanging ball at bottom)
                        drawCircle(
                            color = Color(0xFF8B6508),
                            radius = 8.dp.toPx(),
                            center = Offset(w / 2, h * 0.87f)
                        )
                        // Clapper stem
                        drawLine(
                            color = Color(0xFF8B6508),
                            start = Offset(w / 2, h * 0.75f),
                            end = Offset(w / 2, h * 0.87f),
                            strokeWidth = 3.dp.toPx()
                        )
                    }
                    
                    // Golden sacred OM character inside the bell
                    Text(
                        text = "ॐ",
                        color = Color(0xFF4A0E17),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.offset(y = 10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Real-time Logs view below
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Bell Ring Logs" else "घंटी नाद इतिहास",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                )
                if (bellLogs.isNotEmpty()) {
                    TextButton(onClick = { bellLogs.clear() }) {
                        Text(if (isEng) "Clear Logs" else "लॉग साफ़ करें", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), thickness = 1.dp)

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                if (bellLogs.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isEng) "Tap the bell to hear original sound and see logs" else "घंटी को स्पर्श करके दिव्य ध्वनि और इतिहास देखें",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    androidx.compose.foundation.lazy.LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(bellLogs) { log ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = log,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun playTempleBellSound() {
    com.example.ui.audio.SadhakAudioManager.playOneShot(com.example.ui.audio.AudioTrackId.TEMPLE_BELL)
}

// --- BHAKTI SADHANA SCREEN ---

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun BhaktiSadhanaScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var activePlayingMantraId by remember { mutableStateOf<String?>(null) }

    if (activePlayingMantraId != null) {
        LaunchedEffect(activePlayingMantraId) {
            viewModel.setBottomBarVisibility(false)
        }
        DisposableEffect(Unit) {
            onDispose {
                viewModel.setBottomBarVisibility(true)
            }
        }
        androidx.activity.compose.BackHandler {
            activePlayingMantraId = null
        }
        val prayerData = com.example.ui.viewmodel.allPrayersList.find { it.id == activePlayingMantraId }
        if (prayerData != null) {
            MantraDetailScreen(
                prayerData = prayerData,
                isEnglish = isEng,
                onBack = { activePlayingMantraId = null },
                onRelatedClick = { id -> activePlayingMantraId = id },
                viewModel = viewModel
            )
        } else {
            activePlayingMantraId = null
        }
        return
    }

    // Ensure state defaults to valid tab for this screen
    DisposableEffect(Unit) { onDispose { viewModel.setBottomBarVisibility(true) } }
    LaunchedEffect(selectedToolSubTab) { if (selectedToolSubTab == "Timer") viewModel.setBottomBarVisibility(false) else viewModel.setBottomBarVisibility(true) }

    val activeTab = if (selectedToolSubTab == "Dashboard" || selectedToolSubTab == "Mantras" || selectedToolSubTab == "Timer" || selectedToolSubTab == "My Mantras") {
        selectedToolSubTab
    } else {
        "Dashboard"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
                .testTag("bhakti_sadhana_screen"),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SacredHeader(
                title = if (isEng) "Bhakti Sadhana" else "भक्ति साधना",
                subtitle = if (isEng) "Daily rituals, calming meditation, and sacred chants" else "दैनिक अनुष्ठान, शांत ध्यान और पवित्र मंत्र",
                showDiyas = true
            )

            // Sub Navigation Tabs: Dashboard, Mantras, My Mantras, Timer
            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Dashboard", "Mantras", "My Mantras", "Timer")
                tabs.forEach { tab ->
                    val label = when (tab) {
                        "Dashboard" -> if (isEng) "Dashboard" else "डैशबोर्ड"
                        "Mantras" -> if (isEng) "Prayers" else "मंत्र/आरती"
                        "My Mantras" -> if (isEng) "My Mantras" else "मेरे मंत्र"
                        else -> if (isEng) "Dhyana Sadhana" else "ध्यान साधना"
                    }
                    CategoryTab(
                        text = label,
                        isSelected = activeTab == tab,
                        onClick = { viewModel.navigateToBhakti(tab) },
                        testTagStr = "bhakti_sub_tab_$tab"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Sub Section Content
            when (activeTab) {
                "Dashboard" -> SadhanaDashboardView(viewModel, isEng)
                "Mantras" -> MantraLibraryView(viewModel, isEng) { activePlayingMantraId = it }
                "My Mantras" -> MyMantrasView(viewModel, isEng) { activePlayingMantraId = it }
                "Timer" -> MeditationTimerView(viewModel, isEng)
                else -> SadhanaDashboardView(viewModel, isEng)
            }
        }

        // Global Scroll To Top Button
        ScrollToTopButton(
            visible = scrollState.value > 300,
            testTag = "scroll_to_top_sadhana",
            onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

// --- BHAKTI TOOLS SCREEN (NEW) ---

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()
    // Ensure state defaults to valid tab for this screen
    DisposableEffect(Unit) { onDispose { viewModel.setBottomBarVisibility(true) } }
    LaunchedEffect(selectedToolSubTab) { if (selectedToolSubTab == "Counter") viewModel.setBottomBarVisibility(false) else viewModel.setBottomBarVisibility(true) }

    DisposableEffect(Unit) { onDispose { viewModel.setBottomBarVisibility(true) } }
    val activeTab = if (selectedToolSubTab == "Bell" || selectedToolSubTab == "KarmaLog" || selectedToolSubTab == "Gratitude" || selectedToolSubTab == "Counter" || selectedToolSubTab == "TempleFinder") {
        selectedToolSubTab
    } else {
        "Bell"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag("bhakti_tools_screen"),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SacredHeader(
                title = if (isEng) "Bhakti Tools" else "भक्ति साधन",
                subtitle = if (isEng) "Spiritual progress trackers, gratitude log, and chant counters" else "आध्यात्मिक प्रगति ट्रैकर, कृतज्ञता डायरी और जाप काउंटर",
                showDiyas = true
            )

            // Sub Navigation Tabs: Bell, Counter, KarmaLog, Gratitude, TempleFinder
            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Bell", "Counter", "KarmaLog", "Gratitude", "TempleFinder")
                tabs.forEach { tab ->
                    val label = when (tab) {
                        "Bell" -> if (isEng) "Aarti Bell" else "आरती घंटी"
                        "KarmaLog" -> if (isEng) "Karma Tracker" else "कर्म चक्र"
                        "Gratitude" -> if (isEng) "Gratitude" else "कृतज्ञता"
                        "TempleFinder" -> if (isEng) "Temple Finder" else "मंदिर खोजक"
                        else -> if (isEng) "Mantra Counter" else "मंत्र जाप"
                    }
                    CategoryTab(
                        text = label,
                        isSelected = activeTab == tab,
                        onClick = { viewModel.navigateToBhakti(tab) },
                        testTagStr = "bhakti_sub_tab_$tab"
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Sub Section Content
            when (activeTab) {
                "Bell" -> Column(modifier = Modifier.weight(1f)) { AartiBellWidget(isEng = isEng, viewModel = viewModel) }
                "Counter" -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
                "KarmaLog" -> Column(modifier = Modifier.weight(1f)) { KarmaTrackerView(viewModel, isEng) }
                "Gratitude" -> Column(modifier = Modifier.weight(1f)) { GratitudeJournalView(viewModel, isEng) }
                "TempleFinder" -> Column(modifier = Modifier.weight(1f)) { TempleFinderView(viewModel) }
                else -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
            }
        }

    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean, onMantraClick: (String) -> Unit) {
    val userRole by viewModel.userRole.collectAsState()
    val favoriteMantras by viewModel.favoriteMantras.collectAsState(initial = emptyList())
    val uriHandler = LocalUriHandler.current
    val context = androidx.compose.ui.platform.LocalContext.current

    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var newYoutubeUrl by remember { mutableStateOf("") }
    var showAddCustomMantra by remember { mutableStateOf(false) }
    var mantraToDelete by remember { mutableStateOf<com.example.data.FavoriteMantra?>(null) }

    var searchQuery by remember { mutableStateOf("") }

    if (mantraToDelete != null) {
        AlertDialog(
            onDismissRequest = { mantraToDelete = null },
            title = { Text(if (isEnglish) "Delete Custom Mantra" else "व्यक्तिगत मंत्र हटाएं") },
            text = { Text(if (isEnglish) "Are you sure you want to delete '${mantraToDelete?.title}'?" else "क्या आप निश्चित रूप से '${mantraToDelete?.title}' हटाना चाहते हैं?") },
            confirmButton = {
                TextButton(onClick = {
                    mantraToDelete?.id?.let { viewModel.deleteFavoriteMantra(it) }
                    mantraToDelete = null
                }) {
                    Text(if (isEnglish) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mantraToDelete = null }) {
                    Text(if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("bhakti_sadhana_search_bar"),
            placeholder = {
                Text(text = if (isEnglish) "Search mantras, prayers, aartis..." else "खोजें: मंत्र, प्रार्थना, आरती...")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

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
                                if (userRole == "Guest") {
                                    viewModel.triggerGuestPromo()
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

        val filteredClassical = com.example.ui.viewmodel.allPrayersList.filter { prayer ->
            searchQuery.isEmpty() ||
                    prayer.titleEn.contains(searchQuery, ignoreCase = true) ||
                    prayer.titleHi.contains(searchQuery, ignoreCase = true) ||
                    prayer.descEn.contains(searchQuery, ignoreCase = true) ||
                    prayer.descHi.contains(searchQuery, ignoreCase = true)
        }

        val filteredPersonal = favoriteMantras.filter { fav ->
            searchQuery.isEmpty() ||
                    fav.title.contains(searchQuery, ignoreCase = true) ||
                    fav.content.contains(searchQuery, ignoreCase = true)
        }

        if (filteredClassical.isNotEmpty()) {
            Text(
                text = if (isEnglish) "Classical Scriptural Chants:" else "शास्त्रीय वेद मंत्र व आरती:",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )
            
            filteredClassical.forEach { prayer ->
                SacredCard(
                    modifier = Modifier.clickable {
                        onMantraClick(prayer.id)
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
        }

        if (filteredPersonal.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isEnglish) "My Personal Prayers:" else "मेरी व्यक्तिगत प्रार्थनाएं:",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            filteredPersonal.forEach { fav ->
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
                            IconButton(onClick = { mantraToDelete = fav }) {
                                Icon(imageVector = androidx.compose.material.icons.Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = fav.content, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        if (fav.youtubeId.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = {
                                    try {
                                        // The field can hold a full link or a bare video ID
                                        val link = fav.youtubeId.trim().let {
                                            if (it.startsWith("http")) it else "https://www.youtube.com/watch?v=$it"
                                        }
                                        uriHandler.openUri(link)
                                    } catch (e: Exception) {
                                        android.widget.Toast.makeText(context, if (isEnglish) "Couldn't open YouTube" else "यूट्यूब नहीं खुल सका", android.widget.Toast.LENGTH_SHORT).show()
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

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun MeditationTimerView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var timerRunning by remember { mutableStateOf(false) }
    var secondsLeft by remember { mutableStateOf(300) } // 5 Minutes Default
    var selectedDuration by remember { mutableStateOf(300) }
    var customDurationMins by remember { mutableStateOf(5f) }
    var selectedSoundIndex by remember { mutableStateOf(1) } // Default to Temple Bells
    var backgroundVolume by remember { mutableStateOf(0.7f) }
    var breathingPhase by remember { mutableStateOf("Inhale") }

    // Ambient Sound Playback logic
    LaunchedEffect(timerRunning, selectedSoundIndex, backgroundVolume) {
        if (timerRunning && selectedSoundIndex > 0) {
            val trackId = com.example.ui.audio.AudioTrackId.fromIndex(selectedSoundIndex)
            com.example.ui.audio.SadhakAudioManager.playTrack(context, trackId, isLooping = true, volume = backgroundVolume)
        } else {
            com.example.ui.audio.SadhakAudioManager.stop()
        }
    }

    // Coroutine timer logic
    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            while (secondsLeft > 0 && timerRunning) {
                kotlinx.coroutines.delay(1000)
                secondsLeft -= 1
                
                // Sync breathing instruction (every 4 seconds)
                breathingPhase = when ((secondsLeft % 12)) {
                    in 0..3 -> if (isEnglish) "Inhale Deeply (श्वास लें)" else "गहरी श्वास लें"
                    in 4..7 -> if (isEnglish) "Hold Calmly (कुंभक)" else "शांत रहकर श्वास रोकें"
                    else -> if (isEnglish) "Exhale Gently (श्वास छोड़ें)" else "धीरे से श्वास छोड़ें"
                }
            }
            if (secondsLeft <= 0) {
                timerRunning = false
                secondsLeft = selectedDuration
                com.example.ui.audio.SadhakAudioManager.stop()
                HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.BELL_RING)
                HapticFeedbackManager.performHaptic(haptic, HapticFeedbackManager.FeedbackType.BELL_RING)
                val completedMins = (selectedDuration / 60).coerceAtLeast(1)
                viewModel.logKarmaDeed("Dhyana Sadhana Completed for $completedMins mins")
                viewModel.recordMeditationMinutes(completedMins)
            }
        }
    }

    // Animation scale for breathing circle
    val breatheScale by animateFloatAsState(
        targetValue = if (timerRunning) {
            when (breathingPhase) {
                "Inhale Deeply (श्वास लें)", "गहरी श्वास लें" -> 1.4f
                "Hold Calmly (कुंभक)", "शांत रहकर श्वास रोकें" -> 1.4f
                else -> 0.9f
            }
        } else 1.0f,
        animationSpec = tween(durationMillis = 4000)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.tertiary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isEnglish) "Dhyana & Breath Meditation" else "ध्यान एवं प्राणायाम",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // Circular progress tracker and Breathing visualizer combined
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(190.dp)
                ) {
                    androidx.compose.material3.CircularProgressIndicator(
                        progress = if (selectedDuration > 0) (secondsLeft.toFloat() / selectedDuration.toFloat()).coerceIn(0f, 1f) else 0f,
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("timer_circular_progress"),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 6.dp,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )

                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .scale(breatheScale)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                        Terracotta.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                )
                            )
                            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
                            .semantics {
                                contentDescription = if (isEnglish) {
                                    "Meditation timer: $breathingPhase, ${secondsLeft / 60} minutes ${secondsLeft % 60} seconds remaining. Tap to toggle or hold to reset."
                                } else {
                                    "ध्यान टाइमर: $breathingPhase, ${secondsLeft / 60} मिनट ${secondsLeft % 60} सेकंड शेष।"
                                }
                            }
                            .testTag("timer_bead_tap_target")
                            .pointerInput(selectedDuration) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val down = awaitFirstDown(requireUnconsumed = false)
                                        var triggered = false
                                        val job = coroutineScope.launch {
                                            kotlinx.coroutines.delay(2000)
                                            triggered = true
                                            timerRunning = false
                                            secondsLeft = selectedDuration
                                            com.example.ui.audio.SadhakAudioManager.stop()
                                            HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
                                            HapticFeedbackManager.performHaptic(haptic, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
                                        }
                                        
                                        var isDown = true
                                        while (isDown) {
                                            val event = awaitPointerEvent()
                                            val anyUp = event.changes.any { it.id == down.id && !it.pressed }
                                            if (anyUp) {
                                                isDown = false
                                                job.cancel()
                                                if (!triggered) {
                                                    val newRunning = !timerRunning
                                                    timerRunning = newRunning
                                                    val fbType = if (newRunning) HapticFeedbackManager.FeedbackType.SUCCESS else HapticFeedbackManager.FeedbackType.LIGHT_TOUCH
                                                    HapticFeedbackManager.vibrate(context, fbType)
                                                    HapticFeedbackManager.performHaptic(haptic, fbType)
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (timerRunning) breathingPhase else "🕉",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val mins = secondsLeft / 60
                            val secs = secondsLeft % 60
                            Text(
                                text = String.format("%02d:%02d", mins, secs),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (timerRunning) {
                                    if (isEnglish) "HOLD TO STOP" else "रोकने के लिए दबाए रखें"
                                } else {
                                    if (isEnglish) "TAP TO START" else "शुरू करने के लिए दबाएं"
                                },
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Terracotta,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Explicit Start/Pause and Reset Controls
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            val newRunning = !timerRunning
                            timerRunning = newRunning
                            val fb = if (newRunning) HapticFeedbackManager.FeedbackType.SUCCESS else HapticFeedbackManager.FeedbackType.LIGHT_TOUCH
                            HapticFeedbackManager.vibrate(context, fb)
                            HapticFeedbackManager.performHaptic(haptic, fb)
                        },
                        modifier = Modifier.testTag("meditation_play_pause_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (timerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (timerRunning) "Pause" else "Play",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (timerRunning) {
                                if (isEnglish) "Pause" else "विराम दें"
                            } else {
                                if (isEnglish) "Start Dhyana" else "ध्यान आरंभ करें"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    OutlinedButton(
                        onClick = {
                            timerRunning = false
                            secondsLeft = selectedDuration
                            com.example.ui.audio.SadhakAudioManager.stop()
                            HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
                            HapticFeedbackManager.performHaptic(haptic, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
                        },
                        modifier = Modifier.testTag("meditation_reset_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEnglish) "Reset" else "रीसेट",
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dynamic visual frequency wave generator (equalizer)
                if (timerRunning) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(50.dp).padding(vertical = 4.dp)
                    ) {
                        repeat(16) { index ->
                            var scaleVal by remember { mutableStateOf(1f) }
                            LaunchedEffect(timerRunning) {
                                while (timerRunning) {
                                    scaleVal = 0.2f + (kotlin.random.Random.nextFloat() * 1.3f)
                                    kotlinx.coroutines.delay(kotlin.random.Random.nextLong(120, 280))
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .width(3.dp)
                                    .height(36.dp * scaleVal)
                                    .clip(RoundedCornerShape(1.5.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(MaterialTheme.colorScheme.primary, Terracotta, MaterialTheme.colorScheme.tertiary)
                                        )
                                    )
                            )
                        }
                    }

                    val sounds = listOf(
                        Pair("Silent", "मौन"),
                        Pair("Temple Bells 🔔", "घंटी 🔔"),
                        Pair("Divine OM 🕉", "ॐ ध्वनि 🕉"),
                        Pair("Cosmic Flute 🪈", "बांसुरी 🪈"),
                        Pair("Gayatri Mantra 📿", "गायत्री मंत्र 📿"),
                        Pair("Shiva Tandav 🔱", "शिव तांडव 🔱"),
                        Pair("Maha Mrityunjaya 🪔", "महामृत्युंजय 🪔"),
                        Pair("Vishnu Sahasranamam 🪷", "विष्णु सहस्रनाम 🪷")
                    )
                    val soundName = if (isEnglish) sounds[selectedSoundIndex].first else sounds[selectedSoundIndex].second
                    Text(
                        text = if (isEnglish) "Playing Ambient Sound: $soundName" else "पृष्ठभूमि ध्वनि सक्रिय: $soundName",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Duration Selector presets
                if (!timerRunning) {
                    Text(
                        text = if (isEnglish) "Select Quick Duration Preset:" else "त्वरित समय अवधि चुनें:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val durations = listOf(60, 300, 600, 1200)
                        durations.forEach { dur ->
                            val label = "${dur / 60} ${if (isEnglish) "Min" else "मिनट"}"
                            OutlinedButton(
                                onClick = {
                                    selectedDuration = dur
                                    secondsLeft = dur
                                    customDurationMins = (dur / 60).toFloat()
                                },
                                border = ButtonDefaults.outlinedButtonBorder(enabled = selectedDuration == dur),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (selectedDuration == dur) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(text = label, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Custom Duration Slider
                    Text(
                        text = if (isEnglish) "Or Set Custom Duration: ${customDurationMins.toInt()} Min" else "या अपनी पसंद की अवधि चुनें: ${customDurationMins.toInt()} मिनट",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )
                    Slider(
                        value = customDurationMins,
                        onValueChange = {
                            customDurationMins = it
                            selectedDuration = it.toInt() * 60
                            secondsLeft = selectedDuration
                        },
                        valueRange = 1f..60f,
                        steps = 59,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
                        ),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).testTag("custom_duration_slider")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sound Option Selector
                    Text(
                        text = if (isEnglish) "Choose Calming Ambient Sound:" else "शांत ध्यान संगीत चुनें:",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    val sounds = listOf(
                        Pair("Silent", "मौन"),
                        Pair("Temple Bells 🔔", "घंटी 🔔"),
                        Pair("Divine OM 🕉", "ॐ ध्वनि 🕉"),
                        Pair("Cosmic Flute 🪈", "बांसुरी 🪈"),
                        Pair("Gayatri Mantra 📿", "गायत्री मंत्र 📿"),
                        Pair("Shiva Tandav 🔱", "शिव तांडव 🔱"),
                        Pair("Maha Mrityunjaya 🪔", "महामृत्युंजय 🪔"),
                        Pair("Vishnu Sahasranamam 🪷", "विष्णु सहस्रनाम 🪷")
                    )

                    androidx.compose.foundation.layout.FlowRow(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        sounds.indices.forEach { index ->
                            val isSelected = selectedSoundIndex == index
                            val label = if (isEnglish) sounds[index].first else sounds[index].second
                            OutlinedButton(
                                onClick = { selectedSoundIndex = index },
                                border = ButtonDefaults.outlinedButtonBorder(enabled = isSelected),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.secondary
                                ),
                                modifier = Modifier.testTag("meditation_sound_option_$index")
                            ) {
                                Text(text = label, fontSize = 11.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Ambient Sound Volume Slider
                if (selectedSoundIndex > 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolumeUp,
                                contentDescription = "Volume",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isEnglish) "Ambient Volume: ${(backgroundVolume * 100).toInt()}%" else "ध्वनि स्तर: ${(backgroundVolume * 100).toInt()}%",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Slider(
                            value = backgroundVolume,
                            onValueChange = { backgroundVolume = it },
                            valueRange = 0f..1f,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
                            ),
                            modifier = Modifier.fillMaxWidth().testTag("ambient_volume_slider")
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun KarmaTrackerView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val userRole by viewModel.userRole.collectAsState()
    val logs by viewModel.karmaLogs.collectAsState()
    var inputDeed by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    val currentStreak = if (logs.isNotEmpty()) logs.first().streak else 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Streak Card
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
            borderColor = MaterialTheme.colorScheme.tertiary
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DiyaDecoration(size = 36.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isEnglish) "Your Karma Streak" else "आपका कर्म चक्र सिलसिला",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "$currentStreak ${if (isEnglish) "Days of Selfless Service" else "दिनों का निष्काम कर्म"}",
                        fontWeight = FontWeight.Bold,
                        color = SageGreen,
                        fontSize = 18.sp
                    )
                }
            }
        }

        // Add Log Form
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isEnglish) "Record Today's Positive Deed" else "आज का सत्कर्म दर्ज करें",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = if (isEnglish) "Log one small good deed to keep your personal streak." else "अपना सिलसिला बनाए रखने के लिए एक सत्कर्म लिखें।",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = inputDeed,
                    onValueChange = { inputDeed = it },
                    placeholder = {
                        Text(text = if (isEnglish) "E.g., Fed stray dogs, helped neighbor, planted flower..." else "उदा. भूखों को भोजन कराया, पौधों में पानी डाला...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(12.dp)
                )

                if (statusMessage.isNotEmpty()) {
                    Text(text = statusMessage, color = SageGreen, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (userRole == "Guest") {
                            viewModel.triggerGuestPromo()
                        } else if (inputDeed.isNotBlank()) {
                            viewModel.logKarmaDeed(inputDeed)
                            inputDeed = ""
                            statusMessage = if (isEnglish) "Good Karma logged! May you find peace." else "सत्कर्म दर्ज हुआ! ईश्वर आपका कल्याण करे।"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = if (isEnglish) "Log Deed" else "दर्ज करें", color = SadhakThemeColors.onPrimary)
                }
            }
        }

        // Historic Logs
        Text(
            text = if (isEnglish) "Karma Journal History" else "कर्म इतिहास",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )

        if (logs.isEmpty()) {
            Text(
                text = if (isEnglish) "No logs yet. Start doing positive deeds today!" else "इतिहास खाली है। आज से सत्कर्म शुरू करें!",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        } else {
            logs.forEach { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = MaterialTheme.colorScheme.tertiary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = log.deed, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
                            Text(text = "${log.date} • Streak ${log.streak}", color = Color.Gray, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun GratitudeJournalView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val userRole by viewModel.userRole.collectAsState()
    val logs by viewModel.gratitudeLogs.collectAsState()
    var gratitudeText by remember { mutableStateOf("") }
    var alertText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isEnglish) "Daily Gratitude Reflection" else "दैनिक आभार चिंतन",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = if (isEnglish) "What are you thankful to the Divine for today?" else "आज आप परमात्मा के प्रति किस बात के लिए आभारी हैं?",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = gratitudeText,
                    onValueChange = { gratitudeText = it },
                    placeholder = {
                        Text(text = if (isEnglish) "I am grateful for..." else "मैं भगवान के प्रति आभारी हूँ कि...")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(12.dp)
                )

                if (alertText.isNotEmpty()) {
                    Text(text = alertText, color = SageGreen, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (gratitudeText.isNotBlank()) {
                            viewModel.logGratitude(gratitudeText)
                            gratitudeText = ""
                            alertText = if (isEnglish) "Gratitude saved. Stay blessed!" else "कृतज्ञता सहेजी गई। सदैव खुश रहें!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = if (isEnglish) "Save Reflection" else "सुरक्षित करें", color = SadhakThemeColors.onPrimary)
                }
            }
        }

        // Saved journal
        Text(
            text = if (isEnglish) "Saved Gratitude Journal" else "कृतज्ञता डायरी",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )

        if (logs.isEmpty()) {
            Text(
                text = if (isEnglish) "No gratitude records yet." else "डायरी खाली है। आभार प्रगट करें!",
                color = Color.Gray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
        } else {
            logs.forEach { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = log.content, color = MaterialTheme.colorScheme.onSurface, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = log.date, color = Color.Gray, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun SadhanaDashboardView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val context = LocalContext.current
    val userEmail by viewModel.userEmail.collectAsState()
    val effectiveUserId = remember(userEmail) {
        if (userEmail.isBlank()) "guest_devotee" else userEmail.trim().lowercase()
    }
    val prefsKey = remember(effectiveUserId) {
        val sanitized = effectiveUserId.replace("[^a-zA-Z0-9_]".toRegex(), "_")
        "mantra_counter_prefs_$sanitized"
    }
    val prefs = remember(prefsKey) { context.getSharedPreferences(prefsKey, android.content.Context.MODE_PRIVATE) }
    
    val totalJapaCount = prefs.getInt("current_count", 0)
    val beadsPerMala = prefs.getInt("beads_per_mala", 108)
    val targetMalas = prefs.getInt("target_malas", 1)
    val dailyTargetChants = (beadsPerMala * targetMalas).coerceAtLeast(108)

    val karmaLogs by viewModel.karmaLogs.collectAsState()
    val sadhanaProfile by viewModel.sadhanaProfile.collectAsState()
    val sadhanaBadges by viewModel.sadhanaBadges.collectAsState()
    val leaderboardEntries by viewModel.leaderboardEntries.collectAsState()
    val leaderboardPeriod by viewModel.leaderboardPeriod.collectAsState()
    val activeMilestoneCelebration by viewModel.activeMilestoneCelebration.collectAsState()

    val currentStreak = sadhanaProfile?.currentStreak ?: prefs.getInt("streak", 0)
    val meditationMinutes = sadhanaProfile?.totalMeditationMinutes ?: 0
    val targetMeditationMinutes = 20
    val sankalpaDay = (sadhanaProfile?.currentStreak ?: 1).coerceIn(1, 40)

    var showLogProgressDialog by remember { mutableStateOf(false) }
    var logNotes by remember { mutableStateOf("") }
    var selectedBadgeForDetail by remember { mutableStateOf<com.example.data.SadhanaBadge?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sadhana_dashboard_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Sadhana Sacred Profile & Spiritual Rank Card
        com.example.ui.screens.sadhana.SadhanaRankProfileCard(
            profile = sadhanaProfile,
            isEnglish = isEnglish,
            onViewAllBadges = {}
        )

        // 2. Devotional Badges Showcase
        com.example.ui.screens.sadhana.SadhanaBadgesSection(
            badges = sadhanaBadges,
            isEnglish = isEnglish,
            onBadgeClick = { badge -> selectedBadgeForDetail = badge }
        )

        // 3. Quick Action Tiles
        SadhakSectionHeader(
            title = if (isEnglish) "Sadhana Practices" else "साधना अभ्यास"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SadhakFeatureTile(
                title = if (isEnglish) "Japa Counter" else "मंत्र जाप काउंटर",
                shortDescription = if (isEnglish) "Tap mala beads with haptic vibrations & targets" else "माला मनकों पर जाप करें तथा लक्ष्य ट्रैक करें",
                icon = Icons.Default.SelfImprovement,
                badge = if (isEnglish) "Interactive" else "सक्रिय",
                onClick = { viewModel.navigateToBhakti("Counter") },
                testTag = "tile_japa_counter"
            )

            SadhakFeatureTile(
                title = if (isEnglish) "Guided Dhyan (Meditation)" else "ध्यान साधना एवं प्राणायाम",
                shortDescription = if (isEnglish) "Breathing rhythms & temple bell sounds" else "श्वसन चक्र और मंदिर की घंटियों के साथ ध्यान",
                icon = Icons.Default.Timer,
                onClick = { viewModel.navigateToBhakti("Timer") },
                testTag = "tile_dhyan_meditation"
            )

            SadhakFeatureTile(
                title = if (isEnglish) "Daily Mantras & Stotras" else "मंत्र, स्तोत्र एवं चालीसा",
                shortDescription = if (isEnglish) "Authentic chants, audio pronunciation & meanings" else "प्रामाणिक पाठ, उच्चारण व अर्थ",
                icon = Icons.Default.MenuBook,
                onClick = { viewModel.navigateToBhakti("Mantras") },
                testTag = "tile_daily_mantras"
            )

            SadhakFeatureTile(
                title = if (isEnglish) "Personal Spiritual Journal" else "आध्यात्मिक दैनंदिनी",
                shortDescription = if (isEnglish) "Log daily reflections & gratitude entries" else "दैनिक चिंतन व कृतज्ञता दर्ज करें",
                icon = Icons.Default.Article,
                onClick = { viewModel.navigateToBhakti("Gratitude") },
                testTag = "tile_spiritual_journal"
            )

            SadhakFeatureTile(
                title = if (isEnglish) "Karma & Vow Tracker" else "कर्म चक्र एवं सत्कर्म",
                shortDescription = if (isEnglish) "Track good deeds & daily spiritual vows" else "दैनिक सत्कर्म और नियमों का हिसाब रखें",
                icon = Icons.Default.AutoAwesome,
                onClick = { viewModel.navigateToBhakti("KarmaLog") },
                testTag = "tile_karma_tracker"
            )
        }

        // 3. Daily Sadhana Goals Progress
        SadhakSectionHeader(
            title = if (isEnglish) "Daily Goals Progress" else "दैनिक लक्ष्य प्रगति"
        )

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Japa Goal
                Column(modifier = Modifier.testTag("daily_japa_progress_card")) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEnglish) "Mantra Japa Target" else "मंत्र जाप लक्ष्य",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "$totalJapaCount / $dailyTargetChants",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    val japaProgress = if (dailyTargetChants > 0) (totalJapaCount.toFloat() / dailyTargetChants.toFloat()).coerceAtMost(1.0f) else 0f
                    LinearProgressIndicator(
                        progress = japaProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                }

                // Dhyan Goal
                Column(modifier = Modifier.testTag("daily_dhyan_progress_card")) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEnglish) "Dhyan Meditation Goal" else "ध्यान साधना लक्ष्य",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = if (isEnglish) "$meditationMinutes / $targetMeditationMinutes Mins" else "$meditationMinutes / $targetMeditationMinutes मिनट",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    val dhyanProgress = if (targetMeditationMinutes > 0) (meditationMinutes.toFloat() / targetMeditationMinutes.toFloat()).coerceIn(0f, 1f) else 0f
                    LinearProgressIndicator(
                        progress = dhyanProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Terracotta,
                        trackColor = Terracotta.copy(alpha = 0.15f)
                    )
                }

                OutlinedButton(
                    onClick = { showLogProgressDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Log", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = if (isEnglish) "Quick Log Progress" else "त्वरित प्रगति दर्ज करें", fontSize = 13.sp)
                }
            }
        }

        // 4. Active Sankalpa Card
        SadhakSectionHeader(
            title = if (isEnglish) "Active Sankalpa (Vow)" else "सक्रिय संकल्प"
        )

        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
            modifier = Modifier.testTag("active_sankalpa_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEnglish) "Gayatri Anushthan" else "गायत्री अनुष्ठान",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = if (isEnglish) "108 Chants Daily for 40 Days" else "४० दिनों तक प्रतिदिन १०८ जाप",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Terracotta.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isEnglish) "Day $sankalpaDay of 40" else "दिन $sankalpaDay / ४०",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Terracotta
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = (sankalpaDay.toFloat() / 40f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                )
            }
        }

        // 5. Recent Activity Stream
        SadhakSectionHeader(
            title = if (isEnglish) "Recent Activity Stream" else "हाल की साधना गतिविधि"
        )

        if (karmaLogs.isEmpty()) {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Text(
                    text = if (isEnglish) "No recorded activities yet today. Start with Japa or Dhyan!" else "आज की कोई गतिविधि दर्ज नहीं है। जाप या ध्यान से शुरुआत करें!",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            karmaLogs.take(5).forEach { log ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Karma",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = log.deed,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = log.date,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // 6. Devotional Community Mandala & Spiritual Leaderboard
        com.example.ui.screens.sadhana.SadhanaMandalaLeaderboardView(
            entries = leaderboardEntries,
            currentProfile = sadhanaProfile,
            selectedPeriod = leaderboardPeriod,
            onPeriodSelected = { viewModel.setLeaderboardPeriod(it) },
            isEnglish = isEnglish,
            onToggleLeaderboardParticipation = { viewModel.setLeaderboardOptIn(it) }
        )
    }

    // Badge Details Dialog
    selectedBadgeForDetail?.let { badge ->
        com.example.ui.screens.sadhana.BadgeDetailDialog(
            badge = badge,
            isEnglish = isEnglish,
            onSetShowcase = {
                viewModel.setShowcaseBadge(badge.badgeId)
                selectedBadgeForDetail = null
            },
            onDismiss = { selectedBadgeForDetail = null }
        )
    }

    // Active Milestone Celebration Dialog
    activeMilestoneCelebration?.let { celebration ->
        com.example.ui.screens.sadhana.SadhanaMilestoneCelebrationDialog(
            celebration = celebration,
            isEnglish = isEnglish,
            onDismiss = { viewModel.dismissMilestoneCelebration() }
        )
    }

    // Quick Log Progress Dialog
    if (showLogProgressDialog) {
        AlertDialog(
            onDismissRequest = { showLogProgressDialog = false },
            title = {
                Text(
                    text = if (isEnglish) "Quick Log Progress" else "त्वरित प्रगति दर्ज करें",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (isEnglish) "Record a completed Sadhana round or reflection:" else "पूर्ण साधना चक्र या विचार लिखें:",
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = logNotes,
                        onValueChange = { logNotes = it },
                        placeholder = { Text(text = if (isEnglish) "E.g., Completed 1 Mala Gayatri Japa" else "उदा. १ माला गायत्री जाप पूर्ण किया") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (logNotes.isNotBlank()) {
                            viewModel.logKarmaDeed(logNotes)
                            logNotes = ""
                        }
                        showLogProgressDialog = false
                    }
                ) {
                    Text(text = if (isEnglish) "Save" else "सहेजें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogProgressDialog = false }) {
                    Text(text = if (isEnglish) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}


// --- VIDEO HUB SCREEN ---
