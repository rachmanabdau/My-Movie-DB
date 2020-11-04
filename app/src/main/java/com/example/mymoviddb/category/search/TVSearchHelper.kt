package com.example.mymoviddb.category.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.category.tv.TVDataSourceFactory
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.CoroutineScope


class TVSearchHelper(tvListAccess: ICategoryTVListAccess, scope: CoroutineScope, title: String) {
    val dataSourceFactory = TVDataSourceFactory(tvListAccess, scope, title)

    fun getPageList(config: PagedList.Config): LiveData<PagedList<TVShowModel.Result>> {
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun getResult(): LiveData<Result<TVShowModel?>> {
        return Transformations.switchMap(
            dataSourceFactory.sourceLiveData,
            TVDataSource::result
        )
    }

    fun getRetry() {
        dataSourceFactory.sourceLiveData.value?.retry()
    }
}