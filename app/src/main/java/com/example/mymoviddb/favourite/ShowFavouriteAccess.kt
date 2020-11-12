package com.example.mymoviddb.favourite

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.FavouriteShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class ShowFavouriteAccess @Inject constructor(private val access: NetworkService) :
    IShowFavouriteAccess {

    override suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        showType: String,
        apiKey: String
    ): Result<FavouriteShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getFavoriteAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    showType = showType,
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
        showType: String,
        apiKey: String
    ): Result<FavouriteShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getFavoriteAsync(
                    accountId = accountId,
                    sessionId = sessionId,
                    page = page,
                    showType = showType,
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
}