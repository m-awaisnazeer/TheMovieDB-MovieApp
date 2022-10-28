package com.example.themoviedb.home.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository

class GetAllMovies(
    private val repository: MovieRepository
) {

    operator fun invoke() = repository.getMovies()
}