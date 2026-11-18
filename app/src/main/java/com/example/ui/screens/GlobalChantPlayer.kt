package com.example.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.ui.audio.SadhakAudioManager

/**
 * Background Om chant. Starts straight away when the setting is on, with no player UI.
 * It pauses while the main media player is playing something and resumes afterwards.
 */
@Composable
fun GlobalChantPlayer(enabled: Boolean, volume: Float) {
    val context = LocalContext.current
    val currentTrack by SadhakAudioManager.currentTrack.collectAsState()
    val soundOn by SadhakAudioManager.soundEnabled.collectAsState()

    LaunchedEffect(enabled, currentTrack, soundOn) {
        if (enabled && soundOn && currentTrack == null) {
            SadhakAudioManager.startBgChant(context, volume)
        } else if (enabled && currentTrack != null) {
            SadhakAudioManager.pauseBgChant()
        } else {
            SadhakAudioManager.stopBgChant()
        }
    }

    LaunchedEffect(volume) { SadhakAudioManager.setBgChantVolume(volume) }

    DisposableEffect(Unit) { onDispose { SadhakAudioManager.stopBgChant() } }
}
