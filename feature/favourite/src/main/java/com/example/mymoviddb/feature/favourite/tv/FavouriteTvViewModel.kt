package com.example.mymoviddb.feature.favourite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import com.example.mymoviddb.feature.favourite.IFavouriteAccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteTvViewModel @Inject constructor(
    private val favouriteRepository: IFavouriteAccess
) : ViewModel() {

    private val _favouriteTvList = MutableLiveData<PagingData<FavouriteTvShow.Result>>()
    val favouriteTvList: LiveData<PagingData<FavouriteTvShow.Result>> = _favouriteTvList

    fun getFavouriteTvList(apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            favouriteRepository.getFavouriteTVShows(apiKey)
                .cachedIn(this).collectLatest {
                    _favouriteTvList.value = it
                }
        }
    }
}