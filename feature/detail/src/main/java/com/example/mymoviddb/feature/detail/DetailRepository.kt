package com.example.mymoviddb.feature.detail

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.model.category.movie.RecommendationMovie
import com.example.mymoviddb.core.model.category.movie.SimilarMovie
import com.example.mymoviddb.core.model.category.tv.RecommendationTvShow
import com.example.mymoviddb.core.model.category.tv.SimilarTvShow
import com.example.mymoviddb.core.utils.Util
import javax.inject.Inject

class DetailRepository @Inject constructor(private val access: NetworkService) : IDetailAccess {

    override suspend fun getMovieDetail(movieId: Long, apiKey: String): Result<MovieDetail?> {
        return Util.getDataFromServer {
            access.getDetailhMoviesAsync(movieId, apiKey).await()
        }
    }

    override suspend fun getRecommendationMovies(
        movieId: Long,
        apiKey: String
    ): Result<RecommendationMovie?> {
        return Util.getDataFromServer {
            access.getRecommendationMoviesAsync(movieId, apiKey).await()
        }
    }

    override suspend fun getSimilarMovies(movieId: Long, apiKey: String): Result<SimilarMovie?> {
        return Util.getDataFromServer {
            access.getSimilarMoviesAsync(movieId, apiKey).await()
        }
    }

    override suspend fun getDetailTV(tvId: Long, apiKey: String): Result<TVDetail?> {
        return Util.getDataFromServer {
            access.getDetailTvShowsAsync(tvId, apiKey).await()
        }
    }

    override suspend fun getRecommendationTVShows(
        tvId: Long,
        apiKey: String
    ): Result<RecommendationTvShow?> {
        return Util.getDataFromServer {
            access.getRecommendationTVShowsAsync(tvId, apiKey).await()
        }
    }

    override suspend fun getSimilarTVShows(tvId: Long, apiKey: String): Result<SimilarTvShow?> {
        return Util.getDataFromServer {
            access.getSimilarTVShowsAsync(tvId, apiKey).await()
        }
    }

    override suspend fun getMovieAuthState(
        movieId: Long,
        sessionId: String,
        apiKey: String
    ): Result<MediaState?> {
        return Util.getDataFromServer {
            access.getMovieAuthStateAsync(movieId, sessionId, apiKey).await()
        }
    }

    override suspend fun getTVAuthState(
        tvId: Long,
        sessionId: String,
        apiKey: String
    ): Result<MediaState?> {
        return Util.getDataFromServer {
            access.getTVAuthStateAsync(tvId, sessionId, apiKey).await()
        }
    }

    override suspend fun markAsFavorite(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String
    ): Result<ResponsedBackend?> {
        return Util.getDataFromServer {
            access.markAsFavoriteAsync(accoundId, sessionId, sendMediaType, apiKey).await()
        }
    }

    override suspend fun addToWatchList(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String
    ): Result<ResponsedBackend?> {
        return Util.getDataFromServer {
            access.addToWatchListAsync(accoundId, sessionId, sendMediaType, apiKey).await()
        }
    }

}