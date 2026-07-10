package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.viewmodel.SadhakViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdminScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    var selectedTab by remember { mutableStateOf("Dashboard") }

    val tabs = listOf(
        Pair("Dashboard", if (isEng) "Dashboard" else "डैशबोर्ड"),
        Pair("Users", if (isEng) "Users" else "साधक"),
        Pair("Articles", if (isEng) "Articles" else "लेख"),
        Pair("Videos", if (isEng) "Videos" else "वीडियो"),
        Pair("Instagram", if (isEng) "Instagram" else "इंस्टाग्राम"),
        Pair("Community", if (isEng) "Community" else "समुदाय"),
        Pair("QuotesBanners", if (isEng) "Quotes & Banners" else "विचार और बैनर")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_screen_container")
    ) {
        SacredHeader(
            title = if (isEng) "Admin Control Panel" else "प्रशासक नियंत्रण कक्ष",
            subtitle = if (isEng) "Manage content, sync feeds, and moderate users" else "सामग्री प्रबंधित करें, फीड सिंक करें और उपयोगकर्ताओं को संचालित करें",
            showDiyas = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Tabs
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { tab ->
                val isSelected = selectedTab == tab.first
                Button(
                    onClick = { selectedTab = tab.first },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("admin_tab_${tab.first.lowercase()}")
                ) {
                    Text(text = tab.second, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                "Dashboard" -> AdminDashboardView(viewModel, isEng)
                "Users" -> AdminUsersView(viewModel, isEng)
                "Articles" -> AdminArticlesView(viewModel, isEng)
                "Videos" -> AdminVideosView(viewModel, isEng)
                "Instagram" -> AdminInstagramView(viewModel, isEng)
                "Community" -> AdminCommunityView(viewModel, isEng)
                "QuotesBanners" -> AdminQuotesBannersView(viewModel, isEng)
            }
        }
    }
}

@Composable
fun AdminDashboardView(viewModel: SadhakViewModel, isEng: Boolean) {
    val articles by viewModel.articles.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val comments by viewModel.allComments.collectAsState()
    val videos by viewModel.videos.collectAsState()
    val instagrams by viewModel.instagramPosts.collectAsState()
    val quotes by viewModel.thoughtQuotes.collectAsState()
    val announcements by viewModel.announcements.collectAsState()
    val users by viewModel.allUsers.collectAsState()
    val isPublicPosting by viewModel.isPublicPostingEnabled.collectAsState()
    val isSyncing by viewModel.isSyncingVideos.collectAsState()

    var syncResultMsg by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (isEng) "Dashboard Overview" else "डैशबोर्ड अवलोकन",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        // Stats Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(
                        title = if (isEng) "Users" else "साधक",
                        count = users.size.toString(),
                        icon = Icons.Default.People,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = if (isEng) "Articles" else "लेख",
                        count = articles.size.toString(),
                        icon = Icons.Default.Description,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(
                        title = if (isEng) "Videos" else "वीडियो",
                        count = videos.size.toString(),
                        icon = Icons.Default.VideoLibrary,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = if (isEng) "Instagram" else "इंस्टाग्राम",
                        count = instagrams.size.toString(),
                        icon = Icons.Default.PhotoCamera,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(
                        title = if (isEng) "Posts" else "पोस्ट",
                        count = posts.size.toString(),
                        icon = Icons.Default.Forum,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = if (isEng) "Comments" else "टिप्पणियाँ",
                        count = comments.size.toString(),
                        icon = Icons.Default.Comment,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard(
                        title = if (isEng) "Quotes" else "सुविचार",
                        count = quotes.size.toString(),
                        icon = Icons.Default.FormatQuote,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = if (isEng) "Banners" else "बैनर",
                        count = announcements.size.toString(),
                        icon = Icons.Default.Campaign,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Action controls
        item {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (isEng) "Global Controls" else "वैश्विक नियंत्रण",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Divider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isEng) "Enable Public Posting" else "सार्वजनिक पोस्टिंग सक्षम करें",
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isEng) "Allows all logged-in users to create posts" else "सभी लॉग-इन साधकों को पोस्ट बनाने की अनुमति देता है",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = isPublicPosting,
                            onCheckedChange = { viewModel.setPublicPosting(it) },
                            modifier = Modifier.testTag("global_posting_switch")
                        )
                    }
                }
            }
        }

        // Video Sync Section
        item {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = if (isEng) "YouTube Content Synchronization" else "यूट्यूब सामग्री तुल्यकालन",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (isEng) "Pull dynamic content from the divine channel (UCzHzPEdLJEvTSHn_ev3JYBA) and categorize new updates automatically." else "दिव्य चैनल (UCzHzPEdLJEvTSHn_ev3JYBA) से नई सामग्री लाएं और स्वतः श्रेणीबद्ध करें।",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Button(
                        onClick = {
                            syncResultMsg = null
                            viewModel.syncYoutubeVideos { added ->
                                syncResultMsg = if (added > 0) {
                                    if (isEng) "Success! Synchronized $added new divine video entries." else "सफलता! $added नए दिव्य वीडियो प्रविष्टियां सिंक की गईं।"
                                } else {
                                    if (isEng) "Synchronization complete. Content is up-to-date." else "सिंक्रनाइज़ेशन पूरा हुआ। सामग्री अद्यतित है।"
                                }
                            }
                        },
                        enabled = !isSyncing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("youtube_sync_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isEng) "Synchronizing Divine Feed..." else "सिंक हो रहा है...")
                        } else {
                            Icon(Icons.Default.Sync, contentDescription = "Sync")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (isEng) "Sync Now" else "अभी सिंक करें")
                        }
                    }

                    syncResultMsg?.let { msg ->
                        Text(
                            text = msg,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(title: String, count: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    SacredCard(
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = count, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun AdminUsersView(viewModel: SadhakViewModel, isEng: Boolean) {
    val users by viewModel.allUsers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredUsers = users.filter {
        it.fullName.contains(searchQuery, ignoreCase = true) || it.email.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(if (isEng) "Search devotees..." else "साधक खोजें...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredUsers) { user ->
                val isHardcodedAdmin = user.email == "kunalpareekusa@gmail.com" || user.email == "ashishpareekbhl@gmail.com"

                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Avatar
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.fullName.take(1).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = user.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(text = user.email, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                                SuggestionChip(
                                    onClick = {},
                                    label = { Text(user.role, fontSize = 9.sp) }
                                )
                                if (user.canPost) {
                                    SuggestionChip(
                                        onClick = {},
                                        label = { Text(if (isEng) "Can Post" else "पोस्ट अनुमत", fontSize = 9.sp) }
                                    )
                                }
                            }
                        }

                        if (!isHardcodedAdmin) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = if (isEng) "Can Post" else "पोस्टिंग",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Switch(
                                    checked = user.canPost,
                                    onCheckedChange = { viewModel.updateUserCanPost(user.email, it) },
                                    modifier = Modifier.testTag("user_post_switch_${user.email}")
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminArticlesView(viewModel: SadhakViewModel, isEng: Boolean) {
    val articles by viewModel.articles.collectAsState()

    var showFormDialog by remember { mutableStateOf(false) }
    var editingArticle by remember { mutableStateOf<KnowledgeArticle?>(null) }
    var deletingArticle by remember { mutableStateOf<KnowledgeArticle?>(null) }

    // Form fields
    var title by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Darshan") }
    var language by remember { mutableStateOf("en") }

    LaunchedEffect(editingArticle) {
        if (editingArticle != null) {
            title = editingArticle!!.title
            summary = editingArticle!!.summary
            content = editingArticle!!.content
            category = editingArticle!!.category
            language = editingArticle!!.language
        } else {
            title = ""
            summary = ""
            content = ""
            category = "Darshan"
            language = "en"
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEng) "Manage Articles" else "लेख प्रबंधित करें",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = {
                    editingArticle = null
                    showFormDialog = true
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isEng) "Add Article" else "लेख जोड़ें")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articles) { article ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = article.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp
                            )
                            IconButton(onClick = {
                                editingArticle = article
                                showFormDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { deletingArticle = article }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                        Text(text = article.summary, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                            SuggestionChip(onClick = {}, label = { Text(article.category, fontSize = 9.sp) })
                            SuggestionChip(onClick = {}, label = { Text(article.language.uppercase(), fontSize = 9.sp) })
                        }
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    deletingArticle?.let { article ->
        AlertDialog(
            onDismissRequest = { deletingArticle = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete \"${article.title}\"? This action cannot be undone." else "क्या आप निश्चित रूप से \"${article.title}\" को हमेशा के लिए हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteKnowledgeArticle(article.id)
                    deletingArticle = null
                }, modifier = Modifier.testTag("confirm_delete_article")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingArticle = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Add / Edit Dialog
    if (showFormDialog) {
        AlertDialog(
            onDismissRequest = { showFormDialog = false },
            title = { Text(if (editingArticle != null) (if (isEng) "Edit Article" else "लेख संपादित करें") else (if (isEng) "Add New Article" else "नया लेख जोड़ें")) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text(if (isEng) "Title" else "शीर्षक") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = summary,
                            onValueChange = { summary = it },
                            label = { Text(if (isEng) "Summary" else "सारांश") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text(if (isEng) "Content" else "विषय-वस्तु") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                    }
                    item {
                        Text(if (isEng) "Category" else "श्रेणी", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        val categories = listOf("Darshan", "Sadhana", "Spiritual", "Heritage")
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            categories.forEach { cat ->
                                FilterChip(
                                    selected = category == cat,
                                    onClick = { category = cat },
                                    label = { Text(cat, fontSize = 10.sp) }
                                )
                            }
                        }
                    }
                    item {
                        Text(if (isEng) "Language" else "भाषा", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            FilterChip(selected = language == "en", onClick = { language = "en" }, label = { Text("English") })
                            FilterChip(selected = language == "hi", onClick = { language = "hi" }, label = { Text("Hindi") })
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingArticle != null) {
                            viewModel.updateKnowledgeArticle(editingArticle!!.copy(
                                title = title,
                                summary = summary,
                                content = content,
                                category = category,
                                language = language
                            ))
                        } else {
                            viewModel.addKnowledgeArticle(title, summary, content, category, language)
                        }
                        showFormDialog = false
                    },
                    enabled = title.isNotBlank() && content.isNotBlank()
                ) {
                    Text(if (isEng) "Save" else "सुरक्षित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFormDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

@Composable
fun AdminVideosView(viewModel: SadhakViewModel, isEng: Boolean) {
    val videos by viewModel.videos.collectAsState()

    var showFormDialog by remember { mutableStateOf(false) }
    var editingVideo by remember { mutableStateOf<BhaktiVideo?>(null) }
    var deletingVideo by remember { mutableStateOf<BhaktiVideo?>(null) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Darshan & Puja") }
    var videoId by remember { mutableStateOf("") }

    LaunchedEffect(editingVideo) {
        if (editingVideo != null) {
            title = editingVideo!!.title
            description = editingVideo!!.description
            category = editingVideo!!.category
            videoId = editingVideo!!.videoId
        } else {
            title = ""
            description = ""
            category = "Darshan & Puja"
            videoId = ""
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEng) "Manage Videos" else "वीडियो प्रबंधित करें",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = {
                    editingVideo = null
                    showFormDialog = true
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isEng) "Add Video" else "वीडियो जोड़ें")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(videos) { video ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = video.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp
                            )
                            IconButton(onClick = {
                                editingVideo = video
                                showFormDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { deletingVideo = video }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                        Text(text = video.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                            SuggestionChip(onClick = {}, label = { Text(video.category, fontSize = 9.sp) })
                            SuggestionChip(onClick = {}, label = { Text("ID: " + video.videoId, fontSize = 9.sp) })
                        }
                    }
                }
            }
        }
    }

    // Delete confirmation
    deletingVideo?.let { video ->
        AlertDialog(
            onDismissRequest = { deletingVideo = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete \"${video.title}\"? This action cannot be undone." else "क्या आप निश्चित रूप से \"${video.title}\" को हमेशा के लिए हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteBhaktiVideo(video.id)
                    deletingVideo = null
                }, modifier = Modifier.testTag("confirm_delete_video")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingVideo = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Add / Edit Dialog
    if (showFormDialog) {
        AlertDialog(
            onDismissRequest = { showFormDialog = false },
            title = { Text(if (editingVideo != null) (if (isEng) "Edit Video" else "वीडियो संपादित करें") else (if (isEng) "Add Custom Video" else "नया वीडियो जोड़ें")) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text(if (isEng) "Title" else "शीर्षक") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text(if (isEng) "Description" else "विवरण") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = videoId,
                            onValueChange = { videoId = it },
                            label = { Text(if (isEng) "YouTube Video ID" else "यूट्यूब वीडियो ID") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Text(if (isEng) "Category" else "श्रेणी", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        val videoCategories = listOf("Darshan & Puja", "Aarti & Abhishek", "Temple Visits", "Devotional Stories")
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                            videoCategories.forEach { cat ->
                                FilterChip(
                                    selected = category == cat,
                                    onClick = { category = cat },
                                    label = { Text(cat, fontSize = 9.sp) }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingVideo != null) {
                            viewModel.updateBhaktiVideo(editingVideo!!.copy(
                                title = title,
                                description = description,
                                category = category,
                                videoId = videoId
                            ))
                        } else {
                            viewModel.addBhaktiVideo(title, description, category, videoId)
                        }
                        showFormDialog = false
                    },
                    enabled = title.isNotBlank() && videoId.isNotBlank()
                ) {
                    Text(if (isEng) "Save" else "सुरक्षित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFormDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun AdminInstagramView(viewModel: SadhakViewModel, isEng: Boolean) {
    val instagrams by viewModel.instagramPosts.collectAsState()
    val uriHandler = LocalUriHandler.current

    var showFormDialog by remember { mutableStateOf(false) }
    var editingPost by remember { mutableStateOf<InstagramPost?>(null) }
    var deletingPost by remember { mutableStateOf<InstagramPost?>(null) }

    var url by remember { mutableStateOf("") }
    var caption by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Devotional Thought") }
    var urlError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(editingPost) {
        if (editingPost != null) {
            url = editingPost!!.url
            caption = editingPost!!.caption
            category = editingPost!!.category
        } else {
            url = ""
            caption = ""
            category = "Devotional Thought"
        }
        urlError = null
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEng) "Manage Instagram Updates" else "इंस्टाग्राम अपडेट प्रबंधित करें",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Button(
                onClick = {
                    editingPost = null
                    showFormDialog = true
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isEng) "Publish Post" else "पोस्ट प्रकाशित करें")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(instagrams) { post ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = "IG", tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = post.caption,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = post.category,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            IconButton(onClick = {
                                editingPost = post
                                showFormDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                            }
                            IconButton(onClick = { deletingPost = post }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                        Text(
                            text = post.url,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable {
                                    try {
                                        uriHandler.openUri(post.url)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }

    // Delete Confirmation
    deletingPost?.let { post ->
        AlertDialog(
            onDismissRequest = { deletingPost = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete this Instagram post update? This action cannot be undone." else "क्या आप निश्चित रूप से इस इंस्टाग्राम अपडेट को हमेशा के लिए हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteInstagramPost(post.id)
                    deletingPost = null
                }, modifier = Modifier.testTag("confirm_delete_instagram")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingPost = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Add / Edit Dialog
    if (showFormDialog) {
        AlertDialog(
            onDismissRequest = { showFormDialog = false },
            title = { Text(if (editingPost != null) (if (isEng) "Edit Post" else "पोस्ट संपादित करें") else (if (isEng) "Publish Instagram Post" else "इंस्टाग्राम पोस्ट प्रकाशित करें")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = url,
                        onValueChange = { 
                            url = it 
                            urlError = null
                        },
                        label = { Text(if (isEng) "Instagram Post / Reel URL" else "इंस्टाग्राम पोस्ट / रील URL") },
                        isError = urlError != null,
                        supportingText = {
                            if (urlError != null) {
                                Text(urlError!!, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = caption,
                        onValueChange = { caption = it },
                        label = { Text(if (isEng) "Caption" else "कैप्शन") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(if (isEng) "Category" else "श्रेणी", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    val categories = listOf("Darshan", "Aarti", "Temple Visit", "Devotional Thought")
                    androidx.compose.foundation.layout.FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        categories.forEach { cat ->
                            FilterChip(
                                selected = category == cat,
                                onClick = { category = cat },
                                label = { Text(cat, fontSize = 10.sp) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val trimmedUrl = url.trim()
                        val isValid = (trimmedUrl.startsWith("https://") || trimmedUrl.startsWith("http://")) && 
                                      (trimmedUrl.contains("instagram.com/p/") || trimmedUrl.contains("instagram.com/reel/"))
                        if (!isValid) {
                            urlError = if (isEng) "Please enter a valid Instagram URL (with /p/ or /reel/)" else "कृपया एक वैध इंस्टाग्राम URL दर्ज करें (/p/ या /reel/ के साथ)"
                        } else {
                            urlError = null
                            if (editingPost != null) {
                                viewModel.updateInstagramPost(editingPost!!.copy(url = trimmedUrl, caption = caption, category = category))
                            } else {
                                viewModel.addInstagramPost(trimmedUrl, caption, category)
                            }
                            showFormDialog = false
                        }
                    },
                    enabled = url.isNotBlank() && caption.isNotBlank()
                ) {
                    Text(if (isEng) "Publish" else "प्रकाशित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFormDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

@Composable
fun AdminCommunityView(viewModel: SadhakViewModel, isEng: Boolean) {
    val posts by viewModel.posts.collectAsState()
    val comments by viewModel.allComments.collectAsState()

    var activeSubSection by remember { mutableStateOf("Posts") }
    var deletingPost by remember { mutableStateOf<CommunityPost?>(null) }
    var deletingComment by remember { mutableStateOf<PostComment?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { activeSubSection = "Posts" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeSubSection == "Posts") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (activeSubSection == "Posts") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEng) "Manage Posts" else "पोस्ट प्रबंधित करें")
            }
            Button(
                onClick = { activeSubSection = "Comments" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeSubSection == "Comments") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (activeSubSection == "Comments") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEng) "Manage Comments" else "टिप्पणियाँ प्रबंधित करें")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (activeSubSection == "Posts") {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(posts) { post ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "By " + post.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { deletingPost = post }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                            Text(text = post.content, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 4.dp)) {
                                Text("👍 ${post.upvotes}", fontSize = 11.sp)
                                Text("💬 ${post.commentsCount}", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments) { comment ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "By " + comment.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { deletingComment = comment }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                            Text(text = comment.content, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }

    // Delete post confirm
    deletingPost?.let { post ->
        AlertDialog(
            onDismissRequest = { deletingPost = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete this community post and all its comments? This action cannot be undone." else "क्या आप निश्चित रूप से इस समुदाय पोस्ट और इसकी सभी टिप्पणियों को हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePost(post.id)
                    deletingPost = null
                }, modifier = Modifier.testTag("confirm_delete_post")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingPost = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Delete comment confirm
    deletingComment?.let { comment ->
        AlertDialog(
            onDismissRequest = { deletingComment = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete this comment? This action cannot be undone." else "क्या आप निश्चित रूप से इस टिप्पणी को हमेशा के लिए हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePostComment(comment.id)
                    deletingComment = null
                }, modifier = Modifier.testTag("confirm_delete_comment")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingComment = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

@Composable
fun AdminQuotesBannersView(viewModel: SadhakViewModel, isEng: Boolean) {
    val quotes by viewModel.thoughtQuotes.collectAsState()
    val announcements by viewModel.announcements.collectAsState()

    var activeSubSection by remember { mutableStateOf("Quotes") }
    var showQuoteDialog by remember { mutableStateOf(false) }
    var editingQuote by remember { mutableStateOf<ThoughtQuote?>(null) }
    var deletingQuote by remember { mutableStateOf<ThoughtQuote?>(null) }

    var showAnnDialog by remember { mutableStateOf(false) }
    var editingAnn by remember { mutableStateOf<Announcement?>(null) }
    var deletingAnn by remember { mutableStateOf<Announcement?>(null) }

    // Forms fields
    var quoteEn by remember { mutableStateOf("") }
    var quoteHi by remember { mutableStateOf("") }

    var annEn by remember { mutableStateOf("") }
    var annHi by remember { mutableStateOf("") }
    var annVisible by remember { mutableStateOf(true) }

    LaunchedEffect(editingQuote) {
        if (editingQuote != null) {
            quoteEn = editingQuote!!.textEn
            quoteHi = editingQuote!!.textHi
        } else {
            quoteEn = ""
            quoteHi = ""
        }
    }

    LaunchedEffect(editingAnn) {
        if (editingAnn != null) {
            annEn = editingAnn!!.textEn
            annHi = editingAnn!!.textHi
            annVisible = editingAnn!!.isVisible
        } else {
            annEn = ""
            annHi = ""
            annVisible = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { activeSubSection = "Quotes" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeSubSection == "Quotes") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (activeSubSection == "Quotes") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEng) "Quotes" else "सुविचार")
            }
            Button(
                onClick = { activeSubSection = "Banners" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (activeSubSection == "Banners") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (activeSubSection == "Banners") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(if (isEng) "Announcements" else "घोषणा बैनर")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (activeSubSection == "Quotes") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(if (isEng) "Thought-of-the-Day" else "दैनिक सुविचार", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Button(onClick = { editingQuote = null; showQuoteDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isEng) "Add Quote" else "जोड़ें")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quotes) { quote ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Thought Quote", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { editingQuote = quote; showQuoteDialog = true }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = { deletingQuote = quote }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                            Text(text = "EN: " + quote.textEn, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "HI: " + quote.textHi, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(if (isEng) "Announcement Banners" else "घोषणा बैनर", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Button(onClick = { editingAnn = null; showAnnDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isEng) "Add Banner" else "जोड़ें")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(announcements) { ann ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (ann.isVisible) "ACTIVE BANNER" else "HIDDEN",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = if (ann.isVisible) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = { editingAnn = ann; showAnnDialog = true }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = { deletingAnn = ann }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                            Text(text = "EN: " + ann.textEn, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                            Text(text = "HI: " + ann.textHi, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }

    // Deletion dialogs
    deletingQuote?.let { quote ->
        AlertDialog(
            onDismissRequest = { deletingQuote = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete this thought-of-the-day quote? This action cannot be undone." else "क्या आप निश्चित रूप से इस दैनिक विचार को हमेशा के लिए हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteThoughtQuote(quote.id)
                    deletingQuote = null
                }, modifier = Modifier.testTag("confirm_delete_quote")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingQuote = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    deletingAnn?.let { ann ->
        AlertDialog(
            onDismissRequest = { deletingAnn = null },
            title = { Text(if (isEng) "Confirm Deletion" else "हटाने की पुष्टि करें") },
            text = { Text(if (isEng) "Are you sure you want to permanently delete this announcement banner? This action cannot be undone." else "क्या आप निश्चित रूप से इस घोषणा बैनर को हटाना चाहते हैं? यह क्रिया पूर्ववत नहीं की जा सकती।") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteAnnouncement(ann.id)
                    deletingAnn = null
                }, modifier = Modifier.testTag("confirm_delete_banner")) {
                    Text(if (isEng) "Delete" else "हटाएं", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingAnn = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    // Forms
    if (showQuoteDialog) {
        AlertDialog(
            onDismissRequest = { showQuoteDialog = false },
            title = { Text(if (editingQuote != null) (if (isEng) "Edit Quote" else "विचार संपादित करें") else (if (isEng) "Add Thought Quote" else "नया विचार जोड़ें")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = quoteEn,
                        onValueChange = { quoteEn = it },
                        label = { Text("Quote (English)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = quoteHi,
                        onValueChange = { quoteHi = it },
                        label = { Text("Quote (Hindi / हिंदी)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingQuote != null) {
                            viewModel.updateThoughtQuote(editingQuote!!.copy(textEn = quoteEn, textHi = quoteHi))
                        } else {
                            viewModel.addThoughtQuote(quoteEn, quoteHi)
                        }
                        showQuoteDialog = false
                    },
                    enabled = quoteEn.isNotBlank() && quoteHi.isNotBlank()
                ) {
                    Text(if (isEng) "Save" else "सुरक्षित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuoteDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }

    if (showAnnDialog) {
        AlertDialog(
            onDismissRequest = { showAnnDialog = false },
            title = { Text(if (editingAnn != null) (if (isEng) "Edit Announcement" else "घोषणा संपादित करें") else (if (isEng) "Add Announcement" else "नई घोषणा जोड़ें")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = annEn,
                        onValueChange = { annEn = it },
                        label = { Text("Banner Text (English)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = annHi,
                        onValueChange = { annHi = it },
                        label = { Text("Banner Text (Hindi / हिंदी)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isEng) "Is Visible" else "दृश्यमान है")
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(checked = annVisible, onCheckedChange = { annVisible = it })
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingAnn != null) {
                            viewModel.updateAnnouncement(editingAnn!!.copy(textEn = annEn, textHi = annHi, isVisible = annVisible))
                        } else {
                            viewModel.addAnnouncement(annEn, annHi, annVisible)
                        }
                        showAnnDialog = false
                    },
                    enabled = annEn.isNotBlank() && annHi.isNotBlank()
                ) {
                    Text(if (isEng) "Save" else "सुरक्षित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAnnDialog = false }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}
