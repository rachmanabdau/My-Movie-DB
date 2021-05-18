package com.example.mymoviddb.core.di

import com.example.mymoviddb.core.datasource.remote.NetworkAPI
import com.example.mymoviddb.core.datasource.remote.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): NetworkService {
        return NetworkAPI.retrofitService
    }
}
