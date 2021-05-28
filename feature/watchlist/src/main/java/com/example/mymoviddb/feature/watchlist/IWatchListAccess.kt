package com.example.mymoviddb.feature.watchlist

import androidx.paging.PagingData
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow
import kotlinx.coroutines.flow.Flow

interface IWatchListAccess {

    suspend fun getWatchListMovies(
        apiKey: String = BuildConfig.V3_AUTH
    ): Flow<PagingData<WatchListMovie.Result>>

    suspend fun getWatchListTVShows(
        apiKey: String = BuildConfig.V3_AUTH
    ): Flow<PagingData<WatchListTvShow.Result>>
}