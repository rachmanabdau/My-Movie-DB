package com.example.mymoviddb.login

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.*

interface ILoginAccess {

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
        requestToken: String,
        apiKey: String
    ): Result<NewSessionModel?>

    suspend fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH): Result<GuestSessionModel?>
}