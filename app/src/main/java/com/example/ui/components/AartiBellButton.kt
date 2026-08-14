package com.example.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager

@Composable
fun AartiBellButton(modifier: Modifier = Modifier) {
    IconButton(
        onClick = {
            SadhakAudioManager.playOneShot(AudioTrackId.TEMPLE_BELL)
        },
        modifier = modifier.size(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.NotificationsActive,
            contentDescription = "Aarti Bell",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
