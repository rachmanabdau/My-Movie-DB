package com.example.mymoviddb.core.model.category.tv

import com.example.mymoviddb.core.model.ShowResponse
import com.example.mymoviddb.core.model.ShowResult
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class WatchListTvShow(
    @Json(name = "page")
    override val page: Int,
    @Json(name = "results")
    override val results: List<Result>,
    @Json(name = "total_pages")
    override val totalPages: Int,
    @Json(name = "total_results")
    override val totalResults: Int
) : ShowResponse() {

    @Parcelize
    data class Result(
        @Json(name = "backdrop_path")
        override val backdropPath: String?,
        @Json(name = "first_air_date")
        override val firstAirDate: String?,
        @Json(name = "genre_ids")
        override val genreIds: List<Int>,
        @Json(name = "id")
        override val id: Long,
        @Json(name = "origin_country")
        override val originCountry: List<String>,
        @Json(name = "original_language")
        override val originalLanguage: String,
        @Json(name = "original_name")
        override val originalTitle: String,
        @Json(name = "overview")
        override val overview: String,
        @Json(name = "popularity")
        override val popularity: Double,
        @Json(name = "poster_path")
        override val posterPath: String?,
        @Json(name = "name")
        override val title: String,
        @Json(name = "vote_average")
        override val voteAverage: Double,
        @Json(name = "vote_count")
        override val voteCount: Int
    ) : ShowResult(), TvField
}
