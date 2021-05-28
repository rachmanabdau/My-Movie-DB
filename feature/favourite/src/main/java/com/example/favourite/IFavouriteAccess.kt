package com.example.favourite

import androidx.paging.PagingData
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import kotlinx.coroutines.flow.Flow

interface IFavouriteAccess {

    suspend fun getFavouriteMovies(
        apiKey: String = BuildConfig.V3_AUTH
    ): Flow<PagingData<FavouriteMovie.Result>>

    suspend fun getFavouriteTVShows(
        apiKey: String = BuildConfig.V3_AUTH
    ): Flow<PagingData<FavouriteTvShow.Result>>
}