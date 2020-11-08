package com.example.mymoviddb.main

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.ResponsedBackend
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class MainAccess @Inject constructor(private val access: NetworkService) : IMainAccess {

    override suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getAccountDetailAsync(sessionId, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun logout(
        sessionId: Map<String, String>,
        apiKey: String
    ): Result<ResponsedBackend?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.logoutAsync(sessionId, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}