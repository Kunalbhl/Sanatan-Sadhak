package com.example.data

data class AuditLog(
    val id: String = java.util.UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val adminIdentifier: String,
    val action: String, // CONTENT_CREATED, CONTENT_EDITED, CONTENT_VERIFIED, CONTENT_PUBLISHED, CONTENT_ARCHIVED, USER_ROLE_CHANGED, PANCHANG_CONFIG_CHANGED, VIDEO_SYNCED, NOTIFICATION_CREATED, APP_SETTINGS_CHANGED
    val entityType: String,
    val entityId: String,
    val oldValue: String = "",
    val newValue: String = ""
)

data class DeityItem(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val nameSanskrit: String = "",
    val description: String = "",
    val iconRef: String = "ic_deity",
    val sortOrder: Int = 0,
    val isActive: Boolean = true
)

data class PoojaStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String,
    val mantra: String = "",
    val durationMinutes: Int = 5,
    val isRequired: Boolean = true
)

data class PoojaVidhiItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val pujaName: String,
    val deity: String,
    val occasion: String = "",
    val samagriList: String = "",
    val preparation: String = "",
    val purificationSteps: String = "",
    val sankalpa: String = "",
    val steps: List<PoojaStep> = emptyList(),
    val offerings: String = "",
    val closingProcedure: String = "",
    val safetyNotes: String = "",
    val source: String = "",
    val verificationStatus: String = "UNVERIFIED", // VERIFIED_SOURCE, TRADITIONAL_REFERENCE, EDITOR_REVIEW_REQUIRED, UNVERIFIED
    val workflowStatus: String = "PUBLISHED" // DRAFT, UNDER_REVIEW, VERIFIED, PUBLISHED, ARCHIVED
)

data class SamagriItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nameEn: String,
    val nameHi: String,
    val nameSanskrit: String = "",
    val description: String = "",
    val quantity: String = "1",
    val unit: String = "item",
    val isRequired: Boolean = true,
    val applicablePooja: String = "All",
    val substituteNote: String = "",
    val source: String = ""
)

data class MantraItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val deity: String,
    val originalSanskrit: String,
    val transliteration: String = "",
    val meaning: String = "",
    val purpose: String = "",
    val source: String = "",
    val reference: String = "",
    val tradition: String = "Sanatan",
    val japaRecommendation: String = "108 times",
    val audioUrl: String = "",
    val verificationStatus: String = "UNVERIFIED",
    val workflowStatus: String = "PUBLISHED",
    val isVisible: Boolean = true
)

data class FestivalItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val nameEn: String,
    val nameHi: String,
    val deity: String,
    val description: String,
    val tradition: String = "Sanatan",
    val region: String = "All India",
    val observanceRule: String = "",
    val dateType: String = "RULE_BASED", // RULE_BASED, FIXED_DATE, REMOTE_SOURCE, MANUAL_VERIFIED
    val fixedDate: String = "",
    val source: String = "",
    val verificationStatus: String = "UNVERIFIED",
    val workflowStatus: String = "PUBLISHED"
)

data class YoutubeChannelConfig(
    val channelId: String = "UCzHzPEdLJEvTSHn_ev3JYBA",
    val channelUrl: String = "https://www.youtube.com/@SanatanSadhak",
    val fetchEnabled: Boolean = true,
    val lastSyncTimestamp: Long = System.currentTimeMillis(),
    val syncStatus: String = "SUCCESS"
)

data class AdminNotificationRecord(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val category: String, // Daily Panchang, Daily Mantra, Daily Shloka, Festival, Vrat, Sadhana, Meditation, New Video, Community, Knowledge
    val schedule: String = "IMMEDIATE",
    val enabled: Boolean = true,
    val priority: String = "NORMAL",
    val createdAt: Long = System.currentTimeMillis()
)

data class AppSystemSettings(
    val appName: String = "Sanatan Sadhak",
    val aiAssistantName: String = "Sadhak AI Mitra",
    val defaultLanguage: String = "hi",
    val isMaintenanceMode: Boolean = false,
    val contentVisibilityDefault: String = "PUBLISHED",
    val defaultNotificationEnabled: Boolean = true,
    val aboutInfo: String = "Sanatan Sadhak is a comprehensive spiritual platform dedicated to authentic Sanatan Dharma knowledge, Panchang, Sadhana tools, and community."
)
