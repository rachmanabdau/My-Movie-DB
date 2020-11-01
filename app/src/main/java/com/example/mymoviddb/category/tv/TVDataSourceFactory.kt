package com.example.mymoviddb.category.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.CoroutineScope

class TVDataSourceFactory(
    private val networkService: ICategoryTVListAccess,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, TVShowModel.Result>() {

    val sourceLiveData = MutableLiveData<TVDataSource>()

    override fun create(): DataSource<Int, TVShowModel.Result> {
        val source = TVDataSource(networkService, scope)
        sourceLiveData.postValue(source)
        return source
    }

}