package com.example.mymoviddb.category

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.movie.SearchMovieResult
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import com.example.mymoviddb.core.model.category.tv.SearchTvResult

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PopularMovie?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<NowPlayingMovie?>

    suspend fun searchMovies(title: String, page: Int, apiKey: String): Result<SearchMovieResult?>

    suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteMovie?>

    suspend fun getWatchlistMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewMovie?>

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PopularTvShow?>

    suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<OnAirTvShow?>

    suspend fun searchTvShowList(title: String, page: Int, apiKey: String): Result<SearchTvResult?>

    suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteTvShow?>

    suspend fun getWatchlistTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<PreviewTvShow?>
}