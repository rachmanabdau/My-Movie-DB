package com.example.mymoviddb.home

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import com.example.mymoviddb.core.utils.Util
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource
import javax.inject.Inject

class HomeRepository @Inject constructor(private val access: NetworkService) : IHomeAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PopularMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getPopularMoviesAsync(page, apiKey).await()
                if (result.body() != null){
                    Result.Success(result.body())
                }else{
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getNowPlayingMovieList(
        page: Int,
        apiKey: String
    ): Result<NowPlayingMovie?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getNowPlayingMoviesAsync(page, apiKey).await()
                if (result.body() != null){
                    Result.Success(result.body())
                }else{
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PopularTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getPopularTvShowAsync(page, apiKey).await()
                if (result.body() != null){
                    Result.Success(result.body())
                }else{
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<OnAirTvShow?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getOnAirTvShowAsync(page, apiKey).await()
                if (result.body() != null){
                    Result.Success(result.body())
                }else{
                    Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun getUserDetail(sessionId: String, apiKey: String): Result<UserDetail?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getAccountDetailAsync(sessionId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

    override suspend fun logout(
        sessionId: Map<String, String>,
        apiKey: String
    ): Result<ResponsedBackend?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.logoutAsync(sessionId, apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

}