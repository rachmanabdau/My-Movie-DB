package com.example.mymoviddb.di

import com.example.mymoviddb.account.AccountShowAccess
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.category.CategoryShowListAccess
import com.example.mymoviddb.category.ICategoryShowListAccess
import com.example.mymoviddb.detail.DetailAccess
import com.example.mymoviddb.detail.IDetailAccess
import com.example.mymoviddb.home.HomeAccess
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.main.IMainAccess
import com.example.mymoviddb.main.MainAccess
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
    abstract fun bindHomeAccess(homeAccess: HomeAccess): IHomeAccess

    @Binds
    @ViewModelScoped
    abstract fun bindUserDetial(userDetailAccess: MainAccess): IMainAccess

    @Binds
    @ViewModelScoped
    abstract fun bindShowDetail(detailAccess: DetailAccess): IDetailAccess

    @Binds
    @ViewModelScoped
    abstract fun bindAuthAccess(authenticationAccess: AuthenticationAccess): IAuthenticationAccess

    @Binds
    @ViewModelScoped
    abstract fun bindCategoryMovieAccess(categoryMovieAccess: CategoryShowListAccess): ICategoryShowListAccess

    @Binds
    @ViewModelScoped
    abstract fun bindshowFavourite(showFavouriteAccess: AccountShowAccess): IAccountShowAccess
}
