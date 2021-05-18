package com.example.mymoviddb.core.model


import com.squareup.moshi.Json

data class GuestSessionModel(
    @Json(name = "expires_at")
    val expiresAt: String,
    @Json(name = "guest_session_id")
    val guestSessionId: String,
    @Json(name = "success")
    val success: Boolean
)