package com.example.mymoviddb.di

import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AccessModule {

    @Binds
    abstract fun bindAccess(authenticationAccess: AuthenticationAccess): IAuthenticationAccess
}
