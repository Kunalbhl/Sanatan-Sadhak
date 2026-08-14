package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreferences
import com.example.data.FavoriteMantra
import com.example.data.MantraRepository
import com.example.data.ProfileRepository
import com.example.ui.common.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MantraViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MantraRepository(application)
    private val profileRepository = ProfileRepository(application)

    val favoriteMantras: StateFlow<List<FavoriteMantra>> = repository.getFavoriteMantras()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteMantrasUiState: StateFlow<UiState<List<FavoriteMantra>>> = favoriteMantras.map { list ->
        if (list.isEmpty()) UiState.Empty else UiState.Success(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    val bgChantEnabled: StateFlow<Boolean> = AppPreferences.bgChantEnabled
    val bgChantVolume: StateFlow<Float> = AppPreferences.bgChantVolume

    // Japa Counter state
    private val _japaCount = MutableStateFlow(0)
    val japaCount: StateFlow<Int> = _japaCount

    private val _targetCount = MutableStateFlow(108)
    val targetCount: StateFlow<Int> = _targetCount

    private val _malaCount = MutableStateFlow(0)
    val malaCount: StateFlow<Int> = _malaCount

    private val _selectedMantraTitle = MutableStateFlow("Gayatri Mantra")
    val selectedMantraTitle: StateFlow<String> = _selectedMantraTitle

    fun setSelectedMantraTitle(title: String) {
        _selectedMantraTitle.value = title
    }

    fun toggleBgChant() {
        AppPreferences.toggleBgChant()
    }

    fun setBgChantVolume(volume: Float) {
        AppPreferences.setBgChantVolume(volume)
    }

    fun addFavoriteMantra(title: String, content: String, youtubeId: String) {
        viewModelScope.launch {
            val newMantra = FavoriteMantra(
                title = title,
                content = content,
                youtubeId = youtubeId
            )
            repository.addFavoriteMantra(newMantra)
        }
    }

    fun deleteFavoriteMantra(id: Int) {
        viewModelScope.launch {
            repository.deleteFavoriteMantra(id)
        }
    }

    fun incrementJapaCount() {
        _japaCount.value += 1
        if (_japaCount.value >= _targetCount.value) {
            _malaCount.value += 1
            _japaCount.value = 0
            // Increment overall sadhana count in repository
            viewModelScope.launch {
                val log = com.example.data.KarmaLog(
                    deed = "Completed Mala Japa (${_targetCount.value} Chants)",
                    streak = 1,
                    date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                )
                profileRepository.addKarmaLog(log)
            }
        }
    }

    fun decrementJapaCount() {
        if (_japaCount.value > 0) {
            _japaCount.value -= 1
        }
    }

    fun resetJapaCount() {
        _japaCount.value = 0
        _malaCount.value = 0
    }

    fun setTargetCount(target: Int) {
        _targetCount.value = target
    }
}
