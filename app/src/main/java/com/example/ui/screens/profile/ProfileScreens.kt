package com.example.ui.screens
import com.example.SanatanSadhakLogo
import com.example.ui.components.GoldenLotusMedallion
import com.example.ui.theme.SadhakThemeColors
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
import androidx.compose.foundation.layout.PaddingValues
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

@OptIn(ExperimentalMaterial3Api::class)
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
    var showPrivacyPolicy by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var showOnboardingTour by remember { mutableStateOf(false) }
    var showAllBadgesDialog by remember { mutableStateOf(false) }

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

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // LOGIN LOGIC
    Box(modifier = Modifier.fillMaxSize().background(SadhakThemeColors.sacredBackground)) {
    if (userRole == "Guest") {
        var showSignUp by remember { mutableStateOf(false) }
        var showForgotPasswordDialog by remember { mutableStateOf(false) }
        var showWelcomeGreeting by remember { mutableStateOf(false) }
        var resetEmailInput by remember { mutableStateOf("") }
        var resetStatusText by remember { mutableStateOf("") }
        var isResetSending by remember { mutableStateOf(false) }
        var resetSuccess by remember { mutableStateOf(false) }
        var termsAccepted by remember { mutableStateOf(false) }

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

        var isPassVisible by remember { mutableStateOf(false) }
        var isConfirmPassVisible by remember { mutableStateOf(false) }
        var isLoginPassVisible by remember { mutableStateOf(false) }
        val context = androidx.compose.ui.platform.LocalContext.current

        // Looping soft OM ambient audio for Devotee Login screen
        DisposableEffect(Unit) {
            com.example.ui.audio.SadhakAudioManager.startLoginAmbient(context)
            onDispose {
                com.example.ui.audio.SadhakAudioManager.stopLoginAmbient()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .testTag("profile_login_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Canonical Sanatan Sadhak logo
            com.example.SanatanSadhakLogo(
                modifier = Modifier.padding(bottom = 12.dp),
                logoSize = 96.dp,
                isAnimated = true
            )

            // 2. "Sanatan Sadhak"
            Text(
                text = "Sanatan Sadhak",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = SadhakThemeColors.sacredSecondary,
                    fontSize = 24.sp,
                    letterSpacing = 0.5.sp
                )
            )

            // 3. Short devotional tagline
            Text(
                text = if (isEng) "Your Daily Companion for Sanatan Sadhana" else "सनातन साधना और नित्य धर्म का पावन साथी",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SadhakThemeColors.sacredPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // 4. Credential Card (Primary Login / Registration Form - Placed Prominently at Top)
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.4f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = if (showSignUp) {
                            if (isEng) "Create Devotee Account" else "नया भक्त खाता बनाएं"
                        } else {
                            if (isEng) "Devotee Sign In" else "भक्त प्रवेश"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary
                        )
                    )

                    if (showSignUp) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text(text = if (isEng) "Full Name" else "पूरा नाम") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = mobileInput,
                            onValueChange = { mobileInput = it },
                            label = { Text(text = if (isEng) "Mobile Number" else "मोबाइल नंबर") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text(text = if (isEng) "Devotee Email" else "भक्त ईमेल") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = cityInput,
                                onValueChange = { cityInput = it },
                                label = { Text(text = if (isEng) "City" else "शहर") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                    unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                    focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                    unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                    cursorColor = SadhakThemeColors.sacredPrimary,
                                    focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                    unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                                ),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = stateInput,
                                onValueChange = { stateInput = it },
                                label = { Text(text = if (isEng) "State" else "राज्य") },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                    unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                    focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                    unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                    cursorColor = SadhakThemeColors.sacredPrimary,
                                    focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                    unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                                ),
                                singleLine = true
                            )
                        }

                        OutlinedTextField(
                            value = passInput,
                            onValueChange = { passInput = it },
                            label = { Text(text = if (isEng) "New Password" else "नया पासवर्ड") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isPassVisible = !isPassVisible }) {
                                    Icon(
                                        imageVector = if (isPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Show or hide password",
                                        tint = SadhakThemeColors.sacredPrimary
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
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            visualTransformation = if (isConfirmPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isConfirmPassVisible = !isConfirmPassVisible }) {
                                    Icon(
                                        imageVector = if (isConfirmPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Show or hide password",
                                        tint = SadhakThemeColors.sacredPrimary
                                    )
                                }
                            },
                            singleLine = true
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { termsAccepted = !termsAccepted }
                        ) {
                            androidx.compose.material3.Checkbox(
                                checked = termsAccepted,
                                onCheckedChange = { termsAccepted = it },
                                colors = androidx.compose.material3.CheckboxDefaults.colors(
                                    checkedColor = SadhakThemeColors.sacredPrimary,
                                    uncheckedColor = SadhakThemeColors.sacredGold
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEng) "I accept Terms & Privacy Policy" else "मैं नियम व गोपनीयता नीति स्वीकार करता/करती हूँ",
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 12.sp
                            )
                        }
                    } else {
                        // Email field
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text(text = if (isEng) "Devotee Email" else "भक्त ईमेल") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            singleLine = true
                        )

                        // Password field
                        OutlinedTextField(
                            value = passInput,
                            onValueChange = { passInput = it },
                            label = { Text(text = if (isEng) "Password" else "पासवर्ड") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                unfocusedTextColor = SadhakThemeColors.sacredTextPrimary,
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                                cursorColor = SadhakThemeColors.sacredPrimary,
                                focusedLabelColor = SadhakThemeColors.sacredPrimary,
                                unfocusedLabelColor = SadhakThemeColors.sacredTextSecondary
                            ),
                            visualTransformation = if (isLoginPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isLoginPassVisible = !isLoginPassVisible }) {
                                    Icon(
                                        imageVector = if (isLoginPassVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Show or hide password",
                                        tint = SadhakThemeColors.sacredPrimary
                                    )
                                }
                            },
                            singleLine = true
                        )

                        // Forgot Password & Remember Me row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { rememberMe = !rememberMe }
                            ) {
                                androidx.compose.material3.Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = androidx.compose.material3.CheckboxDefaults.colors(
                                        checkedColor = SadhakThemeColors.sacredPrimary,
                                        uncheckedColor = SadhakThemeColors.sacredGold
                                    )
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isEng) "Remember Me" else "मुझे याद रखें",
                                    color = SadhakThemeColors.sacredSecondary,
                                    fontSize = 13.sp
                                )
                            }

                            Text(
                                text = if (isEng) "Forgot Password?" else "पासवर्ड भूल गए?",
                                color = SadhakThemeColors.sacredPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable {
                                    resetEmailInput = emailInput
                                    resetStatusText = ""
                                    resetSuccess = false
                                    showForgotPasswordDialog = true
                                }
                            )
                        }
                    }

                    if (statusText.isNotEmpty()) {
                        Text(
                            text = statusText,
                            color = SadhakThemeColors.error,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    // 9. PRIMARY "Sign In" button
                    Button(
                        onClick = {
                            statusText = ""
                            if (emailInput.isNotBlank() && passInput.isNotBlank()) {
                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.trim()).matches()) {
                                    statusText = if (isEng) "Please enter a valid email" else "कृपया एक मान्य ईमेल दर्ज करें"
                                    return@Button
                                }
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
                                    if (!termsAccepted) {
                                        statusText = if (isEng) "Please accept terms & conditions" else "कृपया नियम और शर्तें स्वीकार करें"
                                        return@Button
                                    }
                                    viewModel.signUp(emailInput.trim(), passInput, nameInput.trim(), cityInput.trim(), stateInput.trim(), mobileInput.trim()) { success, msg ->
                                        if (success) {
                                            com.example.ui.audio.SadhakAudioManager.stopLoginAmbient()
                                            showWelcomeGreeting = true
                                        } else {
                                            statusText = msg
                                        }
                                    }
                                } else {
                                    viewModel.login(emailInput.trim(), passInput, rememberMe) { success, msg ->
                                        if (success) {
                                            com.example.ui.audio.SadhakAudioManager.stopLoginAmbient()
                                            showWelcomeGreeting = true
                                        } else {
                                            statusText = msg
                                        }
                                    }
                                }
                            } else {
                                statusText = if (isEng) "Email and Password required" else "ईमेल और पासवर्ड आवश्यक हैं"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_devotee_submit_auth"),
                        colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (showSignUp) (if (isEng) "Register Devotee" else "पंजीकरण करें") else (if (isEng) "Sign In" else "प्रवेश करें"),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // 10. Secondary Action
                    Text(
                        text = if (showSignUp) {
                            if (isEng) "Already have an account? Sign In" else "पहले से खाता है? प्रवेश करें"
                        } else {
                            if (isEng) "New Devotee? Create an Account" else "नए भक्त? नया खाता बनाएं"
                        },
                        color = SadhakThemeColors.sacredSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable {
                                showSignUp = !showSignUp
                                statusText = ""
                            }
                    )
                }
            }

            // 5. Divider "— OR —"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier.weight(1f),
                    color = SadhakThemeColors.sacredGold.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
                Text(
                    text = if (isEng) "  OR  " else "  अथवा  ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SadhakThemeColors.sacredTextSecondary,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Divider(
                    modifier = Modifier.weight(1f),
                    color = SadhakThemeColors.sacredGold.copy(alpha = 0.3f),
                    thickness = 1.dp
                )
            }

            // 6. "Continue with Google"
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        statusText = ""
                        com.example.ui.audio.SadhakAudioManager.stopLoginAmbient()
                        var act: android.app.Activity? = null
                        var c: android.content.Context? = context
                        while (c is android.content.ContextWrapper) { if (c is android.app.Activity) { act = c; break }; c = c.baseContext }
                        if (act == null) { statusText = "Google sign-in is not available here."; return@clickable }
                        viewModel.loginWithGoogle(act) { success, msg ->
                            if (success) {
                                showWelcomeGreeting = true
                            } else {
                                statusText = msg
                            }
                        }
                    }
                    .testTag("btn_continue_google")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 1.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "G",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = Color(0xFF4285F4)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (isEng) "Continue with Google" else "Google के साथ जारी रखें",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = SadhakThemeColors.sacredTextPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 7. "Continue as Guest" / Offline Access
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.35f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        com.example.ui.audio.SadhakAudioManager.stopLoginAmbient()
                        viewModel.setScreen("Home")
                    }
                    .testTag("btn_continue_guest")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = SadhakThemeColors.sacredPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isEng) "Continue as Guest (Offline)" else "अतिथि के रूप में जारी रखें (ऑफलाइन)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (isEng) {
                            "Offline guest mode enables Mantras, daily Panchang, and Meditation. Cloud sync, devotee identity, and community require signing in."
                        } else {
                            "ऑफलाइन अतिथि मोड में मंत्र, दैनिक पंचांग और ध्यान उपलब्ध हैं। क्लाउड सिंक व भक्त समुदाय के लिए साइन इन आवश्यक है।"
                        },
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 8. Why Sanatan Sadhak / Sacred Features & Benefits Section (Below Auth Options)
            SacredCard(
                backgroundColor = SadhakThemeColors.sacredSurface,
                borderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.35f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🕉", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEng) "Why Sanatan Sadhak?" else "सनातन साधक की विशेषताएं",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary
                            )
                        )
                    }

                    val benefits = if (isEng) listOf(
                        "🕉 Vedic Panchang — Accurate Tithi, Nakshatra, Rahu Kaal & Muhurtas",
                        "📿 Sacred Japa Mala — 108 Bead counter with haptic touch feedback",
                        "🧘 Dhyan Sanctuary — Peaceful Om vibrations, flute and temple ambience",
                        "📜 Granth & Scriptures — Srimad Bhagavad Gita & Upanishads with meanings",
                        "🎵 Devotional Audio — Stotrams, Chalisa, Bhajans with background playback",
                        "🤖 Sadhak Mitra AI — Scriptural inquiries and spiritual wisdom",
                        "⏰ Sadhana Reminders — Timely alarms for Brahma Muhurta & Sandhya Aarti",
                        "📱 100% Offline Capable — Sadhana tools work seamlessly without internet"
                    ) else listOf(
                        "🕉 वैदिक पंचांग — सटीक तिथि, नक्षत्र, राहुकाल एवं शुभ मुहूर्त",
                        "📿 पावन जप माला — १०८ मणका काउंटर और स्पर्श कंपन",
                        "🧘 दिव्य ध्यान — अखंड ॐ ध्वनि, बाँसुरी एवं मंदिर वातावरण",
                        "📜 धर्मग्रंथ संग्रह — श्रीमद्भगवद्गीता एवं उपनिषद् सार्थ अनुवाद",
                        "🎵 भक्तिमय ऑडियो — स्तोत्र, चालीसा, भजन बैकग्राउंड प्लेयर सहित",
                        "🤖 साधक मित्र AI — धार्मिक व शास्त्र जिज्ञासा समाधान",
                        "⏰ साधना स्मरण — ब्रह्म मुहूर्त एवं संध्या आरती समय पर अलर्ट",
                        "📱 पूर्ण ऑफलाइन साधन — बिना इंटरनेट के भी सभी साधना साधन सक्रिय"
                    )

                    benefits.forEach { benefit ->
                        Text(
                            text = benefit,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextPrimary,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 9. Terms & Conditions + Privacy Policy Acknowledgement (Clickable)
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "Terms & Conditions" else "नियम व शर्तें",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SadhakThemeColors.sacredPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.clickable { showPrivacyPolicy = true }
                )
                Text(
                    text = "  •  ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SadhakThemeColors.sacredTextSecondary,
                        fontSize = 11.sp
                    )
                )
                Text(
                    text = if (isEng) "Privacy Policy" else "गोपनीयता नीति",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SadhakThemeColors.sacredPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    ),
                    modifier = Modifier.clickable { showPrivacyPolicy = true }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Welcome Transition Dialog upon successful login
        if (showWelcomeGreeting) {
            androidx.compose.ui.window.Dialog(
                onDismissRequest = {
                    showWelcomeGreeting = false
                    viewModel.setScreen("Home")
                },
                properties = androidx.compose.ui.window.DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SadhakThemeColors.sacredBackground),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, SadhakThemeColors.sacredGold),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .testTag("welcome_greeting_dialog")
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GoldenLotusMedallion(modifier = Modifier.size(60.dp))
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = if (isEng) "Jai Shree Mahakaal 🕉" else "जय श्री महाकाल 🕉",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SadhakThemeColors.sacredSecondary,
                                fontSize = 22.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isEng) "Welcome, Sadhak" else "सुस्वागतम्, साधक",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = SadhakThemeColors.sacredPrimary,
                                fontSize = 17.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = if (isEng) "May your spiritual journey be blessed with peace, devotion, and divine grace." else "आपकी साधना और आध्यात्मिक यात्रा सुख, शांति और भगवत्कृपा से परिपूर्ण हो।",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SadhakThemeColors.sacredTextSecondary,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                showWelcomeGreeting = false
                                viewModel.setScreen("Home")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("btn_enter_sanctuary")
                        ) {
                            Text(
                                text = if (isEng) "Enter Sanctuary" else "प्रवेश करें",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Forgot Password Dialog
        if (showForgotPasswordDialog) {
            AlertDialog(
                onDismissRequest = { showForgotPasswordDialog = false },
                title = {
                    Text(
                        text = if (isEng) "Reset Devotee Password" else "भक्त पासवर्ड रीसेट करें",
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredPrimary
                    )
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = if (isEng)
                                "Enter your registered email to get a link to set a new password. Signed in with Google? Use this to add a password too."
                            else
                                "पासवर्ड रीसेट निर्देश प्राप्त करने के लिए अपना पंजीकृत ईमेल पता दर्ज करें।",
                            fontSize = 13.sp,
                            color = SadhakThemeColors.sacredTextPrimary
                        )
                        OutlinedTextField(
                            value = resetEmailInput,
                            onValueChange = { resetEmailInput = it },
                            label = { Text(if (isEng) "Email Address" else "ईमेल पता") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = SadhakThemeColors.sacredPrimary,
                                unfocusedBorderColor = SadhakThemeColors.sacredGold.copy(alpha = 0.5f)
                            )
                        )
                        if (resetStatusText.isNotEmpty()) {
                            Text(
                                text = resetStatusText,
                                color = if (resetSuccess) SageGreen else SadhakThemeColors.error,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            resetStatusText = ""
                            if (resetEmailInput.isBlank()) {
                                resetStatusText = if (isEng) "Please enter your email" else "कृपया अपना ईमेल दर्ज करें"
                                return@Button
                            }
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(resetEmailInput.trim()).matches()) {
                                resetStatusText = if (isEng) "Please enter a valid email" else "कृपया एक मान्य ईमेल दर्ज करें"
                                return@Button
                            }
                            isResetSending = true
                            viewModel.sendPasswordReset(resetEmailInput.trim()) { success, msg ->
                                isResetSending = false
                                resetSuccess = success
                                resetStatusText = msg
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredPrimary),
                        enabled = !isResetSending
                    ) {
                        Text(if (isResetSending) (if (isEng) "Sending..." else "भेज रहे हैं...") else (if (isEng) "Send Reset Email" else "रीसेट ईमेल भेजें"), color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showForgotPasswordDialog = false }) {
                        Text(if (isEng) "Cancel" else "रद्द करें", color = SadhakThemeColors.sacredSecondary)
                    }
                }
            )
        }
    } else {
        // ACCOUNT PAGE (Logged In)
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val sadhanaProfile by viewModel.sadhanaProfile.collectAsState()
                    val sadhanaBadges by viewModel.sadhanaBadges.collectAsState()
                    val rankLevel = com.example.data.SadhanaRankCalculator.getLevelForScore(sadhanaProfile.totalScore)
                    val rankTitle = if (isEng) rankLevel.titleEn else rankLevel.titleHi
                    val unlockedBadgesCount = sadhanaBadges.count { it.unlocked }

                    // Integrated Profile Avatar + Spiritual Rank Badge (A3 & A4)
                    Box(
                        contentAlignment = Alignment.BottomCenter,
                        modifier = Modifier.padding(bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(bottom = 12.dp)
                        ) {
                            if (userProfileImageUri.isNotBlank()) {
                                coil.compose.AsyncImage(
                                    model = userProfileImageUri,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .border(2.5.dp, SadhakThemeColors.sacredGold, CircleShape),
                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                )
                            } else {
                                val avatarColor = when (userAvatar) {
                                    4 -> SadhakThemeColors.sacredPrimary
                                    3 -> Terracotta
                                    2 -> SageGreen
                                    else -> SadhakThemeColors.sacredSecondary
                                }
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(CircleShape)
                                        .background(avatarColor)
                                        .border(2.5.dp, SadhakThemeColors.sacredGold, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isAdmin) "👑" else "🕉",
                                        fontSize = 44.sp
                                    )
                                }
                            }
                        }

                        // Overlapping Spiritual Rank Badge Badge
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SadhakThemeColors.sacredSurface,
                            border = BorderStroke(1.dp, SadhakThemeColors.sacredGold),
                            shadowElevation = 3.dp,
                            modifier = Modifier.testTag("spiritual_rank_avatar_badge")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = rankLevel.icon, fontSize = 14.sp)
                                Text(
                                    text = rankTitle,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SadhakThemeColors.sacredSecondary
                                    )
                                )
                            }
                        }
                    }

                    // Secondary rank label removed for cleaner avatar badge integration

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

                    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

                    Spacer(modifier = Modifier.height(4.dp))

                    // A5: Compact Profile Statistics Row (Rank • Merit • Streak • Badges)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SadhakThemeColors.sacredBackground,
                        border = BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAllBadgesDialog = true }
                            .testTag("compact_sadhana_stats_bar")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Rank
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = rankLevel.icon, fontSize = 13.sp)
                                Text(
                                    text = rankTitle,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = SadhakThemeColors.sacredSecondary
                                )
                            }

                            Text(text = "•", color = SadhakThemeColors.sacredGold, fontSize = 11.sp)

                            // Merit
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = "🪔", fontSize = 12.sp)
                                Text(
                                    text = "${sadhanaProfile.totalScore} ${if (isEng) "Merit" else "मेरिट"}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = SadhakThemeColors.sacredTextPrimary
                                )
                            }

                            Text(text = "•", color = SadhakThemeColors.sacredGold, fontSize = 11.sp)

                            // Streak
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = "🔥", fontSize = 12.sp)
                                Text(
                                    text = "${sadhanaProfile.currentStreak} ${if (isEng) "Days" else "दिन"}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = SadhakThemeColors.sacredPrimary
                                )
                            }

                            Text(text = "•", color = SadhakThemeColors.sacredGold, fontSize = 11.sp)

                            // Badges
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = "🏅", fontSize = 12.sp)
                                Text(
                                    text = "$unlockedBadgesCount ${if (isEng) "Badges" else "बैज"}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = SadhakThemeColors.sacredSecondary
                                )
                            }
                        }
                    }

                    // A6: Compact Sacred Achievements Section
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = SadhakThemeColors.sacredBackground,
                        border = BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("compact_sacred_achievements_card")
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(text = "🏅", fontSize = 15.sp)
                                    Text(
                                        text = if (isEng) "Sacred Achievements" else "पावन उपलब्धियां",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SadhakThemeColors.sacredSecondary
                                        )
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = SadhakThemeColors.sacredGold.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "$unlockedBadgesCount / ${sadhanaBadges.size}",
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = SadhakThemeColors.sacredSecondary
                                        )
                                    )
                                }
                            }

                            val unlockedList = sadhanaBadges.filter { it.unlocked }
                            if (unlockedList.isNotEmpty()) {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    items(unlockedList.take(5)) { badge ->
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = SadhakThemeColors.sacredSurface,
                                            border = BorderStroke(1.dp, SadhakThemeColors.sacredGold.copy(alpha = 0.4f))
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                                            ) {
                                                Text(text = badge.icon, fontSize = 13.sp)
                                                Text(
                                                    text = if (isEng) badge.title else badge.titleHi,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = SadhakThemeColors.sacredTextPrimary
                                                )
                                            }
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = if (isEng) "Begin daily Sadhana to unlock sacred badges." else "पावन बैज अनलॉक करने के लिए नित्य साधना आरंभ करें।",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SadhakThemeColors.sacredTextSecondary,
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = { showAllBadgesDialog = true },
                                    modifier = Modifier.testTag("btn_view_all_badges"),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = if (isEng) "View All Badges →" else "सभी बैज देखें →",
                                        fontWeight = FontWeight.Bold,
                                        color = SadhakThemeColors.sacredPrimary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    
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

                    SettingActionTile(
                        title = if (isEng) "Guided App Tour & Features" else "ऐप परिचयात्मक मार्गदर्शिका",
                        icon = Icons.Default.AutoAwesome,
                        onClick = { showOnboardingTour = true }
                    )

                    SettingActionTile(
                        title = if (isEng) "Privacy Policy & Data Safety" else "गोपनीयता नीति और डेटा सुरक्षा",
                        icon = Icons.Default.Article,
                        onClick = { showPrivacyPolicy = true }
                    )
                    
                    if (isAdmin) {
                        SettingActionTile(
                            title = if (isEng) "Admin Control Panel" else "एडमिन कंट्रोल पैनल",
                            icon = Icons.Default.AdminPanelSettings,
                            onClick = onNavigateAdmin
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.logoutUser() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (isEng) "Logout from Temple" else "मंदिर से प्रस्थान करें", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { showDeleteAccountDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (isEng) "Delete Devotee Account" else "खाता हटाएं", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }

    if (showPrivacyPolicy) {
        AlertDialog(
            onDismissRequest = { showPrivacyPolicy = false },
            title = {
                Text(
                    text = if (isEng) "Privacy Policy & Data Safety" else "गोपनीयता नीति और डेटा सुरक्षा",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isEng) 
                            "Sanatan Sadhak respects your privacy and is dedicated to protecting your personal information."
                        else 
                            "सनातन साधक आपकी गोपनीयता का सम्मान करता है और आपकी व्यक्तिगत जानकारी की सुरक्षा के लिए प्रतिबद्ध है।",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isEng)
                            "• Location Data: Optional fine/coarse location is processed locally solely to calculate precise Vedic Panchang timings (Sunrise, Sunset, Rahu Kalam). Coordinates are never sold or tracked.\n\n" +
                            "• User Accounts: Name and Email are secured via Firebase Authentication and Firestore for account sync. You can delete your account at any time.\n\n" +
                            "• Sadhana & Japa: All Japa counts, journals, and favorites are stored locally in room database on your device.\n\n" +
                            "• AI Mitra: Prompts are processed strictly via secure API without sharing personal identity."
                        else
                            "• स्थान डेटा: पंचांग समय (सूर्योदय, सूर्यास्त, राहु काल) की गणना के लिए स्थान का उपयोग केवल स्थानीय रूप से किया जाता है।\n\n" +
                            "• उपयोगकर्ता खाता: नाम और ईमेल फायरबेस के माध्यम से सुरक्षित हैं। आप किसी भी समय अपना खाता हटा सकते हैं।\n\n" +
                            "• साधना और जप: सभी जप संख्या और डायरी आपके डिवाइस में स्थानीय रूप से संग्रहीत हैं।"
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyPolicy = false }) {
                    Text(if (isEng) "Close" else "बंद करें")
                }
            }
        )
    }

    if (showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog = false },
            title = {
                Text(
                    text = if (isEng) "Delete Account Confirmation" else "खाता हटाने की पुष्टि",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            },
            text = {
                Text(
                    text = if (isEng) 
                        "Are you sure you want to permanently delete your Sanatan Sadhak account? This action will remove your profile details and sign you out."
                    else 
                        "क्या आप अपना सनातन साधक खाता स्थायी रूप से हटाना चाहते हैं? यह क्रिया आपकी प्रोफ़ाइल को हटा देगी और आपको साइन आउट कर देगी।"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteAccountDialog = false
                        viewModel.deleteUserAccount { _ -> }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(if (isEng) "Yes, Delete Account" else "हाँ, खाता हटाएं", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    if (showOnboardingTour) {
        com.example.ui.screens.onboarding.DevoteeOnboardingDialog(
            isEnglish = isEng,
            onComplete = { showOnboardingTour = false }
        )
    }

    if (showAllBadgesDialog) {
        val sadhanaBadges by viewModel.sadhanaBadges.collectAsState()
        FullSadhanaBadgesDialog(
            badges = sadhanaBadges,
            isEnglish = isEng,
            onDismiss = { showAllBadgesDialog = false }
        )
    }
    
    ScrollToTopButton(
        visible = scrollState.value > 300,
        testTag = "scroll_to_top_profile",
        onClick = {
            coroutineScope.launch {
                scrollState.animateScrollTo(0)
            }
        },
        modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 80.dp, end = 24.dp)
    )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

    val photoCtx = androidx.compose.ui.platform.LocalContext.current
    val photoPickerLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                // Copy into app storage so the photo survives restarts (gallery links expire)
                val saved = com.example.data.ProfileImageStore.importPicked(photoCtx, uri, viewModel.userEmail.value)
                editImageUri = saved.ifEmpty { uri.toString() }
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

@OptIn(ExperimentalMaterial3Api::class)
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
                    val cardBorderColor = if (isSelected) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredDivider
                    val cardBg = if (isSelected) SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f) else SadhakThemeColors.sacredSurface

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
                    com.example.ui.components.SadhakSwitch(
                        checked = notificationsEnabled,
                        onCheckedChange = { viewModel.toggleNotifications() }
                    )
                }

                Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

                val soundEnabled by viewModel.soundEnabled.collectAsState()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isEng) "Sound & Audio Feedback" else "ध्वनि और ऑडियो प्रभाव",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                        Text(
                            text = if (isEng) "Enable or mute temple bell rings, chant audio, and celebration chimes." else "मंदिर की घंटी, मंत्र उच्चारण और उत्सव की ध्वनियों को सक्षम या मूक करें।",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    com.example.ui.components.SadhakSwitch(
                        checked = soundEnabled,
                        onCheckedChange = { viewModel.toggleSound() }
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
                            colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredSurface, contentColor = SadhakThemeColors.sacredTextPrimary),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredDivider)
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
                            colors = ButtonDefaults.buttonColors(containerColor = SadhakThemeColors.sacredSurface, contentColor = SadhakThemeColors.sacredTextPrimary),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.sacredDivider)
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
                    com.example.ui.components.SadhakSwitch(
                        checked = bgChantEnabled,
                        onCheckedChange = { viewModel.toggleBgChant() }
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

@Composable
fun FullSadhanaBadgesDialog(
    badges: List<com.example.data.SadhanaBadge>,
    isEnglish: Boolean,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("All") }
    val categories = listOf("All", "Streak", "Japa", "Meditation", "Knowledge", "Mantra", "Devotion")

    val filteredBadges = if (selectedTab == "All") badges else badges.filter { it.category.equals(selectedTab, ignoreCase = true) }
    val unlockedCount = badges.count { it.unlocked }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEnglish) "Sacred Badge Collection" else "पावन बैज संग्रह",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredSecondary
                    )
                )
                Text(
                    text = "$unlockedCount / ${badges.size}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SadhakThemeColors.sacredPrimary
                    )
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Category Filter Pills
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = selectedTab == category
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredSurface,
                            border = BorderStroke(1.dp, if (isSelected) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredGold.copy(alpha = 0.4f)),
                            modifier = Modifier.clickable { selectedTab = category }
                        ) {
                            Text(
                                text = category,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else SadhakThemeColors.sacredTextPrimary
                            )
                        }
                    }
                }

                Divider(color = SadhakThemeColors.sacredGold.copy(alpha = 0.3f))

                // Badges List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 360.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredBadges) { badge ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (badge.unlocked) SadhakThemeColors.sacredSurface else SadhakThemeColors.sacredBackground.copy(alpha = 0.6f),
                            border = BorderStroke(1.dp, if (badge.unlocked) SadhakThemeColors.sacredGold else SadhakThemeColors.sacredGold.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(if (badge.unlocked) SadhakThemeColors.sacredGold.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (badge.unlocked) {
                                        Text(text = badge.icon, fontSize = 20.sp)
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = "Locked",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = if (isEnglish) badge.title else badge.titleHi,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = if (badge.unlocked) SadhakThemeColors.sacredSecondary else Color.Gray
                                        )
                                        if (badge.unlocked) {
                                            Text(
                                                text = "✓",
                                                fontWeight = FontWeight.Bold,
                                                color = SadhakThemeColors.sacredPrimary,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                    Text(
                                        text = if (isEnglish) badge.description else badge.descriptionHi,
                                        fontSize = 11.sp,
                                        color = SadhakThemeColors.sacredTextSecondary,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = if (isEnglish) "Close" else "बंद करें",
                    color = SadhakThemeColors.sacredPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}
