package com.example.mymoviddb.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.model.ShowResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryShowListViewModel @Inject constructor(
    private val categoryShowListAccess: ICategoryShowListAccess
) :
    ViewModel() {

    private val _showPageData = MutableLiveData<PagingData<ShowResult>>()
    val showPageData: LiveData<PagingData<ShowResult>> = _showPageData

    fun getShowCategory(categoryId: ShowCategoryIndex) {
        if (categoryId.index < 21) {
            getMovieData(categoryId)
        } else {
            getTVData(categoryId)
        }
    }

    private fun getMovieData(categoryId: ShowCategoryIndex, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                ShowDataSource(categoryShowListAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }

    private fun getTVData(categoryId: ShowCategoryIndex, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                ShowDataSource(categoryShowListAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }
}