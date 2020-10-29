package com.example.mymoviddb

import android.app.Application
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.authentication.ServiceLocatorAuthentication
import com.example.mymoviddb.datasource.remote.NetworkAPI
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.home.ServiceLocatorHome
import timber.log.Timber

class MyMovieDBApplication : Application() {

    val server = NetworkAPI.retrofitService

    val homeAccess: IHomeAccess
        get() = ServiceLocatorHome.provideHomeAccess(server)

    val authenticatationAccess: IAuthenticationAccess
        get() = ServiceLocatorAuthentication.provideAuthenticationAccess(server)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}