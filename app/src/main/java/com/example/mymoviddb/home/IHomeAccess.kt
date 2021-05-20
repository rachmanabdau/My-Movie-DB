package com.example.mymoviddb.home

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.PopularMovie

interface IHomeAccess {

    suspend fun getPopularMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PopularMovie?>

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