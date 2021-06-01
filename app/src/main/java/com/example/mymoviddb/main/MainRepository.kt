package com.example.mymoviddb.main

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class MainRepository @Inject constructor(private val access: NetworkService) : IMainAccess {

    override suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getAccountDetailAsync(sessionId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
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
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }
}