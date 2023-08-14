package com.example.themoviedb.home.domain

import com.applications.domain.domain.repositories.MovieRepository
import javax.inject.Inject

class GetAllMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovies()
}