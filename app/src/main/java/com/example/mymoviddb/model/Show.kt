package com.example.mymoviddb.model

import android.os.Parcelable

abstract class ShowResponse {
    abstract val page: Int
    abstract val results: List<ShowResult>
    abstract val totalPages: Int
    abstract val totalResults: Int
}

abstract class ShowResult : Parcelable {
    abstract val backdropPath: String?
    abstract val genreIds: List<Int>
    abstract val id: Long
    abstract val originalLanguage: String
    abstract val originalTitle: String
    abstract val overview: String
    abstract val popularity: Double
    abstract val posterPath: String?
    abstract val title: String
    abstract val voteAverage: Double
    abstract val voteCount: Int

    override fun equals(other: Any?): Boolean {
        return this == (other as ShowResult)
    }

    override fun hashCode(): Int {
        var result = backdropPath?.hashCode() ?: 0
        result = 31 * result + genreIds.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount
        return result
    }
}