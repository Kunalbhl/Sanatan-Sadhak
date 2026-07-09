package com.example.data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SadhakRepository(private val context: Context) {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "sanatan_sadhak_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    val firebaseService = FirebaseService(context)

    // DAOs
    private val articleDao by lazy { db.knowledgeArticleDao() }
    private val postDao by lazy { db.communityPostDao() }
    private val commentDao by lazy { db.postCommentDao() }
    private val videoDao by lazy { db.bhaktiVideoDao() }
    private val karmaDao by lazy { db.karmaLogDao() }
    private val gratitudeDao by lazy { db.gratitudeLogDao() }
    private val settingDao by lazy { db.appSettingDao() }
    private val chatDao by lazy { db.chatMessageDao() }
    private val favoriteMantraDao by lazy { db.favoriteMantraDao() }
    private val userDao by lazy { db.userDao() }

    // User session state (local auth persistence using Room)
    private val _userEmailState = MutableStateFlow("")
    val userEmailState: StateFlow<String> = _userEmailState

    private val _userRoleState = MutableStateFlow("Guest") // "Guest", "Regular", "SuperUser"
    val userRoleState: StateFlow<String> = _userRoleState

    private val _userNameState = MutableStateFlow("Guest Sadhak")
    val userNameState: StateFlow<String> = _userNameState

    private val _userAvatarState = MutableStateFlow(1) // Avatar 1-4
    val userAvatarState: StateFlow<Int> = _userAvatarState

    private val _userProfileImageUriState = MutableStateFlow("")
    val userProfileImageUriState: StateFlow<String> = _userProfileImageUriState

    private val _userMobileState = MutableStateFlow("")
    val userMobileState: StateFlow<String> = _userMobileState

    private val _userCityState = MutableStateFlow("")
    val userCityState: StateFlow<String> = _userCityState

    private val _userStateProvinceState = MutableStateFlow("")
    val userStateProvinceState: StateFlow<String> = _userStateProvinceState

    // Global settings State
    private val _isPublicPostingEnabled = MutableStateFlow(false)
    val isPublicPostingEnabled: StateFlow<Boolean> = _isPublicPostingEnabled

    // Designated Super User Email / Username (Role-Based Access Control configuration)
    private val _designatedSuperUser = MutableStateFlow("ashishpareekbhl@gmail.com")
    val designatedSuperUser: StateFlow<String> = _designatedSuperUser

    init {
        // Prepopulate database asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateIfEmpty()
            loadSettings()
            refreshYoutubeVideos()
        }
    }

    private suspend fun prepopulateIfEmpty() {
        // Check articles
        val articlesFlow = articleDao.getAllArticles("en")
        // To check if empty, we can make a direct quick query or look at first item
        // Let's check using database connection
        val dbSize = withContext(Dispatchers.IO) {
            try {
                // Insert default articles if empty
                // We'll use a direct count query. Let's just try inserting if count is 0
                // Wait, we can implement a custom query in DAO or just try inserting default data once.
                // Let's see: we can load the setting "prepopulated" to ensure we only do it once!
                val prepopulatedSetting = settingDao.getSetting("db_prepopulated_v4")
                if (prepopulatedSetting == null) {
                    // Refresh articles to include newly added scripture categories
                    articleDao.clearAllArticles()
                    articleDao.insertArticles(SpiritualData.defaultArticles)
                    
                    videoDao.clearNonCustomVideos()
                    videoDao.insertVideos(SpiritualData.defaultVideos)
                    
                    // Only insert default posts if the database is brand new and empty
                    val postsSetting = settingDao.getSetting("db_prepopulated")
                    if (postsSetting == null) {
                        for (post in SpiritualData.defaultPosts) {
                            postDao.insertPost(post)
                        }
                        for (comment in SpiritualData.defaultComments) {
                            commentDao.insertComment(comment)
                        }
                    }
                    
                    settingDao.insertSetting(AppSetting("db_prepopulated_v4", "true"))
                    settingDao.insertSetting(AppSetting("db_prepopulated", "true"))
                    settingDao.insertSetting(AppSetting("public_posting_enabled", "false"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun loadSettings() {
        val postingSetting = settingDao.getSetting("public_posting_enabled")
        _isPublicPostingEnabled.value = postingSetting?.value == "true"
        val superUserSetting = settingDao.getSetting("designated_super_user")
        _designatedSuperUser.value = superUserSetting?.value ?: "ashishpareekbhl@gmail.com"

        // Auto-login if Remember Me was selected
        val rememberedEmail = settingDao.getSetting("remembered_user_email")?.value ?: ""
        if (rememberedEmail.isNotEmpty()) {
            val user = userDao.getUserByEmail(rememberedEmail) ?: userDao.getUserByMobile(rememberedEmail)
            if (user != null) {
                val emailClean = user.email.lowercase().trim()
                val isSuper = emailClean == "kunalpareekusa@gmail.com" || emailClean == "ashishpareekbhl@gmail.com"
                val finalUser = user.copy(
                    role = if (isSuper) "SuperUser" else "Regular",
                    avatar = if (isSuper) 4 else user.avatar
                )
                loginAsUser(finalUser)
            }
        }
    }

    suspend fun setRememberedUser(emailOrMobile: String) {
        settingDao.insertSetting(AppSetting("remembered_user_email", emailOrMobile))
    }

    suspend fun clearRememberedUser() {
        settingDao.insertSetting(AppSetting("remembered_user_email", ""))
    }

    // --- ARTICLE REPO FUNCTIONS ---
    fun getArticles(language: String): Flow<List<KnowledgeArticle>> = articleDao.getAllArticles(language)
    fun searchArticles(query: String, language: String): Flow<List<KnowledgeArticle>> = articleDao.searchArticles(query, language)
    suspend fun addArticle(article: KnowledgeArticle) = articleDao.insertArticle(article)

    // --- COMMUNITY POST FUNCTIONS ---
    fun getPosts(): Flow<List<CommunityPost>> = postDao.getAllPosts()
    
    suspend fun addPost(post: CommunityPost) {
        postDao.insertPost(post)
        // Sync to firebase if available
        if (firebaseService.isFirebaseAvailable.value) {
            firebaseService.savePostToFirestore(post)
        }
    }

    suspend fun updatePost(post: CommunityPost) = postDao.updatePost(post)
    suspend fun deletePost(id: Int) = postDao.deletePostById(id)

    // --- COMMENT FUNCTIONS ---
    fun getComments(postId: Int): Flow<List<PostComment>> = commentDao.getCommentsForPost(postId)
    suspend fun addComment(comment: PostComment) = commentDao.insertComment(comment)

    // --- VIDEO FUNCTIONS ---
    fun getVideos(): Flow<List<BhaktiVideo>> = videoDao.getAllVideos()
    suspend fun addVideo(video: BhaktiVideo) = videoDao.insertVideo(video)
    
    suspend fun refreshYoutubeVideos() = withContext(Dispatchers.IO) {
        try {
            val fetched = YoutubeFetcher.fetchVideosFromChannel("UCzHzPEdLJEvTSHn_ev3JYBA")
            if (fetched.isNotEmpty()) {
                videoDao.clearNonCustomVideos()
                videoDao.insertVideos(fetched)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- KARMA JOURNAL FUNCTIONS ---
    fun getKarmaLogs(): Flow<List<KarmaLog>> = karmaDao.getAllLogs()
    suspend fun addKarmaLog(log: KarmaLog) = karmaDao.insertLog(log)

    // --- GRATITUDE JOURNAL FUNCTIONS ---
    fun getGratitudeLogs(): Flow<List<GratitudeLog>> = gratitudeDao.getAllLogs()
    suspend fun addGratitudeLog(log: GratitudeLog) = gratitudeDao.insertLog(log)

    // --- CHAT MESSAGES FUNCTIONS ---
    fun getChatMessages(): Flow<List<ChatMessage>> = chatDao.getAllMessages()
    suspend fun addChatMessage(msg: ChatMessage) = chatDao.insertMessage(msg)
    suspend fun clearChat() = chatDao.clearChat()

    // --- SETTINGS ---
    suspend fun setPublicPostingEnabled(enabled: Boolean) {
        _isPublicPostingEnabled.value = enabled
        settingDao.insertSetting(AppSetting("public_posting_enabled", enabled.toString()))
    }

    suspend fun setDesignatedSuperUser(email: String) {
        _designatedSuperUser.value = email.trim()
        settingDao.insertSetting(AppSetting("designated_super_user", email.trim()))
    }

    // --- FAVORITE MANTRAS FUNCTIONS ---
    fun getFavoriteMantras(): Flow<List<FavoriteMantra>> = favoriteMantraDao.getAllFavoriteMantras()
    suspend fun addFavoriteMantra(mantra: FavoriteMantra) = favoriteMantraDao.insertFavoriteMantra(mantra)
    suspend fun deleteFavoriteMantra(id: Int) = favoriteMantraDao.deleteFavoriteMantra(id)

    // --- USER PROFILE & ROLE ---
    suspend fun registerUser(user: User): Boolean {
        val existing = userDao.getUserByEmail(user.email.lowercase().trim())
        if (existing != null) {
            return false
        }
        userDao.insertUser(user)
        return true
    }

    suspend fun authenticateUser(identifier: String, passwordText: String): User? {
        val idClean = identifier.lowercase().trim()
        var user = userDao.getUserByEmail(idClean)
        if (user == null) {
            user = userDao.getUserByMobile(idClean)
        }
        if (user != null && user.password == passwordText) {
            return user
        }
        return null
    }

    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {
        userDao.updateUserProfile(email.lowercase().trim(), fullName, mobileNumber, city, state, avatar, imageUri)
        if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
            _userNameState.value = fullName
            _userMobileState.value = mobileNumber
            _userCityState.value = city
            _userStateProvinceState.value = state
            _userAvatarState.value = avatar
        }
    }

    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        _userMobileState.value = user.mobileNumber
        _userCityState.value = user.city
        _userStateProvinceState.value = user.state
        // imageUri would be loaded from persistence in real app
    }

    fun logout() {
        _userEmailState.value = ""
        _userNameState.value = "Guest Sadhak"
        _userRoleState.value = "Guest"
        _userAvatarState.value = 1
        _userProfileImageUriState.value = ""
        _userMobileState.value = ""
        _userCityState.value = ""
        _userStateProvinceState.value = ""
        firebaseService.signOut()
        CoroutineScope(Dispatchers.IO).launch {
            clearRememberedUser()
        }
    }
}
