package com.example.mymoviddb.account

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result

interface IAccountShowAccess {

    suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "tv",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteAndWatchListShow?>

    suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "movies",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteAndWatchListShow?>

    suspend fun getWatchlistTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "tv",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteAndWatchListShow?>

    suspend fun getWatchlistMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "movies",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteAndWatchListShow?>
}