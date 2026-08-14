package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.TempleRepository
import com.example.ui.common.UiState
import com.example.ui.screens.HinduTemple
import com.example.ui.screens.famousTemples
import kotlinx.coroutines.flow.*

class TempleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TempleRepository(application)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCity = MutableStateFlow("All")
    val selectedCity: StateFlow<String> = _selectedCity

    private val _selectedDeityFilter = MutableStateFlow("All")
    val selectedDeityFilter: StateFlow<String> = _selectedDeityFilter

    val temples: StateFlow<List<HinduTemple>> = combine(
        _searchQuery,
        _selectedCity,
        _selectedDeityFilter
    ) { query, city, deity ->
        famousTemples.filter { temple ->
            val matchesQuery = query.isEmpty() ||
                    temple.nameEn.contains(query, ignoreCase = true) ||
                    temple.nameHi.contains(query, ignoreCase = true) ||
                    temple.cityEn.contains(query, ignoreCase = true)
            val matchesCity = city == "All" || temple.cityEn.equals(city, ignoreCase = true)
            val matchesDeity = deity == "All" || temple.deityEn.contains(deity, ignoreCase = true)
            matchesQuery && matchesCity && matchesDeity
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), famousTemples)

    val templesUiState: StateFlow<UiState<List<HinduTemple>>> = temples.map { list ->
        if (list.isEmpty()) UiState.Empty else UiState.Success(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCity(city: String) {
        _selectedCity.value = city
    }

    fun setSelectedDeityFilter(deity: String) {
        _selectedDeityFilter.value = deity
    }
}
