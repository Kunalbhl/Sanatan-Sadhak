package com.example.data

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class FirebaseService(private val context: Context) {
    private val tag = "FirebaseService"

    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null

    private val _isFirebaseAvailable = MutableStateFlow(false)
    val isFirebaseAvailable: StateFlow<Boolean> = _isFirebaseAvailable

    private val _currentUserState = MutableStateFlow<FirebaseUser?>(null)
    val currentUserState: StateFlow<FirebaseUser?> = _currentUserState

    init {
        try {
            // Attempt to initialize Firebase explicitly.
            FirebaseApp.initializeApp(context)
            
            // Attempt to initialize Firebase services.
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            _isFirebaseAvailable.value = true
            _currentUserState.value = auth?.currentUser
            Log.d(tag, "Firebase successfully initialized.")
        } catch (e: Exception) {
            _isFirebaseAvailable.value = false
            Log.w(tag, "Firebase initialization failed (likely missing google-services.json): ${e.message}")
        }
    }

    fun getCurrentUserId(): String {
        return auth?.currentUser?.uid ?: "local_sadhak_user_108"
    }

    fun getCurrentUserEmail(): String {
        return auth?.currentUser?.email ?: "sadhak@sanatandharma.org"
    }

    fun getCurrentUserName(): String {
        return auth?.currentUser?.displayName ?: "Devoted Sadhak"
    }

    suspend fun signUpAnonymously(): Boolean {
        if (!_isFirebaseAvailable.value || auth == null) return false
        return try {
            val result = auth!!.signInAnonymously().await()
            _currentUserState.value = result.user
            true
        } catch (e: Exception) {
            Log.e(tag, "Anonymous sign in failed: ${e.message}")
            false
        }
    }

    suspend fun signUpWithEmail(email: String, password: String): Boolean {
        if (!_isFirebaseAvailable.value || auth == null) return false
        return try {
            val result = auth!!.createUserWithEmailAndPassword(email, password).await()
            _currentUserState.value = result.user
            true
        } catch (e: Exception) {
            Log.e(tag, "Email signup failed: ${e.message}")
            false
        }
    }

    suspend fun signInWithEmail(email: String, password: String): Boolean {
        if (!_isFirebaseAvailable.value || auth == null) return false
        return try {
            val result = auth!!.signInWithEmailAndPassword(email, password).await()
            _currentUserState.value = result.user
            true
        } catch (e: Exception) {
            Log.e(tag, "Email signin failed: ${e.message}")
            false
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Boolean {
        if (!_isFirebaseAvailable.value || auth == null) return false
        return try {
            auth!!.sendPasswordResetEmail(email).await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Password reset email failed: ${e.message}")
            false
        }
    }

    fun signOut() {
        auth?.signOut()
        _currentUserState.value = null
    }

    // --- FIRESTORE PERSISTENCE ---
    suspend fun saveUserToFirestore(user: User): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            val userData = hashMapOf(
                "email" to user.email,
                "fullName" to user.fullName,
                // Roles are granted by an admin in Firebase, never self-assigned from the app
                "avatar" to user.avatar,
                "mobileNumber" to user.mobileNumber,
                "city" to user.city,
                "state" to user.state,
                "profileImageUri" to user.profileImageUri,
                "createdAt" to System.currentTimeMillis()
            )
            val uid = auth?.currentUser?.uid ?: user.email.replace(".", "_")
            firestore!!.collection("users")
                .document(uid)
                .set(userData, com.google.firebase.firestore.SetOptions.merge())
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save user to Firestore: ${e.message}")
            false
        }
    }
    /** Save/merge profile fields (incl. a small base64 photo) so they survive reinstall and new devices. */
    suspend fun saveProfile(user: User, photoBase64: String): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            val data = hashMapOf<String, Any>(
                "email" to user.email.lowercase().trim(),
                "fullName" to user.fullName,
                "avatar" to user.avatar,
                "mobileNumber" to user.mobileNumber,
                "city" to user.city,
                "state" to user.state,
                "updatedAt" to System.currentTimeMillis()
            )
            if (photoBase64.isNotEmpty()) data["photoBase64"] = photoBase64
            val uid = auth?.currentUser?.uid ?: user.email.replace(".", "_")
            firestore!!.collection("users").document(uid)
                .set(data, com.google.firebase.firestore.SetOptions.merge()).await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save profile: ${e.message}"); false
        }
    }

    /** Signed-in user's cloud profile (null if none / offline). */
    suspend fun loadProfile(email: String): Map<String, Any>? {
        if (!_isFirebaseAvailable.value || firestore == null) return null
        return try {
            val uid = auth?.currentUser?.uid
            val doc = if (uid != null) firestore!!.collection("users").document(uid).get().await() else null
            if (doc != null && doc.exists()) doc.data
            else firestore!!.collection("users").whereEqualTo("email", email.lowercase().trim()).limit(1)
                .get().await().documents.firstOrNull()?.data
        } catch (e: Exception) {
            Log.e(tag, "Failed to load profile: ${e.message}"); null
        }
    }

    suspend fun savePostToFirestore(post: CommunityPost): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            val postData = hashMapOf(
                "category" to post.category,
                "author" to post.author,
                "title" to post.title,
                "content" to post.content,
                "upvotes" to post.upvotes,
                "downvotes" to post.downvotes,
                "commentsCount" to post.commentsCount,
                "createdAt" to post.createdAt,
                "isPinned" to post.isPinned,
                "sourceType" to post.sourceType,
                "sourceId" to post.sourceId,
                "sourceTitle" to post.sourceTitle,
                "authorVerified" to post.authorEmail.isNotBlank(),
                "authorCity" to post.authorCity
            )
            firestore!!.collection("community_posts")
                .document(post.id.toString())
                .set(postData)
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save post to Firestore: ${e.message}")
            false
        }
    }

    suspend fun deletePostFromFirestore(postId: String): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            firestore!!.collection("community_posts")
                .document(postId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to delete post from Firestore: ${e.message}")
            false
        }
    }

    suspend fun fetchPostsFromFirestore(): List<CommunityPost> {
        if (!_isFirebaseAvailable.value || firestore == null) return emptyList()
        return try {
            val snapshot = firestore!!.collection("community_posts")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                try {
                    CommunityPost(
                        id = doc.id.toIntOrNull() ?: 0,
                        category = doc.getString("category") ?: "",
                        author = doc.getString("author") ?: "Sadhak",
                        title = doc.getString("title") ?: "",
                        content = doc.getString("content") ?: "",
                        upvotes = doc.getLong("upvotes")?.toInt() ?: 1,
                        downvotes = doc.getLong("downvotes")?.toInt() ?: 0,
                        commentsCount = doc.getLong("commentsCount")?.toInt() ?: 0,
                        createdAt = doc.getLong("createdAt") ?: System.currentTimeMillis(),
                        isPinned = doc.getBoolean("isPinned") ?: false,
                        sourceType = doc.getString("sourceType") ?: "",
                        sourceId = doc.getString("sourceId") ?: "",
                        sourceTitle = doc.getString("sourceTitle") ?: "",
                        authorEmail = if (doc.getBoolean("authorVerified") == true) "verified" else "",
                        authorCity = doc.getString("authorCity") ?: ""
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch posts from Firestore: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteUserAccount(): Boolean {
        if (!_isFirebaseAvailable.value || auth == null) return false
        return try {
            val currentUser = auth?.currentUser
            if (currentUser != null) {
                val uid = currentUser.uid
                firestore?.collection("users")?.document(uid)?.delete()?.await()
                currentUser.delete().await()
                _currentUserState.value = null
                true
            } else false
        } catch (e: Exception) {
            Log.e(tag, "Failed to delete user account: ${e.message}")
            false
        }
    }

    // --- YOUTUBE VIDEOS REMOTE PERSISTENCE ---
    suspend fun saveBhaktiVideoToFirestore(video: RemoteBhaktiVideo): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null || video.videoId.isBlank()) return false
        return try {
            firestore!!.collection("bhakti_videos")
                .document(video.videoId)
                .set(video.toFirestoreMap())
                .await()
            Log.d(tag, "Successfully saved video ${video.videoId} to Firestore.")
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save video to Firestore: ${e.message}")
            false
        }
    }

    suspend fun deleteBhaktiVideoFromFirestore(videoId: String): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null || videoId.isBlank()) return false
        return try {
            firestore!!.collection("bhakti_videos")
                .document(videoId)
                .delete()
                .await()
            Log.d(tag, "Successfully deleted video $videoId from Firestore.")
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to delete video from Firestore: ${e.message}")
            false
        }
    }

    suspend fun fetchBhaktiVideosFromFirestore(includeDrafts: Boolean = false): List<RemoteBhaktiVideo> {
        if (!_isFirebaseAvailable.value || firestore == null) return emptyList()
        return try {
            val query = if (includeDrafts) {
                firestore!!.collection("bhakti_videos")
            } else {
                firestore!!.collection("bhakti_videos").whereEqualTo("published", true)
            }
            val snapshot = query.get().await()
            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                RemoteBhaktiVideo.fromFirestoreMap(doc.id, data)
            }
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch videos from Firestore: ${e.message}")
            emptyList()
        }
    }

    suspend fun updateBhaktiVideoPublishStatus(videoId: String, published: Boolean): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null || videoId.isBlank()) return false
        return try {
            firestore!!.collection("bhakti_videos")
                .document(videoId)
                .update(
                    mapOf(
                        "published" to published,
                        "updatedAt" to System.currentTimeMillis()
                    )
                )
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to update video publish status in Firestore: ${e.message}")
            false
        }
    }

    // --- OBSERVABILITY & DIAGNOSTICS PERSISTENCE ---
    suspend fun saveDiagnosticErrorToFirestore(error: DiagnosticErrorRecord): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            val data = mapOf(
                "fingerprint" to error.fingerprint,
                "category" to error.category.name,
                "severity" to error.severity.name,
                "message" to error.message,
                "stackTracePreview" to error.stackTracePreview,
                "occurrenceCount" to error.occurrenceCount,
                "firstSeenTimestamp" to error.firstSeenTimestamp,
                "lastSeenTimestamp" to error.lastSeenTimestamp,
                "status" to error.status.name,
                "appVersion" to error.appVersion,
                "deviceInfo" to error.deviceInfo,
                "featureContext" to error.featureContext
            )
            firestore!!.collection("diagnostic_errors")
                .document(error.fingerprint)
                .set(data)
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save diagnostic error to Firestore: ${e.message}")
            false
        }
    }

    suspend fun fetchDiagnosticErrorsFromFirestore(): List<DiagnosticErrorRecord> {
        if (!_isFirebaseAvailable.value || firestore == null) return emptyList()
        return try {
            val snapshot = firestore!!.collection("diagnostic_errors").get().await()
            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                DiagnosticErrorRecord(
                    fingerprint = doc.id,
                    category = try { ErrorCategory.valueOf(data["category"] as? String ?: "APP_CORE") } catch (e: Exception) { ErrorCategory.APP_CORE },
                    severity = try { ErrorSeverity.valueOf(data["severity"] as? String ?: "LOW") } catch (e: Exception) { ErrorSeverity.LOW },
                    message = data["message"] as? String ?: "",
                    stackTracePreview = data["stackTracePreview"] as? String ?: "",
                    occurrenceCount = (data["occurrenceCount"] as? Number)?.toInt() ?: 1,
                    firstSeenTimestamp = (data["firstSeenTimestamp"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                    lastSeenTimestamp = (data["lastSeenTimestamp"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                    status = try { ErrorStatus.valueOf(data["status"] as? String ?: "OPEN") } catch (e: Exception) { ErrorStatus.OPEN },
                    appVersion = data["appVersion"] as? String ?: "1.3.1",
                    deviceInfo = data["deviceInfo"] as? String ?: "Android",
                    featureContext = data["featureContext"] as? String ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch diagnostic errors from Firestore: ${e.message}")
            emptyList()
        }
    }

    suspend fun updateDiagnosticErrorStatusInFirestore(fingerprint: String, status: String): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null || fingerprint.isBlank()) return false
        return try {
            firestore!!.collection("diagnostic_errors")
                .document(fingerprint)
                .update("status", status)
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to update diagnostic error status in Firestore: ${e.message}")
            false
        }
    }

    // --- AUDIT TRAIL PERSISTENCE ---
    suspend fun saveAuditLogToFirestore(log: AuditLog): Boolean {
        if (!_isFirebaseAvailable.value || firestore == null) return false
        return try {
            val data = mapOf(
                "id" to log.id,
                "timestamp" to log.timestamp,
                "adminIdentifier" to log.adminIdentifier,
                "action" to log.action,
                "entityType" to log.entityType,
                "entityId" to log.entityId,
                "oldValue" to log.oldValue,
                "newValue" to log.newValue
            )
            firestore!!.collection("admin_audit_logs")
                .document(log.id)
                .set(data)
                .await()
            true
        } catch (e: Exception) {
            Log.e(tag, "Failed to save audit log to Firestore: ${e.message}")
            false
        }
    }

    suspend fun fetchAuditLogsFromFirestore(): List<AuditLog> {
        if (!_isFirebaseAvailable.value || firestore == null) return emptyList()
        return try {
            val snapshot = firestore!!.collection("admin_audit_logs")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .limit(100)
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                AuditLog(
                    id = doc.id,
                    timestamp = (data["timestamp"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                    adminIdentifier = data["adminIdentifier"] as? String ?: "Admin",
                    action = data["action"] as? String ?: "",
                    entityType = data["entityType"] as? String ?: "",
                    entityId = data["entityId"] as? String ?: "",
                    oldValue = data["oldValue"] as? String ?: "",
                    newValue = data["newValue"] as? String ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e(tag, "Failed to fetch audit logs from Firestore: ${e.message}")
            emptyList()
        }
    }

    // --- ROLE AUTHORIZATION CHECKS ---
    suspend fun getUserRole(email: String): String {
        if (!_isFirebaseAvailable.value || firestore == null || email.isBlank()) {
            return if (email.equals("admin@sanatansadhak.org", ignoreCase = true) ||
                email.equals("kunalpareekusa@gmail.com", ignoreCase = true)
            ) "SUPER_ADMIN" else "DEVOTEE"
        }
        return try {
            val uid = email.replace(".", "_")
            val doc = firestore!!.collection("users").document(uid).get().await()
            val role = doc.getString("role") ?: "DEVOTEE"
            if (email.equals("admin@sanatansadhak.org", ignoreCase = true) ||
                email.equals("kunalpareekusa@gmail.com", ignoreCase = true)
            ) {
                "SUPER_ADMIN"
            } else {
                role
            }
        } catch (e: Exception) {
            Log.w(tag, "Failed to check user role from Firestore, fallback: ${e.message}")
            if (email.equals("admin@sanatansadhak.org", ignoreCase = true) ||
                email.equals("kunalpareekusa@gmail.com", ignoreCase = true)
            ) "SUPER_ADMIN" else "DEVOTEE"
        }
    }

    suspend fun isUserAdmin(email: String): Boolean {
        val role = getUserRole(email)
        return role.equals("ADMIN", ignoreCase = true) || role.equals("SUPER_ADMIN", ignoreCase = true)
    }
}
