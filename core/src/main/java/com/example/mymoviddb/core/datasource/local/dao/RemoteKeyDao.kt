package com.example.mymoviddb.core.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymoviddb.core.model.category.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM category_remote_keys")
    suspend fun getRemoteKey(): RemoteKey

    @Query("DELETE FROM category_remote_keys")
    suspend fun clearRemoteKey()
}
