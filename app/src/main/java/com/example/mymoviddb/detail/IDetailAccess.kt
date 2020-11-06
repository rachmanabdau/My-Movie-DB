package com.example.mymoviddb.detail

import com.example.mymoviddb.model.*

interface IDetailAccess {

    suspend fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?>

    suspend fun getRecommendationMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getSimilarMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?>

    suspend fun getRecommendationTVShows(tvId: Long, apiKey: String): Result<TVShowModel?>

    suspend fun getSimilarTVShows(tvId: Long, apiKey: String): Result<TVShowModel?>
}