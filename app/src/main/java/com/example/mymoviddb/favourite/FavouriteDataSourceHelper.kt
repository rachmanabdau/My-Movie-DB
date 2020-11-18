package com.example.mymoviddb.favourite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.CoroutineScope

class FavouriteDataSourceHelper(
    app: Application,
    networkService: IShowFavouriteAccess,
    scope: CoroutineScope,
    showType: Int
) {
    val favouriteMovieDataSourceFactory = FavouriteDatasourceFactory(
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
            FavouriteDatasource::result
        )
    }

    fun getRetry() {
        favouriteMovieDataSourceFactory.sourceLiveData.value?.retry()
    }
}