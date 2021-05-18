package com.example.mymoviddb.core.model


import com.squareup.moshi.Json

data class NewSessionModel(
    @Json(name = "session_id")
    val sessionId: String,
    @Json(name = "success")
    val success: Boolean
)