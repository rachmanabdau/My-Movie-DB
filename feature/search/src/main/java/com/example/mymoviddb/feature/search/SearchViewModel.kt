package com.example.mymoviddb.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searcHRepository: ISearchAccess
) : ViewModel() {

    private val _showPageData = MutableLiveData<PagingData<ShowResult>>()
    val showPageData: LiveData<PagingData<ShowResult>> = _showPageData

    fun searchShow(
        categoryId: ShowCategoryIndex,
        title: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        if (categoryId.index < ShowCategoryIndex.SEARCH_TV_SHOWS.index) {
            searchMovieData(title, apiKey)
        } else {
            searchTVData(title, apiKey)
        }
    }

    private fun searchMovieData(title: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            searcHRepository.searchMovies(title, apiKey)
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }

    private fun searchTVData(title: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            searcHRepository.searchTvShowList(title, apiKey)
                .cachedIn(this).collectLatest {
                    _showPageData.value = it
                }
        }
    }

}