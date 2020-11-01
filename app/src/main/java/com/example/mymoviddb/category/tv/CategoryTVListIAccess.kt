package com.example.mymoviddb.category.tv

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject

//Add Try-Catch in Category Movie
class CategoryTVListIAccess @Inject constructor(private val access: NetworkService) :
    ICategoryTVListAccess {

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
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

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
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
    ): Result<TVShowModel?> {
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
}