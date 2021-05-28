package com.example.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteMovieViewModel @Inject constructor(
    private val favouriteRepository: IFavouriteAccess
) : ViewModel() {

    private val _favouriteMovieList = MutableLiveData<PagingData<FavouriteMovie.Result>>()
    val favouriteMovieList: LiveData<PagingData<FavouriteMovie.Result>> = _favouriteMovieList

    fun getFavouriteMovieList(apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            favouriteRepository.getFavouriteMovies(apiKey)
                .cachedIn(this).collectLatest {
                    _favouriteMovieList.value = it
                }
        }
    }
}