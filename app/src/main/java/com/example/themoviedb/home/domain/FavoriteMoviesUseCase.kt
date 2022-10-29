package com.example.themoviedb.home.domain

import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.domain.repositories.MovieRepository

class FavoriteMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.updateMovie(movie)
}