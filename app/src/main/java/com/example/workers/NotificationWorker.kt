package com.example.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val channelId = "prayer_reminders"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Spiritual Reminders", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val type = inputData.getString("notification_type") ?: "prayer"
        val destination = inputData.getString("destination") ?: when (type) {
            "Morning Prayer" -> "Bhakti"
            "Panchang" -> "Calendar"
            "Shloka" -> "Home"
            else -> "Home"
        }

        val title = when (type) {
            "Morning Prayer" -> "Morning Prayer & Sadhana Time"
            "Panchang" -> "Daily Panchang Update"
            "Shloka" -> "Daily Sacred Shloka"
            else -> "Spiritual Sadhak Alert"
        }

        val text = when (type) {
            "Morning Prayer" -> "It's time for your sacred morning prayers and chants. Click to start your Sadhana."
            "Panchang" -> "Today's auspicious Muhurats, Rahu Kaal, and Tithi are updated. Click to view."
            "Shloka" -> "Receive today's divine verse of wisdom from Bhagavad Gita. Click to read."
            else -> "A new day of spiritual alignment and positive Karma awaits. Click to open."
        }

        val intent = Intent(applicationContext, com.example.MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination", destination)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
        
        return Result.success()
    }
}
