package com.example.themoviedb.common.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.common.data.cache.dao.MovieDao
import com.example.themoviedb.common.data.cache.dao.RemoteKeysDao
import com.example.themoviedb.common.data.cache.model.CachedMovie
import com.example.themoviedb.common.data.cache.model.MovieRemoteKeys

@Database(
    entities = [
        CachedMovie::class,
        MovieRemoteKeys::class
    ],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}