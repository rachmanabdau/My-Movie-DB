package com.example.mymoviddb.category.tv

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.Util
//Add Try-Catch in Category Movie
class CategoryTVListIAccess(private val access: NetworkService) : ICategoryTVListAccess {

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return try {
            val movieResult = access.getPopularTvShow(page, apiKey).await()

            if (movieResult.isSuccessful) {
                Result.Success(movieResult.body())
            } else {
                Util.returnError(movieResult)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return try {
            val movieResult = access.getOnAirTvShow(page, apiKey).await()

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