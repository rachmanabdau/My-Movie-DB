package com.example.mymoviddb.category.movie

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.cancel

class CategoryMovieListViewModel(private val dataSourceFactory: MovieDataSourceFactory) :
    ViewModel() {

    val movieList: LiveData<PagedList<MovieModel.Result>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()

        movieList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    val result: LiveData<Result<MovieModel?>> = Transformations.switchMap(
        dataSourceFactory.sourceLiveData,
        MovieDataSource::result
    )

    fun retry() {
        dataSourceFactory.sourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    class Factory(
        private val dataSourceFactory: MovieDataSourceFactory
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryMovieListViewModel::class.java)) {
                return CategoryMovieListViewModel(dataSourceFactory) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}