package com.terabyte.jetpackweather.application

import android.app.Application
import android.util.Log
import com.terabyte.jetpackweather.BuildConfig
import timber.log.Timber

class ApplicationController: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.IS_DEBUGGABLE) {
            Timber.plant(Timber.DebugTree())
        }
    }
}