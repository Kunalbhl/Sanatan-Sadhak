package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CommunityPost
import com.example.data.CommunityRepository
import com.example.data.InstagramPost
import com.example.data.PostComment
import com.example.data.UserRepository
import com.example.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommunityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CommunityRepository(application)
    private val userRepository = UserRepository.getInstance(application)

    private val _communitySortBy = MutableStateFlow("New")
    val communitySortBy: StateFlow<String> = _communitySortBy

    private val _selectedPostId = MutableStateFlow<Int?>(null)
    val selectedPostId: StateFlow<Int?> = _selectedPostId

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    val userEmail: StateFlow<String> = userRepository.userEmailState
    val userName: StateFlow<String> = userRepository.userNameState
    val userRole: StateFlow<String> = userRepository.userRoleState
    val isPublicPostingEnabled: StateFlow<Boolean> = userRepository.isPublicPostingEnabled

    val posts: StateFlow<List<CommunityPost>> = repository.getPosts()
        .combine(_communitySortBy) { list, sort ->
            val sortedList = when (sort) {
                "Top" -> list.sortedByDescending { it.upvotes - it.downvotes }
                "Discussed" -> list.sortedByDescending { it.commentsCount }
                else -> list.sortedByDescending { it.createdAt }
            }
            sortedList.sortedWith(compareByDescending { it.isPinned })
        }.combine(_selectedCategory) { list, category ->
            if (category == "All") list else list.filter { it.category == category }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val postsUiState: StateFlow<UiState<List<CommunityPost>>> = posts.map { list ->
        if (list.isEmpty()) UiState.Empty else UiState.Success(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState.Loading)

    val allComments: StateFlow<List<PostComment>> = repository.getAllComments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeComments: StateFlow<List<PostComment>> = _selectedPostId.flatMapLatest { id ->
        repository.getComments(id ?: -1)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val instagramPosts: StateFlow<List<InstagramPost>> = repository.getInstagramPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setCommunitySort(sort: String) {
        _communitySortBy.value = sort
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectPost(postId: Int?) {
        _selectedPostId.value = postId
    }

    fun createPost(title: String, content: String, category: String) {
        viewModelScope.launch {
            val authorName = if (userRole.value == "Guest") "Guest Sadhak" else userName.value
            val newPost = CommunityPost(
                category = category,
                author = authorName,
                title = title,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            repository.addPost(newPost)
        }
    }

    fun addPostComment(postId: Int, content: String) {
        viewModelScope.launch {
            val authorName = if (userRole.value == "Guest") "Guest Sadhak" else userName.value
            val newComment = PostComment(
                postId = postId,
                author = authorName,
                content = content,
                createdAt = System.currentTimeMillis()
            )
            repository.addComment(newComment)
            val currentPosts = posts.value
            val targetPost = currentPosts.find { it.id == postId }
            if (targetPost != null) {
                repository.updatePost(targetPost.copy(commentsCount = targetPost.commentsCount + 1))
            }
        }
    }

    fun toggleLikePost(post: CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value.ifEmpty { "guest" }
            val upEmails = post.upvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()
            val downEmails = post.downvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()

            if (upEmails.contains(email)) {
                upEmails.remove(email)
            } else {
                upEmails.add(email)
                downEmails.remove(email)
            }

            repository.updatePost(post.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            ))
        }
    }

    fun toggleDislikePost(post: CommunityPost) {
        viewModelScope.launch {
            val email = userEmail.value.ifEmpty { "guest" }
            val upEmails = post.upvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()
            val downEmails = post.downvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()

            if (downEmails.contains(email)) {
                downEmails.remove(email)
            } else {
                downEmails.add(email)
                upEmails.remove(email)
            }

            repository.updatePost(post.copy(
                upvotes = upEmails.size,
                downvotes = downEmails.size,
                upvotedByEmails = upEmails.joinToString(","),
                downvotedByEmails = downEmails.joinToString(",")
            ))
        }
    }

    fun togglePinPost(post: CommunityPost) {
        viewModelScope.launch {
            repository.updatePost(post.copy(isPinned = !post.isPinned))
        }
    }

    fun deletePost(post: CommunityPost) {
        viewModelScope.launch {
            repository.deletePost(post.id)
        }
    }

    fun reportPost(post: CommunityPost) {
        viewModelScope.launch {
            val downEmails = post.downvotedByEmails.split(",").filter { it.isNotBlank() }.toMutableList()
            val email = userEmail.value.ifEmpty { "reporter" }
            if (!downEmails.contains(email)) {
                downEmails.add(email)
            }
            repository.updatePost(post.copy(
                downvotes = downEmails.size,
                downvotedByEmails = downEmails.joinToString(",")
            ))
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.launch {
            repository.deleteComment(id)
        }
    }

    fun publishInstagramPost(url: String, caption: String, category: String = "Sadhana") {
        viewModelScope.launch {
            repository.addInstagramPost(InstagramPost(url = url, caption = caption, category = category))
        }
    }

    fun updateInstagramPost(post: InstagramPost) {
        viewModelScope.launch {
            repository.updateInstagramPost(post)
        }
    }

    fun deleteInstagramPost(id: Int) {
        viewModelScope.launch {
            repository.deleteInstagramPostById(id)
        }
    }
}
