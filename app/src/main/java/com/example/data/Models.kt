package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "knowledge_articles")
data class KnowledgeArticle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Vedas & Granths, Puranas, Itihasa (Epics), Deities, Temples & Tirtha, Mantras & Stotras, Puja & Path Vidhi, Kriya & Sadhana, Festivals & Vrats, Concepts & Gyaan, Saints & Gurus
    val title: String,
    val summary: String,
    val content: String,
    val relatedVideos: String, // comma-separated youtube video IDs
    val language: String, // "en" or "hi"
    val difficulty: String = "Beginner", // Beginner, Intermediate, Advanced
    val readTimeMinutes: Int = 5,
    val relatedTopics: String = "", // comma-separated
    val format: String = "Article" // Article, Mantra, Puja Vidhi
)

data class DetailedPanchang(
    val tithi: String,
    val nakshatra: String,
    val yoga: String,
    val karana: String,
    val rashi: String,
    val sunrise: String,
    val sunset: String,
    val rahukaal: String,
    val gulikaKaal: String,
    val yamaganda: String,
    val abhijeet: String,
    val festival: String,
    val details: String,
    val weekday: String = "",
    val tithiEndTime: String = "",
    val nakshatraPada: String = "",
    val nakshatraLord: String = "",
    val lunarMonth: String = "",
    val paksha: String = "",
    val ritu: String = "",
    val samvatYear: String = "",
    val sunSign: String = ""
)

data class PanchangResult(
    val data: DetailedPanchang?,
    val error: String?,
    val url: String?,
    val requestBody: String?,
    val rawResponseBody: String?,
    val statusCode: Int?
)

@Entity(tableName = "bookmarked_articles", primaryKeys = ["userId", "articleId"])
data class BookmarkedArticle(
    val userId: String,
    val articleId: Int,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Bhakti Experiences, Doubts & Discussions, Daily Karma Journal, Temple Stories
    val author: String,
    val title: String,
    val content: String,
    val upvotes: Int = 1,
    val downvotes: Int = 0,
    val upvotedByEmails: String = "",
    val downvotedByEmails: String = "",
    val commentsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false
)

@Entity(tableName = "post_comments")
data class PostComment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val author: String,
    val content: String,
    val upvotes: Int = 0,
    val downvotes: Int = 0,
    val upvotedByEmails: String = "",
    val downvotedByEmails: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bhakti_videos")
data class BhaktiVideo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // Darshan & Puja, Aarti & Abhishek, Temple Visits, Devotional Stories
    val videoId: String, // YouTube video ID
    val isCustom: Boolean = false,
    val thumbnail: String = "",
    val publishDate: String = ""
)

@Entity(tableName = "karma_logs")
data class KarmaLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // yyyy-MM-dd
    val deed: String,
    val streak: Int = 1,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "gratitude_logs")
data class GratitudeLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, // yyyy-MM-dd
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_settings")
data class AppSetting(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sender: String, // "user" or "gemini"
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_mantras")
data class FavoriteMantra(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val youtubeId: String = "", // Link or YouTube video ID
    val playlistOrder: Int = 0,
    val timerMinutes: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val fullName: String,
    val mobileNumber: String,
    val city: String,
    val state: String,
    val password: String,
    val avatar: Int = 1,
    val role: String = "Regular",
    val profileImageUri: String = "",
    val canPost: Boolean = false
)

@Entity(tableName = "instagram_posts")
data class InstagramPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val caption: String,
    val category: String = "Devotional Thought",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "thought_quotes")
data class ThoughtQuote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val textEn: String,
    val textHi: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val textEn: String,
    val textHi: String,
    val isVisible: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)


data class AppNotification(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val type: String,
    var isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "sankalpa_logs")
data class SankalpaLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val intention: String,
    val progressNote: String,
    val progressValue: Int, // e.g., 0-100 or number of rounds
    val date: String, // yyyy-MM-dd
    val timestamp: Long = System.currentTimeMillis()
)

data class DailyMantra(
    val title: String,
    val content: String,
    val meaning: String,
    val audioUrl: String
)

data class Shloka(
    val verse: String,
    val meaning: String,
    val source: String
)

data class DeityShrine(
    val nameEn: String,
    val nameHi: String,
    val imageUrl: String,
    val captionEn: String,
    val captionHi: String,
    val description: String
)

