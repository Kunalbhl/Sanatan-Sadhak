package com.example.ui.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SadhakThemeColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * SadhakTextReader / DevotionalTextReader
 *
 * Centralized, peaceful Text-to-Speech reader engine for Sanatan Sadhak.
 * Used consistently across Home (Daily Shloka, Daily Mantra, Daily Wisdom),
 * Knowledge Hub articles, Spiritual Chants, Prayers, and Scriptures.
 */
object SadhakTextReader {
    private const val TAG = "SadhakTextReader"

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var appContext: Context? = null

    private val _isReading = MutableStateFlow(false)
    val isReading: StateFlow<Boolean> = _isReading.asStateFlow()

    private val _currentlyReadingId = MutableStateFlow<String?>(null)
    val currentlyReadingId: StateFlow<String?> = _currentlyReadingId.asStateFlow()

    private var lastText: String? = null
    private var lastIsEnglish: Boolean = false

    fun init(context: Context) {
        if (isInitialized && tts != null) return
        appContext = context.applicationContext

        tts = TextToSpeech(appContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                isInitialized = true
                configureVoiceDefaults()
                setupProgressListener()
            } else {
                Log.w(TAG, "TextToSpeech initialization failed with status $status")
            }
        }
    }

    private fun configureVoiceDefaults() {
        tts?.let { engine ->
            // Slower speech rate for a calm, devotional, peaceful delivery
            engine.setSpeechRate(0.82f)
            // Slightly deeper/peaceful pitch
            engine.setPitch(0.95f)
        }
    }

    private fun setupProgressListener() {
        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isReading.value = true
                _currentlyReadingId.value = utteranceId
            }

            override fun onDone(utteranceId: String?) {
                _isReading.value = false
                _currentlyReadingId.value = null
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                _isReading.value = false
                _currentlyReadingId.value = null
                Log.w(TAG, "TTS error on utterance $utteranceId")
            }

            override fun onError(utteranceId: String?, errorCode: Int) {
                _isReading.value = false
                _currentlyReadingId.value = null
                Log.w(TAG, "TTS error code $errorCode on utterance $utteranceId")
            }
        })
    }

    /**
     * Read text aloud with peaceful delivery.
     * Automatically stops music audio to prevent overlap.
     */
    fun read(id: String, text: String, isEnglish: Boolean = false) {
        if (text.isBlank()) return

        // Pause / stop music player so voice and music do not clash
        SadhakAudioManager.pause()

        val engine = tts
        if (engine == null || !isInitialized) {
            appContext?.let { init(it) }
            return
        }

        // Set locale based on language
        if (isEnglish) {
            engine.setLanguage(Locale.ENGLISH)
        } else {
            val hindiResult = engine.setLanguage(Locale("hi", "IN"))
            if (hindiResult == TextToSpeech.LANG_MISSING_DATA || hindiResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                engine.setLanguage(Locale("hi"))
            }
        }

        configureVoiceDefaults()

        lastText = text
        lastIsEnglish = isEnglish
        _currentlyReadingId.value = id
        _isReading.value = true

        val params = Bundle().apply {
            putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 1.0f)
        }

        engine.speak(cleanDevotionalText(text), TextToSpeech.QUEUE_FLUSH, params, id)
    }

    /**
     * Toggle read / stop for a given ID.
     */
    fun toggle(id: String, text: String, isEnglish: Boolean = false) {
        if (_currentlyReadingId.value == id && _isReading.value) {
            stop()
        } else {
            read(id, text, isEnglish)
        }
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping TTS", e)
        } finally {
            _isReading.value = false
            _currentlyReadingId.value = null
        }
    }

    fun shutdown() {
        stop()
        try {
            tts?.shutdown()
        } catch (e: Exception) {
            Log.e(TAG, "Error shutting down TTS", e)
        } finally {
            tts = null
            isInitialized = false
        }
    }

    private fun cleanDevotionalText(raw: String): String {
        // Strip markdown-like markers or technical tags if any
        return raw.replace(Regex("\\[.*?\\]"), "")
            .replace(Regex("[#*_`~]"), "")
            .trim()
    }
}

/**
 * Devotional Listen Icon Button component
 */
@Composable
fun DevotionalListenButton(
    id: String,
    text: String,
    isEnglish: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = SadhakThemeColors.sacredPrimary,
    testTag: String = "listen_button_$id"
) {
    val currentlyReadingId by SadhakTextReader.currentlyReadingId.collectAsState()
    val isReading by SadhakTextReader.isReading.collectAsState()

    val isActive = currentlyReadingId == id && isReading

    IconButton(
        onClick = { SadhakTextReader.toggle(id, text, isEnglish) },
        modifier = modifier.testTag(testTag)
    ) {
        Icon(
            imageVector = if (isActive) Icons.Default.Stop else Icons.Default.VolumeUp,
            contentDescription = if (isActive) (if (isEnglish) "Stop Listening" else "सुनना बंद करें") else (if (isEnglish) "Listen Aloud" else "सुनें"),
            tint = if (isActive) SadhakThemeColors.sacredSecondary else tint,
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * Devotional Audio / Text Reader Bar component for reading long text
 */
@Composable
fun DevotionalTextReaderBar(
    id: String,
    title: String,
    textToRead: String,
    isEnglish: Boolean,
    modifier: Modifier = Modifier
) {
    val currentlyReadingId by SadhakTextReader.currentlyReadingId.collectAsState()
    val isReading by SadhakTextReader.isReading.collectAsState()
    val isActive = currentlyReadingId == id && isReading

    DisposableEffect(id) {
        onDispose {
            if (currentlyReadingId == id) {
                SadhakTextReader.stop()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) SadhakThemeColors.sacredPrimary.copy(alpha = 0.12f) else SadhakThemeColors.sacredSurface)
            .border(
                1.dp,
                if (isActive) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredGold.copy(alpha = 0.35f),
                RoundedCornerShape(12.dp)
            )
            .clickable { SadhakTextReader.toggle(id, textToRead, isEnglish) }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isActive) SadhakThemeColors.sacredPrimary else SadhakThemeColors.sacredIconBackground)
                        .border(1.dp, SadhakThemeColors.sacredGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isActive) Icons.Default.Stop else Icons.Default.VolumeUp,
                        contentDescription = if (isActive) "Stop" else "Listen",
                        tint = if (isActive) Color.White else SadhakThemeColors.sacredPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = if (isActive) {
                            if (isEnglish) "Speaking: $title" else "पाठ जारी: $title"
                        } else {
                            if (isEnglish) "Listen to $title" else "$title का पाठ सुनें"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SadhakThemeColors.sacredSecondary,
                            fontSize = 13.sp
                        )
                    )
                    Text(
                        text = if (isActive) {
                            if (isEnglish) "Tap to stop reading" else "रोकने के लिए स्पर्श करें"
                        } else {
                            if (isEnglish) "Peaceful devotional text-to-speech reader" else "शुद्ध एवं शांत पाठ वाचन"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SadhakThemeColors.sacredTextSecondary,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            if (isActive) {
                Text(
                    text = if (isEnglish) "Playing •••" else "वाचन जारी •••",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = SadhakThemeColors.sacredPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
