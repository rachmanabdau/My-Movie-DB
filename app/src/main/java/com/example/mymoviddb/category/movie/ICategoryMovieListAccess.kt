package com.example.mymoviddb.category.movie

import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result

interface ICategoryMovieListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?>
}