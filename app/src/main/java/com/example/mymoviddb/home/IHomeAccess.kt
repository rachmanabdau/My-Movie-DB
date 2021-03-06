package com.example.mymoviddb.home

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.PreviewTvShow
import com.example.mymoviddb.model.Result

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
    ): Result<PreviewTvShow?>

    suspend fun getOnAirTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewTvShow?>
}