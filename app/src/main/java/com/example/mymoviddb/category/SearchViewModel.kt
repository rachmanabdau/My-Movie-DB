package com.example.mymoviddb.category

import android.app.SearchManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.movie.MovieDataSourceFactory
import com.example.mymoviddb.category.tv.ICategoryTVListAccess

class SearchViewModel @ViewModelInject constructor(
    private val categoryMovieListAccess: ICategoryMovieListAccess,
    private val categoryTVListIAccess: ICategoryTVListAccess,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    var moviedataSourceFactory = MovieDataSourceFactory(categoryMovieListAccess, viewModelScope, "")

    val movieList = savedStateHandle.getLiveData<String>(SearchManager.QUERY).switchMap {
        moviedataSourceFactory = MovieDataSourceFactory(categoryMovieListAccess, viewModelScope, it)
        LivePagedListBuilder(moviedataSourceFactory, config).build()
    }

    val resultMovie = Transformations.switchMap(
        moviedataSourceFactory.sourceLiveData,
        MovieDataSource::result
    )

    fun searchTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
    }

    fun retrySearchMovies() {
        moviedataSourceFactory.sourceLiveData.value?.retry()
    }


}