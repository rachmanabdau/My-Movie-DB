package com.example.mymoviddb.di

import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.home.HomeAccess
import com.example.mymoviddb.home.IHomeAccess
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
    abstract fun bindHomeAccess(authenticationAccess: HomeAccess): IHomeAccess
}
