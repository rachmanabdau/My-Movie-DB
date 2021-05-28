package com.example.mymoviddb.feature.favourite

import androidx.paging.PagingData
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import kotlinx.coroutines.flow.Flow

interface IFavouriteAccess {

    suspend fun getFavouriteMovies(): Flow<PagingData<FavouriteMovie.Result>>

    suspend fun getFavouriteTVShows(): Flow<PagingData<FavouriteTvShow.Result>>
}