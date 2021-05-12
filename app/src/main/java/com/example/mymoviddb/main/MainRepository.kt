package com.example.mymoviddb.main

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.ResponsedBackend
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class MainRepository @Inject constructor(private val access: NetworkService) : IMainAccess {

    override suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?> {
        return Util.getDataFromServer {
            access.getAccountDetailAsync(sessionId, apiKey).await()
        }
    }

    override suspend fun logout(
        sessionId: Map<String, String>,
        apiKey: String
    ): Result<ResponsedBackend?> {
        wrapEspressoIdlingResource {
            return Util.getDataFromServer {
                access.logoutAsync(sessionId, apiKey).await()
            }
        }
    }
}