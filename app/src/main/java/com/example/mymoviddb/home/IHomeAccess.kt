package com.example.mymoviddb.home

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

interface IHomeAccess {

    suspend fun getPopularMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewMovie?>

    suspend fun getNowPlayingMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewMovie?>

    suspend fun getPopularTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<TVShowModel?>

    suspend fun getOnAirTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<TVShowModel?>
}