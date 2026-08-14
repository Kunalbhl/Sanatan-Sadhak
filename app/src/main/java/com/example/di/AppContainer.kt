package com.example.di

import android.app.Application
import com.example.data.*

class AppContainer(application: Application) {
    val appPreferencesRepository = AppPreferencesRepository(application)
    val navigationRepository = NavigationRepository()
    val userRepository = UserRepository.getInstance(application)
    val knowledgeRepository = KnowledgeRepository(application)
    val panchangRepository = PanchangRepository(application)
    val mantraRepository = MantraRepository(application)
    val meditationRepository = MeditationRepository(application)
    val videoRepository = VideoRepository(application)
    val communityRepository = CommunityRepository(application)
    val templeRepository = TempleRepository(application)
    val profileRepository = ProfileRepository(application)
    val settingsRepository = SettingsRepository(application)
    val searchRepository = SearchRepository(application)
    val aiMitraRepository = AiMitraRepository(application)
}
