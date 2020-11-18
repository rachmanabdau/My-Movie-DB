package com.example.mymoviddb.favourite

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import kotlinx.coroutines.CoroutineScope

class FavouriteDatasourceFactory(
    private val app: Application,
    private val networkService: IShowFavouriteAccess,
    private val scope: CoroutineScope,
    private val showType: Int
) :
    DataSource.Factory<Int, FavouriteAndWatchListShow.Result>() {

    val sourceLiveData = MutableLiveData<FavouriteDatasource>()

    override fun create(): DataSource<Int, FavouriteAndWatchListShow.Result> {
        val source = FavouriteDatasource(app, networkService, scope, showType)
        sourceLiveData.postValue(source)
        return source
    }
}