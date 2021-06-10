package com.example.mymoviddb.core

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.*
import com.example.mymoviddb.core.model.category.tv.*
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource

class DatasourceDependency(
    userPreference: Preference,
    private val networkService: NetworkService,
    val showType: ShowCategoryIndex,
    val title: String = ""
) {

    private val sessionId: String = userPreference.readUserSession()
    private val userId: Int = userPreference.readAccountId()

    suspend fun searchMovie(pageNumber: Int): Result<SearchMovieResult?> {
            return try {
                val result =
                    networkService.searchMoviesAsync(title, pageNumber, BuildConfig.V3_AUTH).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }
    }

    suspend fun getPopularMovies(pageNumber: Int): Result<PopularMovie?> {
            return try {
                val result =
                    networkService.getPopularMoviesAsync(pageNumber, BuildConfig.V3_AUTH).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))

            }
    }

    suspend fun getNowPlayiingMovies(pageNumber: Int): Result<NowPlayingMovie?> {
            return try {
                val result =
                    networkService.getNowPlayingMoviesAsync(pageNumber, BuildConfig.V3_AUTH)
                        .await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))

            }

    }

    suspend fun getFavouriteMovies(pageNumber: Int): Result<FavouriteMovie?> {
            return try {
                val result =
                    networkService.getFavoriteMoviesAsync(
                        userId,
                        sessionId,
                        pageNumber,
                        BuildConfig.V3_AUTH
                    ).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }


    }

    suspend fun getWatchListMovies(pageNumber: Int): Result<WatchListMovie?> {
            return try {
                val result =
                    networkService.getWatchListMoviesAsync(
                        userId,
                        sessionId,
                        pageNumber,
                        BuildConfig.V3_AUTH
                    ).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }

    }


    suspend fun searchTvShows(pageNumber: Int): Result<SearchTvResult?> {
            return try {
                val result =
                    networkService.searchTvShowsAsync(title, pageNumber, BuildConfig.V3_AUTH)
                        .await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }


    }

    suspend fun getPopularTvShows(pageNumber: Int): Result<PopularTvShow?> {
            return try {
                val result =
                    networkService.getPopularTvShowAsync(pageNumber, BuildConfig.V3_AUTH)
                        .await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }


    }

    suspend fun getOnAirTvShows(pageNumber: Int): Result<OnAirTvShow?> {
            return try {
                val result =
                    networkService.getOnAirTvShowAsync(pageNumber, BuildConfig.V3_AUTH).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }


    }

    suspend fun getFavouriteTvShows(pageNumber: Int): Result<FavouriteTvShow?> {
            return try {
                val result =
                    networkService.getFavoriteTvShowAsync(
                        userId,
                        sessionId,
                        pageNumber,
                        BuildConfig.V3_AUTH
                    ).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }

    }

    suspend fun getWatchListTvShows(pageNumber: Int): Result<WatchListTvShow?> {
            return try {
                val result =
                    networkService.getWatchListTvShowsAsync(
                        userId,
                        sessionId,
                        pageNumber,
                        BuildConfig.V3_AUTH
                    ).await()
                Result.Success(result.body())
            } catch (e: Exception) {
                Result.Error(Exception(e.message))
            }

    }
}
