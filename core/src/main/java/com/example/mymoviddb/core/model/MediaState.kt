package com.example.mymoviddb.core.model


import com.squareup.moshi.Json

data class MediaState(
    @Json(name = "favorite")
    val favorite: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "rated")
    val rated: Boolean,
    @Json(name = "watchlist")
    val watchlist: Boolean
)