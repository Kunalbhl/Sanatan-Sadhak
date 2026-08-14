package com.example

import android.app.Application
import com.example.di.AppContainer
import com.example.data.AppPreferences

class SadhakApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
        container = AppContainer(this)
    }
}
