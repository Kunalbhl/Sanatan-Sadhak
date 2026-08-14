package com.example

import com.example.ui.audio.AudioTrackId
import com.example.ui.audio.SadhakAudioManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SadhakAudioManagerTest {

    @Before
    fun setUp() {
        SadhakAudioManager.setSoundEnabled(true)
    }

    @Test
    fun testAudioTrackIdEnumMapping() {
        assertEquals(AudioTrackId.TEMPLE_BELL, AudioTrackId.fromRawName("temple_bell"))
        assertEquals(AudioTrackId.SADHANA_SUCCESS, AudioTrackId.fromRawName("sadhana_success"))
        assertEquals(AudioTrackId.OM_AMBIENT, AudioTrackId.fromRawName("om_ambient"))
        assertEquals(AudioTrackId.FLUTE_AMBIENT, AudioTrackId.fromRawName("flute_ambient"))
        assertEquals(AudioTrackId.GAYATRI_MANTRA, AudioTrackId.fromRawName("gayatri_mantra"))
        assertEquals(AudioTrackId.SHIVA_TANDAV, AudioTrackId.fromRawName("shiva_tandav"))
        assertEquals(AudioTrackId.MAHA_MRITYUNJAYA, AudioTrackId.fromRawName("maha_mrityunjaya"))
        assertEquals(AudioTrackId.VISHNU_SAHASRANAMAM, AudioTrackId.fromRawName("vishnu_sahasranamam"))
        assertEquals(AudioTrackId.HANUMAN_CHALISA, AudioTrackId.fromRawName("hanuman_chalisa"))

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
    fun testOneShotFlag() {
        assertTrue(AudioTrackId.TEMPLE_BELL.isOneShot)
        assertTrue(AudioTrackId.SADHANA_SUCCESS.isOneShot)
        assertFalse(AudioTrackId.OM_AMBIENT.isOneShot)
        assertFalse(AudioTrackId.FLUTE_AMBIENT.isOneShot)
        assertFalse(AudioTrackId.GAYATRI_MANTRA.isOneShot)
        assertFalse(AudioTrackId.SHIVA_TANDAV.isOneShot)
        assertFalse(AudioTrackId.MAHA_MRITYUNJAYA.isOneShot)
        assertFalse(AudioTrackId.VISHNU_SAHASRANAMAM.isOneShot)
        assertFalse(AudioTrackId.HANUMAN_CHALISA.isOneShot)
    }

    @Test
    fun testSoundEnableToggle() {
        SadhakAudioManager.setSoundEnabled(false)
        assertFalse(SadhakAudioManager.soundEnabled.value)

        // Calling playOneShot when disabled should be safely ignored
        SadhakAudioManager.playOneShot(AudioTrackId.TEMPLE_BELL)
        assertNull(SadhakAudioManager.currentTrack.value)

        SadhakAudioManager.setSoundEnabled(true)
        assertTrue(SadhakAudioManager.soundEnabled.value)
    }

    @Test
    fun testStopResetsState() {
        SadhakAudioManager.stop()
        assertFalse(SadhakAudioManager.isPlaying.value)
        assertFalse(SadhakAudioManager.isBuffering.value)
        assertEquals(0f, SadhakAudioManager.progress.value, 0.001f)
        assertNull(SadhakAudioManager.currentTrack.value)
    }

    @Test
    fun testReleaseAllIdempotency() {
        SadhakAudioManager.releaseAll()
        assertNull(SadhakAudioManager.currentTrack.value)
        assertFalse(SadhakAudioManager.isPlaying.value)

        // Calling releaseAll multiple times should be safe and idempotent
        SadhakAudioManager.releaseAll()
        assertNull(SadhakAudioManager.currentTrack.value)
    }
}
