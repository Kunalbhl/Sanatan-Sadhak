package com.example

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarToday
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
import com.example.ui.screens.AccessDeniedScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.BhaktiSadhanaScreen
import com.example.ui.screens.BhaktiToolsScreen
import com.example.ui.screens.AartiBellScreen
import com.example.ui.screens.CalendarScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KnowledgeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.VideoHubScreen
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

                val userRole by viewModel.userRole.collectAsState()
                val showLanguagePref by viewModel.showLanguagePrefDialog.collectAsState()
                val showGuestPromo by viewModel.showGuestPromoDialog.collectAsState()

                Scaffold(
                    topBar = {
                        SadhakTopAppBar(
                            isEnglish = isEng,
                            onLanguageToggle = { viewModel.toggleLanguage() },
                            onLogoClick = { viewModel.setScreen("Home") },
                            onCalendarClick = { viewModel.setScreen("Calendar") }
                        )
                    },
                    bottomBar = {
                        SadhakBottomBar(
                            currentScreen = currentScreen,
                            userRole = userRole,
                            isEnglish = isEng,
                            onNavigate = { viewModel.setScreen(it) }
                        )
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
                            "AartiBell" -> AartiBellScreen(isEng = isEng, onBack = { viewModel.setScreen("BhaktiTools") })
                            "Videos" -> VideoHubScreen(viewModel = viewModel)
                            "Chat" -> ChatScreen(viewModel = viewModel)
                            "Calendar" -> CalendarScreen(onBack = { viewModel.setScreen("Home") }, isEng = isEng)
                            "Profile" -> ProfileScreen(viewModel = viewModel, onNavigateAdmin = { viewModel.setScreen("Admin") })
                            "Admin" -> {
                                if (userRole == "SuperUser") {
                                    AdminScreen(viewModel = viewModel)
                                } else {
                                    AccessDeniedScreen(viewModel = viewModel)
                                }
                            }
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
    onCalendarClick: () -> Unit
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
                // Calendar icon
                IconButton(onClick = onCalendarClick) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendar", tint = MaterialTheme.colorScheme.primary)
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
fun SanatanSadhakLogo(modifier: Modifier = Modifier) {
    
    val primaryColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .size(36.dp)
            .then(modifier)
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
