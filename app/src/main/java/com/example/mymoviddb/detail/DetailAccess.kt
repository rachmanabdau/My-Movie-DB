package com.example.mymoviddb.detail

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class DetailAccess @Inject constructor(private val access: NetworkService) : IDetailAccess {

    override suspend fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?> {
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

    override suspend fun getSimilarMovies(movieId: Long, apiKey: String): Result<MovieModel?> {
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

    override suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?> {
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
        tvId: Long,
        apiKey: String
    ): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRecommendationTVShowsAsync(tvId, apiKey).await()

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

    override suspend fun getSimilarTVShows(tvId: Long, apiKey: String): Result<TVShowModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getSimilarTVShowsAsync(tvId, apiKey).await()

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

    override suspend fun getMovieAuthState(
        movieId: Long,
        sessionId: String,
        apiKey: String
    ): Result<MediaState?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getMovieAuthStateAsync(movieId, sessionId, apiKey).await()

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

    override suspend fun getTVAuthState(
        tvId: Long,
        sessionId: String,
        apiKey: String
    ): Result<MediaState?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getTVAuthStateAsync(tvId, sessionId, apiKey).await()

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

    override suspend fun markAsFavorite(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String
    ): Result<ResponsedBackend?> {
        wrapEspressoIdlingResource {
            return try {
                val result =
                    access.markAsFavoriteAsync(accoundId, sessionId, sendMediaType, apiKey).await()

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