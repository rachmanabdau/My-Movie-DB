package com.example.mymoviddb.category.search

import android.app.SearchManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
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

    private val resultListingMovie = savedStateHandle.getLiveData<String>(SearchManager.QUERY).map {
        MovieSearchHelper(categoryMovieListAccess, viewModelScope, it)
    }

    val movieList = resultListingMovie.switchMap { it.getPageList(config) }

    val resultMovie = resultListingMovie.switchMap {
        it.getResult()
    }

    fun searchMovieTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
    }

    fun retrySearchMovies() {
        resultListingMovie.value?.getRetry()
    }

    private val resultListingTV = savedStateHandle.getLiveData<String>(SearchManager.QUERY).map {
        TVSearchHelper(categoryTVListIAccess, viewModelScope, it)
    }

    val tvList = resultListingTV.switchMap { it.getPageList(config) }

    val resultTV = resultListingTV.switchMap { it.getResult() }

    fun searchTvTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
        Timber.d("in search title $title and query ${savedStateHandle.get<String>(SearchManager.QUERY)}")
    }

    fun retrySearchTV() {
        resultListingTV.value?.getRetry()
    }

}