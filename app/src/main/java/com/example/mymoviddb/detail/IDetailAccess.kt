package com.example.mymoviddb.detail

import com.example.mymoviddb.model.MovieDetail
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVDetail

interface IDetailAccess {

    suspend fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?>

    suspend fun getRecommendationMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getSimialrMovies(movieId: Long, apiKey: String): Result<MovieModel?>

    suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?>
}