package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.RequestTokenModel
import com.example.mymoviddb.model.Result

class FakeRemoteServer : RemoteServer {

    override suspend fun requestAccessToken(apiKey: String): Result<RequestTokenModel?> {
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
            Result.Success(
                RequestTokenModel(
                    success = true,
                    expiresAt = "2020-10-23 00:25:25 UTC",
                    requestToken = "1a137cd5ef00a95ef132708e461fb906"
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ): Result<LoginTokenModel?> {
        //TODO("Not yet implemented")
        return Result.Success(LoginTokenModel(true, "", ""))
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
            Result.Success(
                GuestSessionModel(
                    success = true,
                    expiresAt = "2020-10-23 00:25:25 UTC",
                    guestSessionId = "1a137cd5ef00a95ef132708e461fb906"
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }
}