package com.example.mymoviddb.category.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymoviddb.core.DatasourceDependency
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.utils.preference.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepository @Inject constructor(
    private val networkService: NetworkService,
    private val userPreference: Preference
) : ISearchAccess {

    override suspend fun searchMovies(
        title: String,
        apiKey: String
    ): Flow<PagingData<ShowResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)
        ) {
            NewShowDataSource(
                DatasourceDependency(
                    userPreference,
                    networkService,
                    ShowCategoryIndex.SEARCH_MOVIES,
                    title
                )
            )
        }.flow
    }

    override suspend fun searchTvShowList(
        title: String,
        apiKey: String
    ): Flow<PagingData<ShowResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 4, enablePlaceholders = false)
        ) {
            NewShowDataSource(
                DatasourceDependency(
                    userPreference,
                    networkService,
                    ShowCategoryIndex.SEARCH_TV_SHOWS,
                    title
                )
            )
        }.flow
    }
}