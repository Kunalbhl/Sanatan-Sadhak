package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.audio.AudioCategory
import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import com.example.utils.HapticFeedbackManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class Task159BhaktiAudioTempleBellLiveAartiRegressionTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        SadhakAudioManager.init(context)
        SadhakAudioManager.setSoundEnabled(true)
        SadhakAudioManager.setMuted(false)
        SadhakAudioManager.setVolume(1.0f)
        SadhakAudioManager.closeFullPlayer()
    }

    @Test
    fun testTask159_audioTrackIdIntegrityAndCategories() {
        // Verify audio categories
        assertEquals("Sacred Mantras", AudioCategory.MANTRA.titleEn)
        assertEquals("पवित्र मंत्र", AudioCategory.MANTRA.titleHi)
        assertEquals("Stotrams & Chalisa", AudioCategory.DEVOTIONAL.titleEn)
        assertEquals("स्तोत्र एवं चालीसा", AudioCategory.DEVOTIONAL.titleHi)
        assertEquals("Dhyan & Ambient", AudioCategory.AMBIENT.titleEn)
        assertEquals("ध्यान एवं शांति", AudioCategory.AMBIENT.titleHi)

        // Verify one-shot tracks
        assertTrue(AudioTrackId.TEMPLE_BELL.isOneShot)
        assertTrue(AudioTrackId.SADHANA_SUCCESS.isOneShot)
        assertEquals("temple_bell", AudioTrackId.TEMPLE_BELL.rawName)
        assertEquals("sadhana_success", AudioTrackId.SADHANA_SUCCESS.rawName)

        // Verify playable library tracks exclude one-shots
        val playable = AudioTrackId.playableTracks()
        assertFalse(playable.contains(AudioTrackId.TEMPLE_BELL))
        assertFalse(playable.contains(AudioTrackId.SADHANA_SUCCESS))
        assertTrue(playable.contains(AudioTrackId.GAYATRI_MANTRA))
        assertTrue(playable.contains(AudioTrackId.MAHA_MRITYUNJAYA))
        assertTrue(playable.contains(AudioTrackId.SHIVA_TANDAV))
        assertTrue(playable.contains(AudioTrackId.HANUMAN_CHALISA))
        assertTrue(playable.contains(AudioTrackId.VISHNU_SAHASRANAMAM))
        assertTrue(playable.contains(AudioTrackId.OM_AMBIENT))
        assertTrue(playable.contains(AudioTrackId.FLUTE_AMBIENT))
    }

    @Test
    fun testTask159_audioTrackBilingualMetadata() {
        for (track in AudioTrackId.entries) {
            assertTrue("Track ${track.name} English title must not be empty", track.titleEn.isNotBlank())
            assertTrue("Track ${track.name} Hindi title must not be empty", track.titleHi.isNotBlank())
            assertTrue("Track ${track.name} English subtitle must not be empty", track.subtitleEn.isNotBlank())
            assertTrue("Track ${track.name} Hindi subtitle must not be empty", track.subtitleHi.isNotBlank())
            assertTrue("Track ${track.name} raw resource name must not be empty", track.rawName.isNotBlank())
        }
    }

    @Test
    fun testTask159_audioTrackLookupByIdAndIndex() {
        assertEquals(AudioTrackId.TEMPLE_BELL, AudioTrackId.fromRawName("temple_bell"))
        assertEquals(AudioTrackId.OM_AMBIENT, AudioTrackId.fromRawName("om_ambient"))
        assertEquals(AudioTrackId.FLUTE_AMBIENT, AudioTrackId.fromRawName("flute_ambient"))
        assertEquals(AudioTrackId.GAYATRI_MANTRA, AudioTrackId.fromRawName("gayatri_mantra"))
        assertEquals(AudioTrackId.SHIVA_TANDAV, AudioTrackId.fromRawName("shiva_tandav"))
        assertNull(AudioTrackId.fromRawName("non_existent_track"))

        assertEquals(AudioTrackId.TEMPLE_BELL, AudioTrackId.fromIndex(1))
        assertEquals(AudioTrackId.OM_AMBIENT, AudioTrackId.fromIndex(2))
        assertEquals(AudioTrackId.FLUTE_AMBIENT, AudioTrackId.fromIndex(3))
        assertEquals(AudioTrackId.GAYATRI_MANTRA, AudioTrackId.fromIndex(4))
        assertEquals(AudioTrackId.SHIVA_TANDAV, AudioTrackId.fromIndex(5))
        assertEquals(AudioTrackId.MAHA_MRITYUNJAYA, AudioTrackId.fromIndex(6))
        assertEquals(AudioTrackId.VISHNU_SAHASRANAMAM, AudioTrackId.fromIndex(7))
        assertEquals(AudioTrackId.HANUMAN_CHALISA, AudioTrackId.fromIndex(8))
    }

    @Test
    fun testTask159_volumeAndMuteManagement() {
        // Initial state
        assertEquals(1.0f, SadhakAudioManager.volume.value, 0.001f)
        assertFalse(SadhakAudioManager.isMuted.value)

        // Set volume
        SadhakAudioManager.setVolume(0.75f)
        assertEquals(0.75f, SadhakAudioManager.volume.value, 0.001f)

        // Toggle mute
        SadhakAudioManager.toggleMute()
        assertTrue(SadhakAudioManager.isMuted.value)
        // Volume setting persists even when muted
        assertEquals(0.75f, SadhakAudioManager.volume.value, 0.001f)

        // Toggle mute back
        SadhakAudioManager.toggleMute()
        assertFalse(SadhakAudioManager.isMuted.value)
        assertEquals(0.75f, SadhakAudioManager.volume.value, 0.001f)

        // Explicit setMuted
        SadhakAudioManager.setMuted(true)
        assertTrue(SadhakAudioManager.isMuted.value)
        SadhakAudioManager.setMuted(false)
        assertFalse(SadhakAudioManager.isMuted.value)
    }

    @Test
    fun testTask159_fullPlayerModalState() {
        assertFalse(SadhakAudioManager.showFullPlayer.value)
        SadhakAudioManager.openFullPlayer()
        assertTrue(SadhakAudioManager.showFullPlayer.value)
        SadhakAudioManager.closeFullPlayer()
        assertFalse(SadhakAudioManager.showFullPlayer.value)
    }

    @Test
    fun testTask159_repeatToggle() {
        assertFalse(SadhakAudioManager.isRepeatEnabled.value)
        SadhakAudioManager.toggleRepeat()
        assertTrue(SadhakAudioManager.isRepeatEnabled.value)
        SadhakAudioManager.toggleRepeat()
        assertFalse(SadhakAudioManager.isRepeatEnabled.value)
    }

    @Test
    fun testTask159_soundEnabledSafety() {
        assertTrue(SadhakAudioManager.soundEnabled.value)
        SadhakAudioManager.setSoundEnabled(false)
        assertFalse(SadhakAudioManager.soundEnabled.value)

        // Playing while disabled should be a no-op
        SadhakAudioManager.playOneShot(context, AudioTrackId.TEMPLE_BELL)
        assertNull(SadhakAudioManager.currentTrack.value)
        assertFalse(SadhakAudioManager.isPlaying.value)

        // Re-enable
        SadhakAudioManager.setSoundEnabled(true)
        assertTrue(SadhakAudioManager.soundEnabled.value)
    }

    @Test
    fun testTask159_stopAndReleaseIdempotency() {
        SadhakAudioManager.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertFalse(SadhakAudioManager.isBuffering.value)
        assertEquals(0f, SadhakAudioManager.progress.value, 0.001f)
        assertEquals("00:00", SadhakAudioManager.currentPositionStr.value)
        assertNull(SadhakAudioManager.currentTrack.value)

        // Multiple calls to releaseAll must be safe and idempotent
        SadhakAudioManager.releaseAll()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertNull(SadhakAudioManager.currentTrack.value)

        SadhakAudioManager.releaseAll()
        assertFalse(SadhakAudioManager.isPlaying.value)
    }

    @Test
    fun testTask159_templeBellHapticFeedbackTokens() {
        // Verify BELL_RING haptic token
        val bellRing = HapticFeedbackManager.FeedbackType.BELL_RING
        assertNotNull(bellRing)
        assertEquals("BELL_RING", bellRing.name)

        // Verify DEVOTIONAL_TAP haptic token
        val devotionalTap = HapticFeedbackManager.FeedbackType.DEVOTIONAL_TAP
        assertNotNull(devotionalTap)
        assertEquals("DEVOTIONAL_TAP", devotionalTap.name)

        // Verify executing vibrate with context is safe and doesn't throw
        HapticFeedbackManager.vibrate(context, bellRing)
        HapticFeedbackManager.vibrate(context, devotionalTap)
        HapticFeedbackManager.performHaptic(null, bellRing)
    }

    @Test
    fun testTask159_audioQueueCompleteness() {
        val queue = SadhakAudioManager.queue.value
        assertNotNull(queue)
        assertTrue(queue.isNotEmpty())
        for (track in queue) {
            assertFalse("Queue should only contain playable tracks, not one-shots", track.isOneShot)
        }
    }
}
