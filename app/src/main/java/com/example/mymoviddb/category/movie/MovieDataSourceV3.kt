package com.example.mymoviddb.category.movie

import androidx.paging.PagingSource
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.wrapEspressoIdlingResource

class MovieDataSourceV3(
    private val networkService: ICategoryMovieListAccess,
    private val categoryId: Int,
    private val title: String
) : PagingSource<Int, MovieModel.Result>() {

    companion object {
        const val POPULAR_MOVIE_ID = 1
        const val NOW_PLAYING_MOVIE_ID = 2
        const val SEARCH_MOVIES = 31
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel.Result> {
        // Start refresh at page 1 if undefined.
        wrapEspressoIdlingResource {
            val nextPageNumber = params.key ?: 1
            return try {
                val movieResult: Result<MovieModel?> =
                    movieData(categoryId, nextPageNumber, networkService)

                if (movieResult is Result.Success) {
                    val movieList = movieResult.data?.results ?: emptyList()
                    LoadResult.Page(
                        data = movieList,
                        prevKey = null,
                        nextKey = nextPageNumber + 1
                    )
                } else {
                    LoadResult.Error(Exception(getErrorMessage(movieResult)))
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    private fun getErrorMessage(result: Result<*>): String {
        return if (result is Result.Error) {
            result.exception.localizedMessage ?: "Unknown exception occured."
        } else {
            "Unknown exception occured."
        }
    }

    private suspend fun movieData(
        categoryId: Int,
        pageNumber: Int,
        networkService: ICategoryMovieListAccess
    ): Result<MovieModel?> {
        return when {
            categoryId == SEARCH_MOVIES && title.isNotBlank() -> {
                networkService.searchMovies(title, pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == POPULAR_MOVIE_ID -> {
                networkService.getPopularMovieList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == NOW_PLAYING_MOVIE_ID -> {
                networkService.getNowPlayingMovieList(pageNumber, BuildConfig.V3_AUTH)
            }
            else -> {
                Result.Success(null)
            }
        }
    }
}