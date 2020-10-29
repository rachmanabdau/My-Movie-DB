package com.example.mymoviddb.authentication

import androidx.annotation.VisibleForTesting
import com.example.mymoviddb.datasource.remote.NetworkService

object ServiceLocatorAuthentication {

    private var networkAccess: NetworkService? = null

    @VisibleForTesting
    var authenticationAccess: IAuthenticationAccess? = null
        @VisibleForTesting set

    fun provideAuthenticationAccess(networkService: NetworkService): IAuthenticationAccess {
        synchronized(this) {
            return authenticationAccess ?: createAutehnticationAccess(networkService)
        }
    }

    private fun createAutehnticationAccess(networkService: NetworkService): IAuthenticationAccess {
        val access = AuthenticationAccess(networkAccess ?: createNetworkService(networkService))
        authenticationAccess = access
        return access
    }

    private fun createNetworkService(networkService: NetworkService): NetworkService {
        val service = networkService
        networkAccess = service
        return service
    }
}