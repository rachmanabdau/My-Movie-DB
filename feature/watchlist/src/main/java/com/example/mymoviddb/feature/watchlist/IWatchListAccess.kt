package com.example.mymoviddb.feature.watchlist

import androidx.paging.PagingData
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow
import kotlinx.coroutines.flow.Flow

interface IWatchListAccess {

    suspend fun getWatchListMovies(): Flow<PagingData<WatchListMovie.Result>>

    suspend fun getWatchListTVShows(): Flow<PagingData<WatchListTvShow.Result>>
}