package com.example.mymoviddb.category.search

import androidx.paging.PagingData
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.ShowResult
import kotlinx.coroutines.flow.Flow

interface ISearchAccess {

    suspend fun searchMovies(
        title: String,
        apiKey: String = BuildConfig.BASE_URL
    ): Flow<PagingData<ShowResult>>

    suspend fun searchTvShowList(
        title: String,
        apiKey: String = BuildConfig.BASE_URL
    ): Flow<PagingData<ShowResult>>
}