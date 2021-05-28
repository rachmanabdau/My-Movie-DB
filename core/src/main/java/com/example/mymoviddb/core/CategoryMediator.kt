package com.example.mymoviddb.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymoviddb.core.datasource.local.TmdbDatabase
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.ShowResponse
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.model.category.RemoteKey
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow

@OptIn(ExperimentalPagingApi::class)
class CategoryMediator<T : ShowResult>(
    private val mediaDependency: DatasourceDependency,
    private val database: TmdbDatabase
) : RemoteMediator<Int, T>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remotKeyDao().getRemoteKey()
                    }

                    if (remoteKey.nextKey == 0) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextKey
                }
            }

            val response = getShowData(mediaDependency.showType, pageNumber = loadKey ?: 1)

            if (response is Result.Success) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        clearDatabase(mediaDependency.showType)
                        database.remotKeyDao().clearRemoteKey()
                    }

                    // Update RemoteKey for this query.
                    database.remotKeyDao().insertOrReplace(
                        RemoteKey(response.data?.page?.plus(1) ?: 0)
                    )
                    insertAllToDatabase(mediaDependency.showType, response.data)
                }

                MediatorResult.Success(
                    endOfPaginationReached = response.data?.page ?: 0 == response.data?.totalPages
                            ?: 0
                )
            } else {
                val error = Exception("Something went wrong.")
                MediatorResult.Error(error)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getShowData(
        showType: ShowCategoryIndex,
        pageNumber: Int
    ): Result<ShowResponse?> {
        return when (showType) {
            ShowCategoryIndex.FAVOURITE_MOVIES ->
                mediaDependency.getFavouriteMovies(pageNumber)

            ShowCategoryIndex.WATCHLIST_MOVIES ->
                mediaDependency.getWatchListMovies(pageNumber)

            ShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                mediaDependency.getFavouriteTvShows(pageNumber)

            else ->
                mediaDependency.getWatchListTvShows(pageNumber)
        }
    }

    private suspend fun clearDatabase(showType: ShowCategoryIndex) {
        when (showType) {
            ShowCategoryIndex.FAVOURITE_MOVIES ->
                database.favouriteMovieDao().cleartAllFavouriteMovie()

            ShowCategoryIndex.WATCHLIST_MOVIES ->
                database.watchListMovieDao().cleartAllWatchListMovie()

            ShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                database.favouriteTvDao().clearAllFavouriteTv()

            else ->
                database.watchListTvDao().clearAllWatchListTv()
        }
    }

    private suspend fun insertAllToDatabase(showType: ShowCategoryIndex, result: ShowResponse?) {
        result?.let { showResult ->
            when (showType) {
                ShowCategoryIndex.FAVOURITE_MOVIES ->
                    database.favouriteMovieDao()
                        .insertAll(showResult.results.map { it as FavouriteMovie.Result })

                ShowCategoryIndex.WATCHLIST_MOVIES ->
                    database.watchListMovieDao()
                        .insertAll(showResult.results.map { it as WatchListMovie.Result })

                ShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                    database.favouriteTvDao()
                        .insertAll(showResult.results.map { it as FavouriteTvShow.Result })

                else ->
                    database.watchListTvDao()
                        .insertAll(showResult.results.map { it as WatchListTvShow.Result })
            }
        }
    }

}
