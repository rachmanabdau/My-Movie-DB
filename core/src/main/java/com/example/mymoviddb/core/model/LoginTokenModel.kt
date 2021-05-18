package com.example.mymoviddb.core.model

import com.squareup.moshi.Json

data class LoginTokenModel(
    val success: Boolean,
    @Json(name = "expires_at")
    val expiresAt: String,
    @Json(name = "request_token")
    val requestToken: String
)