package com.example.mymoviddb.favourite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.model.ShowFavourite
import kotlinx.coroutines.CoroutineScope

class FavouriteDatasourceFactory(
    private val context: Context,
    private val networkService: IShowFavouriteAccess,
    private val scope: CoroutineScope,
    private val showType: Int
) :
    DataSource.Factory<Int, ShowFavourite.Result>() {

    val sourceLiveData = MutableLiveData<FavouriteDatasource>()

    override fun create(): DataSource<Int, ShowFavourite.Result> {
        val source = FavouriteDatasource(context, networkService, scope, showType)
        sourceLiveData.postValue(source)
        return source
    }
}