package com.example.mymoviddb.sharedData

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Util

class FakeAuthenticationAccess(private val networkService: NetworkService) : IAuthenticationAccess {

    override suspend fun getRequestToken(apiKey: String): Result<RequestTokenModel?> {
        val newRequestToken = networkService.getRequestTokenAsync().await()

        return if (newRequestToken.isSuccessful) {
            Result.Success(newRequestToken.body())
        } else {
            Util.returnError(newRequestToken)
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        val fakeUsername = "rachmanabdau"
        val fakePassword = "123456"
        val token = BuildConfig.V3_AUTH

        val loginResult = networkService.loginAsync(fakeUsername, fakePassword, token).await()

        return if (loginResult.isSuccessful) {
            Result.Success(loginResult.body())
        } else Util.returnError(loginResult)
    }

    override suspend fun createNewSession(
        requestToken: String,
        apiKey: String
    ): Result<NewSessionModel?> {
        val newSession = networkService.createSessionAsync(requestToken, apiKey).await()

        return if (newSession.isSuccessful) {
            Result.Success(newSession.body())
        } else Util.returnError(newSession)
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        val loginasGuestResult = networkService.loginAsGuestAsync(apiKey).await()

        return if (loginasGuestResult.isSuccessful) Result.Success(loginasGuestResult.body())
        else Util.returnError(loginasGuestResult)
    }

}