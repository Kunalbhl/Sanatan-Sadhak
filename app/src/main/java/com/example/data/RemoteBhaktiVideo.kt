package com.example.data

/**
 * Remote data model for YouTube video content synchronized with Firestore backend
 * and cached in local Room database.
 */
data class RemoteBhaktiVideo(
    val videoId: String = "",
    val sourceType: String = "OFFICIAL_CHANNEL", // "OFFICIAL_CHANNEL" | "ADMIN_CURATED"
    val channelId: String = "UCzHzPEdLJEvTSHn_ev3JYBA",
    val channelName: String = "Sanatan Sadhak",
    val title: String = "",
    val description: String = "",
    val thumbnailUrl: String = "",
    val publishedAt: String = "",
    val duration: String = "",
    val category: String = "Darshan & Puja",
    val tags: List<String> = emptyList(),
    val featured: Boolean = false,
    val displayOrder: Int = 0,
    val published: Boolean = true, // false = draft/unpublished (admins only)
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val syncedAt: Long = System.currentTimeMillis(),
    val availability: String = "AVAILABLE", // AVAILABLE, UNAVAILABLE, RESTRICTED
    val adminNotes: String = ""
) {
    /**
     * Converts to Room entity BhaktiVideo for offline database cache.
     */
    fun toBhaktiVideo(id: Int = 0): BhaktiVideo {
        return BhaktiVideo(
            id = id,
            title = title,
            description = description,
            category = category,
            videoId = videoId,
            isCustom = (sourceType == "ADMIN_CURATED"),
            thumbnail = thumbnailUrl.ifEmpty { YoutubeUrlParser.getThumbnailUrl(videoId) },
            publishDate = publishedAt
        )
    }

    /**
     * Serializes to Firestore document map.
     */
    fun toFirestoreMap(): Map<String, Any> {
        return mapOf(
            "videoId" to videoId,
            "sourceType" to sourceType,
            "channelId" to channelId,
            "channelName" to channelName,
            "title" to title,
            "description" to description,
            "thumbnailUrl" to thumbnailUrl.ifEmpty { YoutubeUrlParser.getThumbnailUrl(videoId) },
            "publishedAt" to publishedAt,
            "duration" to duration,
            "category" to category,
            "tags" to tags,
            "featured" to featured,
            "displayOrder" to displayOrder,
            "published" to published,
            "createdAt" to createdAt,
            "updatedAt" to updatedAt,
            "syncedAt" to syncedAt,
            "availability" to availability,
            "adminNotes" to adminNotes
        )
    }

    companion object {
        fun fromBhaktiVideo(
            video: BhaktiVideo,
            isPublished: Boolean = true,
            featured: Boolean = false,
            displayOrder: Int = 0,
            notes: String = ""
        ): RemoteBhaktiVideo {
            return RemoteBhaktiVideo(
                videoId = video.videoId,
                sourceType = if (video.isCustom) "ADMIN_CURATED" else "OFFICIAL_CHANNEL",
                channelId = if (video.isCustom) "" else "UCzHzPEdLJEvTSHn_ev3JYBA",
                channelName = if (video.isCustom) "Curated Devotional" else "Sanatan Sadhak",
                title = video.title,
                description = video.description,
                thumbnailUrl = video.thumbnail.ifEmpty { YoutubeUrlParser.getThumbnailUrl(video.videoId) },
                publishedAt = video.publishDate,
                category = video.category,
                featured = featured,
                displayOrder = displayOrder,
                published = isPublished,
                adminNotes = notes
            )
        }

        fun fromFirestoreMap(id: String, data: Map<String, Any?>): RemoteBhaktiVideo {
            return RemoteBhaktiVideo(
                videoId = data["videoId"] as? String ?: id,
                sourceType = data["sourceType"] as? String ?: "ADMIN_CURATED",
                channelId = data["channelId"] as? String ?: "",
                channelName = data["channelName"] as? String ?: "Sanatan Sadhak",
                title = data["title"] as? String ?: "",
                description = data["description"] as? String ?: "",
                thumbnailUrl = data["thumbnailUrl"] as? String ?: "",
                publishedAt = data["publishedAt"] as? String ?: "",
                duration = data["duration"] as? String ?: "",
                category = data["category"] as? String ?: "Darshan & Puja",
                tags = (data["tags"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                featured = data["featured"] as? Boolean ?: false,
                displayOrder = (data["displayOrder"] as? Number)?.toInt() ?: 0,
                published = data["published"] as? Boolean ?: true,
                createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                updatedAt = (data["updatedAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                syncedAt = (data["syncedAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                availability = data["availability"] as? String ?: "AVAILABLE",
                adminNotes = data["adminNotes"] as? String ?: ""
            )
        }
    }
}
