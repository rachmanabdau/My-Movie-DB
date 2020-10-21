package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.RequestTokenModel
import com.example.mymoviddb.model.Result

interface RemoteServer {

    suspend fun requestAccessToken(): Result<RequestTokenModel?>

    suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ): Result<LoginTokenModel?>

    suspend fun loginAsGuest(): Result<GuestSessionModel?>
}