package com.example.mymoviddb.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface WatchListMovieDao {

    @Query("SELECT * FROM watch_list_movie")
    fun getAllFavouriteMovie(): Flow<List<WatchListMovie.Result>>

    @Query("SELECT * FROM favourite_movie WHERE id IN (:id)")
    fun getFavouriteMovieById(id: Long): Flow<WatchListMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE title LIKE :favouriteTitle")
    fun getFavouriteMovieByTitle(favouriteTitle: String): Flow<List<WatchListMovie.Result>>

    @Query("DELETE FROM favourite_movie")
    fun cleartAllFavouriteMovie()
}
