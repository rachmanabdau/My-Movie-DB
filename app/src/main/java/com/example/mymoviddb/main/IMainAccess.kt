package com.example.mymoviddb.main

import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail

interface IMainAccess {

    suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?>

    suspend fun logout(sessionId: Map<String, String>, apiKey: String): Result<ResponsedBackend?>
}