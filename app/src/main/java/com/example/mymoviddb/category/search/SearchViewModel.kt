package com.example.mymoviddb.category.search

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.MovieDataSourceV3
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
import com.example.mymoviddb.category.tv.TVDataSourceV3
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchViewModel @ViewModelInject constructor(
    private val categoryMovieListAccess: ICategoryMovieListAccess,
    private val categoryTVListIAccess: ICategoryTVListAccess,
    @Assisted private val savedStateHandle: SavedStateHandle
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
                MovieDataSourceV3(categoryMovieListAccess, categoryId, title)
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
                TVDataSourceV3(categoryTVListIAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _tvPageData.value = it
                }
        }
    }

}