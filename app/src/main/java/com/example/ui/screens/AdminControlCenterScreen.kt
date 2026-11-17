package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.ui.theme.SadhakThemeColors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.components.*
import com.example.ui.designsystem.*
import com.example.ui.viewmodel.AdminViewModel
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.viewmodel.VideoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AdminControlCenterScreen(
    sadhakViewModel: SadhakViewModel,
    adminViewModel: AdminViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = com.example.ui.viewmodel.AppViewModelProvider.Factory),
    videoViewModel: VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = com.example.ui.viewmodel.AppViewModelProvider.Factory)
) {
    val isEng by sadhakViewModel.isEnglish.collectAsState()
    val adminEmail by sadhakViewModel.userEmail.collectAsState()
    val userRole by sadhakViewModel.userRole.collectAsState()
    val isAdmin by sadhakViewModel.isAdmin.collectAsState()
    val pendingConfirmation by adminViewModel.pendingConfirmation.collectAsState()

    if (!isAdmin) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .testTag("admin_access_denied"),
            contentAlignment = Alignment.Center
        ) {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Access Restricted",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = if (isEng) "Admin Access Required" else "प्रशासक अनुमति आवश्यक",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = if (isEng) 
                            "This sacred control center is restricted to authorized administrators only. Current role: $userRole." 
                        else 
                            "यह नियंत्रण केंद्र केवल अधिकृत प्रशासकों के लिए आरक्षित है। वर्तमान भूमिका: $userRole।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
        return
    }

    var activeTab by remember { mutableStateOf("Overview") }

    val navSections = listOf(
        Pair("Overview", if (isEng) "Overview" else "अवलोकन"),
        Pair("Content", if (isEng) "Content Management" else "सामग्री प्रबंधन"),
        Pair("Deities", if (isEng) "Deities" else "देवता कर-तत्व"),
        Pair("Panchang", if (isEng) "Panchang Admin" else "पंचांग प्रशासन"),
        Pair("YouTube", if (isEng) "YouTube & Media" else "यूट्यूब व मीडिया"),
        Pair("Notifications", if (isEng) "Notifications" else "सूचना केंद्र"),
        Pair("Diagnostics", if (isEng) "Observability & Health" else "निरीक्षण व स्वास्थ्य"),
        Pair("Compliance", if (isEng) "Play Compliance" else "अनुपालन व नीतियां"),
        Pair("Community", if (isEng) "Community" else "समुदाय नियंत्रण"),
        Pair("Users", if (isEng) "User Management" else "उपयोगकर्ता प्रबंधन"),
        Pair("Settings", if (isEng) "App Settings" else "ऐप सेटिंग्स"),
        Pair("AuditLog", if (isEng) "Audit Log" else "अंकेक्षण लॉग")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_control_center_container")
    ) {
        // --- HEADER SECTION ---
        SacredHeader(
            title = if (isEng) "Sanatan Sadhak Admin" else "सनातन साधक प्रशासक",
            subtitle = if (isEng) "Control Center" else "नियंत्रण केंद्र",
            showDiyas = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // --- SYSTEM STATUS BANNER ---
        SacredCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(modifier = Modifier.size(8.dp).background(SadhakThemeColors.success, CircleShape))
                        Text(
                            text = "${if (isEng) "Admin" else "प्रशासक"}: ${if (adminEmail.isBlank()) "Master Administrator" else adminEmail}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Text(
                        text = "${if (isEng) "System Status" else "प्रणाली स्थिति"}: Operational • ${if (isEng) "Local Room Engine" else "स्थानीय रूम इंजन"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(
                    text = SimpleDateFormat("HH:mm, dd MMM", Locale.getDefault()).format(Date()),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- NAVIGATION TABS ---
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(navSections) { section ->
                val isSelected = activeTab == section.first
                FilterChip(
                    selected = isSelected,
                    onClick = { activeTab = section.first },
                    label = { Text(section.second, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("admin_nav_${section.first.lowercase()}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- MAIN CONTROL CENTER BODY ---
        Box(modifier = Modifier.weight(1f)) {
            when (activeTab) {
                "Overview" -> AdminOverviewTab(adminViewModel, sadhakViewModel, isEng, videoViewModel, onOpen = { activeTab = it })
                "Content" -> AdminContentManagementTab(adminViewModel, isEng, adminEmail)
                "Deities" -> AdminDeitiesTab(adminViewModel, isEng, adminEmail)
                "Panchang" -> AdminPanchangTab(adminViewModel, isEng, adminEmail)
                "YouTube" -> AdminYoutubeTab(adminViewModel, videoViewModel, isEng, adminEmail)
                "Notifications" -> AdminNotificationsTab(adminViewModel, isEng, adminEmail)
                "Diagnostics" -> AdminDiagnosticsTab(adminViewModel, isEng, adminEmail)
                "Compliance" -> AdminComplianceTab(adminViewModel, isEng, adminEmail)
                "Community" -> AdminCommunityTab(adminViewModel, isEng, adminEmail)
                "Users" -> AdminUsersTab(adminViewModel, isEng, adminEmail)
                "Settings" -> AdminSettingsTab(adminViewModel, isEng, adminEmail)
                "AuditLog" -> AdminAuditLogTab(adminViewModel, isEng)
            }
        }
    }

    // --- ACTION CONFIRMATION DIALOG ---
    pendingConfirmation?.let { conf ->
        AlertDialog(
            onDismissRequest = { adminViewModel.dismissConfirmation() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = MaterialTheme.colorScheme.error)
                    Text(conf.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(conf.message)
                    conf.warningMessage?.let { warn ->
                        Text(warn, fontSize = 12.sp, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Medium)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { adminViewModel.executePendingConfirmation() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(if (isEng) "Confirm Action" else "पुष्टि करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { adminViewModel.dismissConfirmation() }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

// ==========================================
// 1. OVERVIEW TAB
// ==========================================
@Composable
fun AdminOverviewTab(
    adminViewModel: AdminViewModel,
    sadhakViewModel: SadhakViewModel,
    isEng: Boolean,
    videoViewModel: VideoViewModel,
    onOpen: (String) -> Unit = {}
) {
    val articles by adminViewModel.articles.collectAsState()
    val mantras by adminViewModel.mantras.collectAsState()
    val poojas by adminViewModel.poojaVidhis.collectAsState()
    val festivals by adminViewModel.festivals.collectAsState()
    val videos by adminViewModel.videos.collectAsState()
    val posts by adminViewModel.posts.collectAsState()
    val notifications by adminViewModel.adminNotifications.collectAsState()
    val users by adminViewModel.users.collectAsState()

    val verifiedCount = articles.count { it.verificationStatus == "VERIFIED_SOURCE" }
    val underReviewCount = articles.count { it.verificationStatus == "EDITOR_REVIEW_REQUIRED" }
    val unverifiedCount = articles.count { it.verificationStatus == "UNVERIFIED" || it.verificationStatus.isBlank() }
    val missingSourceCount = articles.count { it.sourceName.isBlank() }
    val missingReferenceCount = articles.count { it.textReference.isBlank() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            SadhakCard(backgroundColor = MaterialTheme.colorScheme.errorContainer) {
                Row(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.Warning, contentDescription = "Security Warning", tint = MaterialTheme.colorScheme.onErrorContainer)
                    Column {
                        Text(
                            text = if (isEng) "Security Warning" else "सुरक्षा चेतावनी",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isEng) "Admin actions are securely enforced via Firebase Rules." else "व्यवस्थापक कार्रवाई फ़ायरबेस नियमों द्वारा सुरक्षित रूप से लागू की जाती है।",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = if (isEng) "Data Quality Dashboard" else "डेटा गुणवत्ता डैशबोर्ड",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Total Articles" else "कुल लेख", articles.size.toString(), Icons.Default.Description, Modifier.weight(1f), onClick = { onOpen("Content") })
                    StatBox(if (isEng) "Verified" else "सत्यापित", verifiedCount.toString(), Icons.Default.Verified, Modifier.weight(1f), onClick = { onOpen("Content") })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Under Review" else "समीक्षाधीन", underReviewCount.toString(), Icons.Default.RateReview, Modifier.weight(1f), onClick = { onOpen("Content") })
                    StatBox(if (isEng) "Unverified" else "असत्यापित", unverifiedCount.toString(), Icons.Default.Info, Modifier.weight(1f), onClick = { onOpen("Content") })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Missing Source" else "स्रोत गायब", missingSourceCount.toString(), Icons.Default.Warning, Modifier.weight(1f), onClick = { onOpen("Content") })
                    StatBox(if (isEng) "Missing Ref" else "संदर्भ गायब", missingReferenceCount.toString(), Icons.Default.ReportProblem, Modifier.weight(1f), onClick = { onOpen("Content") })
                }
            }
        }

        item {
            Text(
                text = if (isEng) "System Statistics Overview" else "प्रणाली सांख्यिकी अवलोकन",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Mantras" else "मंत्र", mantras.size.toString(), Icons.Default.SelfImprovement, Modifier.weight(1f), onClick = { onOpen("Content") })
                    StatBox(if (isEng) "Pooja Vidhi" else "पूजा विधि", poojas.size.toString(), Icons.Default.LibraryBooks, Modifier.weight(1f), onClick = { onOpen("Content") })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Festivals" else "त्यौहार व व्रत", festivals.size.toString(), Icons.Default.Event, Modifier.weight(1f), onClick = { onOpen("Panchang") })
                    StatBox(if (isEng) "Bhakti Videos" else "भक्ति वीडियो", videos.size.toString(), Icons.Default.VideoLibrary, Modifier.weight(1f), onClick = { onOpen("YouTube") })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Community Posts" else "समुदाय पोस्ट", posts.size.toString(), Icons.Default.Forum, Modifier.weight(1f), onClick = { onOpen("Community") })
                    StatBox(if (isEng) "Notifications" else "सूचनाएं", notifications.size.toString(), Icons.Default.Notifications, Modifier.weight(1f), onClick = { onOpen("Notifications") })
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatBox(if (isEng) "Total Users" else "कुल उपयोगकर्ता", users.size.toString(), Icons.Default.People, Modifier.weight(1f), onClick = { onOpen("Users") })
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StatBox(title: String, count: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    SacredCard(
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = if (onClick != null) modifier.clickable { onClick() } else modifier
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(text = count, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                Text(text = title, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (onClick != null) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ==========================================
// 2. CONTENT MANAGEMENT TAB
// ==========================================
@Composable
fun AdminContentManagementTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val articles by adminViewModel.articles.collectAsState()
    val searchQuery by adminViewModel.searchQuery.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var editingArticle by remember { mutableStateOf<KnowledgeArticle?>(null) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isEng) "Knowledge & Scripture Management" else "ज्ञान व शास्त्र प्रबंधन",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Button(
                onClick = { editingArticle = null; showAddDialog = true },
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isEng) "Add Content" else "सामग्री जोड़ें", fontSize = 12.sp)
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { adminViewModel.setSearchQuery(it) },
            placeholder = { Text(if (isEng) "Search articles by title/category..." else "शीर्षक/श्रेणी से खोजें...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        val filteredArticles = articles.filter {
            searchQuery.isBlank() || it.title.contains(searchQuery, true) || it.category.contains(searchQuery, true)
        }

        if (filteredArticles.isEmpty()) {
            SadhakEmptyState(
                title = if (isEng) "No articles found" else "कोई लेख नहीं मिला",
                description = if (isEng) "No knowledge articles match your search or filter criteria." else "आपकी खोज के अनुसार कोई लेख उपलब्ध नहीं है।"
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(filteredArticles) { art ->
                    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(art.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Surface(
                                    color = if (art.verificationStatus == "VERIFIED_SOURCE") SadhakThemeColors.success.copy(alpha = 0.15f) else SadhakThemeColors.warning.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = art.verificationStatus,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = if (art.verificationStatus == "VERIFIED_SOURCE") SadhakThemeColors.success else SadhakThemeColors.warning
                                    )
                                }
                            }
                            Text(text = "Category: ${art.category} • Source: ${art.sourceName}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = { editingArticle = art; showAddDialog = true }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Edit" else "संपादित करें", fontSize = 12.sp)
                                }
                                TextButton(onClick = { adminViewModel.archiveKnowledgeArticle(art, adminEmail) }) {
                                    Icon(Icons.Default.Archive, contentDescription = "Archive", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Archive" else "संग्रहित करें", fontSize = 12.sp)
                                }
                                TextButton(onClick = { adminViewModel.deleteKnowledgeArticle(art, adminEmail) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Delete" else "हटाएं", fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        KnowledgeEditDialog(
            article = editingArticle,
            isEng = isEng,
            onDismiss = { showAddDialog = false; errorMsg = null },
            onSave = { title, category, summary, content, orig, trans, src, ref, status ->
                adminViewModel.saveKnowledgeArticle(
                    id = editingArticle?.id ?: 0,
                    title = title,
                    category = category,
                    summary = summary,
                    content = content,
                    originalText = orig,
                    translation = trans,
                    sourceName = src,
                    textReference = ref,
                    verificationStatus = status,
                    currentAdminEmail = adminEmail,
                    onSuccess = { showAddDialog = false; errorMsg = null },
                    onError = { err -> errorMsg = err }
                )
            },
            errorMsg = errorMsg
        )
    }
}

@Composable
fun KnowledgeEditDialog(
    article: KnowledgeArticle?,
    isEng: Boolean,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, String, String, String, String) -> Unit,
    errorMsg: String?
) {
    var title by remember { mutableStateOf(article?.title ?: "") }
    var category by remember { mutableStateOf(article?.category ?: "Vedas & Vedangas") }
    var summary by remember { mutableStateOf(article?.summary ?: "") }
    var content by remember { mutableStateOf(article?.content ?: "") }
    var originalText by remember { mutableStateOf(article?.originalText ?: "") }
    var translation by remember { mutableStateOf(article?.translation ?: "") }
    var sourceName by remember { mutableStateOf(article?.sourceName ?: "") }
    var textReference by remember { mutableStateOf(article?.textReference ?: "") }
    var verificationStatus by remember { mutableStateOf(article?.verificationStatus ?: "VERIFIED_SOURCE") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (article == null) (if (isEng) "Add Knowledge Article" else "नया ज्ञान लेख") else (if (isEng) "Edit Article" else "लेख संपादित करें")) },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                errorMsg?.let { err ->
                    item {
                        Text(err, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                item { OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Summary") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Content") }, modifier = Modifier.fillMaxWidth(), minLines = 3) }
                item { OutlinedTextField(value = originalText, onValueChange = { originalText = it }, label = { Text("Original Text / Sanskrit Verse") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = translation, onValueChange = { translation = it }, label = { Text("Translation") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = sourceName, onValueChange = { sourceName = it }, label = { Text("Source Name (e.g. Gita Press)") }, modifier = Modifier.fillMaxWidth()) }
                item { OutlinedTextField(value = textReference, onValueChange = { textReference = it }, label = { Text("Text Reference (e.g. Rigveda 1.1.1)") }, modifier = Modifier.fillMaxWidth()) }
                item {
                    Text("Verification Status:", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf("VERIFIED_SOURCE", "TRADITIONAL_REFERENCE", "EDITOR_REVIEW_REQUIRED", "UNVERIFIED").forEach { status ->
                            FilterChip(
                                selected = verificationStatus == status,
                                onClick = { verificationStatus = status },
                                label = { Text(status, fontSize = 10.sp) }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(title, category, summary, content, originalText, translation, sourceName, textReference, verificationStatus) }) {
                Text(if (isEng) "Save Article" else "सुरक्षित करें")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(if (isEng) "Cancel" else "रद्द करें") }
        }
    )
}

// ==========================================
// 3. DEITIES TAB
// ==========================================
@Composable
fun AdminDeitiesTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val deities by adminViewModel.deities.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingDeity by remember { mutableStateOf<DeityItem?>(null) }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isEng) "Centralized Deity Taxonomy" else "देवता वर्गीकरण व कर-तत्व",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Button(onClick = { editingDeity = null; showDialog = true }, shape = RoundedCornerShape(12.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isEng) "Add Deity" else "देवता जोड़ें", fontSize = 12.sp)
                    }
                }

                }
            }
            items(deities) { deity ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("${deity.nameEn} (${deity.nameHi})", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Sanskrit: ${deity.nameSanskrit.ifBlank { "—" }} • Sort: ${deity.sortOrder}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { editingDeity = deity; showDialog = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        var nameEn by remember { mutableStateOf(editingDeity?.nameEn ?: "") }
        var nameHi by remember { mutableStateOf(editingDeity?.nameHi ?: "") }
        var nameSk by remember { mutableStateOf(editingDeity?.nameSanskrit ?: "") }
        var desc by remember { mutableStateOf(editingDeity?.description ?: "") }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (editingDeity == null) "Add New Deity" else "Edit Deity") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = nameEn, onValueChange = { nameEn = it }, label = { Text("English Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = nameHi, onValueChange = { nameHi = it }, label = { Text("Hindi Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = nameSk, onValueChange = { nameSk = it }, label = { Text("Sanskrit Name") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = {
                Button(onClick = {
                    val item = DeityItem(
                        id = editingDeity?.id ?: nameEn.lowercase().trim(),
                        nameEn = nameEn,
                        nameHi = nameHi,
                        nameSanskrit = nameSk,
                        description = desc
                    )
                    adminViewModel.addOrUpdateDeity(item, editingDeity != null, adminEmail)
                    showDialog = false
                }) { Text("Save Deity") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } }
        )
    }
}

// ==========================================
// 4. PANCHANG ADMIN TAB
// ==========================================
@Composable
fun AdminPanchangTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val config by adminViewModel.panchangConfig.collectAsState()

    var primarySource by remember { mutableStateOf(config.defaultPanchangSource) }
    var fallbackBehavior by remember { mutableStateOf(config.fallbackBehavior) }
    var displayTransparency by remember { mutableStateOf(config.displaySourceInformation) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = if (isEng) "Panchang & Astronomical Calculation Config" else "पंचांग व खगोलीय गणना प्रशासन",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        item {
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Primary Data Engine Source", fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(value = primarySource, onValueChange = { primarySource = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Primary Engine Source") })

                    Text("Fallback Engine Strategy", fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(value = fallbackBehavior, onValueChange = { fallbackBehavior = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Fallback Mode") })

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Display Source Transparency Badges")
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(checked = displayTransparency, onCheckedChange = { displayTransparency = it })
                    }

                    Button(
                        onClick = {
                            val newConfig = config.copy(
                                defaultPanchangSource = primarySource,
                                fallbackBehavior = fallbackBehavior,
                                displaySourceInformation = displayTransparency
                            )
                            adminViewModel.savePanchangConfig(newConfig, adminEmail)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (isEng) "Save Panchang Settings" else "पंचांग सेटिंग्स सुरक्षित करें")
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. YOUTUBE & MEDIA TAB
// ==========================================
@Composable
fun AdminYoutubeTab(adminViewModel: AdminViewModel, videoViewModel: VideoViewModel, isEng: Boolean, adminEmail: String) {
    val ytConfig by adminViewModel.youtubeConfig.collectAsState()
    val videos by adminViewModel.videos.collectAsState()
    val isSyncing by videoViewModel.isSyncingVideos.collectAsState()
    val hiddenIds by videoViewModel.hiddenVideoIds.collectAsState()
    var resultMsg by remember { mutableStateOf<String?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingVideo by remember { mutableStateOf<BhaktiVideo?>(null) }
    var filterCategory by remember { mutableStateOf("All") }
    var searchKeyword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val channelVideosCount = videos.count { !it.isCustom }
    val curatedVideosCount = videos.count { it.isCustom }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        // Channel Sync Card
        // Search & Category Filter
        val filteredVideos = videos.filter { v ->
            val matchSearch = searchKeyword.isBlank() || v.title.contains(searchKeyword, true) || v.videoId.contains(searchKeyword, true)
            val matchCat = when (filterCategory) {
                "All" -> true
                "Channel" -> !v.isCustom
                "Curated" -> v.isCustom
                "Hidden" -> v.videoId in hiddenIds
                else -> v.category.equals(filterCategory, ignoreCase = true)
            }
            matchSearch && matchCat
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEng) "YouTube Content Platform" else "यूट्यूब व मीडिया प्रशासन",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Button(
                        onClick = { editingVideo = null; showAddDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Video")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isEng) "Curate Video" else "वीडियो जोड़ें", fontSize = 12.sp)
                    }
                }

                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Column {
                                Text(if (isEng) "Official Channel: @SanatanSadhakk" else "आधिकारिक चैनल: @SanatanSadhak", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("Total: ${videos.size} (Channel: $channelVideosCount • Curated: $curatedVideosCount)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Button(
                                onClick = {
                                    videoViewModel.syncYoutubeVideos { count ->
                                        resultMsg = if (count > 0) "Synchronized $count new videos!" else "Channel content is up to date."
                                    }
                                },
                                enabled = !isSyncing,
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                if (isSyncing) CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                                else {
                                    Icon(Icons.Default.Refresh, contentDescription = "Sync", modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isEng) "Sync Channel" else "सिंक करें", fontSize = 12.sp)
                                }
                            }
                        }
                        resultMsg?.let { Text(it, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                    }
                }

                AdminYoutubeChannelsCard(videoViewModel, isEng, adminEmail)

                OutlinedTextField(
                    value = searchKeyword,
                    onValueChange = { searchKeyword = it },
                    placeholder = { Text(if (isEng) "Search videos by title or ID..." else "शीर्षक या आईडी से खोजें...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    val filterOptions = listOf("All", "Channel", "Curated", "Hidden") + YoutubeUrlParser.SUPPORTED_CATEGORIES.take(6)
                    items(filterOptions) { cat ->
                        FilterChip(
                            selected = filterCategory == cat,
                            onClick = { filterCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) }
                        )
                    }
                }

                }
            }
            items(filteredVideos) { vid ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(vid.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                            Surface(
                                color = if (vid.isCustom) MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = if (vid.isCustom) "CURATED" else "CHANNEL",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (vid.isCustom) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text("Category: ${vid.category} • ID: ${vid.videoId}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (vid.description.isNotBlank()) {
                            Text(vid.description, fontSize = 11.sp, maxLines = 2)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = {
                                editingVideo = vid
                                showAddDialog = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isEng) "Edit" else "संपादित करें", fontSize = 11.sp)
                            }
                            val isHidden = vid.videoId in hiddenIds
                            TextButton(onClick = { videoViewModel.setVideoHidden(vid.videoId, !isHidden, adminEmail) }) {
                                Icon(if (isHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = if (isHidden) "Show" else "Hide", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isHidden) (if (isEng) "Show" else "दिखाएँ") else (if (isEng) "Hide" else "छिपाएँ"), fontSize = 11.sp)
                            }
                            TextButton(onClick = { adminViewModel.toggleVideoStatus(vid, adminEmail) }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Toggle", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (vid.isCustom) "Mark Standard" else "Mark Curated", fontSize = 11.sp)
                            }
                            TextButton(onClick = { adminViewModel.deleteCuratedVideo(vid, adminEmail) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isEng) "Delete" else "हटाएं", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var urlOrId by remember { mutableStateOf(editingVideo?.videoId ?: "") }
        var title by remember { mutableStateOf(editingVideo?.title ?: "") }
        var description by remember { mutableStateOf(editingVideo?.description ?: "") }
        var category by remember { mutableStateOf(editingVideo?.category ?: "Darshan & Puja") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false; errorMessage = null },
            title = { Text(if (editingVideo == null) (if (isEng) "Curate YouTube Video" else "यूट्यूब वीडियो जोड़ें") else (if (isEng) "Edit Video Details" else "वीडियो संपादित करें")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    errorMessage?.let { err ->
                        Text(err, color = MaterialTheme.colorScheme.error, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    if (editingVideo == null) {
                        OutlinedTextField(
                            value = urlOrId,
                            onValueChange = { urlOrId = it },
                            label = { Text("YouTube URL or 11-char Video ID") },
                            placeholder = { Text("e.g. https://youtu.be/... or ID") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Video Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description / Admin Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Text("Category:", style = MaterialTheme.typography.labelSmall)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(YoutubeUrlParser.SUPPORTED_CATEGORIES) { cat ->
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
                Button(onClick = {
                    if (editingVideo == null) {
                        adminViewModel.addCuratedVideo(
                            urlOrId = urlOrId,
                            title = title,
                            description = description,
                            category = category,
                            currentAdminEmail = adminEmail,
                            onSuccess = { showAddDialog = false; errorMessage = null },
                            onError = { err -> errorMessage = err }
                        )
                    } else {
                        adminViewModel.updateCuratedVideo(
                            id = editingVideo!!.id,
                            videoId = editingVideo!!.videoId,
                            title = title,
                            description = description,
                            category = category,
                            isCustom = editingVideo!!.isCustom,
                            currentAdminEmail = adminEmail,
                            onSuccess = { showAddDialog = false; errorMessage = null },
                            onError = { err -> errorMessage = err }
                        )
                    }
                }) {
                    Text(if (isEng) "Save Video" else "सुरक्षित करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false; errorMessage = null }) {
                    Text(if (isEng) "Cancel" else "रद्द करें")
                }
            }
        )
    }
}

// ==========================================
// OBSERVABILITY & DIAGNOSTICS TAB
// ==========================================
@Composable
fun AdminDiagnosticsTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val healthList by adminViewModel.systemHealth.collectAsState()
    val errorLogs by adminViewModel.diagnosticErrors.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    var selectedStatusFilter by remember { mutableStateOf("ALL") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isEng) "System Observability & Diagnostics" else "प्रणाली निरीक्षण व स्व-निदान",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Button(
                    onClick = { adminViewModel.runSystemDiagnostics(context) },
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Build, contentDescription = "Run", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isEng) "Run Diagnostics" else "निदान चलाएं", fontSize = 11.sp)
                }
            }
        }

        item {
            Text(if (isEng) "Component Health Status" else "घटक स्वास्थ्य स्थिति", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        items(healthList) { health ->
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier.size(8.dp).background(
                                    when (health.status) {
                                        HealthStatus.HEALTHY -> SadhakThemeColors.success
                                        HealthStatus.DEGRADED -> SadhakThemeColors.warning
                                        else -> MaterialTheme.colorScheme.error
                                    },
                                    CircleShape
                                )
                            )
                            Text(health.componentName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Text(health.details, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text("${health.latencyMs}ms", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(if (isEng) "Crash & Error Diagnostics (${errorLogs.size})" else "त्रुटि व क्रैश रिकॉर्ड्स (${errorLogs.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                if (errorLogs.isNotEmpty()) {
                    TextButton(onClick = { adminViewModel.clearDiagnosticErrors(adminEmail) }) {
                        Text(if (isEng) "Clear Logs" else "लॉग साफ करें", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }

        if (errorLogs.isEmpty()) {
            item {
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = "Clean", tint = SadhakThemeColors.success)
                        Text(
                            text = if (isEng) "Zero active crash or diagnostic exceptions recorded. System is fully healthy." else "कोई सक्रिय क्रैश या अपवाद रिकॉर्ड नहीं है। प्रणाली पूर्णतः स्वस्थ है।",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        } else {
            items(errorLogs) { err ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${err.category.name}: ${err.errorType}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                            Surface(
                                color = when (err.severity) {
                                    ErrorSeverity.CRITICAL -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                                    ErrorSeverity.HIGH -> SadhakThemeColors.warning.copy(alpha = 0.2f)
                                    else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                },
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "${err.severity.name} (x${err.occurrenceCount})",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                    color = if (err.severity == ErrorSeverity.CRITICAL) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Text(err.message, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        Text(err.stackTraceSnippet, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            if (err.status != ErrorStatus.RESOLVED) {
                                TextButton(onClick = { adminViewModel.updateErrorDiagnosticStatus(err.fingerprint, ErrorStatus.RESOLVED, adminEmail) }) {
                                    Text("Mark Resolved", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// COMPLIANCE & PLAY STORE AUDIT TAB
// ==========================================
@Composable
fun AdminComplianceTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val auditList by adminViewModel.complianceAuditList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Text(
                text = if (isEng) "Google Play Policy & Compliance Audit" else "गूगल प्ले नीति व अनुपालन जांच",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        item {
            SacredCard(backgroundColor = SadhakThemeColors.success.copy(alpha = 0.12f)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Verified, contentDescription = "Passed", tint = SadhakThemeColors.success)
                    Column {
                        Text(if (isEng) "All 8 Compliance Checks Passed" else "सभी 8 अनुपालन परीक्षण उत्तीर्ण", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(if (isEng) "Codebase adheres to Play Store policies & YouTube Developer Terms." else "कोडबेस प्ले स्टोर नीतियों और यूट्यूब डेवलपर शर्तों का पूर्णतः पालन करता है।", fontSize = 11.sp)
                    }
                }
            }
        }

        items(auditList) { item ->
            SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text("[${item.id}] ${item.title}", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Surface(color = SadhakThemeColors.success.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
                            Text("COMPLIANT", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SadhakThemeColors.success, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                    Text("Category: ${item.policyCategory}", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text(item.requirementDescription, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Evidence: ${item.complianceEvidence}", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// ==========================================
// 6. NOTIFICATIONS TAB
// ==========================================
@Composable
fun AdminNotificationsTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val notifications by adminViewModel.adminNotifications.collectAsState()

    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Daily Panchang") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEng) "Broadcasting & Scheduled Notifications" else "सूचना प्रसारण",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Notification Title") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("Notification Message") }, modifier = Modifier.fillMaxWidth())
                        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category (e.g. Daily Panchang, Festival)") }, modifier = Modifier.fillMaxWidth())

                        Button(
                            onClick = {
                                val record = AdminNotificationRecord(title = title, message = message, category = category)
                                adminViewModel.broadcastNotification(record, adminEmail)
                                title = ""
                                message = ""
                            },
                            enabled = title.isNotBlank() && message.isNotBlank(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(if (isEng) "Broadcast Notification" else "सूचना प्रसारित करें")
                        }
                    }
                }

                }
            }
            items(notifications) { notif ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(notif.message, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Category: ${notif.category} • Priority: ${notif.priority}", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
    }
}

// ==========================================
// 7. COMMUNITY TAB
// ==========================================
@Composable
fun AdminCommunityTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val posts by adminViewModel.posts.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEng) "Community Post Moderation" else "समुदाय पोस्ट संचालन",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                }
            }
            items(posts) { post ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(post.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            if (post.isPinned) {
                                Surface(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), shape = RoundedCornerShape(6.dp)) {
                                    Text("PINNED", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                        }
                        Text("By: ${post.author} • Category: ${post.category}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(post.content, fontSize = 12.sp, maxLines = 2)

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = { adminViewModel.pinCommunityPost(post, adminEmail) }) {
                                Icon(Icons.Default.PushPin, contentDescription = "Pin", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (post.isPinned) "Unpin" else "Pin", fontSize = 12.sp)
                            }
                            TextButton(onClick = { adminViewModel.deleteCommunityPost(post.id, adminEmail) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Remove", fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 8. USERS TAB
// ==========================================
@Composable
fun AdminUsersTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val users by adminViewModel.users.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEng) "Registered Sadhak Accounts & Roles" else "साधक खाते व भूमिकाएं",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                }
            }
            items(users) { user ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(user.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text("Email: ${user.email}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("Role: ${user.role}", fontSize = 11.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        }

                        Row {
                            if (user.role == "ADMIN" || user.role == "SuperUser") {
                                TextButton(onClick = { adminViewModel.updateUserRole(user.email, "USER", adminEmail) }) {
                                    Text("Make USER", fontSize = 11.sp)
                                }
                            } else {
                                Button(onClick = { adminViewModel.updateUserRole(user.email, "ADMIN", adminEmail) }) {
                                    Text("Make ADMIN", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 9. APP SETTINGS TAB
// ==========================================
@Composable
fun AdminSettingsTab(adminViewModel: AdminViewModel, isEng: Boolean, adminEmail: String) {
    val settings by adminViewModel.appSettings.collectAsState()

    var appName by remember { mutableStateOf(settings.appName) }
    var aiName by remember { mutableStateOf(settings.aiAssistantName) }
    var aboutInfo by remember { mutableStateOf(settings.aboutInfo) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(bottom = 80.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = if (isEng) "Global System Configuration" else "वैश्विक प्रणाली विन्यास",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(value = appName, onValueChange = { appName = it }, label = { Text("App Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = aiName, onValueChange = { aiName = it }, label = { Text("AI Assistant Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = aboutInfo, onValueChange = { aboutInfo = it }, label = { Text("About Platform Text") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

                Button(
                    onClick = {
                        val updated = settings.copy(appName = appName, aiAssistantName = aiName, aboutInfo = aboutInfo)
                        adminViewModel.saveAppSettings(updated, adminEmail)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (isEng) "Save App Settings" else "सेटिंग्स सुरक्षित करें")
                }
            }
        }
    }
}

// ==========================================
// 10. AUDIT LOG TAB
// ==========================================
@Composable
fun AdminAuditLogTab(adminViewModel: AdminViewModel, isEng: Boolean) {
    val logs by adminViewModel.auditLogs.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isEng) "Append-Only Admin Audit Trail" else "प्रशासक अंकेक्षण लॉग",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )

                }
            }
            items(logs) { log ->
                SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(log.action, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                            Text(SimpleDateFormat("HH:mm:ss, dd/MM", Locale.getDefault()).format(Date(log.timestamp)), fontSize = 10.sp, color = Color.Gray)
                        }
                        Text("Admin: ${log.adminIdentifier} • Entity: ${log.entityType} (${log.entityId})", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (log.newValue.isNotBlank()) {
                            Text("Details: ${log.newValue}", fontSize = 11.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}


/** Admin: manage the YouTube channels whose uploads feed the video library. */
@Composable
fun AdminYoutubeChannelsCard(videoViewModel: VideoViewModel, isEng: Boolean, adminEmail: String) {
    val channels by videoViewModel.youtubeChannels.collectAsState()
    var input by remember { mutableStateOf("") }
    var busy by remember { mutableStateOf(false) }
    var msg by remember { mutableStateOf<String?>(null) }
    var confirmRemove by remember { mutableStateOf<com.example.data.YoutubeChannel?>(null) }
    LaunchedEffect(Unit) { videoViewModel.refreshChannels() }

    SacredCard(backgroundColor = MaterialTheme.colorScheme.surface) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(if (isEng) "Video library channels (${channels.size})" else "वीडियो चैनल (${channels.size})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Text(
                if (isEng) "New uploads from every enabled channel appear in the Videos library automatically." else "सक्रिय चैनलों के नए वीडियो अपने-आप लाइब्रेरी में आते हैं।",
                fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedTextField(
                    value = input, onValueChange = { input = it }, singleLine = true,
                    placeholder = { Text(if (isEng) "Channel link or @handle" else "चैनल लिंक या @handle", fontSize = 12.sp) },
                    modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)
                )
                Button(
                    onClick = {
                        busy = true; msg = null
                        videoViewModel.addYoutubeChannel(input, adminEmail) { r -> msg = r; busy = false; if (r.startsWith("Added")) input = "" }
                    },
                    enabled = input.isNotBlank() && !busy,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (busy) CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                    else Text(if (isEng) "Add" else "जोड़ें", fontSize = 12.sp)
                }
            }
            msg?.let { Text(it, color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
            channels.forEach { ch ->
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(ch.title, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, maxLines = 1)
                        Text(ch.channelId, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                    }
                    Switch(checked = ch.enabled, onCheckedChange = { videoViewModel.setYoutubeChannelEnabled(ch.channelId, it) })
                    IconButton(onClick = { confirmRemove = ch }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove channel", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
    confirmRemove?.let { ch ->
        AlertDialog(
            onDismissRequest = { confirmRemove = null },
            title = { Text(if (isEng) "Remove ${ch.title}?" else "${ch.title} हटाएँ?") },
            text = { Text(if (isEng) "Its videos already in the library stay; new uploads stop syncing." else "पुराने वीडियो रहेंगे; नए वीडियो नहीं आएँगे।") },
            confirmButton = { TextButton(onClick = { videoViewModel.removeYoutubeChannel(ch.channelId); confirmRemove = null }) { Text(if (isEng) "Remove" else "हटाएँ") } },
            dismissButton = { TextButton(onClick = { confirmRemove = null }) { Text(if (isEng) "Cancel" else "रद्द करें") } }
        )
    }
}
