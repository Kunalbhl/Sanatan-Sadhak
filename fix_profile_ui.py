import re

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "r") as f:
    content = f.read()

profile_screen_start = content.find("fun ProfileScreen(viewModel: SadhakViewModel) {")
profile_screen_end = content.find("// --- KNOWLEDGE / VEDA SCREEN ---", profile_screen_start)

# We will completely rewrite the ProfileScreen and add EditProfileScreen.
# Wait, it's safer to extract and replace the entire function.

new_profile_screens = """fun ProfileScreen(viewModel: SadhakViewModel, onNavigateAdmin: () -> Unit) {
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
        var nameInput by remember { mutableStateOf("") }
        var statusText by remember { mutableStateOf("") }
        val isLoading by viewModel.isLoading.collectAsState()

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
                    Text(
                        text = if (showSignUp) {
                            if (isEng) "Create Devotee Account" else "नया भक्त खाता बनाएं"
                        } else {
                            if (isEng) "Devotee Login" else "भक्त प्रवेश"
                        },
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
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
                    }

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
                        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                        singleLine = true
                    )

                    if (statusText.isNotEmpty()) {
                        Text(text = statusText, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            if (emailInput.isNotBlank() && passInput.isNotBlank()) {
                                if (showSignUp) {
                                    if (nameInput.isNotBlank()) {
                                        viewModel.signUp(emailInput, passInput, nameInput) { success, msg ->
                                            if (!success) statusText = msg
                                        }
                                    } else {
                                        statusText = if (isEng) "Name required" else "नाम आवश्यक है"
                                    }
                                } else {
                                    viewModel.login(emailInput, passInput) { success, msg ->
                                        if (!success) statusText = msg
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
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

                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )

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

                    Button(
                        onClick = { isEditingProfile = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = if (isEng) "Edit Profile Details" else "प्रोफ़ाइल विवरण संपादित करें")
                    }
                    
                    if (userRole == "SuperUser") {
                        Button(
                            onClick = onNavigateAdmin,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Terracotta)
                        ) {
                            Text(text = if (isEng) "Admin Control Panel" else "एडमिन कंट्रोल पैनल", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.logout() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (isEng) "Logout" else "लॉग आउट करें", color = Color.White)
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
"""

content = content[:profile_screen_start] + new_profile_screens + "\n\n" + content[profile_screen_end:]

with open("app/src/main/java/com/example/ui/screens/AllScreens.kt", "w") as f:
    f.write(content)
