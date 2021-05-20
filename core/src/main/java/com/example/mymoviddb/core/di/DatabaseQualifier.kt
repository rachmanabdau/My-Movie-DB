package com.example.mymoviddb.core.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavouriteMovieDao

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavouriteTvDao

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WatchListMovieDao

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WatchListTvDao
