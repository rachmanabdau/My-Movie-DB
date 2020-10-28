package com.example.mymoviddb.category.movie

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Util

class CategoryMovieListAccess(private val access: NetworkService) : CategoryMovieListInterface {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        val movieResult = access.getNowPlayingMoviesAsync(page, apiKey).await()

        return if (movieResult.isSuccessful) {
            Result.Success(movieResult.body())
        } else {
            Util.returnError(movieResult)
        }
    }

    override suspend fun getNowPlayingMovieList(page: Int, apiKey: String): Result<MovieModel?> {
        val movieResult = access.getNowPlayingMoviesAsync(page, apiKey).await()

        return if (movieResult.isSuccessful) {
            Result.Success(movieResult.body())
        } else {
            Util.returnError(movieResult)
        }
    }

}