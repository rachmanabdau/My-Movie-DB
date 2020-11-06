package com.example.mymoviddb.detail

import com.example.mymoviddb.model.*

interface IDetailAccess {

    suspend fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?>

    suspend fun getRecommendationMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getSimialrMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?>

    suspend fun getRecommendationTVShows(movieId: Long, apiKey: String): Result<TVShowModel?>

    suspend fun getSimialrTVShows(movieId: Long, apiKey: String): Result<TVShowModel?>
}