package com.example.mymoviddb.home


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarkAsFavorite(
    @Json(name = "favorite")
    val favorite: Boolean,
    @Json(name = "media_id")
    val mediaId: Int,
    @Json(name = "media_type")
    val mediaType: String
)