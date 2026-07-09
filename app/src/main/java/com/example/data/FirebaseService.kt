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

    fun signOut() {
        auth?.signOut()
        _currentUserState.value = null
    }

    // --- FIRESTORE PERSISTENCE ---
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
                "isPinned" to post.isPinned
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
                        isPinned = doc.getBoolean("isPinned") ?: false
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
}
