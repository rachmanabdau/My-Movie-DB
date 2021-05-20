package com.example.mymoviddb.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListTvDao {

    @Query("SELECT * FROM watch_list_tv_show")
    fun getAllFavouriteTv(): Flow<List<WatchListTvShow.Result>>

    @Query("SELECT * FROM watch_list_tv_show WHERE id IN (:id)")
    fun getFavouriteTvById(id: Long): Flow<WatchListTvShow.Result>

    @Query("SELECT * FROM watch_list_tv_show WHERE title LIKE :favouriteTitle")
    fun getFavouriteTvByTitle(favouriteTitle: String): Flow<List<WatchListTvShow.Result>>

    @Query("DELETE FROM watch_list_tv_show")
    fun clearAllFavouriteMovie()
}