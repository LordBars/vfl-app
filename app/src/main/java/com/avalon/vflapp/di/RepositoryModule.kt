package com.avalon.vflapp.di

import com.avalon.vflapp.service.UserService
import com.avalon.vflapp.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideLoginRepository(userService: UserService): LoginRepository {
        return LoginRepository(userService)
    }
}