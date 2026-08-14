package com.example.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager

@Composable
fun GlobalChantPlayer(enabled: Boolean, volume: Float) {
    val context = LocalContext.current

    DisposableEffect(enabled, volume) {
        if (enabled) {
            SadhakAudioManager.playTrack(context, AudioTrackId.OM_AMBIENT, isLooping = true, volume = volume)
        } else {
            if (SadhakAudioManager.currentTrack.value == AudioTrackId.OM_AMBIENT) {
                SadhakAudioManager.stop()
            }
        }

        onDispose {
            if (SadhakAudioManager.currentTrack.value == AudioTrackId.OM_AMBIENT) {
                SadhakAudioManager.stop()
            }
        }
    }
}
