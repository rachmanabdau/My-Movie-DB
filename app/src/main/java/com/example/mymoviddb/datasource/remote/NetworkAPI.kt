package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.LoginTokenModel
import com.example.mymoviddb.model.NewSessionModel
import com.example.mymoviddb.model.RequestTokenModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BuildConfig.BASE_URL)
    .build()

interface NetworkService {

    @GET("authentication/token/new")
    fun getRequestTokenAsync(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Deferred<Response<RequestTokenModel?>>

    @GET("authentication/guest_session/new")
    fun loginAsGuestAsync(
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Deferred<Response<GuestSessionModel?>>

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    fun loginAsync(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Deferred<Response<LoginTokenModel?>>

    @FormUrlEncoded
    @POST("authentication/session/new")
    fun createSeesionAsync(
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = BuildConfig.V3_AUTH
    ): Deferred<Response<NewSessionModel>>
}

object NetworkAPI {
    val retrofitService: NetworkService by lazy {
        retrofit.create(NetworkService::class.java)
    }
}