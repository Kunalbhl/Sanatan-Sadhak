package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val adminRepository: IAdminRepository = AdminRepository(application.applicationContext)
    private val userRepository: UserRepository = UserRepository.getInstance(application.applicationContext)
    private val knowledgeRepository: KnowledgeRepository = KnowledgeRepository(application.applicationContext)
    private val communityRepository: CommunityRepository = CommunityRepository(application.applicationContext)
    private val videoRepository: BhaktiVideoDao = AppDatabase.getInstance(application.applicationContext).bhaktiVideoDao()

    val auditLogs: StateFlow<List<AuditLog>> = adminRepository.auditLogs
    val deities: StateFlow<List<DeityItem>> = adminRepository.deities
    val poojaVidhis: StateFlow<List<PoojaVidhiItem>> = adminRepository.poojaVidhis
    val samagriItems: StateFlow<List<SamagriItem>> = adminRepository.samagriItems
    val mantras: StateFlow<List<MantraItem>> = adminRepository.mantras
    val festivals: StateFlow<List<FestivalItem>> = adminRepository.festivals
    val panchangConfig: StateFlow<PanchangAdminConfig> = adminRepository.panchangConfig
    val youtubeConfig: StateFlow<YoutubeChannelConfig> = adminRepository.youtubeConfig
    val adminNotifications: StateFlow<List<AdminNotificationRecord>> = adminRepository.adminNotifications
    val appSettings: StateFlow<AppSystemSettings> = adminRepository.appSettings

    val users: StateFlow<List<User>> = userRepository.getAllUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val articles: StateFlow<List<KnowledgeArticle>> = knowledgeRepository.getArticles("en").stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val posts: StateFlow<List<CommunityPost>> = communityRepository.getPosts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val comments: StateFlow<List<PostComment>> = communityRepository.getAllComments().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val videos: StateFlow<List<BhaktiVideo>> = videoRepository.getAllVideos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Diagnostics & Observability State
    val diagnosticErrors: StateFlow<List<DiagnosticErrorRecord>> = AppDiagnosticsManager.errorRecords
    val systemHealth: StateFlow<List<SystemComponentHealth>> = AppDiagnosticsManager.systemHealth
    val complianceAuditList: StateFlow<List<ComplianceAuditItem>> = AppDiagnosticsManager.complianceAudit

    // Filter & Search states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedDeityFilter = MutableStateFlow("All")
    val selectedDeityFilter: StateFlow<String> = _selectedDeityFilter.asStateFlow()

    private val _selectedStatusFilter = MutableStateFlow("All")
    val selectedStatusFilter: StateFlow<String> = _selectedStatusFilter.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setDeityFilter(deity: String) {
        _selectedDeityFilter.value = deity
    }

    fun setStatusFilter(status: String) {
        _selectedStatusFilter.value = status
    }

    // Action Confirmation Modal State
    data class ConfirmationAction(
        val title: String,
        val message: String,
        val warningMessage: String? = null,
        val onConfirm: () -> Unit
    )

    private val _pendingConfirmation = MutableStateFlow<ConfirmationAction?>(null)
    val pendingConfirmation: StateFlow<ConfirmationAction?> = _pendingConfirmation.asStateFlow()

    fun requestConfirmation(title: String, message: String, warningMessage: String? = null, onConfirm: () -> Unit) {
        _pendingConfirmation.value = ConfirmationAction(title, message, warningMessage, onConfirm)
    }

    fun dismissConfirmation() {
        _pendingConfirmation.value = null
    }

    fun executePendingConfirmation() {
        _pendingConfirmation.value?.onConfirm?.invoke()
        _pendingConfirmation.value = null
    }

    // --- KNOWLEDGE ARTICLE MANAGED ACTIONS ---
    fun saveKnowledgeArticle(
        id: Int = 0,
        title: String,
        category: String,
        summary: String,
        content: String,
        originalText: String,
        translation: String,
        sourceName: String,
        textReference: String,
        verificationStatus: String,
        currentAdminEmail: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (verificationStatus == "VERIFIED_SOURCE" && (sourceName.isBlank() || textReference.isBlank())) {
            onError("Cannot mark content as VERIFIED_SOURCE without specifying Source Name and Text Reference!")
            return
        }

        viewModelScope.launch {
            val article = KnowledgeArticle(
                id = id,
                title = title,
                category = category,
                summary = summary,
                content = content,
                originalText = originalText,
                translation = translation,
                sourceName = if (sourceName.isBlank()) "Unspecified Traditional Source" else sourceName,
                textReference = textReference,
                verificationStatus = verificationStatus,
                language = "en"
            )
            if (id == 0) {
                knowledgeRepository.addArticle(article)
                adminRepository.logAction(currentAdminEmail, "CONTENT_CREATED", "KNOWLEDGE_ARTICLE", title, "", "Created & set to $verificationStatus")
            } else {
                knowledgeRepository.updateArticle(article)
                adminRepository.logAction(currentAdminEmail, "CONTENT_EDITED", "KNOWLEDGE_ARTICLE", id.toString(), "Updated", "Status: $verificationStatus")
            }
            onSuccess()
        }
    }

    fun archiveKnowledgeArticle(article: KnowledgeArticle, currentAdminEmail: String) {
        requestConfirmation(
            title = "Archive Article",
            message = "Are you sure you want to archive '${article.title}'?",
            warningMessage = "Archived content will no longer be visible to regular users.",
            onConfirm = {
                viewModelScope.launch {
                    val updated = article.copy(verificationStatus = "ARCHIVED")
                    knowledgeRepository.updateArticle(updated)
                    adminRepository.logAction(currentAdminEmail, "CONTENT_ARCHIVED", "KNOWLEDGE_ARTICLE", article.id.toString(), article.title, "ARCHIVED")
                }
            }
        )
    }

    fun deleteKnowledgeArticle(article: KnowledgeArticle, currentAdminEmail: String) {
        requestConfirmation(
            title = "Delete Article",
            message = "Are you sure you want to permanently delete '${article.title}'?",
            warningMessage = "This action is irreversible. Prefer ARCHIVE for published spiritual texts.",
            onConfirm = {
                viewModelScope.launch {
                    knowledgeRepository.deleteArticle(article.id)
                    adminRepository.logAction(currentAdminEmail, "CONTENT_DELETED", "KNOWLEDGE_ARTICLE", article.id.toString(), article.title, "DELETED")
                }
            }
        )
    }

    // --- DEITY MANAGEMENT ---
    fun addOrUpdateDeity(deity: DeityItem, isEdit: Boolean, currentAdminEmail: String) {
        viewModelScope.launch {
            if (isEdit) {
                adminRepository.updateDeity(deity, currentAdminEmail)
            } else {
                adminRepository.addDeity(deity, currentAdminEmail)
            }
        }
    }

    // --- MANTRA MANAGEMENT ---
    fun addOrUpdateMantra(mantra: MantraItem, isEdit: Boolean, currentAdminEmail: String) {
        viewModelScope.launch {
            if (isEdit) {
                adminRepository.updateMantra(mantra, currentAdminEmail)
            } else {
                adminRepository.addMantra(mantra, currentAdminEmail)
            }
        }
    }

    // --- POOJA VIDHI MANAGEMENT ---
    fun addOrUpdatePoojaVidhi(pooja: PoojaVidhiItem, isEdit: Boolean, currentAdminEmail: String) {
        viewModelScope.launch {
            if (isEdit) {
                adminRepository.updatePoojaVidhi(pooja, currentAdminEmail)
            } else {
                adminRepository.addPoojaVidhi(pooja, currentAdminEmail)
            }
        }
    }

    fun addSamagriItem(samagri: SamagriItem, currentAdminEmail: String) {
        viewModelScope.launch {
            adminRepository.addSamagri(samagri, currentAdminEmail)
        }
    }

    // --- FESTIVAL MANAGEMENT ---
    fun addOrUpdateFestival(festival: FestivalItem, isEdit: Boolean, currentAdminEmail: String) {
        viewModelScope.launch {
            if (isEdit) {
                adminRepository.updateFestival(festival, currentAdminEmail)
            } else {
                adminRepository.addFestival(festival, currentAdminEmail)
            }
        }
    }

    // --- PANCHANG ADMIN CONFIG ---
    fun savePanchangConfig(config: PanchangAdminConfig, currentAdminEmail: String) {
        requestConfirmation(
            title = "Update Panchang Configuration",
            message = "Apply new Panchang calculation and source parameters?",
            warningMessage = "Changes affect Panchang display and festival rule derivations across the application.",
            onConfirm = {
                viewModelScope.launch {
                    adminRepository.updatePanchangConfig(config, currentAdminEmail)
                }
            }
        )
    }

    // --- YOUTUBE CONFIG & VIDEO CONTROL ---
    fun saveYoutubeConfig(config: YoutubeChannelConfig, currentAdminEmail: String) {
        viewModelScope.launch {
            adminRepository.updateYoutubeConfig(config, currentAdminEmail)
        }
    }

    fun addCuratedVideo(
        urlOrId: String,
        title: String,
        description: String,
        category: String,
        currentAdminEmail: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val videoId = YoutubeUrlParser.extractVideoId(urlOrId)
        if (videoId == null) {
            onError("Invalid YouTube URL or Video ID. Please provide a valid YouTube watch, short, or share link.")
            return
        }
        if (title.isBlank()) {
            onError("Video Title cannot be empty.")
            return
        }

        viewModelScope.launch {
            try {
                val newVideo = BhaktiVideo(
                    title = title.trim(),
                    description = description.trim(),
                    category = category,
                    videoId = videoId,
                    isCustom = true,
                    thumbnail = YoutubeUrlParser.getThumbnailUrl(videoId),
                    publishDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date())
                )
                videoRepository.insertVideo(newVideo)
                adminRepository.logAction(currentAdminEmail, "YOUTUBE_VIDEO_CURATED", "BHAKTI_VIDEO", videoId, "", title)
                onSuccess()
            } catch (e: Exception) {
                AppDiagnosticsManager.recordException(e, ErrorCategory.YOUTUBE, ErrorSeverity.MEDIUM)
                onError("Failed to save video: ${e.message}")
            }
        }
    }

    fun updateCuratedVideo(
        id: Int,
        videoId: String,
        title: String,
        description: String,
        category: String,
        isCustom: Boolean,
        currentAdminEmail: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (title.isBlank()) {
            onError("Video Title cannot be empty.")
            return
        }

        viewModelScope.launch {
            try {
                val updated = BhaktiVideo(
                    id = id,
                    title = title.trim(),
                    description = description.trim(),
                    category = category,
                    videoId = videoId,
                    isCustom = isCustom,
                    thumbnail = YoutubeUrlParser.getThumbnailUrl(videoId)
                )
                videoRepository.updateVideo(updated)
                adminRepository.logAction(currentAdminEmail, "YOUTUBE_VIDEO_UPDATED", "BHAKTI_VIDEO", id.toString(), videoId, title)
                onSuccess()
            } catch (e: Exception) {
                AppDiagnosticsManager.recordException(e, ErrorCategory.YOUTUBE, ErrorSeverity.MEDIUM)
                onError("Failed to update video: ${e.message}")
            }
        }
    }

    fun deleteCuratedVideo(video: BhaktiVideo, currentAdminEmail: String) {
        requestConfirmation(
            title = "Delete Curated Video",
            message = "Are you sure you want to remove '${video.title}' from the Sanatan Sadhak video hub?",
            warningMessage = "This video will be removed from all users' video playlists.",
            onConfirm = {
                viewModelScope.launch {
                    videoRepository.deleteVideoById(video.id)
                    adminRepository.logAction(currentAdminEmail, "YOUTUBE_VIDEO_DELETED", "BHAKTI_VIDEO", video.id.toString(), video.videoId, video.title)
                }
            }
        )
    }

    fun toggleVideoStatus(video: BhaktiVideo, currentAdminEmail: String) {
        viewModelScope.launch {
            val updated = video.copy(isCustom = !video.isCustom)
            videoRepository.updateVideo(updated)
            adminRepository.logAction(currentAdminEmail, "VIDEO_UPDATED", "BHAKTI_VIDEO", video.id.toString(), video.title, if (updated.isCustom) "CUSTOM" else "STANDARD")
        }
    }

    // --- OBSERVABILITY & SYSTEM DIAGNOSTICS ---
    fun runSystemDiagnostics(context: android.content.Context) {
        viewModelScope.launch {
            AppDiagnosticsManager.runSystemSelfDiagnostics(context)
            adminRepository.logAction("System Diagnostics", "DIAGNOSTICS_EXECUTED", "SYSTEM_HEALTH", "ALL_COMPONENTS", "", "Self-diagnostics completed")
        }
    }

    fun updateErrorDiagnosticStatus(fingerprint: String, newStatus: ErrorStatus, adminEmail: String) {
        viewModelScope.launch {
            AppDiagnosticsManager.updateErrorStatus(fingerprint, newStatus)
            adminRepository.logAction(adminEmail, "ERROR_STATUS_UPDATED", "DIAGNOSTIC_ERROR", fingerprint, "", newStatus.name)
        }
    }

    fun clearDiagnosticErrors(adminEmail: String) {
        requestConfirmation(
            title = "Clear Error Logs",
            message = "Clear all resolved and open diagnostic error logs?",
            warningMessage = "Active logs help developers identify crash trends.",
            onConfirm = {
                viewModelScope.launch {
                    AppDiagnosticsManager.clearAllErrors()
                    adminRepository.logAction(adminEmail, "DIAGNOSTICS_CLEARED", "DIAGNOSTIC_ERRORS", "ALL", "", "Cleared error history")
                }
            }
        )
    }

    // --- NOTIFICATIONS ---
    fun broadcastNotification(notification: AdminNotificationRecord, currentAdminEmail: String) {
        requestConfirmation(
            title = "Broadcast Notification",
            message = "Broadcast '${notification.title}' to users?",
            warningMessage = "User notification preferences will still be respected.",
            onConfirm = {
                viewModelScope.launch {
                    adminRepository.createNotification(notification, currentAdminEmail)
                }
            }
        )
    }

    // --- COMMUNITY MODERATION ---
    fun pinCommunityPost(post: CommunityPost, currentAdminEmail: String) {
        viewModelScope.launch {
            val updated = post.copy(isPinned = !post.isPinned)
            communityRepository.updatePost(updated)
            adminRepository.logAction(currentAdminEmail, "COMMUNITY_POST_MODERATED", "POST", post.id.toString(), post.title, if (updated.isPinned) "PINNED" else "UNPINNED")
        }
    }

    fun deleteCommunityPost(postId: Int, currentAdminEmail: String) {
        requestConfirmation(
            title = "Delete Community Post",
            message = "Permanently remove this community post?",
            warningMessage = "This action removes the post and associated comments.",
            onConfirm = {
                viewModelScope.launch {
                    communityRepository.deletePost(postId)
                    adminRepository.logAction(currentAdminEmail, "COMMUNITY_POST_DELETED", "POST", postId.toString(), "", "DELETED")
                }
            }
        )
    }

    // --- USER MANAGEMENT & ROLE CHANGES ---
    fun updateUserRole(userEmail: String, newRole: String, currentAdminEmail: String) {
        requestConfirmation(
            title = "Change User Role",
            message = "Change role of $userEmail to '$newRole'?",
            warningMessage = "Granting ADMIN role gives full access to the Sanatan Sadhak Admin Control Center.",
            onConfirm = {
                viewModelScope.launch {
                    userRepository.updateUserRole(userEmail, newRole)
                    adminRepository.logAction(currentAdminEmail, "USER_ROLE_CHANGED", "USER", userEmail, "", newRole)
                }
            }
        )
    }

    fun toggleUserCanPost(userEmail: String, canPost: Boolean, currentAdminEmail: String) {
        viewModelScope.launch {
            userRepository.updateUserCanPost(userEmail, canPost)
            adminRepository.logAction(currentAdminEmail, "USER_PERMISSIONS_CHANGED", "USER", userEmail, "", "canPost=$canPost")
        }
    }

    // --- SYSTEM APP SETTINGS ---
    fun saveAppSettings(settings: AppSystemSettings, currentAdminEmail: String) {
        viewModelScope.launch {
            adminRepository.updateAppSettings(settings, currentAdminEmail)
        }
    }
}
