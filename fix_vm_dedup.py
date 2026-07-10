import re

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

# Define the block of text to remove
target = """    fun upvoteComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (upEmails.contains(email)) {
                upEmails.remove(email)
            } else {
                upEmails.add(email)
                downEmails.remove(email)
            }
            
            repository.updateComment(comment.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            ))
        }
    }

    fun downvoteComment(comment: com.example.data.PostComment) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            
            var upEmails = comment.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = comment.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (downEmails.contains(email)) {
                downEmails.remove(email)
            } else {
                downEmails.add(email)
                upEmails.remove(email)
            }
            
            repository.updateComment(comment.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            ))
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            repository.deleteComment(id)
        }
    }

    fun togglePinPost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }

    fun upvotePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            if (upEmails.contains(email)) upEmails.remove(email)
            else { upEmails.add(email); downEmails.remove(email) }
            repository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun downvotePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            if (downEmails.contains(email)) downEmails.remove(email)
            else { downEmails.add(email); upEmails.remove(email) }
            repository.updatePost(post.copy(upvotes = upEmails.size, downvotes = downEmails.size, upvotedByEmails = upEmails.joinToString(","), downvotedByEmails = downEmails.joinToString(",")))
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            repository.deletePost(id)
        }
    }

    fun deletePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            repository.deletePost(post.id)
        }
    }
"""

if text.count(target) > 0:
    text = text.replace(target, "", 1)

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
