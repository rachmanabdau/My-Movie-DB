package com.example.mymoviddb

import android.app.Application
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.ServiceLocatorCategoryMovie
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
import com.example.mymoviddb.category.tv.ServiceLocatorCategoryTV
import com.example.mymoviddb.datasource.remote.NetworkAPI
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.home.ServiceLocatorHome
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyMovieDBApplication : Application() {

    val server = NetworkAPI.retrofitService

    val homeAccess: IHomeAccess
        get() = ServiceLocatorHome.provideHomeAccess(server)

    /*val authenticatationAccess: IAuthenticationAccess
        get() = ServiceLocatorAuthentication.provideAuthenticationAccess(server)*/

    val categoryMovieListAccess: ICategoryMovieListAccess
        get() = ServiceLocatorCategoryMovie.provideServiceLocatorCategoryMovie(server)

    val categoryTVListAccess: ICategoryTVListAccess
        get() = ServiceLocatorCategoryTV.provideServiceLocatorCategoryTV(server)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}