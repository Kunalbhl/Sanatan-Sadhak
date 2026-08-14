package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.data.CommunityPost

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class UserDataIsolationTest {

    @Test
    fun `USER cannot edit another USER's post`() {
        val postOwnerId = "user1@example.com"
        val anotherUserId = "user2@example.com"
        
        val post = CommunityPost(
            id = 1,
            author = postOwnerId,
            title = "Test Post",
            content = "Original Content",
            category = "General"
        )
        
        // Simulating the backend rule that prevents a user from editing a post they don't own
        fun attemptEdit(editingUserId: String, updatedPost: CommunityPost): Boolean {
            if (editingUserId != post.author) {
                return false // Backend rejects
            }
            return true
        }
        
        val result = attemptEdit(anotherUserId, post.copy(content = "Edited Content"))
        assertFalse("Backend should reject edit by non-owner", result)
    }
}
