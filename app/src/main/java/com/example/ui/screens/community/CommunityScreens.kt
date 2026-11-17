package com.example.ui.screens

import com.example.ui.components.*
import com.example.ui.theme.*
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
fun CommunityScreen(
    viewModel: SadhakViewModel,
    communityViewModel: com.example.ui.viewmodel.CommunityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userRole by viewModel.userRole.collectAsState()
    val isEng by viewModel.isEnglish.collectAsState()
    val posts by communityViewModel.posts.collectAsState()
    val selectedPostId by communityViewModel.selectedPostId.collectAsState()
    val isPublicPosting by viewModel.isPublicPostingEnabled.collectAsState()
    val currentUserRole by viewModel.userRole.collectAsState()
    val isAdmin by viewModel.isAdmin.collectAsState()
    
    var showCreatePostDialog by remember { mutableStateOf(false) }
    var draftPostTitle by remember { mutableStateOf("") }
    var draftPostCategory by remember { mutableStateOf("Bhakti Experiences") }
    var draftPostContent by remember { mutableStateOf("") }
    var draftPostStatusText by remember { mutableStateOf("") }
    var reportingPost by remember { mutableStateOf<CommunityPost?>(null) }
    val sortBy by communityViewModel.communitySortBy.collectAsState()

    if (selectedPostId != null) {
        LaunchedEffect(selectedPostId) {
            viewModel.setBottomBarVisibility(false)
        }
        DisposableEffect(Unit) {
            onDispose {
                viewModel.setBottomBarVisibility(true)
            }
        }
        androidx.activity.compose.BackHandler {
            viewModel.selectPost(null)
        }
        // Comment Thread view
        val activePost = posts.find { it.id == selectedPostId }
        if (activePost != null) {
            CommentThreadScreen(
                post = activePost,
                viewModel = viewModel,
                isEnglish = isEng,
                onBack = { viewModel.selectPost(null) }
            )
        } else {
            viewModel.selectPost(null)
        }
    } else {
        Scaffold(
            floatingActionButton = {
                // Post creation FAB
                Button(
                    onClick = {
                        if (userRole == "Guest") viewModel.triggerGuestPromo() else showCreatePostDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("create_post_fab")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isEng) "Share Reflection" else "विचार साझा करें", color = Color.White)
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .testTag("community_screen")
            ) {
                SacredHeader(
                    title = if (isEng) "Sadhak Community" else "साधक संघ",
                    subtitle = if (isEng) "Ask, answer and share: experiences, yatra help, hidden temples" else "पूछें, उत्तर दें, साझा करें: अनुभव, यात्रा सहायता, छिपे तीर्थ",
                    showDiyas = true
                )

                // Category & Filtering options
                var selectedCommunityCategory by remember { mutableStateOf("All") }
                val communityCategories = listOf("All", "Bhakti Experiences", "Doubts & Discussions", "Yatra Questions", "Yatra Tips & Guides", "Hidden Spiritual Treasures", "Temple Stories", "Temple Info Correction", "Daily Karma Journal")
                val getCommunityCategoryDisplayName = { cat: String ->
                    if (isEng) cat
                    else when (cat) {
                        "All" -> "सभी"
                        "Bhakti Experiences" -> "भक्ति अनुभव"
                        "Doubts & Discussions" -> "जिज्ञासा एवं चर्चा"
                        "Daily Karma Journal" -> "दैनिक कर्म पत्रिका"
                        "Temple Stories" -> "मंदिर गाथा"
                        "Yatra Questions" -> "यात्रा प्रश्न"
                        "Yatra Tips & Guides" -> "यात्रा सुझाव व मार्गदर्शन"
                        "Hidden Spiritual Treasures" -> "भारत के छिपे आध्यात्मिक रत्न"
                        "Temple Info Correction" -> "मंदिर जानकारी सुधार"
                        else -> cat
                    }
                }

                val filteredPosts = remember(posts, selectedCommunityCategory) {
                    if (selectedCommunityCategory == "All") posts
                    else posts.filter { it.category == selectedCommunityCategory }
                }

                // Row 1: Category Row
                androidx.compose.foundation.lazy.LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 4.dp, horizontal = 2.dp)
                ) {
                    items(communityCategories) { category ->
                        val isSelected = selectedCommunityCategory == category
                        androidx.compose.material3.FilterChip(
                            selected = isSelected,
                            onClick = { selectedCommunityCategory = category },
                            label = { Text(getCommunityCategoryDisplayName(category)) },
                            modifier = Modifier.animateContentSize().touchFeedback()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Row 2: Additional Filters Row (Sort dropdown AssistChip)
                val sorts = listOf("New", "Top", "Discussed")
                val label = when (sortBy) {
                    "New" -> if (isEng) "New" else "नवीनतम"
                    "Top" -> if (isEng) "Top Devotional" else "लोकप्रिय"
                    else -> if (isEng) "Most Discussed" else "चर्चा में"
                }

                val isAnyFilterActive = selectedCommunityCategory != "All" || sortBy != "New"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var expandedSort by remember { mutableStateOf(false) }
                    Box {
                        androidx.compose.material3.AssistChip(
                            onClick = { expandedSort = true },
                            label = { Text(if (isEng) "Sort: $label" else "क्रम: $label") },
                            modifier = Modifier.touchFeedback()
                        )
                        DropdownMenu(
                            expanded = expandedSort,
                            onDismissRequest = { expandedSort = false }
                        ) {
                            sorts.forEach { sort ->
                                val itemLabel = when (sort) {
                                    "New" -> if (isEng) "New" else "नवीनतम"
                                    "Top" -> if (isEng) "Top Devotional" else "लोकप्रिय"
                                    else -> if (isEng) "Most Discussed" else "चर्चा में"
                                }
                                DropdownMenuItem(
                                    text = { Text(itemLabel) },
                                    onClick = {
                                        viewModel.setCommunitySort(sort)
                                        expandedSort = false
                                    }
                                )
                            }
                        }
                    }

                    if (isAnyFilterActive) {
                        Text(
                            text = if (isEng) "Clear all filters" else "साफ़ करें",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .clickable {
                                    selectedCommunityCategory = "All"
                                    viewModel.setCommunitySort("New")
                                }
                                .touchFeedback()
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Post listings
                if (filteredPosts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isEng) "No posts here. Tap share to create one!" else "अभी तक कोई पोस्ट नहीं है। पहली पोस्ट साझा करें!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    val communityListState = androidx.compose.foundation.lazy.rememberLazyListState()
                    val showCommunityScrollToTop by remember {
                        derivedStateOf { communityListState.firstVisibleItemIndex > 0 || communityListState.firstVisibleItemScrollOffset > 300 }
                    }
                    val communityCoroutineScope = rememberCoroutineScope()

                    val isRefreshing by viewModel.isRefreshing.collectAsState()
                    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.refreshGeneric() },
                        modifier = Modifier.weight(1f)
                    ) {
                        LazyColumn(
                            state = communityListState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                        items(filteredPosts) { post ->
                            SacredCard(
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                borderColor = if (post.isPinned) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Header
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "@${post.author}",
                                                style = MaterialTheme.typography.labelLarge.copy(
                                                    color = MaterialTheme.colorScheme.secondary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                            if (post.isPinned) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Verified,
                                                    contentDescription = "Pinned",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(2.dp))
                                                Text(
                                                    text = if (isEng) "PINNED" else "पिन किया गया",
                                                    fontSize = 10.sp,
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = post.category,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier
                                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                            
                                            val currentUserName by viewModel.userName.collectAsState()
                                            val isSuper = currentUserRole == "SuperUser" || isAdmin
                                            val canDelete = isSuper || (currentUserName == post.author)

                                            if (isSuper) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Verified,
                                                        contentDescription = "Pin Post",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }

                                            if (canDelete) {
                                                Spacer(modifier = Modifier.width(8.dp))
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post.id) },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Delete,
                                                        contentDescription = "Delete Post",
                                                        tint = MaterialTheme.colorScheme.error,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    // Title
                                    Text(
                                        text = post.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        ),
                                        modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Body preview
                                    Text(
                                        text = post.content,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                            lineHeight = 20.sp
                                        ),
                                        maxLines = 4,
                                        modifier = Modifier.clickable { viewModel.selectPost(post.id) }
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Vote count & Comments Count
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { viewModel.upvotePost(post) },
                                                modifier = Modifier.size(36.dp).testTag("upvote_post_${post.id}")
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ThumbUp,
                                                    contentDescription = "Upvote",
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                            Text(
                                                text = post.upvotes.toString(),
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp).testTag("upvote_count_${post.id}")
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            IconButton(
                                                onClick = { viewModel.downvotePost(post) },
                                                modifier = Modifier.size(36.dp).testTag("downvote_post_${post.id}")
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ThumbDown,
                                                    contentDescription = "Downvote",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                            Text(
                                                text = post.downvotes.toString(),
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Gray,
                                                fontSize = 13.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp).testTag("downvote_count_${post.id}")
                                            )
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (isAdmin) {
                                                IconButton(
                                                    onClick = { viewModel.togglePinPost(post) },
                                                    modifier = Modifier.size(36.dp).testTag("pin_post_${post.id}")
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.Star,
                                                        contentDescription = "Pin",
                                                        tint = if (post.isPinned) MaterialTheme.colorScheme.primary else Color.Gray,
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                            val userName by viewModel.userName.collectAsState()
                                            IconButton(
                                                onClick = { reportingPost = post },
                                                modifier = Modifier.size(36.dp).testTag("report_post_${post.id}")
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Announcement,
                                                    contentDescription = "Report Post",
                                                    tint = Color.Gray,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                            if (isAdmin || post.author == userName) {
                                                IconButton(
                                                    onClick = { viewModel.deletePost(post) },
                                                    modifier = Modifier.size(36.dp).testTag("delete_post_${post.id}")
                                                ) {
                                                    Icon(
                                                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                                                        contentDescription = "Delete",
                                                        tint = Color.Red.copy(alpha=0.7f),
                                                        modifier = Modifier.size(18.dp)
                                                    )
                                                }
                                            }
                                            val isSaved = viewModel.favoritePostIds.collectAsState().value.contains(post.id)
                                            IconButton(
                                                onClick = { viewModel.toggleFavoritePost(post.id) },
                                                modifier = Modifier.size(36.dp).testTag("bookmark_post_${post.id}")
                                            ) {
                                                Icon(
                                                    imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.Book,
                                                    contentDescription = if (isSaved) "Remove Bookmark" else "Save Bookmark",
                                                    tint = if (isSaved) MaterialTheme.colorScheme.primary else Color.Gray,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                        
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.clickable { viewModel.selectPost(post.id) }.testTag("post_comments_button_${post.id}")
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Comment,
                                                contentDescription = "Comments",
                                                tint = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "${post.commentsCount} ${if (isEng) "Comments" else "टिप्पणियाँ"}",
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                ScrollToTopButton(
                    visible = showCommunityScrollToTop,
                    testTag = "scroll_to_top_community",
                    onClick = {
                        communityCoroutineScope.launch {
                            communityListState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }

            // Write Post Dialog
            if (showCreatePostDialog) {
                val canPost by viewModel.canUserPost.collectAsState()

                Dialog(onDismissRequest = { showCreatePostDialog = false }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEng) "Share Reflection" else "विचार साझा करें",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )
                                DiyaDecoration(size = 24.dp)
                            }

                            Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))

                            // Gentle guidelines
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                                    .border(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = if (isEng) {
                                        "🕉 Sadhak Guidelines: Speak with respect, kindness, and truth. Avoid politics, marketing, or hate speech. Let our community be a temple of peace."
                                    } else {
                                        "🕉 साधक नियम: सम्मान, दया और सत्य के साथ बोलें। राजनीति, विपणन या घृणास्पद भाषण से बचें। हमारे समुदाय को शांति का मंदिर बनने दें।"
                                    },
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            if (!canPost) {
                                Text(
                                    text = if (isEng) {
                                        "❌ Public posting is currently closed by the administrator. Please contact verification support in Profile or log in as administrator."
                                    } else {
                                        "❌ प्रशासक द्वारा सार्वजनिक पोस्टिंग वर्तमान में बंद है। कृपया प्रोफ़ाइल में जाकर सत्यापन का अनुरोध करें।"
                                    },
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            } else {
                                // Form
                                OutlinedTextField(
                                    value = draftPostTitle,
                                    onValueChange = { draftPostTitle = it },
                                    label = { Text(text = if (isEng) "Title" else "शीर्षक") },
                                    modifier = Modifier.fillMaxWidth().testTag("create_post_title_input"),
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                )

                                // Category selector
                                Text(
                                    text = if (isEng) "Select Forum Category" else "मंच श्रेणी चुनें:",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontSize = 13.sp
                                )
                                val cats = listOf("Bhakti Experiences", "Doubts & Discussions", "Yatra Questions", "Yatra Tips & Guides", "Hidden Spiritual Treasures", "Temple Stories", "Temple Info Correction", "Daily Karma Journal")
                                Column {
                                    cats.forEach { cat ->
                                        val displayCat = getCommunityCategoryDisplayName(cat)
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { draftPostCategory = cat }
                                        ) {
                                            RadioButton(
                                                selected = draftPostCategory == cat,
                                                onClick = { draftPostCategory = cat },
                                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                                            )
                                            Text(text = displayCat, fontSize = 13.sp)
                                        }
                                    }
                                }

                                OutlinedTextField(
                                    value = draftPostContent,
                                    onValueChange = { draftPostContent = it },
                                    label = { Text(text = if (isEng) "Reflection content..." else "विचार विवरण...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 100.dp)
                                        .testTag("create_post_content_input"),
                                    maxLines = 6,
                                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                )

                                if (draftPostStatusText.isNotEmpty()) {
                                    Text(text = draftPostStatusText, color = Color.Red, fontSize = 12.sp)
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = { showCreatePostDialog = false },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
                                        modifier = Modifier.testTag("cancel_post_button")
                                    ) {
                                        Text(text = if (isEng) "Cancel" else "रद्द करें")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            if (draftPostTitle.isBlank() || draftPostContent.isBlank()) {
                                                draftPostStatusText = "Please fill in all fields."
                                            } else {
                                                viewModel.createPost(draftPostTitle, draftPostContent, draftPostCategory)
                                                draftPostTitle = ""
                                                draftPostContent = ""
                                                draftPostCategory = "Bhakti Experiences"
                                                draftPostStatusText = ""
                                                showCreatePostDialog = false
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        modifier = Modifier.testTag("publish_post_button")
                                    ) {
                                        Text(text = if (isEng) "Publish" else "प्रकाशित करें", color = Color.White)
                                    }
                                }
                            }

                            if (!canPost) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { showCreatePostDialog = false },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                    ) {
                                        Text(text = "OK", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (reportingPost != null) {
                val postToReport = reportingPost!!
                AlertDialog(
                    onDismissRequest = { reportingPost = null },
                    title = {
                        Text(
                            text = if (isEng) "Report Community Post" else "पोस्ट की शिकायत करें",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    text = {
                        Text(
                            text = if (isEng)
                                "Are you sure you want to report '${postToReport.title}'? This post will be flagged for review by community moderators."
                            else
                                "क्या आप '${postToReport.title}' की शिकायत करना चाहते हैं? यह पोस्ट संयोजकों द्वारा समीक्षा के लिए चिह्नित की जाएगी।"
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                communityViewModel.reportPost(postToReport)
                                reportingPost = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(if (isEng) "Report" else "रिपोर्ट करें", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { reportingPost = null }) {
                            Text(if (isEng) "Cancel" else "रद्द करें")
                        }
                    }
                )
            }
        }
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentThreadScreen(
    post: CommunityPost,
    viewModel: SadhakViewModel,
    isEnglish: Boolean,
    onBack: () -> Unit
) {
    val comments by viewModel.activeComments.collectAsState()
    var newCommentText by remember { mutableStateOf("") }

    LaunchedEffect(post.id) {
        viewModel.selectPost(post.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondary)
            }
            Text(
                text = if (isEnglish) "Back to Community" else "वापस समुदाय",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable(onClick = onBack)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main Post Card
        SacredCard(
            backgroundColor = MaterialTheme.colorScheme.surface,
            borderColor = MaterialTheme.colorScheme.primary
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "@${post.author}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = post.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 22.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEnglish) "Discussion Thread" else "चर्चा सूत्र",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Comments List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (comments.isEmpty()) {
                item {
                    Text(
                        text = if (isEnglish) "No comments yet. Start the dialogue with kind words!" else "कोई टिप्पणी नहीं है। मधुर शब्दों के साथ चर्चा की शुरुआत करें!",
                        color = Color.Gray,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            } else {
                items(comments) { comment ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "@${comment.author}",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Just now", // Local simple representation
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = comment.content,
                                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = { viewModel.toggleLikeComment(comment) },
                                        modifier = Modifier.size(32.dp).testTag("comment_upvote_${comment.id}")
                                    ) {
                                        Icon(Icons.Default.ThumbUp, contentDescription = "Upvote", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                                    }
                                    Text(text = comment.upvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                    IconButton(
                                        onClick = { viewModel.toggleDislikeComment(comment) },
                                        modifier = Modifier.size(32.dp).testTag("comment_downvote_${comment.id}")
                                    ) {
                                        Icon(Icons.Default.ThumbDown, contentDescription = "Downvote", modifier = Modifier.size(16.dp), tint = Color.Gray)
                                    }
                                    Text(text = comment.downvotes.toString(), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                }
                                val userName by viewModel.userName.collectAsState()
                                val isAdmin by viewModel.isAdmin.collectAsState()
                                if (isAdmin || comment.author == userName) {
                                    IconButton(
                                        onClick = { viewModel.deleteComment(comment.id) },
                                        modifier = Modifier.size(32.dp).testTag("comment_delete_${comment.id}")
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier.size(16.dp), tint = Color.Red.copy(alpha=0.7f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Write comment input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newCommentText,
                onValueChange = { newCommentText = it },
                placeholder = {
                    Text(text = if (isEnglish) "Write a supportive comment..." else "सहायक टिप्पणी लिखें...")
                },
                modifier = Modifier.weight(1f).testTag("comment_input_field"),
                colors = OutlinedTextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSurface, unfocusedTextColor = MaterialTheme.colorScheme.onSurface, focusedBorderColor = MaterialTheme.colorScheme.primary, cursorColor = MaterialTheme.colorScheme.primary, focusedLabelColor = MaterialTheme.colorScheme.primary, unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (newCommentText.isNotBlank()) {
                        viewModel.addPostComment(post.id, newCommentText)
                        newCommentText = ""
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.testTag("submit_comment_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}


// --- SACRED AARTI BELL CHIME ENGINE & WIDGET ---
