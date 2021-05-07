package com.example.mymoviddb.category.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryMovieListViewModel @Inject constructor(private val categoryMovieListAccess: ICategoryMovieListAccess) :
    ViewModel() {

    private val _moviePageData = MutableLiveData<PagingData<MovieModel.Result>>()
    val moviePageData: LiveData<PagingData<MovieModel.Result>> = _moviePageData

    fun getMovieData(categoryId: Int, title: String = "") {
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
}