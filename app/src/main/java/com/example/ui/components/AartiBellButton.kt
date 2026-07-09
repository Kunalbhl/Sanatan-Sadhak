package com.example.ui.components

import android.media.MediaPlayer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.R // This will need to be imported once the file is added

@Composable
fun AartiBellButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    IconButton(
        onClick = {
            // Placeholder for bell sound. 
            // The user needs to add "temple_bell.mp3" to res/raw
            // val mediaPlayer = MediaPlayer.create(context, R.raw.temple_bell)
            // mediaPlayer.start()
            // mediaPlayer.setOnCompletionListener { it.release() }
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
