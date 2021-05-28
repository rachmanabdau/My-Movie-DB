package com.example.mymoviddb.core.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow

@Dao
interface FavouriteTvDao {

    @Query("SELECT * FROM favourite_tv_show")
    fun getAllFavouriteTv(): PagingSource<Int, FavouriteTvShow.Result>

    @Query("SELECT * FROM favourite_tv_show WHERE id IN (:id)")
    fun getFavouriteTvById(id: Long): PagingSource<Int, FavouriteTvShow.Result>

    @Query("SELECT * FROM favourite_tv_show WHERE title LIKE :favouriteTitle")
    fun getFavouriteTvByTitle(favouriteTitle: String): PagingSource<Int, FavouriteTvShow.Result>

    @Query("DELETE FROM favourite_tv_show")
    suspend fun clearAllFavouriteTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favouriteTv: List<FavouriteTvShow.Result>)
}