package com.example.mymoviddb.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteTvDao {

    @Query("SELECT * FROM favourite_tv_show")
    fun getAllFavouriteTv(): Flow<List<FavouriteTvShow.Result>>

    @Query("SELECT * FROM favourite_tv_show WHERE id IN (:id)")
    fun getFavouriteTvById(id: Long): Flow<FavouriteTvShow.Result>

    @Query("SELECT * FROM favourite_tv_show WHERE title LIKE :favouriteTitle")
    fun getFavouriteTvByTitle(favouriteTitle: String): Flow<List<FavouriteTvShow.Result>>

    @Query("DELETE FROM favourite_tv_show")
    fun clearAllFavouriteMovie()
}