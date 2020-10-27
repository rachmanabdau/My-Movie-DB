package com.example.mymoviddb.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PopularMovieDataSource(
    private val networkService: RemoteServer,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, MovieModel.Result>() {

    val result: MutableLiveData<Result<MovieModel?>> = MutableLiveData()

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    fun retry() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel.Result>
    ) {
        scope.launch {
            try {
                result.value = Result.Loading
                val movieResult = networkService.getPopularMovieList(1, BuildConfig.V3_AUTH)

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
                Log.d("loadinitial", "error in log after  ${e.message}")
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel.Result>
    ) {
        scope.launch {
            try {
                result.value = Result.Loading
                val movieResult =
                    networkService.getPopularMovieList(params.key + 1, BuildConfig.V3_AUTH)

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
                Log.d("loadafter", "error in log after ${e.message}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel.Result>
    ) {
    }
}