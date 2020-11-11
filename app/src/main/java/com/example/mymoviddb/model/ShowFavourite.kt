package com.example.mymoviddb.model


import com.squareup.moshi.Json

data class ShowFavourite(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
) {
    data class Result(

        // parameter for favourite movies
        @Json(name = "adult")
        val isMovieAdult: Boolean?,
        @Json(name = "backdrop_path")
        val backdropPath: String?,
        // parameter for favourite tv shows
        @Json(name = "first_air_date")
        val tvFirstAirDate: String?,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "id")
        val id: Int,
        // parameter for favourite tv shows
        @Json(name = "name")
        val tvName: String?,
        // parameter for favourite tv shows
        @Json(name = "origin_country")
        val tvOriginCountry: List<String>?,
        @Json(name = "original_language")
        val originalLanguage: String,
        // parameter for favourite tv shows
        @Json(name = "original_name")
        val tvOriginalName: String?,
        // parameter for favourite movies
        @Json(name = "original_title")
        val movieOriginalTitle: String?,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "popularity")
        val popularity: Double,
        @Json(name = "poster_path")
        val posterPath: String?,
        // parameter for favourite movies
        @Json(name = "release_date")
        val moviReleaseDate: String?,
        // parameter for favourite movies
        @Json(name = "title")
        val movieTitle: String?,
        // parameter for favourite movies
        @Json(name = "video")
        val movieVideo: Boolean?,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int
    )
}