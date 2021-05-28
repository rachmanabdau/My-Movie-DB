package com.example.mymoviddb.core.di

import android.content.Context
import androidx.room.Room
import com.example.mymoviddb.core.datasource.local.TmdbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        TmdbDatabase::class.java,
        "tmdb_local"
    ).build()
}