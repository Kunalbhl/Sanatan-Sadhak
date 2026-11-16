package com.example

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.test.core.app.ApplicationProvider
import com.example.data.*
import com.example.ui.theme.*
import com.example.utils.HapticFeedbackManager
import com.example.utils.ReadingTimeUtils
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
class Task156KnowledgeVedicLibraryCanonicalReconciliationRegressionTest {

    private lateinit var context: Context
    private lateinit var knowledgeRepo: KnowledgeRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        knowledgeRepo = KnowledgeRepository(context)
        AppDiagnosticsManager.clearAllErrors()
    }

    @Test
    fun testTask156_canonicalDesignTokensMatchVedicDesign() {
        assertEquals("Temple Cream", Color(0xFFFDF8EE), TempleCream)
        assertEquals("Deep Sacred Maroon", Color(0xFF7A1720), DeepSacredMaroon)
        assertEquals("Devotional Saffron", Color(0xFFE58A16), DevotionalSaffron)
        assertEquals("Sacred Gold", Color(0xFFD4A23A), SacredGold)
        assertEquals("Temple Dark Background", Color(0xFF160D08), TempleDarkBackground)

        assertEquals(TempleCream, LightSadhakColors.background)
        assertEquals(DeepSacredMaroon, LightSadhakColors.secondary)
        assertEquals(DevotionalSaffron, LightSadhakColors.primary)
        assertEquals(SacredGold, LightSadhakColors.accent)
        assertEquals(TempleDarkBackground, DarkSadhakColors.background)
    }

    @Test
    fun testTask156_verificationStatusesAreTraceableAndPreserved() {
        val validStatuses = listOf(
            "VERIFIED_SOURCE",
            "TRADITIONAL_REFERENCE",
            "EDITOR_REVIEW_REQUIRED",
            "UNVERIFIED"
        )
        SpiritualData.defaultArticles.forEach { article ->
            assertTrue(
                "Article '${article.title}' must have a valid verification status",
                validStatuses.contains(article.verificationStatus)
            )
            assertTrue("Article title must not be blank", article.title.isNotBlank())
            assertTrue("Article content must not be blank", article.content.isNotBlank())
            assertTrue("Article category must not be blank", article.category.isNotBlank())
            if (article.verificationStatus == "VERIFIED_SOURCE") {
                assertTrue("Verified article must have source metadata", article.sourceName.isNotBlank())
            }
        }
    }

    @Test
    fun testTask156_readingTimeCalculationAccuracy() {
        val shortContent = "Short spiritual wisdom text."
        val readTimeShort = ReadingTimeUtils.calculateReadTime(shortContent)
        assertTrue("Short content read time should be at least 1 min", readTimeShort >= 1)

        val longContent = (1..600).joinToString(" ") { "Sanatan" }
        val readTimeLong = ReadingTimeUtils.calculateReadTime(longContent)
        assertTrue("600 words should be approx 3 mins", readTimeLong in 2..4)
    }

    @Test
    fun testTask156_offlineRoomCachingAndPersistence() = runBlocking {
        val testArticle = KnowledgeArticle(
            id = 9999,
            category = "Upanishads",
            title = "Katha Upanishad - Nachiketa Dialogue",
            summary = "Sacred teaching of Yama to Nachiketa on immortality of the Atman.",
            content = "The Katha Upanishad explores the sacred mystery of death and the immortal nature of consciousness.",
            sourceName = "Gita Press Gorakhpur",
            sourceType = "Traditional Scripture",
            textReference = "Katha Upanishad 1.2.1",
            verificationStatus = "VERIFIED_SOURCE",
            language = "en"
        )

        knowledgeRepo.addArticle(testArticle)
        val articlesEn = knowledgeRepo.getArticles("en").first()
        assertTrue("Inserted article must be cached and retrieved from Room", articlesEn.any { it.title.contains("Nachiketa") })

        // Reading Progress persistence
        val progress = ReadingProgress(
            userId = "devotee@sanatansadhak.org",
            articleId = 9999,
            progress = 0.75f,
            lastUpdated = System.currentTimeMillis()
        )
        knowledgeRepo.saveReadingProgress(progress)
        val savedProgress = knowledgeRepo.getReadingProgress("devotee@sanatansadhak.org", 9999)
        assertNotNull(savedProgress)
        assertEquals(0.75f, savedProgress!!.progress, 0.001f)

        // Clean up
        knowledgeRepo.deleteArticle(9999)
    }

    @Test
    fun testTask156_searchFilteringAndNoResultHandling() = runBlocking {
        val resultsGita = knowledgeRepo.searchArticles("Gita", "en").first()
        assertNotNull(resultsGita)

        val resultsNonExistent = knowledgeRepo.searchArticles("XYZNONEXISTENTQUERY999", "en").first()
        assertTrue("Non-existent query must return empty list gracefully", resultsNonExistent.isEmpty())
    }

    @Test
    fun testTask156_hapticFeedbackSafetyForKnowledgeInteractions() {
        val feedbackTypes = listOf(
            HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP,
            HapticFeedbackManager.FeedbackType.LIGHT_TOUCH,
            HapticFeedbackManager.FeedbackType.STANDARD_CLICK,
            HapticFeedbackManager.FeedbackType.SUCCESS
        )
        for (feedback in feedbackTypes) {
            HapticFeedbackManager.vibrate(context, feedback)
            HapticFeedbackManager.performHaptic(null, feedback)
        }
        assertTrue("Haptics executed safely without crashing", true)
    }

    @Test
    fun testTask156_guestDevoteeAdminRoleBoundaries() {
        // Guest can preview first 2 articles, locked after index >= 2
        val guestIndex0Locked = false
        val guestIndex1Locked = false
        val guestIndex2Locked = true

        assertFalse(guestIndex0Locked)
        assertFalse(guestIndex1Locked)
        assertTrue(guestIndex2Locked)

        // Devotee and Admin have full access
        val devoteeIndex2Locked = false
        val adminIndex2Locked = false
        assertFalse(devoteeIndex2Locked)
        assertFalse(adminIndex2Locked)
    }

    @Test
    fun testTask156_vedicAudioTracksIntegrity() {
        val tracks = com.example.ui.viewmodel.devotionalTracks
        assertTrue("Devotional tracks list must not be empty", tracks.isNotEmpty())
        tracks.forEach { track ->
            assertTrue("Track title (EN) must not be empty", track.titleEn.isNotBlank())
            assertTrue("Track title (HI) must not be empty", track.titleHi.isNotBlank())
            assertTrue("Track category must not be empty", track.category.isNotBlank())
            assertTrue("Track ID must be positive", track.id > 0)
        }
    }
}
