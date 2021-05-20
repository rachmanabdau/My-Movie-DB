package com.example.mymoviddb.home

import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.utils.Util
import javax.inject.Inject

class HomeRepository @Inject constructor(private val access: NetworkService) : IHomeAccess {

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

    override suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?> {
        return Util.getDataFromServer {
            access.getPopularTvShowAsync(page, apiKey).await()
        }
    }

    override suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<PreviewTvShow?> {
        return Util.getDataFromServer {
            access.getOnAirTvShowAsync(page, apiKey).await()
        }
    }

}