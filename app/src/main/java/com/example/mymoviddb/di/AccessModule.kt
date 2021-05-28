package com.example.mymoviddb.di

import com.example.mymoviddb.home.HomeRepository
import com.example.mymoviddb.home.IHomeAccess
import com.example.mymoviddb.login.ILoginAccess
import com.example.mymoviddb.login.LoginRepository
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
    abstract fun bindAuthAccess(authenticationAccess: LoginRepository): ILoginAccess
}
