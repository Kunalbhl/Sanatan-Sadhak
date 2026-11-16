package com.example.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.data.AppPreferences
import com.example.notifications.SadhakNotificationHelper

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val type = inputData.getString("notification_type") ?: "prayer"
        val destination = inputData.getString("destination") ?: when (type) {
            "Morning Prayer" -> "Bhakti"
            "Panchang" -> "Calendar"
            "Shloka" -> "Home"
            else -> "Home"
        }

        val isEng = AppPreferences.isEnglish.value

        val (title, text) = when (type) {
            "Morning Prayer" -> {
                if (isEng) {
                    "Morning Prayer & Sadhana Time" to "It's time for your sacred morning prayers and chants. Click to start your Sadhana."
                } else {
                    "प्रातः स्मरण एवं दैनिक साधना" to "अपने पावन दिन का शुभारंभ प्रभु स्मरण और स्तोत्र पाठ के साथ करें।"
                }
            }
            "Panchang" -> {
                if (isEng) {
                    "Daily Panchang Update" to "Today's auspicious Muhurats, Rahu Kaal, and Tithi are updated. Click to view."
                } else {
                    "आज का पावन पंचांग एवं मुहूर्त" to "आज की शुभ तिथि, नक्षत्र और शुभ मुहूर्त विवरण तैयार है।"
                }
            }
            "Shloka" -> {
                if (isEng) {
                    "Daily Sacred Shloka" to "Receive today's divine verse of wisdom from Bhagavad Gita. Click to read."
                } else {
                    "आज का पावन श्लोक" to "श्रीमद्भगवद्गीता का आज का अमृतोपदेश पढ़ने हेतु स्पर्श करें।"
                }
            }
            else -> {
                if (isEng) {
                    "Spiritual Sadhak Alert" to "A new day of spiritual alignment and positive Karma awaits. Click to open."
                } else {
                    "सनातन साधक दैनिक स्मरण" to "आत्म-शांति और आध्यात्मिक जागरण हेतु साधक ऐप खोलें।"
                }
            }
        }

        val channelId = when (type) {
            "Morning Prayer" -> SadhakNotificationHelper.CHANNEL_DAILY_SADHANA
            "Panchang", "Shloka" -> SadhakNotificationHelper.CHANNEL_PANCHANG
            else -> SadhakNotificationHelper.CHANNEL_DAILY_SADHANA
        }

        SadhakNotificationHelper.showNotification(
            context = applicationContext,
            notificationId = 1,
            channelId = channelId,
            title = title,
            text = text,
            destination = destination
        )

        return Result.success()
    }
}
