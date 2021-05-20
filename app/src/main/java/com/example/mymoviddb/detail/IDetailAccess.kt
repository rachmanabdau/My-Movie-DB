package com.example.mymoviddb.detail

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.model.category.movie.RecommendationMovie
import com.example.mymoviddb.core.model.category.movie.SimilarMovie
import com.example.mymoviddb.core.model.category.tv.RecommendationTvShow
import com.example.mymoviddb.core.model.category.tv.SimilarTvShow

interface IDetailAccess {

    suspend fun getMovieDetail(movieId: Long, apiKey: String): Result<MovieDetail?>

    suspend fun getRecommendationMovies(movieId: Long, apiKey: String): Result<RecommendationMovie?>

    suspend fun getSimilarMovies(movieId: Long, apiKey: String): Result<SimilarMovie?>

    suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?>

    suspend fun getRecommendationTVShows(tvId: Long, apiKey: String): Result<RecommendationTvShow?>

    suspend fun getSimilarTVShows(tvId: Long, apiKey: String): Result<SimilarTvShow?>

    suspend fun getMovieAuthState(
        movieId: Long,
        sessionId: String,
        apiKey: String
    ): Result<MediaState?>

    suspend fun getTVAuthState(
        tvId: Long,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<MediaState?>

    suspend fun markAsFavorite(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<ResponsedBackend?>

    suspend fun addToWatchList(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String = BuildConfig.V3_AUTH
    ): Result<ResponsedBackend?>
}