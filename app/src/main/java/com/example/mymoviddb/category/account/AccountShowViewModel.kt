package com.example.mymoviddb.category.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymoviddb.category.ICategoryShowListAccess
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.category.ShowDataSource
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountShowViewModel @Inject constructor(
    private val userPreference: UserPreference,
    private val accountShowAccess: ICategoryShowListAccess,
) : ViewModel() {

    private val _accountShowList = MutableLiveData<PagingData<ShowResult>>()
    val accountShowList: LiveData<PagingData<ShowResult>> = _accountShowList

    fun getShowList(categoryId: ShowCategoryIndex) {
        viewModelScope.launch {
            Pager(
                // Configure how data is loaded by passing additional properties to
                // PagingConfig, such as prefetchDistance.
                PagingConfig(pageSize = 20, prefetchDistance = 5)
            ) {
                ShowDataSource(userPreference, accountShowAccess, categoryId, "")
            }.flow
                .cachedIn(this).collectLatest {
                    _accountShowList.value = it
                }
        }
    }
}