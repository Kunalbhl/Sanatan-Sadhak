package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SacredCard
import com.example.ui.theme.SadhakThemeColors
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.viewmodel.DevotionalTrack
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VedicLibraryScreen(viewModel: SadhakViewModel, onBack: () -> Unit) {
    val isEng by viewModel.isEnglish.collectAsState()
    val tracks = com.example.ui.viewmodel.devotionalTracks
    val groupedTracks = tracks.groupBy { it.category }
    var showPhoneticGuide by remember { mutableStateOf<String?>(null) }
    
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 300 }
    }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEng) "Vedic Stotras & Mantras" else "वैदिक स्तोत्र एवं मंत्र",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = if (isEng) "Back" else "वापस")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                groupedTracks.forEach { (category, categoryTracks) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    items(categoryTracks) { track ->
                        SacredCard(
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.clickable { 
                                val index = com.example.ui.viewmodel.devotionalTracks.indexOf(track)
                                viewModel.changeAudioTrack(index)
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isEng) track.titleEn else track.titleHi,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isEng) track.artistEn else track.artistHi,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                if (track.phoneticGuide != null) {
                                    IconButton(onClick = { showPhoneticGuide = track.phoneticGuide }) {
                                        Icon(
                                            Icons.Default.Info,
                                            contentDescription = if (isEng) "Phonetic Guide" else "उच्चारण निर्देश",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = if (isEng) "Play" else "बजाएं",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
            
            ScrollToTopButton(
                visible = showScrollToTop,
                testTag = "scroll_to_top_vedic_library",
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
            )
        }
    }
    
    showPhoneticGuide?.let { guide ->
        AlertDialog(
            onDismissRequest = { showPhoneticGuide = null },
            title = { Text(if (isEng) "Phonetic Pronunciation Guide" else "शुद्ध वैदिक उच्चारण निर्देश") },
            text = { Text(guide, lineHeight = 20.sp) },
            confirmButton = {
                TextButton(onClick = { showPhoneticGuide = null }) {
                    Text(if (isEng) "Close" else "बंद करें")
                }
            }
        )
    }
}
