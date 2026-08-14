package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.ui.common.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ProfileRepository(application)
    private val userRepository = UserRepository.getInstance(application)
    private val meditationRepository = MeditationRepository(application)

    val userRole: StateFlow<String> = userRepository.userRoleState
    val userName: StateFlow<String> = userRepository.userNameState
    val userEmail: StateFlow<String> = userRepository.userEmailState
    val userMobile: StateFlow<String> = userRepository.userMobileState
    val userCity: StateFlow<String> = userRepository.userCityState
    val userStateProvince: StateFlow<String> = userRepository.userStateProvinceState
    val userAvatar: StateFlow<Int> = userRepository.userAvatarState
    val userProfileImageUri: StateFlow<String> = userRepository.userProfileImageUriState

    val karmaLogs: StateFlow<List<KarmaLog>> = repository.getKarmaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gratitudeLogs: StateFlow<List<GratitudeLog>> = repository.getGratitudeLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val sankalpaLogs: StateFlow<List<SankalpaLog>> = meditationRepository.getSankalpaLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val karmaPoints: StateFlow<Int> = combine(karmaLogs, gratitudeLogs) { k, g ->
        k.size * 10 + g.size * 5
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val dailyStreak: StateFlow<Int> = karmaLogs.map { logs ->
        if (logs.isNotEmpty()) logs.first().streak else 0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun updateUserProfileDetails(
        fullName: String,
        mobileNumber: String,
        city: String,
        state: String,
        avatar: Int,
        imageUri: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isNotEmpty()) {
                userRepository.updateUserProfile(email, fullName, mobileNumber, city, state, avatar, imageUri)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun logKarmaDeed(deed: String) {
        viewModelScope.launch {
            val lastLogs = karmaLogs.value
            val currentStreak = if (lastLogs.isNotEmpty()) lastLogs.first().streak else 1
            val log = KarmaLog(
                deed = deed,
                streak = currentStreak,
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
            )
            repository.addKarmaLog(log)
        }
    }

    fun addSankalpaLog(intention: String, progressNote: String, progressValue: Int, date: String) {
        viewModelScope.launch {
            meditationRepository.addSankalpaLog(
                SankalpaLog(
                    intention = intention,
                    progressNote = progressNote,
                    progressValue = progressValue,
                    date = date
                )
            )
        }
    }

    fun addGratitudeEntry(blessing: String) {
        viewModelScope.launch {
            val entry = GratitudeLog(
                content = blessing,
                date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
            )
            repository.addGratitudeLog(entry)
        }
    }

    fun deleteGratitudeEntry(id: Int) {
        // Reserved for future deletion implementation
    }

    fun logout() {
        userRepository.logout()
    }
}
