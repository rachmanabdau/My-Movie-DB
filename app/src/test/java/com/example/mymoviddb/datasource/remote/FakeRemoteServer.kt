package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.*

class FakeRemoteServer : RemoteServer {

    override suspend fun requestAccessToken(apiKey: String): Result<RequestTokenModel?> {
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
            Result.Success(
                RequestTokenModel(
                    success = true,
                    expiresAt = "2020-10-23 00:25:25 UTC",
                    requestToken = "1a137cd5ef00a95ef132708e461fb906"
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ): Result<LoginTokenModel?> {
        //TODO("Not yet implemented")
        return Result.Success(LoginTokenModel(true, "", ""))
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
            Result.Success(
                GuestSessionModel(
                    success = true,
                    expiresAt = "2020-10-23 00:25:25 UTC",
                    guestSessionId = "1a137cd5ef00a95ef132708e461fb906"
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
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
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
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
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
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
        return if (apiKey.equals(BuildConfig.V3_AUTH)) {
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