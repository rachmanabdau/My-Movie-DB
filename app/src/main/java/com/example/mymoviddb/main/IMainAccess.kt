package com.example.mymoviddb.main

import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail

interface IMainAccess {

    suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?>
}