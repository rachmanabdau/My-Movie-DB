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
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.category.tv.TVDataSourceFactory
import timber.log.Timber

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

    fun searchMovieTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
    }

    fun retrySearchMovies() {
        moviedataSourceFactory.sourceLiveData.value?.retry()
    }

    var tvDataSourceFactory = TVDataSourceFactory(categoryTVListIAccess, viewModelScope, "")

    val tvList = savedStateHandle.getLiveData<String>(SearchManager.QUERY).switchMap {
        Timber.d("query in switch map ${it}")
        tvDataSourceFactory = TVDataSourceFactory(categoryTVListIAccess, viewModelScope, it)
        LivePagedListBuilder(tvDataSourceFactory, config).build()
    }

    val resultTV = Transformations.switchMap(
        tvDataSourceFactory.sourceLiveData,
        TVDataSource::result
    )

    fun searchTvTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
        Timber.d("in search title $title and query ${savedStateHandle.get<String>(SearchManager.QUERY)}")
    }

    fun retrySearchTV() {
        tvDataSourceFactory.sourceLiveData.value?.retry()
    }


}