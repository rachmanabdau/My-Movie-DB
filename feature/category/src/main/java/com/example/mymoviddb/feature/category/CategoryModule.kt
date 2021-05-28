package com.example.mymoviddb.feature.category

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@InstallIn(ViewModelComponent::class)
@Module
abstract class CategoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsCategoryrepository(categoryRepository: CategoryShowListRepository): ICategoryShowListAccess
}