package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppPreferences
import com.example.data.KnowledgeArticle
import com.example.data.KnowledgeRepository
import com.example.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class KnowledgeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = KnowledgeRepository(application)

    private val _selectedKnowledgeCategory = MutableStateFlow("All")
    val selectedKnowledgeCategory: StateFlow<String> = _selectedKnowledgeCategory

    private val _knowledgeSortBy = MutableStateFlow("Newest")
    val knowledgeSortBy: StateFlow<String> = _knowledgeSortBy

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _knowledgeFormat = MutableStateFlow("All")
    val knowledgeFormat: StateFlow<String> = _knowledgeFormat

    private val _knowledgeDifficulty = MutableStateFlow("All")
    val knowledgeDifficulty: StateFlow<String> = _knowledgeDifficulty

    private val _recentSearches = MutableStateFlow<List<String>>(emptyList())
    val recentSearches: StateFlow<List<String>> = _recentSearches

    private val _selectedDeity = MutableStateFlow("All")
    val selectedDeity: StateFlow<String> = _selectedDeity

    fun setSelectedDeity(deity: String) {
        _selectedDeity.value = deity
    }

    fun setKnowledgeCategory(category: String) {
        _selectedKnowledgeCategory.value = category
    }

    fun setKnowledgeSort(sort: String) {
        _knowledgeSortBy.value = sort
    }

    fun setKnowledgeSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            addRecentSearch(query)
        }
    }

    fun setKnowledgeFormat(format: String) {
        _knowledgeFormat.value = format
    }

    fun setKnowledgeDifficulty(difficulty: String) {
        _knowledgeDifficulty.value = difficulty
    }

    fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        _recentSearches.value = current.take(10)
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }

    private data class FilterParams(
        val eng: Boolean,
        val cat: String,
        val sort: String,
        val query: String,
        val format: String,
        val difficulty: String,
        val deity: String
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles: StateFlow<List<KnowledgeArticle>> = combine(
        AppPreferences.isEnglish as Flow<Any>,
        _selectedKnowledgeCategory as Flow<Any>,
        _knowledgeSortBy as Flow<Any>,
        _searchQuery as Flow<Any>,
        _knowledgeFormat as Flow<Any>,
        _knowledgeDifficulty as Flow<Any>,
        _selectedDeity as Flow<Any>
    ) { args ->
        FilterParams(
            eng = args[0] as Boolean,
            cat = args[1] as String,
            sort = args[2] as String,
            query = args[3] as String,
            format = args[4] as String,
            difficulty = args[5] as String,
            deity = args[6] as String
        )
    }.flatMapLatest { params ->
        val lang = if (params.eng) "en" else "hi"
        repository.getArticles(lang).map { list ->
            list.filter { article ->
                val matchesCategory = when (params.cat) {
                    "All", "सभी" -> true
                    "Vedas & Vedangas", "Vedas", "VEDA", "वेद और वेदांग" -> article.category.contains("Veda", ignoreCase = true) || article.category.contains("Granth", ignoreCase = true)
                    "Upanishads", "UPANISHAD", "उपनिषद" -> article.category.contains("Upanishad", ignoreCase = true)
                    "Itihasa", "ITIHASA", "इतिहास" -> article.category.contains("Itihasa", ignoreCase = true) || article.category.contains("Epic", ignoreCase = true) || article.title.contains("Ramayan", ignoreCase = true)
                    "Puranas", "PURANA", "पुराण" -> article.category.contains("Purana", ignoreCase = true)
                    "Darshana", "DARSHANA", "दर्शन" -> article.category.contains("Darshana", ignoreCase = true) || article.category.contains("Concept", ignoreCase = true)
                    "Scriptures & Granths", "GRANTHA", "धर्मग्रंथ" -> article.category.contains("Scripture", ignoreCase = true) || article.category.contains("Granth", ignoreCase = true)
                    "Stotra & Sacred Texts", "STOTRA", "स्तोत्र" -> article.category.contains("Stotra", ignoreCase = true) || article.format.contains("Stotra", ignoreCase = true)
                    "Saints & Acharyas", "Saints & Gurus", "SAINTS", "संत और आचार्य" -> article.category.contains("Saint", ignoreCase = true) || article.category.contains("Guru", ignoreCase = true)
                    "Spiritual Concepts", "Concepts", "CONCEPTS", "आध्यात्मिक विचार" -> article.category.contains("Concept", ignoreCase = true) || article.category.contains("Gyaan", ignoreCase = true)
                    "Pooja & Vidhi", "Rituals", "POOJA", "पूजा और विधि" -> article.category.contains("Pooja", ignoreCase = true) || article.category.contains("Puja", ignoreCase = true) || article.category.contains("Vidhi", ignoreCase = true)
                    "Mantras & Chanting", "Mantras", "MANTRAS", "मंत्र एवं जप" -> article.category.contains("Mantra", ignoreCase = true) || article.format.contains("Mantra", ignoreCase = true)
                    "Sadhana & Yoga", "SADHANA", "साधना एवं योग" -> article.category.contains("Sadhana", ignoreCase = true) || article.category.contains("Kriya", ignoreCase = true)
                    "Festivals & Fasting", "Festivals", "FESTIVALS", "त्यौहार और व्रत" -> article.category.contains("Festival", ignoreCase = true) || article.category.contains("Vrat", ignoreCase = true)
                    "Temples & Sacred Sites", "Temples", "TEMPLES", "मंदिर और तीर्थ" -> article.category.contains("Temple", ignoreCase = true) || article.category.contains("Tirtha", ignoreCase = true)
                    else -> article.category.equals(params.cat, ignoreCase = true)
                }

                val matchesDeity = when (params.deity) {
                    "All", "सभी" -> true
                    else -> article.relatedTopics.contains(params.deity, ignoreCase = true) ||
                            article.title.contains(params.deity, ignoreCase = true) ||
                            article.content.contains(params.deity, ignoreCase = true)
                }

                val matchesFormat = params.format == "All" || article.format.equals(params.format, ignoreCase = true)
                val matchesDifficulty = params.difficulty == "All" || article.difficulty.equals(params.difficulty, ignoreCase = true)

                val q = params.query.trim()
                val matchesQuery = q.isEmpty() ||
                        article.title.contains(q, ignoreCase = true) ||
                        article.summary.contains(q, ignoreCase = true) ||
                        article.content.contains(q, ignoreCase = true) ||
                        article.originalText.contains(q, ignoreCase = true) ||
                        article.translation.contains(q, ignoreCase = true) ||
                        article.sourceName.contains(q, ignoreCase = true) ||
                        article.textReference.contains(q, ignoreCase = true) ||
                        article.relatedTopics.contains(q, ignoreCase = true)

                matchesCategory && matchesDeity && matchesFormat && matchesDifficulty && matchesQuery
            }.sortedWith(
                when (params.sort) {
                    "A-Z" -> compareBy { it.title }
                    else -> compareByDescending { it.id } // Newest
                }
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val articlesUiState: StateFlow<UiState<List<KnowledgeArticle>>> = articles.map { list ->
        if (list.isEmpty()) UiState.Empty else UiState.Success(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    fun addKnowledgeArticle(title: String, summary: String, content: String, category: String, lang: String) {
        viewModelScope.launch {
            val newArticle = KnowledgeArticle(
                category = category,
                title = title,
                summary = summary,
                content = content,
                relatedVideos = "",
                language = lang
            )
            repository.addArticle(newArticle)
        }
    }

    fun updateKnowledgeArticle(article: KnowledgeArticle) {
        viewModelScope.launch {
            repository.updateArticle(article)
        }
    }

    fun deleteKnowledgeArticle(id: Int) {
        viewModelScope.launch {
            repository.deleteArticle(id)
        }
    }
}
