package com.example.mymoviddb.category.tv

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.Util

class CategoryTVListIAccess(private val access: NetworkService) : CategoryTVListInterface {

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        val movieResult = access.getPopularTvShow(page, apiKey).await()

        return if (movieResult.isSuccessful) {
            Result.Success(movieResult.body())
        } else {
            Util.returnError(movieResult)
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        val movieResult = access.getOnAirTvShow(page, apiKey).await()

        return if (movieResult.isSuccessful) {
            Result.Success(movieResult.body())
        } else {
            Util.returnError(movieResult)
        }
    }
}