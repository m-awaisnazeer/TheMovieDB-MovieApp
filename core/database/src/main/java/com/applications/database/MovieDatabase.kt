package com.applications.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.applications.database.dao.MovieDao
import com.applications.database.dao.RemoteKeysDao
import com.applications.database.model.CachedMovie
import com.applications.database.model.MovieRemoteKeys

@Database(
    entities = [
        CachedMovie::class,
        MovieRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}