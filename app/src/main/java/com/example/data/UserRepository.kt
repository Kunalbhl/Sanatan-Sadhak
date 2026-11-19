package com.example.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository private constructor(private val appContext: Context) {
    private val context: Context get() = appContext

    private val db = AppDatabase.getInstance(context)
    private val userDao = db.userDao()
    private val settingDao = db.appSettingDao()
    private val articleDao = db.knowledgeArticleDao()
    private val videoDao = db.bhaktiVideoDao()
    private val postDao = db.communityPostDao()
    private val commentDao = db.postCommentDao()

    val firebaseService = FirebaseService(context)

    private val _userEmailState = MutableStateFlow("")
    val userEmailState: StateFlow<String> = _userEmailState

    private val _userRoleState = MutableStateFlow("Guest")
    val userRoleState: StateFlow<String> = _userRoleState

    private val _userNameState = MutableStateFlow("Guest Sadhak")
    val userNameState: StateFlow<String> = _userNameState

    private val _userAvatarState = MutableStateFlow(1)
    val userAvatarState: StateFlow<Int> = _userAvatarState

    private val _userProfileImageUriState = MutableStateFlow("")
    val userProfileImageUriState: StateFlow<String> = _userProfileImageUriState

    private val _userMobileState = MutableStateFlow("")
    val userMobileState: StateFlow<String> = _userMobileState

    private val _userCityState = MutableStateFlow("")
    val userCityState: StateFlow<String> = _userCityState

    private val _userStateProvinceState = MutableStateFlow("")
    val userStateProvinceState: StateFlow<String> = _userStateProvinceState

    private val _isPublicPostingEnabled = MutableStateFlow(false)
    val isPublicPostingEnabled: StateFlow<Boolean> = _isPublicPostingEnabled

    private val _designatedSuperUser = MutableStateFlow("ashishpareekbhl@gmail.com")
    val designatedSuperUser: StateFlow<String> = _designatedSuperUser

    init {
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateIfEmpty()
            loadSettings()
        }
    }

    private suspend fun prepopulateIfEmpty() {
        withContext(Dispatchers.IO) {
            try {
                val prepopulatedSetting = settingDao.getSetting("db_prepopulated_v6")
                if (prepopulatedSetting == null) {
                    articleDao.insertArticles(SpiritualData.defaultArticles)
                    
                    videoDao.deleteByVideoIds(SpiritualData.defaultVideos.map { it.videoId })
                    
                    val postsSetting = settingDao.getSetting("db_prepopulated")
                    if (postsSetting == null) {
                        for (post in SpiritualData.defaultPosts) {
                            postDao.insertPost(post)
                        }
                        for (comment in SpiritualData.defaultComments) {
                            commentDao.insertComment(comment)
                        }
                    }
                    
                    settingDao.insertSetting(AppSetting("db_prepopulated_v6", "true"))
                    settingDao.insertSetting(AppSetting("db_prepopulated", "true"))
                    settingDao.insertSetting(AppSetting("public_posting_enabled", "false"))
                }
            } catch (e: Exception) {
                android.util.Log.e("UserRepository", "Error pre-populating database", e)
            }
        }
    }

    private suspend fun loadSettings() {
        val postingSetting = settingDao.getSetting("public_posting_enabled")
        _isPublicPostingEnabled.value = postingSetting?.value == "true"
        val superUserSetting = settingDao.getSetting("designated_super_user")
        _designatedSuperUser.value = superUserSetting?.value ?: "ashishpareekbhl@gmail.com"

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

    suspend fun getRememberedUserEmail(): String? {
        return settingDao.getSetting("remembered_user_email")?.value
    }

    suspend fun clearRememberedUser() {
        settingDao.insertSetting(AppSetting("remembered_user_email", ""))
    }

    fun getAllUsers() = userDao.getAllUsers()
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun updateUserCanPost(email: String, canPost: Boolean) = userDao.updateUserCanPost(email, canPost)
    suspend fun updateUserRole(email: String, role: String) = userDao.updateUserRole(email, role)

    suspend fun setPublicPostingEnabled(enabled: Boolean) {
        _isPublicPostingEnabled.value = enabled
        settingDao.insertSetting(AppSetting("public_posting_enabled", enabled.toString()))
    }

    suspend fun setDesignatedSuperUser(email: String) {
        _designatedSuperUser.value = email.trim()
        settingDao.insertSetting(AppSetting("designated_super_user", email.trim()))
    }

    suspend fun registerUser(user: User): Boolean {
        val existing = userDao.getUserByEmail(user.email.lowercase().trim())
        if (existing != null) {
            return false
        }
        
        // Try Firebase auth if available
        if (firebaseService.isFirebaseAvailable.value) {
            val success = firebaseService.signUpWithEmail(user.email.lowercase().trim(), user.password)
            if (success) {
                firebaseService.saveUserToFirestore(user)
            }
        }
        
        userDao.insertUser(user)
        return true
    }

    suspend fun authenticateUser(identifier: String, passwordText: String): User? {
        val idClean = identifier.lowercase().trim()
        
        // Try Firebase auth if available and identifier looks like an email
        var cloudOk = false
        if (firebaseService.isFirebaseAvailable.value && idClean.contains("@")) {
            cloudOk = firebaseService.signInWithEmail(idClean, passwordText)
        }

        // New phone / reinstall: Firebase knows the account but this device doesn't — restore it.
        if (cloudOk && userDao.getUserByEmail(idClean) == null) {
            val p = firebaseService.loadProfile(idClean)
            userDao.insertUser(User(
                email = idClean,
                fullName = p?.get("fullName") as? String ?: idClean.substringBefore("@"),
                mobileNumber = p?.get("mobileNumber") as? String ?: "",
                city = p?.get("city") as? String ?: "",
                state = p?.get("state") as? String ?: "",
                avatar = (p?.get("avatar") as? Number)?.toInt() ?: 1,
                password = passwordText,
                role = p?.get("role") as? String ?: "Regular",
                profileImageUri = ProfileImageStore.fromBase64(context, idClean, p?.get("photoBase64") as? String ?: "")
            ))
        }

        var user = userDao.getUserByEmail(idClean)
        if (user == null) {
            user = userDao.getUserByMobile(idClean)
        }
        // Account that so far only existed on this phone: create its Firebase account now,
        // so profile backup, Community and admin rights work in the cloud too.
        // If the cloud already has this email, the cloud password is the only truth: a stale
        // local password (e.g. the one before a reset) must NOT work any more.
        if (!cloudOk && user != null && user.password == passwordText && idClean.contains("@") &&
            firebaseService.isFirebaseAvailable.value && passwordText.length >= 6) {
            when (firebaseService.signUpResult(idClean, passwordText)) {
                "CREATED" -> firebaseService.saveProfile(user, ProfileImageStore.toBase64(context, user.email))
                "EXISTS" -> {
                    userDao.insertUser(user.copy(password = "locked_" + java.util.UUID.randomUUID()))
                    return null
                }
                else -> {} // offline: allow the local login below
            }
        }

        if (user != null && (user.password == passwordText || cloudOk)) {
            if (cloudOk && user.password != passwordText) userDao.insertUser(user.copy(password = passwordText))
            return user
        }
        return null
    }

    /** Real Google sign-in. Reuses the existing account for that email, or creates it. */
    suspend fun signInWithGoogle(activity: android.app.Activity): User? {
        val g = firebaseService.signInWithGoogle(activity) ?: return null
        var user = userDao.getUserByEmail(g.email)
        if (user == null) {
            val p = firebaseService.loadProfile(g.email)
            user = User(
                email = g.email,
                fullName = p?.get("fullName") as? String ?: g.name,
                mobileNumber = p?.get("mobileNumber") as? String ?: "",
                city = p?.get("city") as? String ?: "",
                state = p?.get("state") as? String ?: "",
                avatar = (p?.get("avatar") as? Number)?.toInt() ?: 1,
                password = "google_" + java.util.UUID.randomUUID(),
                role = p?.get("role") as? String ?: "Regular",
                profileImageUri = ProfileImageStore.fromBase64(context, g.email, p?.get("photoBase64") as? String ?: "")
            )
            userDao.insertUser(user)
            if (p == null) firebaseService.saveProfile(user, "")
        }
        return user
    }

    suspend fun authenticateUserByEmailOnly(identifier: String): User? {
        val idClean = identifier.lowercase().trim()
        var user = userDao.getUserByEmail(idClean)
        if (user == null) {
            user = userDao.getUserByMobile(idClean)
        }
        return user
    }

    suspend fun updateUserProfile(email: String, fullName: String, mobileNumber: String, city: String, state: String, avatar: Int, imageUri: String) {
        userDao.updateUserProfile(email.lowercase().trim(), fullName, mobileNumber, city, state, avatar, imageUri)
        // Back up to the cloud so the profile (and photo) come back after reinstall / on a new phone
        userDao.getUserByEmail(email.lowercase().trim())?.let { u ->
            firebaseService.saveProfile(u, ProfileImageStore.toBase64(context, u.email))
        }
        if (_userEmailState.value.lowercase().trim() == email.lowercase().trim()) {
            _userNameState.value = fullName
            _userMobileState.value = mobileNumber
            _userCityState.value = city
            _userStateProvinceState.value = state
            _userAvatarState.value = avatar
            _userProfileImageUriState.value = imageUri
        }
    }

    private val settingFiles = listOf("app_prefs", "panchang_prefs", com.example.notifications.SadhakReminderScheduler.PREFS_NAME)
    private val sensitiveKey = Regex("(?i)password|token|secret|api|remembered|email|auth|session")

    /** Upload all non-sensitive preferences so they are never lost on reinstall. */
    suspend fun backupSettings() {
        if (!firebaseService.isFirebaseAvailable.value || _userEmailState.value.isBlank()) return
        val out = HashMap<String, Any>()
        for (file in settingFiles) {
            context.getSharedPreferences(file, Context.MODE_PRIVATE).all.forEach { (k, v) ->
                if (sensitiveKey.containsMatchIn(k)) return@forEach
                val enc = when (v) {
                    is Boolean -> "b:$v"; is Int -> "i:$v"; is Long -> "l:$v"; is Float -> "f:$v"
                    is String -> if (v.length < 2000) "s:$v" else null
                    else -> null
                }
                if (enc != null) out["$file|$k"] = enc
            }
        }
        if (out.isNotEmpty()) firebaseService.saveSettings(out)
    }

    /** Fill in preferences missing on this device from the cloud backup. Returns true if anything was restored. */
    suspend fun restoreSettings(): Boolean {
        val cloud = firebaseService.loadSettings() ?: return false
        var changed = false
        for ((composite, v) in cloud) {
            val file = composite.substringBefore("|"); val key = composite.substringAfter("|")
            if (file !in settingFiles || sensitiveKey.containsMatchIn(key)) continue
            val p = context.getSharedPreferences(file, Context.MODE_PRIVATE)
            if (p.contains(key)) continue
            val e = p.edit()
            val raw = v as? String ?: continue
            val body = raw.drop(2)
            try {
                when (raw.take(2)) {
                    "b:" -> e.putBoolean(key, body.toBoolean())
                    "i:" -> e.putInt(key, body.toInt())
                    "l:" -> e.putLong(key, body.toLong())
                    "f:" -> e.putFloat(key, body.toFloat())
                    "s:" -> e.putString(key, body)
                    else -> continue
                }
            } catch (_: NumberFormatException) { continue }
            e.apply(); changed = true
        }
        return changed
    }

    /** After login: fill blank local profile fields from the cloud, or push local ones up if the cloud lacks them. */
    suspend fun syncProfileWithCloud(email: String): User? {
        val id = email.lowercase().trim()
        val local = userDao.getUserByEmail(id) ?: return null
        val p = firebaseService.loadProfile(id) ?: run {
            firebaseService.saveProfile(local, ProfileImageStore.toBase64(context, local.email)); return local
        }
        fun pick(l: String, key: String) = if (l.isBlank()) (p[key] as? String).orEmpty() else l
        var image = local.profileImageUri
        if (image.isBlank()) {
            val b64 = p["photoBase64"] as? String ?: ""
            if (b64.isNotEmpty()) image = ProfileImageStore.fromBase64(context, id, b64)
        }
        val merged = local.copy(
            fullName = pick(local.fullName, "fullName"),
            mobileNumber = pick(local.mobileNumber, "mobileNumber"),
            city = pick(local.city, "city"),
            state = pick(local.state, "state"),
            profileImageUri = image
        )
        if (merged != local) userDao.insertUser(merged)
        firebaseService.saveProfile(merged, ProfileImageStore.toBase64(context, merged.email))
        return merged
    }

    fun loginAsUser(user: User) {
        _userEmailState.value = user.email
        _userNameState.value = user.fullName
        _userRoleState.value = user.role
        _userAvatarState.value = user.avatar
        // Older builds filled placeholders for the admin account; never show them as real data
        val placeholder = user.mobileNumber == "9999999999"
        _userMobileState.value = if (placeholder) "" else user.mobileNumber
        _userCityState.value = if (placeholder && user.city == "Ajmer") "" else user.city
        _userStateProvinceState.value = if (placeholder && user.state == "Rajasthan") "" else user.state
        _userProfileImageUriState.value = user.profileImageUri
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

    suspend fun deleteAccount(): Boolean {
        val success = firebaseService.deleteUserAccount()
        logout()
        return success
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
