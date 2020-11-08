package com.example.mymoviddb.main

import com.example.mymoviddb.model.ResponsedBackend
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail

interface IMainAccess {

    suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?>

    suspend fun logout(sessionId: Map<String, String>, apiKey: String): Result<ResponsedBackend?>
}