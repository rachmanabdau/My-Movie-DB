package com.example.mymoviddb.model


import com.squareup.moshi.Json

data class UserDetail(
    @Json(name = "avatar")
    val avatar: Avatar,
    @Json(name = "id")
    val id: Int,
    @Json(name = "include_adult")
    val includeAdult: Boolean,
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String
) {
    data class Avatar(
        @Json(name = "gravatar")
        val gravatar: Gravatar,
        @Json(name = "tmdb")
        val tmdb: Tmdb?
    ) {
        data class Gravatar(
            @Json(name = "hash")
            val hash: String
        )

        data class Tmdb(
            @Json(name = "avatar_path")
            val avatarPath: String
        )
    }
}