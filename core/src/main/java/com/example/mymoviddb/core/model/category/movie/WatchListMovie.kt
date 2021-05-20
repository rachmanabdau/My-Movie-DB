package com.example.mymoviddb.core.model.category.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mymoviddb.core.datasource.local.converter.GenreIdsConverter
import com.example.mymoviddb.core.model.ShowResponse
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class WatchListMovie(
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
    @Entity(tableName = "watch_list_movie")
    @TypeConverters(GenreIdsConverter::class)
    data class Result(
        @Json(name = "adult")
        override val adult: Boolean,
        @Json(name = "backdrop_path")
        @ColumnInfo(name = "backdrop_path")
        override val backdropPath: String?,
        @Json(name = "genre_ids")
        override val genreIds: List<Int>,
        @PrimaryKey
        @Json(name = "id")
        override val id: Long,
        @Json(name = "original_language")
        @ColumnInfo(name = "original_language")
        override val originalLanguage: String,
        @Json(name = "original_title")
        @ColumnInfo(name = "original_title")
        override val originalTitle: String,
        @Json(name = "overview")
        override val overview: String,
        @Json(name = "popularity")
        @ColumnInfo(typeAffinity = ColumnInfo.REAL)
        override val popularity: Double,
        @Json(name = "poster_path")
        @ColumnInfo(name = "poster_path")
        override val posterPath: String?,
        @Json(name = "release_date")
        @ColumnInfo(name = "release_date")
        override val releaseDate: String?,
        @Json(name = "title")
        override val title: String,
        @Json(name = "video")
        override val video: Boolean,
        @Json(name = "vote_average")
        @ColumnInfo(name = "vote_average", typeAffinity = ColumnInfo.REAL)
        override val voteAverage: Double,
        @Json(name = "vote_count")
        @ColumnInfo(name = "vote_count")
        override val voteCount: Int
    ) : MovieField()
}
