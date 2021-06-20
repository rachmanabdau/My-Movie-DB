package com.example.mymoviddb.home

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow

interface IHomeAccess {

    suspend fun getPopularMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PopularMovie?>

    suspend fun getNowPlayingMovieList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<NowPlayingMovie?>

    suspend fun getPopularTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PopularTvShow?>

    suspend fun getOnAirTvShowList(
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<OnAirTvShow?>

    suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?>

    suspend fun logout(sessionId: Map<String, String>, apiKey: String): Result<ResponsedBackend?>
}