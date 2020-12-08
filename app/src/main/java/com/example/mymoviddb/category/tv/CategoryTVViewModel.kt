package com.example.mymoviddb.category.tv

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryTVViewModel @ViewModelInject constructor(private val categoryTvAccess: ICategoryTVListAccess) :
    ViewModel() {

    private val _tvPageData = MutableLiveData<PagingData<TVShowModel.Result>>()
    val tvPageData: LiveData<PagingData<TVShowModel.Result>> = _tvPageData

    fun getTVData(categoryId: Int, title: String = "") {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                TVDataSourceV3(categoryTvAccess, categoryId, title)
            }.flow
                .cachedIn(this).collectLatest {
                    _tvPageData.value = it
                }
        }
    }
}