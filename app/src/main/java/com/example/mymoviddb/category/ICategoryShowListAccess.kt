package com.example.mymoviddb.category

import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?>

    suspend fun searchMovies(title: String, page: Int, apiKey: String): Result<MovieModel?>

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun searchTvShowList(title: String, page: Int, apiKey: String): Result<TVShowModel?>
}