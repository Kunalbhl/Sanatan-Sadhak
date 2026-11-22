package com.example.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

/**
 * Centralized Haptic Feedback Manager for Sanatan Sadhak.
 * Provides subtle, spiritually pleasant tactile feedback for devotional actions,
 * card taps, button presses, japa beads, and bell rings.
 */
object HapticFeedbackManager {

    enum class FeedbackType {
        LIGHT_TOUCH,
        STANDARD_CLICK,
        DEVOTIONAL_TAP,
        JAPA_BEAD,
        BELL_RING,
        MALA_COMPLETE,
        SUCCESS
    }

    /**
     * Perform haptic feedback using Compose's native HapticFeedback interface
     */
    fun performHaptic(haptic: HapticFeedback?, type: FeedbackType = FeedbackType.LIGHT_TOUCH) {
        if (haptic == null) return
        try {
            when (type) {
                FeedbackType.LIGHT_TOUCH -> haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                FeedbackType.STANDARD_CLICK -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                FeedbackType.DEVOTIONAL_TAP -> haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                FeedbackType.JAPA_BEAD -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                FeedbackType.BELL_RING -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                FeedbackType.MALA_COMPLETE -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                FeedbackType.SUCCESS -> haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
        } catch (_: Throwable) {
            // Gracefully ignore if platform haptics fail
        }
    }

    /**
     * Perform nuanced hardware vibration feedback using Android Vibrator service
     */
    fun vibrate(context: Context?, type: FeedbackType = FeedbackType.LIGHT_TOUCH) {
        if (context == null) return
        try {
            val vibrator = getVibrator(context) ?: return
            if (!vibrator.hasVibrator()) return

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val effect = when (type) {
                    FeedbackType.LIGHT_TOUCH -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                        } else {
                            VibrationEffect.createOneShot(12, VibrationEffect.DEFAULT_AMPLITUDE)
                        }
                    }
                    FeedbackType.STANDARD_CLICK -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                        } else {
                            VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE)
                        }
                    }
                    FeedbackType.DEVOTIONAL_TAP -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                        } else {
                            VibrationEffect.createOneShot(15, 80)
                        }
                    }
                    FeedbackType.JAPA_BEAD -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                        } else {
                            VibrationEffect.createOneShot(30, 160)
                        }
                    }
                    FeedbackType.BELL_RING -> {
                        // Resonant double pulse mimicking a sacred ghanti reverberation
                        val timings = longArrayOf(0, 25, 40, 35)
                        val amplitudes = intArrayOf(0, 180, 0, 120)
                        VibrationEffect.createWaveform(timings, amplitudes, -1)
                    }
                    FeedbackType.MALA_COMPLETE -> {
                        // About one second of rising pulses when a 108-bead Mala is completed
                        val timings = longArrayOf(0, 220, 80, 260, 80, 360)
                        val amplitudes = intArrayOf(0, 140, 0, 200, 0, 255)
                        VibrationEffect.createWaveform(timings, amplitudes, -1)
                    }
                    FeedbackType.SUCCESS -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
                        } else {
                            val timings = longArrayOf(0, 25, 40, 30)
                            VibrationEffect.createWaveform(timings, -1)
                        }
                    }
                }
                vibrator.vibrate(effect)
            } else {
                @Suppress("DEPRECATION")
                when (type) {
                    FeedbackType.LIGHT_TOUCH -> vibrator.vibrate(12)
                    FeedbackType.STANDARD_CLICK -> vibrator.vibrate(20)
                    FeedbackType.DEVOTIONAL_TAP -> vibrator.vibrate(15)
                    FeedbackType.JAPA_BEAD -> vibrator.vibrate(30)
                    FeedbackType.BELL_RING -> vibrator.vibrate(longArrayOf(0, 25, 40, 35), -1)
                    FeedbackType.MALA_COMPLETE -> vibrator.vibrate(longArrayOf(0, 220, 80, 260, 80, 360), -1)
                    FeedbackType.SUCCESS -> vibrator.vibrate(longArrayOf(0, 25, 40, 30), -1)
                }
            }
        } catch (_: Throwable) {
            // Fail silently on emulators or restricted environments
        }
    }

    private fun getVibrator(context: Context): Vibrator? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vibratorManager?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }
}
