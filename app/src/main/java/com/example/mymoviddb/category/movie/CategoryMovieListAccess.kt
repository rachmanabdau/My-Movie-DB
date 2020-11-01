package com.example.mymoviddb.category.movie

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class CategoryMovieListAccess @Inject constructor(private val access: NetworkService) :
    ICategoryMovieListAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.getPopularMoviesAsync(page, apiKey).await()

                if (movieResult.isSuccessful) {
                    Result.Success(movieResult.body())
                } else {
                    Util.returnError(movieResult)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.getNowPlayingMoviesAsync(page, apiKey).await()

                if (movieResult.isSuccessful) {
                    Result.Success(movieResult.body())
                } else {
                    Util.returnError(movieResult)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun searchMovies(
        title: String,
        page: Int,
        apiKey: String
    ): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.searchMoviesAsync(title, page, apiKey).await()

                if (movieResult.isSuccessful) {
                    Result.Success(movieResult.body())
                } else {
                    Util.returnError(movieResult)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

}