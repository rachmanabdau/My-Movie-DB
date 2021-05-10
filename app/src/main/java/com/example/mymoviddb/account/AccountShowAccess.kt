package com.example.mymoviddb.account

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.PreviewTvShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class AccountShowAccess @Inject constructor(private val access: NetworkService) :
    IAccountShowAccess {

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
}