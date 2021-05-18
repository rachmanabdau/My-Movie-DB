package com.example.mymoviddb.core.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarkMediaAs(
    @Json(name = "media_id")
    val mediaId: Long,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "favorite")
    val favorite: Boolean?,
    @Json(name = "watchlist")
    val watchList: Boolean?
)