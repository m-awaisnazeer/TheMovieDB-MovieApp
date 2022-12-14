package com.example.themoviedb.home.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class GetAllMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getMovies()
}