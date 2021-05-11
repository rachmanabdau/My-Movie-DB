package com.example.mymoviddb.category.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.category.ICategoryShowListAccess
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.category.ShowDataSource
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userPreference: UserPreference,
    private val categoryMovieListAccess: ICategoryShowListAccess
) : ViewModel() {

    private val _showPageData = MutableLiveData<PagingData<ShowResult>>()
    val showPageData: LiveData<PagingData<ShowResult>> = _showPageData

    fun searchShow(categoryId: ShowCategoryIndex, title: String) {
        if (categoryId.index < 21) {
            searchMovieData(categoryId, title)
        } else {
            searchTVData(categoryId, title)
        }
    }

    private fun searchMovieData(categoryId: ShowCategoryIndex, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                ShowDataSource(userPreference, categoryMovieListAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }

    private fun searchTVData(categoryId: ShowCategoryIndex, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                ShowDataSource(userPreference, categoryMovieListAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }

}