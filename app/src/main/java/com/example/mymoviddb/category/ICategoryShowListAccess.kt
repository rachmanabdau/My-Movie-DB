package com.example.mymoviddb.category

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.PreviewTvShow
import com.example.mymoviddb.model.Result

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun searchMovies(title: String, page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewMovie?>

    suspend fun getWatchlistMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewMovie?>

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?>

    suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?>

    suspend fun searchTvShowList(title: String, page: Int, apiKey: String): Result<PreviewTvShow?>

    suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewTvShow?>

    suspend fun getWatchlistTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewTvShow?>
}