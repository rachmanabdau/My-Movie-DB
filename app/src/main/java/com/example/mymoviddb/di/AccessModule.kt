package com.example.mymoviddb.di

import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.category.CategoryShowListRepository
import com.example.mymoviddb.category.ICategoryShowListAccess
import com.example.mymoviddb.detail.DetailRepository
import com.example.mymoviddb.detail.IDetailAccess
import com.example.mymoviddb.home.HomeRepository
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.main.IMainAccess
import com.example.mymoviddb.main.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AccessModule {

    @Binds
    @ViewModelScoped
    abstract fun bindHomeAccess(homeAccess: HomeRepository): IHomeAccess

    @Binds
    @ViewModelScoped
    abstract fun bindUserDetial(userDetailAccess: MainRepository): IMainAccess

    @Binds
    @ViewModelScoped
    abstract fun bindShowDetail(detailAccess: DetailRepository): IDetailAccess

    @Binds
    @ViewModelScoped
    abstract fun bindAuthAccess(authenticationAccess: AuthenticationAccess): IAuthenticationAccess

    @Binds
    @ViewModelScoped
    abstract fun bindCategoryMovieAccess(categoryMovieAccess: CategoryShowListRepository): ICategoryShowListAccess
}
