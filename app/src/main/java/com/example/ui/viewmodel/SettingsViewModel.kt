package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.AppPreferencesRepository

class SettingsViewModel(
    application: Application,
    private val prefRepo: AppPreferencesRepository = AppPreferencesRepository(application)
) : AndroidViewModel(application) {
    val isEnglish = prefRepo.isEnglish
    val isDarkMode = prefRepo.isDarkMode
    val themePreference = prefRepo.themePreference
    
    fun toggleLanguage() = prefRepo.toggleLanguage()
    fun toggleDarkMode() = prefRepo.toggleDarkMode()
    fun setThemePreference(pref: Int) = prefRepo.setThemePreference(pref)
}
