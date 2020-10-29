package com.example.mymoviddb.category.tv

import androidx.annotation.VisibleForTesting
import com.example.mymoviddb.datasource.remote.NetworkService

object ServiceLocatorCategoryTV {
    private var networkService: NetworkService? = null
    private val lock = Any()

    @VisibleForTesting
    var homeAccess: ICategoryTVListAccess? = null
        @VisibleForTesting set

    fun provideServiceLocatorCategoryTV(service: NetworkService): ICategoryTVListAccess {
        synchronized(this) {
            return homeAccess ?: createHomeAccess(service)
        }
    }

    private fun createHomeAccess(service: NetworkService): ICategoryTVListAccess {
        val access = CategoryTVListIAccess(networkService ?: createNetworkService(service))
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