package com.example.mymoviddb.favourite

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.FavouriteShow
import com.example.mymoviddb.model.Result

interface IShowFavouriteAccess {

    suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "tv",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteShow?>

    suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String = "movies",
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<FavouriteShow?>
}