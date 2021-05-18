package com.example.mymoviddb.core.di

import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.core.utils.preference.UserPreference
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