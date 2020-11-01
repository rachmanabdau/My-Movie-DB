package com.example.mymoviddb.category.tv

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.cancel

class CategoryTVViewModel @ViewModelInject constructor(categoryTvAccess: ICategoryTVListAccess) :
    ViewModel() {

    val tvList: LiveData<PagedList<TVShowModel.Result>>
    val dataSourceFactory = TVDataSourceFactory(categoryTvAccess, viewModelScope)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()
        tvList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    val result: LiveData<Result<TVShowModel?>> = Transformations.switchMap(
        dataSourceFactory.sourceLiveData,
        TVDataSource::result
    )

    fun retry() {
        dataSourceFactory.sourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}