package com.example.mymoviddb.account.paging

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AccountShowDatasource(
    app: Application,
    private val networkService: IAccountShowAccess,
    private val scope: CoroutineScope,
    private val showType: Int
) : PageKeyedDataSource<Int, FavouriteAndWatchListShow.Result>() {

    val result: MutableLiveData<Result<FavouriteAndWatchListShow?>> = MutableLiveData()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    // for sessionId
    private val sessionId: String = PreferenceUtil.readUserSession(app)

    // for user id
    private val userId = PreferenceUtil.readAccountId(app)

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FavouriteAndWatchListShow.Result>
    ) {
        emptyList<String>()
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val listAndWatchListShow: Result<FavouriteAndWatchListShow?> =
                        when (showType) {
                            FAVOURITE_MOVIES ->
                                networkService.getFavouriteMovies(
                                    accountId = userId, sessionId = sessionId, 1
                                )

                            FAVOURITE_TVSHOWS ->
                                networkService.getFavouriteTVShows(
                                    accountId = userId, sessionId = sessionId, 1
                                )

                            WATCHLIST_MOVIES ->
                                networkService.getWatchlistMovies(
                                    accountId = userId,
                                    sessionId = sessionId,
                                    1
                                )

                            else -> networkService.getWatchlistMovies(
                                accountId = userId,
                                sessionId = sessionId,
                                1
                            )
                        }


                    if (listAndWatchListShow is Result.Success) {
                        result.value = listAndWatchListShow
                        val movieList = listAndWatchListShow.data?.results ?: emptyList()
                        callback.onResult(movieList, null, 2)
                    } else {
                        result.value = listAndWatchListShow
                        retry = {
                            loadInitial(params, callback)
                        }
                    }
                } catch (e: Exception) {
                    result.value = Result.Error(e)
                    retry = {
                        loadInitial(params, callback)
                    }
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, FavouriteAndWatchListShow.Result>
    ) {
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val listAndWatchListShow: Result<FavouriteAndWatchListShow?> =
                        when (showType) {
                            FAVOURITE_MOVIES ->
                                networkService.getFavouriteMovies(
                                    accountId = userId, sessionId = sessionId, params.key
                                )

                            FAVOURITE_TVSHOWS ->
                                networkService.getFavouriteTVShows(
                                    accountId = userId, sessionId = sessionId, params.key
                                )

                            WATCHLIST_MOVIES ->
                                networkService.getWatchlistMovies(
                                    accountId = userId,
                                    sessionId = sessionId,
                                    params.key
                                )

                            else -> networkService.getWatchlistMovies(
                                accountId = userId,
                                sessionId = sessionId,
                                params.key
                            )
                        }

                    if (listAndWatchListShow is Result.Success) {
                        result.value = listAndWatchListShow
                        val movieList = listAndWatchListShow.data?.results ?: emptyList()
                        callback.onResult(movieList, params.key + 1)
                    } else {
                        result.value = listAndWatchListShow
                        retry = {
                            loadAfter(params, callback)
                        }
                    }
                } catch (e: Exception) {
                    result.value = Result.Error(e)
                    retry = {
                        loadAfter(params, callback)
                    }
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, FavouriteAndWatchListShow.Result>
    ) {
    }

    companion object {
        const val FAVOURITE_MOVIES = 31
        const val FAVOURITE_TVSHOWS = 32
        const val WATCHLIST_MOVIES = 41
        const val WATCHLIST_TVSHOWS = 42
    }
}