package com.example.mymoviddb.category.search

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
import com.example.mymoviddb.category.tv.TVDataSource
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.TVShowModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val categoryMovieListAccess: ICategoryMovieListAccess,
    private val categoryTVListIAccess: ICategoryTVListAccess,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _moviePageData = MutableLiveData<PagingData<MovieModel.Result>>()
    val moviePageData: LiveData<PagingData<MovieModel.Result>> = _moviePageData

    fun searchMovieData(categoryId: Int, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                MovieDataSource(categoryMovieListAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _moviePageData.value = it
                }
        }
    }

    private val _tvPageData = MutableLiveData<PagingData<TVShowModel.Result>>()
    val tvPageData: LiveData<PagingData<TVShowModel.Result>> = _tvPageData

    fun searchTVData(categoryId: Int, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                TVDataSource(categoryTVListIAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _tvPageData.value = it
                }
        }
    }

}