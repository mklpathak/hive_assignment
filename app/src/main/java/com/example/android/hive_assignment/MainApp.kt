package com.example.android.hive_assignment

import android.app.Application
import com.facebook.stetho.Stetho


class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}