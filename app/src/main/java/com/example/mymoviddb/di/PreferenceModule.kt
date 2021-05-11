package com.example.mymoviddb.di

import com.example.mymoviddb.utils.UserPreference
import com.example.mymoviddb.utils.preference.Preference
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    @Singleton
    abstract fun bindsUserPreference(userPreference: UserPreference): Preference
}