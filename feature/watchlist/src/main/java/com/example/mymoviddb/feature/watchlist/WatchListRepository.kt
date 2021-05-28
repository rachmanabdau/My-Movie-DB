package com.example.mymoviddb.feature.watchlist

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymoviddb.core.CategoryMediator
import com.example.mymoviddb.core.DatasourceDependency
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.datasource.local.TmdbDatabase
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow
import com.example.mymoviddb.core.utils.preference.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class WatchListRepository @Inject constructor(
    private val database: TmdbDatabase,
    private val networkService: NetworkService,
    private val userPreference: Preference
) : IWatchListAccess {

    override suspend fun getWatchListMovies(): Flow<PagingData<WatchListMovie.Result>> {
        val resourceDependency = DatasourceDependency(
            userPreference,
            networkService,
            ShowCategoryIndex.WATCHLIST_MOVIES
        )
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false),
            remoteMediator = CategoryMediator(resourceDependency, database)
        ) {
            database.watchListMovieDao().getAllWatchListMovie()
        }.flow
    }

    override suspend fun getWatchListTVShows(): Flow<PagingData<WatchListTvShow.Result>> {
        val resourceDependency = DatasourceDependency(
            userPreference,
            networkService,
            ShowCategoryIndex.WATCHLIST_TV_SHOWS,
        )
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false),
            remoteMediator = CategoryMediator(resourceDependency, database)
        ) {
            database.watchListTvDao().getAllWatchListTv()
        }.flow
    }
}