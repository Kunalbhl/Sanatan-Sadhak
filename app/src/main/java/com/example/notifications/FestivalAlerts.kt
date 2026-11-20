package com.example.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.data.AstrologyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Calendar

/**
 * Festival / vrat alerts. Nothing is hard-coded: the day's Tithi, Paksha and observance come from the
 * same Panchang engine the Calendar uses, for the location the user chose there.
 */
object FestivalAlerts {
    private const val TAG = "FestivalAlerts"

    fun notifyAsync(
        context: Context,
        pending: BroadcastReceiver.PendingResult,
        isEve: Boolean,
        notificationId: Int,
        channelId: String,
        destination: String,
        isEng: Boolean
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeoutOrNull(8_000) { run(context, isEve, notificationId, channelId, destination, isEng) }
            } catch (e: Exception) {
                Log.e(TAG, "Festival alert failed", e)
            } finally {
                pending.finish()
            }
        }
    }

    private suspend fun run(context: Context, isEve: Boolean, notificationId: Int, channelId: String, destination: String, isEng: Boolean) {
        val prefs = context.getSharedPreferences("panchang_prefs", Context.MODE_PRIVATE)
        val lat = prefs.getFloat("loc_lat", 28.6139f).toDouble()
        val lng = prefs.getFloat("loc_lng", 77.2090f).toDouble()
        val tz = prefs.getFloat("loc_tz", 5.5f).toDouble()

        val cal = Calendar.getInstance()
        if (isEve) cal.add(Calendar.DAY_OF_YEAR, 1)
        val result = AstrologyService.fetchRealPanchang(
            day = cal.get(Calendar.DAY_OF_MONTH),
            month = cal.get(Calendar.MONTH) + 1,
            year = cal.get(Calendar.YEAR),
            hours = 6, minutes = 0, seconds = 0,
            latitude = lat, longitude = lng, timezone = tz
        )
        val p = result.data ?: return
        val festival = p.festival.trim()
        if (festival.isEmpty() || festival == "—" || festival == "-") return

        // "English name (हिन्दी नाम)" → pick the language the user uses in the app
        val en = festival.substringBefore("(").trim()
        val hi = festival.substringAfter("(", "").substringBefore(")").trim().ifEmpty { en }
        val name = if (isEng) en else hi
        val detail = listOf(p.paksha, p.tithi).filter { it.isNotBlank() && it != "—" }.joinToString(" · ")

        val title = when {
            isEve && isEng -> "Tomorrow: $name"
            isEve -> "कल: $name"
            isEng -> "Today: $name"
            else -> "आज: $name"
        }
        val body = (if (isEng) "$detail. Dates can differ by a day in some regions and traditions. Tap for timings in the Panchang Calendar."
                    else "$detail। कुछ क्षेत्रों और परंपराओं में तिथि एक दिन आगे-पीछे हो सकती है। समय पंचांग कैलेंडर में देखें।")
        SadhakNotificationHelper.showNotification(context, notificationId, channelId, title, body, destination)
    }
}
