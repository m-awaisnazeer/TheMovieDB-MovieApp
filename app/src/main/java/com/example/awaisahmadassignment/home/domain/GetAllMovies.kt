package com.example.awaisahmadassignment.home.domain

import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository

class GetAllMovies(
    private val repository: MovieRepository
) {

    operator fun invoke() = repository.getMovies()
}