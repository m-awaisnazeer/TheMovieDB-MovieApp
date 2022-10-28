package com.example.awaisahmadassignment.common.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.awaisahmadassignment.common.domain.model.Movie

@Entity(tableName = "Movie")
data class CachedMovie(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isFavorite:Boolean
){
    companion object{
        fun fromDomain(movie: Movie):CachedMovie{
            return CachedMovie(
                id = movie.id,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                isFavorite = movie.isFavorite
            )
        }

        fun fromDomain(movies: List<Movie>):List<CachedMovie>{
            return movies.map {
                CachedMovie(
                    id = it.id,
                    posterPath = it.posterPath,
                    releaseDate = it.releaseDate,
                    title = it.title,
                    isFavorite = it.isFavorite
                )
            }
        }

        fun toDomain(cachedMovie: CachedMovie):Movie{
            return Movie(
                id = cachedMovie.id,
                posterPath = cachedMovie.posterPath,
                releaseDate = cachedMovie.releaseDate,
                title = cachedMovie.title,
                isFavorite = cachedMovie.isFavorite
            )
        }
    }
}