package com.avalon.vflapp.di

import com.avalon.vflapp.service.UserService
import com.avalon.vflapp.service.UserServiceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.FirebaseFunctions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FBServiceModule {

    @Singleton
    @Provides
    fun provideUserService(): UserService {
        return UserServiceImpl(FirebaseFunctions.getInstance(), FirebaseAuth.getInstance())
    }
}