package com.example.mymoviddb.category.tv

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.cancel

class CategoryTVViewModel(private val dataSourceFactory: TVDataSourceFactory) :
    ViewModel() {

    val tvList: LiveData<PagedList<TVShowModel.Result>>

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

    class Factory(
        private val dataSourceFactory: TVDataSourceFactory
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryTVViewModel::class.java)) {
                return CategoryTVViewModel(dataSourceFactory) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}