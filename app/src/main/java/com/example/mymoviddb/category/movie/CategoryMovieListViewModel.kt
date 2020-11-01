package com.example.mymoviddb.category.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.cancel

class CategoryMovieListViewModel @ViewModelInject constructor(categoryMovieListAccess: ICategoryMovieListAccess) :
    ViewModel() {

    val movieList: LiveData<PagedList<MovieModel.Result>>
    val dataSourceFactory = MovieDataSourceFactory(categoryMovieListAccess, viewModelScope)

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
}