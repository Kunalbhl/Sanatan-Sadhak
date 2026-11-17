package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.ui.audio.SadhakTextReader
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.data.BhaktiVideo
import com.example.ui.components.DivineEnvironmentBanner
import com.example.ui.components.DivineJapaMalaIcon
import com.example.ui.components.DivineDhyanIcon
import com.example.ui.components.DivineMyMantraIcon
import com.example.ui.components.DivineAartiCounterIcon
import com.example.ui.components.DivineChantsIcon
import com.example.ui.components.DiyaDecoration
import com.example.ui.components.GoldenLotusMedallion
import com.example.ui.components.SacredGranthArtwork
import com.example.ui.components.SacredTempleArtwork
import com.example.ui.components.SacredTempleBellArtwork
import com.example.ui.designsystem.DeityFilterCategory
import com.example.ui.designsystem.SadhakCard
import com.example.ui.designsystem.SadhakChip
import com.example.ui.designsystem.SadhakDeityFilterRow
import com.example.ui.designsystem.SadhakEmptyState
import com.example.ui.designsystem.SadhakErrorState
import com.example.ui.designsystem.SadhakFeatureTile
import com.example.ui.designsystem.SadhakLoading
import com.example.ui.designsystem.SadhakPrimaryButton
import com.example.ui.designsystem.SadhakSectionHeader
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.theme.SadhakThemeDimensions
import com.example.ui.viewmodel.AiMitraViewModel
import com.example.ui.viewmodel.HomeViewModel
import com.example.ui.viewmodel.MantraViewModel
import com.example.ui.viewmodel.PanchangViewModel
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.viewmodel.VideoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: SadhakViewModel,
    homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    panchangViewModel: PanchangViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    videoViewModel: VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    aiViewModel: AiMitraViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    mantraViewModel: MantraViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val isEng by viewModel.isEnglish.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val hasNewNotifications by viewModel.hasNewNotifications.collectAsState()

    // Panchang State
    val todayPanchang by panchangViewModel.todayDetailedPanchang.collectAsState()
    val currentTithi by panchangViewModel.currentTithi.collectAsState()
    val currentNakshatra by panchangViewModel.currentNakshatra.collectAsState()
    val currentSunrise by panchangViewModel.currentSunrise.collectAsState()
    val currentSunset by panchangViewModel.currentSunset.collectAsState()
    val currentRahukaal by panchangViewModel.currentRahukaal.collectAsState()
    val currentAbhijeet by panchangViewModel.currentAbhijeet.collectAsState()
    val currentFestival by panchangViewModel.currentFestival.collectAsState()
    val panchangError by panchangViewModel.panchangError.collectAsState()
    val currentLocation by panchangViewModel.currentLocation.collectAsState()

    // Daily Spiritual Content
    val dailyShlokaItem by homeViewModel.dailyShlokaItem.collectAsState()
    val dailyMantraItem by homeViewModel.dailyMantraItem.collectAsState()
    val dailyMantra by homeViewModel.dailyMantra.collectAsState()
    val shlokaOfTheDay by homeViewModel.shlokaOfTheDay.collectAsState()
    val currentThought by panchangViewModel.currentThought.collectAsState()

    // User Progress / Favorites / Japa Stats
    val favoriteMantras by viewModel.favoriteMantras.collectAsState()
    val karmaPoints by viewModel.karmaPoints.collectAsState()
    val dailyStreak by viewModel.dailyStreak.collectAsState()
    val japaCount by mantraViewModel.japaCount.collectAsState()
    val malaCount by mantraViewModel.malaCount.collectAsState()

    // Videos
    val videos by videoViewModel.videos.collectAsState()

    // Deity Filter
    var selectedDeity by remember { mutableStateOf("All") }

    // Text to Speech
    var tts: TextToSpeech? by remember { mutableStateOf(null) }
    var isTtsReady by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("sa", "IN"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale("hi", "IN"))
                }
                tts?.setPitch(0.9f)
                tts?.setSpeechRate(0.85f)
                isTtsReady = true
            }
        }
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                panchangViewModel.loadDailyPanchang()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val currentDateStr = remember(isEng) {
        val locale = if (isEng) Locale.ENGLISH else Locale("hi", "IN")
        val formatter = SimpleDateFormat("EEEE, d MMMM yyyy", locale)
        formatter.format(Date())
    }

    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                panchangViewModel.loadDailyPanchang()
                kotlinx.coroutines.delay(600)
                isRefreshing = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("home_screen_lazy_column"),
            contentPadding = PaddingValues(bottom = SadhakThemeDimensions.screenPaddingLarge)
        ) {
        // 1. HEADER & DIVINE GREETING
        item(key = "header") {
            DivineEnvironmentBanner(
                isEng = isEng,
                userName = if (userName.isNotBlank() && userName != "Guest Sadhak" && userName != "Guest Devotee") userName else null,
                currentDate = currentDateStr,
                hasNotifications = hasNewNotifications,
                onNotificationClick = {
                    viewModel.setHasNewNotifications(false)
                },
                onProfileClick = {
                    viewModel.setScreen("Profile")
                }
            )
        }

        // 2. TODAY'S PANCHANG
        item(key = "panchang") {
            HomePanchangSection(
                isEng = isEng,
                tithi = currentTithi,
                nakshatra = currentNakshatra,
                sunrise = currentSunrise,
                sunset = currentSunset,
                rahukaal = currentRahukaal,
                abhijeet = currentAbhijeet,
                gulika = todayPanchang?.gulikaKaal,
                yamaganda = todayPanchang?.yamaganda,
                festival = if (currentFestival.isNotBlank() && currentFestival != "Devotional Puja Muhurat" && currentFestival != "भक्ति पूजा मुहूर्त") currentFestival else todayPanchang?.festival,
                paksha = todayPanchang?.paksha,
                sourceType = todayPanchang?.sourceType,
                locationName = if (isEng) currentLocation.name else currentLocation.nameHi,
                error = panchangError,
                onViewFullPanchang = { viewModel.setScreen("Calendar") },
                onRetry = { panchangViewModel.loadDailyPanchang() }
            )
        }

        // 3. QUICK ACTIONS (Placed above Daily Shloka per Canonical UX Spec)
        item(key = "quick_actions") {
            HomeQuickActionsSection(
                isEng = isEng,
                onNavigateTab = { tab -> viewModel.navigateToBhakti(tab) },
                onNavigateScreen = { screen -> viewModel.setScreen(screen) }
            )
        }

        // 4. DAILY SPIRITUAL THOUGHT, SHLOKA & DEFINED MANTRA (Collapsed by Default)
        item(key = "today_content") {
            HomeTodayContentSection(
                isEng = isEng,
                dailyShlokaItem = dailyShlokaItem,
                dailyMantraItem = dailyMantraItem,
                thought = currentThought,
                onSpeak = { text ->
                    SadhakTextReader.toggle("home_spoken_content", text, isEng)
                },
                onCopy = { text ->
                    clipboardManager.setText(AnnotatedString(text))
                    Toast.makeText(context, if (isEng) "Copied to clipboard" else "क्लिपबोर्ड पर कॉपी किया गया", Toast.LENGTH_SHORT).show()
                },
                onShare = { text ->
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(Intent.createChooser(intent, if (isEng) "Share Divine Content" else "दिव्य संदेश साझा करें"))
                }
            )
        }

        // 5. SADHANA PROGRESS (Japa, Mala, Streak, Karma Points)
        item(key = "continue_sadhana") {
            HomeContinueSadhanaSection(
                isEng = isEng,
                favoriteMantras = favoriteMantras,
                karmaPoints = karmaPoints,
                dailyStreak = dailyStreak,
                japaCount = japaCount,
                malaCount = malaCount,
                onStartSadhana = { viewModel.setScreen("Bhakti") },
                onNavigate = { screen -> viewModel.setScreen(screen) }
            )
        }

        // 6. SANATAN KNOWLEDGE & SCRIPTURES
        item(key = "explore_grid") {
            HomeExploreSection(
                isEng = isEng,
                onNavigate = { screen -> viewModel.setScreen(screen) }
            )
        }

        // 7. DEITY EXPLORE & DEVOTIONAL CONTENT
        item(key = "deity_explore") {
            HomeDeityExploreSection(
                isEng = isEng,
                selectedDeity = selectedDeity,
                onDeitySelected = { selectedDeity = it },
                favoriteMantras = favoriteMantras,
                onNavigate = { screen -> viewModel.setScreen(screen) }
            )
        }

        // 8. VIDEO HUB PREVIEW
        item(key = "video_preview") {
            HomeVideoPreviewSection(
                isEng = isEng,
                videos = videos.take(4),
                onViewAll = { viewModel.setScreen("Videos") },
                onVideoClick = { viewModel.setScreen("Videos") }
            )
        }

        // 9. SADHAK AI MITRA
        item(key = "ai_mitra") {
            HomeAiMitraSection(
                isEng = isEng,
                onPromptClick = { prompt ->
                    aiViewModel.sendMessage(prompt, userRole)
                    viewModel.setScreen("Chat")
                },
                onOpenChat = { viewModel.setScreen("Chat") }
            )
        }
    }
    }
}

// ---------------------------------------------------------------------
// SUB-COMPONENTS & SECTIONS FOR HOME SCREEN
// ---------------------------------------------------------------------

@Composable
private fun HomeTopHeaderSection(
    isEng: Boolean,
    userName: String?,
    currentDate: String,
    hasNotifications: Boolean,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
            .padding(top = 10.dp, bottom = 6.dp)
    ) {
        // Subtle Background Sacred Temple Art in top right corner
        SacredTempleArtwork(
            modifier = Modifier
                .width(180.dp)
                .height(110.dp)
                .align(Alignment.TopEnd)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                Text(
                    text = "SANATAN SADHAK",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SadhakThemeColors.sacredSecondary,
                        fontSize = 20.sp,
                        letterSpacing = 0.8.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val dynamicGreeting = remember(isEng) {
                        com.example.data.DeityGreetingEngine.getGreeting(java.util.Calendar.getInstance(), isEng)
                    }
                    Text(
                        text = dynamicGreeting,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredPrimary,
                            fontSize = 11.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if (userName != null) {
                        if (isEng) "Welcome $userName" else "स्वागतम् $userName"
                    } else {
                        if (isEng) "Welcome Devotee" else "स्वागतम् साधक"
                    },
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = SadhakThemeColors.sacredSecondary,
                        fontSize = 15.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = currentDate,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SadhakThemeColors.sacredTextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.testTag("home_notifications_button")
                ) {
                    BadgedBox(
                        badge = {
                            if (hasNotifications) {
                                Badge(
                                    containerColor = SadhakThemeColors.sacredGold,
                                    contentColor = Color.White
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier.testTag("home_profile_button")
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(SadhakThemeColors.sacredIconBackground)
                            .border(1.5.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.6f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = SadhakThemeColors.sacredSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomePanchangSection(
    isEng: Boolean,
    tithi: String,
    nakshatra: String,
    sunrise: String,
    sunset: String,
    rahukaal: String = "—",
    abhijeet: String = "—",
    gulika: String? = null,
    yamaganda: String? = null,
    festival: String?,
    paksha: String?,
    sourceType: String?,
    locationName: String?,
    error: String?,
    onViewFullPanchang: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Today's Panchang" else "आज का पंचांग",
            subtitle = if (!locationName.isNullOrBlank()) {
                if (!paksha.isNullOrBlank()) "$tithi • $paksha ($locationName)" else "$tithi ($locationName)"
            } else {
                if (!paksha.isNullOrBlank()) "$tithi • $paksha" else tithi
            },
            actionText = if (isEng) "View Full Panchang" else "संपूर्ण पंचांग देखें",
            onActionClick = onViewFullPanchang
        )

        if (!error.isNullOrEmpty()) {
            SadhakErrorState(
                message = error,
                onRetry = onRetry
            )
        } else {
            SadhakCard(
                onClick = onViewFullPanchang,
                testTag = "home_panchang_card",
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredPrimary.copy(alpha = 0.35f)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (!festival.isNullOrBlank()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "🎉",
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = festival,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredSecondary,
                                    fontSize = 13.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // 2x2 Grid for Panchang Metrics
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left Column: Tithi & Sunrise
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isEng) "TITHI" else "तिथि",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SadhakThemeColors.sacredTextSecondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tithi,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredSecondary,
                                    fontSize = 15.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WbSunny,
                                    contentDescription = "Sunrise",
                                    tint = Color(0xFFE65100),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = if (isEng) "SUNRISE" else "सूर्योदय",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SadhakThemeColors.sacredTextSecondary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = sunrise,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SadhakThemeColors.sacredTextPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Right Column: Nakshatra & Sunset
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isEng) "NAKSHATRA" else "नक्षत्र",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SadhakThemeColors.sacredTextSecondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 0.5.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = nakshatra,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredSecondary,
                                    fontSize = 15.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.NightsStay,
                                    contentDescription = "Sunset",
                                    tint = Color(0xFF5C6BC0),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = if (isEng) "SUNSET" else "सूर्यास्त",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SadhakThemeColors.sacredTextSecondary,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = sunset,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SadhakThemeColors.sacredTextPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if (rahukaal.isNotBlank() && rahukaal != "—") {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SadhakThemeColors.sacredIconBackground.copy(alpha = 0.7f))
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isEng) "Rahu Kaal: $rahukaal" else "राहुकाल: $rahukaal",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SadhakThemeColors.sacredTextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (abhijeet.isNotBlank() && abhijeet != "—") {
                                Text(
                                    text = if (isEng) "Abhijit: $abhijeet" else "अभिजित: $abhijeet",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SadhakThemeColors.sacredPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(SadhakThemeColors.sacredDivider)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (sourceType == "REMOTE_SOURCE")
                                (if (isEng) "✓ VERIFIED / Verified Ephemeris Source" else "✓ प्रमाणित पंचांग स्रोत")
                            else
                                (if (isEng) "⚙ CALCULATED / Local Ephemeris" else "⚙ स्थानीय गणना"),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = SadhakThemeColors.sacredPrimary,
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable(onClick = onViewFullPanchang)
                        ) {
                            Text(
                                text = if (isEng) "Full Details" else "विस्तृत विवरण",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredPrimary,
                                    fontSize = 11.sp
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private enum class QuickActionType {
    JAPA_MALA, DHYAN, MY_MANTRA, AARTI, CHANTS
}

private data class QuickActionItem(
    val title: String,
    val type: QuickActionType,
    val target: String,
    val isTab: Boolean
)

@Composable
private fun HomeQuickActionsSection(
    isEng: Boolean,
    onNavigateTab: (String) -> Unit,
    onNavigateScreen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Quick Actions" else "त्वरित सेवाएँ",
            showDecorations = false
        )

        val quickActions = remember(isEng) {
            listOf(
                QuickActionItem(if (isEng) "Japa Mala" else "जप माला", QuickActionType.JAPA_MALA, "Counter", true),
                QuickActionItem(if (isEng) "Dhyan" else "ध्यान", QuickActionType.DHYAN, "Timer", true),
                QuickActionItem(if (isEng) "My Mantra" else "मेरे मंत्र", QuickActionType.MY_MANTRA, "My Mantras", true),
                QuickActionItem(if (isEng) "Aarti" else "आरती", QuickActionType.AARTI, "Bell", true),
                QuickActionItem(if (isEng) "Chants" else "स्तोत्र", QuickActionType.CHANTS, "Mantras", true)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            quickActions.forEach { action ->
                QuickActionTile(
                    label = action.title,
                    type = action.type,
                    onClick = {
                        if (action.isTab) {
                            onNavigateTab(action.target)
                        } else {
                            onNavigateScreen(action.target)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Community entry: always visible right under Quick Actions
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            onClick = { onNavigateScreen("Community") },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth().testTag("home_community_card")
        ) {
            Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Forum, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(if (isEng) "Satsang Community" else "सत्संग समुदाय", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(
                        if (isEng) "Ask questions, share yatra tips, experiences & hidden temples" else "प्रश्न पूछें, यात्रा सुझाव, अनुभव व छिपे तीर्थ साझा करें",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun QuickActionTile(
    label: String,
    type: QuickActionType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(SadhakThemeColors.sacredSurface)
            .border(1.dp, SadhakThemeColors.sacredDivider, RoundedCornerShape(14.dp))
            .clickable(onClick = {
                val feedbackType = when (type) {
                    QuickActionType.JAPA_MALA -> com.example.utils.HapticFeedbackManager.FeedbackType.JAPA_BEAD
                    QuickActionType.AARTI -> com.example.utils.HapticFeedbackManager.FeedbackType.BELL_RING
                    else -> com.example.utils.HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP
                }
                com.example.utils.HapticFeedbackManager.performHaptic(haptic, feedbackType)
                onClick()
            })
            .padding(vertical = 10.dp, horizontal = 2.dp)
            .testTag("quick_action_$label")
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(SadhakThemeColors.sacredIconBackground),
            contentAlignment = Alignment.Center
        ) {
            when (type) {
                QuickActionType.JAPA_MALA -> DivineJapaMalaIcon(size = 24.dp)
                QuickActionType.DHYAN -> DivineDhyanIcon(size = 24.dp)
                QuickActionType.MY_MANTRA -> DivineMyMantraIcon(size = 24.dp)
                QuickActionType.AARTI -> DivineAartiCounterIcon(size = 24.dp)
                QuickActionType.CHANTS -> DivineChantsIcon(size = 24.dp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = SadhakThemeColors.sacredTextPrimary,
                fontSize = 11.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun HomeExploreSection(
    isEng: Boolean,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Explore Sanatan Dharma" else "सनातन धर्म अन्वेषण",
            subtitle = if (isEng) "Scriptures, chants, rituals & spiritual wisdom" else "ग्रंथ, स्तोत्र, पूजा विधि एवं सनातन ज्ञान"
        )

        // Hero Feature 1: Sadhak AI Mitra
        SadhakCard(
            onClick = { onNavigate("Chat") },
            backgroundColor = SadhakThemeColors.sacredSurface,
            borderColor = SadhakThemeColors.sacredPrimary,
            modifier = Modifier.padding(bottom = 10.dp),
            testTag = "home_featured_ai_mitra"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(SadhakThemeColors.sacredPrimary, SadhakThemeColors.sacredSecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Mitra",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isEng) "Sadhak AI Mitra" else "साधक AI मित्र",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SadhakThemeColors.sacredPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (isEng) "AI" else "एआई",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredPrimary,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Ask questions regarding Dharma, Shlokas & Vedic customs" else "धर्म, श्लोक एवं वैदिक परंपराओं पर प्रश्न पूछें",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = SadhakThemeColors.sacredPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Hero Feature 2: Sacred Scriptures / Granths
        SadhakCard(
            onClick = { onNavigate("Knowledge") },
            backgroundColor = SadhakThemeColors.sacredSurface,
            borderColor = SadhakThemeColors.sacredPrimary.copy(alpha = 0.35f),
            modifier = Modifier.padding(bottom = 12.dp),
            testTag = "home_featured_granths"
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(SadhakThemeColors.sacredIconBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = "Scriptures",
                        tint = SadhakThemeColors.sacredPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isEng) "Sacred Scriptures & Granths" else "पवित्र धर्मग्रंथ एवं शास्त्र",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 15.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Study Vedas, Upanishads, Gita, and Ramayana" else "वेद, उपनिषद, श्रीमद्भगवद्गीता एवं रामायण अध्ययन",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = SadhakThemeColors.sacredPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        val exploreCategories = remember(isEng) {
            listOf(
                ExploreCategoryItem(
                    title = if (isEng) "Mantra & Japa" else "मंत्र एवं जाप",
                    desc = if (isEng) "Sacred Chants & Mala counter" else "पवित्र मंत्र एवं माला जप",
                    icon = Icons.Default.SelfImprovement,
                    route = "Tab:Counter"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Chants / Stotra" else "स्तोत्र एवं आरती",
                    desc = if (isEng) "Aarti, Chalisa & Stotras" else "आरती, चालीसा एवं स्तोत्र",
                    icon = Icons.Default.MusicNote,
                    route = "Tab:Mantras"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Pooja Vidhi" else "पूजा विधि",
                    desc = if (isEng) "Vedic Rituals & Vidhi Guide" else "वैदिक पूजा एवं अनुष्ठान",
                    icon = Icons.Default.LocalFlorist,
                    route = "BhaktiTools"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Dhyan Sadhana" else "ध्यान साधना",
                    desc = if (isEng) "Meditation & Mindful Peace" else "ध्यान एवं मानस शांति",
                    icon = Icons.Default.Star,
                    route = "Tab:Timer"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Temples & Darshan" else "तीर्थ एवं मंदिर",
                    desc = if (isEng) "Sacred Shrines & Darshan Guide" else "प्रसिद्ध मंदिर एवं दर्शन निर्देश",
                    icon = Icons.Default.AccountBalance,
                    route = "Tab:TempleFinder"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Spiritual Books" else "साहित्य एवं पुस्तकें",
                    desc = if (isEng) "Spiritual Literature & Texts" else "धर्मग्रंथ एवं आध्यात्मिक पुस्तकें",
                    icon = Icons.Default.Book,
                    route = "Knowledge"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Devotional Audio" else "भक्ति ऑडियो",
                    desc = if (isEng) "Sacred Chants & Audio Player" else "मंत्र, भजन एवं आरती ध्वनि",
                    icon = Icons.Default.Headphones,
                    route = "Bhakti"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Community" else "सत्संग समुदाय",
                    desc = if (isEng) "Ask, share & discuss with devotees" else "भक्तों से पूछें, साझा करें",
                    icon = Icons.Default.Forum,
                    route = "Community"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Divine Updates" else "दिव्य अपडेट",
                    desc = if (isEng) "Latest posts & temple news" else "नवीनतम पोस्ट एवं समाचार",
                    icon = Icons.Default.Campaign,
                    route = "InstagramUpdates"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Panchang Calendar" else "पंचांग कैलेंडर",
                    desc = if (isEng) "Tithi, vrat & festivals" else "तिथि, व्रत एवं पर्व",
                    icon = Icons.Default.CalendarMonth,
                    route = "Calendar"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "AI Mitra" else "एआई मित्र",
                    desc = if (isEng) "Ask any dharma question" else "धर्म संबंधी कोई भी प्रश्न",
                    icon = Icons.Default.AutoAwesome,
                    route = "Chat"
                ),
                ExploreCategoryItem(
                    title = if (isEng) "Video Hub" else "वीडियो हब",
                    desc = if (isEng) "Discourses, Bhajans & Katha" else "प्रवचन, भजन एवं कथा वीडियो",
                    icon = Icons.Default.VideoLibrary,
                    route = "Videos"
                )
            )
        }

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isTablet = maxWidth > 600.dp
            val chunkCount = if (isTablet) 3 else 2

            exploreCategories.chunked(chunkCount).forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { item ->
                        SadhakFeatureTile(
                            title = item.title,
                            shortDescription = item.desc,
                            icon = item.icon,
                            badge = item.badge,
                            onClick = { onNavigate(item.route) },
                            modifier = Modifier.weight(1f),
                            testTag = "explore_tile_${item.route}"
                        )
                    }
                    if (rowItems.size < chunkCount) {
                        repeat(chunkCount - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

private data class ExploreCategoryItem(
    val title: String,
    val desc: String,
    val icon: ImageVector,
    val route: String,
    val badge: String? = null
)

@Composable
private fun HomeDeityExploreSection(
    isEng: Boolean,
    selectedDeity: String,
    onDeitySelected: (String) -> Unit,
    favoriteMantras: List<com.example.data.FavoriteMantra>,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Deity Explore" else "देवता अन्वेषण",
            subtitle = if (isEng) "Filter spiritual wisdom by Devta" else "इष्ट देव अनुसार सामग्री देखें"
        )

        SadhakDeityFilterRow(
            selectedDeity = selectedDeity,
            onDeitySelected = onDeitySelected,
            useHindiNames = !isEng
        )

        val filteredItems = remember(selectedDeity, favoriteMantras) {
            if (selectedDeity.equals("All", ignoreCase = true) || selectedDeity.equals("सभी", ignoreCase = true)) {
                favoriteMantras
            } else {
                favoriteMantras.filter {
                    it.title.contains(selectedDeity, ignoreCase = true) ||
                            it.content.contains(selectedDeity, ignoreCase = true)
                }
            }
        }

        if (filteredItems.isEmpty() && !selectedDeity.equals("All", ignoreCase = true) && !selectedDeity.equals("सभी", ignoreCase = true)) {
            SadhakEmptyState(
                title = if (isEng) "No content found for $selectedDeity" else "$selectedDeity से सम्बद्ध सामग्री नहीं मिली",
                description = if (isEng) "Explore other deities or start a new mantra sadhana for $selectedDeity." else "अन्य देवता चुनें या $selectedDeity की नई साधना आरंभ करें।",
                actionLabel = if (isEng) "Show All" else "सभी देखें",
                onAction = { onDeitySelected("All") },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        } else if (filteredItems.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                filteredItems.take(2).forEach { mantra ->
                    SadhakCard(
                        onClick = { onNavigate("Bhakti") }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(SadhakThemeColors.primary.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SelfImprovement,
                                    contentDescription = null,
                                    tint = SadhakThemeColors.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mantra.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SadhakThemeColors.textPrimary
                                    )
                                )
                                Text(
                                    text = mantra.content,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SadhakThemeColors.textSecondary
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = SadhakThemeColors.textSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeContinueSadhanaSection(
    isEng: Boolean,
    favoriteMantras: List<com.example.data.FavoriteMantra>,
    karmaPoints: Int,
    dailyStreak: Int,
    japaCount: Int,
    malaCount: Int,
    onStartSadhana: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Sadhana Progress" else "दैनिक साधना प्रगति",
            subtitle = if (isEng) "Track your daily Japa, Mala & devotion" else "दैनिक मंत्र जप, माला एवं साधना ट्रैकर",
            actionText = if (isEng) "Sadhana Hub" else "साधना केंद्र",
            onActionClick = onStartSadhana
        )

        // Sadhana Key Metrics Cards: Japa Count, Mala Count, Streak, Karma
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Japa Chants Metric
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredPrimary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                    .clickable(onClick = { onNavigate("Counter") })
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$japaCount",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredPrimary,
                            fontSize = 17.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Japa Chants" else "मंत्र जप",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 10.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            // Mala Completed Metric
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredSecondary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                    .clickable(onClick = { onNavigate("Counter") })
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$malaCount",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 17.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Mala Done" else "माला पूर्ण",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 10.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            // Streak Metric
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                    .clickable(onClick = { onNavigate("Bhakti") })
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🔥 $dailyStreak",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredGold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Day Streak" else "दिवस निरंतर",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 10.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            // Karma Points Metric
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SadhakThemeColors.sacredSurface)
                    .border(1.dp, SadhakThemeColors.sacredPrimary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                    .clickable(onClick = { onNavigate("Profile") })
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "✨ $karmaPoints",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredPrimary,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (isEng) "Punya Pts" else "पुण्य अंक",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 10.sp
                        ),
                        maxLines = 1
                    )
                }
            }
        }

        if (favoriteMantras.isEmpty()) {
            SadhakCard(
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = SadhakThemeColors.primary.copy(alpha = 0.3f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DiyaDecoration(size = 36.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isEng) "Begin Your Daily Sadhana" else "अपनी दैनिक साधना आरंभ करें",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.secondary,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isEng) "Start mantra recitation, meditation, or Stotra japa to elevate your inner peace." else "मंत्र जप, ध्यान एवं स्तोत्र पाठ से अपनी आध्यात्मिक चेतना व ऊर्जा बढ़ाएं।",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.textSecondary,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    SadhakPrimaryButton(
                        text = if (isEng) "Start Sadhana Now" else "साधना प्रारंभ करें",
                        onClick = onStartSadhana,
                        icon = Icons.Default.SelfImprovement,
                        testTag = "home_start_sadhana_button"
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                favoriteMantras.take(2).forEach { item ->
                    SadhakCard(
                        onClick = { onNavigate("Bhakti") },
                        testTag = "sadhana_item_${item.id}"
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SadhakThemeColors.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SadhakThemeColors.textPrimary
                                    )
                                )
                                Text(
                                    text = item.content,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SadhakThemeColors.textSecondary
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = SadhakThemeColors.textSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeTodayContentSection(
    isEng: Boolean,
    dailyShlokaItem: com.example.data.DailyShlokaItem,
    dailyMantraItem: com.example.data.DailyMantraItem,
    thought: String,
    onSpeak: (String) -> Unit,
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit
) {
    var isShlokaExpanded by rememberSaveable { mutableStateOf(false) }
    var isMantraExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Daily Shloka & Wisdom" else "आज का दिव्य श्लोक व विचार",
            subtitle = if (isEng) "Timeless teachings from Vedic scriptures" else "श्रीमद्भगवद्गीता, वेद एवं उपनिषदों की अमर शिक्षा"
        )

        // Master Sacred Maroon Gita / Scripture Shloka Card (Collapsed by Default)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF7A1720), Color(0xFF5A0E15))
                    )
                )
                .border(1.5.dp, Color(0xFFD4A23A).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                .padding(16.dp)
                .testTag("home_daily_shloka_card")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val fullShlokaText = "${dailyShlokaItem.verseSanskrit}\n${if (isEng) dailyShlokaItem.meaningEn else dailyShlokaItem.meaningHi}\n— ${if (isEng) dailyShlokaItem.sourceScriptureEn else dailyShlokaItem.sourceScriptureHi} ${dailyShlokaItem.chapterVerse}"

                // Card Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        GoldenLotusMedallion(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = (if (isEng) "DAILY SHLOKA • " else "दैनिक श्लोक • ") + dailyShlokaItem.category.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD54F),
                                fontSize = 11.sp,
                                letterSpacing = 0.6.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Sanskrit Verse + Source Text (Takes full horizontal space with priority)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = dailyShlokaItem.verseSanskrit,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFFDF8),
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "— ${if (isEng) dailyShlokaItem.sourceScriptureEn else dailyShlokaItem.sourceScriptureHi} ${dailyShlokaItem.chapterVerse}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFFFD54F),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }

                    // Granth Artwork
                    SacredGranthArtwork(
                        modifier = Modifier
                            .width(54.dp)
                            .height(44.dp),
                        primaryTint = Color(0xFF9E1F2A),
                        goldTint = Color(0xFFFFD54F)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action Row BELOW main text (Listen | Copy | Share | Expand)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { onSpeak("${dailyShlokaItem.verseSanskrit}.\n${if (isEng) dailyShlokaItem.meaningEn else dailyShlokaItem.meaningHi}") },
                            modifier = Modifier.size(32.dp).testTag("shloka_listen_button")
                        ) {
                            Icon(
                                Icons.Default.VolumeUp,
                                contentDescription = "Listen",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { onCopy(fullShlokaText) },
                            modifier = Modifier.size(32.dp).testTag("shloka_copy_button")
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = Color(0xFFFFF8E9).copy(alpha = 0.85f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { onShare(fullShlokaText) },
                            modifier = Modifier.size(32.dp).testTag("shloka_share_button")
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Share",
                                tint = Color(0xFFFFF8E9).copy(alpha = 0.85f),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = { isShlokaExpanded = !isShlokaExpanded },
                        modifier = Modifier.size(32.dp).testTag("shloka_expand_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isShlokaExpanded) (if (isEng) "Less" else "कम") else (if (isEng) "More" else "अधिक"),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFFFFD54F),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                            Icon(
                                imageVector = if (isShlokaExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isShlokaExpanded) "Collapse" else "Expand",
                                tint = Color(0xFFFFD54F),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Expandable Section: Meaning & Practical Wisdom
                AnimatedVisibility(visible = isShlokaExpanded) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFFD4A23A).copy(alpha = 0.25f))
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isEng) dailyShlokaItem.meaningEn else dailyShlokaItem.meaningHi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFFFF8E9).copy(alpha = 0.92f),
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        )

                        if (dailyShlokaItem.practicalWisdomEn.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFFD54F).copy(alpha = 0.12f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.Top) {
                                    Text(text = "💡", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isEng) dailyShlokaItem.practicalWisdomEn else dailyShlokaItem.practicalWisdomHi,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color(0xFFFFD54F),
                                            fontSize = 11.sp,
                                            lineHeight = 15.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // Subtle Expand indicator when collapsed
                if (!isShlokaExpanded) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isShlokaExpanded = true }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isEng) "Tap to view meaning & wisdom ▼" else "अर्थ एवं व्याख्या देखें ▼",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFFFD54F).copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Daily Mantra Card with Explicit Defined Title and Metadata (Collapsed by Default)
        SadhakCard(
            backgroundColor = SadhakThemeColors.sacredSurface,
            borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f),
            testTag = "home_daily_mantra_card"
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val fullMantraText = "${if (isEng) dailyMantraItem.nameEn else dailyMantraItem.nameHi}\n${dailyMantraItem.sanskrit}\n${if (isEng) dailyMantraItem.meaningEn else dailyMantraItem.meaningHi}"

                // Card Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Text(text = "🕉", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = (if (isEng) "DAILY MANTRA • " else "दैनिक मंत्र • ") + (if (isEng) dailyMantraItem.deityEn else dailyMantraItem.deityHi).uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { onSpeak("${if (isEng) dailyMantraItem.nameEn else dailyMantraItem.nameHi}.\n${dailyMantraItem.sanskrit}.\n${if (isEng) dailyMantraItem.meaningEn else dailyMantraItem.meaningHi}") },
                            modifier = Modifier.size(28.dp).testTag("mantra_listen_button")
                        ) {
                            Icon(
                                Icons.Default.VolumeUp,
                                contentDescription = "Listen",
                                tint = SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { onCopy(fullMantraText) },
                            modifier = Modifier.size(28.dp).testTag("mantra_copy_button")
                        ) {
                            Icon(
                                Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = SadhakThemeColors.sacredTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { onShare(fullMantraText) },
                            modifier = Modifier.size(28.dp).testTag("mantra_share_button")
                        ) {
                            Icon(
                                Icons.Default.Share,
                                contentDescription = "Share",
                                tint = SadhakThemeColors.sacredTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        IconButton(
                            onClick = { isMantraExpanded = !isMantraExpanded },
                            modifier = Modifier.size(28.dp).testTag("mantra_expand_button")
                        ) {
                            Icon(
                                imageVector = if (isMantraExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = if (isMantraExpanded) "Collapse" else "Expand",
                                tint = SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Defined Mantra Name / Title
                Text(
                    text = if (isEng) dailyMantraItem.nameEn else dailyMantraItem.nameHi,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredSecondary,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Sanskrit Mantra
                Text(
                    text = dailyMantraItem.sanskrit,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredPrimary,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                )

                if (dailyMantraItem.transliteration.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = dailyMantraItem.transliteration,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 11.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                }

                // Expandable Section: Meaning & Spiritual Focus
                AnimatedVisibility(visible = isMantraExpanded) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(SadhakThemeColors.sacredDivider)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Meaning
                        Text(
                            text = if (isEng) dailyMantraItem.meaningEn else dailyMantraItem.meaningHi,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextPrimary,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        )

                        // Spiritual Focus / Purpose
                        if (dailyMantraItem.purposeEn.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SadhakThemeColors.sacredPrimary.copy(alpha = 0.08f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (isEng) "Focus: " else "साधना लक्ष्य: ",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SadhakThemeColors.sacredPrimary,
                                        fontSize = 11.sp
                                    )
                                )
                                Text(
                                    text = if (isEng) dailyMantraItem.purposeEn else dailyMantraItem.purposeHi,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = SadhakThemeColors.sacredTextSecondary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }

                // Subtle Expand indicator when collapsed
                if (!isMantraExpanded) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isMantraExpanded = true }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = if (isEng) "Tap to view meaning & guidance ▼" else "अर्थ एवं साधना लक्ष्य देखें ▼",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SadhakThemeColors.sacredPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeVideoPreviewSection(
    isEng: Boolean,
    videos: List<BhaktiVideo>,
    onViewAll: () -> Unit,
    onVideoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Sanatan Sadhak Videos" else "सनातन साधक वीडियो",
            actionText = if (isEng) "View All" else "सभी देखें",
            onActionClick = onViewAll
        )

        if (videos.isEmpty()) {
            SadhakCard(
                onClick = onViewAll,
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredPrimary.copy(alpha = 0.25f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.VideoLibrary,
                        contentDescription = null,
                        tint = SadhakThemeColors.sacredPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEng) "Explore Spiritual Videos" else "आध्यात्मिक वीडियो देखें",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredTextPrimary
                            )
                        )
                        Text(
                            text = if (isEng) "Watch Darshan, Aarti, and discourses" else "दर्शन, आरती व संत प्रवचन वीडियो देखें",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextSecondary
                            )
                        )
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = SadhakThemeColors.sacredTextSecondary)
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(videos) { video ->
                    SadhakCard(
                        onClick = onVideoClick,
                        modifier = Modifier.width(200.dp),
                        backgroundColor = SadhakThemeColors.sacredSurface,
                        borderColor = SadhakThemeColors.sacredPrimary.copy(alpha = 0.25f),
                        testTag = "home_video_item_${video.id}"
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SadhakThemeColors.sacredPrimary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = SadhakThemeColors.sacredPrimary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = video.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SadhakThemeColors.sacredTextPrimary,
                                    fontSize = 13.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = video.category,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = SadhakThemeColors.sacredPrimary,
                                    fontSize = 10.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeAiMitraSection(
    isEng: Boolean,
    onPromptClick: (String) -> Unit,
    onOpenChat: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SadhakThemeDimensions.screenPaddingMedium)
    ) {
        SadhakSectionHeader(
            title = if (isEng) "Sadhak AI Mitra" else "साधक AI मित्र",
            showDecorations = false
        )

        SadhakCard(
            onClick = onOpenChat,
            testTag = "sadhak_ai_mitra_card",
            backgroundColor = SadhakThemeColors.sacredSurface,
            borderColor = SadhakThemeColors.sacredPrimary
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(SadhakThemeColors.sacredPrimary, SadhakThemeColors.sacredSecondary)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Sadhak AI Mitra",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEng) "Sadhak AI Mitra" else "साधक AI मित्र",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = if (isEng) "Your companion for learning & exploring Sanatan Dharma" else "सनातन धर्म को जानने और समझने का आपका डिजिटल साथी",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = SadhakThemeColors.sacredPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isEng) "SUGGESTED PROMPTS" else "सुझाये गए प्रश्न",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredTextSecondary,
                        fontSize = 10.sp
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                val prompts = remember(isEng) {
                    if (isEng) {
                        listOf(
                            "Explain today's Panchang",
                            "Teach me a mantra",
                            "Explain a Granth",
                            "How is this Pooja traditionally performed?"
                        )
                    } else {
                        listOf(
                            "आज का पंचांग समझाएं",
                            "मुझे एक मंत्र सिखाएं",
                            "शास्त्र के बारे में पूछें",
                            "पूजा विधि विस्तार से जानें"
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    prompts.forEach { prompt ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SadhakThemeColors.primary.copy(alpha = 0.08f))
                                .border(0.5.dp, SadhakThemeColors.primary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .clickable { onPromptClick(prompt) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "✨ $prompt",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SadhakThemeColors.textPrimary,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = SadhakThemeColors.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
