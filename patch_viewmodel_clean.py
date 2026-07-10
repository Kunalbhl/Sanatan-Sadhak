import sys
with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "r") as f:
    text = f.read()

# I will just remove the old functions completely, or remove the ones I added and replace the old ones.
old_block = """    fun upvotePost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(upvotes = post.upvotes + 1))
            statsLikesCount.value += 1
        }
    }

    fun downvotePost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(downvotes = post.downvotes + 1))
            statsLikesCount.value += 1
        }
    }

    fun togglePinPost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }"""

if old_block in text:
    text = text.replace(old_block, "")
    print("Old post functions removed")

with open("app/src/main/java/com/example/ui/viewmodel/SadhakViewModel.kt", "w") as f:
    f.write(text)
