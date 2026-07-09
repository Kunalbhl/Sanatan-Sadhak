package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "knowledge_articles")
data class KnowledgeArticle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Deities, Temples, Scriptures, Festivals, Rituals, Mantras, Concepts, Gurus
    val title: String,
    val summary: String,
    val content: String,
    val relatedVideos: String, // comma-separated youtube video IDs
    val language: String // "en" or "hi"
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
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bhakti_videos")
data class BhaktiVideo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // Darshan & Puja, Aarti & Abhishek, Temple Visits, Devotional Stories
    val videoId: String, // YouTube video ID
    val isCustom: Boolean = false
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
    val youtubeUrl: String = "", // Link or YouTube video ID
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
    val profileImageUri: String = ""
)

