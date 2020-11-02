package com.example.mymoviddb.category

import android.app.SearchManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.MovieDataSourceFactory
import com.example.mymoviddb.category.tv.ICategoryTVListAccess

class SearchViewModel @ViewModelInject constructor(
    private val categoryMovieListAccess: ICategoryMovieListAccess,
    private val categoryTVListIAccess: ICategoryTVListAccess,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /*private val _moviList =  MutableLiveData<PagedList<MovieModel.Result>>()
    val movieList: LiveData<PagedList<MovieModel.Result>> = _moviList

    private val _tvList =  MutableLiveData<PagedList<TVShowModel.Result>>()
    val tvList: LiveData<PagedList<TVShowModel.Result>> = _tvList

    private val _resultMovie =  MutableLiveData<Result<MovieModel?>>(Result.Loading)
    val resultMovie: LiveData<Result<MovieModel?>> = _resultMovie

    private val _resultTV =  MutableLiveData<Result<TVShowModel?>>()
    val resultTV: LiveData<Result<TVShowModel?>> = _resultTV

    private lateinit var movieDataSourceFactory: MovieDataSourceFactory
    private lateinit var tvDataSourceFactory: TVDataSourceFactory*/

    companion object {
        val KEY_TITLE = "title"
    }

    val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    val movieList = savedStateHandle.getLiveData<String>(SearchManager.QUERY).switchMap {
        val dataSourceFactory = MovieDataSourceFactory(categoryMovieListAccess, viewModelScope, it)
        LivePagedListBuilder(dataSourceFactory, config).build()
    }/*

    val resultMovie: LiveData<Result<MovieModel?>> = Transformations.switchMap(
    dataSourceFactory.sourceLiveData,
    MovieDataSource::result
    )*/

    fun searchTitle(title: String) {
        if (savedStateHandle.get<String>(SearchManager.QUERY) == title && title.isBlank()) return

        savedStateHandle.set(SearchManager.QUERY, title)
    }/*

    fun retrySearchMovies() {
        dataSourceFactory.sourceLiveData.value?.retry()
    }*/


}