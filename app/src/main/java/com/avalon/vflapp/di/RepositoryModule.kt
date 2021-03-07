package com.avalon.vflapp.di

import com.avalon.vflapp.service.UserService
import com.avalon.vflapp.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideLoginRepository(userService: UserService): UserRepository {
        return UserRepository(userService)
    }
}