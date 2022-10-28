package com.example.awaisahmadassignment.common.domain.repositories

import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getFavoriteMovies():Flow<List<Movie>>
    fun updateMovie(movieId: Int,isFavorite:Boolean)
    fun getMovieById(movieId: Int):Flow<Movie>
}