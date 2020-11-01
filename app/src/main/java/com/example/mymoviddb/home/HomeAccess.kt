package com.example.mymoviddb.home

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class HomeAccess @Inject constructor(private val access: NetworkService) : IHomeAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getPopularMoviesAsync(page, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getNowPlayingMoviesAsync(page, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getPopularTvShowAsync(page, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getOnAirTvShowAsync(page, apiKey).await()

                if (result.isSuccessful) {
                    Result.Success(result.body())
                } else {
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

}