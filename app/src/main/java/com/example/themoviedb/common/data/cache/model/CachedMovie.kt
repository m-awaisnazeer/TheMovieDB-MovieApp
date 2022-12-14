package com.example.themoviedb.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviedb.common.domain.entities.Movie

@Entity(tableName = "Movie")
data class CachedMovie(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val overView:String,
    val isFavorite:Boolean
){
    companion object{
        fun toDomain(cachedMovie: CachedMovie): Movie {
            return Movie(
                id = cachedMovie.id,
                posterPath = cachedMovie.posterPath,
                releaseDate = cachedMovie.releaseDate,
                title = cachedMovie.title,
                overView = cachedMovie.overView,
                isFavorite = cachedMovie.isFavorite
            )
        }
    }
}