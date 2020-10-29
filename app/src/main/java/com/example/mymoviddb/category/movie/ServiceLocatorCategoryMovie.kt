package com.example.mymoviddb.category.movie

import androidx.annotation.VisibleForTesting
import com.example.mymoviddb.datasource.remote.NetworkService

object ServiceLocatorCategoryMovie {
    private var networkService: NetworkService? = null
    private val lock = Any()

    @VisibleForTesting
    var homeAccess: ICategoryMovieListAccess? = null
        @VisibleForTesting set

    fun provideServiceLocatorCategory(service: NetworkService): ICategoryMovieListAccess {
        synchronized(this) {
            return homeAccess ?: createHomeAccess(service)
        }
    }

    private fun createHomeAccess(service: NetworkService): ICategoryMovieListAccess {
        val access = CategoryMovieListAccess(networkService ?: createNetworkService(service))
        homeAccess = access
        return access
    }

    private fun createNetworkService(service: NetworkService): NetworkService {
        networkService = service
        return service
    }

    @VisibleForTesting
    fun resetHomeAccess() {
        synchronized(lock) {
            networkService = null
            homeAccess = null
        }
    }
}