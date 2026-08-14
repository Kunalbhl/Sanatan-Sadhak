package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class KnowledgeRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val articleDao = db.knowledgeArticleDao()
    private val readingProgressDao = db.readingProgressDao()
    private val thoughtQuoteDao = db.thoughtQuoteDao()
    private val announcementDao = db.announcementDao()

    fun getArticles(language: String): Flow<List<KnowledgeArticle>> = articleDao.getAllArticles(language)
    fun searchArticles(query: String, language: String): Flow<List<KnowledgeArticle>> = articleDao.searchArticles(query, language)
    suspend fun addArticle(article: KnowledgeArticle) = articleDao.insertArticle(article)
    suspend fun updateArticle(article: KnowledgeArticle) = articleDao.updateArticle(article)
    suspend fun deleteArticle(id: Int) = articleDao.deleteArticleById(id)

    suspend fun getReadingProgress(userId: String, articleId: Int): ReadingProgress? = readingProgressDao.getProgress(userId, articleId)
    suspend fun saveReadingProgress(progress: ReadingProgress) = readingProgressDao.insertProgress(progress)

    fun getThoughtQuotes(): Flow<List<ThoughtQuote>> = thoughtQuoteDao.getAllThoughtQuotes()
    suspend fun addThoughtQuote(quote: ThoughtQuote) = thoughtQuoteDao.insertThoughtQuote(quote)
    suspend fun updateThoughtQuote(quote: ThoughtQuote) = thoughtQuoteDao.updateThoughtQuote(quote)
    suspend fun deleteThoughtQuoteById(id: Int) = thoughtQuoteDao.deleteById(id)

    fun getAnnouncements(): Flow<List<Announcement>> = announcementDao.getAllAnnouncements()
    suspend fun addAnnouncement(announcement: Announcement) = announcementDao.insertAnnouncement(announcement)
    suspend fun updateAnnouncement(announcement: Announcement) = announcementDao.updateAnnouncement(announcement)
    suspend fun deleteAnnouncementById(id: Int) = announcementDao.deleteById(id)
}
