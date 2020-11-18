package com.example.mymoviddb.account

import android.app.Application
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.mymoviddb.account.paging.AccountShowDataSourceHelper
import com.example.mymoviddb.account.paging.AccountShowDatasource

class AccountShowViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    app: Application,
    accountShowAccess: IAccountShowAccess,
) : AndroidViewModel(app) {

    val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(20)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    private val resultAccountList = savedStateHandle.getLiveData<Int>("showType").map {
        AccountShowDataSourceHelper(app, accountShowAccess, viewModelScope, it)
    }

    val accountShowList = resultAccountList.switchMap { it.getPageList(config) }

    val resultFavourite = resultAccountList.switchMap {
        it.getResult()
    }

    fun getFavouriteTVShows() {
        savedStateHandle.set("showType", AccountShowDatasource.FAVOURITE_TVSHOWS)
    }

    fun getFavouriteMovies() {
        savedStateHandle.set("showType", AccountShowDatasource.FAVOURITE_MOVIES)
    }

    fun retryLoadFavourite() {
        resultAccountList.value?.getRetry()
    }
}