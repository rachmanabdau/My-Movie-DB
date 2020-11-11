package com.example.mymoviddb.favourite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.ShowFavourite
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FavouriteDatasource(
    context: Context,
    private val networkService: IShowFavouriteAccess,
    private val scope: CoroutineScope,
    private val showType: Int
) : PageKeyedDataSource<Int, ShowFavourite.Result>() {

    val result: MutableLiveData<Result<ShowFavourite?>> = MutableLiveData()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    // for sessionId
    private val sessionId: String = PreferenceUtil.readUserSession(context)

    // for user id
    private val userId = PreferenceUtil.readAccountId(context)

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShowFavourite.Result>
    ) {
        emptyList<String>()
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val movieResult: Result<ShowFavourite?> =
                        when (showType) {
                            FAVOURITE_MOVIES -> {
                                networkService.getFavouriteMovies(
                                    accountId = userId, sessionId = sessionId, 1
                                )
                            }
                            else -> {
                                networkService.getFavouriteTVShows(
                                    accountId = userId, sessionId = sessionId, 1
                                )
                            }
                        }


                    if (movieResult is Result.Success) {
                        result.value = movieResult
                        val movieList = movieResult.data?.results ?: emptyList()
                        callback.onResult(movieList, 0, 2)
                    } else {
                        result.value = movieResult
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
        callback: LoadCallback<Int, ShowFavourite.Result>
    ) {
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val movieResult: Result<ShowFavourite?> =
                        when (showType) {
                            FAVOURITE_MOVIES -> {
                                networkService.getFavouriteMovies(
                                    accountId = userId, sessionId = sessionId, params.key + 1
                                )
                            }
                            else -> {
                                networkService.getFavouriteTVShows(
                                    accountId = userId, sessionId = sessionId, params.key + 1
                                )
                            }
                        }

                    if (movieResult is Result.Success) {
                        result.value = movieResult
                        val movieList = movieResult.data?.results ?: emptyList()
                        callback.onResult(movieList, params.key + 1)
                    } else {
                        result.value = movieResult
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
        callback: LoadCallback<Int, ShowFavourite.Result>
    ) {
    }

    companion object {
        const val FAVOURITE_MOVIES = 31
        const val FAVOURITE_TVSHOWS = 32
    }
}