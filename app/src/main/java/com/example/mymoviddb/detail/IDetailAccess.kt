package com.example.mymoviddb.detail

import com.example.mymoviddb.model.MovieDetail
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVDetail

interface IDetailAccess {

    suspend fun getDetailMovie(movieId: Long, apiKey: String): Result<MovieDetail?>

    suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?>
}