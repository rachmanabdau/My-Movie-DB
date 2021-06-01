package com.example.mymoviddb.feature.detail

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.model.category.movie.RecommendationMovie
import com.example.mymoviddb.core.model.category.movie.SimilarMovie
import com.example.mymoviddb.core.model.category.tv.RecommendationTvShow
import com.example.mymoviddb.core.model.category.tv.SimilarTvShow
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class DetailRepository @Inject constructor(private val access: NetworkService) : IDetailAccess {

    override suspend fun getMovieDetail(movieId: Long, apiKey: String): Result<MovieDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getDetailhMoviesAsync(movieId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getRecommendationMovies(
        movieId: Long,
        apiKey: String
    ): Result<RecommendationMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRecommendationMoviesAsync(movieId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Long, apiKey: String): Result<SimilarMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getSimilarMoviesAsync(movieId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getDetailTvShowsAsync(tvId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getRecommendationTVShows(
        tvId: Long,
        apiKey: String
    ): Result<RecommendationTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRecommendationTVShowsAsync(tvId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getSimilarTVShows(tvId: Long, apiKey: String): Result<SimilarTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getSimilarTVShowsAsync(tvId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
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
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
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
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
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
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun addToWatchList(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String
    ): Result<ResponsedBackend?> {
        wrapEspressoIdlingResource {
            return try {
                val result =
                    access.addToWatchListAsync(accoundId, sessionId, sendMediaType, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

}