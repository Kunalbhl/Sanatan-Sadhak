package com.example.ui.screens




import kotlinx.coroutines.launch
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.Icons

import androidx.compose.ui.Alignment

import androidx.compose.runtime.setValue

import androidx.compose.foundation.clickable

import androidx.compose.material.icons.filled.Info

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.draw.shadow

import androidx.compose.ui.text.style.TextAlign

import com.example.ui.theme.SadhakThemeColors
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.*

import androidx.compose.foundation.shape.RoundedCornerShape

import com.example.ui.viewmodel.SadhakViewModel

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.border

import androidx.compose.ui.Modifier

import androidx.compose.foundation.lazy.items

import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.filled.PlayArrow

import androidx.compose.ui.draw.clip

import androidx.compose.material.icons.filled.Festival

import androidx.compose.material.icons.filled.MenuBook

import androidx.compose.material.icons.filled.Warning

import androidx.compose.material.icons.filled.ArrowForward

import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.ui.graphics.Color

import com.example.data.ChatMessage

import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.material.icons.filled.Forum

import androidx.compose.material.icons.filled.AutoAwesome

import androidx.compose.material.icons.filled.TempleHindu

import androidx.compose.runtime.getValue

import androidx.compose.foundation.background

import androidx.compose.material.icons.filled.SelfImprovement

import androidx.compose.material3.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccessDeniedScreen(viewModel: SadhakViewModel) {
    val isEng by viewModel.isEnglish.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Default.Warning, contentDescription = "Denied", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = if (isEng) "Access Denied" else "पहुंच अस्वीकृत", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, fontSize = 20.sp)
        Text(text = if (isEng) "You do not have permission to view this page." else "आपको इस पृष्ठ को देखने की अनुमति नहीं है।", color = MaterialTheme.colorScheme.onSurface)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: SadhakViewModel, aiViewModel: com.example.ui.viewmodel.AiMitraViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val isEng by viewModel.isEnglish.collectAsState()
    val messages by aiViewModel.chatMessages.collectAsState()
    val isLoading by aiViewModel.isChatLoading.collectAsState()
    var currentInput by remember { mutableStateOf("") }
    val userRole by viewModel.userRole.collectAsState()
    
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember { androidx.compose.runtime.derivedStateOf { listState.firstVisibleItemIndex > 2 } }

    val quickAsks = listOf(
        "What does today's Panchang mean for me?",
        "Explain Karma simply",
        "Tell me about Mahadev",
        "What is a shloka from Gita Chapter 2?",
        "How do I start a daily puja?",
        "Story of Sindari ke Balaji"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            // Chat History / Main Content Area
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            // Initial Greeting and Cards (only show if no messages or maybe keep at top?)
            if (messages.isEmpty()) {
                item {
                    Text(
                        text = if (isEng) "Pranam Devotee! May your day be blessed with peace and spiritual growth." else "प्रणाम भक्त! आपका दिन शांति और आध्यात्मिक विकास से भरा हो।",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Guidance Note",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEng) "Sadhak Mitra provides AI spiritual guidance. For authoritative Vedic rituals, consult traditional acharyas & scriptures." 
                                else "साधक मित्र AI-संचालित आध्यात्मिक मार्गदर्शन प्रदान करता है। प्रामाणिक वैदिक विधियों हेतु पारंपरिक आचार्यों एवं मूल ग्रंथों का परामर्श लें।",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

                    // Quick Ask Row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        items(quickAsks) { ask ->
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surface,
                                shadowElevation = 2.dp,
                                modifier = Modifier.clickable { aiViewModel.sendMessage(ask, viewModel.userRole.value) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(ask, fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                    }

                    // Category Cards 2-column Grid
                    val categories = listOf(
                        Pair("Mantras & Aarti", Icons.Default.PlayArrow to "Explore Mantras"),
                        Pair("Scriptures", Icons.Default.MenuBook to "Tell me about Gita"),
                        Pair("Deities & Temples", Icons.Default.TempleHindu to "Tell me about Mahadev"),
                        Pair("Festivals & Rituals", Icons.Default.Festival to "How do I start a daily puja?"),
                        Pair("Karma & Dharma", Icons.Default.SelfImprovement to "Explain Karma simply"),
                        Pair("Community Discussions", Icons.Default.Forum to "What are people discussing?")
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (i in categories.indices step 2) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                val cat1 = categories[i]
                                CategoryCard(cat1.first, cat1.second.first, modifier = Modifier.weight(1f)) {
                                    aiViewModel.sendMessage(cat1.second.second, viewModel.userRole.value)
                                }
                                if (i + 1 < categories.size) {
                                    val cat2 = categories[i + 1]
                                    CategoryCard(cat2.first, cat2.second.first, modifier = Modifier.weight(1f)) {
                                        aiViewModel.sendMessage(cat2.second.second, viewModel.userRole.value)
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            } else {
                items(messages) { msg ->
                    ChatBubble(msg, onChipClick = { target ->
                        // Navigate or handle based on chip target
                        viewModel.setScreen(target)
                    }, onFollowUpClick = { text ->
                        aiViewModel.sendMessage(text, viewModel.userRole.value)
                    })
                }
                
                if (isLoading) {
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
            } // Close LazyColumn

            // Input Area
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    val guestQuestionCount by aiViewModel.guestQuestionCount.collectAsState()
                    if (userRole == "Guest") {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha=0.1f)
                            ) {
                                Text(
                                    text = if (isEng) "Questions remaining: ${10 - guestQuestionCount}" else "शेष प्रश्न: ${10 - guestQuestionCount}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.Info, contentDescription = "Info", tint = SadhakThemeColors.textSecondary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isEng) "Helps keep app free" else "ऐप को निःशुल्क रखने में सहायक", fontSize = 10.sp, color = SadhakThemeColors.textSecondary)
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = currentInput,
                            onValueChange = { currentInput = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text(if (isEng) "Ask Sadhak Mitra..." else "साधक मित्र से पूछें...", fontSize = 14.sp) },
                            shape = RoundedCornerShape(24.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        FloatingActionButton(
                            onClick = {
                                if (currentInput.isNotBlank()) {
                                    aiViewModel.sendMessage(currentInput, viewModel.userRole.value)
                                    currentInput = ""
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = SadhakThemeColors.surface,
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.ArrowForward, contentDescription = if (isEng) "Send" else "भेजें")
                        }
                    }
                }
            }
        }

        com.example.ui.screens.ScrollToTopButton(
            visible = showScrollToTop,
            testTag = "scroll_to_top_chat",
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 100.dp, end = 24.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary, textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBubble(msg: ChatMessage, onChipClick: (String) -> Unit, onFollowUpClick: (String) -> Unit) {
    val isUser = msg.sender == "user"
    
    // Parse the message content
    // Text is everything not in [CHIP:...] or [FOLLOWUP:...]
    val chipRegex = Regex("\\[CHIP:(.*?):([a-zA-Z]+)\\]")
    val followupRegex = Regex("\\[FOLLOWUP:(.*?)\\]")
    
    val mainText = msg.content
        .replace(chipRegex, "")
        .replace(followupRegex, "")
        .trim()
        
    val chips = chipRegex.findAll(msg.content).map { it.groupValues[1] to it.groupValues[2] }.toList()
    val followups = followupRegex.findAll(msg.content).map { it.groupValues[1] }.toList()

    if (isUser) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Surface(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp),
                color = SadhakThemeColors.secondaryContainer, // Soft cream
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Text(mainText, modifier = Modifier.padding(12.dp), color = SadhakThemeColors.secondary, fontSize = 14.sp)
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(SadhakThemeColors.accent, CircleShape)
                    .border(1.dp, SadhakThemeColors.secondary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("ॐ", color = SadhakThemeColors.secondary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            
            Column {
                Surface(
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                    color = SadhakThemeColors.surface,
                    shadowElevation = 2.dp,
                    modifier = Modifier.widthIn(max = 280.dp)
                ) {
                    Text(mainText, modifier = Modifier.padding(12.dp), color = SadhakThemeColors.textPrimary, fontSize = 14.sp, lineHeight = 20.sp)
                }
                
                if (chips.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    chips.forEach { (label, target) ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SadhakThemeColors.secondary.copy(alpha=0.05f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.secondary.copy(alpha=0.2f)),
                            modifier = Modifier.padding(bottom = 6.dp).clickable { onChipClick(target) }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                                Text(label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SadhakThemeColors.secondary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(12.dp), tint = SadhakThemeColors.secondary)
                            }
                        }
                    }
                }
                
                if (followups.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("You might also ask:", fontSize = 10.sp, color = SadhakThemeColors.textSecondary, modifier = Modifier.padding(bottom = 4.dp, start = 4.dp))
                    followups.forEach { followup ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = SadhakThemeColors.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SadhakThemeColors.primary),
                            modifier = Modifier.padding(bottom = 6.dp).clickable { onFollowUpClick(followup) }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                                Text(followup, fontSize = 11.sp, color = SadhakThemeColors.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SankalpaJournalView(viewModel: SadhakViewModel, isEnglish: Boolean) {
    val logs by viewModel.sankalpaLogs.collectAsState()
    var intentionInput by remember { mutableStateOf("") }
    var noteInput by remember { mutableStateOf("") }
    var progressInput by remember { mutableStateOf(50f) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (isEnglish) "Log New Sankalpa" else "नया संकल्प दर्ज करें",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = intentionInput,
                    onValueChange = { intentionInput = it },
                    label = { Text(if (isEnglish) "Spiritual Intention" else "आध्यात्मिक संकल्प") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = noteInput,
                    onValueChange = { noteInput = it },
                    label = { Text(if (isEnglish) "Progress Note" else "प्रगति नोट") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (isEnglish) "Commitment Level: ${progressInput.toInt()}%" else "प्रतिबद्धता स्तर: ${progressInput.toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Slider(
                    value = progressInput,
                    onValueChange = { progressInput = it },
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.primary,
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (intentionInput.isNotEmpty() && noteInput.isNotEmpty()) {
                            val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                            val dateStr = sdf.format(java.util.Date())
                            viewModel.addSankalpaLog(intentionInput, noteInput, progressInput.toInt(), dateStr)
                            intentionInput = ""
                            noteInput = ""
                            progressInput = 50f
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(if (isEnglish) "Save Entry" else "सहेजें", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        if (logs.isNotEmpty()) {
            Text(
                text = if (isEnglish) "Sankalpa Growth Chart" else "संकल्प प्रगति चार्ट",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // A simple visual representation of the progress logs
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    logs.take(7).reversed().forEach { log ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height((log.progressValue.coerceIn(10, 100)).dp)
                                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = log.date.takeLast(5),
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.heightIn(max = 400.dp)) {
            items(logs) { log ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = log.date, fontSize = 12.sp, color = SadhakThemeColors.textSecondary)
                            Text(text = "${log.progressValue}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = log.intention, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = log.progressNote, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
