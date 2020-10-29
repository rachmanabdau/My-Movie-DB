package com.example.mymoviddb.sharedData

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.RequestTokenModel
import com.example.mymoviddb.model.Result

class FakeAuthenticationAccess : IAuthenticationAccess {
    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        val fakeUsername = "rachmanabdau"
        val fakePassword = "123456"
        val token = BuildConfig.V3_AUTH

        return if ((fakeUsername == username && fakePassword == password && token.equals(
                other = requestToken
            ))
        ) {
            Result.Success(
                LoginTokenModel(
                    true,
                    "2200-08-27 16:26:40 UTC",
                    "1ce82ec1223641636ad4a60b07de3581"
                )
            )
        } else Result.Error(Exception("Invalid username or password"))
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {

        val token = BuildConfig.V3_AUTH

        return if (token == apiKey) {
            Result.Success(
                GuestSessionModel(
                    "2200-08-27 16:26:40 UTC",
                    "1ce82ec1223641636ad4a60b07de3581",
                    true
                )
            )
        } else Result.Error(Exception("Token is invalid."))
    }

}