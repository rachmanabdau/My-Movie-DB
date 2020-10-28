package com.example.mymoviddb.authentication

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.RequestTokenModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util

class AuthenticationAccess(private val access: NetworkService) : IAuthenticationAccess {

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        return try {
            val result = access.loginAsync(
                username, password, requestToken?.requestToken ?: ""
            ).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return Util.returnError(result)
            }
        } catch (e: Exception) {
            return Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        return try {
            val result = access.loginAsGuestAsync(apiKey).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return Util.returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

}