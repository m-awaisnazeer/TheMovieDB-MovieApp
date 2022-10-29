package com.example.themoviedb.common.domain.repositories

import androidx.paging.PagingData
import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getFavoriteMovies():Flow<List<Movie>>
    fun updateMovie(movieId: Int,isFavorite:Boolean)
    fun getMovieById(movieId: Int):Flow<Movie>
    fun searchMovie(query:String):Flow<Resource<List<Movie>>>
}