package com.example.mymoviddb.feature.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.model.ShowResult
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

    private fun getMovieData(categoryId: ShowCategoryIndex) {
        viewModelScope.launch {
            if (categoryId == ShowCategoryIndex.POPULAR_MOVIES) {
                categoryShowListAccess.getPopularMovieList().cachedIn(this).collectLatest { data ->
                    _showPageData.value = data.map { it }
                }
            } else if (categoryId == ShowCategoryIndex.NOW_PLAYING_MOVIES) {
                categoryShowListAccess.getNowPlayingMovieList().cachedIn(this)
                    .collectLatest { data ->
                        _showPageData.value = data.map { it }
                    }
            }
        }
    }

    private fun getTVData(categoryId: ShowCategoryIndex) {
        viewModelScope.launch {
            if (categoryId == ShowCategoryIndex.POPULAR_TV_SHOWS) {
                categoryShowListAccess.getPopularTvShowList().cachedIn(this).collectLatest { data ->
                    _showPageData.value = data.map { it }
                }
            } else if (categoryId == ShowCategoryIndex.ON_AIR_TV_SHOWS) {
                categoryShowListAccess.getOnAirTvShowList().cachedIn(this).collectLatest { data ->
                    _showPageData.value = data.map { it }
                }
            }
        }
    }
}