package com.example.themoviedb.common.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themoviedb.common.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<CachedMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: CachedMovie)

    @Query("SELECT * FROM Movie")
    suspend fun getAllMovies(): List<CachedMovie>

    @Query("SELECT * FROM Movie")
    fun getMovies(): PagingSource<Int, CachedMovie>

    @Query("DELETE FROM Movie")
    fun deleteAllMovies()

    @Query("SELECT * FROM Movie WHERE isFavorite=:isFavorite")
    fun getFavoriteMovies(isFavorite: Boolean): Flow<List<CachedMovie>>

    @Query("UPDATE Movie SET isFavorite=:isFavorite where id=:id")
    fun updateMovie(id: Int, isFavorite: Boolean):Int

    @Query("SELECT * FROM Movie WHERE id=:id")
    fun getMovieById(id: Int):Flow<CachedMovie>
}