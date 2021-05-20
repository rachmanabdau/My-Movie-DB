package com.example.mymoviddb.category

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.PopularTvShow

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PopularMovie?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<NowPlayingMovie?>

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

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PopularTvShow?>

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