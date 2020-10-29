package com.example.mymoviddb.home

import com.example.mymoviddb.datasource.remote.NetworkService

object ServiceLocatorHome {
    private var networkService: NetworkService? = null
    var homeAccess: IHomeAccess? = null

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
}