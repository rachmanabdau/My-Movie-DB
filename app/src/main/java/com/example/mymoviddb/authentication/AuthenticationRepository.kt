package com.example.mymoviddb.authentication

import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.remote.NetworkService
import com.example.mymoviddb.utils.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(private val access: NetworkService) :
    IAuthenticationAccess {

    override suspend fun getRequestToken(apiKey: String): Result<RequestTokenModel?> {
        return Util.getDataFromServer {
            access.getRequestTokenAsync(apiKey).await()
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        return Util.getDataFromServer {
            access.loginAsync(
                username, password, requestToken?.requestToken ?: ""
            ).await()
        }
    }

    override suspend fun createNewSession(
        requestToken: String,
        apiKey: String
    ): Result<NewSessionModel?> {
        return Util.getDataFromServer {
            access.createSessionAsync(requestToken, apiKey).await()
        }
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        return Util.getDataFromServer {
            access.loginAsGuestAsync(apiKey).await()
        }
    }

}