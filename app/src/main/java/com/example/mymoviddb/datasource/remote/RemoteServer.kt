package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.RequestTokenModel
import com.example.mymoviddb.model.Result

interface RemoteServer {

    suspend fun requestAccessToken(apiKey: String = BuildConfig.V3_AUTH): Result<RequestTokenModel?>

    suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ): Result<LoginTokenModel?>

    suspend fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH): Result<GuestSessionModel?>
}