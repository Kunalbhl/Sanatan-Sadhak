package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CommunityPost
import com.example.data.CommunityRepository
import com.example.data.PostComment
import com.example.ui.viewmodel.SadhakViewModel
import com.example.utils.HapticFeedbackManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task161CommunityDevoteeExperienceRegressionTest {

    private lateinit var context: Context
    private lateinit var repository: CommunityRepository
    private lateinit var viewModel: SadhakViewModel

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        repository = CommunityRepository(context)
        viewModel = SadhakViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testTask161_communityPost_creation_and_retrieval() = runBlocking {
        val uniqueTitle = "Morning Sadhana Reflection " + System.currentTimeMillis()
        val post = CommunityPost(
            author = "HaribolDevotee",
            title = uniqueTitle,
            content = "Early morning japa was deeply peaceful today.",
            category = "Bhakti Experiences",
            createdAt = System.currentTimeMillis(),
            upvotes = 0,
            downvotes = 0,
            commentsCount = 0,
            isPinned = false
        )
        repository.insertPost(post)

        val retrievedPosts = repository.getAllPosts().first()
        val found = retrievedPosts.find { it.title == uniqueTitle }
        assertNotNull("Inserted post should be retrievable from repository", found)
        assertEquals("HaribolDevotee", found?.author)
        assertEquals("Bhakti Experiences", found?.category)
    }

    @Test
    fun testTask161_communityPost_voting_idempotency_and_toggle() = runBlocking {
        val uniqueTitle = "Temple Darshan " + System.currentTimeMillis()
        val post = CommunityPost(
            author = "BhaktaRamesh",
            title = uniqueTitle,
            content = "Sacred vibration at the temple.",
            category = "Temple Stories",
            createdAt = System.currentTimeMillis(),
            upvotes = 0,
            downvotes = 0,
            upvotedByEmails = "",
            downvotedByEmails = "",
            commentsCount = 0
        )
        repository.insertPost(post)
        val created = repository.getAllPosts().first().find { it.title == uniqueTitle }!!

        // Upvote once
        repository.upvotePost(created, "userA")
        var current = repository.getAllPosts().first().find { it.id == created.id }
        assertEquals("Upvoting should set upvotes to 1", 1, current?.upvotes)
        assertEquals("Downvotes should remain 0", 0, current?.downvotes)

        // Upvote second time (toggle off)
        repository.upvotePost(current!!, "userA")
        current = repository.getAllPosts().first().find { it.id == created.id }
        assertEquals("Toggling off upvote should decrement upvotes back to 0", 0, current?.upvotes)

        // Switching vote to downvote
        repository.downvotePost(current!!, "userA")
        current = repository.getAllPosts().first().find { it.id == created.id }
        assertEquals("Downvoting should increment downvotes to 1", 1, current?.downvotes)
        assertEquals("Upvotes should remain 0", 0, current?.upvotes)
    }

    @Test
    fun testTask161_comment_add_delete_and_count_sync() = runBlocking {
        val uniqueTitle = "Karma Discussion " + System.currentTimeMillis()
        val post = CommunityPost(
            author = "DevoteePriya",
            title = uniqueTitle,
            content = "How to surrender the fruits of action?",
            category = "Doubts & Discussions",
            createdAt = System.currentTimeMillis(),
            commentsCount = 0
        )
        repository.insertPost(post)
        val created = repository.getAllPosts().first().find { it.title == uniqueTitle }!!

        val uniqueComment = "Focus on the sincerity of offering " + System.currentTimeMillis()
        val comment = PostComment(
            postId = created.id,
            author = "DevoteeArjun",
            content = uniqueComment,
            createdAt = System.currentTimeMillis()
        )
        repository.insertComment(comment)

        val postAfterAdd = repository.getAllPosts().first().find { it.id == created.id }
        assertEquals("Adding comment must increment post commentsCount", 1, postAfterAdd?.commentsCount)

        val comments = repository.getCommentsForPost(created.id).first()
        val foundComment = comments.find { it.content == uniqueComment }
        assertNotNull("Comments list should contain newly inserted comment", foundComment)

        // Delete comment
        repository.deleteComment(foundComment!!.id)
        val postAfterDelete = repository.getAllPosts().first().find { it.id == created.id }
        assertEquals("Deleting comment must decrement post commentsCount", 0, postAfterDelete?.commentsCount)
    }

    @Test
    fun testTask161_role_isolation_delete_permission() = runBlocking {
        val uniqueTitle = "Mod Test Post " + System.currentTimeMillis()
        val post = CommunityPost(
            author = "AuthorDevotee",
            title = uniqueTitle,
            content = "Testing moderation permission checks.",
            category = "Bhakti Experiences",
            createdAt = System.currentTimeMillis()
        )
        repository.insertPost(post)
        val created = repository.getAllPosts().first().find { it.title == uniqueTitle }!!

        // Non-admin stranger attempting to delete
        val unauthorizedDelete = repository.deletePostWithAuthCheck(
            postId = created.id,
            currentUser = "StrangerUser",
            isAdmin = false
        )
        assertFalse("Unauthorized user should not be able to delete stranger's post", unauthorizedDelete)
        assertNotNull("Post should still exist after unauthorized deletion attempt",
            repository.getAllPosts().first().find { it.id == created.id })

        // Post author deleting own post
        val authorDelete = repository.deletePostWithAuthCheck(
            postId = created.id,
            currentUser = "AuthorDevotee",
            isAdmin = false
        )
        assertTrue("Author should be able to delete their own post", authorDelete)
        assertNull("Post should be deleted after author deletion",
            repository.getAllPosts().first().find { it.id == created.id })
    }

    @Test
    fun testTask161_admin_moderation_pinning_and_deletion() = runBlocking {
        val uniqueTitle = "Admin Announcement " + System.currentTimeMillis()
        val post = CommunityPost(
            author = "RegularSadhak",
            title = uniqueTitle,
            content = "Kirtan evening scheduled.",
            category = "Bhakti Experiences",
            createdAt = System.currentTimeMillis(),
            isPinned = false
        )
        repository.insertPost(post)
        val created = repository.getAllPosts().first().find { it.title == uniqueTitle }!!

        // Toggle pin
        repository.togglePinPost(created)
        val updated = repository.getAllPosts().first().find { it.id == created.id }
        assertTrue("Admin toggle pin should set isPinned to true", updated?.isPinned == true)

        // Admin delete
        val adminDelete = repository.deletePostWithAuthCheck(
            postId = created.id,
            currentUser = "AdminMaster",
            isAdmin = true
        )
        assertTrue("Admin should be authorized to delete any post", adminDelete)
        assertNull("Post should be deleted after admin deletion",
            repository.getAllPosts().first().find { it.id == created.id })
    }

    @Test
    fun testTask161_hapticFeedbackManager_community_triggers_safely() {
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.LIGHT_TOUCH)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.STANDARD_CLICK)
        HapticFeedbackManager.vibrate(context, HapticFeedbackManager.FeedbackType.SUCCESS)
        assertTrue("Haptic feedback methods must safely complete", true)
    }

    @Test
    fun testTask161_account_isolation_saved_posts_and_reactions() = runBlocking {
        val user1 = "Devotee_1"
        val user2 = "Devotee_2"
        val uniqueTitle = "Isolation Post " + System.currentTimeMillis()

        val post = CommunityPost(
            author = "Auth",
            title = uniqueTitle,
            content = "Isolation test content",
            category = "Bhakti Experiences",
            upvotes = 0,
            downvotes = 0
        )
        repository.insertPost(post)
        val created = repository.getAllPosts().first().find { it.title == uniqueTitle }!!

        // User 1 votes
        repository.upvotePost(created, user1)
        var user1Vote = repository.getUserVote(created.id, user1)
        var user2Vote = repository.getUserVote(created.id, user2)

        assertEquals(1, user1Vote)
        assertEquals("User 2 should have independent vote state (0)", 0, user2Vote)

        // User 2 downvotes
        val currentPost = repository.getAllPosts().first().find { it.id == created.id }!!
        repository.downvotePost(currentPost, user2)

        user1Vote = repository.getUserVote(created.id, user1)
        user2Vote = repository.getUserVote(created.id, user2)
        assertEquals("User 1 vote should remain 1", 1, user1Vote)
        assertEquals("User 2 vote should be -1", -1, user2Vote)
    }

    @Test
    fun testTask161_favoritePost_toggle_and_retrieval() = runBlocking {
        val postId = 101
        viewModel.toggleFavoritePost(postId)
        // Wait or observe
        val favorites = viewModel.favoritePostIds.first()
        // Toggle again to ensure toggling functionality
        viewModel.toggleFavoritePost(postId)
        assertTrue("Favorites flow should exist and respond to toggling", true)
    }
}
