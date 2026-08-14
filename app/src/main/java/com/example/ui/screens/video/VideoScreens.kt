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
fun VideoHubScreen(viewModel: SadhakViewModel, videoViewModel: com.example.ui.viewmodel.VideoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val isEng by viewModel.isEnglish.collectAsState()
    
    val currentFestival by viewModel.currentFestival.collectAsState()
    androidx.compose.runtime.LaunchedEffect(currentFestival) {
        videoViewModel.setCurrentFestival(currentFestival)
    }

    val videos by videoViewModel.videos.collectAsState()
    val selectedCat by videoViewModel.selectedVideoCategory.collectAsState()
    val userRole by viewModel.userRole.collectAsState()

    var activeVideoUrl by remember { mutableStateOf<String?>(null) }
    var activeVideoTitle by remember { mutableStateOf("") }

    val categories = listOf("All", "YouTube", "Darshan", "Puja", "Stories")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("video_hub_screen")
    ) {
        SacredHeader(
            title = if (isEng) "Sacred Video Hub" else "दर्शन तरंगिणी",
            subtitle = if (isEng) "Watch divine Darshan and Puja from @SanatanSadhakk" else "चैनल @SanatanSadhakk के पवित्र आरती, दर्शन और भक्ति प्रसंग देखें",
            showDiyas = true
        )

        // Video Filtering and Sorting Options State
        var selectedVideoSort by remember { mutableStateOf("Newest") }
        val videoSorts = listOf("Newest", "A-Z")
        val sortLabel = when (selectedVideoSort) {
            "Newest" -> if (isEng) "Newest" else "नवीनतम"
            else -> if (isEng) "A-Z" else "अ-ज्ञ"
        }
        val isAnyVideoFilterActive = selectedCat != "All" || selectedVideoSort != "Newest"

        val filteredSortedVideos = remember(videos, selectedVideoSort) {
            if (selectedVideoSort == "A-Z") {
                videos.sortedBy { it.title }
            } else {
                videos
            }
        }

        // Row 1: Categories selector (horizontal scrollable LazyRow)
        androidx.compose.foundation.lazy.LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 2.dp)
        ) {
            items(categories) { category ->
                val isSelected = selectedCat == category
                val label = when (category) {
                    "All" -> if (isEng) "All Videos" else "सभी वीडियो"
                    "YouTube" -> if (isEng) "YouTube Content" else "यूट्यूब वीडियो"
                    "Darshan" -> if (isEng) "Darshan" else "देव दर्शन (Darshan)"
                    "Puja" -> if (isEng) "Puja & Aarti" else "पूजा व आरती (Puja)"
                    else -> if (isEng) "Stories & Kathas" else "कथा प्रसंग (Stories)"
                }
                androidx.compose.material3.FilterChip(
                    selected = isSelected,
                    onClick = { videoViewModel.setVideoCategory(category) },
                    label = { Text(label) },
                    modifier = Modifier.animateContentSize().touchFeedback()
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Row 2: Additional Filters Row (Sort dropdown AssistChip)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var expandedSort by remember { mutableStateOf(false) }
            Box {
                androidx.compose.material3.AssistChip(
                    onClick = { expandedSort = true },
                    label = { Text(if (isEng) "Sort: $sortLabel" else "क्रम: $sortLabel") },
                    modifier = Modifier.touchFeedback()
                )
                DropdownMenu(
                    expanded = expandedSort,
                    onDismissRequest = { expandedSort = false }
                ) {
                    videoSorts.forEach { sort ->
                        val labelVal = when (sort) {
                            "Newest" -> if (isEng) "Newest" else "नवीनतम"
                            else -> if (isEng) "A-Z" else "अ-ज्ञ"
                        }
                        DropdownMenuItem(
                            text = { Text(labelVal) },
                            onClick = {
                                selectedVideoSort = sort
                                expandedSort = false
                            }
                        )
                    }
                }
            }

            if (isAnyVideoFilterActive) {
                Text(
                    text = if (isEng) "Clear all filters" else "साफ़ करें",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .clickable {
                            videoViewModel.setVideoCategory("All")
                            selectedVideoSort = "Newest"
                        }
                        .touchFeedback()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Curated Playlist Section
        val curatedVideos by videoViewModel.curatedPlaylistVideos.collectAsState()
        val curatedTitle = videoViewModel.getCuratedPlaylistTitle(isEng)

        if (curatedVideos.isNotEmpty() && selectedCat == "All") {
            SacredCard(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "✨", fontSize = 16.sp)
                        Text(
                            text = curatedTitle,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(curatedVideos) { video ->
                            Card(
                                modifier = Modifier
                                    .width(200.dp)
                                    .clickable {
                                        activeVideoUrl = video.videoId
                                        activeVideoTitle = video.title
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(90.dp)
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                                    )
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "🕉", fontSize = 36.sp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = video.title,
                                            maxLines = 2,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                lineHeight = 16.sp
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = video.category,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // List of videos
        if (filteredSortedVideos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isEng) "No videos found in this category." else "इस श्रेणी में कोई वीडियो नहीं मिला।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val videoListState = androidx.compose.foundation.lazy.rememberLazyListState()
            val showVideoScrollToTop by remember {
                derivedStateOf { videoListState.firstVisibleItemIndex > 0 || videoListState.firstVisibleItemScrollOffset > 300 }
            }
            val videoCoroutineScope = rememberCoroutineScope()

            val isRefreshing by viewModel.isRefreshing.collectAsState()
            androidx.compose.material3.pulltorefresh.PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshGeneric() },
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    state = videoListState,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                itemsIndexed(filteredSortedVideos) { index, video ->
                    val isLocked = userRole == "Guest" && index >= 2
                    SacredCard(
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        borderColor = if (isLocked) Color.LightGray else MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                    ) {
                        Column {
                            if (activeVideoUrl == video.videoId && !isLocked) {
                                // Inline real YouTube player webview with overlay close button
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                ) {
                                    androidx.compose.ui.viewinterop.AndroidView(
                                        factory = { context ->
                                            android.webkit.WebView(context).apply {
                                                settings.javaScriptEnabled = true
                                                settings.mediaPlaybackRequiresUserGesture = false
                                                webChromeClient = android.webkit.WebChromeClient()
                                                val html = """
                                                    <html>
                                                    <body style="margin:0;padding:0;background-color:black;">
                                                        <iframe width="100%" height="100%" src="https://www.youtube.com/embed/${video.videoId}?autoplay=1&rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
                                                    </body>
                                                    </html>
                                                """.trimIndent()
                                                loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "utf-8", null)
                                            }
                                        },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    IconButton(
                                        onClick = { activeVideoUrl = null },
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(6.dp)
                                            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                            .size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            } else {
                                // Dynamic Thumbnail with Play button overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .background(
                                            Brush.linearGradient(
                                                colors = listOf(
                                                    if (isLocked) Color.LightGray.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                                    if (isLocked) Color.Gray.copy(alpha = 0.15f) else MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (video.thumbnail.isNotEmpty() && !isLocked) {
                                        coil.compose.AsyncImage(
                                            model = video.thumbnail,
                                            contentDescription = video.title,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Text(
                                            text = "🕉",
                                            fontSize = 72.sp,
                                            color = if (isLocked) Color.Gray.copy(alpha = 0.12f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            if (isLocked) {
                                                viewModel.triggerGuestPromo()
                                            } else {
                                                activeVideoUrl = video.videoId
                                                activeVideoTitle = video.title
                                            }
                                        },
                                        colors = IconButtonDefaults.iconButtonColors(containerColor = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)),
                                        modifier = Modifier.size(54.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isLocked) Icons.Default.Lock else Icons.Default.PlayArrow,
                                            contentDescription = if (isLocked) "Locked" else "Play",
                                            tint = Color.White,
                                            modifier = Modifier.size(if (isLocked) 24.dp else 32.dp)
                                        )
                                    }
                                }
                            }

                            // Details
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = if (isLocked) {
                                        if (isEng) "LOCKED (DEVOTEE ONLY)" else "ताला लगा है (केवल साधक)"
                                    } else {
                                        video.category.uppercase()
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = video.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isLocked) Color.Gray else MaterialTheme.colorScheme.secondary
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = video.description,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        lineHeight = 16.sp
                                    )
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val context = androidx.compose.ui.platform.LocalContext.current
                                    androidx.compose.material3.IconButton(
                                        onClick = {
                                            val videoUrl = "https://www.youtube.com/watch?v=${video.videoId}"
                                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                            val clip = android.content.ClipData.newPlainText("Spiritual Video", videoUrl)
                                            clipboard.setPrimaryClip(clip)
                                            
                                            val sendIntent = android.content.Intent().apply {
                                                action = android.content.Intent.ACTION_SEND
                                                putExtra(android.content.Intent.EXTRA_TEXT, "Watch this beautiful spiritual video: '${video.title}' - $videoUrl")
                                                type = "text/plain"
                                            }
                                            val shareIntent = android.content.Intent.createChooser(sendIntent, null)
                                            context.startActivity(shareIntent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share Video",
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
                visible = showVideoScrollToTop,
                testTag = "scroll_to_top_videos",
                onClick = {
                    videoCoroutineScope.launch {
                        videoListState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomStart)
            )
            }
        }
    }
}


// --- PROFILE / ACCOUNT / LOGIN SCREEN ---
