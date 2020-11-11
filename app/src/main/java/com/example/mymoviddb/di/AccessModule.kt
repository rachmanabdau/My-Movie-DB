package com.example.mymoviddb.di

import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.category.movie.CategoryMovieListAccess
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.tv.CategoryTVListIAccess
import com.example.mymoviddb.category.tv.ICategoryTVListAccess
import com.example.mymoviddb.detail.DetailAccess
import com.example.mymoviddb.detail.IDetailAccess
import com.example.mymoviddb.favourite.IShowFavouriteAccess
import com.example.mymoviddb.favourite.ShowFavouriteAccess
import com.example.mymoviddb.home.HomeAccess
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.main.IMainAccess
import com.example.mymoviddb.main.MainAccess
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AccessModule {

    @Binds
    abstract fun bindAuthAccess(authenticationAccess: AuthenticationAccess): IAuthenticationAccess

    @Binds
    abstract fun bindHomeAccess(homeAccess: HomeAccess): IHomeAccess

    @Binds
    abstract fun bindCategoryMovieAccess(categoryMovieAccess: CategoryMovieListAccess): ICategoryMovieListAccess

    @Binds
    abstract fun bindCategoryTVAccess(categoryTVAccess: CategoryTVListIAccess): ICategoryTVListAccess

    @Binds
    abstract fun bindShowDetail(detailAccess: DetailAccess): IDetailAccess

    @Binds
    abstract fun bindUserDetial(userDetailAccess: MainAccess): IMainAccess

    @Binds
    abstract fun bindshowFavourite(showFavouriteAccess: ShowFavouriteAccess): IShowFavouriteAccess
}
