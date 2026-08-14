package com.example.ui.screens
import com.example.SanatanSadhakLogo
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
    Box(modifier = Modifier.fillMaxSize()) {
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
            modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState),
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
                    Switch(
                        checked = soundEnabled,
                        onCheckedChange = { viewModel.toggleSound() },
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
