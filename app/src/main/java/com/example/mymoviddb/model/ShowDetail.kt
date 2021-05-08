package com.example.mymoviddb.model

import com.squareup.moshi.Json

abstract class ShowDetail {

    abstract val backdropPath: String?
    abstract val genres: List<Genre>
    abstract val homepage: String?
    abstract val id: Long
    abstract val originalTitle: String
    abstract val overview: String
    abstract val popularity: Double
    abstract val posterPath: String?
    abstract val productionCompanies: List<ProductionCompany>
    abstract val status: String
    abstract val title: String
    abstract val voteAverage: Double
    abstract val voteCount: Int

    data class Genre(
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String
    )

    data class ProductionCompany(
        @Json(name = "id")
        val id: Int,
        @Json(name = "logo_path")
        val logoPath: String?,
        @Json(name = "name")
        val name: String,
        @Json(name = "origin_country")
        val originCountry: String
    )
}