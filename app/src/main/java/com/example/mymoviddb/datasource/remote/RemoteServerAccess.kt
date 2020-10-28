package com.example.mymoviddb.datasource.remote


import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Util.returnError


@Suppress("BlockingMethodInNonBlockingContext")
class RemoteServerAccess : RemoteServer {

    override suspend fun requestAccessToken(apiKey: String): Result<RequestTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getRequestTokenAsync().await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ):
            Result<LoginTokenModel?> {
        return try {
            val result = NetworkAPI.retrofitService.loginAsync(
                username, password, requestToken?.requestToken ?: ""
            ).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return returnError(result)
            }
        } catch (e: Exception) {
            return Result.Error(Exception(e.message))
        }
    }

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        return try {
            val result = NetworkAPI.retrofitService.loginAsGuestAsync().await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                return returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getPopularMoviesAsync(page, apiKey).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getNowPlayingMoviesAsync(page, apiKey).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getPopularTvShow(page, apiKey).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?> {
        return try {
            val result = NetworkAPI.retrofitService.getOnAirTvShow(page, apiKey).await()

            if (result.isSuccessful && result.body() != null) {
                Result.Success(result.body())
            } else {
                returnError(result)
            }
        } catch (e: Exception) {
            Result.Error(Exception(e.message))
        }
    }
}

