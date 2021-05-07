package com.example.mymoviddb.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.account.paging.AccountShowDatasource
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountShowViewModel @Inject constructor(
    private val app: Application,
    private val accountShowAccess: IAccountShowAccess,
) : AndroidViewModel(app) {

    private val _accountShowList = MutableLiveData<PagingData<FavouriteAndWatchListShow.Result>>()
    val accountShowList: LiveData<PagingData<FavouriteAndWatchListShow.Result>> = _accountShowList

    fun getShowList(categoryId: Int) {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                AccountShowDatasource(app, accountShowAccess, categoryId)
            }.flow
                .cachedIn(this).collectLatest {
                    _accountShowList.value = it
                }
        }
    }
}