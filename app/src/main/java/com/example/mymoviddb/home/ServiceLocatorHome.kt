package com.example.mymoviddb.home

import androidx.annotation.VisibleForTesting
import com.example.mymoviddb.datasource.remote.NetworkService

object ServiceLocatorHome {
    private var networkService: NetworkService? = null
    private val lock = Any()

    @VisibleForTesting
    var homeAccess: IHomeAccess? = null
        @VisibleForTesting set

    fun provideHomeAccess(service: NetworkService): IHomeAccess {
        synchronized(this) {
            return homeAccess ?: createHomeAccess(service)
        }
    }

    private fun createHomeAccess(service: NetworkService): IHomeAccess {
        val access = HomeAccess(networkService ?: createNetworkService(service))
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