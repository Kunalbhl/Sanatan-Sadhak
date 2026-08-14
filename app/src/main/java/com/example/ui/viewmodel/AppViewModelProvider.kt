package com.example.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.SadhakApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { SadhakViewModel(sadhakApplication()) }
        initializer { HomeViewModel(sadhakApplication()) }
        initializer { KnowledgeViewModel(sadhakApplication()) }
        initializer { PanchangViewModel(sadhakApplication()) }
        initializer { MantraViewModel(sadhakApplication()) }
        initializer { MeditationViewModel(sadhakApplication()) }
        initializer { VideoViewModel(sadhakApplication()) }
        initializer { CommunityViewModel(sadhakApplication()) }
        initializer { TempleViewModel(sadhakApplication()) }
        initializer { ProfileViewModel(sadhakApplication()) }
        initializer { SettingsViewModel(sadhakApplication()) }
        initializer { SearchViewModel(sadhakApplication()) }
        initializer { AiMitraViewModel(sadhakApplication()) }
        initializer { AdminViewModel(sadhakApplication()) }
    }
}

fun CreationExtras.sadhakApplication(): SadhakApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SadhakApplication)
