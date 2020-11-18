package com.example.mymoviddb.account

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.CoroutineScope

class AccountShowDataSourceHelper(
    app: Application,
    networkService: IAccountShowAccess,
    scope: CoroutineScope,
    showType: Int
) {
    val favouriteMovieDataSourceFactory = AccountShowDatasourceFactory(
        app,
        networkService,
        scope,
        showType
    )

    fun getPageList(config: PagedList.Config): LiveData<PagedList<FavouriteAndWatchListShow.Result>> {
        return LivePagedListBuilder(favouriteMovieDataSourceFactory, config).build()
    }

    fun getResult(): LiveData<Result<FavouriteAndWatchListShow?>> {
        return Transformations.switchMap(
            favouriteMovieDataSourceFactory.sourceLiveData,
            AccountShowDatasource::result
        )
    }

    fun getRetry() {
        favouriteMovieDataSourceFactory.sourceLiveData.value?.retry()
    }
}