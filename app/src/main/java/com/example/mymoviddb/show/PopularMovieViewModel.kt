package com.example.mymoviddb.show

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.datasource.PopularMovieDataSource
import com.example.mymoviddb.datasource.remote.PopularMovieDataSourceFactory
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.cancel

class PopularMovieViewModel(private val dataSourceFactoryPopular: PopularMovieDataSourceFactory) :
    ViewModel() {

    val movieList: LiveData<PagedList<MovieModel.Result>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()

        movieList = LivePagedListBuilder(dataSourceFactoryPopular, config).build()
    }

    val result: LiveData<Result<MovieModel?>> = Transformations.switchMap(
        dataSourceFactoryPopular.sourceLiveData,
        PopularMovieDataSource::result
    )

    fun retry() {
        dataSourceFactoryPopular.sourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    class Factory(
        private val dataSourceFactoryPopular: PopularMovieDataSourceFactory
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PopularMovieViewModel::class.java)) {
                return PopularMovieViewModel(dataSourceFactoryPopular) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}