package com.example.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager

/**
 * Keeps the optional Om background chant playing while the setting is on.
 * - Starts it when enabled, and starts it again once other audio (a mantra,
 *   a prayer) has finished and nothing else is loaded.
 * - Changes volume in place instead of restarting the track.
 * - Stops it when disabled or when this composable leaves the screen.
 */
@Composable
fun GlobalChantPlayer(enabled: Boolean, volume: Float) {
    val context = LocalContext.current
    val currentTrack by SadhakAudioManager.currentTrack.collectAsState()

    LaunchedEffect(enabled, currentTrack) {
        if (enabled && currentTrack == null) {
            SadhakAudioManager.playTrack(context, AudioTrackId.OM_AMBIENT, isLooping = true, volume = volume)
        } else if (!enabled && currentTrack == AudioTrackId.OM_AMBIENT) {
            SadhakAudioManager.stop()
        }
    }

    LaunchedEffect(volume) {
        if (currentTrack == AudioTrackId.OM_AMBIENT) SadhakAudioManager.setVolume(volume)
    }

    DisposableEffect(Unit) {
        onDispose {
            if (SadhakAudioManager.currentTrack.value == AudioTrackId.OM_AMBIENT) {
                SadhakAudioManager.stop()
            }
        }
    }
}
