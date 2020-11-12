package com.example.mymoviddb.category.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDataSource(
    private val networkService: ICategoryMovieListAccess,
    private val scope: CoroutineScope,
    private val title: String
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
        emptyList<String>()
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val movieResult: Result<MovieModel?> =
                        when {
                            MOVIE_CATEGORY_ID == SEARCH_MOVIES && title.isNotBlank() -> {
                                networkService.searchMovies(title, 1, BuildConfig.V3_AUTH)
                            }
                            MOVIE_CATEGORY_ID == POPULAR_MOVIE_ID -> {
                                networkService.getPopularMovieList(1, BuildConfig.V3_AUTH)
                            }
                            MOVIE_CATEGORY_ID == NOW_PLAYING_MOVIE_ID -> {
                                networkService.getNowPlayingMovieList(1, BuildConfig.V3_AUTH)
                            }
                            else -> {
                                Result.Success(null)
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
                Timber.d((result.value.toString()))
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel.Result>
    ) {
        wrapEspressoIdlingResource {
            scope.launch {
                try {
                    result.value = Result.Loading
                    val movieResult =
                        when {
                            MOVIE_CATEGORY_ID == SEARCH_MOVIES && title.isNotBlank() -> {
                                networkService.searchMovies(
                                    title,
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            MOVIE_CATEGORY_ID == POPULAR_MOVIE_ID -> {
                                networkService.getPopularMovieList(
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            MOVIE_CATEGORY_ID == NOW_PLAYING_MOVIE_ID -> {
                                networkService.getNowPlayingMovieList(
                                    params.key,
                                    BuildConfig.V3_AUTH
                                )
                            }
                            else -> {
                                Result.Success(null)
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
                Timber.d((result.value.toString()))
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieModel.Result>
    ) {
    }

    companion object {
        const val POPULAR_MOVIE_ID = 1
        const val NOW_PLAYING_MOVIE_ID = 2
        const val SEARCH_MOVIES = 31
        var MOVIE_CATEGORY_ID = POPULAR_MOVIE_ID
    }
}