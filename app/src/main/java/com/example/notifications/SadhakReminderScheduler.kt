package com.example.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar

object SadhakReminderScheduler {
    private const val TAG = "SadhakScheduler"
    const val PREFS_NAME = "sadhak_reminder_prefs"

    const val KEY_MASTER_NOTIFICATIONS = "master_notifications_enabled"
    const val KEY_SHLOKA_PANCHANG_ENABLED = "shloka_panchang_enabled"
    const val KEY_SHLOKA_PANCHANG_HOUR = "shloka_panchang_hour"
    const val KEY_SHLOKA_PANCHANG_MINUTE = "shloka_panchang_minute"
    const val KEY_SHLOKA_PANCHANG_TYPE = "shloka_panchang_type"

    const val KEY_MORNING_PRAYER_ENABLED = "morning_prayer_enabled"
    const val KEY_MORNING_PRAYER_HOUR = "morning_prayer_hour"
    const val KEY_MORNING_PRAYER_MINUTE = "morning_prayer_minute"

    const val KEY_EVENING_PRAYER_ENABLED = "evening_prayer_enabled"
    const val KEY_EVENING_PRAYER_HOUR = "evening_prayer_hour"
    const val KEY_EVENING_PRAYER_MINUTE = "evening_prayer_minute"

    const val KEY_BRAHMA_MUHURTA_ENABLED = "brahma_muhurta_enabled"
    const val KEY_SANDHYA_ENABLED = "sandhya_enabled"
    const val KEY_JAPA_ENABLED = "japa_enabled"
    const val KEY_FESTIVAL_ENABLED = "festival_alerts_enabled"

    fun scheduleDailyAlarm(
        context: Context,
        reminderId: Int,
        hour: Int,
        minute: Int,
        reminderType: String,
        channelId: String,
        destination: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val intent = Intent(context, SadhakReminderReceiver::class.java).apply {
            action = "com.example.ACTION_SADHAK_REMINDER"
            putExtra("reminder_id", reminderId)
            putExtra("reminder_type", reminderType)
            putExtra("channel_id", channelId)
            putExtra("destination", destination)
            putExtra("hour", hour)
            putExtra("minute", minute)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val now = Calendar.getInstance()
        if (calendar.before(now)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            Log.i(TAG, "Scheduled reminder ID $reminderId for ${calendar.time}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to schedule alarm for ID $reminderId", e)
        }
    }

    fun cancelAlarm(context: Context, reminderId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, SadhakReminderReceiver::class.java).apply {
            action = "com.example.ACTION_SADHAK_REMINDER"
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            Log.i(TAG, "Canceled reminder ID $reminderId")
        }
    }

    fun rescheduleAllActive(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val masterEnabled = prefs.getBoolean(KEY_MASTER_NOTIFICATIONS, true)
        if (!masterEnabled) {
            Log.i(TAG, "Master notifications disabled, skipping reschedule")
            return
        }

        // Shloka & Panchang
        if (prefs.getBoolean(KEY_SHLOKA_PANCHANG_ENABLED, false)) {
            val h = prefs.getInt(KEY_SHLOKA_PANCHANG_HOUR, 8)
            val m = prefs.getInt(KEY_SHLOKA_PANCHANG_MINUTE, 0)
            val type = prefs.getString(KEY_SHLOKA_PANCHANG_TYPE, "Both") ?: "Both"
            val dest = when (type) {
                "Morning Prayer" -> "Bhakti"
                "Panchang" -> "Calendar"
                "Shloka" -> "Home"
                else -> "Home"
            }
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_SHLOKA_PANCHANG,
                hour = h,
                minute = m,
                reminderType = type,
                channelId = SadhakNotificationHelper.CHANNEL_PANCHANG,
                destination = dest
            )
        }

        // Morning Prayer
        if (prefs.getBoolean(KEY_MORNING_PRAYER_ENABLED, false)) {
            val h = prefs.getInt(KEY_MORNING_PRAYER_HOUR, 6)
            val m = prefs.getInt(KEY_MORNING_PRAYER_MINUTE, 0)
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_MORNING_PRAYER,
                hour = h,
                minute = m,
                reminderType = "Morning Prayer",
                channelId = SadhakNotificationHelper.CHANNEL_DAILY_SADHANA,
                destination = "Bhakti"
            )
        }

        // Evening Prayer
        if (prefs.getBoolean(KEY_EVENING_PRAYER_ENABLED, false)) {
            val h = prefs.getInt(KEY_EVENING_PRAYER_HOUR, 19)
            val m = prefs.getInt(KEY_EVENING_PRAYER_MINUTE, 0)
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_EVENING_PRAYER,
                hour = h,
                minute = m,
                reminderType = "Evening Prayer",
                channelId = SadhakNotificationHelper.CHANNEL_DAILY_SADHANA,
                destination = "Bhakti"
            )
        }

        // Brahma Muhurta
        if (prefs.getBoolean(KEY_BRAHMA_MUHURTA_ENABLED, false)) {
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_BRAHMA_MUHURTA,
                hour = 4,
                minute = 30,
                reminderType = "Brahma Muhurta",
                channelId = SadhakNotificationHelper.CHANNEL_BRAHMA_MUHURTA,
                destination = "Bhakti"
            )
        }

        // Sandhya
        if (prefs.getBoolean(KEY_SANDHYA_ENABLED, false)) {
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_SANDHYA,
                hour = 18,
                minute = 30,
                reminderType = "Sandhya",
                channelId = SadhakNotificationHelper.CHANNEL_SANDHYA,
                destination = "Bhakti"
            )
        }

        // Japa
        if (prefs.getBoolean(KEY_JAPA_ENABLED, false)) {
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_JAPA,
                hour = 8,
                minute = 0,
                reminderType = "Japa",
                channelId = SadhakNotificationHelper.CHANNEL_JAPA,
                destination = "BhaktiTools"
            )
        }

        // Festival / vrat alerts from the Panchang engine: evening before and morning of the day
        if (prefs.getBoolean(KEY_FESTIVAL_ENABLED, true)) {
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_FESTIVAL_EVE,
                hour = 19,
                minute = 0,
                reminderType = "FestivalEve",
                channelId = SadhakNotificationHelper.CHANNEL_FESTIVAL,
                destination = "Calendar"
            )
            scheduleDailyAlarm(
                context = context,
                reminderId = SadhakNotificationHelper.ID_FESTIVAL_TODAY,
                hour = 6,
                minute = 30,
                reminderType = "FestivalToday",
                channelId = SadhakNotificationHelper.CHANNEL_FESTIVAL,
                destination = "Calendar"
            )
        }
    }
}
