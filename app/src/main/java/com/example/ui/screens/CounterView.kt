package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.Canvas
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.components.SacredCard

// NEW POLISHED COUNTER VIEW
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterView(isEng: Boolean, viewModel: SadhakViewModel) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val prefs = remember { context.getSharedPreferences("mantra_counter_prefs", android.content.Context.MODE_PRIVATE) }

    // State Persistence
    var selectedMantraId by remember { mutableStateOf(prefs.getString("selected_mantra_id", "gayatri") ?: "gayatri") }
    var customMantraText by remember { mutableStateOf(prefs.getString("custom_mantra_text", "") ?: "") }
    var beadsPerMala by remember { mutableStateOf(prefs.getInt("beads_per_mala", 108)) }
    var targetMalas by remember { mutableStateOf(prefs.getInt("target_malas", 1)) }
    var count by remember { mutableStateOf(prefs.getInt("current_count", 0)) }
    var streak by remember { mutableStateOf(prefs.getInt("streak", 0)) }
    var lastCompletedDate by remember { mutableStateOf(prefs.getString("last_completed_date", "") ?: "") }
    var vibrateOnTap by remember { mutableStateOf(prefs.getBoolean("vibrate_on_tap", true)) }
    var vibrateOnMala by remember { mutableStateOf(prefs.getBoolean("vibrate_on_mala", true)) }
    var showCelebrationDialog by remember { mutableStateOf(false) }

    // Timer state variables
    var modeSelected by remember { mutableStateOf("Counter") } // "Counter" or "Timer"
    var timerRunning by remember { mutableStateOf(false) }
    var timerType by remember { mutableStateOf("Duration") } // "Duration" or "Count"
    var durationSecondsTotal by remember { mutableStateOf(300) } // 5 mins
    var durationSecondsLeft by remember { mutableStateOf(300) }
    var targetChantCount by remember { mutableStateOf(108) }
    var currentChantCount by remember { mutableStateOf(0) }
    var chantIntervalMs by remember { mutableStateOf(3000L) } // 3 seconds per chant

    // Timer countdown/pacing logic
    LaunchedEffect(timerRunning, timerType, durationSecondsLeft, currentChantCount) {
        if (timerRunning) {
            if (timerType == "Duration") {
                if (durationSecondsLeft > 0) {
                    kotlinx.coroutines.delay(1000)
                    durationSecondsLeft -= 1
                    
                    // Auto-increment chant count based on pacing (e.g. every 3 seconds)
                    if ((durationSecondsTotal - durationSecondsLeft) % (chantIntervalMs / 1000L).toInt() == 0) {
                        currentChantCount += 1
                    }
                } else {
                    // Completed!
                    timerRunning = false
                    com.example.ui.screens.AmbientSoundPlayer.playSound(1, 0.8f) // Ring Majestic Temple Bell!
                    viewModel.logKarmaDeed("Completed Japa Timer of ${durationSecondsTotal / 60} mins: $currentChantCount chants")
                    showCelebrationDialog = true
                }
            } else { // "Count" mode
                if (currentChantCount < targetChantCount) {
                    kotlinx.coroutines.delay(chantIntervalMs)
                    currentChantCount += 1
                } else {
                    // Completed!
                    timerRunning = false
                    com.example.ui.screens.AmbientSoundPlayer.playSound(1, 0.8f) // Ring Majestic Temple Bell!
                    viewModel.logKarmaDeed("Completed Japa Timer of $targetChantCount chants")
                    showCelebrationDialog = true
                }
            }
        }
    }

    // Helper date strings
    val sdf = remember { java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()) }
    val todayStr = remember { sdf.format(java.util.Date()) }
    val yesterdayStr = remember {
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DATE, -1)
        sdf.format(cal.time)
    }

    // Calculated Daily Target (chants)
    val dailyTargetChants = beadsPerMala * targetMalas

    // Mantra Data Lists
    data class MantraItem(val id: String, val nameEn: String, val nameHi: String, val verse: String)
    val mantras = listOf(
        MantraItem("gayatri", "Gayatri Mantra", "गायत्री मंत्र", "ॐ भूर्भुवः स्वः तत्सवितुर्वरेण्यं भर्गो देवस्य धीमहि धियो यो नः प्रचोदयात्॥"),
        MantraItem("mrityunjaya", "Maha Mrityunjaya", "महामृत्युंजय मंत्र", "ॐ त्र्यम्बकं यजामहे सुगन्धिं पुष्टिवर्धनम्। उर्वारुकमिव बन्धनान्मृत्योर्मुक्षीय मामृतात्॥"),
        MantraItem("harekrishna", "Hare Krishna Maha Mantra", "हरे कृष्ण महामंत्र", "हरे कृष्ण हरे कृष्ण कृष्ण कृष्ण हरे हरे। हरे राम हरे राम राम राम हरे हरे॥"),
        MantraItem("omnamahshivaya", "Om Namah Shivaya", "ॐ नमः शिवाय", "ॐ नमः शिवाय॥"),
        MantraItem("omnamobhagavate", "Om Namo Bhagavate", "ॐ नमो भगवते वासुदेवाय", "ॐ नमो भगवते वासुदेवाय॥"),
        MantraItem("custom", "Custom Mantra", "व्यक्तिगत मंत्र", "")
    )

    val activeMantra = mantras.find { it.id == selectedMantraId } ?: mantras[0]
    var expanded by remember { mutableStateOf(false) }

    // Save helpers
    fun saveInt(key: String, value: Int) { prefs.edit().putInt(key, value).apply() }
    fun saveString(key: String, value: String) { prefs.edit().putString(key, value).apply() }
    fun saveBoolean(key: String, value: Boolean) { prefs.edit().putBoolean(key, value).apply() }

    // Handle Bead Tapping
    fun incrementCount() {
        val nextCount = count + 1
        count = nextCount
        saveInt("current_count", nextCount)

        // Haptic feedback on tap
        if (vibrateOnTap) {
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
        }

        // Mala Completion Haptic (every beadsPerMala chants)
        if (vibrateOnMala && nextCount % beadsPerMala == 0) {
            // Trigger multi-pulse feel
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
        }

        // Daily Target Achievement check
        if (nextCount == dailyTargetChants) {
            if (lastCompletedDate != todayStr) {
                val newStreak = if (lastCompletedDate == yesterdayStr) streak + 1 else 1
                streak = newStreak
                saveInt("streak", newStreak)
                lastCompletedDate = todayStr
                saveString("last_completed_date", todayStr)
            }
            showCelebrationDialog = true
            // Special success vibration pattern
            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
        }
    }

    // Reset current round
    fun resetCurrentCount() {
        count = 0
        saveInt("current_count", 0)
    }

    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.lazy.LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("mantra_counter_view"),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Streak & Target Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(com.example.ui.theme.SoftGoldBg)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEng) "Daily Japa Sadhana" else "दैनिक जाप साधना",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isEng) "Target: $targetMalas Malas ($dailyTargetChants Chants)" else "लक्ष्य: $targetMalas माला ($dailyTargetChants जाप)",
                            fontSize = 12.sp,
                            color = com.example.ui.theme.Terracotta,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Streak Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(com.example.ui.theme.Terracotta)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🔥 ",
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEng) "$streak Days" else "$streak दिन",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Japa Counter vs Japa Timer mode selector tab row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val modes = listOf("Counter", "Timer")
                    modes.forEach { mode ->
                        val label = when (mode) {
                            "Counter" -> if (isEng) "Japa Counter" else "जाप काउंटर"
                            else -> if (isEng) "Japa Timer" else "जाप टाइमर"
                        }
                        val isSelected = modeSelected == mode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                                .clickable { modeSelected = mode }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            // 5. Options: Japa Customization Options (SHIFTED UPPER SIDE)
            item {
                Text(
                    text = if (isEng) "Mantra Sadhana Options" else "जाप साधना विकल्प",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // Dropdown List for Mantra Selection
            item {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        value = if (isEng) activeMantra.nameEn else activeMantra.nameHi,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(if (isEng) "Select Mantra" else "मंत्र चुनें") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        mantras.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(if (isEng) item.nameEn else item.nameHi) },
                                onClick = {
                                    selectedMantraId = item.id
                                    saveString("selected_mantra_id", item.id)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 2. Active Mantra Card Display
            item {
                SacredCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isEng) activeMantra.nameEn else activeMantra.nameHi,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        if (selectedMantraId == "custom") {
                            OutlinedTextField(
                                value = customMantraText,
                                onValueChange = {
                                    customMantraText = it
                                    saveString("custom_mantra_text", it)
                                },
                                placeholder = { Text(text = if (isEng) "Type your custom mantra here..." else "अपना मंत्र यहाँ लिखें...") },
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.primary)
                            )
                        } else {
                            Text(
                                text = activeMantra.verse,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    lineHeight = 20.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }

            if (modeSelected == "Counter") {
                // 3. Circular Progress Ring & Tapping Target
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .size(240.dp)
                    ) {
                        // Outer Glow ring via Canvas
                        val primaryColor = MaterialTheme.colorScheme.primary
                        val baseColor = MaterialTheme.colorScheme.outlineVariant
                        Canvas(modifier = Modifier.size(220.dp)) {
                            val strokeWidth = 14.dp.toPx()
                            // track circle
                            drawCircle(
                                color = baseColor.copy(alpha = 0.4f),
                                radius = size.width / 2 - strokeWidth / 2,
                                style = Stroke(width = strokeWidth)
                            )
                            // active progress circle
                            val sweep = if (dailyTargetChants > 0) {
                                (count.toFloat() / dailyTargetChants.toFloat() * 360f).coerceAtMost(360f)
                            } else 0f
                            drawArc(
                                color = primaryColor,
                                startAngle = -90f,
                                sweepAngle = sweep,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }

                        // Inner Interactive Circle Button
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                            MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                )
                                .clickable { incrementCount() }
                                .testTag("bead_tap_target"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = count.toString(),
                                    style = MaterialTheme.typography.displayLarge.copy(
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                                
                                // Mala Rounds Completed Subtitle
                                val currentMalasCompleted = count / beadsPerMala
                                Text(
                                    text = if (isEng) "Mala Completed: $currentMalasCompleted" else "माला पूर्ण: $currentMalasCompleted",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (isEng) "TAP TO COUNT" else "टैप करें",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = com.example.ui.theme.Terracotta,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }

                // 4. Reset & Quick Options Controls
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Reset Button
                        OutlinedButton(
                            onClick = { resetCurrentCount() },
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = if (isEng) "Reset Counter" else "काउंटर रीसेट करें", fontSize = 12.sp)
                        }
                    }
                }

                // 7. Mala Size & Daily Target Selection Rows
                item {
                    SacredCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Beads per Mala Configuration
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEng) "Beads per Mala" else "माला के मनके (मनका)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf(108, 54, 27, 1).forEach { countOpt ->
                                        val activeOpt = beadsPerMala == countOpt
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (activeOpt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                                .border(1.dp, if (activeOpt) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                                .clickable {
                                                    beadsPerMala = countOpt
                                                    saveInt("beads_per_mala", countOpt)
                                                }
                                                .padding(horizontal = 10.dp, vertical = 6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = countOpt.toString(),
                                                color = if (activeOpt) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }

                            // Daily Target Mala Rounds configuration
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = if (isEng) "Daily Target Rounds" else "दैनिक जाप माला फेरे",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                    Text(
                                        text = if (isEng) "Goal: $dailyTargetChants total chants" else "कुल जाप लक्ष्य: $dailyTargetChants",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            if (targetMalas > 1) {
                                                targetMalas -= 1
                                                saveInt("target_malas", targetMalas)
                                            }
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                    ) {
                                        Text(text = "-", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }

                                    Text(
                                        text = targetMalas.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.secondary
                                    )

                                    IconButton(
                                        onClick = {
                                            targetMalas += 1
                                            saveInt("target_malas", targetMalas)
                                        },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                                    ) {
                                        Text(text = "+", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // Timer Progress Ring & Controls
                item {
                    val progressValue = if (timerType == "Duration") {
                        if (durationSecondsTotal > 0) durationSecondsLeft.toFloat() / durationSecondsTotal.toFloat() else 0f
                    } else {
                        if (targetChantCount > 0) currentChantCount.toFloat() / targetChantCount.toFloat() else 0f
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .size(240.dp)
                    ) {
                        // Progress ring canvas
                        val primaryColor = MaterialTheme.colorScheme.primary
                        val baseColor = MaterialTheme.colorScheme.outlineVariant
                        Canvas(modifier = Modifier.size(220.dp)) {
                            val strokeWidth = 14.dp.toPx()
                            // Track
                            drawCircle(
                                color = baseColor.copy(alpha = 0.4f),
                                radius = size.width / 2 - strokeWidth / 2,
                                style = Stroke(width = strokeWidth)
                            )
                            // Progress
                            drawArc(
                                color = primaryColor,
                                startAngle = -90f,
                                sweepAngle = progressValue * 360f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }

                        // Inner click to start/pause, long-press to reset
                        Box(
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                                            MaterialTheme.colorScheme.primaryContainer
                                        )
                                    )
                                )
                                .clickable {
                                    timerRunning = !timerRunning
                                }
                                .testTag("timer_bead_tap_target"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                if (timerType == "Duration") {
                                    val mins = durationSecondsLeft / 60
                                    val secs = durationSecondsLeft % 60
                                    Text(
                                        text = String.format("%02d:%02d", mins, secs),
                                        style = MaterialTheme.typography.displayLarge.copy(
                                            fontSize = 42.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = if (isEng) "Chants: $currentChantCount" else "जाप संख्या: $currentChantCount",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                } else {
                                    Text(
                                        text = "$currentChantCount/$targetChantCount",
                                        style = MaterialTheme.typography.displayLarge.copy(
                                            fontSize = 42.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = if (isEng) "Target Chants" else "जाप लक्ष्य",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (timerRunning) {
                                        if (isEng) "TAP TO PAUSE" else "रोकने के लिए दबाएं"
                                    } else {
                                        if (isEng) "TAP TO START" else "शुरू करने के लिए दबाएं"
                                    },
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = com.example.ui.theme.Terracotta,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }

                // Action buttons for Timer
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { timerRunning = !timerRunning },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = if (timerRunning) (if (isEng) "Pause" else "रोकें") else (if (isEng) "Start Japa" else "जाप शुरू करें"), color = Color.White)
                        }

                        OutlinedButton(
                            onClick = {
                                timerRunning = false
                                durationSecondsLeft = durationSecondsTotal
                                currentChantCount = 0
                            },
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = if (isEng) "Reset Timer" else "टाइमर रीसेट करें", fontSize = 12.sp)
                        }
                    }
                }

                // Timer Configurations card
                item {
                    SacredCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Timer Type Selector (Duration vs Count)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (isEng) "Timer Mode" else "टाइमर मोड",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf("Duration", "Count").forEach { typeOpt ->
                                        val isSelected = timerType == typeOpt
                                        val label = when (typeOpt) {
                                            "Duration" -> if (isEng) "Duration" else "समय"
                                            else -> if (isEng) "Count" else "जाप संख्या"
                                        }
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                                .clickable {
                                                    timerType = typeOpt
                                                    timerRunning = false
                                                    durationSecondsLeft = durationSecondsTotal
                                                    currentChantCount = 0
                                                }
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(text = label, color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }

                            // Presets depending on timerType
                            Column {
                                Text(
                                    text = if (timerType == "Duration") {
                                        if (isEng) "Select Quick Duration Preset" else "त्वरित समय विकल्प चुनें"
                                    } else {
                                        if (isEng) "Select Target Count Preset" else "त्वरित जाप संख्या विकल्प चुनें"
                                    },
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    if (timerType == "Duration") {
                                        val minsPresets = listOf(1, 3, 5, 10)
                                        minsPresets.forEach { mins ->
                                            OutlinedButton(
                                                onClick = {
                                                    durationSecondsTotal = mins * 60
                                                    durationSecondsLeft = mins * 60
                                                    currentChantCount = 0
                                                },
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    containerColor = if (durationSecondsTotal == mins * 60) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent
                                                ),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(text = "$mins ${if (isEng) "Min" else "मिनट"}", fontSize = 11.sp)
                                            }
                                        }
                                    } else {
                                        val countPresets = listOf(27, 54, 108, 1008)
                                        countPresets.forEach { preset ->
                                            OutlinedButton(
                                                onClick = {
                                                    targetChantCount = preset
                                                    currentChantCount = 0
                                                },
                                                colors = ButtonDefaults.outlinedButtonColors(
                                                    containerColor = if (targetChantCount == preset) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent
                                                ),
                                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text(text = preset.toString(), fontSize = 11.sp)
                                            }
                                        }
                                    }
                                }
                            }

                            // Pacing interval speed slider
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = if (isEng) "Auto-Chant Interval: ${(chantIntervalMs / 1000f)} sec" else "स्वचालित जाप अंतराल: ${(chantIntervalMs / 1000f)} सेकंड",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Slider(
                                    value = chantIntervalMs.toFloat() / 1000f,
                                    onValueChange = { chantIntervalMs = (it * 1000f).toLong() },
                                    valueRange = 1.5f..10.0f,
                                    steps = 17,
                                    colors = SliderDefaults.colors(
                                        thumbColor = MaterialTheme.colorScheme.primary,
                                        activeTrackColor = MaterialTheme.colorScheme.primary,
                                        inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }

            // 8. Sound & Vibration Feedback
            item {
                SacredCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Tap Vibration
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEng) "Vibrate on each bead tap" else "प्रत्येक मनके के जाप पर कंपन",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Switch(
                                checked = vibrateOnTap,
                                onCheckedChange = {
                                    vibrateOnTap = it
                                    saveBoolean("vibrate_on_tap", it)
                                }
                            )
                        }

                        // Mala completion vibration
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.SelfImprovement,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEng) "Vibrate on complete Mala round" else "एक पूरी माला होने पर विशेष कंपन",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Switch(
                                checked = vibrateOnMala,
                                onCheckedChange = {
                                    vibrateOnMala = it
                                    saveBoolean("vibrate_on_mala", it)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Global Scroll To Top Button
        if (listState.firstVisibleItemIndex > 2) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                com.example.ui.components.ScrollToTopButton(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    // Celebratory Success Target Dialogue
    if (showCelebrationDialog) {
        AlertDialog(
            onDismissRequest = { showCelebrationDialog = false },
            title = {
                Text(
                    text = if (isEng) "Target Achieved! 🌟" else "अनुष्ठान पूर्ण! 🌟",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🌸 🌸 🌸",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(
                        text = if (isEng) {
                            "Congratulations! You have completed your daily target of $targetMalas Japa Malas ($dailyTargetChants chants) of ${if (selectedMantraId == "custom") "your custom mantra" else activeMantra.nameEn}.\n\nYour streak is now $streak Days!"
                        } else {
                            "बधाई हो! आपने ${if (selectedMantraId == "custom") "अपने व्यक्तिगत मंत्र" else activeMantra.nameHi} के $targetMalas माला ($dailyTargetChants जाप) का दैनिक अनुष्ठान पूरा कर लिया है।\n\nआपकी अनवरत साधना अब $streak दिनों की हो गई है!"
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showCelebrationDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (isEng) "Pranam (Close)" else "प्रणाम (बंद करें)", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            modifier = Modifier.testTag("celebration_dialog")
        )
    }
}
