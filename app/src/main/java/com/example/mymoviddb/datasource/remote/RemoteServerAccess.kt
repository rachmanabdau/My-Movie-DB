package com.example.mymoviddb.datasource.remote


import com.example.mymoviddb.model.*
import okio.BufferedSource


@Suppress("BlockingMethodInNonBlockingContext")
class RemoteServerAccess : RemoteServer {

    override suspend fun requestAccessToken(): Result<RequestTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getRequestTokenAsync().await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                val errorAdapter = moshi.adapter(Error401Model::class.java)
                val errorJson =
                    errorAdapter.fromJson(result.errorBody()?.source() as BufferedSource)

                Result.Error(Exception(errorJson?.statusMessage))
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel
    ):
            Result<LoginTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.loginAsync(
                username, password, requestToken.requestToken
            ).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                val errorAdapter = moshi.adapter(Error401Model::class.java)
                val errorJson =
                    errorAdapter.fromJson(result.errorBody()?.source() as BufferedSource)

                Result.Error(Exception(errorJson?.statusMessage))
            }
        } catch (e: Exception) {
            return Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsGuest(): Result<GuestSessionModel?> {
        return try {
            val result = NetworkAPI.retrofitService.loginAsGuestAsync().await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                val errorAdapter = moshi.adapter(Error401Model::class.java)
                val errorJson =
                    errorAdapter.fromJson(result.errorBody()?.source() as BufferedSource)

                Result.Error(Exception(errorJson?.statusMessage))
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }
}

