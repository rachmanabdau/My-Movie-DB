package com.example.mymoviddb.feature.watchlist.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow
import com.example.mymoviddb.feature.watchlist.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class WatchListTvViewModel @Inject constructor(
    private val watchListRepository: WatchListRepository
) : ViewModel() {

    private val _watchListMovies = MutableLiveData<PagingData<WatchListTvShow.Result>>()
    val watchListMovies: LiveData<PagingData<WatchListTvShow.Result>> = _watchListMovies

    fun getWatchListTvList() {
        viewModelScope.launch {
            watchListRepository.getWatchListTVShows()
                .cachedIn(this).collectLatest {
                    _watchListMovies.value = it
                }
        }
    }
}