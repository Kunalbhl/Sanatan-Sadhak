import sys
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

new_methods = """
    fun upvotePost(post: com.example.data.CommunityPost) {
        if (userRole.value == "Guest") return
        val email = userEmail.value
        val upvotedList = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        val downvotedList = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        
        var newUpvotes = post.upvotes
        var newDownvotes = post.downvotes

        if (upvotedList.contains(email)) {
            upvotedList.remove(email)
            newUpvotes--
        } else {
            upvotedList.add(email)
            newUpvotes++
            if (downvotedList.contains(email)) {
                downvotedList.remove(email)
                newDownvotes--
            }
        }
        
        val updatedPost = post.copy(
            upvotes = newUpvotes,
            downvotes = newDownvotes,
            upvotedByEmails = upvotedList.joinToString(","),
            downvotedByEmails = downvotedList.joinToString(",")
        )
        viewModelScope.launch {
            repository.updateCommunityPost(updatedPost)
        }
    }

    fun downvotePost(post: com.example.data.CommunityPost) {
        if (userRole.value == "Guest") return
        val email = userEmail.value
        val upvotedList = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        val downvotedList = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
        
        var newUpvotes = post.upvotes
        var newDownvotes = post.downvotes

        if (downvotedList.contains(email)) {
            downvotedList.remove(email)
            newDownvotes--
        } else {
            downvotedList.add(email)
            newDownvotes++
            if (upvotedList.contains(email)) {
                upvotedList.remove(email)
                newUpvotes--
            }
        }
        
        val updatedPost = post.copy(
            upvotes = newUpvotes,
            downvotes = newDownvotes,
            upvotedByEmails = upvotedList.joinToString(","),
            downvotedByEmails = downvotedList.joinToString(",")
        )
        viewModelScope.launch {
            repository.updateCommunityPost(updatedPost)
        }
    }

    fun togglePinPost(post: com.example.data.CommunityPost) {
        if (isAdmin.value) {
            val updatedPost = post.copy(isPinned = !post.isPinned)
            viewModelScope.launch {
                repository.updateCommunityPost(updatedPost)
            }
        }
    }

    fun deletePost(post: com.example.data.CommunityPost) {
        if (isAdmin.value || post.author == userName.value) {
            viewModelScope.launch {
                repository.deleteCommunityPost(post.id)
            }
        }
    }
"""

# Insert before "fun sendMessage("
target = "fun sendMessage(prompt: String)"
if target in text:
    text = text.replace(target, new_methods + "\n    " + target)
    with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
        f.write(text)
    print("ViewModel updated with upvote/downvote/pin/delete")
else:
    print("Target not found in ViewModel")
