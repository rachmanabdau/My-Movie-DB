package com.example.mymoviddb.feature.category

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.mymoviddb.core.DatasourceDependency
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.ShowDataSource
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CategoryShowListRepository @Inject constructor(
    private val userPreference: Preference,
    private val service: NetworkService
) :
    ICategoryShowListAccess {

    override suspend fun getPopularMovieList(): Flow<PagingData<PopularMovie.Result>> {
        wrapEspressoIdlingResource{
            val dataSourceDependency = DatasourceDependency(
                userPreference, service, ShowCategoryIndex.POPULAR_MOVIES
            )
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 4,
                    enablePlaceholders = false
                )
            ) {
                ShowDataSource(dataSourceDependency)
            }.flow.mapLatest { pagingData ->
                pagingData.map {
                    it as PopularMovie.Result
                }
            }
        }
    }

    override suspend fun getNowPlayingMovieList(): Flow<PagingData<NowPlayingMovie.Result>> {
        wrapEspressoIdlingResource{
            val dataSourceDependency = DatasourceDependency(
                userPreference, service, ShowCategoryIndex.NOW_PLAYING_MOVIES
            )
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 4,
                    enablePlaceholders = false
                )
            ) {
                ShowDataSource(dataSourceDependency)
            }.flow.mapLatest { pagingData ->
                pagingData.map {
                    it as NowPlayingMovie.Result
                }
            }
        }
    }

    override suspend fun getPopularTvShowList(): Flow<PagingData<PopularTvShow.Result>> {
        wrapEspressoIdlingResource{
            val dataSourceDependency = DatasourceDependency(
                userPreference, service, ShowCategoryIndex.POPULAR_TV_SHOWS
            )
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 4,
                    enablePlaceholders = false
                )
            ) {
                ShowDataSource(dataSourceDependency)
            }.flow.mapLatest { pagingData ->
                pagingData.map {
                    it as PopularTvShow.Result
                }
            }
        }
    }

    override suspend fun getOnAirTvShowList(): Flow<PagingData<OnAirTvShow.Result>> {
        wrapEspressoIdlingResource{
            val dataSourceDependency = DatasourceDependency(
                userPreference, service, ShowCategoryIndex.ON_AIR_TV_SHOWS
            )
            return Pager(
                config = PagingConfig(
                    pageSize = 20,
                    prefetchDistance = 4,
                    enablePlaceholders = false
                )
            ) {
                ShowDataSource(dataSourceDependency)
            }.flow.mapLatest { pagingData ->
                pagingData.map {
                    it as OnAirTvShow.Result
                }
            }
        }
    }

}