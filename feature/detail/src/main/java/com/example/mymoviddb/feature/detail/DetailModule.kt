package com.example.mymoviddb.feature.detail

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class DetailModule {

    @Binds
    @ViewModelScoped
    abstract fun bindShowDetail(detailAccess: DetailRepository): IDetailAccess
}