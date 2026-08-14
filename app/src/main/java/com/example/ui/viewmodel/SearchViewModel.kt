package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import com.example.ui.common.UiState
import com.example.ui.screens.HinduTemple
import com.example.ui.screens.famousTemples
import kotlinx.coroutines.flow.*

data class GlobalSearchResults(
    val articles: List<KnowledgeArticle> = emptyList(),
    val videos: List<BhaktiVideo> = emptyList(),
    val mantras: List<FavoriteMantra> = emptyList(),
    val temples: List<HinduTemple> = emptyList()
)

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val searchRepository = SearchRepository(application)

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    val searchResults: StateFlow<GlobalSearchResults> = combine(
        _query,
        searchRepository.knowledgeRepository.getArticles("en"),
        searchRepository.videoRepository.getVideos(),
        searchRepository.mantraRepository.getFavoriteMantras()
    ) { q, articles, videos, mantras ->
        if (q.isBlank()) {
            GlobalSearchResults()
        } else {
            val matchedArticles = articles.filter {
                it.title.contains(q, ignoreCase = true) || it.summary.contains(q, ignoreCase = true)
            }
            val matchedVideos = videos.filter {
                it.title.contains(q, ignoreCase = true) || it.description.contains(q, ignoreCase = true)
            }
            val matchedMantras = mantras.filter {
                it.title.contains(q, ignoreCase = true) || it.content.contains(q, ignoreCase = true)
            }
            val matchedTemples = famousTemples.filter {
                it.nameEn.contains(q, ignoreCase = true) || it.nameHi.contains(q, ignoreCase = true) ||
                        it.cityEn.contains(q, ignoreCase = true)
            }
            GlobalSearchResults(
                articles = matchedArticles,
                videos = matchedVideos,
                mantras = matchedMantras,
                temples = matchedTemples
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GlobalSearchResults())

    val searchUiState: StateFlow<UiState<GlobalSearchResults>> = searchResults.map { results ->
        if (results.articles.isEmpty() && results.videos.isEmpty() && results.mantras.isEmpty() && results.temples.isEmpty()) {
            UiState.Empty
        } else {
            UiState.Success(results)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Empty)

    fun setQuery(q: String) {
        _query.value = q
    }

    fun addRecentSearch(search: String) {
        if (search.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(search)
        current.add(0, search)
        _recentSearches.value = current.take(10)
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }
}
