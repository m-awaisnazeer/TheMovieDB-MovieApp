package com.applications.database.di

import android.content.Context
import androidx.room.Room
import com.applications.database.MovieDatabase
import com.applications.database.dao.MovieDao
import com.applications.database.dao.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun movieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie.db"
        ).build()
    }

    @Provides
    fun remoteKeysDao(database: MovieDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    @Provides
    fun movieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}