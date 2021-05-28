package com.example.mymoviddb.core

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.*
import com.example.mymoviddb.core.model.category.tv.*
import com.example.mymoviddb.core.utils.Util
import com.example.mymoviddb.core.utils.preference.Preference

class DatasourceDependency(
    userPreference: Preference,
    private val networkService: NetworkService,
    val showType: ShowCategoryIndex,
    val title: String = ""
) {

    private val sessionId: String = userPreference.readUserSession()
    private val userId: Int = userPreference.readAccountId()

    suspend fun searchMovie(pageNumber: Int): Result<SearchMovieResult?> {
        return Util.getDataFromServer {
            networkService.searchMoviesAsync(title, pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getPopularMovies(pageNumber: Int): Result<PopularMovie?> {
        return Util.getDataFromServer {
            networkService.getPopularMoviesAsync(pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getNowPlayiingMovies(pageNumber: Int): Result<NowPlayingMovie?> {
        return Util.getDataFromServer {
            networkService.getNowPlayingMoviesAsync(pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getFavouriteMovies(pageNumber: Int): Result<FavouriteMovie?> {
        return Util.getDataFromServer {
            networkService.getFavoriteMoviesAsync(
                userId,
                sessionId,
                pageNumber,
                BuildConfig.V3_AUTH
            ).await()
        }
    }

    suspend fun getWatchListMovies(pageNumber: Int): Result<WatchListMovie?> {
        return Util.getDataFromServer {
            networkService.getWatchListMoviesAsync(
                userId,
                sessionId,
                pageNumber,
                BuildConfig.V3_AUTH
            ).await()
        }
    }

    suspend fun searchTvShows(pageNumber: Int): Result<SearchTvResult?> {
        return Util.getDataFromServer {
            networkService.searchTvShowsAsync(title, pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getPopularTvShows(pageNumber: Int): Result<PopularTvShow?> {
        return Util.getDataFromServer {
            networkService.getPopularTvShowAsync(pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getOnAirTvShows(pageNumber: Int): Result<OnAirTvShow?> {
        return Util.getDataFromServer {
            networkService.getOnAirTvShowAsync(pageNumber, BuildConfig.V3_AUTH).await()
        }
    }

    suspend fun getFavouriteTvShows(pageNumber: Int): Result<FavouriteTvShow?> {
        return Util.getDataFromServer {
            networkService.getFavoriteTvShowAsync(
                userId,
                sessionId,
                pageNumber,
                BuildConfig.V3_AUTH
            ).await()
        }
    }

    suspend fun getWatchListTvShows(pageNumber: Int): Result<WatchListTvShow?> {
        return Util.getDataFromServer {
            networkService.getWatchListTvShowsAsync(
                userId,
                sessionId,
                pageNumber,
                BuildConfig.V3_AUTH
            ).await()
        }
    }
}
