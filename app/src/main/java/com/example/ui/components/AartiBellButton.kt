package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.utils.HapticFeedbackManager

@Composable
fun AartiBellButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var lastTapTime by remember { mutableStateOf(0L) }

    IconButton(
        onClick = {
            val now = System.currentTimeMillis()
            if (now - lastTapTime >= 200L) {
                lastTapTime = now
                HapticFeedbackManager.performHaptic(haptic, HapticFeedbackManager.FeedbackType.BELL_RING)
                HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.BELL_RING)
                SadhakAudioManager.playOneShot(context, AudioTrackId.TEMPLE_BELL)
            }
        },
        modifier = modifier
            .size(48.dp)
            .testTag("aarti_bell_button")
    ) {
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = "Aarti Bell",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
