
package com.example
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.screens.*
import com.example.ui.viewmodel.devotionalTracks
import com.example.ui.viewmodel.DevotionalTrack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.animation.core.animateFloat
import androidx.compose.ui.draw.alpha


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Divider
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.ui.components.SacredCard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SoftGoldBg
import com.example.ui.theme.WarmSand
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.theme.BrassTan
import com.example.ui.theme.Terracotta

val LocalSadhakViewModel = androidx.compose.runtime.staticCompositionLocalOf<SadhakViewModel> {
    error("No SadhakViewModel provided")
}

class MainActivity : ComponentActivity() {
    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        com.example.ui.audio.SadhakAudioManager.releaseAll()
    }


    override fun onStop() {
        super.onStop()
        try {
            androidx.lifecycle.ViewModelProvider(this)[com.example.ui.viewmodel.SadhakViewModel::class.java].backupSettingsNow()
        } catch (_: Exception) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.example.ui.audio.SadhakAudioManager.init(this)
        com.example.data.GeminiService.appContext = applicationContext
        enableEdgeToEdge()

        // Pre-create WebView cache directories to prevent Chromium opendir errors on startup
        try {
            val webViewCacheDirWasm = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/wasm")
            val webViewCacheDirJs = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/js")
            if (!webViewCacheDirWasm.exists()) {
                webViewCacheDirWasm.mkdirs()
            }
            if (!webViewCacheDirJs.exists()) {
                webViewCacheDirJs.mkdirs()
            }
        } catch (e: Exception) {
            android.util.Log.w("MainActivity", "Note: WebView cache directory initialization handled", e)
        }


        setContent {

                val viewModel: SadhakViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                // Process intent for destination navigation
                androidx.compose.runtime.LaunchedEffect(intent) {
                    val dest = intent.getStringExtra("destination")
                    if (dest != null) {
                        viewModel.setScreen(dest)
                        intent.removeExtra("destination")
                    }
                }
                
                val currentScreen by viewModel.currentScreen.collectAsState()
                val isEng by viewModel.isEnglish.collectAsState()
                val themePref by viewModel.themePreference.collectAsState()
                val isDark = when(themePref) {
                    1 -> false
                    2 -> true
                    else -> androidx.compose.foundation.isSystemInDarkTheme()
                }

                MyApplicationTheme(darkTheme = isDark) {
                    androidx.compose.runtime.CompositionLocalProvider(LocalSadhakViewModel provides viewModel) {

                val bgChantEnabled by viewModel.bgChantEnabled.collectAsState()
                val bgChantVolume by viewModel.bgChantVolume.collectAsState()
                com.example.ui.screens.GlobalChantPlayer(enabled = bgChantEnabled, volume = bgChantVolume)

                // Intercept Android Back Button/Gesture to go back to previous pages instead of exiting the app
                androidx.activity.compose.BackHandler(enabled = currentScreen != "Home") {
                    if (!viewModel.navigateBack()) {
                        viewModel.setScreen("Home")
                    }
                }

                val userRole by viewModel.userRole.collectAsState()
                val showLanguagePref by viewModel.showLanguagePrefDialog.collectAsState()
                val showGuestPromo by viewModel.showGuestPromoDialog.collectAsState()
                val shouldShowAutomaticOnboarding by viewModel.shouldShowAutomaticOnboarding.collectAsState()

                var showNotificationDialog by remember { mutableStateOf(false) }
                val hasNewNotifications by viewModel.hasNewNotifications.collectAsState()
                val notificationList by viewModel.notifications.collectAsState()

                Scaffold(
                    topBar = {
                        SadhakTopAppBar(
                            isEnglish = isEng,
                            onLanguageToggle = { viewModel.toggleLanguage() },
                            onLogoClick = { viewModel.setScreen("Home") },
                            onCalendarClick = { viewModel.setScreen("Calendar") },
                            hasNewNotifications = hasNewNotifications,
                            unreadCount = notificationList.size,
                            onNotificationClick = {
                                showNotificationDialog = true
                                viewModel.setHasNewNotifications(false)
                            }
                        )
                    },
                    bottomBar = {
                        val isBottomBarVisible by viewModel.isBottomBarVisible.collectAsState()
                        if (isBottomBarVisible) {
                            Column {
                                com.example.ui.audio.PersistentMiniPlayer(isEnglish = isEng)
                                SadhakBottomBar(
                                    currentScreen = currentScreen,
                                    userRole = userRole,
                                    isEnglish = isEng,
                                    onNavigate = { viewModel.setScreen(it) }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Soft fade-in for each new screen. The previous screen is removed
                        // immediately, so its cleanup (bottom bar, audio) runs before the new one starts.
                        val screenAlpha = remember(currentScreen) { androidx.compose.animation.core.Animatable(0f) }
                        androidx.compose.runtime.LaunchedEffect(currentScreen) {
                            screenAlpha.animateTo(1f, androidx.compose.animation.core.tween(durationMillis = 220))
                        }
                        Box(modifier = Modifier.fillMaxSize().graphicsLayer { alpha = screenAlpha.value }) {
                            when (currentScreen) {
                                "Home" -> HomeScreen(viewModel = viewModel)
                                "Knowledge" -> KnowledgeScreen(viewModel = viewModel)
                                "Community" -> CommunityScreen(viewModel = viewModel)
                                "Bhakti" -> BhaktiSadhanaScreen(viewModel = viewModel)
                                "BhaktiTools" -> BhaktiToolsScreen(viewModel = viewModel)
                                "Videos" -> VideoHubScreen(viewModel = viewModel)
                                "InstagramUpdates" -> InstagramUpdatesScreen(viewModel = viewModel, onBack = { if (!viewModel.navigateBack()) viewModel.setScreen("Home") })
                                "Chat" -> ChatScreen(viewModel = viewModel)
                                "Calendar" -> CalendarScreen(viewModel = viewModel, onBack = { if (!viewModel.navigateBack()) viewModel.setScreen("Home") }, isEng = isEng)
                                "Profile" -> ProfileScreen(viewModel = viewModel, onNavigateAdmin = { viewModel.setScreen("Admin") })
                                "Contribute" -> com.example.ui.screens.contribute.ContributeScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.setScreen("Profile") }
                                )
                                "Admin" -> {
                                    val isAdmin by viewModel.isAdmin.collectAsState()
                                    if (isAdmin) {
                                        AdminScreen(viewModel = viewModel)
                                    } else {
                                        AccessDeniedScreen(viewModel = viewModel)
                                    }
                                }
                            }
                        }

                        com.example.ui.audio.FullAudioPlayerModal(isEnglish = isEng)

                        if (showNotificationDialog) {
                            val notificationsList by viewModel.notifications.collectAsState()

                            AlertDialog(
                                onDismissRequest = { showNotificationDialog = false },
                                title = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isEng) "Divine Updates" else "दिव्य अपडेट",
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary,
                                            fontSize = 18.sp
                                        )
                                        IconButton(onClick = { showNotificationDialog = false }) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                                        }
                                    }
                                },
                                text = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth().heightIn(max = 350.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = if (isEng) "Your recent alerts:" else "आपकी नवीनतम सूचनाएं:",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            if (notificationsList.isNotEmpty()) {
                                                Row {
                                                    IconButton(onClick = { viewModel.markAllNotificationsAsRead() }, modifier = Modifier.size(24.dp)) {
                                                        Icon(Icons.Default.DoneAll, contentDescription = "Mark All Read", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                                                    }
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    IconButton(onClick = { viewModel.clearAllNotifications() }, modifier = Modifier.size(24.dp)) {
                                                        Icon(Icons.Default.Delete, contentDescription = "Clear All", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                                    }
                                                }
                                            }
                                        }

                                        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

                                        if (notificationsList.isEmpty()) {
                                            Box(
                                                modifier = Modifier.fillMaxWidth().padding(24.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = if (isEng) "No new notifications found." else "कोई नई सूचनाएं नहीं मिलीं।",
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        } else {
                                            LazyColumn(
                                                modifier = Modifier.weight(1f),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                items(notificationsList) { notif ->
                                                    val bgColor = if (notif.isRead) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                                    SacredCard(backgroundColor = bgColor) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(10.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .size(32.dp)
                                                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                                                                contentAlignment = Alignment.Center
                                                            ) {
                                                                val icon = if (notif.type == "video") Icons.Default.PlayArrow else Icons.Default.Notifications
                                                                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                                            }
                                                            Spacer(modifier = Modifier.width(10.dp))
                                                            Column(modifier = Modifier.weight(1f)) {
                                                                Text(
                                                                    text = notif.title,
                                                                    fontWeight = if (notif.isRead) FontWeight.Normal else FontWeight.Bold,
                                                                    fontSize = 12.sp,
                                                                    maxLines = 1
                                                                )
                                                                Text(
                                                                    text = notif.message,
                                                                    fontSize = 10.sp,
                                                                    color = MaterialTheme.colorScheme.secondary,
                                                                    maxLines = 2
                                                                )
                                                            }
                                                            if (!notif.isRead) {
                                                                IconButton(onClick = { viewModel.markNotificationAsRead(notif.id) }, modifier = Modifier.size(24.dp)) {
                                                                    Icon(Icons.Default.Done, contentDescription = "Mark Read", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                confirmButton = {
                                    TextButton(onClick = { showNotificationDialog = false }) {
                                        Text(if (isEng) "Done" else "संपन्न")
                                    }
                                }
                            )
                        }

                        val showLoginWall by viewModel.showLoginWall.collectAsState()
                        if (showLoginWall) {
                            AlertDialog(
                                onDismissRequest = { viewModel.dismissLoginWall() },
                                title = {
                                    Text(text = if (isEng) "Login Required" else "लॉगिन आवश्यक है", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                                },
                                text = {
                                    Text(text = if (isEng) "You have reached the limit of free actions. Please login to continue your spiritual journey." else "आपने अपनी नि:शुल्क सीमा पार कर ली है। कृपया अपनी आध्यात्मिक यात्रा जारी रखने के लिए लॉगिन करें।")
                                },
                                confirmButton = {
                                    TextButton(onClick = { 
                                        viewModel.dismissLoginWall()
                                        viewModel.setScreen("Profile")
                                    }) {
                                        Text(text = if (isEng) "Go to Login" else "लॉगिन करें", color = MaterialTheme.colorScheme.primary)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { viewModel.dismissLoginWall() }) {
                                        Text(text = if (isEng) "Not Now" else "अभी नहीं", color = Color.Gray)
                                    }
                                },
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        }

                        // 1. Language Preference Popup Dialog
                        if (showLanguagePref) {
                            AlertDialog(
                                onDismissRequest = { viewModel.dismissLanguagePrefDialog() },
                                title = {
                                    Text(
                                        text = if (isEng) "Preferred Language Preference" else "भाषा प्राथमिकता",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                },
                                text = {
                                    Text(
                                        text = if (isEng) {
                                            "Please choose your language of preference. You can easily switch this at any time using the toggle in the header."
                                        } else {
                                            "कृपया अपनी पसंदीदा भाषा चुनें। आप इसे कभी भी शीर्ष हेडर में स्विच कर सकते हैं।"
                                        }
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            // Set English
                                            if (!isEng) {
                                                viewModel.toggleLanguage()
                                            }
                                            viewModel.dismissLanguagePrefDialog()
                                        },
                                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = "English")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = {
                                            // Set Hindi
                                            if (isEng) {
                                                viewModel.toggleLanguage()
                                            }
                                            viewModel.dismissLanguagePrefDialog()
                                        },
                                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = "हिंदी")
                                    }
                                },
                                modifier = Modifier.testTag("language_pref_dialog")
                            )
                        }

                        // 2. Guest Promo Dialog
                        if (showGuestPromo) {
                            AlertDialog(
                                onDismissRequest = { viewModel.dismissGuestPromo() },
                                title = {
                                    Text(
                                        text = if (isEng) "Devotee Login Required" else "साधक प्रवेश आवश्यक",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                },
                                text = {
                                    Text(
                                        text = if (isEng) {
                                            "Please register or log in with your Sadhak account to unlock full spiritual articles, sacred chanting counters, guided meditation volume adjustments, community discussions, and Guide AI."
                                        } else {
                                            "सभी आध्यात्मिक लेख, मंत्र जाप काउंटर, भजन वीडियो, चर्चा मंडल और 'मार्गदर्शक एआई' का उपयोग करने के लिए कृपया नया खाता बनाएं या लॉगिन करें।"
                                        }
                                    )
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            viewModel.dismissGuestPromo()
                                            viewModel.setScreen("Profile")
                                        },
                                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = if (isEng) "Log In / Sign Up" else "लॉगिन / खाता बनाएं")
                                    }
                                },
                                dismissButton = {
                                    TextButton(
                                        onClick = { viewModel.dismissGuestPromo() },
                                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                                    ) {
                                        Text(text = if (isEng) "Cancel" else "रद्द करें")
                                    }
                                },
                                modifier = Modifier.testTag("guest_promo_dialog")
                            )
                        }

                        // 3. First-Time Devotee Guided Onboarding Tour
                        if (shouldShowAutomaticOnboarding && !showLanguagePref) {
                            com.example.ui.screens.onboarding.DevoteeOnboardingDialog(
                                isEnglish = isEng,
                                onComplete = {
                                    viewModel.completeOnboardingForActiveUser()
                                },
                                onSkip = {
                                    viewModel.skipOnboardingForActiveUser()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SadhakTopAppBar(
    isEnglish: Boolean,
    onLanguageToggle: () -> Unit,
    onLogoClick: () -> Unit,
    onCalendarClick: () -> Unit,
    hasNewNotifications: Boolean,
    unreadCount: Int = 0,
    onNotificationClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 1.dp, color = SadhakThemeColors.divider)
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clickable(
                            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                            indication = null
                        ) { onLogoClick() }
                        .testTag("header_logo_click")
                ) {
                    SanatanSadhakLogo()
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Sanatan Sadhak",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.secondary,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Text(
                            text = if (isEnglish) "सनातन • साधना • संस्कृति" else "सनातन • साधना • संस्कृति",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.textMuted,
                                fontSize = 9.sp,
                                letterSpacing = 0.8.sp
                            )
                        )
                    }
                }
            },
            actions = {
                // Notification Bell icon
                IconButton(onClick = onNotificationClick, modifier = Modifier.testTag("notification_bell_button")) {
                    Box {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = SadhakThemeColors.primary
                        )
                        if (hasNewNotifications) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 6.dp, y = (-4).dp)
                                    .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                                    .background(SadhakThemeColors.error, CircleShape)
                                    .padding(horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (unreadCount > 9) "9+" else if (unreadCount > 0) unreadCount.toString() else "!",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Calendar icon
                IconButton(onClick = onCalendarClick) {
                    val dayOfMonth = remember { java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH) }
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(1.8.dp, SadhakThemeColors.primary, RoundedCornerShape(5.dp)),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            Modifier.fillMaxWidth().height(6.dp).align(Alignment.TopCenter)
                                .background(SadhakThemeColors.primary, RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        )
                        Text(
                            text = dayOfMonth.toString(),
                            color = SadhakThemeColors.primary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                }

                // Bilingual toggle button
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SadhakThemeColors.primary.copy(alpha = 0.12f))
                        .border(1.dp, SadhakThemeColors.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                        .clickable(onClick = onLanguageToggle)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val on = SadhakThemeColors.primary
                        Text(
                            text = "EN",
                            color = if (isEnglish) Color.White else SadhakThemeColors.secondary.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.background(if (isEnglish) on else Color.Transparent, RoundedCornerShape(10.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                        Text(
                            text = "हिं",
                            color = if (!isEnglish) Color.White else SadhakThemeColors.secondary.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.background(if (!isEnglish) on else Color.Transparent, RoundedCornerShape(10.dp)).padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = SadhakThemeColors.secondary
            )
        )
    }
}

@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier, logoSize: androidx.compose.ui.unit.Dp = 36.dp, isAnimated: Boolean = false) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "logo_anim")
    val alpha by infiniteTransition.animateFloat(
        initialValue = if (isAnimated) 0.6f else 1f,
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(1200),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "blink"
    )

    Box(
        modifier = Modifier
            .size(logoSize)
            .then(modifier)
            .alpha(if (isAnimated) alpha else 1f)
            .clip(CircleShape)
            .background(Color(0xFF2B090F))
            .border(1.5.dp, SadhakThemeColors.sacredGold, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Image(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.app_logo),
            contentDescription = "Sanatan Sadhak Logo",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}

@Composable
fun SadhakBottomBar(
    currentScreen: String,
    userRole: String,
    isEnglish: Boolean,
    onNavigate: (String) -> Unit
) {
    // NEW Order: Knowledge | Sadhana | HOME | Videos | Profile
    val items = listOf(
        NavigationItem("Knowledge", if (isEnglish) "Knowledge" else "ज्ञान", Icons.Default.MenuBook),
        NavigationItem("Bhakti", if (isEnglish) "Sadhana" else "साधना", Icons.Default.SelfImprovement),
        NavigationItem("Home", if (isEnglish) "Home" else "मुख्य", Icons.Default.Home),
        NavigationItem("Videos", if (isEnglish) "Videos" else "वीडियो", Icons.Default.VideoLibrary),
        NavigationItem("Profile", if (isEnglish) "Profile" else "प्रोफ़ाइल", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = SadhakThemeColors.sacredNavigationBackground,
        tonalElevation = 0.dp,
        modifier = Modifier
            .testTag("bottom_nav_bar")
            .border(width = 1.dp, color = SadhakThemeColors.sacredDivider)
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.route
            val isCenterHome = item.route == "Home"

            val scale by androidx.compose.animation.core.animateFloatAsState(
                targetValue = if (isSelected) 1.15f else 1.0f,
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                    stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                )
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    if (isCenterHome) {
                        // Sanctum Center Home Treatment
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) SadhakThemeColors.sacredPrimary
                                    else SadhakThemeColors.sacredIconBackground
                                )
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) SadhakThemeColors.sacredGold else SadhakThemeColors.sacredPrimary.copy(alpha = 0.3f),
                                    shape = CircleShape
                                )
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (isSelected) Color.White else SadhakThemeColors.sacredPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = if (isCenterHome) 10.sp else 9.sp,
                        softWrap = false,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Visible,
                        fontWeight = if (isSelected || isCenterHome) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1,
                        color = if (isSelected) SadhakThemeColors.sacredNavigationSelected
                        else if (isCenterHome) SadhakThemeColors.sacredSecondary
                        else SadhakThemeColors.sacredNavigationUnselected
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SadhakThemeColors.sacredNavigationSelected,
                    selectedTextColor = SadhakThemeColors.sacredNavigationSelected,
                    unselectedIconColor = SadhakThemeColors.sacredNavigationUnselected,
                    unselectedTextColor = SadhakThemeColors.sacredNavigationUnselected,
                    indicatorColor = Color.Transparent
                ),
                modifier = Modifier.testTag("nav_item_${item.route}")
            )
        }
    }
}

data class NavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun PersistentAudioPlayer(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val isPlaying by viewModel.isAudioPlaying.collectAsState()
    val currentIndex by viewModel.currentAudioTrackIndex.collectAsState()
    val track = devotionalTracks[currentIndex]
    var showQueue by remember { mutableStateOf(false) }

    if (showQueue) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showQueue = false },
            title = { Text(if (isEnglish) "Audio Queue" else "ऑडियो कतार", fontWeight = FontWeight.Bold, color = SadhakThemeColors.secondary) },
            text = {
                LazyColumn {
                    devotionalTracks.groupBy { it.category }.forEach { (category, tracks) ->
                        item {
                            Text(
                                text = category,
                                style = MaterialTheme.typography.titleSmall,
                                color = SadhakThemeColors.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        items(tracks) { itemTrack ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.changeAudioTrack(devotionalTracks.indexOf(itemTrack))
                                        showQueue = false
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = if (itemTrack == track) SadhakThemeColors.primary else SadhakThemeColors.textSecondary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEnglish) itemTrack.titleEn else itemTrack.titleHi,
                                    color = if (itemTrack == track) SadhakThemeColors.primary else SadhakThemeColors.textPrimary,
                                    fontWeight = if (itemTrack == track) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SadhakThemeColors.primary.copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = itemTrack.category,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = SadhakThemeColors.primary,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = { showQueue = false }) {
                    Text(if (isEnglish) "Close" else "बंद करें", color = SadhakThemeColors.primary)
                }
            }
        )
    }

    // Slim Floating/Anchored Mini Player
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(SadhakThemeColors.sacredSurface)
            .border(width = 1.dp, color = SadhakThemeColors.sacredPrimary.copy(alpha = 0.25f))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left part: Rotating Icon & Track Info
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "music_rotation")
                    val rotationAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
                            animation = androidx.compose.animation.core.tween(durationMillis = 10000, easing = androidx.compose.animation.core.LinearEasing),
                            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
                        ),
                        label = "rotation"
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .graphicsLayer {
                                if (isPlaying) {
                                    rotationZ = rotationAngle
                                }
                            }
                            .clip(CircleShape)
                            .background(SadhakThemeColors.sacredIconBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isEnglish) track.titleEn else track.titleHi,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            color = SadhakThemeColors.sacredTextPrimary
                        )
                        Text(
                            text = if (isEnglish) track.artistEn else track.artistHi,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            color = SadhakThemeColors.sacredTextSecondary
                        )
                    }
                }

                // Right part: Controls (Queue, Prev, Play/Pause, Next, Close)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // Queue Button
                    IconButton(
                        onClick = { showQueue = true },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Queue",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Prev Track Button
                    IconButton(
                        onClick = { viewModel.prevAudioTrack() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous Track",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Play / Pause Button with solid circle
                    IconButton(
                        onClick = { viewModel.toggleAudioPlay() },
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(SadhakThemeColors.sacredPrimary)
                    ) {
                        if (isPlaying) {
                            PauseIcon(tint = Color.White)
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // Next Track Button
                    IconButton(
                        onClick = { viewModel.nextAudioTrack() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Track",
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Close Button
                    IconButton(
                        onClick = { viewModel.closeAudioPlayer() },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Player",
                            tint = SadhakThemeColors.sacredTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            // Slim Progress Slider
            val progress by viewModel.globalAudioProgress.collectAsState()
            androidx.compose.material3.Slider(
                value = progress,
                onValueChange = { viewModel.seekAudio(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp),
                colors = androidx.compose.material3.SliderDefaults.colors(
                    thumbColor = SadhakThemeColors.sacredPrimary,
                    activeTrackColor = SadhakThemeColors.sacredPrimary,
                    inactiveTrackColor = SadhakThemeColors.sacredPrimary.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Composable
fun PauseIcon(tint: Color) {
    Canvas(modifier = Modifier.size(14.dp)) {
        val w = size.width
        val h = size.height
        val barWidth = 3.dp.toPx()
        // Left bar
        drawRect(
            color = tint,
            topLeft = Offset(w * 0.25f, 0f),
            size = androidx.compose.ui.geometry.Size(barWidth, h)
        )
        // Right bar
        drawRect(
            color = tint,
            topLeft = Offset(w * 0.60f, 0f),
            size = androidx.compose.ui.geometry.Size(barWidth, h)
        )
    }
}