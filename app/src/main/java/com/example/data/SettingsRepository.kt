package com.example.data

import android.content.Context

class SettingsRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val settingDao = db.appSettingDao()
    val appPreferencesRepository = AppPreferencesRepository(context)
    val userRepository = UserRepository.getInstance(context)

    suspend fun getSetting(key: String): AppSetting? = settingDao.getSetting(key)
    suspend fun insertSetting(setting: AppSetting) = settingDao.insertSetting(setting)
}
