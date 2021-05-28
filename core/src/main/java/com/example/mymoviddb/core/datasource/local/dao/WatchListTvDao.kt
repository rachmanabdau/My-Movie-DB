package com.example.mymoviddb.core.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow

@Dao
interface WatchListTvDao {

    @Query("SELECT * FROM watch_list_tv_show")
    fun getAllWatchListTv(): PagingSource<Int, WatchListTvShow.Result>

    @Query("SELECT * FROM watch_list_tv_show WHERE id IN (:id)")
    fun getWatchListTvById(id: Long): PagingSource<Int, WatchListTvShow.Result>

    @Query("SELECT * FROM watch_list_tv_show WHERE title LIKE :watchListTitle")
    fun getWatchListTvByTitle(watchListTitle: String): PagingSource<Int, WatchListTvShow.Result>

    @Query("DELETE FROM watch_list_tv_show")
    suspend fun clearAllWatchListTv()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(watchListTv: List<WatchListTvShow.Result>)
}