package com.example.mymoviddb.model


import com.squareup.moshi.Json

data class RequestTokenModel(
    @Json(name = "expires_at")
    val expiresAt: String,
    @Json(name = "request_token")
    val requestToken: String,
    @Json(name = "success")
    val success: Boolean
)