package com.example.mymoviddb.account.paging

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.CoroutineScope

class AccountShowDataSourceHelper(
    app: Application,
    networkService: IAccountShowAccess,
    scope: CoroutineScope,
    showType: Int
) {
    val accountShowDataSourceFactory = AccountShowDatasourceFactory(
        app,
        networkService,
        scope,
        showType
    )

    fun getPageList(config: PagedList.Config): LiveData<PagedList<FavouriteAndWatchListShow.Result>> {
        return LivePagedListBuilder(accountShowDataSourceFactory, config).build()
    }

    fun getResult(): LiveData<Result<FavouriteAndWatchListShow?>> {
        return Transformations.switchMap(
            accountShowDataSourceFactory.sourceLiveData,
            AccountShowDatasource::result
        )
    }

    fun getRetry() {
        accountShowDataSourceFactory.sourceLiveData.value?.retry()
    }
}