package com.example.themoviedb.home.domain

import com.applications.domain.domain.entities.Movie
import com.applications.domain.domain.repositories.MovieRepository
import javax.inject.Inject

class FavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.updateMovie(movie)
}