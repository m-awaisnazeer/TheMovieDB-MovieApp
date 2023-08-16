package com.applications.home.domain

import com.applications.domain.repositories.MovieRepository
import javax.inject.Inject

class GetAllMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovies()
}