package com.example.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.data.AppPreferences

class SadhakReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra("reminder_id", 0)
        val reminderType = intent.getStringExtra("reminder_type") ?: "Spiritual Reminder"
        val channelId = intent.getStringExtra("channel_id") ?: SadhakNotificationHelper.CHANNEL_DAILY_SADHANA
        val destination = intent.getStringExtra("destination") ?: "Home"
        val hour = intent.getIntExtra("hour", 8)
        val minute = intent.getIntExtra("minute", 0)

        Log.i("SadhakReminderReceiver", "Received alarm for reminderId=$reminderId type=$reminderType")

        val prefs = context.getSharedPreferences(SadhakReminderScheduler.PREFS_NAME, Context.MODE_PRIVATE)
        val masterEnabled = prefs.getBoolean(SadhakReminderScheduler.KEY_MASTER_NOTIFICATIONS, true)
        if (!masterEnabled) {
            Log.i("SadhakReminderReceiver", "Master notifications disabled, ignoring.")
            return
        }

        val isEng = AppPreferences.isEnglish.value

        if (reminderType == "FestivalEve" || reminderType == "FestivalToday") {
            // Always book the next day's alarm first, then work out today's/tomorrow's festival
            SadhakReminderScheduler.scheduleDailyAlarm(context, reminderId, hour, minute, reminderType, channelId, destination)
            if (!prefs.getBoolean(SadhakReminderScheduler.KEY_FESTIVAL_ENABLED, true)) return
            FestivalAlerts.notifyAsync(context, goAsync(), reminderType == "FestivalEve", reminderId, channelId, destination, isEng)
            return
        }

        val (title, text) = when (reminderType) {
            "Morning Prayer" -> {
                if (isEng) {
                    "Pratah Smaran & Morning Sadhana" to "Start your auspicious day with sacred morning prayers, Stotras, and divine remembrance."
                } else {
                    "प्रातः स्मरण एवं दैनिक साधना" to "अपने पावन दिन का शुभारंभ प्रभु स्मरण, स्तोत्र पाठ और आत्म-साधना के साथ करें।"
                }
            }
            "Evening Prayer" -> {
                if (isEng) {
                    "Sayankal Sandhya & Aarti" to "Time for evening Aarti, gratitude, and divine connection before the day concludes."
                } else {
                    "सायंकाल संध्या व दीप वंदन" to "संध्या आरती और प्रभु कृतज्ञता का समय। अपने मन को ईश्वर चरणों में समर्पित करें।"
                }
            }
            "Brahma Muhurta" -> {
                if (isEng) {
                    "Brahma Muhurta Awakening" to "The divine nectar hour (Amrit Vela) has begun. Supreme time for deep meditation and Gayatri Japa."
                } else {
                    "ब्रह्म मुहूर्त अमृत वेला" to "पवित्र ब्रह्म मुहूर्त का समय हो गया है। गहन ध्यान, प्राणायाम और गायत्री मंत्र जप हेतु सर्वोत्तम समय।"
                }
            }
            "Sandhya" -> {
                if (isEng) {
                    "Sandhya Kaal Remembrance" to "Auspicious twilight transition. Light the holy lamp (Diya) and chant sacred mantras."
                } else {
                    "संध्या काल पावन वेला" to "संध्या काल का शुभ समय। दीप प्रज्वलित करें और अपने ईष्ट देव का स्मरण करें।"
                }
            }
            "Japa" -> {
                if (isEng) {
                    "Daily Japa Mala Commitment" to "Fulfill your daily sacred Naam Japa rounds. Keep your spiritual consciousness aligned."
                } else {
                    "दैनिक नाम जप संकल्प" to "अपनी दैनिक जप माला का संकल्प पूर्ण करें। मन को पावन भगवन्नाम में लीन करें।"
                }
            }
            "Panchang" -> {
                if (isEng) {
                    "Today's Sacred Panchang & Muhurat" to "Today's auspicious Tithi, Nakshatra, Rahu Kaal, and Shubha Muhurat are ready to view."
                } else {
                    "आज का पावन पंचांग एवं मुहूर्त" to "आज की शुभ तिथि, नक्षत्र, राहुकाल और शुभ मुहूर्त विवरण उपलब्ध है।"
                }
            }
            "Shloka" -> {
                if (isEng) {
                    "Divine Shloka of the Day" to "Receive today's eternal nectar from the Shrimad Bhagavad Gita to guide your actions."
                } else {
                    "आज का पावन श्लोक" to "श्रीमद्भगवद्गीता का आज का अमृतोपदेश आपके जीवन दर्शन और कर्म को आलोकित करने के लिए।"
                }
            }
            "Both" -> {
                if (isEng) {
                    "Daily Shloka & Panchang Wisdom" to "Today's Gita verse and auspicious Panchang timings are ready. Click to view."
                } else {
                    "दैनिक श्लोक एवं पंचांग अमृत" to "आज का पावन श्लोक और शुभ पंचांग मुहूर्त उपलब्ध हैं। दर्शन हेतु स्पर्श करें।"
                }
            }
            else -> {
                if (isEng) {
                    "Sanatan Sadhak Reminder" to "Take a moment for inner peace, mantra remembrance, and mindful living."
                } else {
                    "सनातन साधक पावन स्मरण" to "आत्म-शांति, भगवन्नाम स्मरण और आध्यात्मिक जागरण के लिए एक क्षण निकालें।"
                }
            }
        }

        SadhakNotificationHelper.showNotification(
            context = context,
            notificationId = if (reminderId != 0) reminderId else 1,
            channelId = channelId,
            title = title,
            text = text,
            destination = destination
        )

        // Reschedule for next day at the exact same hour and minute
        if (reminderId != 0) {
            SadhakReminderScheduler.scheduleDailyAlarm(
                context = context,
                reminderId = reminderId,
                hour = hour,
                minute = minute,
                reminderType = reminderType,
                channelId = channelId,
                destination = destination
            )
        }
    }
}
