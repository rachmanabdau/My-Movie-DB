package com.example.mymoviddb.authentication

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.wrapEspressoIdlingResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationAccess @Inject constructor(private val access: NetworkService) :
    IAuthenticationAccess {

    override suspend fun getRequestToken(apiKey: String): Result<RequestTokenModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRequestTokenAsync(apiKey).await()

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

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.loginAsync(
                    username, password, requestToken?.requestToken ?: ""
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

    override suspend fun createNewSession(
        apiKey: String,
        requestToken: String
    ): Result<NewSessionModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.createSeesionAsync(requestToken, apiKey).await()

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

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.loginAsGuestAsync(apiKey).await()

                if (result.isSuccessful && result.body() != null) {
                    Result.Success(result.body())
                } else {
                    return Util.returnError(result)
                }
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

}