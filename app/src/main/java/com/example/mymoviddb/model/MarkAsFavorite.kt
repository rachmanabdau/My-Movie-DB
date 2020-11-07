package com.example.mymoviddb.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarkAsFavorite(
    @Json(name = "favorite")
    val favorite: Boolean,
    @Json(name = "media_id")
    val mediaId: Long,
    @Json(name = "media_type")
    val mediaType: String
)