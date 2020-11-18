package com.example.mymoviddb.account.paging

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import kotlinx.coroutines.CoroutineScope

class AccountShowDatasourceFactory(
    private val app: Application,
    private val networkService: IAccountShowAccess,
    private val scope: CoroutineScope,
    private val showType: Int
) :
    DataSource.Factory<Int, FavouriteAndWatchListShow.Result>() {

    val sourceLiveData = MutableLiveData<AccountShowDatasource>()

    override fun create(): DataSource<Int, FavouriteAndWatchListShow.Result> {
        val source = AccountShowDatasource(app, networkService, scope, showType)
        sourceLiveData.postValue(source)
        return source
    }
}