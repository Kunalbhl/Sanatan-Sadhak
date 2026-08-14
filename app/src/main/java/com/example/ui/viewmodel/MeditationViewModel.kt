package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MeditationRepository
import com.example.data.ProfileRepository
import com.example.ui.common.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MeditationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MeditationRepository(application)
    private val profileRepository = ProfileRepository(application)

    private val _selectedDurationSeconds = MutableStateFlow(300) // 5 minutes default
    val selectedDurationSeconds: StateFlow<Int> = _selectedDurationSeconds

    private val _secondsRemaining = MutableStateFlow(300)
    val secondsRemaining: StateFlow<Int> = _secondsRemaining

    private val _timerRunning = MutableStateFlow(false)
    val timerRunning: StateFlow<Boolean> = _timerRunning

    private val _selectedSoundIndex = MutableStateFlow(1) // Temple Bells default
    val selectedSoundIndex: StateFlow<Int> = _selectedSoundIndex

    private val _ambientVolume = MutableStateFlow(0.7f)
    val ambientVolume: StateFlow<Float> = _ambientVolume

    private val _breathingPhase = MutableStateFlow("Inhale")
    val breathingPhase: StateFlow<String> = _breathingPhase

    private var timerJob: Job? = null

    fun setDurationMinutes(minutes: Int) {
        val seconds = minutes * 60
        _selectedDurationSeconds.value = seconds
        if (!_timerRunning.value) {
            _secondsRemaining.value = seconds
        }
    }

    fun setSelectedSound(index: Int) {
        _selectedSoundIndex.value = index
    }

    fun setAmbientVolume(vol: Float) {
        _ambientVolume.value = vol
    }

    fun startTimer() {
        if (_timerRunning.value) return
        _timerRunning.value = true
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerRunning.value && _secondsRemaining.value > 0) {
                delay(1000)
                _secondsRemaining.value -= 1
                
                // Update breathing phase based on 12s cycle
                val cycle = _secondsRemaining.value % 12
                _breathingPhase.value = when (cycle) {
                    in 0..3 -> "Inhale"
                    in 4..7 -> "Hold"
                    else -> "Exhale"
                }
            }
            if (_secondsRemaining.value <= 0) {
                _timerRunning.value = false
                val completedMins = _selectedDurationSeconds.value / 60
                val log = com.example.data.KarmaLog(
                    deed = "Dhyana Meditation Completed ($completedMins mins)",
                    streak = 1,
                    date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                )
                profileRepository.addKarmaLog(log)
                _secondsRemaining.value = _selectedDurationSeconds.value
            }
        }
    }

    fun pauseTimer() {
        _timerRunning.value = false
        timerJob?.cancel()
    }

    fun resetTimer() {
        pauseTimer()
        _secondsRemaining.value = _selectedDurationSeconds.value
    }
}
