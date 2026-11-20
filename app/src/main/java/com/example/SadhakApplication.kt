package com.example

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.imageLoader
import com.example.di.AppContainer
import com.example.data.AppPreferences
import com.example.notifications.SadhakNotificationHelper
import com.example.notifications.SadhakReminderScheduler

class SadhakApplication : Application(), ImageLoaderFactory {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        try {
            AppPreferences.init(this)
            container = AppContainer(this)
            SadhakNotificationHelper.createNotificationChannels(this)
            SadhakReminderScheduler.rescheduleAllActive(this)
            com.example.widget.PanchangWidgetProvider.refreshAll(this)
        } catch (e: Exception) {
            Log.e("SadhakApplication", "Initialization error in SadhakApplication", e)
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        try {
            if (level >= TRIM_MEMORY_RUNNING_LOW) {
                // Trim Coil's in-memory image cache under memory pressure
                imageLoader.memoryCache?.clear()
            }
        } catch (e: Exception) {
            Log.e("SadhakApplication", "Error during onTrimMemory", e)
        }
    }
}

