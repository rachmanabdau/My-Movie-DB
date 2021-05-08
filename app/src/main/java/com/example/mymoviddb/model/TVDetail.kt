package com.example.mymoviddb.model


import com.squareup.moshi.Json

data class TVDetail(
    @Json(name = "backdrop_path")
    override val backdropPath: String?,
    @Json(name = "created_by")
    val createdBy: List<CreatedBy>,
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int>,
    @Json(name = "first_air_date")
    val firstAirDate: String,
    @Json(name = "genres")
    override val genres: List<Genre>,
    @Json(name = "homepage")
    override val homepage: String?,
    @Json(name = "id")
    override val id: Long,
    @Json(name = "in_production")
    val inProduction: Boolean,
    @Json(name = "languages")
    val languages: List<String>,
    @Json(name = "last_air_date")
    val lastAirDate: String,
    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,
    @Json(name = "name")
    override val title: String,
    @Json(name = "networks")
    val networks: List<Network>,
    @Json(name = "next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int,
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_name")
    override val originalTitle: String,
    @Json(name = "overview")
    override val overview: String,
    @Json(name = "popularity")
    override val popularity: Double,
    @Json(name = "poster_path")
    override val posterPath: String?,
    @Json(name = "production_companies")
    override val productionCompanies: List<ProductionCompany>,
    @Json(name = "seasons")
    val seasons: List<Season>,
    @Json(name = "status")
    override val status: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "vote_average")
    override val voteAverage: Double,
    @Json(name = "vote_count")
    override val voteCount: Int
) : ShowDetail() {
    data class CreatedBy(
        @Json(name = "credit_id")
        val creditId: String,
        @Json(name = "gender")
        val gender: Int,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String,
        @Json(name = "profile_path")
        val profilePath: String?
    )

    data class LastEpisodeToAir(
        @Json(name = "air_date")
        val airDate: String,
        @Json(name = "episode_number")
        val episodeNumber: Int,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "production_code")
        val productionCode: String,
        @Json(name = "season_number")
        val seasonNumber: Int,
        @Json(name = "show_id")
        val showId: Int?,
        @Json(name = "still_path")
        val stillPath: String?,
        @Json(name = "vote_average")
        val voteAverage: Double?,
        @Json(name = "vote_count")
        val voteCount: Int
    )

    data class Network(
        @Json(name = "id")
        val id: Int,
        @Json(name = "logo_path")
        val logoPath: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "origin_country")
        val originCountry: String
    )

    data class NextEpisodeToAir(
        @Json(name = "air_date")
        val airDate: String,
        @Json(name = "episode_number")
        val episodeNumber: Int,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String?,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "production_code")
        val productionCode: String,
        @Json(name = "season_number")
        val seasonNumber: Int,
        @Json(name = "show_id")
        val showId: Int?,
        @Json(name = "still_path")
        val stillPath: String?,
        @Json(name = "vote_average")
        val voteAverage: Int,
        @Json(name = "vote_count")
        val voteCount: Int
    )

    data class Season(
        @Json(name = "air_date")
        val airDate: String?,
        @Json(name = "episode_count")
        val episodeCount: Int,
        @Json(name = "id")
        val id: Int,
        @Json(name = "name")
        val name: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "poster_path")
        val posterPath: String?,
        @Json(name = "season_number")
        val seasonNumber: Int
    )
}