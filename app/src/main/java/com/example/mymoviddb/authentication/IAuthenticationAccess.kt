package com.example.mymoviddb.authentication

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.*

interface IAuthenticationAccess {

    suspend fun getRequestToken(
        apiKey: String
    ): Result<RequestTokenModel?>

    suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<LoginTokenModel?>

    suspend fun createNewSession(
        apiKey: String,
        requestToken: String
    ): Result<NewSessionModel?>

    suspend fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH): Result<GuestSessionModel?>
}