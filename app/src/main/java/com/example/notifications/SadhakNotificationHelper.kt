package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.example.data.AppPreferences

object SadhakNotificationHelper {

    const val CHANNEL_DAILY_SADHANA = "daily_sadhana_channel"
    const val CHANNEL_PANCHANG = "panchang_shloka_channel"
    const val CHANNEL_BRAHMA_MUHURTA = "brahma_muhurta_channel"
    const val CHANNEL_SANDHYA = "sandhya_channel"
    const val CHANNEL_JAPA = "japa_channel"
    const val CHANNEL_FESTIVAL = "festival_channel"

    const val ID_SHLOKA_PANCHANG = 101
    const val ID_MORNING_PRAYER = 102
    const val ID_EVENING_PRAYER = 103
    const val ID_BRAHMA_MUHURTA = 104
    const val ID_SANDHYA = 105
    const val ID_JAPA = 106
    const val ID_FESTIVAL_TODAY = 107
    const val ID_FESTIVAL_EVE = 108

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

            val isEng = AppPreferences.isEnglish.value

            val channels = listOf(
                NotificationChannel(
                    CHANNEL_DAILY_SADHANA,
                    if (isEng) "Daily Sadhana & Prayers" else "दैनिक साधना एवं प्रार्थना",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = if (isEng) "Reminders for daily morning and evening prayer sessions" else "दैनिक प्रातः एवं संध्या प्रार्थना सत्रों के अनुस्मारक"
                    enableVibration(true)
                    enableLights(true)
                },
                NotificationChannel(
                    CHANNEL_PANCHANG,
                    if (isEng) "Panchang & Sacred Shlokas" else "पंचांग एवं पावन श्लोक",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = if (isEng) "Daily Tithi, Muhurat, and Gita verse updates" else "दैनिक तिथि, शुभ मुहूर्त एवं गीता श्लोक के अपडेट"
                    enableVibration(true)
                },
                NotificationChannel(
                    CHANNEL_BRAHMA_MUHURTA,
                    if (isEng) "Brahma Muhurta Awakening" else "ब्रह्म मुहूर्त जागरण",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = if (isEng) "Sacred pre-dawn meditation & spiritual awakening" else "पवित्र ब्रह्म मुहूर्त ध्यान और आध्यात्मिक जागरण"
                    enableVibration(true)
                },
                NotificationChannel(
                    CHANNEL_SANDHYA,
                    if (isEng) "Sandhya Kaal Aarti" else "संध्या काल आरती व वंदन",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = if (isEng) "Evening twilight remembrance and Aarti" else "संध्याकालीन ईश स्मरण और आरती"
                },
                NotificationChannel(
                    CHANNEL_JAPA,
                    if (isEng) "Japa & Mantra Chanting" else "नाम जप एवं मंत्र साधना",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = if (isEng) "Commitment reminders for daily Japa Mala rounds" else "दैनिक जप माला संकल्प के अनुस्मारक"
                },
                NotificationChannel(
                    CHANNEL_FESTIVAL,
                    if (isEng) "Festivals & Vrat Days" else "पर्व एवं व्रत तिथियाँ",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = if (isEng) "Ekadashi, Pradosh, Purnima, Amavasya, Sankashti and other sacred days, the evening before and on the day" else "एकादशी, प्रदोष, पूर्णिमा, अमावस्या, संकष्टी आदि पावन तिथियाँ, एक दिन पहले और उसी दिन"
                    enableVibration(true)
                }
            )

            channels.forEach { channel ->
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(
        context: Context,
        notificationId: Int,
        channelId: String,
        title: String,
        text: String,
        destination: String = "Home"
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        // Make sure channels are created
        createNotificationChannels(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("destination", destination)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_sanatan_sadhak)
            .setContentTitle(title)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        try {
            notificationManager.notify(notificationId, builder.build())
        } catch (e: Exception) {
            android.util.Log.e("SadhakNotificationHelper", "Error showing notification", e)
        }
    }
}
