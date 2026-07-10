
package com.example
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            e.printStackTrace()
        }

        setContent {

                val viewModel: SadhakViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                
                val currentScreen by viewModel.currentScreen.collectAsState()
                val isEng by viewModel.isEnglish.collectAsState()
                val themePref by viewModel.themePreference.collectAsState()
                val isDark = when(themePref) {
                    1 -> false
                    2 -> true
                    else -> androidx.compose.foundation.isSystemInDarkTheme()
                }

                MyApplicationTheme(darkTheme = isDark) {

                val bgChantEnabled by viewModel.bgChantEnabled.collectAsState()
                val bgChantVolume by viewModel.bgChantVolume.collectAsState()
                com.example.ui.screens.GlobalChantPlayer(enabled = bgChantEnabled, volume = bgChantVolume)

                // Intercept Android Back Button/Gesture to go back to previous pages instead of exiting the app
                androidx.activity.compose.BackHandler(enabled = currentScreen != "Home") {
                    when (currentScreen) {
                        "Admin" -> viewModel.setScreen("Profile")
                        "AartiBell" -> viewModel.setScreen("BhaktiTools")
                        else -> viewModel.setScreen("Home")
                    }
                }

                val userRole by viewModel.userRole.collectAsState()
                val showLanguagePref by viewModel.showLanguagePrefDialog.collectAsState()
                val showGuestPromo by viewModel.showGuestPromoDialog.collectAsState()

                var showNotificationDialog by remember { mutableStateOf(false) }
                val hasNewNotifications by viewModel.hasNewNotifications.collectAsState()

                Scaffold(
                    topBar = {
                        SadhakTopAppBar(
                            isEnglish = isEng,
                            onLanguageToggle = { viewModel.toggleLanguage() },
                            onLogoClick = { viewModel.setScreen("Home") },
                            onCalendarClick = { viewModel.setScreen("Calendar") },
                            hasNewNotifications = hasNewNotifications,
                            onNotificationClick = {
                                showNotificationDialog = true
                                viewModel.setHasNewNotifications(false)
                            }
                        )
                    },
                    bottomBar = {
                        Column {
                            val isPlayerVisible by viewModel.isAudioPlayerVisible.collectAsState()
                            if (isPlayerVisible) {
                                PersistentAudioPlayer(viewModel = viewModel, isEnglish = isEng)
                            }
                            SadhakBottomBar(
                                currentScreen = currentScreen,
                                userRole = userRole,
                                isEnglish = isEng,
                                onNavigate = { viewModel.setScreen(it) }
                            )
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
                        when (currentScreen) {
                            "Home" -> HomeScreen(viewModel = viewModel)
                            "Knowledge" -> KnowledgeScreen(viewModel = viewModel)
                            "Community" -> CommunityScreen(viewModel = viewModel)
                            "Bhakti" -> BhaktiSadhanaScreen(viewModel = viewModel)
                            "BhaktiTools" -> BhaktiToolsScreen(viewModel = viewModel)
                            "AartiBell" -> AartiBellScreen(viewModel = viewModel, isEng = isEng, onBack = { viewModel.setScreen("BhaktiTools") })
                            "Videos" -> VideoHubScreen(viewModel = viewModel)
                            "InstagramUpdates" -> InstagramUpdatesScreen(viewModel = viewModel, onBack = { viewModel.setScreen("Home") })
                            "Chat" -> ChatScreen(viewModel = viewModel)
                            "Calendar" -> CalendarScreen(viewModel = viewModel, onBack = { viewModel.setScreen("Home") }, isEng = isEng)
                            "Profile" -> ProfileScreen(viewModel = viewModel, onNavigateAdmin = { viewModel.setScreen("Admin") })
                            "Admin" -> {
                                val isAdmin by viewModel.isAdmin.collectAsState()
                                if (isAdmin) {
                                    AdminScreen(viewModel = viewModel)
                                } else {
                                    AccessDeniedScreen(viewModel = viewModel)
                                }
                            }
                        }

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
    onNotificationClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline) // subtle bottom border
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
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 16.sp,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Text(
                            text = if (isEnglish) "INNER PEACE • DHARMA" else "अंतर्मन की शांति • धर्म",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrassTan,
                                fontSize = 8.sp,
                                letterSpacing = 1.sp
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
                            tint = MaterialTheme.colorScheme.primary
                        )
                        if (hasNewNotifications) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Red, CircleShape)
                                    .align(Alignment.TopEnd)
                            )
                        }
                    }
                }

                // Calendar icon
                IconButton(onClick = onCalendarClick) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar", tint = MaterialTheme.colorScheme.primary)
                }

                // Highly polished bilingual toggle button
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.tertiary, RoundedCornerShape(16.dp))
                        .background(SoftGoldBg, RoundedCornerShape(16.dp))
                        .clickable(onClick = onLanguageToggle)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isEnglish) "EN | हिंदी" else "हिंदी | EN",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
                
                // Profile Avatar Badge
                Box(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(36.dp)
                        .border(2.dp, Color.White, CircleShape)
                        .background(WarmSand, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White, // Pure polished white background
                titleContentColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

@Composable
fun SanatanSadhakLogo(modifier: Modifier = Modifier, logoSize: androidx.compose.ui.unit.Dp = 36.dp, isAnimated: Boolean = false) {
    
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = if (isAnimated) 0.4f else 1f,
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "blink"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(logoSize)
            .then(modifier)
            .alpha(if (isAnimated) alpha else 1f)
            .clip(CircleShape)
            .background(Color(0xFF4A0E17)) // Deep wine/maroon
            .border(2.dp, MaterialTheme.colorScheme.tertiary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Draw Trishula (Trident) using Canvas
        Canvas(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            val w = size.width
            val h = size.height
            
            // Central prong
            drawLine(
                color = primaryColor,
                start = Offset(w / 2, h * 0.15f),
                end = Offset(w / 2, h * 0.85f),
                strokeWidth = 2.dp.toPx()
            )
            
            // Left curve
            val leftPath = Path().apply {
                moveTo(w / 2, h * 0.7f)
                quadraticTo(w * 0.2f, h * 0.65f, w * 0.25f, h * 0.3f)
            }
            drawPath(
                path = leftPath,
                color = primaryColor,
                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // Right curve
            val rightPath = Path().apply {
                moveTo(w / 2, h * 0.7f)
                quadraticTo(w * 0.8f, h * 0.65f, w * 0.75f, h * 0.3f)
            }
            drawPath(
                path = rightPath,
                color = primaryColor,

                style = Stroke(width = 1.5.dp.toPx())
            )
            
            // Flame Glow on top
            drawCircle(
                color = Color(0xFFFF9800).copy(alpha = 0.8f),
                radius = 3.dp.toPx(),
                center = Offset(w / 2, h * 0.15f)
            )
            drawCircle(
                color = Color(0xFFFFEB3B),
                radius = 1.5.dp.toPx(),
                center = Offset(w / 2, h * 0.15f)
            )
        }
        
        // Golden OM symbol overlay in the center
        Text(
            text = "ॐ",
            color = Color(0xFFFFC107),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.offset(y = 1.dp)
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
    val items = mutableListOf(
        NavigationItem("Bhakti", if (isEnglish) "Sadhana" else "साधना", Icons.Default.SelfImprovement),
        NavigationItem("BhaktiTools", if (isEnglish) "Tools" else "साधन", Icons.Default.Build),
        NavigationItem("Home", if (isEnglish) "Home" else "मुख्य", Icons.Default.Home),
        NavigationItem("Chat", if (isEnglish) "Sadhak Mitra AI" else "साधक मित्र AI", Icons.Default.Chat),
        NavigationItem("Profile", if (isEnglish) "Devotee" else "भक्त", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp, // Flat professional style
        modifier = Modifier
            .testTag("bottom_nav_bar")
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline) // Elegant top border
    ) {
        items.forEach { item ->
            val isSelected = currentScreen == item.route
            val scale by androidx.compose.animation.core.animateFloatAsState(
                targetValue = if (isSelected) 1.2f else 1.0f,
                animationSpec = androidx.compose.animation.core.spring(
                    dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
                    stiffness = androidx.compose.animation.core.Spring.StiffnessLow
                )
            )
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp).graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 9.sp,
                        softWrap = false,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Visible,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray.copy(alpha = 0.6f),
                    unselectedTextColor = Color.Gray.copy(alpha = 0.8f),
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.95f))
            .border(width = 1.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left part: Icon & Text
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Sacred spinning icon
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
                        .size(36.dp)
                        .graphicsLayer {
                            if (isPlaying) {
                                rotationZ = rotationAngle
                            }
                        }
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SelfImprovement,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isEnglish) track.titleEn else track.titleHi,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = if (isEnglish) track.artistEn else track.artistHi,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Right part: Controls (Prev, Play/Pause, Next)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Prev Track Button
                IconButton(
                    onClick = { viewModel.prevAudioTrack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous Track",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Play / Pause Button with circle outline
                IconButton(
                    onClick = { viewModel.toggleAudioPlay() },
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                ) {
                    if (isPlaying) {
                        PauseIcon(tint = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Next Track Button
                IconButton(
                    onClick = { viewModel.nextAudioTrack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next Track",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Close / Hide Audio Player Button
                IconButton(
                    onClick = { viewModel.closeAudioPlayer() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Player",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
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
