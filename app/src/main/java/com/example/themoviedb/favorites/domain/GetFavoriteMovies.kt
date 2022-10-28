package com.example.themoviedb.favorites.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository

class GetFavoriteMovies(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getFavoriteMovies()
}