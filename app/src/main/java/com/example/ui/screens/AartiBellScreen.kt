package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SacredHeader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AartiBellScreen(viewModel: com.example.ui.viewmodel.SadhakViewModel, isEng: Boolean, onBack: () -> Unit) {
    val bellCount by viewModel.currentSessionAartiCount.collectAsState()
    val haptic = LocalHapticFeedback.current
    val aartiLogs by viewModel.aartiSessionLogs.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text(text = if (isEng) "Sacred Aarti Bell" else "पवित्र आरती घंटी", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "${if (isEng) "Count" else "गिनती"}: $bellCount", style = MaterialTheme.typography.displayMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .clickable {
                    viewModel.incrementAartiBell()
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    playTempleBellSound()
                },
            contentAlignment = Alignment.Center
        ) {
            Text("🔔", fontSize = 64.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (aartiLogs.isNotEmpty()) {
            Text(text = if (isEng) "Aarti Bell Log" else "आरती घंटी लॉग", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.foundation.lazy.LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                items(aartiLogs.size) { index ->
                    Text(
                        text = "${index + 1}. ${aartiLogs[index]}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
