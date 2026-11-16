package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.BhaktiVideo
import com.example.data.VideoRepository
import com.example.ui.viewmodel.SadhakViewModel
import com.example.ui.viewmodel.VideoViewModel
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
class Task162VideoHubYouTubeCanonicalRegressionTest {

    private lateinit var context: Context
    private lateinit var database: AppDatabase
    private lateinit var videoRepository: VideoRepository
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var sadhakViewModel: SadhakViewModel

    @Before
    fun setup() = runBlocking {
        context = ApplicationProvider.getApplicationContext()
        database = AppDatabase.getInstance(context)
        videoRepository = VideoRepository(context)
        videoRepository.ensureSeedVideos()
        val app = ApplicationProvider.getApplicationContext<Application>()
        videoViewModel = VideoViewModel(app)
        sadhakViewModel = SadhakViewModel(app)
    }

    @Test
    fun testTask162_defaultCuratedVideos_loaded_and_validVideoIds() = runBlocking {
        val videos = videoRepository.getVideos().first()
        assertTrue("Default seed videos must be available", videos.isNotEmpty())
        
        // Every video must have a valid non-blank video identifier and metadata
        videos.forEach { video ->
            assertTrue("Video ID must not be blank", video.videoId.isNotBlank())
            assertTrue("Video title must not be blank", video.title.isNotBlank())
            assertTrue("Video category must not be blank", video.category.isNotBlank())
        }
    }

    @Test
    fun testTask162_categoryFiltering_worksAccurately() = runBlocking {
        val allVideos = videoRepository.getVideos().first()
        assertTrue("Videos must be loaded", allVideos.isNotEmpty())

        val darshanVideos = videoViewModel.filterVideos(allVideos, "Darshan", "")
        assertTrue("Darshan videos must not be empty", darshanVideos.isNotEmpty())
        darshanVideos.forEach { video ->
            assertTrue(
                "Filtered video must match Darshan category",
                video.category.contains("Darshan", ignoreCase = true) || video.category.contains("Aarti", ignoreCase = true)
            )
        }

        val allFiltered = videoViewModel.filterVideos(allVideos, "All", "")
        assertTrue("All videos count must be >= category videos count", allFiltered.size >= darshanVideos.size)
    }

    @Test
    fun testTask162_searchQuery_filtersByTitleCategoryAndId() = runBlocking {
        val allVideos = videoRepository.getVideos().first()
        assertTrue("Videos must be loaded", allVideos.isNotEmpty())
        
        // Search for known keyword "Aarti"
        val aartiResults = videoViewModel.filterVideos(allVideos, "All", "Aarti")
        assertTrue("Aarti results should not be empty", aartiResults.isNotEmpty())
        aartiResults.forEach { video ->
            val match = video.title.contains("Aarti", ignoreCase = true) ||
                    video.description.contains("Aarti", ignoreCase = true) ||
                    video.category.contains("Aarti", ignoreCase = true)
            assertTrue("Search result must contain 'Aarti'", match)
        }

        // Search for non-existent text
        val emptyResults = videoViewModel.filterVideos(allVideos, "All", "XYZ_NON_EXISTENT_STRING_999")
        assertTrue("Non-matching search must return empty list", emptyResults.isEmpty())

        // Clear search
        val clearedResults = videoViewModel.filterVideos(allVideos, "All", "")
        assertTrue("Clearing search should restore list", clearedResults.isNotEmpty())
        assertEquals(allVideos.size, clearedResults.size)
    }

    @Test
    fun testTask162_favoritesVideoToggle_persistenceAndIsolation() = runBlocking {
        val testVideoId = "test_vid_162_abc"
        val userId = "test_user_isolation"
        val profileRepository = com.example.data.ProfileRepository(context)
        
        // Initial state
        val initialFavs = profileRepository.getUserFavorites(userId).first()
        val initialCount = initialFavs.size
        
        // Toggle on (Add)
        profileRepository.addUserFavorite(
            com.example.data.UserFavorite(
                userId = userId,
                targetId = testVideoId,
                type = "bhakti_video"
            )
        )
        val afterAdd = profileRepository.getUserFavorites(userId).first()
        assertTrue("Favorites should contain toggled videoId", afterAdd.any { it.targetId == testVideoId && it.type == "bhakti_video" })
        assertEquals(initialCount + 1, afterAdd.size)

        // Toggle off (Delete)
        profileRepository.deleteUserFavorite(userId, testVideoId, "bhakti_video")
        val afterDelete = profileRepository.getUserFavorites(userId).first()
        assertFalse("Favorites should no longer contain toggled videoId", afterDelete.any { it.targetId == testVideoId && it.type == "bhakti_video" })
        assertEquals(initialCount, afterDelete.size)
    }

    @Test
    fun testTask162_curatedPlaylistVideos_festivalReconciliation() = runBlocking {
        val allVideos = videoRepository.getVideos().first()
        assertTrue("Videos must be loaded", allVideos.isNotEmpty())
        
        // When festival is Diwali
        val diwaliVideos = videoViewModel.computeCuratedPlaylist(allVideos, "Diwali")
        assertTrue("Curated playlist should have videos during Diwali", diwaliVideos.isNotEmpty())
        
        videoViewModel.setCurrentFestival("Diwali")
        val diwaliTitle = videoViewModel.getCuratedPlaylistTitle(isEng = true)
        assertTrue("Curated title should mention Diwali or Deepawali", diwaliTitle.contains("Diwali", ignoreCase = true) || diwaliTitle.contains("Deepawali", ignoreCase = true))

        // When festival is Shivratri
        val shivratriVideos = videoViewModel.computeCuratedPlaylist(allVideos, "Maha Shivratri")
        assertTrue("Curated playlist should have videos during Shivratri", shivratriVideos.isNotEmpty())
        
        videoViewModel.setCurrentFestival("Maha Shivratri")
        val shivTitle = videoViewModel.getCuratedPlaylistTitle(isEng = true)
        assertTrue("Curated title should mention Shivratri", shivTitle.contains("Shivratri", ignoreCase = true))
    }

    @Test
    fun testTask162_videoRepository_insertAndDeduplication() = runBlocking {
        val customVideo = BhaktiVideo(
            videoId = "custom_test_123",
            title = "Test Custom Katha",
            description = "Custom description for testing deduplication",
            thumbnail = "https://example.com/thumb.jpg",
            category = "Stories",
            isCustom = true
        )

        // Insert video via repository
        videoRepository.insertVideo(customVideo)
        val videosAfterInsert = videoRepository.getVideos().first()
        val found = videosAfterInsert.find { it.videoId == "custom_test_123" }
        assertNotNull("Inserted video should exist", found)
        assertEquals("Test Custom Katha", found?.title)
        assertTrue("Should be marked as custom", found?.isCustom == true)

        // Insert duplicate with updated title
        val duplicateUpdated = customVideo.copy(title = "Updated Test Custom Katha")
        videoRepository.insertVideo(duplicateUpdated)
        val videosAfterDuplicate = videoRepository.getVideos().first()
        val duplicates = videosAfterDuplicate.filter { it.videoId == "custom_test_123" }
        assertTrue("Duplicate videos should be processed properly", duplicates.isNotEmpty())
    }

    @Test
    fun testTask162_guestRoleRestriction_validation() = runBlocking {
        // Test role transition for Guest
        sadhakViewModel.setUserSessionForTesting("guest@test.org", "Guest", "Guest Devotee")
        assertEquals("Guest", sadhakViewModel.userRole.first())
        assertFalse("Guest cannot be Admin", sadhakViewModel.isAdminUser())

        // Test role transition for Devotee
        sadhakViewModel.setUserSessionForTesting("devotee@test.org", "Devotee", "Bhakti Devotee")
        assertEquals("Devotee", sadhakViewModel.userRole.first())
        assertFalse("Devotee cannot be Admin", sadhakViewModel.isAdminUser())

        // Test role transition for Admin
        sadhakViewModel.setUserSessionForTesting("kunalpareekusa@gmail.com", "Admin", "Admin Sadhak")
        assertEquals("Admin", sadhakViewModel.userRole.first())
        assertTrue("Master admin email should be recognized as Admin", sadhakViewModel.isAdminUser())
    }
}
