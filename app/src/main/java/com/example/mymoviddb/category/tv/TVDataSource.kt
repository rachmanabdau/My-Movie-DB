package com.example.mymoviddb.category.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TVDataSource(
    private val networkService: RemoteServer,
    private val scope: CoroutineScope,
    private val categoryId: Int
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
        scope.launch {
            try {
                result.value = Result.Loading
                val tvShowResult = if (categoryId == POPULAR_TV_ID) {
                    networkService.getPopularTvShowList(1, BuildConfig.V3_AUTH)
                } else {
                    networkService.getOnAirTvShowList(1, BuildConfig.V3_AUTH)
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

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVShowModel.Result>
    ) {

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVShowModel.Result>
    ) {
        scope.launch {
            try {
                result.value = Result.Loading
                val tvResult = if (categoryId == POPULAR_TV_ID) {
                    networkService.getPopularTvShowList(params.key + 1, BuildConfig.V3_AUTH)
                } else {
                    networkService.getOnAirTvShowList(params.key + 1, BuildConfig.V3_AUTH)
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

    companion object {
        const val POPULAR_TV_ID = 1
        const val NOW_PLAYING_TV_ID = 2
    }
}