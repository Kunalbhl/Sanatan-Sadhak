with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

target = """    fun getComments(postId: Int): StateFlow<List<com.example.data.PostComment>> {"""

replacement = """
    fun upvotePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (upEmails.contains(email)) {
                // remove upvote
                upEmails.remove(email)
            } else {
                // add upvote, remove downvote if exists
                upEmails.add(email)
                downEmails.remove(email)
            }
            
            val updatedPost = post.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            )
            repository.updatePost(updatedPost)
        }
    }

    fun downvotePost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value
            if (email.isEmpty()) return@launch
            
            var upEmails = post.upvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            var downEmails = post.downvotedByEmails.split(",").filter { it.isNotEmpty() }.toMutableList()
            
            if (downEmails.contains(email)) {
                // remove downvote
                downEmails.remove(email)
            } else {
                // add downvote, remove upvote if exists
                downEmails.add(email)
                upEmails.remove(email)
            }
            
            val updatedPost = post.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            )
            repository.updatePost(updatedPost)
        }
    }
    
    fun togglePinPost(post: com.example.data.CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }
    
    fun deletePost(id: Int) {
        viewModelScope.launch {
            repository.deletePost(id)
        }
    }

    fun upvoteComment(comment: com.example.data.PostComment) {
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

    fun getComments(postId: Int): StateFlow<List<com.example.data.PostComment>> {"""

text = text.replace(target, replacement)
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
