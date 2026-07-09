package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface KnowledgeArticleDao {
    @Query("SELECT * FROM knowledge_articles WHERE language = :language ORDER BY id ASC")
    fun getAllArticles(language: String): Flow<List<KnowledgeArticle>>

    @Query("SELECT * FROM knowledge_articles WHERE language = :language AND (title LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%') ORDER BY id ASC")
    fun searchArticles(query: String, language: String): Flow<List<KnowledgeArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: KnowledgeArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<KnowledgeArticle>)

    @Query("DELETE FROM knowledge_articles")
    suspend fun clearAllArticles()
}

@Dao
interface CommunityPostDao {
    @Query("SELECT * FROM community_posts ORDER BY isPinned DESC, createdAt DESC")
    fun getAllPosts(): Flow<List<CommunityPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPost)

    @Update
    suspend fun updatePost(post: CommunityPost)

    @Query("DELETE FROM community_posts WHERE id = :id")
    suspend fun deletePostById(id: Int)
}

@Dao
interface PostCommentDao {
    @Query("SELECT * FROM post_comments WHERE postId = :postId ORDER BY createdAt ASC")
    fun getCommentsForPost(postId: Int): Flow<List<PostComment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: PostComment)
}

@Dao
interface BhaktiVideoDao {
    @Query("SELECT * FROM bhakti_videos ORDER BY id DESC")
    fun getAllVideos(): Flow<List<BhaktiVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: BhaktiVideo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<BhaktiVideo>)

    @Query("DELETE FROM bhakti_videos WHERE isCustom = 0")
    suspend fun clearNonCustomVideos()
}

@Dao
interface KarmaLogDao {
    @Query("SELECT * FROM karma_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<KarmaLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: KarmaLog)
}

@Dao
interface GratitudeLogDao {
    @Query("SELECT * FROM gratitude_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<GratitudeLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: GratitudeLog)
}

@Dao
interface AppSettingDao {
    @Query("SELECT * FROM app_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSetting(key: String): AppSetting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: AppSetting)
}

@Dao
interface ChatMessageDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun clearChat()
}

@Dao
interface FavoriteMantraDao {
    @Query("SELECT * FROM favorite_mantras ORDER BY createdAt DESC")
    fun getAllFavoriteMantras(): Flow<List<FavoriteMantra>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMantra(mantra: FavoriteMantra)

    @Query("DELETE FROM favorite_mantras WHERE id = :id")
    suspend fun deleteFavoriteMantra(id: Int)
}

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
    
    @Query("SELECT * FROM users WHERE mobileNumber = :mobile LIMIT 1")
    suspend fun getUserByMobile(mobile: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("UPDATE users SET fullName = :fullName, mobileNumber = :mobileNumber, city = :city, state = :state, avatar = :avatar, profileImageUri = :profileImageUri WHERE email = :email")
    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, profileImageUri: String)
}

@Database(
    entities = [
        KnowledgeArticle::class,
        CommunityPost::class,
        PostComment::class,
        BhaktiVideo::class,
        KarmaLog::class,
        GratitudeLog::class,
        AppSetting::class,
        ChatMessage::class,
        FavoriteMantra::class,
        User::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun knowledgeArticleDao(): KnowledgeArticleDao
    abstract fun communityPostDao(): CommunityPostDao
    abstract fun postCommentDao(): PostCommentDao
    abstract fun bhaktiVideoDao(): BhaktiVideoDao
    abstract fun karmaLogDao(): KarmaLogDao
    abstract fun gratitudeLogDao(): GratitudeLogDao
    abstract fun appSettingDao(): AppSettingDao
    abstract fun chatMessageDao(): ChatMessageDao
    abstract fun favoriteMantraDao(): FavoriteMantraDao
    abstract fun userDao(): UserDao
}
