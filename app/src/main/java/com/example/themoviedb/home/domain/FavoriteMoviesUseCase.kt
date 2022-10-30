package com.example.themoviedb.home.domain

import com.example.themoviedb.common.domain.entities.Movie
import com.example.themoviedb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class FavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.updateMovie(movie)
}