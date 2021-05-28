package com.example.mymoviddb.core.model.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_remote_keys")
data class RemoteKey(@PrimaryKey val nextKey: Int)
