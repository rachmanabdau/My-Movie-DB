package com.example.mymoviddb.core.datasource.remote

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.BuildConfig.V3_AUTH
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.movie.SearchMovieResult
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import com.example.mymoviddb.core.model.category.tv.SearchTvResult
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

val moshi: Moshi = Moshi.Builder()
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
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<RequestTokenModel?>>

    @GET("authentication/guest_session/new")
    fun loginAsGuestAsync(
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<GuestSessionModel?>>

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    fun loginAsync(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<LoginTokenModel?>>

    @FormUrlEncoded
    @POST("authentication/session/new")
    fun createSessionAsync(
        @Field("request_token") requestToken: String,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<NewSessionModel>>

    @GET("movie/popular")
    fun getPopularMoviesAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PopularMovie>>

    @GET("movie/now_playing")
    fun getNowPlayingMoviesAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<NowPlayingMovie>>

    @GET("tv/popular")
    fun getPopularTvShowAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PopularTvShow>>

    @GET("tv/on_the_air")
    fun getOnAirTvShowAsync(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<OnAirTvShow>>

    @GET("search/movie")
    fun searchMoviesAsync(
        @Query("query") title: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<SearchMovieResult>>

    @GET("search/tv")
    fun searchTvShowsAsync(
        @Query("query") title: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<SearchTvResult>>

    @GET("movie/{movie_id}")
    fun getDetailhMoviesAsync(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<MovieDetail>>

    @GET("tv/{tv_id}")
    fun getDetailTvShowsAsync(
        @Path("tv_id") tvId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<TVDetail>>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationMoviesAsync(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewMovie>>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMoviesAsync(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewMovie>>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendationTVShowsAsync(
        @Path("tv_id") tvId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewTvShow>>

    @GET("tv/{tv_id}/similar")
    fun getSimilarTVShowsAsync(
        @Path("tv_id") tvId: Long,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewTvShow>>

    @GET("account")
    fun getAccountDetailAsync(
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String
    ): Deferred<Response<UserDetail>>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    fun logoutAsync(
        @Body userSessionId: Map<String, String>,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<ResponsedBackend>>

    @GET("movie/{movie_id}/account_states")
    fun getMovieAuthStateAsync(
        @Path("movie_id") movieId: Long,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<MediaState>>

    @GET("tv/{tv_id}/account_states")
    fun getTVAuthStateAsync(
        @Path("tv_id") tvId: Long,
        @Query("session_id") sessionId: String,
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<MediaState>>

    @POST("account/{account_id}/favorite")
    fun markAsFavoriteAsync(
        @Path("account_id") accoundId: Int,
        @Query("session_id") sessionId: String,
        @Body sendMediaType: MarkMediaAs,
        @Query("api_key") apiKey: String = V3_AUTH,
        @Header("Content-Type") contentType: String = "application/json;charset=utf-8"
    ): Deferred<Response<ResponsedBackend>>

    @GET("account/{account_id}/favorite/movies")
    fun getFavoriteMoviesAsync(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewMovie>>

    @GET("account/{account_id}/favorite/tv")
    fun getFavoriteTvShowAsync(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewTvShow>>

    @POST("account/{account_id}/watchlist")
    fun addToWatchListAsync(
        @Path("account_id") accoundId: Int,
        @Query("session_id") sessionId: String,
        @Body sendMediaType: MarkMediaAs,
        @Query("api_key") apiKey: String = V3_AUTH,
        @Header("Content-Type") contentType: String = "application/json;charset=utf-8"
    ): Deferred<Response<ResponsedBackend>>

    @GET("account/{account_id}/watchlist/movies")
    fun getWatchListMoviesAsync(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewMovie>>

    @GET("account/{account_id}/watchlist/tv")
    fun getWatchListTvShowsAsync(
        @Path("account_id") accountId: Int,
        @Query("session_id") sessionId: String,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "created_at.desc",
        @Query("api_key") apiKey: String = V3_AUTH
    ): Deferred<Response<PreviewTvShow>>
}

object NetworkAPI {
    val retrofitService: NetworkService by lazy {
        retrofit.create(NetworkService::class.java)
    }
}