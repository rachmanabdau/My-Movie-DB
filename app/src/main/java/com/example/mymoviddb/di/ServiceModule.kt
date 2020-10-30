package com.example.mymoviddb.di

import com.example.mymoviddb.datasource.remote.NetworkAPI
import com.example.mymoviddb.datasource.remote.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): NetworkService {
        return NetworkAPI.retrofitService
    }
}
