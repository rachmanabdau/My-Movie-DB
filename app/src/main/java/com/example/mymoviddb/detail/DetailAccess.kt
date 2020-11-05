package com.example.mymoviddb.detail

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.MovieDetail
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVDetail
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

}