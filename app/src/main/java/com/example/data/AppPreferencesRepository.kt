package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppPreferencesRepository(private val context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _isEnglish = MutableStateFlow(prefs.getBoolean("is_english", true))
    val isEnglish: StateFlow<Boolean> = _isEnglish

    fun toggleLanguage() {
        val newVal = !_isEnglish.value
        _isEnglish.value = newVal
        prefs.edit().putBoolean("is_english", newVal).apply()
    }

    private val _isDarkMode = MutableStateFlow(prefs.getBoolean("is_dark_mode", false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode
    fun toggleDarkMode() {
        val newVal = !_isDarkMode.value
        _isDarkMode.value = newVal
        prefs.edit().putBoolean("is_dark_mode", newVal).apply()
    }

    private val _themePreference = MutableStateFlow(prefs.getInt("theme_pref", 1))
    val themePreference: StateFlow<Int> = _themePreference
    fun setThemePreference(pref: Int) {
        _themePreference.value = pref
        prefs.edit().putInt("theme_pref", pref).apply()
    }

    private val _soundEnabled = MutableStateFlow(prefs.getBoolean("sound_enabled", true))
    val soundEnabled: StateFlow<Boolean> = _soundEnabled

    fun setSoundEnabled(enabled: Boolean) {
        _soundEnabled.value = enabled
        prefs.edit().putBoolean("sound_enabled", enabled).apply()
        com.example.ui.audio.SadhakAudioManager.setSoundEnabled(enabled)
    }

    fun toggleSound() {
        setSoundEnabled(!_soundEnabled.value)
    }
}
