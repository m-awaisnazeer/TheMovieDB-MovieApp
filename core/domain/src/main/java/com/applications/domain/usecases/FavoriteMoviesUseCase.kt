package com.applications.domain.usecases

import com.applications.domain.entities.Movie
import com.applications.domain.repositories.MovieRepository
import javax.inject.Inject

class FavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) = repository.updateMovie(movie)
}