package com.example.mymoviddb.feature.favourite

import androidx.paging.ExperimentalPagingApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@InstallIn(ViewModelComponent::class)
@Module
abstract class FavouriteModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsFavouriteRepository(favouriteRepository: FavouriteRepository): IFavouriteAccess
}