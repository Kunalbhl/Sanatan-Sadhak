package com.example.ui.screens
import com.example.SanatanSadhakLogo
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.combinedClickable

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
import androidx.compose.material.icons.automirrored.filled.Send
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

@Suppress("DEPRECATION")
fun Modifier.clickableWithFeedback(
    onClick: () -> Unit
): Modifier = this.composed {
    val interactionSource = androidx.compose.runtime.remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = androidx.compose.material3.ripple(),
            onClick = onClick
        )
}

fun Modifier.touchFeedback(): Modifier = this.composed {
    val interactionSource = androidx.compose.runtime.remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

@Composable
fun ScrollToTopButton(
    visible: Boolean,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(300)),
        exit = androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(300)),
        modifier = modifier
    ) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val isHovered by interactionSource.collectIsHoveredAsState()
        val targetAlpha = if (isPressed || isHovered) 0.85f else 0.5f
        val animatedAlpha by androidx.compose.animation.core.animateFloatAsState(
            targetValue = targetAlpha,
            animationSpec = androidx.compose.animation.core.tween(200),
            label = "alpha"
        )

        Box(
            modifier = Modifier
                .padding(bottom = 24.dp, start = 24.dp)
                .size(40.dp)
                .graphicsLayer(alpha = animatedAlpha)
                .background(Color(0xFFE65C00), CircleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .testTag(testTag),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "›",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier
                    .graphicsLayer(rotationZ = -90f)
                    .offset(y = (-2).dp)
            )
        }
    }
}

// --- HOME SCREEN ---
@Composable
fun MantraCard(mantra: DailyMantra, isEng: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = if (isEng) "Daily Mantra" else "दैनिक महामंत्र",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = mantra.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = (if (isEng) "Meaning: " else "अर्थ: ") + mantra.meaning, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            if (mantra.audioUrl.isNotEmpty()) {
                val uriHandler = LocalUriHandler.current
                Button(onClick = { uriHandler.openUri(mantra.audioUrl) }) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isEng) "Listen" else "सुनें")
                }
            }
        }
    }
}

@Composable
fun ShlokaCard(shloka: Shloka, isEng: Boolean, onPlayTts: (() -> Unit)? = null) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isEng) "Daily Shloka" else "दैनिक श्लोक",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                if (onPlayTts != null) {
                    IconButton(
                        onClick = onPlayTts,
                        modifier = Modifier.size(28.dp).background(MaterialTheme.colorScheme.primary.copy(alpha=0.1f), CircleShape)
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Play Shloka", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = shloka.verse, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = shloka.meaning, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = shloka.source, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun HomeScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val dailyMantra = viewModel.dailyMantra.collectAsState().value
    val shlokaOfTheDay = viewModel.shlokaOfTheDay.collectAsState().value
    val todayPanchang by viewModel.todayDetailedPanchang.collectAsState()
    val panchangError by viewModel.panchangError.collectAsState()
    val panchangTechnicalError by viewModel.panchangTechnicalError.collectAsState()
    val panchangLastUpdated by viewModel.panchangLastUpdated.collectAsState()
    
    val context = androidx.compose.ui.platform.LocalContext.current
    var tts: android.speech.tts.TextToSpeech? by remember { mutableStateOf(null) }
    var isTtsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        tts = android.speech.tts.TextToSpeech(context) { status ->
            if (status == android.speech.tts.TextToSpeech.SUCCESS) {
                // Try Sanskrit (sa-IN) or Hindi (hi-IN)
                val result = tts?.setLanguage(java.util.Locale("sa", "IN"))
                if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(java.util.Locale("hi", "IN"))
                }
                tts?.setPitch(0.85f)
                tts?.setSpeechRate(0.80f)
                isTtsReady = true
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }
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
    val currentKarana by viewModel.currentKarana.collectAsState()
    val currentRashi by viewModel.currentRashi.collectAsState()
    val currentSunrise by viewModel.currentSunrise.collectAsState()
    val currentSunset by viewModel.currentSunset.collectAsState()
    val currentRahukaal by viewModel.currentRahukaal.collectAsState()
    val currentAbhijeet by viewModel.currentAbhijeet.collectAsState()
    val currentDetails by viewModel.currentDetails.collectAsState()
    
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
        derivedStateOf { homeListState.firstVisibleItemIndex > 0 || homeListState.firstVisibleItemScrollOffset > 300 }
    }
    val homeCoroutineScope = rememberCoroutineScope()

    var showPanchangDetails by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }

    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val isDesktop = screenWidthDp >= 1024
    val isTablet = screenWidthDp in 768..1023
    val isMobile = screenWidthDp < 768

    val containerPadding = if (isDesktop) 32.dp else 16.dp
    val gridSpacing = if (isDesktop) 24.dp else 16.dp

    var selectedShrineForPreview by remember { mutableStateOf<com.example.data.DeityShrine?>(null) }
    var selectedVideoToPlay by remember { mutableStateOf<Triple<String, String, String>?>(null) }

    val localShrines = remember {
        listOf(
            com.example.data.DeityShrine(
                "Maa Annapurna",
                "माँ अन्नपूर्णा",
                "https://upload.wikimedia.org/wikipedia/commons/e/e4/Annapurna_devi.jpg",
                "Jai Maa Annapurna!",
                "जय माँ अन्नपूर्णा!",
                "Giver of infinite nourishment and maternal warmth."
            ),
            com.example.data.DeityShrine(
                "Mahadev Shiva",
                "महादेव शिव",
                "https://upload.wikimedia.org/wikipedia/commons/3/30/Raja_Ravi_Varma_-_Shiva_the_destroyer.jpg",
                "Jai Bholenath!",
                "जय भोलेनाथ!",
                "The cosmic silent Yogi, bestower of ultimate peace."
            ),
            com.example.data.DeityShrine(
                "Hanuman Ji",
                "हनुमान जी",
                "https://upload.wikimedia.org/wikipedia/commons/c/cb/Sri_Hanuman_Lanka.jpg",
                "Jai Bajrangbali!",
                "जय बजरंगबली!",
                "The protective, fearless defender of true devotees."
            ),
            com.example.data.DeityShrine(
                "Lord Ganesha",
                "भगवान गणेश",
                "https://upload.wikimedia.org/wikipedia/commons/e/eb/Ganesha_painting.jpg",
                "Jai Shri Ganesha!",
                "जय श्री गणेश!",
                "Siddhivinayak Ganesha is the remover of obstacles, worshipped first in all auspicious rituals."
            ),
            com.example.data.DeityShrine(
                "Radha Krishna",
                "राधा कृष्ण",
                "https://upload.wikimedia.org/wikipedia/commons/c/cb/Radha_and_Krishna_Raja_Ravi_Varma.jpg",
                "Jai Radha Krishna!",
                "जय राधा कृष्ण!",
                "The supreme guide of love and compassion, guiding pure souls."
            ),
            com.example.data.DeityShrine(
                "Durga Maa",
                "दुर्गा माँ",
                "https://upload.wikimedia.org/wikipedia/commons/a/a2/Durga_on_her_vahana_tiger.jpg",
                "Jai Mata Di!",
                "जय माता दी!",
                "The warrior goddess, representing the supreme power of protective mother."
            ),
            com.example.data.DeityShrine(
                "Lakshmi Maa",
                "लक्ष्मी माँ",
                "https://upload.wikimedia.org/wikipedia/commons/1/15/Goddess_Lakshmi_painting.jpg",
                "Jai Maa Lakshmi!",
                "जय माँ लक्ष्मी!",
                "Goddess of wealth, fortune, and prosperity, both material and spiritual."
            ),
            com.example.data.DeityShrine(
                "Saraswati Maa",
                "सरस्वती माँ",
                "https://upload.wikimedia.org/wikipedia/commons/1/12/Saraswati_by_Raja_Ravi_Varma_original.jpg",
                "Jai Maa Saraswati!",
                "जय माँ सरस्वती!",
                "Goddess of knowledge, music, art, speech, wisdom, and learning."
            )
        )
    }

    val localVideos = remember {
        listOf(
            Triple(
                "Maa Annapurna Daily Shringar Aarti",
                "https://images.unsplash.com/photo-1561361058-c24cecae35ca?auto=format&fit=crop&q=80&w=400",
                "05:12"
            ),
            Triple(
                "Kedarnath Temple Evening Shiv Tandav",
                "https://images.unsplash.com/photo-1605649487212-47bdab064df7?auto=format&fit=crop&q=80&w=400",
                "08:45"
            ),
            Triple(
                "Complete Chapter 1 Bhagavad Gita Shlokas",
                "https://images.unsplash.com/photo-1545079200-224497f14012?auto=format&fit=crop&q=80&w=400",
                "15:20"
            )
        )
    }

    val announcementBannerContent = @Composable {
        if (showVerifiedPrompt) {
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

    val servicesContent = @Composable {
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
            Triple(if (isEng) "Instagram Updates" else "इंस्टाग्राम अपडेट", Icons.Default.PhotoCamera, "InstagramUpdates"),
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

    val panchangCardContent = @Composable {
        val formattedDate = remember(isEng) {
            val sdf = if (isEng) {
                java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", java.util.Locale.ENGLISH)
            } else {
                java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", java.util.Locale("hi"))
            }
            sdf.format(System.currentTimeMillis())
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (isEng) "Today's Panchang" else "आज का पंचांग",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (panchangError != null) {
                SacredCard(backgroundColor = MaterialTheme.colorScheme.errorContainer) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = panchangError ?: "", color = MaterialTheme.colorScheme.onErrorContainer, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadDailyPanchangAndThought() }) {
                            Text(text = if (isEng) "Retry" else "पुनः प्रयास करें")
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.clickable { showPanchangDetails = true }) {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            val infiniteTransition = rememberInfiniteTransition(label = "sun_anim")
                            val scaleValue by infiniteTransition.animateFloat(
                                initialValue = 0.9f,
                                targetValue = 1.15f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1200, easing = EaseInOutSine),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "scale"
                            )
                            val rotateValue by infiniteTransition.animateFloat(
                                initialValue = -10f,
                                targetValue = 10f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(2500, easing = EaseInOutSine),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "rotate"
                            )

                            // Current Date
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Date Icon",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = formattedDate,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (panchangLastUpdated.isNotEmpty()) {
                                    Text(
                                        text = "Last updated: $panchangLastUpdated",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                    )
                                }
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Sunrise",
                                        tint = Color(0xFFFFC107),
                                        modifier = Modifier
                                            .size(20.dp)
                                            .graphicsLayer(scaleX = scaleValue, scaleY = scaleValue, rotationZ = rotateValue)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isEng) "Sunrise:" else "सूर्योदय:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 13.sp
                                    )
                                }
                                Text(
                                    text = currentSunrise,
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Timer,
                                        contentDescription = "Sunset",
                                        tint = Color(0xFFFF5722),
                                        modifier = Modifier
                                            .size(20.dp)
                                            .graphicsLayer(scaleX = scaleValue, scaleY = scaleValue, rotationZ = -rotateValue)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isEng) "Sunset:" else "सूर्यास्त:",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 13.sp
                                    )
                                }
                                Text(
                                    text = currentSunset,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    val thoughtCardContent = @Composable {
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            borderColor = Color(0xFFFFE4C4)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val parts = currentThought.split("\n")
                val sanskritVerse = if (parts.isNotEmpty()) parts[0].trim() else currentThought
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
                        text = if (isEng) "DAILY INSPIRATION" else "दैनिक प्रेरणा",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrassTan,
                            letterSpacing = 1.sp,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    if (isTtsReady) {
                        IconButton(
                            onClick = {
                                tts?.speak(sanskritVerse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null)
                            },
                            modifier = Modifier.size(28.dp).background(MaterialTheme.colorScheme.primary.copy(alpha=0.1f), CircleShape)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Play Shloka", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
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

    val communityHighlightsContent = @Composable {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Recent Reflections" else "हालिया साधक विचार",
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
    }

    val liveDarshanGalleryContent = @Composable {
        val infiniteTransition = rememberInfiniteTransition(label = "halo")
        val haloAlpha by infiniteTransition.animateFloat(
            initialValue = 0.4f,
            targetValue = 0.9f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = EaseInOutSine),
                repeatMode = RepeatMode.Reverse
            ),
            label = "haloAlpha"
        )
        val haloBorderColor = Color(0xFFD4AF37).copy(alpha = haloAlpha)

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Live Shrines Darshan" else "पवित्र धाम लाइव दर्शन",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Red)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text("LIVE", color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (isDesktop) {
                Column(verticalArrangement = Arrangement.spacedBy(gridSpacing)) {
                    val chunked = localShrines.chunked(4)
                    chunked.forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                        ) {
                            rowItems.forEach { shrine ->
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(2.dp, haloBorderColor, RoundedCornerShape(12.dp))
                                        .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp), ambientColor = Color(0xFFD4AF37), spotColor = Color(0xFFD4AF37))
                                        .clickableWithFeedback { selectedShrineForPreview = shrine },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(4f / 5f)
                                        ) {
                                            coil.compose.AsyncImage(
                                                model = shrine.imageUrl,
                                                contentDescription = shrine.nameEn,
                                                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(12.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "${shrine.nameHi} / ${shrine.nameEn}",
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = if (isEng) shrine.captionEn else shrine.captionHi,
                                                color = Color(0xFFE65C00),
                                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                            if (rowItems.size < 4) {
                                repeat(4 - rowItems.size) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            } else {
                val lazyRowListState = androidx.compose.foundation.lazy.rememberLazyListState()
                androidx.compose.foundation.lazy.LazyRow(
                    state = lazyRowListState,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gridSpacing),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp),
                    flingBehavior = androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(lazyListState = lazyRowListState)
                ) {
                    items(localShrines) { shrine ->
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .border(2.dp, haloBorderColor, RoundedCornerShape(12.dp))
                                .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp), ambientColor = Color(0xFFD4AF37), spotColor = Color(0xFFD4AF37))
                                .clickableWithFeedback { selectedShrineForPreview = shrine },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(4f / 5f)
                                ) {
                                    coil.compose.AsyncImage(
                                        model = shrine.imageUrl,
                                        contentDescription = shrine.nameEn,
                                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${shrine.nameHi} / ${shrine.nameEn}",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isEng) shrine.captionEn else shrine.captionHi,
                                        color = Color(0xFFE65C00),
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val videoFeedContent = @Composable {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Divine Video Stream" else "दिव्य सत्संग एवं आरती",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
                Text(
                    text = if (isEng) "Watch More" else "अधिक देखें",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.clickable { viewModel.setScreen("Videos") }
                )
            }

            if (isDesktop) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                ) {
                    localVideos.forEach { video ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(180.dp)
                                .clickable { selectedVideoToPlay = video },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                coil.compose.AsyncImage(
                                    model = video.second,
                                    contentDescription = video.first,
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f))
                                )
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.8f))
                                        .align(Alignment.Center),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = video.third, color = Color.White, fontSize = 10.sp)
                                }
                                Text(
                                    text = video.first,
                                    color = Color.White,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }
            } else {
                androidx.compose.foundation.lazy.LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(gridSpacing),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
                ) {
                    items(localVideos) { video ->
                        Card(
                            modifier = Modifier
                                .width(260.dp)
                                .height(150.dp)
                                .clickable { selectedVideoToPlay = video },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                coil.compose.AsyncImage(
                                    model = video.second,
                                    contentDescription = video.first,
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f))
                                )
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.8f))
                                        .align(Alignment.Center),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(6.dp)
                                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(text = video.third, color = Color.White, fontSize = 9.sp)
                                }
                                Text(
                                    text = video.first,
                                    color = Color.White,
                                    maxLines = 2,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    val dailyDarshanFeaturedDeityContent = @Composable {
        val shrineLocation = when (deityIndex) {
            0 -> if (isEng) "Kashi Vishwanath Dhaam, Varanasi" else "काशी विश्वनाथ धाम, वाराणसी"
            1 -> if (isEng) "Sindari, Rajasthan" else "सिंदरी, राजस्थान"
            else -> if (isEng) "Kedarnath Dhaam, Himalayas" else "केदारनाथ धाम, हिमालय"
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = if (isEng) "Daily Darshan" else "दैनिक दर्शन",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.outline
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(176.dp)
                            .background(Brush.verticalGradient(colors = deityColors))
                    ) {
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

                        Icon(
                            imageVector = Icons.Filled.SelfImprovement,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.12f),
                            modifier = Modifier
                                .size(110.dp)
                                .align(Alignment.Center)
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
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
                }
            }
        }
    }

    val quickBhaktiToolsContent = @Composable {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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


    val todayPanchangForDetail by viewModel.todayDetailedPanchangForDetail.collectAsState()
    LaunchedEffect(showPanchangDetails) {
        if (showPanchangDetails) {
            viewModel.loadDetailPanchang()
        }
    }

    if (showPanchangDetails) {
        val p = todayPanchangForDetail ?: todayPanchang
        val displayTithi = todayPanchangForDetail?.tithi ?: currentTithi
        val displayNakshatra = todayPanchangForDetail?.nakshatra ?: currentNakshatra
        val displayYoga = todayPanchangForDetail?.yoga ?: currentYoga
        val displayKarana = todayPanchangForDetail?.karana ?: currentKarana
        val displayRashi = todayPanchangForDetail?.rashi ?: currentRashi
        val displaySunrise = todayPanchangForDetail?.sunrise ?: currentSunrise
        val displaySunset = todayPanchangForDetail?.sunset ?: currentSunset
        val displayAbhijeet = todayPanchangForDetail?.abhijeet ?: currentAbhijeet
        val displayRahukaal = todayPanchangForDetail?.rahukaal ?: currentRahukaal
        val displayFestival = todayPanchangForDetail?.festival ?: currentFestival
        val displayDetails = todayPanchangForDetail?.details ?: currentDetails

        // Detailed Panchang Dialog
        AlertDialog(
            onDismissRequest = { showPanchangDetails = false },
            title = { 
                Text(
                    text = if (isEng) "Vedic Panchang Details" else "वैदिक पंचांग विवरण",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            text = {
                androidx.compose.foundation.lazy.LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        Text(
                            text = if (isEng) "Core Panchang Elements" else "मुख्य पंचांग तत्व",
                            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Tithi (तिथि):" else "तिथि:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayTithi, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Nakshatra (नक्षत्र):" else "नक्षत्र:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayNakshatra, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Yoga (योग):" else "योग:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayYoga, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Karana (करण):" else "करण:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayKarana, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Rashi (राशि):" else "राशि (चंद्र):", fontWeight = FontWeight.SemiBold)
                            Text(text = displayRashi, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (p != null) {
                        if (p.weekday.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Weekday:" else "वार / दिन:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.weekday, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.tithiEndTime.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Tithi End Time:" else "तिथि समाप्ति समय:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.tithiEndTime, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.nakshatraLord.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Nakshatra Lord:" else "नक्षत्र स्वामी:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.nakshatraLord, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.nakshatraPada.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Nakshatra Pada:" else "नक्षत्र चरण:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.nakshatraPada, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.lunarMonth.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Lunar Month:" else "हिंदू मास (चंद्र):", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.lunarMonth, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.paksha.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Paksha:" else "पक्ष:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.paksha, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.ritu.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Ritu (Season):" else "ऋतु:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.ritu, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        if (p.samvatYear.isNotEmpty()) {
                            item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                            item {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Samvat Year:" else "विक्रम संवत वर्ष:", fontWeight = FontWeight.SemiBold)
                                    Text(text = p.samvatYear, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item {
                        Text(
                            text = if (isEng) "Auspicious & Inauspicious Times" else "शुभ और अशुभ समय",
                            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Sunrise (सूर्योदय):" else "सूर्योदय:", fontWeight = FontWeight.SemiBold)
                            Text(text = displaySunrise, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Sunset (सूर्यास्त):" else "सूर्यास्त:", fontWeight = FontWeight.SemiBold)
                            Text(text = displaySunset, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Abhijeet Muhurat:" else "अभिजीत मुहूर्त:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayAbhijeet, color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                        }
                    }
                    item { Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)) }
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = if (isEng) "Rahu Kaal (राहुकाल):" else "राहुकाल:", fontWeight = FontWeight.SemiBold)
                            Text(text = displayRahukaal, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (displayFestival.isNotEmpty()) {
                        item { Spacer(modifier = Modifier.height(12.dp)) }
                        item {
                            Text(
                                text = if (isEng) "Festival (त्यौहार):" else "त्यौहार / उत्सव:",
                                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            )
                        }
                        item {
                            Text(text = displayFestival, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    item {
                        Text(
                            text = if (isEng) "Spiritual Significance" else "आज का आध्यात्मिक महत्व",
                            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        )
                    }
                    item {
                        Text(
                            text = displayDetails,
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showPanchangDetails = false }) { 
                    Text(if (isEng) "OK" else "ठीक है", fontWeight = FontWeight.Bold) 
                }
            }
        )
    }

    if (showCalendar) {
        // Simple Calendar placeholder Screen
        CalendarScreen(viewModel = viewModel, onBack = { showCalendar = false }, isEng = isEng)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
        val isHovered by interactionSource.collectIsHoveredAsState()
        val buttonAlpha = if (isHovered) 0.85f else 0.5f

        LazyColumn(
            state = homeListState,
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_screen_column")
                .padding(horizontal = containerPadding),
            verticalArrangement = Arrangement.spacedBy(gridSpacing),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = gridSpacing)
        ) {
            // Announcement Banner
            item {
                announcementBannerContent()
            }

            // Mobile-first layouts based on device sizes
            if (isDesktop) {
                // DESKTOP Breakpoint Layout: Responsive balanced grids
                // Row 1: Thought of the Day + Today's Panchang
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1.2f)) {
                            thoughtCardContent()
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            panchangCardContent()
                        }
                    }
                }

                // Row 2: Sacred Services
                item {
                    servicesContent()
                }

                // Row 3: Daily Shloka + Daily Mantra
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1.2f)) {
                            ShlokaCard(
                                shloka = shlokaOfTheDay,
                                isEng = isEng,
                                onPlayTts = if (isTtsReady) { { tts?.speak(shlokaOfTheDay.verse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null) } } else null
                            )
                        }
                        Box(modifier = Modifier.weight(0.8f)) {
                            MantraCard(mantra = dailyMantra, isEng = isEng)
                        }
                    }
                }

                // Row 4: Daily Darshan Featured Deity + Quick Bhakti Tools
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            dailyDarshanFeaturedDeityContent()
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            quickBhaktiToolsContent()
                        }
                    }
                }

                // Row 5: Live Darshan Gallery
                item {
                    liveDarshanGalleryContent()
                }

                // Row 6: Video Feed + Community Highlights
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            videoFeedContent()
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            communityHighlightsContent()
                        }
                    }
                }
            } else if (isTablet) {
                // TABLET Breakpoint Layout
                // Row 1: Thought of the Day + Today's Panchang
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            thoughtCardContent()
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            panchangCardContent()
                        }
                    }
                }

                // Row 2: Sacred Services
                item {
                    servicesContent()
                }

                // Row 3: Daily Shloka
                item {
                    ShlokaCard(
                        shloka = shlokaOfTheDay,
                        isEng = isEng,
                        onPlayTts = if (isTtsReady) { { tts?.speak(shlokaOfTheDay.verse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null) } } else null
                    )
                }

                // Row 4: Mantra Card + Quick Bhakti Tools
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gridSpacing)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            MantraCard(mantra = dailyMantra, isEng = isEng)
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            quickBhaktiToolsContent()
                        }
                    }
                }

                // Row 5: Daily Darshan Featured Deity
                item {
                    dailyDarshanFeaturedDeityContent()
                }

                // Row 6: Live Darshan Gallery
                item {
                    liveDarshanGalleryContent()
                }

                // Row 7: Video Feed
                item {
                    videoFeedContent()
                }

                // Row 8: Community highlights
                item {
                    communityHighlightsContent()
                }
            } else {
                // MOBILE Breakpoint Layout: single column stacked
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(gridSpacing)) {
                        // 1. Thought of the Day (Motivational and Spiritual thoughts)
                        thoughtCardContent()

                        // 2. Today's Panchang
                        panchangCardContent()

                        // 3. Sacred Services
                        servicesContent()

                        // 4. Daily Shloka
                        ShlokaCard(
                            shloka = shlokaOfTheDay,
                            isEng = isEng,
                            onPlayTts = if (isTtsReady) { { tts?.speak(shlokaOfTheDay.verse, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, null) } } else null
                        )

                        // 5. Daily Mantra
                        MantraCard(mantra = dailyMantra, isEng = isEng)

                        // 6. Daily Darshan Featured Deity
                        dailyDarshanFeaturedDeityContent()

                        // 7. Quick Bhakti Tools
                        quickBhaktiToolsContent()

                        // 8. Live Darshan Gallery
                        liveDarshanGalleryContent()

                        // 9. Video Feed
                        videoFeedContent()

                        // 10. Community highlights
                        communityHighlightsContent()
                    }
                }
            }
        }

        // Saffron, bottom-left fixed circle button (40x40px, upward chevron, 0.5 opacity, 0.85 on hover)
        ScrollToTopButton(
            visible = showHomeScrollToTop,
            testTag = "scroll_to_top_home",
            onClick = {
                homeCoroutineScope.launch {
                    homeListState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }

    // Interactive Previews & Modals
    selectedShrineForPreview?.let { shrine ->
        AlertDialog(
            onDismissRequest = { selectedShrineForPreview = null },
            title = {
                Text(
                    text = if (isEng) shrine.nameEn else shrine.nameHi,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        coil.compose.AsyncImage(
                            model = shrine.imageUrl,
                            contentDescription = shrine.nameEn,
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Text(
                        text = if (isEng) shrine.captionEn else shrine.captionHi,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFFF9933))
                    )
                    Text(
                        text = shrine.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedShrineForPreview = null },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (isEng) "Join Live Aarti" else "आरती में शामिल हों")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedShrineForPreview = null }) {
                    Text(if (isEng) "Close" else "बंद करें")
                }
            }
        )
    }

    selectedVideoToPlay?.let { video ->
        AlertDialog(
            onDismissRequest = { selectedVideoToPlay = null },
            title = {
                Text(
                    text = video.first,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        coil.compose.AsyncImage(
                            model = video.second,
                            contentDescription = video.first,
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                        )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Playing",
                            tint = Color.White,
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("0:00", style = MaterialTheme.typography.labelSmall)
                        LinearProgressIndicator(
                            progress = 0.15f,
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            modifier = Modifier.weight(1f)
                        )
                        Text(video.third, style = MaterialTheme.typography.labelSmall)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedVideoToPlay = null }) {
                    Text(if (isEng) "Close Video" else "वीडियो बंद करें")
                }
            }
        )
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
    
    val selectedFormat by viewModel.knowledgeFormat.collectAsState()
    val selectedDifficulty by viewModel.knowledgeDifficulty.collectAsState()
    val sortBy by viewModel.knowledgeSortBy.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
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
        val knowledgeListState = androidx.compose.foundation.lazy.rememberLazyListState()
        val showKnowledgeScrollToTop by remember {
            derivedStateOf { knowledgeListState.firstVisibleItemIndex > 0 || knowledgeListState.firstVisibleItemScrollOffset > 300 }
        }
        val knowledgeCoroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = knowledgeListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .testTag("knowledge_screen_column"),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 16.dp)
            ) {
                item {
                    SacredHeader(
                        title = if (isEng) "Knowledge Hub" else "ज्ञान कोश",
                        subtitle = if (isEng) "Wikipedia of Sanatan Dharma wisdom and scriptures" else "सनातन धर्म के ज्ञान, इतिहास और ग्रंथों का विकिपीडिया",
                        showDiyas = true
                    )
                }

                item {
                    // Search Bar with Recent Searches
                    var expanded by remember { mutableStateOf(false) }
                    val recentSearches = listOf("Hanuman Chalisa", "Gita Chapter 2") // Mock
                    val popularSearches = listOf("Karma meaning", "Shiv mantras", "Ekadashi vrat rules")

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                viewModel.setKnowledgeSearchQuery(it)
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
                            singleLine = true,
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        if (searchQuery.isEmpty()) {
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                Text(if (isEng) "Recent Searches" else "हाल की खोजें", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp))
                                recentSearches.forEach { search ->
                                    DropdownMenuItem(
                                        text = { Text(search) },
                                        onClick = {
                                            viewModel.setKnowledgeSearchQuery(search)
                                            expanded = false
                                        }
                                    )
                                }
                                Divider()
                                Text(if (isEng) "Popular Searches" else "लोकप्रिय खोजें", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp))
                                popularSearches.forEach { search ->
                                    DropdownMenuItem(
                                        text = { Text(search) },
                                        onClick = {
                                            viewModel.setKnowledgeSearchQuery(search)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                // Row 1: Category chips (scrollable)
                item {
                    androidx.compose.foundation.lazy.LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
                    ) {
                        items(categories) { category ->
                            val isSelected = selectedCat == category
                            androidx.compose.material3.FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setKnowledgeCategory(category) },
                                label = { Text(getCategoryDisplayName(category)) },
                                modifier = Modifier.animateContentSize().touchFeedback()
                            )
                        }
                    }
                }

                // Row 2: Additional filters (scrollable) and "Clear all"
                item {
                    val isAnyFilterActive = selectedCat != "All" || selectedFormat != "All" || selectedDifficulty != "All" || searchQuery.isNotEmpty()
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        androidx.compose.foundation.lazy.LazyRow(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
                        ) {
                            // Difficulty Filters
                            item {
                                val difficulties = listOf("All", "Beginner", "Intermediate", "Advanced")
                                var expandedDiff by remember { mutableStateOf(false) }
                                Box {
                                    androidx.compose.material3.AssistChip(
                                        onClick = { expandedDiff = true },
                                        label = { Text(if (isEng) "Difficulty: $selectedDifficulty" else "स्तर: $selectedDifficulty") },
                                        modifier = Modifier.touchFeedback()
                                    )
                                    DropdownMenu(
                                        expanded = expandedDiff,
                                        onDismissRequest = { expandedDiff = false }
                                    ) {
                                        difficulties.forEach { diff ->
                                            DropdownMenuItem(
                                                text = { Text(diff) },
                                                onClick = {
                                                    viewModel.setKnowledgeDifficulty(diff)
                                                    expandedDiff = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            // Format Filters
                            item {
                                val formats = listOf("All", "Article", "Mantra", "Puja Vidhi")
                                var expandedFormat by remember { mutableStateOf(false) }
                                Box {
                                    androidx.compose.material3.AssistChip(
                                        onClick = { expandedFormat = true },
                                        label = { Text(if (isEng) "Format: $selectedFormat" else "प्रकार: $selectedFormat") },
                                        modifier = Modifier.touchFeedback()
                                    )
                                    DropdownMenu(
                                        expanded = expandedFormat,
                                        onDismissRequest = { expandedFormat = false }
                                    ) {
                                        formats.forEach { form ->
                                            DropdownMenuItem(
                                                text = { Text(form) },
                                                onClick = {
                                                    viewModel.setKnowledgeFormat(form)
                                                    expandedFormat = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            // Sort Filters
                            item {
                                val sorts = listOf("Newest", "A-Z")
                                var expandedSort by remember { mutableStateOf(false) }
                                Box {
                                    androidx.compose.material3.AssistChip(
                                        onClick = { expandedSort = true },
                                        label = { 
                                            val label = when (sortBy) {
                                                "Newest" -> if (isEng) "Newest" else "नवीनतम"
                                                else -> if (isEng) "A-Z" else "अ-ज्ञ"
                                            }
                                            Text(if (isEng) "Sort: $label" else "क्रम: $label") 
                                        },
                                        modifier = Modifier.touchFeedback()
                                    )
                                    DropdownMenu(
                                        expanded = expandedSort,
                                        onDismissRequest = { expandedSort = false }
                                    ) {
                                        sorts.forEach { sort ->
                                            val label = when (sort) {
                                                "Newest" -> if (isEng) "Newest" else "नवीनतम"
                                                else -> if (isEng) "A-Z" else "अ-ज्ञ"
                                            }
                                            DropdownMenuItem(
                                                text = { Text(label) },
                                                onClick = {
                                                    viewModel.setKnowledgeSort(sort)
                                                    expandedSort = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (isAnyFilterActive) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEng) "Clear all" else "साफ़ करें",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .clickable {
                                        viewModel.setKnowledgeCategory("All")
                                        viewModel.setKnowledgeFormat("All")
                                        viewModel.setKnowledgeDifficulty("All")
                                        viewModel.setKnowledgeSearchQuery("")
                                    }
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            )
                        }
                    }
                }

                if (articles.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isEng) "No spiritual topics matched your search." else "आपकी खोज के अनुसार कोई लेख नहीं मिला।",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    itemsIndexed(articles) { index, article ->
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
                                Text(
                                    text = "${com.example.utils.ReadingTimeUtils.calculateReadTime(article.content)} min read",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
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
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
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
                                    val context = androidx.compose.ui.platform.LocalContext.current
                                    androidx.compose.material3.IconButton(
                                        onClick = {
                                            val articleUrl = "https://sanatansadhak.app/articles/${article.id}"
                                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("Spiritual Article", articleUrl)
                                            clipboard.setPrimaryClip(clip)
                                            
                                            val sendIntent = android.content.Intent().apply {
                                                action = android.content.Intent.ACTION_SEND
                                                putExtra(android.content.Intent.EXTRA_TEXT, "Read this beautiful spiritual article: '${article.title}' - $articleUrl")
                                                type = "text/plain"
                                            }
                                            val shareIntent = android.content.Intent.createChooser(sendIntent, null)
                                            context.startActivity(shareIntent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share Article",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ScrollToTopButton(
                visible = showKnowledgeScrollToTop,
                testTag = "scroll_to_top_knowledge",
                onClick = {
                    knowledgeCoroutineScope.launch {
                        knowledgeListState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomStart)
            )
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
    val scrollState = rememberScrollState()
    val readingProgress by remember {
        derivedStateOf {
            if (scrollState.maxValue > 0) {
                scrollState.value.toFloat() / scrollState.maxValue.toFloat()
            } else {
                0f
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Sticky Progress Bar
        androidx.compose.material3.LinearProgressIndicator(
            progress = readingProgress,
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
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
}



@Composable
fun CalendarScreen(viewModel: SadhakViewModel, onBack: () -> Unit, isEng: Boolean) {
    val todayCal = remember { java.util.Calendar.getInstance() }
    val todayDay = todayCal.get(java.util.Calendar.DAY_OF_MONTH)
    val todayMonth = todayCal.get(java.util.Calendar.MONTH)
    val todayYear = todayCal.get(java.util.Calendar.YEAR)

    var selectedMonth by remember { mutableStateOf(todayMonth) }
    var selectedYear by remember { mutableStateOf(todayYear) }
    var selectedDay by remember { mutableStateOf(todayDay) }

    val monthNamesEn = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val monthNamesHi = listOf("जनवरी", "फ़रवरी", "मार्च", "अप्रैल", "मई", "जून", "जुलाई", "अगस्त", "सितंबर", "अक्टूबर", "नवंबर", "दिसंबर")
    val monthName = if (isEng) monthNamesEn[selectedMonth] else monthNamesHi[selectedMonth]

    val selectedCal = remember(selectedMonth, selectedYear) {
        java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.YEAR, selectedYear)
            set(java.util.Calendar.MONTH, selectedMonth)
            set(java.util.Calendar.DAY_OF_MONTH, 1)
        }
    }
    val daysInMonth = selectedCal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
    val startDayOfWeek = selectedCal.get(java.util.Calendar.DAY_OF_WEEK) // 1=Sunday, ..., 7=Saturday
    val blanksCount = if (startDayOfWeek == 1) 6 else startDayOfWeek - 2 // Adjust so Monday is first day of week, or Sun is first

    var panchangDetails by remember { mutableStateOf<DetailedPanchang?>(null) }

    LaunchedEffect(selectedDay, selectedMonth, selectedYear, isEng) {
        viewModel.getDetailedPanchangForDate(selectedDay, selectedMonth, selectedYear, isEng) { details ->
            panchangDetails = details
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isEng) "Hindu Calendar" else "हिंदू कैलेंडर",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            )
        }

        // Month / Year Navigation
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                if (selectedMonth == 0) {
                    selectedMonth = 11
                    selectedYear--
                } else {
                    selectedMonth--
                }
            }) {
                Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Prev Month", tint = MaterialTheme.colorScheme.primary)
            }
            
            Text(
                text = "$monthName $selectedYear",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )

            IconButton(onClick = {
                if (selectedMonth == 11) {
                    selectedMonth = 0
                    selectedYear++
                } else {
                    selectedMonth++
                }
            }) {
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Days of week header
        val dowLabels = if (isEng) listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun") else listOf("सोम", "मंगल", "बुध", "गुरु", "शुक्र", "शनि", "रवि")
        Row(modifier = Modifier.fillMaxWidth()) {
            for (label in dowLabels) {
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f))
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Days Grid using safe Row/Column layout (no scroll weights / measurement bugs)
        val daysList = mutableListOf<Int?>()
        val actualBlanks = if (blanksCount < 0) 0 else blanksCount
        for (i in 0 until actualBlanks) {
            daysList.add(null)
        }
        for (i in 1..daysInMonth) {
            daysList.add(i)
        }
        val weeks = daysList.chunked(7)

        Column(modifier = Modifier.fillMaxWidth()) {
            for (week in weeks) {
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                    for (day in week) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (day != null) {
                                val isToday = day == todayDay && selectedMonth == todayMonth && selectedYear == todayYear
                                val isSelected = day == selectedDay

                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isSelected -> MaterialTheme.colorScheme.primary
                                                isToday -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                                else -> Color.Transparent
                                            }
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = when {
                                                isSelected -> MaterialTheme.colorScheme.primary
                                                isToday -> MaterialTheme.colorScheme.secondary
                                                else -> Color.Transparent
                                            },
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            selectedDay = day
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isToday || isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = when {
                                                isSelected -> Color.White
                                                isToday -> MaterialTheme.colorScheme.primary
                                                else -> MaterialTheme.colorScheme.onSurface
                                            }
                                        )
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.size(36.dp))
                            }
                        }
                    }
                    if (week.size < 7) {
                        Spacer(modifier = Modifier.weight((7 - week.size).toFloat()))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Detailed Panchang Card (Scrollable)
        Text(
            text = if (isEng) "Selected Date Panchang" else "चयनित तिथि का पंचांग",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            panchangDetails?.let { details ->
                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isEng) "Date: $selectedDay $monthName $selectedYear" else "दिनांक: $selectedDay $monthName $selectedYear",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            )
                            
                            if (details.festival.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, "Festival", tint = Color(0xFFFF9800), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = details.festival,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFFF9800))
                                    )
                                }
                            }
                            
                            Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))

                            // Core elements
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Tithi (तिथि):" else "तिथि:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = details.tithi, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Nakshatra (नक्षत्र):" else "नक्षत्र:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = details.nakshatra, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Yoga (योग):" else "योग:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = details.yoga, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Karana (करण):" else "करण:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = details.karana, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Rashi (राशि):" else "राशि:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                Text(text = details.rashi, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                            }
                            if (details.weekday.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Weekday:" else "वार / दिन:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.weekday, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.tithiEndTime.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Tithi End Time:" else "तिथि समाप्ति समय:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.tithiEndTime, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.nakshatraLord.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Nakshatra Lord:" else "नक्षत्र स्वामी:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.nakshatraLord, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.nakshatraPada.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Nakshatra Pada:" else "नक्षत्र चरण:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.nakshatraPada, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.lunarMonth.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Lunar Month:" else "हिंदू मास (चंद्र):", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.lunarMonth, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.paksha.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Paksha:" else "पक्ष:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.paksha, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.ritu.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Ritu (Season):" else "ऋतु:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.ritu, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                            if (details.samvatYear.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = if (isEng) "Samvat Year:" else "विक्रम संवत वर्ष:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                                    Text(text = details.samvatYear, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                                }
                            }
                        }
                    }
                }

                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isEng) "Muhurat Timings" else "मुहूर्त और समय",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))

                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Sunrise (सूर्योदय):" else "सूर्योदय:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = details.sunrise, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Sunset (सूर्यास्त):" else "सूर्यास्त:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = details.sunset, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Abhijeet Muhurat:" else "अभिजीत मुहूर्त:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = details.abhijeet, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50)))
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = if (isEng) "Rahu Kaal (राहुकाल):" else "राहुकाल:", style = MaterialTheme.typography.bodyMedium)
                                Text(text = details.rahukaal, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error))
                            }
                        }
                    }
                }

                item {
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isEng) "Planetary Significance" else "ग्रह प्रभाव एवं विशेष सलाह",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                            )
                            Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            Text(
                                text = details.details,
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp, fontStyle = FontStyle.Italic)
                            )
                        }
                    }
                }
            } ?: item {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    androidx.compose.material3.CircularProgressIndicator()
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
    val isAdmin by viewModel.isAdmin.collectAsState()
    
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

                // Category & Filtering options
                var selectedCommunityCategory by remember { mutableStateOf("All") }
                val communityCategories = listOf("All", "Bhakti Experiences", "Doubts & Discussions", "Daily Karma Journal", "Temple Stories")
                val getCommunityCategoryDisplayName = { cat: String ->
                    if (isEng) cat
                    else when (cat) {
                        "All" -> "सभी"
                        "Bhakti Experiences" -> "भक्ति अनुभव"
                        "Doubts & Discussions" -> "जिज्ञासा एवं चर्चा"
                        "Daily Karma Journal" -> "दैनिक कर्म पत्रिका"
                        "Temple Stories" -> "मंदिर गाथा"
                        else -> cat
                    }
                }

                val filteredPosts = remember(posts, selectedCommunityCategory) {
                    if (selectedCommunityCategory == "All") posts
                    else posts.filter { it.category == selectedCommunityCategory }
                }

                // Row 1: Category Row
                androidx.compose.foundation.lazy.LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                ) {
                    items(communityCategories) { category ->
                        val isSelected = selectedCommunityCategory == category
                        androidx.compose.material3.FilterChip(
                            selected = isSelected,
                            onClick = { selectedCommunityCategory = category },
                            label = { Text(getCommunityCategoryDisplayName(category)) },
                            modifier = Modifier.animateContentSize().touchFeedback()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Row 2: Additional Filters Row (Sort dropdown AssistChip)
                val sorts = listOf("New", "Top", "Discussed")
                val label = when (sortBy) {
                    "New" -> if (isEng) "New" else "नवीनतम"
                    "Top" -> if (isEng) "Top Devotional" else "लोकप्रिय"
                    else -> if (isEng) "Most Discussed" else "चर्चा में"
                }

                val isAnyFilterActive = selectedCommunityCategory != "All" || sortBy != "New"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var expandedSort by remember { mutableStateOf(false) }
                    Box {
                        androidx.compose.material3.AssistChip(
                            onClick = { expandedSort = true },
                            label = { Text(if (isEng) "Sort: $label" else "क्रम: $label") },
                            modifier = Modifier.touchFeedback()
                        )
                        DropdownMenu(
                            expanded = expandedSort,
                            onDismissRequest = { expandedSort = false }
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
                                        expandedSort = false
                                    }
                                )
                            }
                        }
                    }

                    if (isAnyFilterActive) {
                        Text(
                            text = if (isEng) "Clear all filters" else "साफ़ करें",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .clickable {
                                    selectedCommunityCategory = "All"
                                    viewModel.setCommunitySort("New")
                                }
                                .touchFeedback()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Post listings
                if (filteredPosts.isEmpty()) {
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
                        derivedStateOf { communityListState.firstVisibleItemIndex > 0 || communityListState.firstVisibleItemScrollOffset > 300 }
                    }
                    val communityCoroutineScope = rememberCoroutineScope()

                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(
                            state = communityListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                        items(filteredPosts) { post ->
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
                                        Row(verticalAlignment = Alignment.CenterVertically) {
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
                                            
                                            val currentUserName by viewModel.userName.collectAsState()
                                            val isSuper = currentUserRole == "SuperUser" || isAdmin
                                            val canDelete = isSuper || (currentUserName == post.author)

                                            if (isSuper) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Verified,
                                                        contentDescription = "Pin Post",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }

                                            if (canDelete) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post.id) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete Post",
                                                        tint = MaterialTheme.colorScheme.error,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
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
                                                text = post.upvotes.toString(),
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
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
                                            Text(
                                                text = post.downvotes.toString(),
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp)
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (isAdmin) {
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(36.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.Star,
                                                        contentDescription = "Pin",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                            val userName by viewModel.userName.collectAsState()
                                            if (isAdmin || post.author == userName) {
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post) },
                                                    modifier = Modifier.size(36.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red.copy(alpha=0.7f),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
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

                ScrollToTopButton(
                    visible = showCommunityScrollToTop,
                    testTag = "scroll_to_top_community",
                    onClick = {
                        communityCoroutineScope.launch {
                            communityListState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }

            // Write Post Dialog
            if (showCreatePostDialog) {
                var title by remember { mutableStateOf("") }
                var category by remember { mutableStateOf("Bhakti Experiences") }
                var content by remember { mutableStateOf("") }
                var statusText by remember { mutableStateOf("") }

                val canPost by viewModel.canUserPost.collectAsState()

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
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { viewModel.toggleLikeComment(comment) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.ThumbUp, contentDescription = "Upvote", modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                                    }
                                    Text(text = comment.upvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                    IconButton(onClick = { viewModel.toggleDislikeComment(comment) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.ThumbDown, contentDescription = "Downvote", modifier = Modifier.size(14.dp), tint = Color.Gray)
                                    }
                                    Text(text = comment.downvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                }
                                val userName by viewModel.userName.collectAsState()
                                val isAdmin by viewModel.isAdmin.collectAsState()
                                if (isAdmin || comment.author == userName) {
                                    IconButton(onClick = { viewModel.deleteComment(comment.id) }, modifier = Modifier.size(24.dp)) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(14.dp), tint = Color.Red.copy(alpha=0.7f))
                                    }
                                }
                            }
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
    val bellLogs = remember { mutableStateListOf<String>() }
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
                text = "${if (isEng) "Total Rings" else "कुल आरती नाद"}: $bellCount",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .clickable(
                        interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                        indication = null
                    ) {
                        isSwinging = true
                        pulseTrigger += 1
                        bellCount += 1
                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                        
                        // Play original temple bell sound
                        playTempleBellSound()

                        // Log the event
                        val sdf = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())
                        val timeStr = sdf.format(java.util.Date())
                        val logMsg = if (isEng) "Bell Rung at $timeStr (Ring #$bellCount)" else "आरती घंटी नाद समय $timeStr (संख्या #$bellCount)"
                        bellLogs.add(0, logMsg)
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
    Thread {
        try {
            val sampleRate = 44100
            val duration = 3.5
            val numSamples = (sampleRate * duration).toInt()
            val samples = FloatArray(numSamples)
            
            val f0 = 240.0f
            val harmonics = floatArrayOf(1.0f, 1.22f, 1.45f, 1.6f, 2.1f, 2.5f, 3.2f, 4.0f, 5.5f)
            val amplitudes = floatArrayOf(1.2f, 0.9f, 0.8f, 0.6f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f)
            
            for (i in 0 until numSamples) {
                val t = i.toFloat() / sampleRate
                val decay = kotlin.math.exp(-1.5 * t).toFloat()
                
                var sampleValue = 0f
                for (h in harmonics.indices) {
                    val freq = f0 * harmonics[h]
                    val beat = 1.0f + 0.1f * kotlin.math.sin(2.0f * kotlin.math.PI * 6.0f * t).toFloat()
                    sampleValue += amplitudes[h] * kotlin.math.sin(2.0 * kotlin.math.PI * freq * t).toFloat() * beat
                }
                samples[i] = sampleValue * decay * 0.8f
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

    var activePlayingMantraId by remember { mutableStateOf<String?>(null) }

    if (activePlayingMantraId != null) {
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
    val activeTab = if (selectedToolSubTab == "Mantras" || selectedToolSubTab == "Timer" || selectedToolSubTab == "My Mantras") {
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
                val tabs = listOf("Mantras", "My Mantras", "Timer")
                tabs.forEach { tab ->
                    val label = when (tab) {
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
                "Mantras" -> MantraLibraryView(viewModel, isEng) { activePlayingMantraId = it }
                "My Mantras" -> MyMantrasView(viewModel, isEng) { activePlayingMantraId = it }
                "Timer" -> MeditationTimerView(viewModel, isEng)
                else -> MantraLibraryView(viewModel, isEng) { activePlayingMantraId = it }
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
@Composable
fun BhaktiToolsScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    val selectedToolSubTab by viewModel.bhaktiTab.collectAsState()
    // Ensure state defaults to valid tab for this screen
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
            @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
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
                "Bell" -> AartiBellWidget(isEng = isEng)
                "Counter" -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
                "KarmaLog" -> KarmaTrackerView(viewModel, isEng)
                "Gratitude" -> GratitudeJournalView(viewModel, isEng)
                "TempleFinder" -> Column(modifier = Modifier.weight(1f)) { TempleFinderView(viewModel) }
                else -> Column(modifier = Modifier.weight(1f)) { CounterView(isEng, viewModel) }
            }
        }

    }
}

@Composable
fun MantraLibraryView(viewModel: SadhakViewModel, isEnglish: Boolean, onMantraClick: (String) -> Unit) {
    val userRole by viewModel.userRole.collectAsState()
    val favoriteMantras by viewModel.favoriteMantras.collectAsState()
    val uriHandler = LocalUriHandler.current

    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var newYoutubeUrl by remember { mutableStateOf("") }
    var showAddCustomMantra by remember { mutableStateOf(false) }

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
                            IconButton(onClick = { viewModel.deleteFavoriteMantra(fav.id) }) {
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
                                        uriHandler.openUri(fav.youtubeId)
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
fun MeditationTimerView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
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
            AmbientSoundPlayer.playSound(selectedSoundIndex, backgroundVolume)
        } else {
            AmbientSoundPlayer.stopSound()
        }
    }

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
        } else if (timerRunning && secondsLeft == 0) {
            timerRunning = false
            secondsLeft = selectedDuration
            viewModel.logKarmaDeed("Dhyana Sadhana Completed for ${selectedDuration / 60} mins")
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

                    @OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
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
                            .combinedClickable(
                                onClick = {
                                    timerRunning = !timerRunning
                                },
                                onLongClick = {
                                    timerRunning = false
                                    secondsLeft = selectedDuration
                                    haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                }
                            ),
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
                        Pair("Ganga Flow 🌊", "गंगा 🌊"),
                        Pair("Gentle Rain 🌧", "वर्षा 🌧"),
                        Pair("Morning Birds 🐦", "पक्षी 🐦"),
                        Pair("Himalayan Wind 🌬", "पहाड़ी हवा 🌬")
                    )

                    @OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
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

        // Video Filtering and Sorting Options State
        var selectedVideoSort by remember { mutableStateOf("Newest") }
        val videoSorts = listOf("Newest", "A-Z")
        val sortLabel = when (selectedVideoSort) {
            "Newest" -> if (isEng) "Newest" else "नवीनतम"
            else -> if (isEng) "A-Z" else "अ-ज्ञ"
        }
        val isAnyVideoFilterActive = selectedCat != "All" || selectedVideoSort != "Newest"

        val filteredSortedVideos = remember(videos, selectedVideoSort) {
            if (selectedVideoSort == "A-Z") {
                videos.sortedBy { it.title }
            } else {
                videos
            }
        }

        // Row 1: Categories selector (horizontal scrollable LazyRow)
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCat == category
                val label = when (category) {
                    "All" -> if (isEng) "All Videos" else "सभी वीडियो"
                    "Darshan" -> if (isEng) "Darshan" else "देव दर्शन (Darshan)"
                    "Puja" -> if (isEng) "Puja & Aarti" else "पूजा व आरती (Puja)"
                    else -> if (isEng) "Stories & Kathas" else "कथा प्रसंग (Stories)"
                }
                androidx.compose.material3.FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.setVideoCategory(category) },
                    label = { Text(label) },
                    modifier = Modifier.animateContentSize().touchFeedback()
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Row 2: Additional Filters Row (Sort dropdown AssistChip)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var expandedSort by remember { mutableStateOf(false) }
            Box {
                androidx.compose.material3.AssistChip(
                    onClick = { expandedSort = true },
                    label = { Text(if (isEng) "Sort: $sortLabel" else "क्रम: $sortLabel") },
                    modifier = Modifier.touchFeedback()
                )
                DropdownMenu(
                    expanded = expandedSort,
                    onDismissRequest = { expandedSort = false }
                ) {
                    videoSorts.forEach { sort ->
                        val labelVal = when (sort) {
                            "Newest" -> if (isEng) "Newest" else "नवीनतम"
                            else -> if (isEng) "A-Z" else "अ-ज्ञ"
                        }
                        DropdownMenuItem(
                            text = { Text(labelVal) },
                            onClick = {
                                selectedVideoSort = sort
                                expandedSort = false
                            }
                        )
                    }
                }
            }

            if (isAnyVideoFilterActive) {
                Text(
                    text = if (isEng) "Clear all filters" else "साफ़ करें",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .clickable {
                            viewModel.setVideoCategory("All")
                            selectedVideoSort = "Newest"
                        }
                        .touchFeedback()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Curated Playlist Section
        val curatedVideos by viewModel.curatedPlaylistVideos.collectAsState()
        val curatedTitle = viewModel.getCuratedPlaylistTitle(isEng)

        if (curatedVideos.isNotEmpty() && selectedCat == "All") {
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "✨", fontSize = 16.sp)
                        Text(
                            text = curatedTitle,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(curatedVideos) { video ->
                            Card(
                                modifier = Modifier
                                    .width(200.dp)
                                    .clickable {
                                        activeVideoUrl = video.videoId
                                        activeVideoTitle = video.title
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(90.dp)
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                                    )
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🕉", fontSize = 36.sp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = video.title,
                                            maxLines = 2,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                lineHeight = 16.sp
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = video.category,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // List of videos
        if (filteredSortedVideos.isEmpty()) {
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
                derivedStateOf { videoListState.firstVisibleItemIndex > 0 || videoListState.firstVisibleItemScrollOffset > 300 }
            }
            val videoCoroutineScope = rememberCoroutineScope()

            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    state = videoListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                itemsIndexed(filteredSortedVideos) { index, video ->
                    val isLocked = userRole == "Guest" && index >= 2
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = if (isLocked) Color.LightGray else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    ) {
                        Column {
                            if (activeVideoUrl == video.videoId && !isLocked) {
                                // Inline real YouTube player webview with overlay close button
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                ) {
                                    androidx.compose.ui.viewinterop.AndroidView(
                                        factory = { context ->
                                            android.webkit.WebView(context).apply {
                                                settings.javaScriptEnabled = true
                                                settings.mediaPlaybackRequiresUserGesture = false
                                                webChromeClient = android.webkit.WebChromeClient()
                                                val html = """
                                                    <html>
                                                    <body style="margin:0;padding:0;background-color:black;">
                                                        <iframe width="100%" height="100%" src="https://www.youtube.com/embed/${video.videoId}?autoplay=1&rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
                                                    </body>
                                                    </html>
                                                """.trimIndent()
                                                loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null)
                                            }
                                        },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    IconButton(
                                        onClick = { activeVideoUrl = null },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(6.dp)
                                            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            } else {
                                // Dynamic Thumbnail with Play button overlay
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
                                    if (video.thumbnail.isNotEmpty() && !isLocked) {
                                        coil.compose.AsyncImage(
                                            model = video.thumbnail,
                                            contentDescription = video.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Text(
                                            text = "🕉",
                                            fontSize = 72.sp,
                                            color = if (isLocked) Color.Gray.copy(alpha = 0.12f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

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
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val context = androidx.compose.ui.platform.LocalContext.current
                                    androidx.compose.material3.IconButton(
                                        onClick = {
                                            val videoUrl = "https://www.youtube.com/watch?v=${video.videoId}"
                                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("Spiritual Video", videoUrl)
                                            clipboard.setPrimaryClip(clip)
                                            
                                            val sendIntent = android.content.Intent().apply {
                                                action = android.content.Intent.ACTION_SEND
                                                putExtra(android.content.Intent.EXTRA_TEXT, "Watch this beautiful spiritual video: '${video.title}' - $videoUrl")
                                                type = "text/plain"
                                            }
                                            val shareIntent = android.content.Intent.createChooser(sendIntent, null)
                                            context.startActivity(shareIntent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share Video",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ScrollToTopButton(
                visible = showVideoScrollToTop,
                testTag = "scroll_to_top_videos",
                onClick = {
                    videoCoroutineScope.launch {
                        videoListState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomStart)
            )
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
    val isAdmin by viewModel.isAdmin.collectAsState()
    
    var isEditingProfile by remember { mutableStateOf(false) }
    var showAppPreferences by remember { mutableStateOf(false) }

    if (isEditingProfile) {
        EditProfileScreen(
            viewModel = viewModel,
            onBack = { isEditingProfile = false }
        )
        return
    }

    if (showAppPreferences) {
        AppPreferencesScreen(
            viewModel = viewModel,
            onBack = { showAppPreferences = false }
        )
        return
    }

    // LOGIN LOGIC
    if (userRole == "Guest") {
        var showSignUp by remember { mutableStateOf(false) }
        var emailInput by remember { mutableStateOf("") }
        var mobileInput by remember { mutableStateOf("") }
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
                    val infiniteTransition = rememberInfiniteTransition(label = "logo_blink")
                    val alphaBlink by infiniteTransition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1.0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1500, easing = androidx.compose.animation.core.FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "alphaBlink"
                    )

                    com.example.SanatanSadhakLogo(
                        modifier = Modifier.padding(16.dp),
                        logoSize = 120.dp,
                        isAnimated = true
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
                            value = mobileInput,
                            onValueChange = { mobileInput = it },
                            label = { Text(text = if (isEng) "Mobile Number" else "मोबाइल नंबर") },
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
                                    viewModel.signUp(emailInput, passInput, nameInput, cityInput, stateInput, mobileInput) { success, msg ->
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
                                text = if (isAdmin) "👑" else "🕉",
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
                            text = if (isAdmin) {
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
                            Text(text = "Bhakti Streak", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp)
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
                    SettingActionTile(
                        title = if (isEng) "App Preferences & Theme" else "ऐप प्राथमिकताएं और थीम",
                        icon = Icons.Default.Palette,
                        onClick = { showAppPreferences = true }
                    )
                    
                    if (isAdmin) {
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

@Composable
fun AppPreferencesScreen(viewModel: SadhakViewModel, onBack: () -> Unit) {
    val isEng by viewModel.isEnglish.collectAsState()
    val themePref by viewModel.themePreference.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = if (isEng) "App Preferences" else "ऐप प्राथमिकताएं",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Section 1: Theme preferences
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Visual Appearance (Theme)" else "दृश्य स्वरूप (थीम)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Text(
                    text = if (isEng) "Choose between Light mode, Eye-Safe Dark mode, or match your device settings." else "लाइट मोड, डार्क मोड, या अपने डिवाइस की सेटिंग के अनुसार चुनें।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Three Option Cards: System (0), Light (1), Dark (2)
                val options = listOf(
                    Triple(0, if (isEng) "System" else "सिस्टम", Icons.Default.Build),
                    Triple(1, if (isEng) "Light Mode" else "लाइट मोड", Icons.Default.Palette),
                    Triple(2, if (isEng) "Dark Mode" else "डार्क मोड", Icons.Default.Star)
                )

                options.forEach { option ->
                    val prefCode = option.first
                    val label = option.second
                    val icon = option.third
                    val isSelected = themePref == prefCode
                    val cardBorderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                    val cardBg = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(cardBg, RoundedCornerShape(12.dp))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { viewModel.setThemePreference(prefCode) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                        RadioButton(
                            selected = isSelected,
                            onClick = { viewModel.setThemePreference(prefCode) },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }

        // Section 2: Language & Localization
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Language Selection" else "भाषा चयन",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Text(
                    text = if (isEng) "Toggle app interface language between English and Hindi." else "ऐप इंटरफ़ेस भाषा को अंग्रेजी और हिंदी के बीच बदलें।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // English Button
                    OutlinedButton(
                        onClick = { if (!isEng) viewModel.toggleLanguage() },
                        modifier = Modifier.weight(1f),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isEng) 2.dp else 1.dp,
                            color = if (isEng) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isEng) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent
                        )
                    ) {
                        Text(
                            text = "English",
                            fontWeight = if (isEng) FontWeight.Bold else FontWeight.Normal,
                            color = if (isEng) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Hindi Button
                    OutlinedButton(
                        onClick = { if (isEng) viewModel.toggleLanguage() },
                        modifier = Modifier.weight(1f),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (!isEng) 2.dp else 1.dp,
                            color = if (!isEng) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (!isEng) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color.Transparent
                        )
                    ) {
                        Text(
                            text = "हिन्दी",
                            fontWeight = if (!isEng) FontWeight.Bold else FontWeight.Normal,
                            color = if (!isEng) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Section 3: Daily Alerts
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Device Notifications" else "डिवाइस सूचनाएं",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEng) "Sadhak Reminders" else "साधक अनुस्मारक",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                        Text(
                            text = if (isEng) "Get alerts for sunrise, daily Panchang, and spiritual thoughts." else "सूर्योदय, दैनिक पंचांग और आध्यात्मिक विचारों के लिए अलर्ट प्राप्त करें।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications() },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }

        // Daily Spiritual Alerts (Shloka & Panchang) Configuration Card
        val shlokaPanchangReminderEnabled by viewModel.reminderEnabled.collectAsState()
        val shlokaPanchangReminderHour by viewModel.reminderHour.collectAsState()
        val shlokaPanchangReminderMinute by viewModel.reminderMinute.collectAsState()
        val shlokaPanchangReminderType by viewModel.reminderType.collectAsState()

        var selectedHour by remember { mutableStateOf(shlokaPanchangReminderHour) }
        var selectedMinute by remember { mutableStateOf(shlokaPanchangReminderMinute) }
        var selectedType by remember { mutableStateOf(shlokaPanchangReminderType) }

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Daily Spiritual Alerts" else "दैनिक आध्यात्मिक अलर्ट",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = if (isEng) 
                        "Receive the sacred 'Shloka of the Day' and Panchang alerts directly on your device at your chosen time."
                    else 
                        "अपनी पसंद के समय पर सीधे अपने डिवाइस पर 'आज का श्लोक' और दैनिक पंचांग अलर्ट प्राप्त करें।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (shlokaPanchangReminderEnabled) {
                    val amPm = if (shlokaPanchangReminderHour >= 12) "PM" else "AM"
                    val displayHour = when {
                        shlokaPanchangReminderHour == 0 -> 12
                        shlokaPanchangReminderHour > 12 -> shlokaPanchangReminderHour - 12
                        else -> shlokaPanchangReminderHour
                    }
                    val displayMin = String.format("%02d", shlokaPanchangReminderMinute)
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEng) "Alert scheduled active!" else "अनुस्मारक अलर्ट सक्रिय है!",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEng) 
                                    "Time: $displayHour:$displayMin $amPm ($shlokaPanchangReminderType alerts)"
                                else 
                                    "समय: $displayHour:$displayMin $amPm ($shlokaPanchangReminderType अलर्ट)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Time Pickers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEng) "Set Time:" else "समय चुनें:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Hour Dropdown/Selector
                    var showHourMenu by remember { mutableStateOf(false) }
                    Box {
                        Button(
                            onClick = { showHourMenu = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                        ) {
                            Text(text = String.format("%02d", selectedHour))
                        }
                        DropdownMenu(expanded = showHourMenu, onDismissRequest = { showHourMenu = false }) {
                            (0..23).forEach { h ->
                                DropdownMenuItem(
                                    text = { Text(String.format("%02d", h)) },
                                    onClick = {
                                        selectedHour = h
                                        showHourMenu = false
                                    }
                                )
                            }
                        }
                    }

                    Text(text = ":", fontWeight = FontWeight.Bold)

                    // Minute Dropdown/Selector
                    var showMinuteMenu by remember { mutableStateOf(false) }
                    Box {
                        Button(
                            onClick = { showMinuteMenu = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant)
                        ) {
                            Text(text = String.format("%02d", selectedMinute))
                        }
                        DropdownMenu(expanded = showMinuteMenu, onDismissRequest = { showMinuteMenu = false }) {
                            listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55).forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(String.format("%02d", m)) },
                                    onClick = {
                                        selectedMinute = m
                                        showMinuteMenu = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Type selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (isEng) "Content:" else "सामग्री:",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.weight(1f)
                    )

                    listOf("Shloka", "Panchang", "Both").forEach { type ->
                        val isSelected = selectedType == type
                        androidx.compose.material3.FilterChip(
                            selected = isSelected,
                            onClick = { selectedType = type },
                            label = { Text(if (isEng) type else when(type) {
                                "Shloka" -> "श्लोक"
                                "Panchang" -> "पंचांग"
                                else -> "दोनों"
                            }) },
                            modifier = Modifier.touchFeedback()
                        )
                    }
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (shlokaPanchangReminderEnabled) {
                        Button(
                            onClick = { viewModel.cancelShlokaPanchangReminder() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            modifier = Modifier.weight(1f).touchFeedback()
                        ) {
                            Text(if (isEng) "Disable Alerts" else "अलर्ट बंद करें")
                        }
                    }
                    Button(
                        onClick = { viewModel.scheduleShlokaPanchangReminder(selectedHour, selectedMinute, selectedType) },
                        modifier = Modifier.weight(1f).touchFeedback()
                    ) {
                        Text(if (isEng) "Save Settings" else "सुरक्षित करें")
                    }
                }
            }
        }

        // Section 3: Prayer Reminders
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Prayer Reminders" else "प्रार्थना अनुस्मारक",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = if (isEng) "Set daily reminders for morning or evening prayers/meditation." else "सुबह या शाम की प्रार्थना/ध्यान के लिए दैनिक अनुस्मारक सेट करें।",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.scheduleDailyPrayerReminder(6, 0) }, modifier = Modifier.weight(1f)) {
                        Text(if (isEng) "Morning (6 AM)" else "सुबह (6 AM)")
                    }
                    Button(onClick = { viewModel.scheduleDailyPrayerReminder(19, 0) }, modifier = Modifier.weight(1f)) {
                        Text(if (isEng) "Evening (7 PM)" else "शाम (7 PM)")
                    }
                }
            }
        }

        val bgChantEnabled by viewModel.bgChantEnabled.collectAsState()
        val bgChantVolume by viewModel.bgChantVolume.collectAsState()

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (isEng) "Global Background Chant" else "पृष्ठभूमि मंत्र",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEng) "Continuous Om / Mantra" else "निरंतर ॐ / मंत्र",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                        Text(
                            text = if (isEng) "Plays softly in the background across the entire app." else "पूरे ऐप में पृष्ठभूमि में धीरे-धीरे बजता है।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = bgChantEnabled,
                        onCheckedChange = { viewModel.toggleBgChant() },
                        colors = SwitchDefaults.colors(checkedThumbColor = MaterialTheme.colorScheme.primary)
                    )
                }

                if (bgChantEnabled) {
                    Text(
                        text = "${if (isEng) "Volume" else "मात्रा"}: ${(bgChantVolume * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Slider(
                        value = bgChantVolume,
                        onValueChange = { viewModel.setBgChantVolume(it) },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = if (isEng) "Go Back" else "वापस जाएं", color = Color.White)
        }
    }
}





