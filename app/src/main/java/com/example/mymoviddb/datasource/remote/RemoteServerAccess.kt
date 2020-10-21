package com.example.mymoviddb.datasource.remote


import com.example.mymoviddb.model.*
import okio.BufferedSource
import retrofit2.Response


@Suppress("BlockingMethodInNonBlockingContext")
class RemoteServerAccess : RemoteServer {

    override suspend fun requestAccessToken(): Result<RequestTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getRequestTokenAsync().await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return return401Error(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ):
            Result<LoginTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.loginAsync(
                username, password, requestToken?.requestToken ?: ""
            ).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return return401Error(result)
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
                return return401Error(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }
}

fun return401Error(result: Response<*>): Result.Error {
    val errorAdapter = moshi.adapter(Error401Model::class.java)
    val errorJson =
        errorAdapter.fromJson(result.errorBody()?.source() as BufferedSource)

    return Result.Error(Exception(errorJson?.statusMessage))
}

