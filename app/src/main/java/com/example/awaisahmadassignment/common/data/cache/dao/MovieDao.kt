package com.example.awaisahmadassignment.common.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<CachedMovie>)

    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies():List<CachedMovie>

    @Query("SELECT * FROM Movie")
    fun getMovies(): PagingSource<Int, CachedMovie>

    @Query("DELETE FROM Movie")
    fun deleteAllMovies()
}