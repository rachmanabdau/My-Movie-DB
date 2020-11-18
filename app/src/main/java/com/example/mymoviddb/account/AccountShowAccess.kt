package com.example.mymoviddb.account

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.FavouriteAndWatchListShow
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
        showType: String,
        apiKey: String
    ): Result<FavouriteAndWatchListShow?> {
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
    ): Result<FavouriteAndWatchListShow?> {
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