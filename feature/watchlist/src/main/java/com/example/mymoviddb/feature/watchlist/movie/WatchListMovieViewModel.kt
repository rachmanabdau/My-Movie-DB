package com.example.mymoviddb.feature.watchlist.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.feature.watchlist.WatchListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltViewModel
class WatchListMovieViewModel @Inject constructor(
    private val watchListRepository: WatchListRepository
) : ViewModel() {

    private val _watchListMovies = MutableLiveData<PagingData<WatchListMovie.Result>>()
    val watchListMovies: LiveData<PagingData<WatchListMovie.Result>> = _watchListMovies

    fun getWatchListMovieList() {
        viewModelScope.launch {
            watchListRepository.getWatchListMovies()
                .cachedIn(this).collectLatest {
                    _watchListMovies.value = it
                }
        }
    }
}