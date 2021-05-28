package com.example.mymoviddb.core.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie


@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM favourite_movie")
    fun getAllFavouriteMovie(): PagingSource<Int, FavouriteMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE id IN (:id)")
    fun getFavouriteMovieById(id: Long): PagingSource<Int, FavouriteMovie.Result>

    @Query("SELECT * FROM favourite_movie WHERE title LIKE :favouriteTitle")
    fun getFavouriteMovieByTitle(favouriteTitle: String): PagingSource<Int, FavouriteMovie.Result>

    @Query("DELETE FROM favourite_movie")
    suspend fun cleartAllFavouriteMovie()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favouriteMovies: List<FavouriteMovie.Result>)
}
