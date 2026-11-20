package com.example.ui.screens
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
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
import com.example.ui.audio.DevotionalTextReaderBar
import com.example.ui.audio.DevotionalListenButton
import com.example.ui.audio.SadhakTextReader
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
fun KnowledgeScreen(
    viewModel: SadhakViewModel,
    knowledgeViewModel: com.example.ui.viewmodel.KnowledgeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isEng by viewModel.isEnglish.collectAsState()
    val articles by knowledgeViewModel.articles.collectAsState()
    val selectedCat by knowledgeViewModel.selectedKnowledgeCategory.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    
    val selectedFormat by knowledgeViewModel.knowledgeFormat.collectAsState()
    val selectedDifficulty by knowledgeViewModel.knowledgeDifficulty.collectAsState()
    val sortBy by knowledgeViewModel.knowledgeSortBy.collectAsState()
    val searchQuery by knowledgeViewModel.searchQuery.collectAsState()
    
    var activeArticle by remember { mutableStateOf<KnowledgeArticle?>(null) }

    val categories = listOf(
        "All",
        "Vedas & Vedangas",
        "Upanishads",
        "Itihasa",
        "Puranas",
        "Darshana",
        "Scriptures & Granths",
        "Stotra & Sacred Texts",
        "Saints & Acharyas",
        "Deities & Concepts",
        "Spiritual Concepts",
        "Pooja & Vidhi",
        "Mantras & Chanting",
        "Sadhana & Yoga",
        "Festivals & Fasting",
        "Temples & Sacred Sites"
    )

    val getCategoryDisplayName = { cat: String ->
        if (isEng) cat
        else when (cat) {
            "All" -> "सभी"
            "Vedas & Vedangas" -> "वेद और वेदांग"
            "Upanishads" -> "उपनिषद"
            "Itihasa" -> "इतिहास (रामायण/महाभारत)"
            "Puranas" -> "अष्टादश पुराण"
            "Darshana" -> "दर्शन शास्त्र"
            "Scriptures & Granths" -> "पवित्र धर्मग्रंथ"
            "Stotra & Sacred Texts" -> "स्तोत्र एवं पाठ"
            "Saints & Acharyas" -> "संत और आचार्य"
            "Spiritual Concepts" -> "आध्यात्मिक विचार"
            "Pooja & Vidhi" -> "पूजा एवं विधि"
            "Mantras & Chanting" -> "मंत्र एवं जप"
            "Sadhana & Yoga" -> "साधना एवं योग"
            "Festivals & Fasting" -> "त्यौहार और व्रत"
            "Temples & Sacred Sites" -> "मंदिर और तीर्थ स्थान"
            else -> cat
        }
    }

    // Fetch / refresh the Vedas, Upanishads, Puranas … library from Wikipedia (weekly, in background)
    val libCtx = androidx.compose.ui.platform.LocalContext.current
    val libScope = rememberCoroutineScope()
    LaunchedEffect(Unit) { com.example.data.ScriptureLibrary.syncIfStale(libCtx) }

    val pendingArticle by viewModel.pendingArticleTitle.collectAsState()
    LaunchedEffect(pendingArticle, articles) {
        val t = pendingArticle ?: return@LaunchedEffect
        articles.firstOrNull { it.title == t }?.let { activeArticle = it; viewModel.clearPendingArticle() }
    }

    if (activeArticle != null) {
        LaunchedEffect(activeArticle) {
            viewModel.setBottomBarVisibility(false)
        }
        DisposableEffect(Unit) {
            onDispose {
                viewModel.setBottomBarVisibility(true)
            }
        }
        androidx.activity.compose.BackHandler {
            activeArticle = null
        }
        // Article Detail View
        ArticleDetailScreen(
            article = activeArticle!!,
            isEnglish = isEng,
            onBack = { activeArticle = null },
            onWatchVideo = {
                viewModel.setScreen("Videos")
            },
            discussion = { com.example.ui.screens.SourceDiscussion(viewModel, "article", activeArticle!!.title, activeArticle!!.title) }
        )
    } else {
        val knowledgeListState = androidx.compose.foundation.lazy.rememberLazyListState()
        val showKnowledgeScrollToTop by remember {
            derivedStateOf { knowledgeListState.firstVisibleItemIndex > 0 || knowledgeListState.firstVisibleItemScrollOffset > 300 }
        }
        val knowledgeCoroutineScope = rememberCoroutineScope()

        val isRefreshing by viewModel.isRefreshing.collectAsState()
        androidx.compose.material3.pulltorefresh.PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refreshGeneric(); libScope.launch { com.example.data.ScriptureLibrary.syncIfStale(libCtx, force = true) } },
            modifier = Modifier.fillMaxSize()
        ) {
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
                    val searchPrefs = androidx.compose.ui.platform.LocalContext.current.getSharedPreferences("knowledge_search", android.content.Context.MODE_PRIVATE)
                    val recentSearches = remember(searchQuery) {
                        if (searchQuery.isBlank()) searchPrefs.getString("recent", "")!!.split("\n").filter { it.isNotBlank() }
                        else { val r = (listOf(searchQuery.trim()) + searchPrefs.getString("recent", "")!!.split("\n")).filter { it.isNotBlank() }.distinct().take(5); if (searchQuery.trim().length >= 3) searchPrefs.edit().putString("recent", r.joinToString("\n")).apply(); r.drop(1) }
                    }
                    val popularSearches = listOf("Karma meaning", "Shiv mantras", "Ekadashi vrat rules")

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                knowledgeViewModel.setKnowledgeSearchQuery(it)
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
                                if (recentSearches.isNotEmpty()) Text(if (isEng) "Recent Searches" else "हाल की खोजें", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp))
                                recentSearches.forEach { search ->
                                    DropdownMenuItem(
                                        text = { Text(search) },
                                        onClick = {
                                            knowledgeViewModel.setKnowledgeSearchQuery(search)
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
                                            knowledgeViewModel.setKnowledgeSearchQuery(search)
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
                                onClick = { knowledgeViewModel.setKnowledgeCategory(category) },
                                label = { Text(getCategoryDisplayName(category)) },
                                modifier = Modifier.animateContentSize().touchFeedback()
                            )
                        }
                    }
                }

                // Row 1B: Deity Filter Row
                item {
                    val selectedDeity by knowledgeViewModel.selectedDeity.collectAsState()
                    val deities = listOf("All", "Shiva", "Vishnu", "Krishna", "Rama", "Hanuman", "Durga", "Ganesha", "Surya")
                    
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(
                            text = if (isEng) "Filter by Sacred Deity" else "इष्टदेव अनुसार खोजें",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.padding(bottom = 4.dp, start = 2.dp)
                        )
                        androidx.compose.foundation.lazy.LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
                        ) {
                            items(deities) { deity ->
                                val isSelected = selectedDeity == deity
                                val displayName = if (isEng) deity else when(deity) {
                                    "All" -> "सभी"
                                    "Shiva" -> "शिव"
                                    "Vishnu" -> "विष्णु"
                                    "Krishna" -> "कृष्ण"
                                    "Rama" -> "राम"
                                    "Hanuman" -> "हनुमान"
                                    "Durga" -> "दुर्गा"
                                    "Ganesha" -> "गणेश"
                                    "Surya" -> "सूर्य"
                                    else -> deity
                                }
                                androidx.compose.material3.FilterChip(
                                    selected = isSelected,
                                    onClick = { knowledgeViewModel.setSelectedDeity(deity) },
                                    label = { Text("🪔 $displayName") },
                                    modifier = Modifier.animateContentSize().touchFeedback()
                                )
                            }
                        }
                    }
                }

                // Row 2: Additional filters (scrollable) and "Clear all"
                item {
                    val selectedDeity by knowledgeViewModel.selectedDeity.collectAsState()
                    val isAnyFilterActive = selectedCat != "All" || selectedDeity != "All" || selectedFormat != "All" || selectedDifficulty != "All" || searchQuery.isNotEmpty()
                    
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
                                                    knowledgeViewModel.setKnowledgeDifficulty(diff)
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
                                                    knowledgeViewModel.setKnowledgeFormat(form)
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
                                                    knowledgeViewModel.setKnowledgeSort(sort)
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
                                        knowledgeViewModel.setKnowledgeCategory("All")
                                        knowledgeViewModel.setSelectedDeity("All")
                                        knowledgeViewModel.setKnowledgeFormat("All")
                                        knowledgeViewModel.setKnowledgeDifficulty("All")
                                        knowledgeViewModel.setKnowledgeSearchQuery("")
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = article.category.uppercase(),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 1.sp
                                            )
                                        )
                                        if (article.textReference.isNotEmpty()) {
                                            Text(
                                                text = "• ${article.textReference}",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                        }
                                    }
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${com.example.utils.ReadingTimeUtils.calculateReadTime(article.content)} min read",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    if (article.difficulty.isNotEmpty()) {
                                        Text(
                                            text = "• ${article.difficulty}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    when (article.verificationStatus) {
                                        "VERIFIED_SOURCE" -> {
                                            Text(
                                                text = if (isEng) "✓ Verified Source" else "✓ प्रमाणित ग्रंथ",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                        "TRADITIONAL_REFERENCE" -> {
                                            Text(
                                                text = if (isEng) "Traditional Text" else "पारंपरिक संदर्भ",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                        "EDITOR_REVIEW_REQUIRED" -> {
                                            Text(
                                                text = if (isEng) "Under Review" else "समीक्षाधीन",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.tertiary,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            )
                                        }
                                        "UNVERIFIED" -> {
                                            Text(
                                                text = if (isEng) "Unverified" else "अप्रमाणित",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                                    fontWeight = FontWeight.Normal
                                                )
                                            )
                                        }
                                    }
                                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    article: KnowledgeArticle,
    isEnglish: Boolean,
    onBack: () -> Unit,
    onWatchVideo: () -> Unit,
    discussion: @Composable () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = androidx.compose.ui.platform.LocalContext.current
    var fontSizeMultiplier by remember { mutableFloatStateOf(1.0f) }
    
    // Ask AI state
    var aiQuestion by remember { mutableStateOf("") }
    var aiAnswer by remember { mutableStateOf("") }
    var isAskingAi by remember { mutableStateOf(false) }
    val articleAiScope = rememberCoroutineScope()

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
            progress = { readingProgress },
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
            // Top Navigation & Reader Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onBack)
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
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                // Font Size Controls (A-, A, A+)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "A-",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .clickable { if (fontSizeMultiplier > 0.8f) fontSizeMultiplier -= 0.15f }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Text(
                        text = "${(fontSizeMultiplier * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "A+",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .clickable { if (fontSizeMultiplier < 1.6f) fontSizeMultiplier += 0.15f }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Article Sacred Card Container
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.surface,
                borderColor = MaterialTheme.colorScheme.tertiary
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Category & Verification Badge Row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = article.category.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )

                            // Verification Badge
                            val isVerified = article.verificationStatus == "VERIFIED_SOURCE"
                            val statusText = when (article.verificationStatus) {
                                "VERIFIED_SOURCE" -> if (isEnglish) "✓ Verified Source" else "✓ प्रमाणित ग्रंथ"
                                "TRADITIONAL_REFERENCE" -> if (isEnglish) "Traditional Text" else "पारंपरिक संदर्भ"
                                "EDITOR_REVIEW_REQUIRED" -> if (isEnglish) "Under Review" else "समीक्षाधीन"
                                else -> if (isEnglish) "Unverified" else "अप्रमाणित"
                            }
                            val badgeBg = when (article.verificationStatus) {
                                "VERIFIED_SOURCE" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                "TRADITIONAL_REFERENCE" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
                                "EDITOR_REVIEW_REQUIRED" -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f)
                                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                            }
                            val badgeColor = when (article.verificationStatus) {
                                "VERIFIED_SOURCE" -> MaterialTheme.colorScheme.primary
                                "TRADITIONAL_REFERENCE" -> MaterialTheme.colorScheme.secondary
                                "EDITOR_REVIEW_REQUIRED" -> MaterialTheme.colorScheme.tertiary
                                else -> MaterialTheme.colorScheme.onSurfaceVariant
                            }
                            Text(
                                text = statusText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = badgeColor
                                ),
                                modifier = Modifier
                                    .background(
                                        badgeBg,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        DiyaDecoration(size = 24.dp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = (20 * fontSizeMultiplier).sp
                        )
                    )

                    // Text Reference Subtitle
                    if (article.textReference.isNotEmpty()) {
                        Text(
                            text = article.textReference,
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Divine Text-to-Speech Reader Bar
                    DevotionalTextReaderBar(
                        id = "article_reader_${article.id}",
                        title = article.title,
                        textToRead = "${article.title}.\n${article.summary}.\n${if (article.originalText.isNotEmpty()) article.originalText + ".\n" else ""}${if (article.translation.isNotEmpty()) article.translation + ".\n" else ""}${article.content}",
                        isEnglish = isEnglish
                    )

                    Divider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.tertiary)

                    // Metadata Box (Source details, Chapter/Verse, Translator)
                    if (article.sourceName.isNotEmpty() || article.textReference.isNotEmpty() || article.chapter.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = if (isEnglish) "📜 Source & Citation Metadata" else "📜 स्रोत एवं संदर्भ विवरण",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                                if (article.sourceName.isNotEmpty()) {
                                    Text(
                                        text = "${if (isEnglish) "Scripture/Source" else "मूल ग्रंथ"}: ${article.sourceName} (${article.sourceType})",
                                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                                    )
                                }
                                if (article.textReference.isNotEmpty()) {
                                    Text(
                                        text = "${if (isEnglish) "Text Reference" else "ग्रंथ संदर्भ"}: ${article.textReference}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                                    )
                                }
                                if (article.chapter.isNotEmpty() || article.verse.isNotEmpty()) {
                                    Text(
                                        text = "${if (isEnglish) "Chapter & Verse" else "अध्याय/श्लोक"}: ${if (article.chapter.isNotEmpty()) "Ch. ${article.chapter}" else ""} ${if (article.verse.isNotEmpty()) "Verse ${article.verse}" else ""}".trim(),
                                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                                    )
                                }
                                if (article.edition.isNotEmpty() || article.translator.isNotEmpty()) {
                                    Text(
                                        text = "${if (isEnglish) "Edition / Translator" else "संस्करण / अनुवादक"}: ${article.edition} ${if (article.translator.isNotEmpty()) "(${article.translator})" else ""}".trim(),
                                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Article Summary
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = article.summary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = (14 * fontSizeMultiplier).sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Original Sanskrit / Devanagari Verse Box
                    if (article.originalText.isNotEmpty()) {
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            borderColor = MaterialTheme.colorScheme.primary
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (isEnglish) "🪔 Sacred Shloka / Original Text" else "🪔 मूल श्लोक / संस्कृत पाठ",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("Shloka", article.originalText)
                                            clipboard.setPrimaryClip(clip)
                                            android.widget.Toast.makeText(context, if (isEnglish) "Shloka copied!" else "श्लोक कॉपी हो गया!", android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Copy Shloka",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = article.originalText,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = (16 * fontSizeMultiplier).sp,
                                        color = MaterialTheme.colorScheme.secondary,
                                        lineHeight = (26 * fontSizeMultiplier).sp
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Verse Translation Box
                    if (article.translation.isNotEmpty()) {
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.03f),
                            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = if (isEnglish) "📖 Authentic Translation" else "📖 प्रामाणिक भावार्थ",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = article.translation,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = (14 * fontSizeMultiplier).sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = (22 * fontSizeMultiplier).sp
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // AI Explanation / Spiritual Insight Box
                    if (article.aiExplanation.isNotEmpty()) {
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f),
                            borderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "AI Insight",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isEnglish) "Sadhak AI Mitra Explanation" else "साधक एआई मित्र व्याख्या",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = article.aiExplanation,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = (14 * fontSizeMultiplier).sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = (22 * fontSizeMultiplier).sp
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Detailed Article Content Narrative
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = (15 * fontSizeMultiplier).sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = (24 * fontSizeMultiplier).sp
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
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // Ask Sadhak AI Mitra Interactive Q&A Card
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "AI QA",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEnglish) "Ask Sadhak AI Mitra About This Text" else "इस विषय पर साधक एआई मित्र से पूछें",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedTextField(
                                value = aiQuestion,
                                onValueChange = { aiQuestion = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = {
                                    Text(
                                        text = if (isEnglish) "e.g. How do I apply this teaching in modern daily life?" else "उदा. मैं इस शिक्षा को दैनिक जीवन में कैसे लागू करूं?",
                                        fontSize = 12.sp
                                    )
                                },
                                singleLine = false,
                                maxLines = 3
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    if (aiQuestion.isNotBlank()) {
                                        isAskingAi = true
                                        val q = aiQuestion.trim()
                                        articleAiScope.launch {
                                            val key = com.example.BuildConfig.GEMINI_API_KEY
                                            aiAnswer = if (key.isBlank() || key == "MY_GEMINI_API_KEY") {
                                                if (isEnglish) "AI Mitra isn't connected in this build yet. Please try again after the app update." else "इस संस्करण में एआई मित्र अभी जुड़ा नहीं है।"
                                            } else com.example.data.GeminiService.generateResponse(
                                                emptyList(),
                                                "Answer this question about the article \"${article.title}\". Article text:\n${article.content.take(6000)}\n\nQuestion: $q",
                                                isEnglish
                                            )
                                            isAskingAi = false
                                        }
                                    }
                                },
                                enabled = aiQuestion.isNotBlank() && !isAskingAi,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text(text = if (isEnglish) "Ask AI Mitra" else "एआई मित्र से पूछें")
                            }

                            if (aiAnswer.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = aiAnswer,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface,
                                            lineHeight = 20.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Discussion about this article (lives in the one Sanatan Community)
            Spacer(modifier = Modifier.height(16.dp))
            discussion()
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
