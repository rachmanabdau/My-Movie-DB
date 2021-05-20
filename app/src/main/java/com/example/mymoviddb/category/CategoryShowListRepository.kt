package com.example.mymoviddb.category

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import com.example.mymoviddb.core.utils.Util
import javax.inject.Inject

class CategoryShowListRepository @Inject constructor(private val access: NetworkService) :
    ICategoryShowListAccess {

    override suspend fun getPopularMovieList(page: Int, apiKey: String): Result<PopularMovie?> {
        return Util.getDataFromServer {
            access.getPopularMoviesAsync(page, apiKey).await()
        }
    }

    override suspend fun getNowPlayingMovieList(
        page: Int,
        apiKey: String
    ): Result<NowPlayingMovie?> {
        return Util.getDataFromServer {
            access.getNowPlayingMoviesAsync(page, apiKey).await()
        }
    }

    override suspend fun searchMovies(
        title: String,
        page: Int,
        apiKey: String
    ): Result<PreviewMovie?> {
        return Util.getDataFromServer {
            access.searchMoviesAsync(title, page, apiKey).await()
        }
    }

    override suspend fun getFavouriteMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewMovie?> {
        return Util.getDataFromServer {
            access.getFavoriteMoviesAsync(
                accountId = accountId,
                sessionId = sessionId,
                page = page,
                apiKey = apiKey
            ).await()
        }
    }

    override suspend fun getWatchlistMovies(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewMovie?> {
        return Util.getDataFromServer {
            access.getWatchListMoviesAsync(
                accountId = accountId,
                sessionId = sessionId,
                page = page,
                apiKey = apiKey
            ).await()
        }
    }

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PopularTvShow?> {
        return Util.getDataFromServer {
            access.getPopularTvShowAsync(page, apiKey).await()
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<OnAirTvShow?> {
        return Util.getDataFromServer {
            access.getOnAirTvShowAsync(page, apiKey).await()
        }
    }

    override suspend fun searchTvShowList(
        title: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        return Util.getDataFromServer {
            access.searchTvShowsAsync(title, page, apiKey).await()
        }
    }

    override suspend fun getFavouriteTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        return Util.getDataFromServer {
            access.getFavoriteTvShowAsync(
                accountId = accountId,
                sessionId = sessionId,
                page = page,
                apiKey = apiKey
            ).await()
        }
    }

    override suspend fun getWatchlistTVShows(
        accountId: Int,
        sessionId: String,
        page: Int,
        apiKey: String
    ): Result<PreviewTvShow?> {
        return Util.getDataFromServer {
            access.getWatchListTvShowsAsync(
                accountId = accountId,
                sessionId = sessionId,
                page = page,
                apiKey = apiKey
            ).await()
        }
    }

}