package com.example.themoviedb.common.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.common.data.cache.model.MovieRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM MovieRemoteKeys WHERE id=:id")
    suspend fun getRemoteKeys(id:Int): MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(quotes:List<MovieRemoteKeys>)

    @Query("DELETE FROM MovieRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}