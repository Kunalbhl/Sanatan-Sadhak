package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationRepository {
    private val _currentScreen = MutableStateFlow("Home")
    val currentScreen: StateFlow<String> = _currentScreen

    fun setScreen(screen: String) {
        _currentScreen.value = screen
    }
}
