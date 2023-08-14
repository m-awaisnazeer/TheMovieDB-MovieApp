package com.applications.favorite.domain

import com.applications.domain.repositories.MovieRepository
import javax.inject.Inject

class GetFavoriteMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getFavoriteMovies()
}