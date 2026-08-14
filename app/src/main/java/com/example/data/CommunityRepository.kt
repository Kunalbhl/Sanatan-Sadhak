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

    suspend fun addPost(post: CommunityPost) {
        postDao.insertPost(post)
        if (firebaseService.isFirebaseAvailable.value) {
            firebaseService.savePostToFirestore(post)
        }
    }

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

    fun getComments(postId: Int): Flow<List<PostComment>> = commentDao.getCommentsForPost(postId)
    fun getAllComments(): Flow<List<PostComment>> = commentDao.getAllComments()
    suspend fun addComment(comment: PostComment) = commentDao.insertComment(comment)
    suspend fun updateComment(comment: PostComment) = commentDao.updateComment(comment)
    suspend fun deleteComment(id: Int) = commentDao.deleteCommentById(id)

    fun getInstagramPosts(): Flow<List<InstagramPost>> = instagramPostDao.getAllInstagramPosts()
    suspend fun addInstagramPost(post: InstagramPost) = instagramPostDao.insertInstagramPost(post)
    suspend fun updateInstagramPost(post: InstagramPost) = instagramPostDao.updateInstagramPost(post)
    suspend fun deleteInstagramPostById(id: Int) = instagramPostDao.deleteById(id)
}
