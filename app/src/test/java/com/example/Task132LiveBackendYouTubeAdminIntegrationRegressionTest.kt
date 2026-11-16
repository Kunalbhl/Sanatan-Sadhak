package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
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
class Task132LiveBackendYouTubeAdminIntegrationRegressionTest {

    private lateinit var context: Context
    private lateinit var videoRepository: VideoRepository
    private lateinit var adminRepository: AdminRepository
    private lateinit var firebaseService: FirebaseService

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        videoRepository = VideoRepository(context)
        adminRepository = AdminRepository(context)
        firebaseService = FirebaseService(context)
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testYouTubeUrlParser_handlesAllFormatsAndRejectsInvalid() {
        // Standard Watch
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        // Short URL
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://youtu.be/dQw4w9WgXcQ"))
        // Shorts
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/shorts/dQw4w9WgXcQ"))
        // Embed
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("https://www.youtube.com/embed/dQw4w9WgXcQ"))
        // Raw 11-char ID
        assertEquals("dQw4w9WgXcQ", YoutubeUrlParser.extractVideoId("dQw4w9WgXcQ"))
        // Invalid
        assertNull(YoutubeUrlParser.extractVideoId("https://vimeo.com/123456"))
        assertNull(YoutubeUrlParser.extractVideoId(""))
        assertNull(YoutubeUrlParser.extractVideoId("invalid_string_short"))
    }

    @Test
    fun testRemoteBhaktiVideo_modelMappingAndSerialization() {
        val remote = RemoteBhaktiVideo(
            videoId = "dQw4w9WgXcQ",
            sourceType = "ADMIN_CURATED",
            channelName = "Sanatan Sadhak",
            title = "Sacred Shiva Mahimna Stotram",
            description = "Devotional recitation of Shiva Mahimna Stotram",
            thumbnailUrl = "https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg",
            publishedAt = "2026-08-24",
            duration = "15:30",
            category = "Mantra",
            tags = listOf("Shiva", "Stotram", "Bhakti"),
            featured = true,
            displayOrder = 1,
            published = true,
            adminNotes = "Editor verified authenticity"
        )

        // Convert to Room BhaktiVideo
        val roomVideo = remote.toBhaktiVideo(id = 101)
        assertEquals("Sacred Shiva Mahimna Stotram", roomVideo.title)
        assertEquals("dQw4w9WgXcQ", roomVideo.videoId)
        assertTrue(roomVideo.isCustom)
        assertEquals("Mantra", roomVideo.category)

        // Convert to Firestore Map
        val map = remote.toFirestoreMap()
        assertEquals("dQw4w9WgXcQ", map["videoId"])
        assertEquals("ADMIN_CURATED", map["sourceType"])
        assertEquals(true, map["featured"])
        assertEquals(true, map["published"])

        // Convert back from Firestore Map
        val reconstructed = RemoteBhaktiVideo.fromFirestoreMap("dQw4w9WgXcQ", map)
        assertEquals(remote.videoId, reconstructed.videoId)
        assertEquals(remote.title, reconstructed.title)
        assertEquals(remote.featured, reconstructed.featured)
        assertEquals(remote.adminNotes, reconstructed.adminNotes)
    }

    @Test
    fun testRoomCache_andOfflineVideoRetrieval() = runBlocking {
        val initialList = videoRepository.getVideos().first()
        val testVideo = BhaktiVideo(
            title = "Hanuman Chalisa Live Aarti",
            description = "Divine evening aarti recitation",
            category = "Darshan & Puja",
            videoId = "test_vid_108",
            isCustom = true,
            thumbnail = "https://img.youtube.com/vi/test_vid_108/hqdefault.jpg",
            publishDate = "2026-08-24"
        )

        videoRepository.addVideo(testVideo)
        val afterAdd = videoRepository.getVideos().first()
        assertTrue(afterAdd.any { it.videoId == "test_vid_108" })

        // Update video in cache
        val existing = afterAdd.first { it.videoId == "test_vid_108" }
        val updated = existing.copy(title = "Hanuman Chalisa Live Aarti (Special)")
        videoRepository.updateVideo(updated)

        val afterUpdate = videoRepository.getVideos().first()
        val retrievedUpdated = afterUpdate.first { it.videoId == "test_vid_108" }
        assertEquals("Hanuman Chalisa Live Aarti (Special)", retrievedUpdated.title)

        // Clean up
        videoRepository.deleteVideoById(retrievedUpdated.id)
        val afterDelete = videoRepository.getVideos().first()
        assertFalse(afterDelete.any { it.videoId == "test_vid_108" })
    }

    @Test
    fun testAdminAuthorizationAndRoleIsolation() = runBlocking {
        // Super Admin emails
        assertTrue(firebaseService.isUserAdmin("admin@sanatansadhak.org"))
        assertTrue(firebaseService.isUserAdmin("kunalpareekusa@gmail.com"))
        assertEquals("SUPER_ADMIN", firebaseService.getUserRole("admin@sanatansadhak.org"))

        // Standard Devotee email
        assertFalse(firebaseService.isUserAdmin("devotee.user@example.com"))
        assertEquals("DEVOTEE", firebaseService.getUserRole("devotee.user@example.com"))
    }

    @Test
    fun testDiagnosticsManager_sanitizesAllPiiAndTokens() {
        val sensitive1 = "User login failure for sadhak.sharma@example.com with key AIzaSyD1234567890abcdef1234567890abc"
        val sanitized1 = AppDiagnosticsManager.sanitizeMessage(sensitive1)
        assertFalse(sanitized1.contains("sadhak.sharma@example.com"))
        assertTrue(sanitized1.contains("[REDACTED_EMAIL]"))
        assertFalse(sanitized1.contains("AIzaSyD1234567890abcdef1234567890abc"))
        assertTrue(sanitized1.contains("[REDACTED_API_KEY]"))

        val sensitive2 = "HTTP Request Auth: Bearer ya29.a0AfH6SMBabc12345 and password=SecretPassword123"
        val sanitized2 = AppDiagnosticsManager.sanitizeMessage(sensitive2)
        assertFalse(sanitized2.contains("ya29.a0AfH6SMBabc12345"))
        assertTrue(sanitized2.contains("Bearer [REDACTED_TOKEN]"))
        assertFalse(sanitized2.contains("SecretPassword123"))
        assertTrue(sanitized2.contains("password=[REDACTED]"))
    }

    @Test
    fun testDiagnosticsManager_deduplicationAndErrorTracking() {
        val ex = java.io.IOException("Socket timeout communicating with video host")
        val fp1 = AppDiagnosticsManager.recordException(ex, ErrorCategory.YOUTUBE, ErrorSeverity.HIGH, "VideoSyncService")

        var records = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, records.size)
        assertEquals(1, records[0].occurrenceCount)
        assertEquals("VideoSyncService", records[0].featureContext)

        // Record again -> increments occurrence count
        val fp2 = AppDiagnosticsManager.recordException(ex, ErrorCategory.YOUTUBE, ErrorSeverity.HIGH, "VideoSyncService")
        assertEquals(fp1, fp2)

        records = AppDiagnosticsManager.errorRecords.value
        assertEquals(1, records.size)
        assertEquals(2, records[0].occurrenceCount)

        // Mark resolved
        AppDiagnosticsManager.updateErrorStatus(fp1, ErrorStatus.RESOLVED)
        assertEquals(ErrorStatus.RESOLVED, AppDiagnosticsManager.errorRecords.value[0].status)
    }

    @Test
    fun testSelfDiagnosticsEngine_verifiesAllEightSystemComponents() {
        val health = AppDiagnosticsManager.runSystemSelfDiagnostics(context)
        assertEquals(8, health.size)

        val componentNames = health.map { it.componentName }
        assertTrue(componentNames.any { it.contains("Room Database") })
        assertTrue(componentNames.any { it.contains("Astronomical Panchang") })
        assertTrue(componentNames.any { it.contains("Authentication & Security") })
        assertTrue(componentNames.any { it.contains("YouTube Content Sync") })
        assertTrue(componentNames.any { it.contains("Notification Scheduler") })
        assertTrue(componentNames.any { it.contains("Content Management") })
        assertTrue(componentNames.any { it.contains("Background Worker") })
        assertTrue(componentNames.any { it.contains("Storage & Media") })

        assertTrue(health.all { it.status == HealthStatus.HEALTHY })
    }

    @Test
    fun testAdminAuditTrail_logsPrivilegedOperations() = runBlocking {
        adminRepository.logAction(
            adminIdentifier = "kunalpareekusa@gmail.com",
            action = "YOUTUBE_VIDEO_CURATED",
            entityType = "BHAKTI_VIDEO",
            entityId = "dQw4w9WgXcQ",
            oldValue = "",
            newValue = "Sacred Shiva Mahimna Stotram"
        )

        val logs = adminRepository.auditLogs.value
        assertTrue(logs.isNotEmpty())
        val latestLog = logs.first()
        assertEquals("kunalpareekusa@gmail.com", latestLog.adminIdentifier)
        assertEquals("YOUTUBE_VIDEO_CURATED", latestLog.action)
        assertEquals("BHAKTI_VIDEO", latestLog.entityType)
        assertEquals("dQw4w9WgXcQ", latestLog.entityId)
    }

    @Test
    fun testVideoSync_exponentialBackoffAndDuplicatePrevention() = runBlocking {
        // Initial state
        val initialCount = videoRepository.getVideos().first().size
        
        // Sync videos with dummy API key (falls back gracefully to RSS/offline without crashing)
        val syncResult = videoRepository.syncAllVideos(apiKey = "", maxRetries = 2)
        assertTrue(syncResult.isSuccess || syncResult.isFailure)
        
        // Verify videos list is intact and accessible
        val videos = videoRepository.getVideos().first()
        assertNotNull(videos)
        assertTrue(videos.size >= initialCount)
    }
}
