package com.applications.domain.repositories

import androidx.paging.PagingData
import com.applications.domain.entities.Movie
import com.applications.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getFavoriteMovies():Flow<List<Movie>>
    suspend fun updateMovie(movie: Movie)
    fun getMovieById(movieId: Int):Flow<Movie>
    fun searchMovie(query:String):Flow<Resource<List<Movie>>>
}