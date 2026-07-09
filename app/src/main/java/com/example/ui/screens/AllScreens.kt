package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.Announcement
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import java.util.Date
import com.example.ui.theme.BrassTan
import com.example.ui.theme.Terracotta
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.text.input.VisualTransformation


// --- HOME SCREEN ---
@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                viewModel.loadDailyPanchangAndThought()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    
    var showVerifiedPrompt by remember { mutableStateOf(true) }
    val posts by viewModel.posts.collectAsState()
    
    val currentThought by viewModel.currentThought.collectAsState()
    val currentTithi by viewModel.currentTithi.collectAsState()
    val currentNakshatra by viewModel.currentNakshatra.collectAsState()
    val currentYoga by viewModel.currentYoga.collectAsState()
    val currentFestival by viewModel.currentFestival.collectAsState()
    
    val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt()

    // Featured deity rotates daily through Maa Annapurna, Balaji, Mahadev
    val deitiesList = listOf("Maa Annapurna Maharani", "Sindari ke Balaji", "Mahadev")
    val deityIndex = dayIndex % deitiesList.size
    val currentDeity = deitiesList[deityIndex]

    val deitySummary = when (deityIndex) {
        0 -> if (isEng) "Giver of infinite nourishment and maternal warmth." else "अनंत पोषण और मातृत्व शक्ति की अधिष्ठात्री।"
        1 -> if (isEng) "The protective, fearless defender of true devotees." else "भक्तों के संकट हरने वाले परम रक्षक और रक्षक।"
        else -> if (isEng) "The cosmic silent Yogi, bestower of ultimate peace." else "परम शांत योगी, मोक्ष और आनंद के दाता।"
    }

    val deityColors = when (deityIndex) {
        0 -> listOf(MaterialTheme.colorScheme.primary, Terracotta)
        1 -> listOf(Terracotta, MaterialTheme.colorScheme.secondary)
        else -> listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    }

    val homeListState = androidx.compose.foundation.lazy.rememberLazyListState()
    val showHomeScrollToTop by remember {
        derivedStateOf { homeListState.firstVisibleItemIndex > 1 }
    }
    val homeCoroutineScope = rememberCoroutineScope()
    
    var showPanchangDetails by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }

    if (showPanchangDetails) {
        // Detailed Panchang Dialog
        AlertDialog(
            onDismissRequest = { showPanchangDetails = false },
            title = { Text(if (isEng) "Today's Panchang" else "आज का पंचांग") },
            text = {
                Column {
                    Text(text = "${if (isEng) "Tithi" else "तिथि"}: $currentTithi")
                    Text(text = "${if (isEng) "Nakshatra" else "नक्षत्र"}: $currentNakshatra")
                    Text(text = "${if (isEng) "Yoga" else "योग"}: $currentYoga")
                    Text(text = "${if (isEng) "Festival" else "त्यौहार"}: $currentFestival")
                }
            },
            confirmButton = {
                TextButton(onClick = { showPanchangDetails = false }) { Text("OK") }
            }
        )
    }

    if (showCalendar) {
        // Simple Calendar placeholder Screen
        CalendarScreen(onBack = { showCalendar = false }, isEng = isEng)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = homeListState,
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_screen_column")
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        // Announcement Banner if any
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
                            imageVector = Icons.Default.Verified,
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
        }

        // 1. Thought of the Day
        item {
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                borderColor = Color(0xFFFFE4C4)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = "Star Sparkle",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEng) "THOUGHT OF THE DAY" else "आज का सुविचार",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrassTan,
                                letterSpacing = 1.sp,
                                fontSize = 11.sp
                            )
                        )
                    }
                    
                    val parts = currentThought.split("\n")
                    if (parts.size > 1) {
                        Text(
                            text = parts[0].trim(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily.Serif
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                        Text(
                            text = parts.drop(1).joinToString("\n").trim(),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )
                        )
                    } else {
                        Text(
                            text = currentThought,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily.Serif
                            )
                        )
                    }
                }
            }
        }

        // 2. Today's Panchang
        item {
            val formattedDate = remember(isEng) {
                val sdf = if (isEng) {
                    java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", java.util.Locale.ENGLISH)
                } else {
                    java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", java.util.Locale("hi"))
                }
                sdf.format(System.currentTimeMillis())
            }

            Text(
                text = if (isEng) "Today's Panchang" else "आज का पंचांग",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Box(modifier = Modifier.clickable { showPanchangDetails = true }) {
                SacredCard(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                    // Current Date
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Date Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp
                            )
                        )
                    }

                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEng) "Tithi (तिथि):" else "तिथि:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = currentTithi,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEng) "Day (वार):" else "वार:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        val dayName = remember(isEng) {
                            val sdf = if (isEng) java.text.SimpleDateFormat("EEEE", java.util.Locale.ENGLISH)
                            else java.text.SimpleDateFormat("EEEE", java.util.Locale("hi"))
                            sdf.format(System.currentTimeMillis())
                        }
                        Text(
                            text = dayName,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEng) "Nakshatra (नक्षत्र):" else "नक्षत्र:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = currentNakshatra,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEng) "Yoga (योग):" else "योग:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = currentYoga,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isEng) "Festival/Muhurat:" else "उत्सव / मुहूर्त:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp
                        )
                        Text(
                            text = currentFestival,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }

        // Category Options Tile Grid (Sacred Services)
        item {
            Text(
                text = if (isEng) "Sacred Services" else "पवित्र सेवाएं",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
            )

            val services = listOf(
                Triple(if (isEng) "Knowledge Hub" else "ज्ञान गंगा", Icons.Default.MenuBook, "Knowledge"),
                Triple(if (isEng) "Community" else "साधक संघ", Icons.Default.Forum, "Community"),
                Triple(if (isEng) "Bhakti Sadhana" else "भक्ति साधना", Icons.Default.SelfImprovement, "Bhakti"),
                Triple(if (isEng) "Video Hub" else "दर्शन तरंगिणी", Icons.Default.VideoLibrary, "Videos"),
                Triple(if (isEng) "Bhakti Tools" else "भक्ति उपकरण", Icons.Default.Build, "BhaktiTools"),
                Triple(if (isEng) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat, "Chat")
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (i in services.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (j in i..i+1) {
                            if (j < services.size) {
                                val svc = services[j]
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(64.dp)
                                        .clickable { viewModel.setScreen(svc.third) },
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = svc.second,
                                            contentDescription = svc.first,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = svc.first,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 11.sp
                                            ),
                                            maxLines = 2
                                        )
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }

        // 3. Featured Deity Darshan
        item {
            Text(
                text = if (isEng) "Daily Darshan" else "दैनिक दर्शन",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            val shrineLocation = when (deityIndex) {
                0 -> if (isEng) "Kashi Vishwanath Dhaam, Varanasi" else "काशी विश्वनाथ धाम, वाराणसी"
                1 -> if (isEng) "Sindari, Rajasthan" else "सिंदरी, राजस्थान"
                else -> if (isEng) "Kedarnath Dhaam, Himalayas" else "केदारनाथ धाम, हिमालय"
            }

            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Column {
                    // Beautiful deity header gradient
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(176.dp)
                            .background(Brush.verticalGradient(colors = deityColors))
                    ) {
                        // Inner content shadow/gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.1f),
                                            Color.Black.copy(alpha = 0.75f)
                                        )
                                    )
                                )
                        )

                        // Subtle temple background placeholder (centered large icon)
                        Icon(
                            imageVector = Icons.Filled.SelfImprovement,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.12f),
                            modifier = Modifier
                                .size(110.dp)
                                .align(Alignment.Center)
                        )

                        // Top-left/bottom labels
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            // Live Darshan Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isEng) "LIVE DARSHAN" else "दैनिक दर्शन",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentDeity,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Text(
                                text = shrineLocation,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }
                    
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = deitySummary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                viewModel.setKnowledgeCategory("Deities")
                                viewModel.setScreen("Knowledge")
                            }
                        ) {
                            Text(
                                text = if (isEng) "Read Devotional Study" else "भक्ति अध्ययन पढ़ें",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "→", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // --- SHLOKA OF THE DAY COMPONENT ---
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (isEng) "Shloka of the Day" else "आज का श्लोक",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            val shlokaList = listOf(
                Pair("कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\nमा कर्मफलहेतुर्भूर्मा ते सङ्गोऽस्त्वकर्मणि॥", "You have a right to perform your prescribed duties, but you are not entitled to the fruits of your actions. Never consider yourself to be the cause of the results of your activities, nor be attached to inaction. This verse is the essence of Karma Yoga, teaching us to focus on the work itself rather than the outcome. Let the divine handle the results."),
                Pair("यदा यदा हि धर्मस्य ग्लानिर्भवति भारत।\nअभ्युत्थानमधर्मस्य तदात्मानं सृजाम्यहम्॥", "Whenever there is a decline in righteousness and an increase in unrighteousness, O Arjuna, at that time I manifest myself on earth. To protect the righteous, to annihilate the wicked, and to reestablish the principles of dharma, I appear millennium after millennium."),
                Pair("श्रद्धावान् लभते ज्ञानं तत्परः संयतेन्द्रियः।\nज्ञानं लब्ध्वा परां शान्तिमचिरेणाधिगच्छति॥", "Those who are full of faith, fully devoted, and have controlled their senses, attain this knowledge. Having attained it, they quickly achieve supreme peace. Faith is the foundation of spiritual journey.")
            )
            val dailyShloka = shlokaList[dayIndex % shlokaList.size]
            val scrollState = rememberScrollState()
            
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = dailyShloka.first,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 28.sp
                        ),
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    androidx.compose.material3.Divider(color = MaterialTheme.colorScheme.outline.copy(alpha=0.5f))
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Progress Indicator
                    val progress = if (scrollState.maxValue > 0) {
                        scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                    } else 1f
                    
                    androidx.compose.material3.LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier.fillMaxWidth().height(4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Scrollable text
                    Box(modifier = Modifier.height(60.dp).verticalScroll(scrollState)) {
                        Text(
                            text = if (isEng) dailyShloka.second else "भावार्थ: (Gemini Translation / Offline) - ${dailyShloka.second}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontStyle = FontStyle.Italic
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // 4. Latest Posts Preview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Recent Community Reflections" else "हालिया चिंतन एवं विचार",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = if (isEng) "View All" else "सभी देखें",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { viewModel.setScreen("Community") }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            
            if (posts.isEmpty()) {
                Text(
                    text = if (isEng) "No posts yet. Be the first to share!" else "कोई विचार नहीं मिला। पहले साधक बनें!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    posts.take(2).forEach { post ->
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "@${post.author}",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = post.category,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = post.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Quick Bhakti Tool Entry: Dhyana Timer
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable {
                        viewModel.navigateToBhakti("Timer")
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Timer Icon",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEng) "Dhyana Sadhana" else "ध्यान साधना",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEng) "Start 5 min meditation" else "५ मिनट का ध्यान शुरू करें",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Start Play",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            // Mantra Counter Quick Entry Card
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        viewModel.navigateToBhakti("Counter")
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.SelfImprovement,
                            contentDescription = "Mantra Counter Icon",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (isEng) "Mantra Sadhana" else "मंत्र साधना",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEng) "Track and count your sacred chants" else "अपने पवित्र मंत्र जाप की गणना करें",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Start Counter",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Karma Tracker
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateToBhakti("KarmaLog") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Karma", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Karma Tracker" else "कर्म चक्र", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
                
                // Gratitude Journal
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.3f), RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateToBhakti("Gratitude") }
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Gratitude", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Gratitude Journal" else "कृतज्ञता डायरी", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 13.sp)
                }
            }
        }
    }

        if (showHomeScrollToTop) {
            androidx.compose.material3.FloatingActionButton(
                onClick = {
                    homeCoroutineScope.launch {
                        homeListState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .testTag("scroll_to_top_home"),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Scroll to top"
                )
            }
        }
    }
}
// --- KNOWLEDGE HUB SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnowledgeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val articles by viewModel.articles.collectAsState()
    val selectedCat by viewModel.selectedKnowledgeCategory.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    
    var searchQuery by remember { mutableStateOf("") }
    val recentSearches by viewModel.recentSearches.collectAsState()
    var activeArticle by remember { mutableStateOf<KnowledgeArticle?>(null) }

    val categories = listOf(
        "All", "Deities", "Temples", "Scriptures", 
        "Bhagavad Gita", "Ramcharitmanas", "Shiv Puran", "Vedas & Granths",
        "Festivals", "Rituals", "Mantras", "Concepts", "Gurus"
    )

    val getCategoryDisplayName = { cat: String ->
        if (isEng) cat
        else when (cat) {
            "All" -> "सभी"
            "Deities" -> "देवी-देवता"
            "Temples" -> "पवित्र मंदिर"
            "Scriptures" -> "धर्मग्रंथ"
            "Bhagavad Gita" -> "भगवद गीता"
            "Ramcharitmanas" -> "रामचरितमानस"
            "Shiv Puran" -> "शिव पुराण"
            "Vedas & Granths" -> "वेद और ग्रंथ"
            "Festivals" -> "त्यौहार"
            "Rituals" -> "पूजा-विधि"
            "Mantras" -> "मंत्र"
            "Concepts" -> "दर्शन/सिद्धांत"
            "Gurus" -> "गुरु परंपरा"
            else -> cat
        }
    }

    if (activeArticle != null) {
        // Article Detail View
        ArticleDetailScreen(
            article = activeArticle!!,
            isEnglish = isEng,
            onBack = { activeArticle = null },
            onWatchVideo = {
                viewModel.setScreen("Videos")
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .testTag("knowledge_screen")
        ) {
            SacredHeader(
                title = if (isEng) "Knowledge Hub" else "ज्ञान कोश",
                subtitle = if (isEng) "Wikipedia of Sanatan Dharma wisdom and scriptures" else "सनातन धर्म के ज्ञान, इतिहास और ग्रंथों का विकिपीडिया",
                showDiyas = true
            )

            // Search Bar with Recent Searches
            var expanded by remember { mutableStateOf(false) }
            val sortBy by viewModel.knowledgeSortBy.collectAsState()

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        expanded = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("knowledge_search_bar"),
                    placeholder = {
                        Text(text = if (isEng) "Search topics, deities, mantras..." else "खोजें: देवी-देवता, ग्रन्थ, मंत्र...")
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.addRecentSearch(searchQuery)
                        expanded = false
                    }),
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

                if (recentSearches.isNotEmpty() && searchQuery.isEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        recentSearches.forEach { search ->
                            DropdownMenuItem(
                                text = { Text(search) },
                                onClick = {
                                    searchQuery = search
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sorting Dropdown
            var sortExpanded by remember { mutableStateOf(false) }
            val sorts = listOf("Newest", "A-Z")
            val label = when (sortBy) {
                "Newest" -> if (isEng) "Newest" else "नवीनतम"
                else -> if (isEng) "A-Z" else "अ-ज्ञ"
            }

            ExposedDropdownMenuBox(
                expanded = sortExpanded,
                onExpandedChange = { sortExpanded = !sortExpanded }
            ) {
                OutlinedTextField(
                    value = label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(if (isEng) "Sort" else "छाँटें") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sortExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = sortExpanded,
                    onDismissRequest = { sortExpanded = false }
                ) {
                    sorts.forEach { sort ->
                        val itemLabel = when (sort) {
                            "Newest" -> if (isEng) "Newest" else "नवीनतम"
                            else -> if (isEng) "A-Z" else "अ-ज्ञ"
                        }
                        DropdownMenuItem(
                            text = { Text(itemLabel) },
                            onClick = {
                                viewModel.setKnowledgeSort(sort)
                                sortExpanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            // Categories Dropdown
            var catExpanded by remember { mutableStateOf(false) }
            val categories = listOf("All", "Bhagavad Gita", "Ramcharitmanas", "Shiv Puran", "Vedas & Granths", "Festivals", "Rituals", "Mantras", "Concepts", "Gurus")

            ExposedDropdownMenuBox(
                expanded = catExpanded,
                onExpandedChange = { catExpanded = !catExpanded }
            ) {
                OutlinedTextField(
                    value = getCategoryDisplayName(selectedCat),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(if (isEng) "Category" else "श्रेणी") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = catExpanded,
                    onDismissRequest = { catExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(getCategoryDisplayName(category)) },
                            onClick = {
                                viewModel.setKnowledgeCategory(category)
                                catExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Articles Grid/List
            val filteredList = articles.filter {
                searchQuery.isEmpty() ||
                        it.title.contains(searchQuery, ignoreCase = true) ||
                        it.summary.contains(searchQuery, ignoreCase = true) ||
                        it.content.contains(searchQuery, ignoreCase = true)
            }

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEng) "No spiritual topics matched your search." else "आपकी खोज के अनुसार कोई लेख नहीं मिला।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val knowledgeListState = androidx.compose.foundation.lazy.rememberLazyListState()
                val showKnowledgeScrollToTop by remember {
                    derivedStateOf { knowledgeListState.firstVisibleItemIndex > 1 }
                }
                val knowledgeCoroutineScope = rememberCoroutineScope()

                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        state = knowledgeListState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                    itemsIndexed(filteredList) { index, article ->
                        val isLocked = userRole == "Guest" && index >= 2
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            borderColor = if (isLocked) Color.LightGray else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        if (isLocked) {
                                            viewModel.triggerGuestPromo()
                                        } else {
                                            activeArticle = article
                                        }
                                    }
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = article.category.uppercase(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    )
                                    Icon(
                                        imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.MenuBook,
                                        contentDescription = if (isLocked) "Locked" else "Read",
                                        tint = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = article.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.secondary
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = article.summary,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        lineHeight = 16.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (isLocked) {
                                            if (isEng) "Locked (Devotee Only)" else "ताला लगा है (केवल साधक)"
                                        } else {
                                            if (isEng) "Read Detailed Article" else "विस्तृत लेख पढ़ें"
                                        },
                                        color = if (isLocked) Color.Gray else Terracotta,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = if (isLocked) "🔒" else "→", color = if (isLocked) Color.Gray else Terracotta, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                if (showKnowledgeScrollToTop) {
                    androidx.compose.material3.FloatingActionButton(
                        onClick = {
                            knowledgeCoroutineScope.launch {
                                knowledgeListState.animateScrollToItem(0)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .testTag("scroll_to_top_knowledge"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Scroll to top"
                        )
                    }
                }
            }
        }
    }
}
}

@Composable
fun ArticleDetailScreen(
    article: KnowledgeArticle,
    isEnglish: Boolean,
    onBack: () -> Unit,
    onWatchVideo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = if (isEnglish) "Back to Hub" else "वापस ज्ञान कोश",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(onClick = onBack)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Traditional Scroll Layout
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.tertiary
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = article.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    DiyaDecoration(size = 24.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.tertiary)

                // Summary
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = article.summary,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Article Content
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Related Videos section
                if (article.relatedVideos.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Related Videos & Darshan" else "संबंधित वीडियो और दर्शन",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable(onClick = onWatchVideo)
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.VideoLibrary,
                                contentDescription = "Videos",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isEnglish) {
                                        "Watch divine darshan on our connected channel"
                                    } else {
                                        "हमारे जुड़े हुए चैनल पर दिव्य दर्शन देखें"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "@SanatanSadhakk",
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                            }
                            Text(text = "▶", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun CalendarScreen(onBack: () -> Unit, isEng: Boolean) {
    var selectedDate by remember { mutableStateOf(java.util.Calendar.getInstance()) }
    var selectedDateForPanchang by remember { mutableStateOf<Int?>(null) }
    val daysInMonth = selectedDate.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    val firstDayOfMonth = selectedDate.clone() as java.util.Calendar
    firstDayOfMonth.set(java.util.Calendar.DAY_OF_MONTH, 1)
    val startDayOfWeek = firstDayOfMonth.get(java.util.Calendar.DAY_OF_WEEK) - 1 // 1=Sun, ..., 7=Sat

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text(text = if (isEng) "Hindu Calendar" else "हिंदू कैलेंडर", style = MaterialTheme.typography.titleLarge)
        }
        
        // Month Navigation
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                selectedDate.add(java.util.Calendar.MONTH, -1)
                selectedDate = selectedDate.clone() as java.util.Calendar
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Prev Month")
            }
            Text(text = "${selectedDate.get(java.util.Calendar.MONTH) + 1} / ${selectedDate.get(java.util.Calendar.YEAR)}", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = {
                selectedDate.add(java.util.Calendar.MONTH, 1)
                selectedDate = selectedDate.clone() as java.util.Calendar
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        // Grid of Days
        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            // Placeholder for days of week
            items(7) { day ->
                Text(text = listOf("S", "M", "T", "W", "T", "F", "S")[day], textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
            // Blank items for start of month
            items(startDayOfWeek - 1) {
                Box(modifier = Modifier)
            }
            // Days
            items(daysInMonth) { day ->
                val dayOfMonth = day + 1
                val isToday = dayOfMonth == java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) &&
                        selectedDate.get(java.util.Calendar.MONTH) == java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) &&
                        selectedDate.get(java.util.Calendar.YEAR) == java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                
                Box(
                    modifier = Modifier.padding(4.dp)
                        .border(1.dp, if (isToday) MaterialTheme.colorScheme.primary else Color.Gray, RoundedCornerShape(4.dp))
                        .background(if (isToday) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent)
                        .clickable { 
                            selectedDateForPanchang = dayOfMonth
                        }.padding(8.dp)
                ) {
                    Text(text = dayOfMonth.toString())
                }
            }
        }
        
        // Panchang View
        Spacer(modifier = Modifier.height(16.dp))
        selectedDateForPanchang?.let { day ->
            SacredCard(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Panchang for $day/${selectedDate.get(java.util.Calendar.MONTH)+1}/${selectedDate.get(java.util.Calendar.YEAR)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (isEng) "Tithi: Pratipada | Nakshatra: Ashwini" else "तिथि: प्रतिपदा | नक्षत्र: अश्विनी")
                }
            }
        }
    }
}

// --- COMMUNITY (Reddit-style) SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(viewModel: SadhakViewModel) {
    val userRole by viewModel.userRole.collectAsState()
    val isEng by viewModel.isEnglish.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val selectedPostId by viewModel.selectedPostId.collectAsState()
    val isPublicPosting by viewModel.isPublicPostingEnabled.collectAsState()
    val currentUserRole by viewModel.userRole.collectAsState()
    
    var showCreatePostDialog by remember { mutableStateOf(false) }
    val sortBy by viewModel.communitySortBy.collectAsState()

    if (selectedPostId != null) {
        // Comment Thread view
        val activePost = posts.find { it.id == selectedPostId }
        if (activePost != null) {
            CommentThreadScreen(
                post = activePost,
                viewModel = viewModel,
                isEnglish = isEng,
                onBack = { viewModel.selectPost(null) }
            )
        } else {
            viewModel.selectPost(null)
        }
    } else {
        Scaffold(
            floatingActionButton = {
                // Post creation FAB
                Button(
                    onClick = {
                        if (userRole == "Guest") viewModel.triggerGuestPromo() else showCreatePostDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("create_post_fab")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isEng) "Share Reflection" else "विचार साझा करें", color = Color.White)
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .testTag("community_screen")
            ) {
                SacredHeader(
                    title = if (isEng) "Sadhak Community" else "साधक संघ",
                    subtitle = if (isEng) "Reddit-style sacred forum to share experiences and karma insights" else "अनुभवों और कर्म विचारों को साझा करने का पावन मंच",
                    showDiyas = true
                )

                // Sorting options
                var expanded by remember { mutableStateOf(false) }
                val sorts = listOf("New", "Top", "Discussed")
                val label = when (sortBy) {
                    "New" -> if (isEng) "New" else "नवीनतम"
                    "Top" -> if (isEng) "Top Devotional" else "लोकप्रिय"
                    else -> if (isEng) "Most Discussed" else "चर्चा में"
                }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = label,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        sorts.forEach { sort ->
                            val itemLabel = when (sort) {
                                "New" -> if (isEng) "New" else "नवीनतम"
                                "Top" -> if (isEng) "Top Devotional" else "लोकप्रिय"
                                else -> if (isEng) "Most Discussed" else "चर्चा में"
                            }
                            DropdownMenuItem(
                                text = { Text(itemLabel) },
                                onClick = {
                                    viewModel.setCommunitySort(sort)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Post listings
                if (posts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isEng) "No posts here. Tap share to create one!" else "अभी तक कोई पोस्ट नहीं है। पहली पोस्ट साझा करें!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    val communityListState = androidx.compose.foundation.lazy.rememberLazyListState()
                    val showCommunityScrollToTop by remember {
                        derivedStateOf { communityListState.firstVisibleItemIndex > 1 }
                    }
                    val communityCoroutineScope = rememberCoroutineScope()

                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = communityListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                        items(posts) { post ->
                            SacredCard(
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                borderColor = if (post.isPinned) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Header
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "@${post.author}",
                                                style = MaterialTheme.typography.labelLarge.copy(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                            if (post.isPinned) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Verified,
                                                    contentDescription = "Pinned",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(
                                                    text = if (isEng) "PINNED" else "पिन किया गया",
                                                    fontSize = 10.sp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Text(
                                            text = post.category,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Title
                                    Text(
                                        text = post.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Body preview
                                    Text(
                                        text = post.content,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                            lineHeight = 20.sp
                                        ),
                                        maxLines = 4,
                                        modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Vote count & Comments Count
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { viewModel.upvotePost(post) },
                                                modifier = Modifier.size(36.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ThumbUp,
                                                    contentDescription = "Upvote",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                            Text(
                                                text = (post.upvotes - post.downvotes).toString(),
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )
                                            IconButton(
                                                onClick = { viewModel.downvotePost(post) },
                                                modifier = Modifier.size(36.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ThumbDown,
                                                    contentDescription = "Downvote",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Comment,
                                                contentDescription = "Comments",
                                                tint = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "${post.commentsCount} ${if (isEng) "Comments" else "टिप्पणियाँ"}",
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                if (showCommunityScrollToTop) {
                    androidx.compose.material3.FloatingActionButton(
                        onClick = {
                            communityCoroutineScope.launch {
                                communityListState.animateScrollToItem(0)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .testTag("scroll_to_top_community"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Scroll to top"
                        )
                    }
                }
            }
        }

            // Write Post Dialog
            if (showCreatePostDialog) {
                var title by remember { mutableStateOf("") }
                var category by remember { mutableStateOf("Bhakti Experiences") }
                var content by remember { mutableStateOf("") }
                var statusText by remember { mutableStateOf("") }

                val canPost = isPublicPosting || currentUserRole == "SuperUser"

                Dialog(onDismissRequest = { showCreatePostDialog = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEng) "Share Reflection" else "विचार साझा करें",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                                DiyaDecoration(size = 24.dp)
                            }

                            Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))

                            // Gentle guidelines
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = if (isEng) {
                                        "🕉 Sadhak Guidelines: Speak with respect, kindness, and truth. Avoid politics, marketing, or hate speech. Let our community be a temple of peace."
                                    } else {
                                        "🕉 साधक नियम: सम्मान, दया और सत्य के साथ बोलें। राजनीति, विपणन या घृणास्पद भाषण से बचें। हमारे समुदाय को शांति का मंदिर बनने दें।"
                                    },
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (!canPost) {
                                Text(
                                    text = if (isEng) {
                                        "❌ Public posting is currently closed by the administrator. Please contact verification support in Profile or log in as administrator."
                                    } else {
                                        "❌ प्रशासक द्वारा सार्वजनिक पोस्टिंग वर्तमान में बंद है। कृपया प्रोफ़ाइल में जाकर सत्यापन का अनुरोध करें।"
                                    },
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            } else {
                                // Form
                                OutlinedTextField(
                                    value = title,
                                    onValueChange = { title = it },
                                    label = { Text(text = if (isEng) "Title" else "शीर्षक") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                )

                                // Category selector
                                Text(
                                    text = if (isEng) "Select Forum Category" else "मंच श्रेणी चुनें:",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 13.sp
                                )
                                val cats = listOf("Bhakti Experiences", "Doubts & Discussions", "Daily Karma Journal", "Temple Stories")
                                Column {
                                    cats.forEach { cat ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { category = cat }
                                        ) {
                                            RadioButton(
                                                selected = category == cat,
                                                onClick = { category = cat },
                                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                            )
                                            Text(text = cat, fontSize = 13.sp)
                                        }
                                    }
                                }

                                OutlinedTextField(
                                    value = content,
                                    onValueChange = { content = it },
                                    label = { Text(text = if (isEng) "Reflection content..." else "विचार विवरण...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 100.dp),
                                    maxLines = 6,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                )

                                if (statusText.isNotEmpty()) {
                                    Text(text = statusText, color = Color.Red, fontSize = 12.sp)
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = { showCreatePostDialog = false },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                                    ) {
                                        Text(text = if (isEng) "Cancel" else "रद्द करें")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            if (title.isBlank() || content.isBlank()) {
                                                statusText = "Please fill in all fields."
                                            } else {
                                                viewModel.createPost(title, content, category)
                                                showCreatePostDialog = false
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = if (isEng) "Publish" else "प्रकाशित करें", color = Color.White)
                                    }
                                }
                            }

                            if (!canPost) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { showCreatePostDialog = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = "OK", color = Color.White)
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
}

@Composable
fun CommentThreadScreen(
    post: CommunityPost,
    viewModel: SadhakViewModel,
    isEnglish: Boolean,
    onBack: () -> Unit
) {
    val comments by viewModel.activeComments.collectAsState()
    var newCommentText by remember { mutableStateOf("") }

    LaunchedEffect(post.id) {
        viewModel.selectPost(post.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = if (isEnglish) "Back to Community" else "वापस समुदाय",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(onClick = onBack)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main Post Card
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.primary
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "@${post.author}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = post.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEnglish) "Discussion Thread" else "चर्चा सूत्र",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Comments List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = if (isEnglish) "No comments yet. Start the dialogue with kind words!" else "कोई टिप्पणी नहीं है। मधुर शब्दों के साथ चर्चा की शुरुआत करें!",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                items(comments) { comment ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "@${comment.author}",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Just now", // Local simple representation
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Write comment input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newCommentText,
                onValueChange = { newCommentText = it },
                placeholder = {
                    Text(text = if (isEnglish) "Write a supportive comment..." else "सहायक टिप्पणी लिखें...")
                },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (newCommentText.isNotBlank()) {
                        viewModel.addPostComment(post.id, newCommentText)
                        newCommentText = ""
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}


// --- SACRED AARTI BELL CHIME ENGINE & WIDGET ---
@Composable
fun AartiBellWidget(isEng: Boolean) {
    var isSwinging by remember { mutableStateOf(false) }
    var pulseTrigger by remember { mutableStateOf(0) }
    var bellCount by remember { mutableStateOf(0) }
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current

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
                text = "${if (isEng) "Count" else "गिनती"}: $bellCount",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clickable(
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        indication = null
                    ) {
                        isSwinging = true
                        pulseTrigger += 1
                        bellCount += 1
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        // Play sound
                        com.example.ui.screens.AmbientSoundPlayer.playSound(frequency = 880.0, volume = 0.5f)
                        // Stop quickly to sound like a bell
                        kotlinx.coroutines.GlobalScope.launch {
                            kotlinx.coroutines.delay(200)
                            com.example.ui.screens.AmbientSoundPlayer.stopSound()
                        }
                    }
            ) {
                // Radiating visual pulse waves representing sound propagation
                if (pulseTrigger > 0) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer {
                                scaleX = rippleScale
                                scaleY = rippleScale
                                alpha = rippleAlpha
                            }
                            .border(3.dp, MaterialTheme.colorScheme.tertiary, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(100.dp)
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
                        .size(80.dp)
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
                            radius = 6.dp.toPx(),
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
                            radius = 7.dp.toPx(),
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
                        fontSize = 18.sp,
                        modifier = Modifier.offset(y = 8.dp)
                    )
                }
            }
        }
    }
}

fun playTempleBellSound() {
    Thread {
        try {
            val sampleRate = 44100
            val duration = 1.5
            val numSamples = (sampleRate * duration).toInt()
            val samples = FloatArray(numSamples)
            
            val f0 = 587.33f // D5 note, highly divine and bright frequency
            val harmonics = floatArrayOf(1.0f, 1.2f, 1.5f, 2.0f, 2.6f, 3.0f)
            val amplitudes = floatArrayOf(1.0f, 0.4f, 0.25f, 0.15f, 0.08f, 0.05f)
            
            for (i in 0 until numSamples) {
                val t = i.toFloat() / sampleRate
                val decay = kotlin.math.exp(-2.5 * t).toFloat() // exponential decay
                
                var sampleValue = 0f
                for (h in harmonics.indices) {
                    val freq = f0 * harmonics[h]
                    sampleValue += amplitudes[h] * kotlin.math.sin(2.0 * kotlin.math.PI * freq * t).toFloat()
                }
                samples[i] = sampleValue * decay * 0.18f
            }
            
            val buffer = ShortArray(numSamples)
            for (i in samples.indices) {
                val s = (samples[i] * 32767).toInt().coerceIn(-32768, 32767)
                buffer[i] = s.toShort()
            }
            
            val audioTrack = android.media.AudioTrack(
                android.media.AudioManager.STREAM_MUSIC,
                sampleRate,
                android.media.AudioFormat.CHANNEL_OUT_MONO,
                android.media.AudioFormat.ENCODING_PCM_16BIT,
                buffer.size * 2,
                android.media.AudioTrack.MODE_STATIC
            )
            audioTrack.write(buffer, 0, buffer.size)
            audioTrack.play()
            Thread.sleep((duration * 1000).toLong())
            audioTrack.stop()
            audioTrack.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()
}

// --- BHAKTI SADHANA SCREEN ---
@Composable
fun BhaktiSadhanaScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Ensure state defaults to valid tab for this screen
    val activeTab = if (selectedToolSubTab == "Mantras" || selectedToolSubTab == "Timer") {
        selectedToolSubTab
    } else {
        "Mantras"
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

            // Sub Navigation Tabs: only Mantras and Timer
            @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Mantras", "Timer")
                tabs.forEach { tab ->
                    val label = when (tab) {
                        "Mantras" -> if (isEng) "Prayers" else "मंत्र/आरती"
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
                "Mantras" -> MantraLibraryView(viewModel, isEng)
                "Timer" -> MeditationTimerView(isEng)
            }
        }

        // Global Scroll To Top Button
        if (scrollState.value > 150) {
            com.example.ui.components.ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp)
            )
        }
    }
}

// --- BHAKTI TOOLS SCREEN (NEW) ---
@Composable
fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()
    // Ensure state defaults to valid tab for this screen
    val activeTab = if (selectedToolSubTab == "KarmaLog" || selectedToolSubTab == "Gratitude" || selectedToolSubTab == "Counter") {
        selectedToolSubTab
    } else {
        "Counter"
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

            // Sub Navigation Tabs: Bell, Counter, KarmaLog, Gratitude
            @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
            androidx.compose.foundation.layout.FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val tabs = listOf("Bell", "Counter", "KarmaLog", "Gratitude")
                tabs.forEach { tab ->
                    val label = when (tab) {
                        "Bell" -> if (isEng) "Aarti Bell" else "आरती घंटी"
                        "KarmaLog" -> if (isEng) "Karma Tracker" else "कर्म चक्र"
                        "Gratitude" -> if (isEng) "Gratitude" else "कृतज्ञता"
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
                "Bell" -> AartiBellWidget(isEng = isEng)
                "Counter" -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
                "KarmaLog" -> KarmaTrackerView(viewModel, isEng)
                "Gratitude" -> GratitudeJournalView(viewModel, isEng)
                else -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
            }
        }

    }
}

@Composable
fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean) {
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

    var searchQuery by remember { mutableStateOf("") }

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

@Composable
fun MeditationTimerView(isEnglish: Boolean) {
    var timerRunning by remember { mutableStateOf(false) }
    var secondsLeft by remember { mutableStateOf(300) } // 5 Minutes Default
    var selectedDuration by remember { mutableStateOf(300) }
    var customDurationMins by remember { mutableStateOf(5f) }
    var selectedSoundIndex by remember { mutableStateOf(1) } // Default to Temple Bells
    var backgroundVolume by remember { mutableStateOf(0.7f) }
    var breathingPhase by remember { mutableStateOf("Inhale") }

    // Coroutine timer logic
    LaunchedEffect(timerRunning, secondsLeft) {
        if (timerRunning && secondsLeft > 0) {
            kotlinx.coroutines.delay(1000)
            secondsLeft -= 1
            
            // Sync breathing instruction (every 4 seconds)
            breathingPhase = when ((secondsLeft % 12)) {
                in 0..3 -> if (isEnglish) "Inhale Deeply (श्वास लें)" else "गहरी श्वास लें"
                in 4..7 -> if (isEnglish) "Hold Calmly (कुंभक)" else "शांत रहकर श्वास रोकें"
                else -> if (isEnglish) "Exhale Gently (श्वास छोड़ें)" else "धीरे से श्वास छोड़ें"
            }
        } else if (secondsLeft == 0) {
            timerRunning = false
            secondsLeft = selectedDuration
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
                        progress = secondsLeft.toFloat() / selectedDuration,
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
                            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (timerRunning) breathingPhase else "🕉",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val mins = secondsLeft / 60
                            val secs = secondsLeft % 60
                            Text(
                                text = String.format("%02d:%02d", mins, secs),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            )
                        }
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
                        Pair("None (Silent)", "मौन"),
                        Pair("Temple Bells 🔔", "मंदिर घंटियाँ 🔔"),
                        Pair("Divine OM 🕉", "दिव्य ॐ ध्वनि 🕉"),
                        Pair("Cosmic Flute 🪈", "बांसुरी धुन 🪈"),
                        Pair("Ganga Flow 🌊", "गंगा प्रवाह 🌊")
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
                        Pair("Ganga Flow 🌊", "गंगा 🌊")
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        items(sounds.size) { index ->
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

                // Action Buttons
                Row {
                    LaunchedEffect(timerRunning, selectedSoundIndex, backgroundVolume) {
                        if (timerRunning && selectedSoundIndex > 0) {
                            val freq = when (selectedSoundIndex) {
                                1 -> 432.0 // Temple Bells pseudo
                                2 -> 136.1 // Om
                                3 -> 528.0 // Flute
                                4 -> 396.0 // Water
                                else -> 432.0
                            }
                            AmbientSoundPlayer.playSound(freq, backgroundVolume)
                        } else {
                            AmbientSoundPlayer.stopSound()
                        }
                    }
                    Button(
                        onClick = {
                            timerRunning = !timerRunning
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.testTag("meditation_start_pause_button")
                    ) {
                        Text(
                            text = if (timerRunning) {
                                if (isEnglish) "Pause" else "रोकें"
                            } else {
                                if (isEnglish) "Start Dhyana" else "ध्यान शुरू करें"
                            },
                            color = Color.White
                        )
                    }
                    
                    if (timerRunning || secondsLeft != selectedDuration) {
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedButton(
                            onClick = {
                                timerRunning = false
                                secondsLeft = selectedDuration
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.testTag("meditation_reset_button")
                        ) {
                            Text(text = if (isEnglish) "Reset" else "पुनः सेट करें")
                        }
                    }
                }
            }
        }
    }
}

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
                    Text(text = if (isEnglish) "Log Deed" else "दर्ज करें", color = Color.White)
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
                    Text(text = if (isEnglish) "Save Reflection" else "सुरक्षित करें", color = Color.White)
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


// --- VIDEO HUB SCREEN ---
@Composable
fun VideoHubScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val videos by viewModel.videos.collectAsState()
    val selectedCat by viewModel.selectedVideoCategory.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    var activeVideoUrl by remember { mutableStateOf<String?>(null) }
    var activeVideoTitle by remember { mutableStateOf("") }

    val categories = listOf("All", "Darshan", "Puja", "Stories")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("video_hub_screen")
    ) {
        SacredHeader(
            title = if (isEng) "Sacred Video Hub" else "दर्शन तरंगिणी",
            subtitle = if (isEng) "Watch divine Darshan and Puja from @SanatanSadhakk" else "चैनल @SanatanSadhakk के पवित्र आरती, दर्शन और भक्ति प्रसंग देखें",
            showDiyas = true
        )

        // Categories selector
        @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
        androidx.compose.foundation.layout.FlowRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                val isSelected = selectedCat == category
                val label = when (category) {
                    "All" -> if (isEng) "All Videos" else "सभी वीडियो"
                    "Darshan" -> if (isEng) "Darshan" else "देव दर्शन (Darshan)"
                    "Puja" -> if (isEng) "Puja & Aarti" else "पूजा व आरती (Puja)"
                    else -> if (isEng) "Stories & Kathas" else "कथा प्रसंग (Stories)"
                }
                CategoryTab(
                    text = label,
                    isSelected = isSelected,
                    onClick = { viewModel.setVideoCategory(category) },
                    testTagStr = "video_category_tab_$category"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Embedded Mock video player if a video is playing
        if (activeVideoUrl != null) {
            SacredCard(
                backgroundColor = TempleDarkBackground,
                borderColor = MaterialTheme.colorScheme.tertiary
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = activeVideoTitle,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { activeVideoUrl = null },
                            colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                        ) {
                            Text(text = "✕", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Real Embedded Video player
                    androidx.compose.ui.viewinterop.AndroidView(
                        factory = { context ->
                            android.webkit.WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.mediaPlaybackRequiresUserGesture = false
                                webChromeClient = android.webkit.WebChromeClient()
                                val html = """
                                    <html>
                                    <body style="margin:0;padding:0;background-color:black;">
                                        <iframe width="100%" height="100%" src="https://www.youtube.com/embed/${activeVideoUrl}?autoplay=1&rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
                                    </body>
                                    </html>
                                """.trimIndent()
                                loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null)
                            }
                        },
                        update = { webView ->
                            // Update when activeVideoUrl changes
                            val html = """
                                <html>
                                <body style="margin:0;padding:0;background-color:black;">
                                    <iframe width="100%" height="100%" src="https://www.youtube.com/embed/${activeVideoUrl}?autoplay=1&rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
                                </body>
                                </html>
                            """.trimIndent()
                            webView.loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isEng) "Streaming with high quality sound..." else "हाई क्वालिटी साउंड के साथ स्ट्रीमिंग...",
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // List of videos
        if (videos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isEng) "No videos found in this category." else "इस श्रेणी में कोई वीडियो नहीं मिला।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val videoListState = androidx.compose.foundation.lazy.rememberLazyListState()
            val showVideoScrollToTop by remember {
                derivedStateOf { videoListState.firstVisibleItemIndex > 1 }
            }
            val videoCoroutineScope = rememberCoroutineScope()

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    state = videoListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                itemsIndexed(videos) { index, video ->
                    val isLocked = userRole == "Guest" && index >= 2
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = if (isLocked) Color.LightGray else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    ) {
                        Column {
                            // Beautiful Mock Thumbnail with Play overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(
                                                if (isLocked) Color.LightGray.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                                if (isLocked) Color.Gray.copy(alpha = 0.15f) else MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                // Draw stylized Om symbol in background
                                Text(
                                    text = "🕉",
                                    fontSize = 72.sp,
                                    color = if (isLocked) Color.Gray.copy(alpha = 0.12f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                    fontWeight = FontWeight.Bold
                                )

                                IconButton(
                                    onClick = {
                                        if (isLocked) {
                                            viewModel.triggerGuestPromo()
                                        } else {
                                            activeVideoUrl = video.videoId
                                            activeVideoTitle = video.title
                                        }
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(containerColor = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)),
                                    modifier = Modifier.size(54.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.PlayArrow,
                                        contentDescription = if (isLocked) "Locked" else "Play",
                                        tint = Color.White,
                                        modifier = Modifier.size(if (isLocked) 24.dp else 32.dp)
                                    )
                                }
                            }

                            // Details
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = if (isLocked) {
                                        if (isEng) "LOCKED (DEVOTEE ONLY)" else "ताला लगा है (केवल साधक)"
                                    } else {
                                        video.category.uppercase()
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = video.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.secondary
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = video.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        lineHeight = 16.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            if (showVideoScrollToTop) {
                    androidx.compose.material3.FloatingActionButton(
                        onClick = {
                            videoCoroutineScope.launch {
                                videoListState.animateScrollToItem(0)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .testTag("scroll_to_top_videos"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = androidx.compose.ui.graphics.Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = "Scroll to top"
                        )
                    }
                }
            }
        }
    }
}


// --- PROFILE / ACCOUNT / LOGIN SCREEN ---
@Composable
fun ProfileScreen(viewModel: SadhakViewModel, onNavigateAdmin: () -> Unit) {
    val isEng by viewModel.isEnglish.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userAvatar by viewModel.userAvatar.collectAsState()
    val userProfileImageUri by viewModel.userProfileImageUri.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()
    val userMobile by viewModel.userMobile.collectAsState()
    val userCity by viewModel.userCity.collectAsState()
    val userStateProvince by viewModel.userStateProvince.collectAsState()
    
    var isEditingProfile by remember { mutableStateOf(false) }

    if (isEditingProfile) {
        EditProfileScreen(
            viewModel = viewModel,
            onBack = { isEditingProfile = false }
        )
        return
    }

    // LOGIN LOGIC
    if (userRole == "Guest") {
        var showSignUp by remember { mutableStateOf(false) }
        var emailInput by remember { mutableStateOf("") }
        var passInput by remember { mutableStateOf("") }
        var confirmPassInput by remember { mutableStateOf("") }
        var nameInput by remember { mutableStateOf("") }
        var cityInput by remember { mutableStateOf("") }
        var stateInput by remember { mutableStateOf("") }
        var statusText by remember { mutableStateOf("") }
        var rememberMe by remember { mutableStateOf(false) }
        val isLoading by viewModel.isLoading.collectAsState()

        // Three hold states for passwords
        var isPassVisible by remember { mutableStateOf(false) }
        var isConfirmPassVisible by remember { mutableStateOf(false) }
        var isLoginPassVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp).testTag("profile_login_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "logo")
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(3000, easing = androidx.compose.animation.core.LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "rotation"
                    )

                    com.example.SanatanSadhakLogo(modifier = Modifier
                        .size(160.dp)
                        .graphicsLayer(rotationZ = rotation)
                    )
                    Text(
                        text = "Sanatan Sadhak",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 22.sp,
                            letterSpacing = 0.5.sp
                        )
                    )
                    
                    Text(
                        text = if (showSignUp) {
                            if (isEng) "Create Devotee Account" else "नया भक्त खाता बनाएं"
                        } else {
                            if (isEng) "Devotee Login" else "भक्त प्रवेश"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                    )

                    if (showSignUp) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text(text = if (isEng) "Full Name" else "पूरा नाम") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text(text = if (isEng) "Devotee Email" else "भक्त ईमेल") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            singleLine = true
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = cityInput,
                                onValueChange = { cityInput = it },
                                label = { Text(text = if (isEng) "City" else "शहर") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = stateInput,
                                onValueChange = { stateInput = it },
                                label = { Text(text = if (isEng) "State" else "राज्य") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                                singleLine = true
                            )
                        }

                        OutlinedTextField(
                            value = passInput,
                            onValueChange = { passInput = it },
                            label = { Text(text = if (isEng) "New Password" else "नया पासवर्ड") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = {
                                                try {
                                                    isPassVisible = true
                                                    awaitRelease()
                                                } finally {
                                                    isPassVisible = false
                                                }
                                            }
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Hold to see password",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = confirmPassInput,
                            onValueChange = { confirmPassInput = it },
                            label = { Text(text = if (isEng) "Confirm Password" else "पासवर्ड की पुष्टि करें") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            visualTransformation = if (isConfirmPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = {
                                                try {
                                                    isConfirmPassVisible = true
                                                    awaitRelease()
                                                } finally {
                                                    isConfirmPassVisible = false
                                                }
                                            }
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isConfirmPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Hold to see password",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            singleLine = true
                        )
                    } else {
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text(text = if (isEng) "Devotee Email" else "भक्त ईमेल") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = passInput,
                            onValueChange = { passInput = it },
                            label = { Text(text = if (isEng) "Password" else "पासवर्ड") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                            visualTransformation = if (isLoginPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(
                                    onClick = {},
                                    modifier = Modifier.pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = {
                                                try {
                                                    isLoginPassVisible = true
                                                    awaitRelease()
                                                } finally {
                                                    isLoginPassVisible = false
                                                }
                                            }
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isLoginPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Hold to see password",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            singleLine = true
                        )
                    }

                    if (!showSignUp) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { rememberMe = !rememberMe }
                        ) {
                            androidx.compose.material3.Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = androidx.compose.material3.CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEng) "Remember Me" else "मुझे याद रखें",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 14.sp
                            )
                        }
                    }

                    if (statusText.isNotEmpty()) {
                        Text(text = statusText, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            statusText = ""
                            if (emailInput.isNotBlank() && passInput.isNotBlank()) {
                                if (showSignUp) {
                                    if (nameInput.isBlank()) {
                                        statusText = if (isEng) "Name required" else "नाम आवश्यक है"
                                        return@Button
                                    }
                                    if (cityInput.isBlank()) {
                                        statusText = if (isEng) "City required" else "शहर आवश्यक है"
                                        return@Button
                                    }
                                    if (stateInput.isBlank()) {
                                        statusText = if (isEng) "State required" else "राज्य आवश्यक है"
                                        return@Button
                                    }
                                    if (passInput != confirmPassInput) {
                                        statusText = if (isEng) "Passwords do not match" else "पासवर्ड मेल नहीं खाते हैं"
                                        return@Button
                                    }
                                    viewModel.signUp(emailInput, passInput, nameInput, cityInput, stateInput) { success, msg ->
                                        if (!success) statusText = msg
                                    }
                                } else {
                                    viewModel.login(emailInput, passInput, rememberMe) { success, msg ->
                                        if (!success) statusText = msg
                                    }
                                }
                            } else {
                                statusText = if (isEng) "Email and Password required" else "ईमेल और पासवर्ड आवश्यक हैं"
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                        } else {
                            Text(text = if (showSignUp) (if (isEng) "Register" else "रजिस्टर करें") else (if (isEng) "Login" else "प्रवेश करें"), color = Color.White)
                        }
                    }

                    Text(
                        text = if (showSignUp) {
                            if (isEng) "Already have an account? Login" else "पहले से खाता है? प्रवेश करें"
                        } else {
                            if (isEng) "New Devotee? Create an Account" else "नए भक्त? खाता बनाएं"
                        },
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                showSignUp = !showSignUp
                                statusText = ""
                            }
                    )
                }
            }
        }
    } else {
        // ACCOUNT PAGE (Logged In)
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (userProfileImageUri.isNotBlank()) {
                        coil.compose.AsyncImage(
                            model = userProfileImageUri,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(100.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        val avatarColor = when (userAvatar) {
                            4 -> MaterialTheme.colorScheme.primary
                            3 -> Terracotta
                            2 -> SageGreen
                            else -> MaterialTheme.colorScheme.secondary
                        }
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(avatarColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (userRole == "SuperUser") "👑" else "🕉",
                                fontSize = 48.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { isEditingProfile = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Role",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (userRole == "SuperUser") {
                                if (isEng) "Verified Channel Owner (Super User)" else "सत्यापित स्वामी (सुपर यूजर)"
                            } else {
                                if (isEng) "Verified Sadhak" else "सत्यापित भक्त"
                            },
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 12.sp
                        )
                    }

                    // Location & Contact Info
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = userEmail,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily.Monospace
                        )
                        if (userMobile.isNotEmpty()) {
                            Text(
                                text = "${if (isEng) "Mobile:" else "मोबाइल:"} $userMobile",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (userCity.isNotEmpty() || userStateProvince.isNotEmpty()) {
                            Text(
                                text = "📍 $userCity, $userStateProvince",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    val karmaPoints by viewModel.karmaPoints.collectAsState()
                    val dailyStreak by viewModel.dailyStreak.collectAsState()
                    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Karma Points", fontWeight = FontWeight.Bold, color = SageGreen, fontSize = 11.sp)
                            Text(text = "$karmaPoints ✨", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Daily Streak", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
                            Text(text = "$dailyStreak Days 🔥", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Setting Tiles
                    SettingToggleTile(
                        title = if (isEng) "Enable Notifications" else "सूचनाएं सक्षम करें",
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications() },
                        icon = Icons.Default.Notifications
                    )

                    // Navigation Tiles
                    // TODO: Navigate to Theme settings
                    SettingActionTile(
                        title = if (isEng) "App Theme" else "ऐप थीम",
                        icon = Icons.Default.Palette,
                        onClick = { viewModel.toggleTheme() }
                    )
                    
                    if (userRole == "SuperUser") {
                        SettingActionTile(
                            title = if (isEng) "Admin Control Panel" else "एडमिन कंट्रोल पैनल",
                            icon = Icons.Default.AdminPanelSettings,
                            onClick = onNavigateAdmin
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.logoutUser() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (isEng) "Logout from Temple" else "मंदिर से प्रस्थान करें", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun EditProfileScreen(viewModel: SadhakViewModel, onBack: () -> Unit) {
    val isEng by viewModel.isEnglish.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userAvatar by viewModel.userAvatar.collectAsState()
    val userProfileImageUri by viewModel.userProfileImageUri.collectAsState()
    val userMobile by viewModel.userMobile.collectAsState()
    val userCity by viewModel.userCity.collectAsState()
    val userStateProvince by viewModel.userStateProvince.collectAsState()

    var editName by remember { mutableStateOf(userName) }
    var editMobile by remember { mutableStateOf(userMobile) }
    var editCity by remember { mutableStateOf(userCity) }
    var editState by remember { mutableStateOf(userStateProvince) }
    var editAvatar by remember { mutableStateOf(userAvatar) }
    var editImageUri by remember { mutableStateOf(userProfileImageUri) }

    val photoPickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                editImageUri = uri.toString()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = if (isEng) "Edit Profile Details" else "प्रोफ़ाइल विवरण संपादित करें",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
            )
        }

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (editImageUri.isNotBlank()) {
                    coil.compose.AsyncImage(
                        model = editImageUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(100.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.primary, CircleShape).clickable {
                            photoPickerLauncher.launch(androidx.activity.result.PickVisualMediaRequest(androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                } else {
                    val avatarColor = when (editAvatar) {
                        4 -> MaterialTheme.colorScheme.primary
                        3 -> Terracotta
                        2 -> SageGreen
                        else -> MaterialTheme.colorScheme.secondary
                    }
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(avatarColor)
                            .clickable {
                                photoPickerLauncher.launch(androidx.activity.result.PickVisualMediaRequest(androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🕉", fontSize = 48.sp)
                    }
                }
                
                Text(
                    text = if (isEng) "Tap image to upload new photo" else "नई तस्वीर अपलोड करने के लिए छवि पर टैप करें",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = editName,
                    onValueChange = { editName = it },
                    label = { Text(text = if (isEng) "Full Name" else "पूरा नाम") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                    singleLine = true
                )
                OutlinedTextField(
                    value = editMobile,
                    onValueChange = { editMobile = it },
                    label = { Text(text = if (isEng) "Mobile Number" else "मोबाइल नंबर") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                    singleLine = true
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = editCity,
                        onValueChange = { editCity = it },
                        label = { Text(text = if (isEng) "City" else "शहर") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = editState,
                        onValueChange = { editState = it },
                        label = { Text(text = if (isEng) "State" else "राज्य") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                        singleLine = true
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (isEng) "Cancel" else "रद्द करें", color = MaterialTheme.colorScheme.secondary)
                    }
                    Button(
                        onClick = {
                            if (editName.isNotBlank()) {
                                viewModel.updateUserProfileDetails(editName, editMobile, editCity, editState, editAvatar, editImageUri) { success ->
                                    if (success) onBack()
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (isEng) "Save Changes" else "सुरक्षित करें", color = Color.White)
                    }
                }
            }
        }
    }
}





