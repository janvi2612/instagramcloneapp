package com.example.instagramclone

import android.app.Application
import com.example.instagramclone.utils.PrefManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
@HiltAndroidApp
class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        PrefManager.init(this)
        Timber.plant(Timber.DebugTree())

    }
}