package com.example.mymoviddb.category.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TVDataSource(
    private val networkService: ICategoryTVListAccess,
    private val scope: CoroutineScope,
    private val title: String
) : PageKeyedDataSource<Int, TVShowModel.Result>() {

    val result = MutableLiveData<Result<TVShowModel?>>()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TVShowModel.Result>
    ) {
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val tvShowResult = when {
                        TV_CATEGORY_ID == SEARCH_TV && title.isNotBlank() -> {
                            networkService.searchTvShowList(title, 1, BuildConfig.V3_AUTH)
                        }
                        TV_CATEGORY_ID == POPULAR_TV_ID -> {
                            networkService.getPopularTvShowList(1, BuildConfig.V3_AUTH)
                        }
                        TV_CATEGORY_ID == ON_AIR_TV_ID -> {
                            networkService.getOnAirTvShowList(1, BuildConfig.V3_AUTH)
                        }
                        else -> {
                            Result.Success(null)
                        }
                    }

                    if (tvShowResult is Result.Success) {
                        result.value = tvShowResult
                        val tvShowList = tvShowResult.data?.results ?: emptyList()
                        callback.onResult(tvShowList, 0, 2)
                    } else {
                        result.value = tvShowResult
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

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVShowModel.Result>
    ) {

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVShowModel.Result>
    ) {
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val tvResult =
                        when {
                            TV_CATEGORY_ID == SEARCH_TV && title.isNotBlank() -> {
                                networkService.searchTvShowList(
                                    title,
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            TV_CATEGORY_ID == POPULAR_TV_ID -> {
                                networkService.getPopularTvShowList(
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            TV_CATEGORY_ID == ON_AIR_TV_ID -> {
                                networkService.getOnAirTvShowList(
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            else -> {
                                Result.Success(null)
                            }
                        }


                    if (tvResult is Result.Success) {
                        result.value = tvResult
                        val tvList = tvResult.data?.results ?: emptyList()
                        callback.onResult(tvList, params.key + 1)
                    } else {
                        result.value = tvResult
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

    companion object {
        const val POPULAR_TV_ID = 1
        const val ON_AIR_TV_ID = 2
        const val SEARCH_TV = 32
        var TV_CATEGORY_ID = POPULAR_TV_ID
    }
}