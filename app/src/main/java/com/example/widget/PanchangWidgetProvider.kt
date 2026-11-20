package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.MainActivity
import com.example.R
import com.example.data.AppPreferences
import com.example.data.AstrologyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Calendar

/**
 * Home-screen widget: today's Tithi, Paksha, Nakshatra and any festival, from the same Panchang
 * engine and location as the Calendar. Tapping it opens the Panchang Calendar.
 */
class PanchangWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        when (intent.action) {
            Intent.ACTION_DATE_CHANGED, Intent.ACTION_TIME_CHANGED, Intent.ACTION_TIMEZONE_CHANGED -> refreshAll(context)
        }
    }

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeoutOrNull(8_000) { render(context, manager, ids) }
            } catch (e: Exception) {
                Log.e("PanchangWidget", "Update failed", e)
            } finally {
                pending.finish()
            }
        }
    }

    private suspend fun render(context: Context, manager: AppWidgetManager, ids: IntArray) {
        val isEng = AppPreferences.isEnglish.value
        val prefs = context.getSharedPreferences("panchang_prefs", Context.MODE_PRIVATE)
        val cal = Calendar.getInstance()
        val data = AstrologyService.fetchRealPanchang(
            day = cal.get(Calendar.DAY_OF_MONTH),
            month = cal.get(Calendar.MONTH) + 1,
            year = cal.get(Calendar.YEAR),
            hours = 6, minutes = 0, seconds = 0,
            latitude = prefs.getFloat("loc_lat", 28.6139f).toDouble(),
            longitude = prefs.getFloat("loc_lng", 77.2090f).toDouble(),
            timezone = prefs.getFloat("loc_tz", 5.5f).toDouble()
        ).data

        val open = PendingIntent.getActivity(
            context, 4001,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("destination", "Calendar")
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        for (id in ids) {
            val v = RemoteViews(context.packageName, R.layout.widget_panchang)
            if (data == null) {
                v.setTextViewText(R.id.widget_tithi, if (isEng) "Open Sanatan Sadhak" else "सनातन साधक खोलें")
                v.setTextViewText(R.id.widget_detail, if (isEng) "Today's Panchang will appear here" else "आज का पंचांग यहाँ दिखेगा")
                v.setTextViewText(R.id.widget_festival, "")
            } else {
                v.setTextViewText(R.id.widget_tithi, data.tithi)
                v.setTextViewText(R.id.widget_detail, "${data.nakshatra} · ${data.weekday.substringBefore(" (")}")
                val f = data.festival.trim()
                val festival = if (f.isEmpty() || f == "—") "" else {
                    val en = f.substringBefore("(").trim()
                    val hi = f.substringAfter("(", "").substringBefore(")").trim().ifEmpty { en }
                    if (isEng) en else hi
                }
                v.setTextViewText(R.id.widget_festival, festival)
            }
            v.setOnClickPendingIntent(R.id.widget_root, open)
            manager.updateAppWidget(id, v)
        }
    }

    companion object {
        /** Refresh all placed widgets (called when the app opens, so it never shows yesterday's date). */
        fun refreshAll(context: Context) {
            val mgr = AppWidgetManager.getInstance(context)
            val ids = mgr.getAppWidgetIds(ComponentName(context, PanchangWidgetProvider::class.java))
            if (ids.isNotEmpty()) {
                context.sendBroadcast(Intent(context, PanchangWidgetProvider::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                })
            }
        }
    }
}
