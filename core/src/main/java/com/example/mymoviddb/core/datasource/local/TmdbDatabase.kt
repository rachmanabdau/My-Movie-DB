package com.example.mymoviddb.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymoviddb.core.datasource.local.dao.*
import com.example.mymoviddb.core.model.category.RemoteKey
import com.example.mymoviddb.core.model.category.movie.FavouriteMovie
import com.example.mymoviddb.core.model.category.movie.WatchListMovie
import com.example.mymoviddb.core.model.category.tv.FavouriteTvShow
import com.example.mymoviddb.core.model.category.tv.WatchListTvShow

@Database(
    entities = arrayOf(
        FavouriteMovie.Result::class,
        FavouriteTvShow.Result::class,
        WatchListMovie.Result::class,
        WatchListTvShow.Result::class,
        RemoteKey::class
    ), version = 1
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
    abstract fun favouriteTvDao(): FavouriteTvDao
    abstract fun watchListMovieDao(): WatchListMovieDao
    abstract fun watchListTvDao(): WatchListTvDao
    abstract fun remotKeyDao(): RemoteKeyDao
}
