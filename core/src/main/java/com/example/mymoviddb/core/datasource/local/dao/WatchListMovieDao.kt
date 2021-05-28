package com.example.mymoviddb.core.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviddb.core.model.category.movie.WatchListMovie


@Dao
interface WatchListMovieDao {

    @Query("SELECT * FROM watch_list_movie")
    fun getAllWatchListMovie(): PagingSource<Int, WatchListMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE id IN (:id)")
    fun getWatchListMovieById(id: Long): PagingSource<Int, WatchListMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE title LIKE :watchListTitle")
    fun getWatchListMovieByTitle(watchListTitle: String): PagingSource<Int, WatchListMovie.Result>

    @Query("DELETE FROM favourite_movie")
    suspend fun cleartAllWatchListMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(watchlistMovie: List<WatchListMovie.Result>)
}
