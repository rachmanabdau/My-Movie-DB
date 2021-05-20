package com.example.mymoviddb.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movie")
    fun getAllFavouriteMovie(): Flow<List<FavouriteMovie.Result>>

    @Query("SELECT * FROM favourite_movie WHERE id IN (:id)")
    fun getFavouriteMovieById(id: Long): Flow<FavouriteMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE title LIKE :favouriteTitle")
    fun getFavouriteMovieByTitle(favouriteTitle: String): Flow<List<FavouriteMovie.Result>>

    @Query("DELETE FROM favourite_movie")
    fun cleartAllFavouriteMovie()
}
