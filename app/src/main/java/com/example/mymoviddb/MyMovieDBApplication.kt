package com.example.mymoviddb

import android.app.Application
import timber.log.Timber

class MyMovieDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}