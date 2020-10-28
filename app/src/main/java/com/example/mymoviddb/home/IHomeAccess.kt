package com.example.mymoviddb.home

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

interface IHomeAccess {

    suspend fun getPopularMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<MovieModel?>

    suspend fun getNowPlayingMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<MovieModel?>

    suspend fun getPopularTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<TVShowModel?>

    suspend fun getOnAirTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<TVShowModel?>
}