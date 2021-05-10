package com.example.mymoviddb.category

import com.example.mymoviddb.model.PreviewMovie
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun searchMovies(title: String, page: Int, apiKey: String): Result<PreviewMovie?>

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun searchTvShowList(title: String, page: Int, apiKey: String): Result<TVShowModel?>
}