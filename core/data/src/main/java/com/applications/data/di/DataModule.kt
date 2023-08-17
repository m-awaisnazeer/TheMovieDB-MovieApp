package com.applications.data.di

import com.applications.data.MovieRepositoryImp
import com.applications.domain.repositories.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImp): MovieRepository

}