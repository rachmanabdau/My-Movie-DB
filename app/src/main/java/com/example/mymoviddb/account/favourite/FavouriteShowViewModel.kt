package com.example.mymoviddb.account.favourite

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.mymoviddb.account.FavouriteDataSourceHelper
import com.example.mymoviddb.account.FavouriteDatasource
import com.example.mymoviddb.account.IShowFavouriteAccess

class FavouriteShowViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    app: Application,
    favourtieshowAccess: IShowFavouriteAccess,
) : AndroidViewModel(app) {

    val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    private val resultLFavourite = savedStateHandle.getLiveData<Int>("showType").map {
        FavouriteDataSourceHelper(app, favourtieshowAccess, viewModelScope, it)
    }

    val favouriteList = resultLFavourite.switchMap { it.getPageList(config) }

    val resultFavourite = resultLFavourite.switchMap {
        it.getResult()
    }

    fun getFavouriteTVShows() {
        savedStateHandle.set("showType", FavouriteDatasource.FAVOURITE_TVSHOWS)
    }

    fun getFavouriteMovies() {
        savedStateHandle.set("showType", FavouriteDatasource.FAVOURITE_MOVIES)
    }

    fun retryLoadFavourite() {
        resultLFavourite.value?.getRetry()
    }
}