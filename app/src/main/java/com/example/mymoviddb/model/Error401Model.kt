package com.example.mymoviddb.model


import com.squareup.moshi.Json

data class Error401Model(
    @Json(name = "status_code")
    val statusCode: Int?,
    @Json(name = "status_message")
    val statusMessage: String?,
    @Json(name = "success")
    val success: Boolean
)