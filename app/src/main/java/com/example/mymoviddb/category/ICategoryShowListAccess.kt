package com.example.mymoviddb.category

import androidx.paging.PagingData
import com.example.mymoviddb.core.model.category.movie.NowPlayingMovie
import com.example.mymoviddb.core.model.category.movie.PopularMovie
import com.example.mymoviddb.core.model.category.tv.OnAirTvShow
import com.example.mymoviddb.core.model.category.tv.PopularTvShow
import kotlinx.coroutines.flow.Flow

interface ICategoryShowListAccess {

    suspend fun getPopularMovieList(): Flow<PagingData<PopularMovie.Result>>

    suspend fun getNowPlayingMovieList(): Flow<PagingData<NowPlayingMovie.Result>>

    suspend fun getPopularTvShowList(): Flow<PagingData<PopularTvShow.Result>>

    suspend fun getOnAirTvShowList(): Flow<PagingData<OnAirTvShow.Result>>
}