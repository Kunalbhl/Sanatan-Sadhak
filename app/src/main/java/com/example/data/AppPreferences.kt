package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AppPreferences {
    private lateinit var prefs: SharedPreferences

    private val _isEnglish = MutableStateFlow(true)
    val isEnglish: StateFlow<Boolean> = _isEnglish

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    private val _themePreference = MutableStateFlow(1)
    val themePreference: StateFlow<Int> = _themePreference

    private val _bgChantEnabled = MutableStateFlow(false)
    val bgChantEnabled: StateFlow<Boolean> = _bgChantEnabled

    private val _bgChantVolume = MutableStateFlow(0.3f)
    val bgChantVolume: StateFlow<Float> = _bgChantVolume

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled

    fun init(context: Context) {
        prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        _isEnglish.value = prefs.getBoolean("is_english", true)
        _isDarkMode.value = prefs.getBoolean("is_dark_mode", false)
        _themePreference.value = prefs.getInt("theme_pref", 1)
        _bgChantEnabled.value = prefs.getBoolean("bg_chant_enabled", false)
        _bgChantVolume.value = prefs.getFloat("bg_chant_vol", 0.3f)
        _soundEnabled.value = prefs.getBoolean("sound_enabled", true)
        com.example.ui.audio.SadhakAudioManager.setSoundEnabled(_soundEnabled.value)
    }

    fun toggleLanguage() {
        val newVal = !_isEnglish.value
        _isEnglish.value = newVal
        prefs.edit().putBoolean("is_english", newVal).apply()
    }

    fun toggleDarkMode() {
        val newVal = !_isDarkMode.value
        _isDarkMode.value = newVal
        prefs.edit().putBoolean("is_dark_mode", newVal).apply()
    }

    fun setThemePreference(pref: Int) {
        _themePreference.value = pref
        prefs.edit().putInt("theme_pref", pref).apply()
    }
    
    fun toggleBgChant() {
        val newVal = !_bgChantEnabled.value
        _bgChantEnabled.value = newVal
        prefs.edit().putBoolean("bg_chant_enabled", newVal).apply()
    }
    
    fun setBgChantVolume(vol: Float) {
        _bgChantVolume.value = vol
        prefs.edit().putFloat("bg_chant_vol", vol).apply()
    }

    fun setSoundEnabled(enabled: Boolean) {
        _soundEnabled.value = enabled
        if (::prefs.isInitialized) {
            prefs.edit().putBoolean("sound_enabled", enabled).apply()
        }
        com.example.ui.audio.SadhakAudioManager.setSoundEnabled(enabled)
    }

    fun toggleSound() {
        setSoundEnabled(!_soundEnabled.value)
    }
}
