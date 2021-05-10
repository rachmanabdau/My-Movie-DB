package com.example.mymoviddb.sharedData

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

class FakeHomeServer : IHomeAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PreviewMovie?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            val jsonConverter = moshi.adapter(PreviewMovie::class.java)
            val responseSuccess =
                jsonConverter.fromJson(FakeRemoteServer.popularMovieResponse) as PreviewMovie
            Result.Success(
                PreviewMovie(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = responseSuccess.results
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<PreviewMovie?> {
        return if (apiKey == BuildConfig.V3_AUTH) {
            val jsonConverter = moshi.adapter(PreviewMovie::class.java)
            val responseSuccess =
                jsonConverter.fromJson(FakeRemoteServer.nowPlayingMoviesResponse) as PreviewMovie
            Result.Success(
                PreviewMovie(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = responseSuccess.results
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
            val jsonConverter = moshi.adapter(TVShowModel::class.java)
            val responseSuccess =
                jsonConverter.fromJson(FakeRemoteServer.popularTvResponse) as TVShowModel
            Result.Success(
                TVShowModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = responseSuccess.results
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
            val jsonConverter = moshi.adapter(TVShowModel::class.java)
            val responseSuccess =
                jsonConverter.fromJson(FakeRemoteServer.onAirTVResponse) as TVShowModel
            Result.Success(
                TVShowModel(
                    page = 1, totalPages = 100, totalResults = 1000,
                    results = responseSuccess.results
                )
            )
        } else {
            Result.Error(
                Exception("token is invalid")
            )
        }
    }
}