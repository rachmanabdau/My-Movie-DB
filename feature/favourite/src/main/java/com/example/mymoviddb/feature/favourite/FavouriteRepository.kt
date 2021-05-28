package com.example.mymoviddb.feature.favourite

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymoviddb.core.CategoryMediator
import com.example.mymoviddb.core.DatasourceDependency
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.datasource.local.TmdbDatabase
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import com.example.mymoviddb.core.utils.preference.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class FavouriteRepository @Inject constructor(
    private val database: TmdbDatabase,
    private val networkService: NetworkService,
    private val userPreference: Preference
) : IFavouriteAccess {

    override suspend fun getFavouriteMovies(): Flow<PagingData<FavouriteMovie.Result>> {
        val resourceDependency = DatasourceDependency(
            userPreference,
            networkService,
            ShowCategoryIndex.FAVOURITE_MOVIES
        )
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false),
            remoteMediator = CategoryMediator(resourceDependency, database)
        ) {
            database.favouriteMovieDao().getAllFavouriteMovie()
        }.flow
    }

    override suspend fun getFavouriteTVShows(): Flow<PagingData<FavouriteTvShow.Result>> {
        val resourceDependency = DatasourceDependency(
            userPreference,
            networkService,
            ShowCategoryIndex.FAVOURITE_TV_SHOWS,
        )
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false),
            remoteMediator = CategoryMediator(resourceDependency, database)
        ) {
            database.favouriteTvDao().getAllFavouriteTv()
        }.flow
    }
}