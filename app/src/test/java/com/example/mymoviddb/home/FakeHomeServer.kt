package com.example.mymoviddb.home

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

class FakeHomeServer : IHomeAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            Result.Success(
                MovieModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = listOf(
                        MovieModel.Result(
                            adult = false, backdropPath = "", genreIds = listOf(1, 2, 3),
                            id = 1, originalLanguage = "English", popularity = 2795.629,
                            voteCount = 157, video = false, posterPath = "", originalTitle = "2067",
                            title = "2067", voteAverage = 5.7, overview = "", releaseDate = ""
                        )
                    )
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            Result.Success(
                MovieModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = listOf(
                        MovieModel.Result(
                            adult = false, backdropPath = "", genreIds = listOf(1, 2, 3),
                            id = 1, originalLanguage = "English", popularity = 2795.629,
                            voteCount = 157, video = false, posterPath = "", originalTitle = "2067",
                            title = "2067", voteAverage = 5.7, overview = "", releaseDate = ""
                        )
                    )
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            Result.Success(
                TVShowModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = listOf(
                        TVShowModel.Result(
                            name = "Pretty Little Liars", originalName = "Pretty Little Liars",
                            originalLanguage = "en", originCountry = listOf("US"),
                            backdropPath = "", genreIds = listOf(1, 2, 3),
                            id = 1, popularity = 2795.629, firstAirDate = "2015-05-27",
                            voteCount = 157, posterPath = "", voteAverage = 5.7, overview = "",
                        )
                    )
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            Result.Success(
                TVShowModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = listOf(
                        TVShowModel.Result(
                            name = "Pretty Little Liars", originalName = "Pretty Little Liars",
                            originalLanguage = "en", originCountry = listOf("US"),
                            backdropPath = "", genreIds = listOf(1, 2, 3),
                            id = 1, popularity = 2795.629, firstAirDate = "2015-05-27",
                            voteCount = 157, posterPath = "", voteAverage = 5.7, overview = "",
                        )
                    )
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }
}