package com.example.themoviedb.favorites.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class GetFavoriteMovies @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() = repository.getFavoriteMovies()
}