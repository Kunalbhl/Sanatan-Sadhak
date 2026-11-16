package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class CommunityRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val postDao = db.communityPostDao()
    private val commentDao = db.postCommentDao()
    private val instagramPostDao = db.instagramPostDao()

    val firebaseService = FirebaseService(context)

    fun getPosts(): Flow<List<CommunityPost>> = postDao.getAllPosts()
    fun getAllPosts(): Flow<List<CommunityPost>> = postDao.getAllPosts()

    suspend fun getPostById(id: Int): CommunityPost? = postDao.getPostById(id)

    suspend fun addPost(post: CommunityPost) {
        postDao.insertPost(post)
        if (firebaseService.isFirebaseAvailable.value) {
            firebaseService.savePostToFirestore(post)
        }
    }

    suspend fun insertPost(post: CommunityPost) = addPost(post)

    suspend fun updatePost(post: CommunityPost) {
        postDao.updatePost(post)
        if (firebaseService.isFirebaseAvailable.value) {
            firebaseService.savePostToFirestore(post) // savePost acts as an upsert/set
        }
    }

    suspend fun deletePost(id: Int) {
        postDao.deletePostById(id)
        if (firebaseService.isFirebaseAvailable.value) {
            firebaseService.deletePostFromFirestore(id.toString())
        }
    }

    suspend fun deletePostWithAuthCheck(postId: Int, currentUser: String, isAdmin: Boolean): Boolean {
        val post = postDao.getPostById(postId) ?: return false
        if (isAdmin || post.author == currentUser) {
            deletePost(postId)
            return true
        }
        return false
    }

    suspend fun togglePinPost(post: CommunityPost) {
        updatePost(post.copy(isPinned = !post.isPinned))
    }

    suspend fun upvotePost(post: CommunityPost, userIdentifier: String) {
        val user = userIdentifier.ifEmpty { "guest" }
        val upList = post.upvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()
        val downList = post.downvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()

        if (upList.contains(user)) {
            upList.remove(user)
        } else {
            upList.add(user)
            downList.remove(user)
        }

        updatePost(post.copy(
            upvotes = upList.size,
            downvotes = downList.size,
            upvotedByEmails = upList.joinToString(","),
            downvotedByEmails = downList.joinToString(",")
        ))
    }

    suspend fun downvotePost(post: CommunityPost, userIdentifier: String) {
        val user = userIdentifier.ifEmpty { "guest" }
        val upList = post.upvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()
        val downList = post.downvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()

        if (downList.contains(user)) {
            downList.remove(user)
        } else {
            downList.add(user)
            upList.remove(user)
        }

        updatePost(post.copy(
            upvotes = upList.size,
            downvotes = downList.size,
            upvotedByEmails = upList.joinToString(","),
            downvotedByEmails = downList.joinToString(",")
        ))
    }

    fun getUserVote(postId: Int, userIdentifier: String): Int {
        val user = userIdentifier.ifEmpty { "guest" }
        return kotlinx.coroutines.runBlocking {
            val post = postDao.getPostById(postId) ?: return@runBlocking 0
            val upList = post.upvotedByEmails.split(",").filter { it.isNotBlank() }
            val downList = post.downvotedByEmails.split(",").filter { it.isNotBlank() }
            if (upList.contains(user)) 1 else if (downList.contains(user)) -1 else 0
        }
    }

    fun getComments(postId: Int): Flow<List<PostComment>> = commentDao.getCommentsForPost(postId)
    fun getCommentsForPost(postId: Int): Flow<List<PostComment>> = commentDao.getCommentsForPost(postId)
    fun getAllComments(): Flow<List<PostComment>> = commentDao.getAllComments()

    suspend fun addComment(comment: PostComment) {
        commentDao.insertComment(comment)
        val post = postDao.getPostById(comment.postId)
        if (post != null) {
            updatePost(post.copy(commentsCount = post.commentsCount + 1))
        }
    }

    suspend fun insertComment(comment: PostComment) = addComment(comment)

    suspend fun updateComment(comment: PostComment) = commentDao.updateComment(comment)

    suspend fun deleteComment(id: Int) {
        val comment = commentDao.getCommentById(id)
        val targetPostId = comment?.postId
        commentDao.deleteCommentById(id)
        if (targetPostId != null) {
            val post = postDao.getPostById(targetPostId)
            if (post != null && post.commentsCount > 0) {
                updatePost(post.copy(commentsCount = post.commentsCount - 1))
            }
        }
    }

    fun getInstagramPosts(): Flow<List<InstagramPost>> = instagramPostDao.getAllInstagramPosts()
    suspend fun addInstagramPost(post: InstagramPost) = instagramPostDao.insertInstagramPost(post)
    suspend fun updateInstagramPost(post: InstagramPost) = instagramPostDao.updateInstagramPost(post)
    suspend fun deleteInstagramPostById(id: Int) = instagramPostDao.deleteById(id)
}
