package com.example.awaisahmadassignment.favorites.domain

import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository

class GetFavoriteMovies(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getFavoriteMovies()
}