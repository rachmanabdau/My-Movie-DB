package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.*

interface RemoteServer {

    suspend fun requestAccessToken(apiKey: String = BuildConfig.V3_AUTH): Result<RequestTokenModel?>

    suspend fun loginAsUser(
        username: String,
        password: String,
        requestToken: RequestTokenModel?
    ): Result<LoginTokenModel?>

    suspend fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH): Result<GuestSessionModel?>

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?>
}