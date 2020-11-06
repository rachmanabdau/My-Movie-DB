package com.example.mymoviddb.detail

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class DetailAccess @Inject constructor(private val access: NetworkService) : IDetailAccess {

    suspend override fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getDetailhMoviesAsync(movieId, apiKey).await()

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

    override suspend fun getRecommendationMovies(
        movieId: Long,
        apiKey: String
    ): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRecommendationMoviesAsync(movieId, apiKey).await()

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

    override suspend fun getSimialrMovies(movieId: Long, apiKey: String): Result<MovieModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getSimilarMoviesAsync(movieId, apiKey).await()

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

    suspend override fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getDetailTvShowsAsync(tvId, apiKey).await()

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

    override suspend fun getRecommendationTVShows(
        movieId: Long,
        apiKey: String
    ): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRecommendationTVShowsAsync(movieId, apiKey).await()

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

    override suspend fun getSimialrTVShows(movieId: Long, apiKey: String): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getSimilarTVShowsAsync(movieId, apiKey).await()

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