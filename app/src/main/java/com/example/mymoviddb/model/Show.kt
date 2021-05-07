package com.example.mymoviddb.model

abstract class ShowResponse {
    abstract val page: Int
    abstract val results: List<ShowResult>
    abstract val totalPages: Int
    abstract val totalResults: Int
}

abstract class ShowResult {
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
}