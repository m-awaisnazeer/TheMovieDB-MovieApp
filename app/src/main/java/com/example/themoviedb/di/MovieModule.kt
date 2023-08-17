package com.example.themoviedb.di

import com.applications.utils.DefaultDispatcher
import com.applications.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieModule {
    companion object{


        @Provides
        @Singleton
        fun bindDispatcher(): DispatcherProvider =
            DefaultDispatcher()
    }

}