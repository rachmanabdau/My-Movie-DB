package com.example.mymoviddb.login

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.utils.Util
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private val access: NetworkService) :
    ILoginAccess {

    override suspend fun getRequestToken(apiKey: String): Result<RequestTokenModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.getRequestTokenAsync(apiKey).await()
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

    override suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?,
        apiKey: String
    ): Result<LoginTokenModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result =
                    access.loginAsync(
                        username, password, requestToken?.requestToken ?: ""
                    ).await()
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

    override suspend fun createNewSession(
        requestToken: String,
        apiKey: String
    ): Result<NewSessionModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result = access.createSessionAsync(requestToken, apiKey).await()
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

    override suspend fun loginAsGuest(apiKey: String): Result<GuestSessionModel?> {
        wrapEspressoIdlingResource {
            return try {
                val result =
                    access.loginAsGuestAsync(apiKey).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
        }
    }

}