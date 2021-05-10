package com.example.mymoviddb.category

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.PreviewTvShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class CategoryShowListAccess @Inject constructor(private val access: NetworkService) :
    ICategoryShowListAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PreviewMovie?> {
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

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<PreviewMovie?> {
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
    ): Result<PreviewMovie?> {
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

    override suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getFavoriteMoviesAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    apiKey = apiKey
                ).await()

                if (result.isSuccessful && result.body() != null) {
                    Result.Success(result.body())
                } else {
                    return Util.returnError(result)
                }
            } catch (e: Exception) {
                return Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getWatchlistMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getWatchListMoviesAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    apiKey = apiKey
                ).await()

                if (result.isSuccessful && result.body() != null) {
                    Result.Success(result.body() as PreviewMovie?)
                } else {
                    return Util.returnError(result)
                }
            } catch (e: Exception) {
                return Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.getPopularTvShowAsync(page, apiKey).await()

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

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.getOnAirTvShowAsync(page, apiKey).await()

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

    override suspend fun searchTvShowList(
        title: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val movieResult = access.searchTvShowsAsync(title, page, apiKey).await()

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

    override suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getFavoriteTvShowAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    apiKey = apiKey
                ).await()

                if (result.isSuccessful && result.body() != null) {
                    Result.Success(result.body())
                } else {
                    return Util.returnError(result)
                }
            } catch (e: Exception) {
                return Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getWatchlistTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getWatchListTvShowsAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    apiKey = apiKey
                ).await()

                if (result.isSuccessful && result.body() != null) {
                    Result.Success(result.body() as PreviewTvShow?)
                } else {
                    return Util.returnError(result)
                }
            } catch (e: Exception) {
                return Result.Error(Exception(e.message))
            }
        }
    }

}